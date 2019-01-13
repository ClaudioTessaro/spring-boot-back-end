package com.claudiotessaro.curso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.repository.CategoriaRepository;
import com.claudiotessaro.curso.services.exceptions.DataIntegrityService;
import com.claudiotessaro.curso.services.exceptions.ObjectNotFoundException;

@Service // Anotacao responsavel por informar ao spring que a classe faz parte da camada de servico
public class CategoriaService {

	@Autowired // Anotacao responsavel por instanciar automaticamente pelo spring
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if(obj == null){
			throw new ObjectNotFoundException("Objeto não não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName());
		}
		return obj;

	}
	
	//Criação do metodo de inserção
	public Categoria insert(Categoria obj) {
		obj.setId(null); //Serve para confirmar que é uma inserção de um objeto novo
		return repo.save(obj);
	}
	
	//Criacao do metodo de atualização
	public Categoria update(Categoria obj) {
		
		//Serve para informar a existencia de um id, pois a atualização so é possivel com a existencia de um id
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id); //Para caso o id nao exista, ele lançar um exception
		try {
		repo.delete(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityService("Não é possivel excluir uma categoria que possui produtos");
		}
		
		
	}

	public List<Categoria> findAll() {
		return repo.findAll();
		
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

}
