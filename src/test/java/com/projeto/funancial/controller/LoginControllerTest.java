package com.projeto.funancial.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.exception.AuthenticationServiceException;
import com.projeto.funancial.exception.EncriptadorServiceException;
import com.projeto.funancial.model.Usuario;
import com.projeto.funancial.service.AuthenticationService;
import com.projeto.funancial.service.EncriptadorService;
import com.projeto.funancial.service.UsuarioService;
import com.projeto.funancial.transformation.UsuarioTransformation;

public class LoginControllerTest {
	private UsuarioService svc = Mockito.mock(UsuarioService.class);
	private EncriptadorService encrypt = Mockito.mock(EncriptadorService.class);
	private AuthenticationService auth = Mockito.mock(AuthenticationService.class);
	private UsuarioController usuarioController = Mockito.mock(UsuarioController.class);
	private UsuarioTransformation transformation = Mockito.mock(UsuarioTransformation.class);
	private LoginController loginController = new LoginController(svc, transformation, encrypt, auth,
			usuarioController);

	@Test
	public void efetua_login_deve_retornar_usuario_informado_quando_o_token_informado_for_valido() {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get()).jwt("test").build();
		when(auth.validaToken(usuarioCanonical)).thenReturn(true);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(usuarioCanonical, resultado.getBody());
	}

	@Test
	public void efetua_login_deve_retornar_status_ok_quando_o_token_informado_for_valido() {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get()).jwt("test").build();
		when(auth.validaToken(usuarioCanonical)).thenReturn(true);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(HttpStatus.OK, resultado.getStatusCode());
	}

	@Test
	public void efetua_login_bem_sucedido_deve_retornar_usuario_informado() throws EncriptadorServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100, 100,
				30);
		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		when(encrypt.validaSenha(usuarioCanonical.getSenha(), usuario.getSenha())).thenReturn(true);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(usuarioCanonical, resultado.getBody());
	}

	@Test
	public void efetua_login_bem_sucedido_deve_retornar_status_ok() throws EncriptadorServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100, 100,
				30);
		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		when(encrypt.validaSenha(usuarioCanonical.getSenha(), usuario.getSenha())).thenReturn(true);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(HttpStatus.OK, resultado.getStatusCode());
	}

	@Test
	public void efetua_login_mal_sucedido_por_email_nao_encontrado_deve_retornar_usuario_informado() {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioInexistente@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100, 100,
				30);

		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(usuarioCanonical, resultado.getBody());
	}

	@Test
	public void efetua_login_mal_sucedido_por_email_nao_encontrado_deve_retornar_status_no_content() {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioInexistente@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100, 100,
				30);

		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(HttpStatus.NO_CONTENT, resultado.getStatusCode());
	}

	@Test
	public void efetua_login_mal_sucedido_por_senha_incorreta_deve_retornar_usuario_informado()
			throws EncriptadorServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "125", 1, 1, 100, 100,
				30);

		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		when(encrypt.validaSenha(usuarioCanonical.getSenha(), usuario.getSenha())).thenReturn(false);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(usuarioCanonical, resultado.getBody());
	}

	@Test
	public void efetua_login_mal_sucedido_por_senha_incorreta_deve_retornar_status_unauthorized()
			throws EncriptadorServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "125", 1, 1, 100, 100,
				30);

		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		when(encrypt.validaSenha(usuarioCanonical.getSenha(), usuario.getSenha())).thenReturn(false);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(HttpStatus.UNAUTHORIZED, resultado.getStatusCode());
	}

	@Test
	public void efetua_login_mal_sucedido_por_excecao_durante_validacao_de_senha_deve_retornar_usuario_informado()
			throws EncriptadorServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100, 100,
				30);

		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		when(encrypt.validaSenha(usuarioCanonical.getSenha(), usuario.getSenha()))
				.thenThrow(EncriptadorServiceException.class);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(usuarioCanonical, resultado.getBody());
	}

	@Test
	public void efetua_login_mal_sucedido_por_excecao_durante_validacao_de_senha_deve_retornar_status_internal_server_error()
			throws EncriptadorServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100, 100,
				30);

		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		when(encrypt.validaSenha(usuarioCanonical.getSenha(), usuario.getSenha()))
				.thenThrow(EncriptadorServiceException.class);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resultado.getStatusCode());
	}

	@Test
	public void efetua_login_mal_sucedido_por_excecao_durante_gerar_token_deve_retornar_usuario_informado()
			throws AuthenticationServiceException, EncriptadorServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100, 100,
				30);

		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		when(encrypt.validaSenha(usuarioCanonical.getSenha(), usuario.getSenha())).thenReturn(true);
		when(transformation.convert(usuario)).thenReturn(usuarioCanonical);
		when(auth.geraToken(usuarioCanonical)).thenThrow(AuthenticationServiceException.class);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(usuarioCanonical, resultado.getBody());
	}

	@Test
	public void efetua_login_mal_sucedido_por_excecao_durante_gerar_token_deve_retornar_status_internal_server_error()
			throws AuthenticationServiceException, EncriptadorServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuario = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100, 100,
				30);

		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuario));
		when(encrypt.validaSenha(usuarioCanonical.getSenha(), usuario.getSenha())).thenReturn(true);
		when(transformation.convert(usuario)).thenReturn(usuarioCanonical);
		when(auth.geraToken(usuarioCanonical)).thenThrow(AuthenticationServiceException.class);
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaLogin(usuarioCanonical);
		// check
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resultado.getStatusCode());
	}

	@Test
	public void efetua_cadastro_bem_sucedido_deve_retornar_usuario_cadastrado()
			throws EncriptadorServiceException, AuthenticationServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();

		when(usuarioController.createUsuario(usuarioCanonical))
				.thenReturn(new ResponseEntity<>(usuarioCanonical, HttpStatus.OK));
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaCadastro(usuarioCanonical);
		// check
		assertEquals(usuarioCanonical, resultado.getBody());
	}

	@Test
	public void efetua_cadastro_bem_sucedido_deve_retornar_status_ok()
			throws EncriptadorServiceException, AuthenticationServiceException {
		// config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();

		when(usuarioController.createUsuario(usuarioCanonical))
				.thenReturn(new ResponseEntity<>(usuarioCanonical, HttpStatus.OK));
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaCadastro(usuarioCanonical);
		// check
		assertEquals(HttpStatus.OK, resultado.getStatusCode());
	}

	@Test
	public void efetua_cadastro_mal_sucedido_deve_retornar_usuario_invalido_quando_usuario_com_email_igual_ja_existir() {
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuarioAlt = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100,
				100, 30);
		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuarioAlt));
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaCadastro(usuarioCanonical);
		// check
		assertEquals(usuarioCanonical, resultado.getBody());
	}

	@Test
	public void efetua_cadastro_mal_sucedido_deve_retornar_status_conflict_quando_usuario_com_email_igual_ja_existir() {
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()._id(ObjectId.get())
				.email("usuarioTeste@teste.com").senha("123").build();
		Usuario usuarioAlt = new Usuario(ObjectId.get(), "usuarioTeste@teste.com", "Jose", "Souza", "123", 1, 1, 100,
				100, 30);
		when(svc.findAll()).thenReturn(Arrays.asList(new Usuario(), usuarioAlt));
		// exec
		ResponseEntity<UsuarioCanonical> resultado = loginController.efetuaCadastro(usuarioCanonical);
		// check
		assertEquals(HttpStatus.CONFLICT, resultado.getStatusCode());
	}
}
