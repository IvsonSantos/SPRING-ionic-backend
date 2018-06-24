package com.ivson.modelagemconceitual.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ivson.modelagemconceitual.services.DBService;
import com.ivson.modelagemconceitual.services.EmailService;
import com.ivson.modelagemconceitual.services.MockEmailService;

/**
 * Todos os Beans desta classe so estarao ativos quando for ativo o profile de TEST
 * @author Santo
 *
 */
@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		dbService.instantiateTestDatabase();
		return true;
	}
	
	// este comoonente vai ser usado por outras classes que precisam, automaticamente
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
}
