package com.github.accountmanagementproject.config.security;


import com.github.accountmanagementproject.repository.redis.RedisTokenRepository;
import com.github.accountmanagementproject.web.dto.accountAuth.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
import java.util.stream.Collectors;


@Component
public class JwtProvider {
    private final RedisTokenRepository redisTokenRepository;

    private final SecretKey key;//= Jwts.SIG.HS256.key().build();  이건 랜덤키 자동생성


    private static final long REFRESH_TOKEN_EXPIRATION = 1000*60*10;//테스트를 위해 10분
    private static final long ACCESS_TOKEN_EXPIRATION = 1000*60;//1분
    public static String getTokenType(){
        return "Bearer";
    }


    public JwtProvider(@Value("${jwtpassword.source}")String keySource, RedisTokenRepository redisTokenRepository) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keySource));
        this.redisTokenRepository = redisTokenRepository;
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
    //리프레시토큰 생성
    public String createNewRefreshToken() {//여기도수정
        return createRefreshToken(new Date(new Date().getTime()+REFRESH_TOKEN_EXPIRATION));
    }
    public String createRefreshToken(Date exp){//이부분 부터 수정해야됨
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(exp)//나중에 유효시간도 숨기는게 좋음
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    //리프레시 토큰의 유효시간만큼 저장기간을 설정하고 레디스에 저장 이후 Dto 생성
    public TokenDto saveRefreshTokenAndCreateTokenDto(String accessToken, String refreshToken, Duration exp){
        //getExpirationTime 으로 유효시간 가져오기
        // 그전에 tokenParsing 으로 refreshToken 의 유효성검사와 파싱을 진행한다.
        redisTokenRepository.tokenSave(accessToken, refreshToken, exp);

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

    public TokenDto tokenRefresh(String accessToken, String clientRefreshToken){
            //테스트를위한 세이브 하나
//            redisTokenRepository.tokenSave("eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDkxMzUwMDMsInN1YiI6ImFiYzJAYWJjLmNvbSIsInJvbGVzIjoiUk9MRV9VU0VSIiwiZXhwIjoxNzA5MTM4NjAzfQ.didiQMVqUvwGP-N9OZdf2jwKnyaiMWocpWKI-ZOHKz0", "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTAzMjc0MDgsImV4cCI6MTcxMDQxMzgwOH0.OCW9wcdlo8g1vWsnIfA3S757HDg_VxNLEQHiR3H3NMo", Duration.ofMillis(REFRESH_TOKEN_EXPIRATION));
        //리프레시 토큰 유효성 검사와 파싱
        Jws<Claims> refreshTokenClaims = tokenParsing(clientRefreshToken);
        String dbRefreshToken = redisTokenRepository.getAndDeleteJwtEntity(accessToken);//가져오면서 지움
        if(clientRefreshToken.equals(dbRefreshToken)){//사용자의 리프레시토큰과 db의 리프레시토큰 대조
            //엑세스토큰 payload
            String payloadStr = new String(Base64.getUrlDecoder().decode(accessToken.split("\\.")[1]));
            //payload Map 형식으로 변환
            Map<String, Object> payload = new BasicJsonParser().parseMap(payloadStr);
            //새로운 액세스 토큰 생성
            String newAccessToken = createNewAccessToken(payload.get("sub").toString(), payload.get("roles").toString());
            // 리프레시 토큰 유효시간
            Date refreshTokenExp = refreshTokenClaims.getPayload().getExpiration();
            // 해당 유효시간으로 새로운 토큰 생성
            String newRefreshToken = createRefreshToken(refreshTokenExp);
            return saveRefreshTokenAndCreateTokenDto(newAccessToken, newRefreshToken,
                    Duration.between(Instant.now(), refreshTokenExp.toInstant()));

        }else throw new RuntimeException("사용 불가능한 리프레시 토큰");

    }

    public Jws<Claims> tokenParsing(String token){
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token);
    }
    public Duration getExpirationTime(Claims refreshTokenClaims){
        return Duration.between(Instant.now(), refreshTokenClaims.getExpiration().toInstant());
    }
}
