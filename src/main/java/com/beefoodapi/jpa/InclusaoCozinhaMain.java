package com.beefoodapi.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.beefoodapi.BeefoodApiApplication;
import com.beefoodapi.domain.model.Cozinha;
import com.beefoodapi.domain.repository.CozinhaRepository;

public class InclusaoCozinhaMain {
 
	public static void main(String[] args) {
		//inicia a aplicacao
		ApplicationContext applicationContext = new SpringApplicationBuilder(BeefoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		//o applicationContext retorna o CadastroCozinha
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		//instanciando algumas cozinhas
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		
		//incluindo as novas cozinhas no contexto de persistencia
		//cozinha1&2 retorna o metodo() e ñ a instancia new cozinha1&2
 		cozinha1 = cozinhaRepository.adicionar(cozinha1);
		cozinha2 = cozinhaRepository.adicionar(cozinha2);
		
		//imprimindo...
		System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
		System.out.printf("%d - %s\n", cozinha2.getId(), cozinha2.getNome());
	}
}
