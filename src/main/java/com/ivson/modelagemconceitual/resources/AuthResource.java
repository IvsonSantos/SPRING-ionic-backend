package com.ivson.modelagemconceitual.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivson.modelagemconceitual.dto.EmailDTO;
import com.ivson.modelagemconceitual.security.JWTUtil;
import com.ivson.modelagemconceitual.security.UserSpringSecurity;
import com.ivson.modelagemconceitual.services.AuthService;
import com.ivson.modelagemconceitual.services.UserService;

/**
 * Para dar refresh token
 * @author Santo
 */
@RestController
@RequestMapping("/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {		
		UserSpringSecurity user = UserService.authenticated(); // esse endpoint tem que estar autenticado
		String token = jwtUtil.generateToken(user.getUsername()); // gera um novo token com meu usuario, renovando o tempo
		response.addHeader("Authorization", "Bearer " + token); // adiciona a resposta do token ao header
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
}