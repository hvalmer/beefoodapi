package com.beefoodapi.domain.repository;

import java.util.List;

import com.beefoodapi.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	List<FormaPagamento> todos();
	FormaPagamento porId(Long id);
	FormaPagamento adicionar(FormaPagamento formaPagamento);
	void remover(FormaPagamento formaPagamento);
	
}
