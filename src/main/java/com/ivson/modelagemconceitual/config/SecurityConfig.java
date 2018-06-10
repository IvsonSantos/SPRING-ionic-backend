package com.ivson.modelagemconceitual.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ivson.modelagemconceitual.security.JWTAuthenticationFilter;
import com.ivson.modelagemconceitual.security.JWTAuthorizationFilter;
import com.ivson.modelagemconceitual.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	/**
	 * Injetamos a interface, o spring vai achar a UserDetailsServiceImpl automaticamente
	 */
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"	// tudo daqui ta liberado
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**"
			//"/clientes/**"
	};
	
	/**
	 * Para permitir  o usuario GUEST se cadastrar no sistema
	 */
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/clientes/**"
	};
	
	/**
	 * Sobrescrever o metodod do WebSecurity
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Se no profile ativo tiver o profile "test", libera o H2 (que controla sessao) 
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		// se tiver um CorsConfiguration definido, ele é chamado aqui
		http.cors()
			.and().csrf().disable();	// para desabilitar ataques de sessao CSRF
		
		http.authorizeRequests()			// autorize	requisições
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll() // so permite o POST que estiverem neste endpint
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // so permite o GET que estiverem neste endpint
			.antMatchers(PUBLIC_MATCHERS).permitAll()	// para todos os endpoints deste vetor permitir fazer qualquer coisa									 
			.anyRequest().authenticated();				// para qualquer outra requisição exige autenticação
		
		// registra os filtros criados
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));		
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		
		// segurança, para não armazenar estado
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/**
	 * Configura o mecanismo de autenticacao, quem é o service e o encoder
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
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