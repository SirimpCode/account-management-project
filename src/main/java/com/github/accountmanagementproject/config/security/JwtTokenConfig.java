package com.github.accountmanagementproject.config.security;

import com.github.accountmanagementproject.web.dto.account.JwtTokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;


@Component
public class JwtTokenConfig {


    private final SecretKey key;//= Jwts.SIG.HS256.key().build();  이건 랜덤키 자동생성


    public JwtTokenConfig(@Value("${jwtpassword.source}")String keySource) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(keySource));
    }

    public JwtTokenDTO createToken(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(authority->authority.getAuthority())
                .toList();
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

//        return Jwts.builder()
//                .setIssuedAt(now)
//                .setSubject(email)
//                .claim("role", roles)
//                .setExpiration(new Date(now.getTime()+1000L * 60 *60))
//                .signWith(SignatureAlgorithm.HS256, KEY)
//                .compact();
    }


//    public boolean validateToken(String token){
//        try{//.ExpiredJwtException 토큰검증실패시 캐치문으로 넘어감.
//            Claims claims = Jwts.parser()
//                    .setSigningKey(key).parseClaimsJws(token)
//                    .getBody();
//            return claims.getExpiration().after(new Date());
//        }catch (Exception e){
//            return false;
//        }
//    }
    public Authentication getAuthentication(String accessToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(accessToken);//검증은 여기서 내부적으로 진행됨

            Claims payload = claimsJws.getPayload();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(payload.get("roles").toString().split(","))
                            .map(role->new SimpleGrantedAuthority(role))
                            .toList();
            UserDetails principal = new User(payload.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(payload.getSubject(), accessToken, principal.getAuthorities());

        } catch (SignatureException e) {
            // 비밀키 일치 X 처리
            return null;
        } catch (ExpiredJwtException e) {
            // 만료 exception 처리
            return null;
        }
//        String email = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwtToken).getBody().getSubject();
//        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
