package com.claudiotessaro.curso;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.claudiotessaro.curso.domain.Categoria;
import com.claudiotessaro.curso.domain.Cidade;
import com.claudiotessaro.curso.domain.Cliente;
import com.claudiotessaro.curso.domain.Endereco;
import com.claudiotessaro.curso.domain.Estado;
import com.claudiotessaro.curso.domain.ItemPedido;
import com.claudiotessaro.curso.domain.Pagamento;
import com.claudiotessaro.curso.domain.PagamentoComBoleto;
import com.claudiotessaro.curso.domain.PagamentoComCartao;
import com.claudiotessaro.curso.domain.Pedido;
import com.claudiotessaro.curso.domain.Produto;
import com.claudiotessaro.curso.domain.enums.EstadoPagamento;
import com.claudiotessaro.curso.domain.enums.TipoCliente;
import com.claudiotessaro.curso.repository.CategoriaRepository;
import com.claudiotessaro.curso.repository.CidadeRepository;
import com.claudiotessaro.curso.repository.ClienteRepository;
import com.claudiotessaro.curso.repository.EnderecoRepository;
import com.claudiotessaro.curso.repository.EstadoRepository;
import com.claudiotessaro.curso.repository.ItemPedidoRepository;
import com.claudiotessaro.curso.repository.PagamentoRepository;
import com.claudiotessaro.curso.repository.PedidoRepository;
import com.claudiotessaro.curso.repository.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Criação das instancias categoria e produto
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000);
		Produto p2 = new Produto(null, "Impressora", 800);
		Produto p3 = new Produto(null, "Mouse", 80);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.save(Arrays.asList(cat1,cat2));
		produtoRepository.save(Arrays.asList(p1,p2,p3));
	
		//Criacao das instancias do estado e cidade
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia",est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		estadoRepository.save(Arrays.asList(est1,est2));
		cidadeRepository.save(Arrays.asList(c1,c2,c3));
		
		//Criacao das instancias cliente e endereco
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("3333333","44444"));
		
		Endereco e1 = new Endereco(null, "Rua flores","300", "Apt 203", "Jardim","382220", cli1,c1);
		Endereco e2 = new Endereco(null, "Avenida Matos","320", "Sala 800", "Centro","382220", cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
	
		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(e1,e2));
		
		//Criacao das instancias pedido e forma de pagamento
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"),cli1,e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO,ped1,6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE,ped2,sdf.parse("20/10/2017 00:00"),null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.save(Arrays.asList(ped1,ped2));
		pagamentoRepository.save(Arrays.asList(pagto1,pagto2));
		
		
		//Criacao das instancias de pedido
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		
		itemPedidoRepository.save(Arrays.asList(ip1,ip2,ip3));
		
	
	}

}

