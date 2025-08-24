package com.github.accountmanagementproject.service.account;

import com.github.accountmanagementproject.common.exceptions.CustomBadRequestException;
import com.github.accountmanagementproject.common.exceptions.CustomServerException;
import com.github.accountmanagementproject.repository.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.github.accountmanagementproject.common.MyUtils.generateRandomNumber;


@Service
@RequiredArgsConstructor
@Log4j2
public class PhoneVerifyService {

    private final DefaultMessageService defaultMessageService;
    private final RedisRepository redisRepository;
    @Value("${cool-sms.phone-number}")
    private String serverPhoneNumber;

    private Message createMessage(String to, String text) {
        Message message = new Message();
        message.setFrom(serverPhoneNumber);
        message.setTo(to);
        message.setText(text);
        return message;
    }

    private void sendExceptionHandling(Message message) {
        try {
            defaultMessageService.send(message);
        } catch (NurigoMessageNotReceivedException e) {
            log.warn(e.getMessage(), e);
            throw CustomServerException.of()
                    .customMessage("문자 발송 실패")
                    .build();
        } catch (NurigoEmptyResponseException e) {
            log.warn(e.getMessage(), e);
            throw CustomServerException.of()
                    .customMessage("문자 발송 실패 (빈 응답)")
                    .build();
        } catch (NurigoUnknownException e) {
            log.warn(e.getMessage(), e);
            throw CustomServerException.of()
                    .customMessage("문자 발송 실패 (알 수 없는 에러)")
                    .build();
        }
    }
    private String replacePhoneNumber(String phoneNumber) {
        return phoneNumber.replace(" ", "").replace("-", "");
    }

    public void sendMessage(String phoneNumber) {
        String to = replacePhoneNumber(phoneNumber);
        String verifyCode = String.valueOf(generateRandomNumber());
        Message message = createMessage(to, verifyCode);
        //레디스에 저장
        redisRepository.save(to, verifyCode, Duration.ofMinutes(10));
        //발송시작
        sendExceptionHandling(message);
    }
    public boolean verifyCode(String phoneNumber, String code) {
        String to = replacePhoneNumber(phoneNumber);
        String serverCode = redisRepository.getValue(to);
        if(serverCode == null)
            throw CustomBadRequestException.of()
                    .customMessage("인증 코드가 만료되었습니다. 다시 시도해주세요.")
                    .build();
        return serverCode.equals(code);
    }
}
