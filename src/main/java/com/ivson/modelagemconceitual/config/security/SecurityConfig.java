package com.ivson.modelagemconceitual.config.security;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"	// tudo daqui ta liberado
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**"
	};
	
	/**
	 * Sobrescrever o metodod do WebSecurity
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		// Se no profile ativo tiver o profile "test", libera o H2 (que controla sessao) 
//		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
//			// libera o acesso
//			http.headers().frameOptions().disable();
//		}
			
		
		
		// se tiver um CorsConfiguration definido, ele é chamado aqui
		http.cors()
			.and().csrf().disable();	// para desabilitar ataques de sessao CSRF
		
		http.authorizeRequests()			// autorize	requisições
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // so permite o GET que estiverem neste endpint
			.antMatchers(PUBLIC_MATCHERS).permitAll()	// para todos os endpoints deste vetor permitir fazer qualquer coisa									 
			.anyRequest().authenticated();				// para qualquer outra requisição exige autenticação
		
		// segurança, para não armazenar estado
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		//permitindo o acesso aos endpoints para configurações basicas
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {		
		return new BCryptPasswordEncoder();
	}

}