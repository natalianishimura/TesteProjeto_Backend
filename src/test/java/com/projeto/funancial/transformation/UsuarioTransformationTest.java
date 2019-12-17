package com.projeto.funancial.transformation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.projeto.funancial.beanUtil.UsuarioBeanUtil;
import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.model.Usuario;

public class UsuarioTransformationTest {
	private UsuarioBeanUtil beanUtil = Mockito.mock(UsuarioBeanUtil.class); 
	private UsuarioTransformation usuarioTransformation = new UsuarioTransformation(beanUtil);
	
	@Test
	public void convert_deve_retornar_um_usuario_canonical() {
		//config
		Usuario usuario = new Usuario();
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		
		when(beanUtil.toUsuarioCanonical(usuario)).thenReturn(usuarioCanonical);
		//exec
		UsuarioCanonical resultado = usuarioTransformation.convert(usuario); 
		//check
		assertEquals(resultado, usuarioCanonical);		
	}
	
	@Test
	public void convert_deve_retornar_um_usuario() {
		//config
		Usuario usuario = new Usuario();
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		
		when(beanUtil.toUsuario(usuarioCanonical)).thenReturn(usuario);
		//exec
		Usuario resultado = usuarioTransformation.convert(usuarioCanonical); 
		//check
		assertEquals(resultado, usuario);		
	}

	@Test
	public void convert_deve_retornar_uma_lista_de_usuario() {
		//config
		Collection<Usuario> usuarios = new ArrayList<Usuario>();
		List<UsuarioCanonical> usuariosCanonical = new ArrayList<UsuarioCanonical>();
		//exec
		List<UsuarioCanonical> resultado = usuarioTransformation.convert(usuarios); 
		//check
		assertEquals(resultado, usuariosCanonical);		
	}
}
