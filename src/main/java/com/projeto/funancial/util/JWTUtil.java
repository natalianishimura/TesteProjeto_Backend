package com.projeto.funancial.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

/**
 * A classe <code>JWTUtil</code> cont�m utilidades em rela��o a gera��o e valida��o de JSON web tokens.
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 */	
public class JWTUtil {
	/**
	 * M�todo respons�vel por gerar um token criptografado em HMAC256 com base em um 
	 * segredo e em um issuer
	 * 
	 * @param String jwtSecret - Segredo que ser� utilizado para a cria��o do JWT
	 * @param String issuer - Issuer que ser� utilizado para a cria��o do JWT
	 * 
	 * @return String - Token gerado ao fim do processo
	 * @throws JWTCreationException
	 */
	public static String createJwt(String jwtSecret, String issuer) throws JWTCreationException {
		Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
		
		return JWT.create()
			.withIssuer(issuer)
			.sign(algorithm);
	}
	
	/**
	 * M�todo respons�vel por validar um token atrav�s de um segredo e um issuer
	 * 
	 * @param String jwtSecret - Segredo que ser� utilizado para a cri��o do JWT utilizado para valida��o
	 * @param String issuer - Issuer que ser� utilizado para a cria��o do JWT
	 * @param String jwt - Token que ser� validado
	 * 
	 * @return Boolean - Retorna verdadeiro caso o token informado seja valido.
	 */
	public static boolean isJwtValid(String jwtSecret, String issuer, String jwt) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer(issuer)
		        .build();
		    
		    verifier.verify(jwt);
		    
		    return true;
		} catch(JWTVerificationException e) {
			return false;
		}			
	}
}
