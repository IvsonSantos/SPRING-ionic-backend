package com.ivson.modelagemconceitual.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivson.modelagemconceitual.security.JWTUtil;
import com.ivson.modelagemconceitual.security.UserSpringSecurity;
import com.ivson.modelagemconceitual.services.UserService;

/**
 * Para dar refresh token
 * @author Santo
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		
		// esse endpoint tem que estar autenticado
		UserSpringSecurity user = UserService.authenticated();
		
		// gera um novo token com meu usuario, renovando o tempo
		String token = jwtUtil.generateToken(user.getUsername());
		
		// adiciona a resposta do token ao header
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
}