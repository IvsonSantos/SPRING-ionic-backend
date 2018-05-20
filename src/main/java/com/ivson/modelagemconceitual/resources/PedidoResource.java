package com.ivson.modelagemconceitual.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ivson.modelagemconceitual.model.Pedido;
import com.ivson.modelagemconceitual.services.PedidoService;

/**
 * PACKAGE resource = nome padrao para os recursos em uma API
 * @author Santo
 *
 */
@RestController
@RequestMapping(value="/pedidos")	// nome do Endpoint REST, por padrao de mercado no plural
public class PedidoResource {

	@Autowired
	private PedidoService service;	
	
	/**
	 * RESPOSNSE ENTITY = ja traz um encapsulamento de uma resposta do tipo REST
	 * @return
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
		Pedido obj = service.find(id); 		
		return ResponseEntity.ok().body(obj);
	}
	
	
	//saveAll
}
