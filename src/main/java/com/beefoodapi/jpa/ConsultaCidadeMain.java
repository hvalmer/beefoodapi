package com.beefoodapi.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.beefoodapi.BeefoodApiApplication;
import com.beefoodapi.domain.model.Cidade;
import com.beefoodapi.domain.repository.CidadeRepository;

public class ConsultaCidadeMain {
 
	public static void main(String[] args) {
		//inicia a aplicacao
		ApplicationContext applicationContext = new SpringApplicationBuilder(BeefoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		//o applicationContext retorna o Cadastrocidade
		CidadeRepository cidades = applicationContext.getBean(CidadeRepository.class);
		
		//listando as cidades
		List<Cidade> todascidades = cidades.todos();
		
		//iterando a lista de cidades
		for(Cidade cidade : todascidades) {
			//imprimindo o nome da cidade
			System.out.printf("%s - %f", cidade.getNome(), cidade.getEstado().getNome());
		}
	}
}
