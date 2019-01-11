package com.claudiotessaro.curso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claudiotessaro.curso.domain.Pedido;
import com.claudiotessaro.curso.repository.PedidoRepository;
import com.claudiotessaro.curso.services.exceptions.ObjectNotFoundException;

@Service // Anotacao responsavel por informar ao spring que a classe faz parte da camada de servico
public class PedidoService {

	@Autowired // Anotacao responsavel por instanciar automaticamente pelo spring
	private PedidoRepository repo;

	public Pedido buscar(Integer id) {
		Pedido obj = repo.findOne(id);
		if(obj == null){
			throw new ObjectNotFoundException("Objeto não não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName());
		}
		return obj;

	}


}
