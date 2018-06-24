package com.ivson.modelagemconceitual.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ivson.modelagemconceitual.model.Cliente;
import com.ivson.modelagemconceitual.repositories.ClienteRepository;
import com.ivson.modelagemconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewpasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		
		char[] vet = new char[10];
		for (int i=0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	/**
	 * Gera uma senha de 10 caracteres aleatorios
	 * @return
	 */
	private char randomChar() {

		int opt = rand.nextInt(3);
		
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48); // digito 0 a 9
		} else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);	// 65 e o codigo UNICODE para a primeira letra maiuscula
		} else {	// gera letra minuscula
			return (char) (rand.nextInt(26) + 97);	// 65 e o codigo UNICODE para a primeira letra maiuscula
		}

	}
}
