package com.manutencao_predial.exceptions;

public class UserNotFoundException extends NotFoundException {
    /**
	 * Construtor para especificar a mensagem
	 * @param msg detalhes da mensagem.
	 */
	public UserNotFoundException(String msg) {
		super(msg);
	}
    /**
	 * Construtor para especificar a mensagem causa da origem.
	 * @param msg detalhes da mensagem.
	 * @param cause causa da origem do erro
	 */
	public UserNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
