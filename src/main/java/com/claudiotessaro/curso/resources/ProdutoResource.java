package com.claudiotessaro.curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.domain.Produto;
import com.claudiotessaro.curso.dto.CategoriaDTO;
import com.claudiotessaro.curso.dto.ProdutoDTO;
import com.claudiotessaro.curso.resources.utils.URL;
import com.claudiotessaro.curso.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")

public class ProdutoResource {
	
	@Autowired
	private ProdutoService service; //O controlador rest esta acessando o serviço, e o serviço por sua avez vai acessar o objeto de acesso ao repository
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //O argumento value serve para informar que o end point do metodo é o id;
	public ResponseEntity<?> find(@PathVariable Integer id) { //PathVarible serve para informar ao metodo que o argumento é o mesmo do endpoint. O objeto do tipo reponseentity tem toda as informações do protocolo Http 
		
		Produto obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
				
	}
	
	@RequestMapping(method = RequestMethod.GET) 
	public ResponseEntity< Page<ProdutoDTO> > findPage(
			@RequestParam(value="nome",defaultValue="0") String nome,
			@RequestParam(value="categorias",defaultValue="0") String categorias, 
			@RequestParam(value="page",defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			@RequestParam(value="direction",defaultValue="ASC") String direction) { 
		Page<Produto> list = service.search(URL.decodeParam(nome), URL.decodeIntList(categorias), page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj)); //Aqui convertemos uma lista em uma outra lista
		return ResponseEntity.ok().body(listDto);

	}
	

}
