package com.manutencao_predial.exceptions;

public abstract class NotFoundException extends RuntimeException {
    /**
	 * Construtor para especificar a mensagem
	 * @param msg detalhes da mensagem.
	 */
	public NotFoundException(String msg) {
		super(msg);
	}
    /**
	 * Construtor para especificar a mensagem causa da origem.
	 * @param msg detalhes da mensagem.
	 * @param cause causa da origem do erro
	 */
	public NotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
