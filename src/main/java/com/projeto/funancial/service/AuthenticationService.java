package com.projeto.funancial.service;

import static com.projeto.funancial.util.JWTUtil.createJwt;
import static com.projeto.funancial.util.JWTUtil.isJwtValid;

import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.configuration.ApplicationConfig;
import com.projeto.funancial.exception.AuthenticationServiceException;

/**
 * A classe <code>AuthenticationService</code> é utilizada para a autenticação de usuários.
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
	 * Método responsável por gerar um token criptografado em HMAC256 com base em um 
	 * segredo e no ID do usuário
	 * 
	 * @param UsuarioCanonical usuarioCanonical - Usuario cujo qual o ID será utilizado na geração do token
	 * @return String - Token gerado ao fim do processo
	 * @throws AuthenticationServiceException - Exceção genérica para tratamento de erros de autenticação na aplicação
	 */
	public String geraToken(UsuarioCanonical usuarioCanonical) throws AuthenticationServiceException {	
		try {
			return createJwt(config.getJwtSecret(), usuarioCanonical.get_id().toHexString());		
		} catch(JWTCreationException e) {
			throw new AuthenticationServiceException("Erro durante a criação do token.", e.getCause()); 
		}	
	}	

	/**
	 * Método responsável por validar um token vinculado a um usuário, buscando validar seu acesso 
	 * 
	 * @param UsuarioCanonical usuarioCanonical - Usuario cujo qual o ID será utilizado na validação do token
	 * @param String token - Token que será validado
	 * @return Boolean - Retorna verdadeiro caso o token seja valido em relação ao usuário.
	 */
	public Boolean validaToken(UsuarioCanonical usuarioCanonical) {
		return isJwtValid(this.config.getJwtSecret(), usuarioCanonical.get_id().toHexString(), usuarioCanonical.getJwt());		
	}
}
