package com.ivson.modelagemconceitual.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ivson.modelagemconceitual.security.UserSpringSecurity;

public class UserService {

	public static UserSpringSecurity authenticated() {
		
		try {
			// retorna o usuario logado no sistema
			return (UserSpringSecurity) SecurityContextHolder
													.getContext()
													.getAuthentication()
													.getPrincipal();

		} catch (Exception e) {
			return null;
		}
	}
}