package com.claudiotessaro.curso.dto;

import java.io.Serializable;

import com.claudiotessaro.curso.domain.Categoria;

//Essa classe servirá para criação de objetos responsaveis por recuperar os dados requeridos, ou seja, somente os dados que eu quero recuperar
public class CategoriaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	

}
