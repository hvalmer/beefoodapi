package com.beefoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.beefoodapi.domain.exception.EntidadeEmUsoException;
import com.beefoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.beefoodapi.domain.model.Estado;
import com.beefoodapi.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado estado) {
		return estadoRepository.adicionar(estado);
	}

	public void excluir(Long estadoId) {
		try {
			estadoRepository.remover(estadoId);

			// tratando duas exceptions
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de estado com código %d", estadoId));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removido, pois está em uso", estadoId));
		}
	}
}
