package com.github.accountmanagementproject.config.security;


import com.github.accountmanagementproject.web.dto.account.JwtTokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

            Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(accessToken);//검증은 여기서 내부적으로 진행됨

            Claims payload = claimsJws.getPayload();
            if(payload.getSubject()==null) throw new NullPointerException("payload의 subject값이 null 입니다.");
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(payload.get("roles").toString().split(","))
                            .map(role -> new SimpleGrantedAuthority(role))
                            .toList();

            return new UsernamePasswordAuthenticationToken(payload.getSubject(), accessToken, authorities);
    }
}
