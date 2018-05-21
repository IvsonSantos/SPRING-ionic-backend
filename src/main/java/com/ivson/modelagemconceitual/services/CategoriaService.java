package com.ivson.modelagemconceitual.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ivson.modelagemconceitual.dto.CategoriaDTO;
import com.ivson.modelagemconceitual.model.Categoria;
import com.ivson.modelagemconceitual.repositories.CategoriaRepository;
import com.ivson.modelagemconceitual.services.exceptions.DataIntegrityException;
import com.ivson.modelagemconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> 
				new ObjectNotFoundException("Objeto nao encontrado: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria categoria) {
		// colocar o ID como nulo para garantir que vai ser salvo e nao update
		categoria.setId(null);
		return repo.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {		
		find(categoria.getId());		
		return repo.save(categoria);
	}
	
	public void delete(Integer id) {
		find(id);
		
		try {		
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possuir produtos");
		}
	}
	
	public List<Categoria> findAll() {
		return repo.findAll();		
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);		
	}
	
	public Categoria fromDTO(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome());
	}
}
