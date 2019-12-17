package com.projeto.funancial.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.mockito.Mockito;

import com.projeto.funancial.model.Usuario;
import com.projeto.funancial.repository.UsuarioRepository;

public class UsuarioServiceTest {
	private UsuarioRepository repository = Mockito.mock(UsuarioRepository.class);
	private UsuarioService svc = new UsuarioService(repository);
	
	@Test
	public void find_all_deve_retornar_todos_os_usuarios_encontrados_no_banco() {
		//config
		List<Usuario> usuarios = new ArrayList<>();
		
		when(repository.findAll()).thenReturn(usuarios);
		//exec
		List<Usuario> resultado = svc.findAll();
		//check
		assertEquals(usuarios, resultado);
	}
	
	@Test
	public void find_by_id_deve_retornar_o_usuario_especificado() {
		//config
		Usuario usuario = new Usuario();
		usuario.set_id(ObjectId.get());
		
		when(repository.findBy_id(usuario.get_id())).thenReturn(usuario);
		//exec
		Usuario resultado = svc.findBy_Id(usuario.get_id());
		//check
		assertEquals(usuario, resultado);
	}
	
	@Test
	public void save_deve_retornar_o_usuario_salvo() {
		//config
		Usuario usuario = new Usuario();
		
		when(repository.save(usuario)).thenReturn(usuario);
		//exec
		Usuario resultado = svc.save(usuario);
		//check
		assertEquals(usuario, resultado);
	}

	@Test
	public void delete_by_id_deve_retornar_o_usuario_deletado() {
		//config
		Usuario usuario = new Usuario();
		usuario.set_id(ObjectId.get());
		
		when(repository.deleteBy_id(usuario.get_id())).thenReturn(usuario);
		//exec
		Usuario resultado = svc.deleteBy_id(usuario.get_id());
		//check
		assertEquals(usuario, resultado);
	}
}
