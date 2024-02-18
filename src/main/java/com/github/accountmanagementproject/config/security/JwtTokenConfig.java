package com.github.accountmanagementproject.config.security;

import com.github.accountmanagementproject.config.security.exception.ExceptionContextHolder;
import com.github.accountmanagementproject.web.dto.account.JwtTokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class JwtTokenConfig {


    private final SecretKey key;//= Jwts.SIG.HS256.key().build();  이건 랜덤키 자동생성


    public JwtTokenConfig(@Value("${jwtpassword.source}")String keySource) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keySource));
    }

    public JwtTokenDTO createToken(Authentication authentication) {
        String roles = authentication.getAuthorities().stream()
                .map(authority->authority.getAuthority())
                .collect(Collectors.joining(","));
        Date now = new Date();

        String accessToken = Jwts.builder()
                .issuedAt(now)
                .subject(authentication.getName())
                .claim("roles", roles)
                .expiration(new Date(now.getTime()+1000*60*60))
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .issuedAt(now)
                .expiration(new Date(now.getTime()+1000*60*60*24))
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        return JwtTokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public Authentication getAuthentication(String accessToken) {

        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(accessToken);//검증은 여기서 내부적으로 진행됨

            Claims payload = claimsJws.getPayload();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(payload.get("roles").toString().split(","))
                            .map(role->new SimpleGrantedAuthority(role))
                            .toList();

            return new UsernamePasswordAuthenticationToken(payload.getSubject(), accessToken, authorities);

        } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            ExceptionContextHolder.setExceptionMessage(e.getMessage(),"토큰의 형식이 올바르지 않거나 지원되지 않는 형식입니다.");
            return null;
        } catch (ExpiredJwtException e){
            System.out.println(e.getMessage());
            ExceptionContextHolder.setExceptionMessage(e.getMessage(),"만료된 토큰 입니다.");
            return null;
        } catch (SignatureException e){
            System.out.println(e.getMessage());
            ExceptionContextHolder.setExceptionMessage(e.getMessage(),"변조된 토큰이거나, 잘못된 서명의 토큰 입니다.");
            return null;
        } catch (Exception e){
            System.out.println(e.getMessage());
            ExceptionContextHolder.setExceptionMessage(e.getMessage(),"확인되지 않은 오류");
            return null;
        }
//        String email = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwtToken).getBody().getSubject();
//        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
