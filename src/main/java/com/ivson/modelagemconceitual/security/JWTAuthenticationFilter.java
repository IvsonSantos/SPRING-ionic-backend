package com.ivson.modelagemconceitual.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivson.modelagemconceitual.dto.CredenciaisDTO;

/**
 * Esse filtro (UsernamePasswordAuthenticationFilter) automaticamente FILTRA
 * toda requisicao que venha do ENDPOINT /login (ja é reservado para o Spring
 * Security)
 * 
 * @author Santo
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
    
    private JWTUtil jwtUtil;
    
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    	setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, 
												HttpServletResponse res)
															throws AuthenticationException {

		try {
			
			// pega os dados JSON que vierem na requisicao POST e converte para CredenciaisDTO
			CredenciaisDTO creds = new ObjectMapper()
	                					.readValue(req.getInputStream(), CredenciaisDTO.class);
	
			// TOKEN do spring security
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), 
	        																						creds.getSenha(), 
	        																						new ArrayList<>());
	        // verifica se o usuario e senha sao validos, baseados no UserDetailsServices
	        Authentication auth = authenticationManager.authenticate(authToken);
	        
	        // informa ao Spring Security o objeto retornado
	        return auth;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Se a autenticacao der certo
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		
		String username = ((UserSpringSecurity) auth.getPrincipal()).getUsername();
		
		// gera um token
        String token = jwtUtil.generateToken(username);
        
        // gera um cabeçalho no corpo do token com o nome "Authorixzation" e valor "Bearer + :token" 
        res.addHeader("Authorization", "Bearer " + token);	
	}
	
	/**
	 * Tratar os erros
	 * @author Santo
	 *
	 */
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }
        
        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }
}
