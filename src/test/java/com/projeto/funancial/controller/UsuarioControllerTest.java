package com.projeto.funancial.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.exception.EncriptadorServiceException;
import com.projeto.funancial.model.Usuario;
import com.projeto.funancial.service.EncriptadorService;
import com.projeto.funancial.service.UsuarioService;
import com.projeto.funancial.transformation.UsuarioTransformation;

public class UsuarioControllerTest {
	private UsuarioService svc = Mockito.mock(UsuarioService.class);
	private UsuarioTransformation transformation = Mockito.mock(UsuarioTransformation.class);
	private EncriptadorService encrypt = Mockito.mock(EncriptadorService.class);
	private UsuarioController usuarioController = new UsuarioController(this.svc, this.transformation, this.encrypt);	
	
	@Test
	public void get_usuarios_deve_retornar_uma_lista_de_usuarios() {
		//config	
		List<Usuario> usuariosEsperados = Arrays.asList(new Usuario(), new Usuario());
		List<UsuarioCanonical> usuariosCanonicalEsperados = 
				Arrays.asList(new UsuarioCanonical(), new UsuarioCanonical());
		
		when(this.svc.findAll()).thenReturn(usuariosEsperados);
		when(this.transformation.convert(usuariosEsperados)).thenReturn(usuariosCanonicalEsperados);
		//exec
		ResponseEntity<List<UsuarioCanonical>> resultado = this.usuarioController.getUsuarios();		
		//check
		assertEquals(usuariosCanonicalEsperados.size(), resultado.getBody().size());
	}
	
	@Test
	public void get_usuarios_deve_retornar_status_ok() {
		//config
		List<Usuario> usuariosEsperados = Arrays.asList(new Usuario(), new Usuario());	
		List<UsuarioCanonical> usuariosCanonicalEsperados = 
				Arrays.asList(new UsuarioCanonical(), new UsuarioCanonical());
		
		when(this.svc.findAll()).thenReturn(usuariosEsperados);			
		when(this.transformation.convert(usuariosEsperados)).thenReturn(usuariosCanonicalEsperados);
		
		//exec
		ResponseEntity<List<UsuarioCanonical>> resultado = this.usuarioController.getUsuarios();		
		//check
		assertEquals(HttpStatus.OK, resultado.getStatusCode());
	}
	
	@Test
	public void get_usuarios_deve_retornar_status_no_content_quando_mal_sucedido() {
		//config		
		when(this.svc.findAll()).thenReturn(new ArrayList<>());			
		//exec
		ResponseEntity<List<UsuarioCanonical>> resultado = this.usuarioController.getUsuarios();		
		//check
		assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
	}
	
	@Test
	public void get_usuario_by_id_deve_retornar_um_usuario() {
		//config
		Usuario usuarioEsperado = new Usuario();
		
		usuarioEsperado.set_id(ObjectId.get());		
		when(this.svc.findBy_Id(usuarioEsperado.get_id())).thenReturn(usuarioEsperado);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = this.usuarioController.getUsuarioById(usuarioEsperado.get_id());
		//check
		assertEquals(this.transformation.convert(usuarioEsperado), resultado.getBody());		
	}

	@Test
	public void get_usuario_by_id_deve_retornar_status_ok() {
		//config
		Usuario usuarioEsperado = new Usuario();
			
		usuarioEsperado.set_id(ObjectId.get());		
		when(this.svc.findBy_Id(usuarioEsperado.get_id())).thenReturn(usuarioEsperado);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = this.usuarioController.getUsuarioById(usuarioEsperado.get_id());
		//check
		assertEquals(HttpStatus.OK, resultado.getStatusCode());
	}
	
	@Test
	public void get_usuario_by_id_deve_retornar_status_no_content_quando_usuario_nao_existir() {
		//config
		ObjectId id = ObjectId.get();
		when(this.svc.findBy_Id(id)).thenReturn(null);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = this.usuarioController.getUsuarioById(id);
		//check
		assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
	}

	@Test
	public void create_usuario_deve_retornar_usuario_criado() throws EncriptadorServiceException{
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();
		usuarioCanonical.setSenha("123");
		
		when(this.encrypt.geraSenhaEncriptada(usuarioCanonical.getSenha())).thenReturn("1&2&3&");
		when(this.transformation.convert(usuarioCanonical)).thenReturn(usuario);
		when(this.svc.save(usuario)).thenReturn(usuario);
		when(this.transformation.convert(usuario)).thenReturn(usuarioCanonical);		
		//exec
		ResponseEntity<UsuarioCanonical> resultado = usuarioController.createUsuario(usuarioCanonical);
		//check
		assertEquals(usuarioCanonical, resultado.getBody());
	}
	
	@Test
	public void create_usuario_deve_retornar_status_created() throws EncriptadorServiceException{
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();
		usuarioCanonical.setSenha("123");
		
		when(this.transformation.convert(usuarioCanonical)).thenReturn(usuario);
		when(this.encrypt.geraSenhaEncriptada(usuarioCanonical.getSenha())).thenReturn("1&2&3&");
		//exec
		ResponseEntity<UsuarioCanonical> resultado = usuarioController.createUsuario(usuarioCanonical);
		//check
		assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
	}
	
	@Test
	public void create_usuario_deve_retornar_usuario_incorreto_quando_mal_sucedido_devido_a_execao() 
			throws EncriptadorServiceException {
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		usuarioCanonical.setSenha("123");
		
		when(this.encrypt.geraSenhaEncriptada(usuarioCanonical.getSenha())).thenThrow(EncriptadorServiceException.class);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = usuarioController.createUsuario(usuarioCanonical);
		//check
		assertEquals(usuarioCanonical, resultado.getBody());
	}

