package com.claudiotessaro.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claudiotessaro.curso.domain.Estado;

@Repository //Anotacao responsavel por informar ao spring que essa interface faz parte da camada de persistencia, ou a repository.
public interface EstadoRepository  extends JpaRepository<Estado, Integer>{

}
