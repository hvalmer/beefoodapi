package com.beefoodapi.domain.repository;

import java.util.List;

import com.beefoodapi.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> todos();
	Permissao porId(Long id);
	Permissao adicionar(Permissao permissao);
	void remover(Permissao permissao);
}
