package com.ivson.modelagemconceitual.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	
//	Pegar o que esta no application.properties	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	/**
	 * Gerar o TOKEN
	 * @param username
	 * @return
	 */
	public String generateToken(String username) {
		
		// gera o TOKEN
		return Jwts.builder()
				   .setSubject(username)
				   .setExpiration(new Date(System.currentTimeMillis() + expiration))
				   .signWith(SignatureAlgorithm.HS512, secret.getBytes())
				   .compact();
	}

}
