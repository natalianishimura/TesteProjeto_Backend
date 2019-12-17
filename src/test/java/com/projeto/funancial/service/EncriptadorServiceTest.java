package com.projeto.funancial.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.mockito.Mockito;

import com.projeto.funancial.exception.EncriptadorServiceException;

public class EncriptadorServiceTest {
	private EncriptadorService encriptadorService = new EncriptadorService();
	
	@Test
	public void get_senha_encriptada_bem_sucedido_deve_retornar_uma_senha_encriptada() 
		throws EncriptadorServiceException {
		//config
		String senha = "123";
		//exec
		String resultado = encriptadorService.geraSenhaEncriptada(senha);
		//check
		assertNotNull(resultado);
	}
	
	@Test
	public void get_senha_encriptada_deve_lancar_execao_encriptador_servie() 
		throws EncriptadorServiceException {
		//config
		String senha = "123";
		//exec
		String resultado = encriptadorService.geraSenhaEncriptada(senha);
		//check
		assertNotNull(resultado);
	}
	
	
	@Test(expected = EncriptadorServiceException.class)
	public void teste() throws EncriptadorServiceException, NoSuchAlgorithmException {
		//config		
		EncriptadorService encSvc = Mockito.mock(EncriptadorService.class);
		
		//when(encSvc.getSalt()).thenThrow(NoSuchAlgorithmException.class);
		EncriptadorServiceException a = new EncriptadorServiceException("", null);
		//RuntimeException r = new RuntimeException(a);
		Mockito.doThrow(a).when(encSvc).geraSenhaEncriptada(Mockito.anyString());
		encSvc.geraSenhaEncriptada("asdsad");
	}
}
