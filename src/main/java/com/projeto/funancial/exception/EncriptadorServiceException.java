package com.projeto.funancial.exception;

/**
 *<code>EncriptadorServiceException</code> trata-se de uma classe de exeções genéricas
 * para tratamento de erros de criptografia e validação de dados
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
