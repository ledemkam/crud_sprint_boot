package com.crud_tutospring.demo.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component //afin qu elle soit geree par spring
public class JwtUtils {
    //cette classe va nous permettre a contruire le token

    //1 etape : on resuprere les valeurs de la configuration(dans application.properties)

    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.expiration-time}")
    private long expirationTime;

    //2 etape : on va creer une methode qui va generer le token

    public  String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    //3 etape methode qui va creer le token createToken
    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder() //obuilder construire le token bzw jwt
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(getSignKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    //on cree la methode pour la cle Sign : getSignKey()
    //on pouurai avoir apartir de cette methode une key secret apartir de la cle secretKey(la valeur de chaines de caracteres donnee dans application.properties)

    private Key getSignKey(){
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes,SignatureAlgorithm.HS256.getJcaName());
    }

    //4 etape : on va creer une methode qui va verifier si le token est valide

    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        //pour verifie si l utilisateur a vraiment le droit
        //on va retourne avec equals si le username est le meme que celui de l utilisateur et si le token n est pas expire
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
        
    }

    //5 etape : on va creer une methode qui va extraire le username du token

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    //6 etape : on va creer une methode qui va extraire la date d expiration du token

    public Date extractExpirationDate(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function <Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims (String token){
        return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
    }

}
