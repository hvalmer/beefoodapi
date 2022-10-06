package com.beefoodapi.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.beefoodapi.BeefoodApiApplication;
import com.beefoodapi.domain.model.Restaurante;
import com.beefoodapi.domain.repository.RestauranteRepository;

public class ConsultaRestauranteMain {
 
	public static void main(String[] args) {
		//inicia a aplicacao
		ApplicationContext applicationContext = new SpringApplicationBuilder(BeefoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		//o applicationContext retorna o CadastroRestaurante
		RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);
		
		//listando os restaurantes
		List<Restaurante> todosRestaurantes = restaurantes.todos();
		
		//iterando a lista de restaurantes
		for(Restaurante restaurante : todosRestaurantes) {
			//imprimindo o nome do restaurante
			System.out.printf("%s - %f - %s\n", restaurante.getNome(), 
					restaurante.getTaxaFrete(), restaurante.getCozinha().getNome());
		}
	}
}
