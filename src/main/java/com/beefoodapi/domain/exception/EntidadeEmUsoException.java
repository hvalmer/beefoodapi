package com.beefoodapi.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//construtor recebe uma mensagem, que chama o super Runtime passando a mensagem
	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}

}
