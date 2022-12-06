package com.beefoodapi.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//construtor recebe uma mensagem, que chama o super Runtime passando a mensagem
	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

}
