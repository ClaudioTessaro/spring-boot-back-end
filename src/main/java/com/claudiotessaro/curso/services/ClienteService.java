package com.claudiotessaro.curso.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.domain.Cidade;
import com.claudiotessaro.curso.domain.Cliente;
import com.claudiotessaro.curso.domain.Endereco;
import com.claudiotessaro.curso.domain.enums.TipoCliente;
import com.claudiotessaro.curso.domain.Cliente;
import com.claudiotessaro.curso.dto.ClienteDTO;
import com.claudiotessaro.curso.dto.ClienteNewDTO;
import com.claudiotessaro.curso.repository.ClienteRepository;
import com.claudiotessaro.curso.repository.EnderecoRepository;
import com.claudiotessaro.curso.services.exceptions.DataIntegrityService;
import com.claudiotessaro.curso.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired // Anotacao responsavel por instanciar automaticamente pelo spring
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder be;
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null){
			throw new ObjectNotFoundException("Objeto não não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName());
		}
		return obj;

	}
	
	//Criacao do metodo de atualização
		public Cliente update(Cliente obj) {
			
			//Serve para informar a existencia de um id, pois a atualização so é possivel com a existencia de um id
			Cliente newObj = find(obj.getId());
			updateData(newObj, obj);
			return repo.save(newObj);
		}

		private void updateData(Cliente newObj, Cliente obj) {
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
			
		}

		public void delete(Integer id) {
			find(id); //Para caso o id nao exista, ele lançar um exception
			try {
			repo.delete(id);
			}catch(DataIntegrityViolationException e) {
				throw new DataIntegrityService("Não é possivel excluir porque existem entidades relacionadas");
			}
			
			
		}

		public List<Cliente> findAll() {
			return repo.findAll();
			
		}
		
		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
			return repo.findAll(pageRequest);
		}
		
		public Cliente fromDTO(ClienteDTO objDto) {
			return new Cliente(objDto.getId(), objDto.getNome(),objDto.getEmail(), null, null,null);
		}
		
		@Transactional
		public Cliente insert(Cliente obj) {
			obj.setId(null); //Serve para confirmar que é uma inserção de um objeto novo
			repo.save(obj);
			enderecoRepository.save(obj.getEnderecos());
			return obj;
		}

		
		public Cliente fromDTO(ClienteNewDTO objDto) {
			Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()),be.encode(objDto.getSenha()));
			Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
			Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemente(), objDto.getBairro(), objDto.getCep(), cli,cid);
			
			cli.getEnderecos().add(end);
			cli.getTelefones().add(objDto.getTelefone1());
			if(objDto.getTelefone2() !=null) {
				cli.getTelefones().add(objDto.getTelefone2());
			}
			if(objDto.getTelefone3() !=null) {
				cli.getTelefones().add(objDto.getTelefone3());
			}
			return cli;
		}
		
	

}
