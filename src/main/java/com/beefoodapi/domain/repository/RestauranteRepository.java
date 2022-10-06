package com.beefoodapi.domain.repository;

import java.util.List;

import com.beefoodapi.domain.model.Restaurante;

public interface RestauranteRepository {

	//construindo o negocio em vez da implementacao
	List<Restaurante> todos();
	Restaurante porId(Long id);
	Restaurante adicionar(Restaurante restaurante);
	void remover(Restaurante restaurante);
	
}
