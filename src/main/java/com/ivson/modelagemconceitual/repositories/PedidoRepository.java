package com.ivson.modelagemconceitual.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivson.modelagemconceitual.model.Cliente;
import com.ivson.modelagemconceitual.model.Pedido;

@Repository // nao é necessario
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
}
