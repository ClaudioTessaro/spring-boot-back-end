package com.claudiotessaro.curso.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claudiotessaro.curso.domain.ItemPedido;
import com.claudiotessaro.curso.domain.PagamentoComBoleto;
import com.claudiotessaro.curso.domain.Pedido;
import com.claudiotessaro.curso.domain.enums.EstadoPagamento;
import com.claudiotessaro.curso.repository.ItemPedidoRepository;
import com.claudiotessaro.curso.repository.PagamentoRepository;
import com.claudiotessaro.curso.repository.PedidoRepository;
import com.claudiotessaro.curso.repository.ProdutoRepository;
import com.claudiotessaro.curso.services.exceptions.ObjectNotFoundException;

@Service // Anotacao responsavel por informar ao spring que a classe faz parte da camada de servico
public class PedidoService {

	@Autowired // Anotacao responsavel por instanciar automaticamente pelo spring
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido buscar(Integer id) {
		Pedido obj = repo.findOne(id);
		if(obj == null){
			throw new ObjectNotFoundException("Objeto não não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName());
		}
		return obj;

	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto,obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip :obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepository.findOne(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		return obj;
	}


}
