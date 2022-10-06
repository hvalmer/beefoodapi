package com.beefoodapi.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.beefoodapi.BeefoodApiApplication;
import com.beefoodapi.domain.model.Cozinha;
import com.beefoodapi.domain.repository.CozinhaRepository;

public class ExclusaoCozinhaMain {
 
	public static void main(String[] args) {
		//inicia a aplicacao
		ApplicationContext applicationContext = new SpringApplicationBuilder(BeefoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		//o applicationContext retorna o CadastroCozinha
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		//alterando o nome da cozinha e atualizando na tabela
		/*adicionando uma nova cozinha, sendo que já existe uma cozinha
		 * (Tailandesa) no BD, subistituindo por (Brasileira)...
		 * lembrando que o método adicionar chama o merge da classe CadastroCozinha*/
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		
		cozinhaRepository.remover(cozinha);
		
		
	}
}
