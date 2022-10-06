package com.beefoodapi.domain.repository;

import java.util.List;

import com.beefoodapi.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> todos();
	Cidade porId(Long id);
	Cidade adicionar(Cidade cidade);
	void remover(Cidade cidade);
}
