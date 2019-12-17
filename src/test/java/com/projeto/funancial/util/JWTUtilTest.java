package com.projeto.funancial.util;

import static com.projeto.funancial.util.JWTUtil.createJwt;
import static com.projeto.funancial.util.JWTUtil.isJwtValid;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.Test;

public class JWTUtilTest {	
	private String tokenValido = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
			 					".eyJpc3MiOiJ0ZXN0ZSJ9" +
			 					".iNLN1vJxBqNflfWphZm0P0K3JoFsH7SFRrmZDeg_cX8";
	
	@Test
	public void create_jwt_deve_retornar_o_token_criado_quando_criacao_bem_sucedida() {
		//config
		String secret = "segredo";
		String issuer = "teste";
		//exec
		String resultado = createJwt(secret, issuer);
		//check
		assertEquals(tokenValido, resultado);
	}
	
	@Test
	public void is_jwt_valid_deve_retornar_true_quando_token_informado_valido() {
		//config
		String secret = "segredo";
		String issuer = "teste";
		String jwt = tokenValido;
		//exec
		Boolean resultado = isJwtValid(secret, issuer, jwt);
		//check
		assertTrue(resultado);		
	}
	
	@Test
	public void is_jwt_valid_deve_retornar_false_quando_token_informado_invalido() {
		//config
		String secret = "segredo";
		String issuer = "teste";
		String jwt = "tokenInvalido";
		//exec
		Boolean resultado = isJwtValid(secret, issuer, jwt);
		//check
		assertFalse(resultado);				
	}
}
