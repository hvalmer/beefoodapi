package com.beefoodapi.domain.repository;

import java.util.List;

import com.beefoodapi.domain.model.Estado;

public interface EstadoRepository {

	//construindo o negocio em vez da implementacao
	List<Estado> todos();
	Estado porId(Long id);
	Estado adicionar(Estado estado);
	void remover(Long id);
}
