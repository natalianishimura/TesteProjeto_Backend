package com.projeto.funancial.service;

import static com.projeto.funancial.util.JWTUtil.createJwt;
import static com.projeto.funancial.util.JWTUtil.isJwtValid;

import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.configuration.ApplicationConfig;
import com.projeto.funancial.exception.AuthenticationServiceException;

/**
 * A classe <code>AuthenticationService</code> � utilizada para a autentica��o de usu�rios.
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 */	
@Service
public class AuthenticationService {	
	private ApplicationConfig config;
	
	public AuthenticationService(ApplicationConfig config) {
		this.config = config;
	}

	/**
	 * M�todo respons�vel por gerar um token criptografado em HMAC256 com base em um 
	 * segredo e no ID do usu�rio
	 * 
	 * @param UsuarioCanonical usuarioCanonical - Usuario cujo qual o ID ser� utilizado na gera��o do token
	 * @return String - Token gerado ao fim do processo
	 * @throws AuthenticationServiceException - Exce��o gen�rica para tratamento de erros de autentica��o na aplica��o
	 */
	public String geraToken(UsuarioCanonical usuarioCanonical) throws AuthenticationServiceException {	
		try {
			return createJwt(config.getJwtSecret(), usuarioCanonical.get_id().toHexString());		
		} catch(JWTCreationException e) {
			throw new AuthenticationServiceException("Erro durante a cria��o do token.", e.getCause()); 
		}	
	}	

	/**
	 * M�todo respons�vel por validar um token vinculado a um usu�rio, buscando validar seu acesso 
	 * 
	 * @param UsuarioCanonical usuarioCanonical - Usuario cujo qual o ID ser� utilizado na valida��o do token
	 * @param String token - Token que ser� validado
	 * @return Boolean - Retorna verdadeiro caso o token seja valido em rela��o ao usu�rio.
	 */
	public Boolean validaToken(UsuarioCanonical usuarioCanonical) {
		return isJwtValid(this.config.getJwtSecret(), usuarioCanonical.get_id().toHexString(), usuarioCanonical.getJwt());		
	}
}
