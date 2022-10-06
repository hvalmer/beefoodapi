package com.beefoodapi.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.beefoodapi.BeefoodApiApplication;
import com.beefoodapi.domain.model.FormaPagamento;
import com.beefoodapi.domain.repository.FormaPagamentoRepository;

public class ConsultaFormaPagamentoMain {
 
	public static void main(String[] args) {
		//inicia a aplicacao
		ApplicationContext applicationContext = new SpringApplicationBuilder(BeefoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		//o applicationContext retorna o CadastroFormaPagamento
		FormaPagamentoRepository formaPagamentos = applicationContext.getBean(FormaPagamentoRepository.class);
		
		//listando as FormaPagamentos
		List<FormaPagamento> todasFormasPagamentos = formaPagamentos.todos();
		
		//iterando a lista de FormaPagamentos
		for(FormaPagamento formaPagamento : todasFormasPagamentos) {
			//imprimindo o nome da FormaPagamento
			System.out.println(formaPagamento.getDescricao());
		}
	}
}
