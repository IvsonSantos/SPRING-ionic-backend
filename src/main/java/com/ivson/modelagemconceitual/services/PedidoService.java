package com.ivson.modelagemconceitual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivson.modelagemconceitual.model.Pedido;
import com.ivson.modelagemconceitual.repositories.PedidoRepository;
import com.ivson.modelagemconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Optional<Pedido> Pedido = repo.findById(id);
		return Pedido.orElseThrow(() -> 
				new ObjectNotFoundException("Objeto nao encontrado: " + Pedido.class.getName()));
	}
}
