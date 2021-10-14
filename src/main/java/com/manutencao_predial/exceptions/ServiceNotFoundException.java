package com.manutencao_predial.exceptions;

public class ServiceNotFoundException extends NotFoundException {
    /**
	 * Construtor para especificar a mensagem
	 * @param msg detalhes da mensagem.
	 */
	public ServiceNotFoundException(String msg) {
		super(msg);
	}
    /**
	 * Construtor para especificar a mensagem causa da origem.
	 * @param msg detalhes da mensagem.
	 * @param cause causa da origem do erro
	 */
	public ServiceNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
