package com.github.accountmanagementproject.config.security;


import com.github.accountmanagementproject.repository.redis.RedisRepository;
import com.github.accountmanagementproject.web.dto.accountAuth.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


@Component
public class JwtProvider {
    private final RedisRepository redisRepository;

    private final SecretKey key;//= Jwts.SIG.HS256.key().build();  이건 랜덤키 자동생성


    private static final long REFRESH_TOKEN_EXPIRATION = 1000*60*10;//테스트를 위해 10분
    private static final long ACCESS_TOKEN_EXPIRATION = 1000*60*60;//60분
    public static String getTokenType(){
        return "Bearer";
    }


    public JwtProvider(@Value("${jwtpassword.source}")String keySource, RedisRepository redisRepository) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keySource));
        this.redisRepository = redisRepository;
    }


    //이메일과 롤을 넣어 엑세스토큰 생성
    public String createNewAccessToken(String email, String roles){
        Date now = new Date();
        return Jwts.builder()
                .issuedAt(now)
                .expiration(new Date(now.getTime()+ACCESS_TOKEN_EXPIRATION))
                .subject(email)
                .claim("roles", roles)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
    //새로운 리프레시 토큰 생성
    public String createNewRefreshToken() {
        return createRefreshToken(new Date(new Date().getTime()+REFRESH_TOKEN_EXPIRATION));
    }

    //만료시간 지정 리프레시 토큰 생성
    public String createRefreshToken(Date exp){
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(exp)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    //리프레시 토큰의 유효시간만큼 저장기간을 설정하고 레디스에 저장 이후 Dto 생성
    public TokenDto saveRefreshTokenAndCreateTokenDto(String accessToken, String refreshToken, Duration exp){

        redisRepository.save(accessToken, refreshToken, exp);

        return TokenDto.builder()
                .tokenType(getTokenType())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
            Jws<Claims> claimsJws = tokenParsing(accessToken);//검증은 여기서 내부적으로 진행됨

            Claims payload = claimsJws.getPayload();
            if(payload.getSubject()==null) throw new NullPointerException("payload의 subject값이 null 입니다.");
            Collection<? extends GrantedAuthority> roles = Arrays.stream(payload.get("roles").toString().split(","))
                .map(role -> new SimpleGrantedAuthority(role))
                .toList();
            return new UsernamePasswordAuthenticationToken(payload.getSubject(), accessToken, roles);
    }

    @Transactional
    public TokenDto tokenRefresh(String accessToken, String clientRefreshToken){
        //리프레시 토큰 유효성 검사와 파싱
        Jws<Claims> refreshTokenClaims = tokenParsing(clientRefreshToken);
        String dbRefreshToken = redisRepository.getAndDeleteValue(accessToken);//가져오면서 지움
        //사용자의 리프레시토큰과 db의 리프레시토큰 대조
        if(!clientRefreshToken.equals(dbRefreshToken))
            throw new NoSuchElementException("Token cannot be used");
        //새로운 토큰 생성
        String newAccessToken = createANewAccessTokenWithOldAccessToken(accessToken);

        // 리프레시 토큰 유효시간
        Date refreshTokenExp = refreshTokenClaims.getPayload().getExpiration();
        // 해당 유효시간으로 새로운 토큰 생성
        String newRefreshToken = createRefreshToken(refreshTokenExp);

        return saveRefreshTokenAndCreateTokenDto(newAccessToken, newRefreshToken,
                Duration.between(Instant.now(), refreshTokenExp.toInstant()));
    }

    private String createANewAccessTokenWithOldAccessToken(String accessToken) {
        //payload 추출
        Map<String, Object> payload = extractPayloadFromToken(accessToken);
        //새로운 액세스 토큰 생성
        return createNewAccessToken(payload.get("sub").toString(), payload.get("roles").toString());
    }

    private Map<String, Object> extractPayloadFromToken(String accessToken) {
        //엑세스토큰 payload
        String payloadStr = new String(Base64.getUrlDecoder().decode(accessToken.split("\\.")[1]));
        //payload Map 형식으로 변환
        return new BasicJsonParser().parseMap(payloadStr);
    }

    public Jws<Claims> tokenParsing(String token){
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token);
    }
}
