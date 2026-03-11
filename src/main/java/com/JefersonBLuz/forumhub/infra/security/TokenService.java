package com.JefersonBLuz.forumhub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration}")
    private Long expirationInMillis;

    public String gerarToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Instant.now().plusMillis(expirationInMillis))
                .sign(algorithm);
    }

    public String getSubject(String tokenJwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(tokenJwt)
                    .getSubject();
        } catch (TokenExpiredException exception) {
            throw new JWTVerificationException("Token JWT expirado.");
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token JWT invalido.");
        }
    }
}
