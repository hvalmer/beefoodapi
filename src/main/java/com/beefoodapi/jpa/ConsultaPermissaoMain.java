package com.beefoodapi.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.beefoodapi.BeefoodApiApplication;
import com.beefoodapi.domain.model.Permissao;
import com.beefoodapi.domain.repository.PermissaoRepository;

public class ConsultaPermissaoMain {
 
	public static void main(String[] args) {
		//inicia a aplicacao
		ApplicationContext applicationContext = new SpringApplicationBuilder(BeefoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		//o applicationContext retorna o CadastroPermissao
		PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);
		
		//listando as Permissaos
		List<Permissao> todasPermissoes = permissaoRepository.todos();
		
		//iterando a lista de Permissaos
		for(Permissao permissao : todasPermissoes) {
			//imprimindo o nome da Permissao
			System.out.printf("%s - %s\n", permissao.getNome(), permissao.getDescricao());
		}
	}
}
