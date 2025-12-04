package com.example.game.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    // 1 tạo token
    public String genToken(String username) {
        Map<String, Objects> clams = new HashMap<>();
        return createToken(clams, username);
    }

    public String createToken(Map<String, Objects> clams, String username) {
        return Jwts.builder() //-> design pattern : builder pattern xây dựng đối tượng
                // theo từng bước
                .setClaims(clams)// bố sung thông tin phụ nhu email, role,...
                .setSubject(username)// tên chủ sỡ hữu token
                .setIssuedAt(new Date(System.currentTimeMillis()))// thời gian tạo token
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // thời gian hết hạn
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // đánh dấu để tránh token bị sửa
                .compact(); // nén lại
    }

    // 2. lấy key

    public Key getSignKey() {
//        giải mã key từ hex/base64 trong file config
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        bắt buộc chuỗi trong properties phải là chuỗi hex hoặc base64 hợp lệ
//        vd: nếu chuoi thường thì dùng keyBytes =secretKey.getBytes()
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> clamsResolver) {
        final Claims claims = extractALLClaims(token);
        return clamsResolver.apply(claims);
    }

    public Claims extractALLClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJwt(token).getBody();
    }

    public Boolean validExpiration(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return  (username.equals(userDetails.getUsername()) && !validExpiration(token));
    }
}
