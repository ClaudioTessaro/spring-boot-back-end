package com.claudiotessaro.curso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claudiotessaro.curso.domain.Cliente;
import com.claudiotessaro.curso.repository.ClienteRepository;
import com.claudiotessaro.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired // Anotacao responsavel por instanciar automaticamente pelo spring
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null){
			throw new ObjectNotFoundException("Objeto não não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName());
		}
		return obj;

	}

}
