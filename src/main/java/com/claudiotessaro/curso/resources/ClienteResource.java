package com.claudiotessaro.curso.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.domain.Cliente;
import com.claudiotessaro.curso.dto.ClienteDTO;
import com.claudiotessaro.curso.dto.ClienteNewDTO;
import com.claudiotessaro.curso.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")

public class ClienteResource {
	
	@Autowired
	private ClienteService service; //O controlador rest esta acessando o serviço, e o serviço por sua avez vai acessar o objeto de acesso ao repository
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET) //O argumento value serve para informar que o end point do metodo é o id;
	public ResponseEntity<?> find(@PathVariable Integer id) { //PathVarible serve para informar ao metodo que o argumento é o mesmo do endpoint. O objeto do tipo reponseentity tem toda as informações do protocolo Http 
		
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
				
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) { // O requestBody serve para ele receber o objeto
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}

	@RequestMapping(method = RequestMethod.GET) 
	public ResponseEntity<List<ClienteDTO>> findAll() { 
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList()); //Aqui convertemos uma lista em uma outra lista
		return ResponseEntity.ok().body(listDto);

	}

	@RequestMapping(value="/page", method = RequestMethod.GET) 
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page",defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			@RequestParam(value="direction",defaultValue="ASC") String direction) { 
		Page<Cliente> list = service.findPage(page,linesPerPage,orderBy,direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj)); //Aqui convertemos uma lista em uma outra lista
		return ResponseEntity.ok().body(listDto);

	}

	

}
