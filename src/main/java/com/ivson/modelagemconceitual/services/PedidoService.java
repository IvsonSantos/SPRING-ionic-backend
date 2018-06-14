package com.ivson.modelagemconceitual.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivson.modelagemconceitual.model.Cliente;
import com.ivson.modelagemconceitual.model.ItemPedido;
import com.ivson.modelagemconceitual.model.PagamentoComBoleto;
import com.ivson.modelagemconceitual.model.Pedido;
import com.ivson.modelagemconceitual.model.enuns.EstadoPagamento;
import com.ivson.modelagemconceitual.repositories.ItemPedidoRepository;
import com.ivson.modelagemconceitual.repositories.PagamentoRepository;
import com.ivson.modelagemconceitual.repositories.PedidoRepository;
import com.ivson.modelagemconceitual.security.UserSpringSecurity;
import com.ivson.modelagemconceitual.services.exceptions.AuthorizationException;
import com.ivson.modelagemconceitual.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService; 
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> Pedido = repo.findById(id);
		return Pedido.orElseThrow(() -> 
				new ObjectNotFoundException("Objeto nao encontrado: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		
		// pedido novo ainda ta com o pagamento pendente
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstant());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());	//pega o preço
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		UserSpringSecurity user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");			
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
		
}