package com.projeto.funancial.exception;

/**
 *<code>AuthenticationServiceException</code> trata-se de uma classe de exeções genéricas
 * para tratamento de erros de autenticação na aplicação
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 */
public class AuthenticationServiceException extends Exception{
	private static final long serialVersionUID = -1896950646383403961L;
	
	public AuthenticationServiceException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
}
