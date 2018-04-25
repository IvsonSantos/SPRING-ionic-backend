package com.ivson.modelagemconceitual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivson.modelagemconceitual.model.Categoria;
import com.ivson.modelagemconceitual.repositories.CategoriaRepository;
import com.ivson.modelagemconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> 
				new ObjectNotFoundException("Objeto nao encontrado: " + Categoria.class.getName()));
	}
}