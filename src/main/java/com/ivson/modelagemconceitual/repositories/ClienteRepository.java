package com.ivson.modelagemconceitual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ivson.modelagemconceitual.model.Cliente;

@Repository // nao é necessario
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	// fica mais rapida a pesquisa
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
	
}