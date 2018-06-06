package com.ivson.modelagemconceitual.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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
	
	public boolean tokenValido(String token) {
		
		/**
		 * Claims é um tipo do JWT que armazena as reinvidicações do TOKEN
		 */
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();	// retorna o usuario
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			} 
		}
		return false;
	}
	
	public String getUsername(String token) {
		
		/**
		 * Claims é um tipo do JWT que armazena as reinvidicações do TOKEN
		 */
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();	// retorna o usuario
		}
		
		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser()
							.setSigningKey(secret.getBytes())
							.parseClaimsJws(token)
							.getBody();
		} catch (Exception e) {
			return null;
		}
	}

}
