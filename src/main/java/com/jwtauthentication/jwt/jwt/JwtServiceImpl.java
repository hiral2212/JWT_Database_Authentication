package com.jwtauthentication.jwt.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.jwtauthentication.jwt.constants.SecurityConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtServiceImpl implements JwtService {

    @Override
    /*
     * The Map<String, Object> claims parameter in the generateToken method is used
     * to store custom claims in the JWT token. You can add any
     * additional information you want to include in the token to this map.
     * 
     */
    public String generateToken(String userName) {

        Map<String, Object> claims = new HashMap<>();
        return createToken(userName, claims);
    }

    /*
     * JSON Web Token (JWT) using the Jwts.builder() method.
     * The setClaims() method is used to add custom claims to the JWT,
     * in this case, the current time in milliseconds (System.currentTimeMillis())
     * is added as an "issued at" claim and the expiration time is set to 30 minutes
     * from now.
     * The setSubject() method is used to set the subject of the JWT (in this case,
     * the user's name). The signWith() method is used to sign the
     * JWT with a secret key using the HS256 algorithm.
     */

    private String createToken(String userName, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

    }

    /*
     * The getSignKey() method is used to retrieve the secret key used for signing
     * the JWTs.
     */

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstant.SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    /*
     * The Claims object contains various claims, including the subject claim, which
     * contains the username of the user
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }


    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public <T>T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
        .parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    @Override
    public java.sql.Date extractExpiration(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extractExpiration'");
    }

}
