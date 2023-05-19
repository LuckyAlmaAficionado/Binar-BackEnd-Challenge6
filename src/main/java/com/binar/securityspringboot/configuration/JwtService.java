package com.binar.securityspringboot.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "48404D635166546A576E5A7234753778214125432A462D4A614E645267556B58";


    // todo -> mengambil dan juga memberikan data mengenai user details
    public String generatedToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // todo -> berguna untuk membuat token
    private String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // todo -> memberikan subject username
                .setIssuedAt(new Date(System.currentTimeMillis())) // todo -> memberikan data mengenai kapan token ini dibuat
                .setExpiration(new Date(System.currentTimeMillis() + 6000000)) // todo -> menetapkan kapan token ini kadarluasa
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // todo -> menggunakan SignatureAlgorithm.HS256
                .compact();
    }


    // todo -> create function for check is token expired or no
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // todo -> create function for check isTokenExpired
    public boolean isTokenExpired(String token) {
        /*
            todo -> setelah mendapatkan kapan tanggal expired yang telah diberikan diawal
                pada pembuatan code maka akan di cek apakah tanggal melebihi tanggal sekarang
         */
        return extracExporation(token).before(new Date());
    }

    // todo -> mengambil data kapan expired dari token
    private Date extracExporation(String token) {
        return extractClaims(token, Claims::getExpiration);
    }


    // todo -> extract username
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }


    // todo -> mengembalikan data yang diperlukan oleh fungsi lain dengan memberikan token
    private <T> T extractClaims(
            String token,
            Function<Claims, T> claimsResolver
    ) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // todo -> mendapatkan body dari token yang diberikan
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()// todo -> untuk melewati builder digunakan untuk memulai build parser JWT
                .setSigningKey(getSignKey())// todo -> menyetel sign in key, karena Ketika Ketika kita mencoba untuk menghasilkan atau mengdekode token kita perlu menggunakan kunci signature
                .build()// todo -> object untuk membangun parser JWT
                .parseClaimsJws(token) // todo -> memparsing dan memveritivikasi token JWT yang diberikan
                .getBody(); // todo -> mendapatkan semua claim yang dimiliki di dalam token
    }

    // todo -> memberikan getSignKey kepada fungsi yang memerlukan
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // todo -> Karena basis ini adalah yang ingin dikodekan pada BASE64.decode. setelah kunci rahasia dikodekan yang perlu dilakukan hanyalah mengembalikan kunci
        return Keys.hmacShaKeyFor(keyBytes); // todo -> dalam JJWT (Java JWT) untuk membuat kunci HMAC-SHA berdasarkan array byte yang diberikan "HS256"
    }


}
