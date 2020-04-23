package com.felipe.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.cursomc.domain.ItemPedido;
import com.felipe.cursomc.domain.PagamentoComBoleto;
import com.felipe.cursomc.domain.Pedido;
import com.felipe.cursomc.domain.enums.EstadoPagamento;
import com.felipe.cursomc.repositories.ItemPedidoRepository;
import com.felipe.cursomc.repositories.PagamentoRepository;
import com.felipe.cursomc.repositories.PedidoRepository;
import com.felipe.cursomc.servvices.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado id: " + id + " , Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagementoComBoleto(pagto, obj.getInstante());
		}
		obj = repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
