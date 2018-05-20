package com.ivson.modelagemconceitual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivson.modelagemconceitual.model.Cliente;
import com.ivson.modelagemconceitual.repositories.ClienteRepository;
import com.ivson.modelagemconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> 
				new ObjectNotFoundException("Objeto nao encontrado: " + Cliente.class.getName()));
	}
}
