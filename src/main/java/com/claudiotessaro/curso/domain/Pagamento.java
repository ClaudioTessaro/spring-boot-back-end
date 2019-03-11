package com.claudiotessaro.curso.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.claudiotessaro.curso.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@Entity
@Inheritance(strategy=InheritanceType.JOINED) //Essa anotação serve para criar o mapeamento de herança. O tipo joined é a criação de uma tabela com todas as informações, e o single serve para criação da quantidade de tabelas iguais a quantidade de classes filho
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type") //minha classe terá um campo adicional com a palavra type
public abstract class Pagamento  implements Serializable{ //Criação da classe do tipo abstract, para certificar que eu nao poderei instanciar o pagamento e sim os tipos de pagamentos

	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private Integer estado;
	
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId  //Essa anotação serve para garantir que o id do pagamento será o msm do pedido
	//@JsonBackReference
	@JsonIgnore
	private Pedido pedido;
	
	public Pagamento() {
		
	}

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		super();
		this.id = id;
		this.estado = (estado == null) ? null: estado.getCod();
		this.pedido = pedido;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}


	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}


	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
