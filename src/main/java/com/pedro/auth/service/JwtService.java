package com.pedro.auth.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    //@Value serve para ler os valores do properties.
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;


    public String generateToken(UserDetails user){
        return Jwts.builder()
                //coloca o email do usuário dentro do token
                .subject(user.getUsername())
                // registra quando o token foi criado
                .issuedAt(new Date())
                //define quando o token vai expirar
                .expiration(new Date(System.currentTimeMillis() + expiration))
                // assina o token com a chave secreta pra ninguém conseguir falsificar
                .signWith(getKey())
                // converte tudo isso numa String
                .compact();

    }

    //Recebe um token e extrai o email que está dentro dele.
    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(getKey()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    //verifica se o token é válido. Checa duas coisas:
    //O email dentro do token bate com o usuário?
    //O token ainda não expirou?
    public boolean isValid(String token, UserDetails user) {
        return extractUsername(token).equals(user.getUsername())
                && !isExpired(token);
    }

    // verifica se o token já passou da data de expiração.
    private boolean isExpired(String token) {
        return Jwts.parser().verifyWith(getKey()).build()
                .parseSignedClaims(token).getPayload()
                .getExpiration().before(new Date());
    }

    //converte a chave secreta do application.properties
    // num formato que o jjwt entende pra assinar e verificar tokens.
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}

