package com.toni.musify.security.jwt;


import com.toni.musify.domain.user.model.UserCredentials;
import com.toni.musify.domain.user.model.UserViewDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static com.toni.musify.security.jwt.JwtService.ClaimsEnum.*;

@Service
public class JwtService {

    private static final String SECRET_KEY="UiZyl6wYuHIQICO9F4WxLp0P8WVAiWfTLvAmvH7zcYko65SXoGe4/U103YyffKVQ";

    public String extractUsername(String jwt) {
        return extractClaim(jwt,Claims::getSubject);
    }

    public String extractId(String jwt){
        return extractClaim(jwt,Claims::getId);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=getClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
    public String generateToken(UserDetails userDetails,Integer userId){
        Map<String,Object> claims=new HashMap<>();
        claims.put("USER_ID",userId);
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setId(userDetails.)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }*/

    public String generateToken(@NonNull UserViewDTO user){
        Map<String,Object> claims=Map.of(
                ID.name(),user.id(),
                EMAIL.name(),user.email(),
                ROLE.name(),user.role().toString()
        );

        return Jwts.builder()
                .addClaims(claims)
                .setIssuer(UUID.randomUUID().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserCredentials userCredentials){
        final String username=extractUsername(token);
        return (username.equals(userCredentials.email())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public Claims getClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public enum ClaimsEnum {
        EMAIL, ID,ROLE
    }

}
