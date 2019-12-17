package com.projeto.funancial.exception;

/**
 *<code>EncriptadorServiceException</code> trata-se de uma classe de exe��es gen�ricas
 * para tratamento de erros de criptografia e valida��o de dados
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 */
public class EncriptadorServiceException extends Exception{
	private static final long serialVersionUID = 649330966267221004L;

	public EncriptadorServiceException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
}
