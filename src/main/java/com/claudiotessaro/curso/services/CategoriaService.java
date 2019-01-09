package com.claudiotessaro.curso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.repository.CategoriaRepository;

@Service // Anotacao responsavel por informar ao spring que a classe faz parte da camada de servico
public class CategoriaService {

	@Autowired // Anotacao responsavel por instanciar automaticamente pelo spring
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {

		Categoria obj = repo.findOne(id);

		return obj;

	}

}
