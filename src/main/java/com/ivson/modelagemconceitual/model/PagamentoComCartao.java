package com.ivson.modelagemconceitual.model;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.ivson.modelagemconceitual.model.enuns.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {

	/**
	 * Versao numero 1 dessa classe
	 */
	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;
	
	public PagamentoComCartao() {

	}

	public PagamentoComCartao(Integer id, EstadoPagamento estadoPagamento, Pedido pedido,Integer numeroDeParcelas) {
		super(id, estadoPagamento, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
}
