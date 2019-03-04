package com.claudiotessaro.curso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.domain.Produto;
import com.claudiotessaro.curso.repository.CategoriaRepository;
import com.claudiotessaro.curso.repository.ProdutoRepository;
import com.claudiotessaro.curso.services.exceptions.ObjectNotFoundException;

@Service // Anotacao responsavel por informar ao spring que a classe faz parte da camada de servico
public class ProdutoService {

	@Autowired // Anotacao responsavel por instanciar automaticamente pelo spring
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto buscar(Integer id) {
		Produto obj = repo.findOne(id);
		if(obj == null){
			throw new ObjectNotFoundException("Objeto não não encontrado! Id: "+id+", Tipo: "+Produto.class.getName());
		}
		return obj;

	}
	
	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids);
		return repo.search(nome,categorias,pageRequest);
		
	}


}
