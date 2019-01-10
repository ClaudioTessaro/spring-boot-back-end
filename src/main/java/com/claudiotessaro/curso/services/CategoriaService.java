package com.claudiotessaro.curso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.repository.CategoriaRepository;
import com.claudiotessaro.curso.services.exceptions.ObjectNotFoundException;

@Service // Anotacao responsavel por informar ao spring que a classe faz parte da camada de servico
public class CategoriaService {

	@Autowired // Anotacao responsavel por instanciar automaticamente pelo spring
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {
		Categoria obj = repo.findOne(id);
		if(obj == null){
			throw new ObjectNotFoundException("Objeto não não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName());
		}
		return obj;

	}


}
