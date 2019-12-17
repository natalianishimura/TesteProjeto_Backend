package com.projeto.funancial.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.configuration.ApplicationConfig;
import com.projeto.funancial.exception.AuthenticationServiceException;
import com.projeto.funancial.util.JWTUtil;

@PowerMockIgnore("javax.crypto.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(JWTUtil.class)
public class AuthenticationServiceTest {
	private ApplicationConfig config = PowerMockito.mock(ApplicationConfig.class);
			
	private AuthenticationService authenticationService = new AuthenticationService(config);
		
	@Test
	public void gera_token_deve_retornar_um_token_quando_bem_sucedido() throws AuthenticationServiceException{
		//config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()
												._id(ObjectId.get())
												.build();
		
		when(config.getJwtSecret()).thenReturn("segredo");
		//exec
		String resultado = authenticationService.geraToken(usuarioCanonical);
		//check
		assertTrue(!resultado.isEmpty());
	}
	
	@Test
	public void gera_token_deve_lancar_authentication_service_exception_quando_erro_durante_criacao_de_token()
			throws JsonProcessingException {
		//config
		PowerMockito.mockStatic(JWTUtil.class);
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()
												._id(new ObjectId("5dc60e3997a7914788664b6f"))
												.build();
		
		when(config.getJwtSecret()).thenReturn("segredo");
		when(JWTUtil.createJwt("segredo", usuarioCanonical.get_id().toHexString()))
			.thenThrow(JWTCreationException.class);
		//when(authenticationService.geraToken(usuarioCanonical)).thenThrow(JWTCreationException.class);
		//exec - check					
		assertThrows(AuthenticationServiceException.class,
				() -> authenticationService.geraToken(usuarioCanonical));	
	}

	@Test
	public void valida_token_deve_retornar_true_quando_token_valido() {
		//config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()
				._id(new ObjectId("5dc60e3997a7914788664b6f"))
				.jwt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9."
						+ "eyJpc3MiOiI1ZGM2MGUzOTk3YTc5MTQ3ODg2NjRiNmYifQ."
						+ "ypqzrJMV7uNoBvgughS1Exin5qSdrTgfuhTqlhgZT58")
				.build();
		when(config.getJwtSecret()).thenReturn("segredo");
		//exec
		boolean resultado = authenticationService.validaToken(usuarioCanonical);
		//check
		assertTrue(resultado);
	}
	
	@Test
	public void valida_token_deve_retornar_false_quando_token_invalido() {
		//config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()
				._id(ObjectId.get())
				.jwt("teste")
				.build();
		when(config.getJwtSecret()).thenReturn("segredo");
		//exec
		boolean resultado = authenticationService.validaToken(usuarioCanonical);
		//check
		assertFalse(resultado);
	}
}
