package com.beefoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beefoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.beefoodapi.domain.model.Cozinha;
import com.beefoodapi.domain.model.Restaurante;
import com.beefoodapi.domain.repository.CozinhaRepository;
import com.beefoodapi.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	//fazendo uma consulta de cozinha com seu repositorio(Repository)
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	//metodo para adicionar um novo restaurante
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		//fazendo uma consulta de cozinha e ver se existe
		Cozinha cozinha = cozinhaRepository.porId(cozinhaId);
		
		//verificando a ...
		if(cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
		}
		
		//chegando aqui é pq tem cozinha, atribuindo ao restaurante
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.adicionar(restaurante);
	}
}
