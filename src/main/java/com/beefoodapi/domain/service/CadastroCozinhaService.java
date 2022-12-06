package com.beefoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.beefoodapi.domain.exception.EntidadeEmUsoException;
import com.beefoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.beefoodapi.domain.model.Cozinha;
import com.beefoodapi.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	// injetando o repository
	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.adicionar(cozinha);
	}

	// removendo uma cozinha e tratando o exception na classe de servico que e
	// negocio
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.remover(cozinhaId);
		
			//tratando duas exceptions
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));

		} catch (DataIntegrityViolationException ex) {
			// lancando uma nova exception
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId));
		}
	}
}
