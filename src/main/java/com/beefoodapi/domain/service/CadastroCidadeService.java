package com.beefoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.beefoodapi.domain.exception.EntidadeEmUsoException;
import com.beefoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.beefoodapi.domain.model.Cidade;
import com.beefoodapi.domain.model.Estado;
import com.beefoodapi.domain.repository.CidadeRepository;
import com.beefoodapi.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.porId(estadoId);

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de estado com código %d", estadoId));
		}

		cidade.setEstado(estado);

		return cidadeRepository.adicionar(cidade);
	}

	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.remover(cidadeId);

			// tratando duas exceptions
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro cidade com o código %d", cidadeId));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
		}
	}
}
