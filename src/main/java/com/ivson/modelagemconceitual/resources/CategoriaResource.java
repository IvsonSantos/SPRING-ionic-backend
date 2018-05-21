package com.ivson.modelagemconceitual.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ivson.modelagemconceitual.dto.CategoriaDTO;
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
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
				
		Categoria categoria = service.fromDTO(categoriaDTO);
		
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
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {
		
		Categoria categoria = service.fromDTO(categoriaDTO);
		categoria.setId(id);
		categoria = service.update(categoria);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {		

		List<Categoria> list = service.findAll();
		
		List<CategoriaDTO> listDTO = list.stream().map
				(obg -> new CategoriaDTO(obg)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> findpage(
			@RequestParam(value="page", defaultValue="0") Integer page, // opcional, se nao informar, vai pra primeira pagina (0)
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {		

		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);		
		Page<CategoriaDTO> listDTO = list.map(obg -> new CategoriaDTO(obg));		
		return ResponseEntity.ok().body(listDTO);
	}
	
}