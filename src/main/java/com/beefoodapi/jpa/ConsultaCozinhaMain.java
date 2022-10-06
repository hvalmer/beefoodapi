package com.beefoodapi.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.beefoodapi.BeefoodApiApplication;
import com.beefoodapi.domain.model.Cozinha;
import com.beefoodapi.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {
 
	public static void main(String[] args) {
		//inicia a aplicacao
		ApplicationContext applicationContext = new SpringApplicationBuilder(BeefoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		//o applicationContext retorna o CadastroCozinha
		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);
		
		//listando as cozinhas
		List<Cozinha> todasCozinhas = cozinhas.todas();
		
		//iterando a lista de cozinhas
		for(Cozinha cozinha : todasCozinhas) {
			//imprimindo o nome da cozinha
			System.out.println(cozinha.getNome());
		}
	}
}
