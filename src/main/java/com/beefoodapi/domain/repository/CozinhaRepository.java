package com.beefoodapi.domain.repository;

import java.util.List;

import com.beefoodapi.domain.model.Cozinha;

public interface CozinhaRepository {

	//ñ pensando em implementacao e sim negocio
	List<Cozinha> todas();
	Cozinha porId(Long id);
	Cozinha adicionar(Cozinha cozinha);
	void remover(Cozinha cozinha);
}
