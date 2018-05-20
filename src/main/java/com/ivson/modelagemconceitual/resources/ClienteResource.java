package com.ivson.modelagemconceitual.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Cliente obj = service.find(id); 		
		return ResponseEntity.ok().body(obj);
	}
	
	
	//saveAll
}
