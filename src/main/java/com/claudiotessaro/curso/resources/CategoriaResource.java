package com.claudiotessaro.curso.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")

public class CategoriaResource {
	
	@Autowired
	private CategoriaService service; //O controlador rest esta acessando o serviço, e o serviço por sua avez vai acessar o objeto de acesso ao repository
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //O argumento value serve para informar que o end point do metodo é o id;
	public ResponseEntity<?> find(@PathVariable Integer id) { //PathVarible serve para informar ao metodo que o argumento é o mesmo do endpoint. O objeto do tipo reponseentity tem toda as informações do protocolo Http 
		
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
				
	}
	

}
