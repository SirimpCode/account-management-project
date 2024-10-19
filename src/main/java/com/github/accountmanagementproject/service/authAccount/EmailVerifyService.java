package com.github.accountmanagementproject.service.authAccount;

import com.github.accountmanagementproject.repository.account.users.MyUsersJpa;
import com.github.accountmanagementproject.repository.redis.RedisRepository;
import com.github.accountmanagementproject.service.customExceptions.CustomServerException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class EmailVerifyService {

    private final JavaMailSender mailSender;
    private final MyUsersJpa myUsersJpa;
    private final RedisRepository redisRepository;
    @Value("${spring.mail.username}")
    private String from;

    //중복 확인
    public boolean duplicateCheckEmail(String email) {
        return !myUsersJpa.existsByEmail(email);
    }
    //메일 발송
    public void sendVerifyCodeToEmail(String to){
        String verifyCode = String.valueOf(generateRandomNumber());
        try {
            //html 파일 읽기
            String htmlContent = Files.readString(Paths.get("src/main/resources/MailTemplate.html"));
            // 플레이스홀더 {{verifyCode}}를 실제 인증 코드로 치환
            htmlContent = htmlContent.replace("{{verifyCode}}", verifyCode);
            htmlContent = htmlContent.replace("{{email}}", from);

            //mailSender에 MimeMessage를 생성
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            //MimeMessageHelper에 mimeMessage를 넣어줌
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            //수신자, 제목 , 본문
            helper.setTo(to);
            helper.setSubject("Account-Management-Project Email 인증코드");
            helper.setText(htmlContent, true);
            //메일 보내기
            mailSender.send(mimeMessage);
            //레디스 저장 (유효 기간 10분)
            redisRepository.save(to, verifyCode, Duration.ofMinutes(10));
        } catch (MessagingException e) {
            throw new CustomServerException.ExceptionBuilder()
                    .customMessage("Failed to send email")
                    .systemMessage(e.getMessage())
                    .request(to)
                    .build();
        } catch (IOException e) {
            throw new CustomServerException.ExceptionBuilder()
                    .customMessage("Failed read html file")
                    .systemMessage(e.getMessage())
                    .request(to)
                    .build();
        }catch (Exception e){
            throw new CustomServerException.ExceptionBuilder()
                    .customMessage("Server Error")
                    .systemMessage(e.getMessage())
                    .request(to)
                    .build();
        }

    }
    //랜덤 코드 생성
    private int generateRandomNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    //이메일 인증
    public boolean verifyEmail(String email, String code) {
        String verifyCode = redisRepository.getValue(email);
        if(verifyCode == null){
            return false;
        }
        return verifyCode.equals(code);
    }
}
