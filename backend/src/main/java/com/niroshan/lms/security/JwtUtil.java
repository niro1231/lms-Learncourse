package com.niroshan.lms.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;



@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secret;


    @Value("${jwt.expiration}")
    private long expiration;



    private SecretKey getSigningKey(){

        return Keys.hmacShaKeyFor(
                secret.getBytes()
        );

    }



    // Generate JWT Token

    public String generateToken(
            String email,
            String role
    ){

        Date now = new Date();

        Date expiry =
                new Date(
                        System.currentTimeMillis()
                                + expiration
                );


        return Jwts.builder()

                .subject(email)

                .claim(
                        "role",
                        role
                )

                .issuedAt(now)

                .expiration(expiry)

                .signWith(
                        getSigningKey()
                )

                .compact();

    }





    // Extract Email from Token

    public String extractEmail(
            String token
    ){

        return Jwts.parser()

                .verifyWith(
                        getSigningKey()
                )

                .build()

                .parseSignedClaims(token)

                .getPayload()

                .getSubject();

    }





    // Extract Role (still useful if needed)

    public String extractRole(
            String token
    ){

        return Jwts.parser()

                .verifyWith(
                        getSigningKey()
                )

                .build()

                .parseSignedClaims(token)

                .getPayload()

                .get(
                        "role",
                        String.class
                );

    }





    // Validate JWT with UserDetails

    public boolean isValid(
            String token,
            UserDetails userDetails
    ){


        String email =
                extractEmail(token);



        return email.equals(
                userDetails.getUsername()
        )
                &&
                !isExpired(token);

    }





    // Check Token Expiration

    private boolean isExpired(
            String token
    ){


        Date expiry =

                Jwts.parser()

                        .verifyWith(
                                getSigningKey()
                        )

                        .build()

                        .parseSignedClaims(token)

                        .getPayload()

                        .getExpiration();



        return expiry.before(
                new Date()
        );

    }

}