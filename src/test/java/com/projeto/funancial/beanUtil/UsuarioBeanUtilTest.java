package com.projeto.funancial.beanUtil;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.model.Usuario;

public class UsuarioBeanUtilTest {
	private UsuarioBeanUtil usuarioBeanUtil = new UsuarioBeanUtil();
	
	@Test
	public void to_usuario_deve_retornar_um_usuario() {
		//config
		Usuario usuario = new Usuario();
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		//exec
		Usuario resultado = usuarioBeanUtil.toUsuario(usuarioCanonical);
		//check
		assertEquals(usuario, resultado);		
	}
	
	@Test
	public void to_usuario_canonical_deve_retornar_um_usuario_canonical() {
		//config
		Usuario usuario = new Usuario();
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		//exec
		UsuarioCanonical resultado = usuarioBeanUtil.toUsuarioCanonical(usuario);
		//check
		assertEquals(usuarioCanonical, resultado);		
	}
}