	@Test
	public void create_usuario_deve_retornar_status_internal_server_error_quando_mal_sucedido_devido_execao() 
			throws EncriptadorServiceException {
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		usuarioCanonical.setSenha("123");
		
		when(this.encrypt.geraSenhaEncriptada(usuarioCanonical.getSenha())).thenThrow(EncriptadorServiceException.class);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = usuarioController.createUsuario(usuarioCanonical);
		//check
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resultado.getStatusCode());
	}
	
	@Test
	public void update_usuario_deve_retornar_usuario_atualizado() throws EncriptadorServiceException {
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();
		
		usuario.set_id(ObjectId.get());
		usuarioCanonical.setSenha("123");
		
		when(this.svc.findBy_Id(usuario.get_id())).thenReturn(usuario);
		when(this.encrypt.geraSenhaEncriptada(usuarioCanonical.getSenha())).thenReturn("1&2&3&");
		when(this.svc.save(usuario)).thenReturn(usuario);
		when(this.transformation.convert(usuario)).thenReturn(usuarioCanonical);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = 
				usuarioController.updateUsuario(usuario.get_id(), usuarioCanonical);
		//check
		assertEquals(usuarioCanonical, resultado.getBody());		
	}

	@Test
	public void update_usuario_deve_retornar_status_ok() throws EncriptadorServiceException{
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();
		
		usuario.set_id(ObjectId.get());
		usuarioCanonical.setSenha("123");
		
		when(this.svc.findBy_Id(usuario.get_id())).thenReturn(usuario);
		when(this.encrypt.geraSenhaEncriptada(usuarioCanonical.getSenha())).thenReturn("1&2&3&");
		when(this.svc.save(usuario)).thenReturn(usuario);
		when(this.transformation.convert(usuario)).thenReturn(usuarioCanonical);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = 
				usuarioController.updateUsuario(usuario.get_id(), usuarioCanonical);
		//check
		assertEquals(HttpStatus.OK, resultado.getStatusCode());		
	}
	
	@Test
	public void update_usuario_deve_retornar_usuario_invalido_quando_nao_encontrar_usuario_especificado() {
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();
		
		usuario.set_id(ObjectId.get());
		
		when(this.svc.findBy_Id(usuario.get_id())).thenReturn(null);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = 
				usuarioController.updateUsuario(usuario.get_id(), usuarioCanonical);
		//check
		assertEquals(usuarioCanonical, resultado.getBody());		
	}
	
	@Test
	public void update_usuario_deve_retornar_status_no_content_quando_nao_encontrar_usuario_especificado() {
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();
		
		usuario.set_id(ObjectId.get());
		
		when(this.svc.findBy_Id(usuario.get_id())).thenReturn(null);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = 
				usuarioController.updateUsuario(usuario.get_id(), usuarioCanonical);
		//check
		assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());		
	}

	@Test
	public void update_usuario_deve_retornar_usuario_informado_quando_mal_sucedido_devido_execao() 
		throws EncriptadorServiceException {
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();
		
		usuario.set_id(ObjectId.get());
		usuarioCanonical.setSenha("123");
		
		when(this.svc.findBy_Id(usuario.get_id())).thenReturn(usuario);
		when(this.encrypt.geraSenhaEncriptada(usuarioCanonical.getSenha())).thenThrow(EncriptadorServiceException.class);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = 
				usuarioController.updateUsuario(usuario.get_id(), usuarioCanonical);
		//check
		assertEquals(usuarioCanonical, resultado.getBody());		
	}
	
	@Test
	public void update_usuario_deve_retornar_status_internal_server_error_quando_mal_sucedido_devido_execao() 
		throws EncriptadorServiceException {
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();
		
		usuario.set_id(ObjectId.get());
		usuarioCanonical.setSenha("123");
		
		when(this.svc.findBy_Id(usuario.get_id())).thenReturn(usuario);
		when(this.encrypt.geraSenhaEncriptada(usuarioCanonical.getSenha())).thenThrow(EncriptadorServiceException.class);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = 
				usuarioController.updateUsuario(usuario.get_id(), usuarioCanonical);
		//check
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resultado.getStatusCode());		
	}
	
	@Test
	public void delete_usuario_deve_retornar_o_usuario_deletado() {
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();		
		usuario.set_id(ObjectId.get());
		
		when(svc.findBy_Id(usuario.get_id())).thenReturn(usuario);
		when(svc.deleteBy_id(usuario.get_id())).thenReturn(usuario);
		when(transformation.convert(usuario)).thenReturn(usuarioCanonical);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = usuarioController.deleteUsuario(usuario.get_id());
		//check
		assertEquals(usuarioCanonical, resultado.getBody());
	}
	
	@Test
	public void delete_usuario_deve_retornar_status_ok() {
		//config
		UsuarioCanonical usuarioCanonical = new UsuarioCanonical();
		Usuario usuario = new Usuario();
		usuario.set_id(ObjectId.get());
		
		when(svc.findBy_Id(usuario.get_id())).thenReturn(usuario);
		when(svc.deleteBy_id(usuario.get_id())).thenReturn(usuario);
		when(transformation.convert(usuario)).thenReturn(usuarioCanonical);
		//exec
		ResponseEntity<UsuarioCanonical> resultado = usuarioController.deleteUsuario(usuario.get_id());
		//check
		assertEquals(HttpStatus.OK, resultado.getStatusCode());
	}	
	
	@Test
	public void delete_usuario_deve_retornar_status_no_content_quando_usuario_nao_encontrado() {
		//config
		Usuario usuario = new Usuario();
		usuario.set_id(ObjectId.get());
		
		when(svc.findBy_Id(usuario.get_id())).thenReturn(null);
		//exec
		ResponseEntity<?> resultado = usuarioController.deleteUsuario(usuario.get_id());
		//check
		assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
	}
}