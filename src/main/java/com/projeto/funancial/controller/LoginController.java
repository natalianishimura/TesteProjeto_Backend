package com.projeto.funancial.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.exception.AuthenticationServiceException;
import com.projeto.funancial.exception.EncriptadorServiceException;
import com.projeto.funancial.model.Usuario;
import com.projeto.funancial.service.AuthenticationService;
import com.projeto.funancial.service.EncriptadorService;
import com.projeto.funancial.service.UsuarioService;
import com.projeto.funancial.transformation.UsuarioTransformation;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
	private UsuarioService usuarioService;
	private UsuarioTransformation usuarioTransformation;
	private EncriptadorService encriptadorService;
	private AuthenticationService authenticationService;	
	private UsuarioController usuarioController;
	
	public LoginController(UsuarioService usuarioService, UsuarioTransformation usuarioTransformation,
			EncriptadorService encriptadorService, AuthenticationService authenticationService, 
			UsuarioController usuarioController) {
		this.usuarioService = usuarioService;
		this.encriptadorService = encriptadorService;
		this.authenticationService = authenticationService;
		this.usuarioController = usuarioController;
		this.usuarioTransformation = usuarioTransformation;
	}

	private final Logger logger = LogManager.getLogger(LoginController.class);

	@PostMapping(value = "/")
	public ResponseEntity<UsuarioCanonical> efetuaLogin(@RequestBody UsuarioCanonical usuarioCanonical) {
		if(Optional.ofNullable(usuarioCanonical.getJwt()).isPresent() && 
				authenticationService.validaToken(usuarioCanonical))
			return new ResponseEntity<UsuarioCanonical>(usuarioCanonical, HttpStatus.OK);		
		
		Optional<Usuario> usuario = usuarioService.findAll().stream()
				.filter(u -> usuarioCanonical.getEmail().equals(u.getEmail())).findFirst();			
		
		if(!usuario.isPresent())
			return new ResponseEntity<UsuarioCanonical>(usuarioCanonical, HttpStatus.NO_CONTENT);	
		
		try {
			if(!encriptadorService.validaSenha(usuarioCanonical.getSenha(), usuario.get().getSenha()))
				return new ResponseEntity<UsuarioCanonical>(usuarioCanonical, HttpStatus.UNAUTHORIZED);
			
			usuarioCanonical.setJwt(authenticationService.geraToken(usuarioTransformation.convert(usuario.get())));
			
			return new ResponseEntity<UsuarioCanonical>(usuarioCanonical, HttpStatus.OK);
		} catch(EncriptadorServiceException e) {
			logger.error("Erro encontrado durante a validação da senha informada:\n" + e.getMessage()
					+ "\nCausa:\n" + e.getCause());
			
			return new ResponseEntity<UsuarioCanonical>(usuarioCanonical, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(AuthenticationServiceException e) {
			logger.error("Erro encontrado durante a geração de token: \n" + e.getMessage()
			+ "\nCausa:\n" + e.getCause());
			
			return new ResponseEntity<UsuarioCanonical>(usuarioCanonical, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/cadastro")
	public ResponseEntity<UsuarioCanonical> efetuaCadastro(@RequestBody UsuarioCanonical usuarioCanonical) {
		Optional<Usuario> usuario = usuarioService.findAll().stream()
				.filter(u -> usuarioCanonical.getEmail().equals(u.getEmail())).findFirst();

		if (usuario.isPresent())
			return new ResponseEntity<UsuarioCanonical>(usuarioCanonical, HttpStatus.CONFLICT);
		
		return new ResponseEntity<UsuarioCanonical>(usuarioController.createUsuario(usuarioCanonical).getBody(), HttpStatus.OK);
	}
}
