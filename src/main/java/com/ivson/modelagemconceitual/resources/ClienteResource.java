package com.ivson.modelagemconceitual.resources;

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

import com.ivson.modelagemconceitual.dto.ClienteDTO;
import com.ivson.modelagemconceitual.model.Cliente;
import com.ivson.modelagemconceitual.services.ClienteService;

/**
 * PACKAGE resource = nome padrao para os recursos em uma API
 * @author Santo
 *
 */
@RestController
@RequestMapping(value="/clientes")	// nome do Endpoint REST, por padrao de mercado no plural
public class ClienteResource {

	@Autowired
	private ClienteService service;	
	
	/**
	 * OP spring Boot ja tem um conversor automatico que transforma qqr objeto em um JSON
	 * RESPOSNSE ENTITY = ja traz um encapsulamento de uma resposta do tipo REST
	 * @return
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = service.find(id); 		
		return ResponseEntity.ok().body(obj);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
		
		Cliente cliente = service.fromDTO(clienteDTO);
		cliente.setId(id);
		cliente = service.update(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {		

		List<Cliente> list = service.findAll();
		
		List<ClienteDTO> listDTO = list.stream().map
				(obg -> new ClienteDTO(obg)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findpage(
			@RequestParam(value="page", defaultValue="0") Integer page, // opcional, se nao informar, vai pra primeira pagina (0)
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {		

		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);		
		Page<ClienteDTO> listDTO = list.map(obg -> new ClienteDTO(obg));		
		return ResponseEntity.ok().body(listDTO);
	}
}
