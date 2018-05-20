package com.ivson.modelagemconceitual.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ivson.modelagemconceitual.model.Categoria;
import com.ivson.modelagemconceitual.services.CategoriaService;

/**
 * PACKAGE resource = nome padrao para os recursos em uma API
 * @author Santo
 *
 */
@RestController
@RequestMapping(value="/categorias")	// nome do Endpoint REST, por padrao de mercado no plural
public class CategoriaResource {

	@Autowired
	private CategoriaService service;	
	
	/**
	 * OP spring Boot ja tem um conversor automatico que transforma qqr objeto em um JSON
	 * RESPOSNSE ENTITY = ja traz um encapsulamento de uma resposta do tipo REST
	 * @return
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {		
		Categoria obj = service.find(id); 		
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria categoria) {
		
		// http status code (pesquisar no google)
		categoria = service.insert(categoria);	
		
		// para chamar o endereco URI do objeto que foi criado, padrao do frame
		URI uri = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(categoria.getId())	// o codigo dacima
						.toUri();	// converte para URI
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria categoria, @PathVariable Integer id) {
		categoria.setId(id);
		categoria = service.update(categoria);
		return ResponseEntity.noContent().build();
	}
	
}