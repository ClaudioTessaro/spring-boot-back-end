package com.claudiotessaro.curso.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")

public class CategoriaResource {
	
	@Autowired
	private CategoriaService service; //O controlador rest esta acessando o serviço, e o serviço por sua avez vai acessar o objeto de acesso ao repository
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //O argumento value serve para informar que o end point do metodo é o id;
	public ResponseEntity<?> find(@PathVariable Integer id) { //PathVarible serve para informar ao metodo que o argumento é o mesmo do endpoint. O objeto do tipo reponseentity tem toda as informações do protocolo Http 
		
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
				
	}
	
	//Quando se inserve um codigo http, ele tem uma resposta particular para essa operação.
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){ //O requestBody serve para ele receber o objeto
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	

}
