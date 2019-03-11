package com.claudiotessaro.curso.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.claudiotessaro.curso.domain.Pedido;
import com.claudiotessaro.curso.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")

public class PedidoResource {

	@Autowired
	private PedidoService service; // O controlador rest esta acessando o serviço, e o serviço por sua avez vai
									// acessar o objeto de acesso ao repository

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // O argumento value serve para informar que o end
																	// point do metodo é o id;
	public ResponseEntity<?> find(@PathVariable Integer id) { // PathVarible serve para informar ao metodo que o
																// argumento é o mesmo do endpoint. O objeto do tipo
																// reponseentity tem toda as informações do protocolo
																// Http

		Pedido obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) { // O requestBody serve para ele
																			// receber o objeto
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
