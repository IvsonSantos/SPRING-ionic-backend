package com.ivson.modelagemconceitual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Filtro para autorizações
 * @author Santo
 *
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, 
								  JWTUtil jwtUtil,
								  UserDetailsService userDetailsService) {
		super(authenticationManager);		
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;		
	}
	
	/**
	 * Metodo que vai verificar se o cara realmente existe e tem autorização
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws IOException, ServletException {
		
		// pega o TOKEN no cabeçalho
		String header = request.getHeader("Authorization");
		
		// para libearr a autoorização do usuario
		if (header != null && header.startsWith("Bearer ")) {			
			
			UsernamePasswordAuthenticationToken auth = getAuthentication(request, 
																	     header.substring(7)); // passa so o Token, sem o "Bearer "			
			if (auth != null) {
				// libera a autorização do usuario no filtro
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * Gerar um objeto a partir do Token
	 * @param request
	 * @param substring
	 * @return
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {

		if (jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}

}
