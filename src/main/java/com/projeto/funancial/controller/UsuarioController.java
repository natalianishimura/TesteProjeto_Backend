package com.projeto.funancial.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.exception.EncriptadorServiceException;
import com.projeto.funancial.model.Usuario;
import com.projeto.funancial.service.EncriptadorService;
import com.projeto.funancial.service.UsuarioService;
import com.projeto.funancial.transformation.UsuarioTransformation;

/**
 * A classe <code>UsuarioController</code> fornece endpoints relacionados ao objeto usuario.
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 */
@RestController
@RequestMapping("/usuario")
@CrossOrigin
public class UsuarioController {
	private UsuarioService service;
	private UsuarioTransformation transformation;
	private EncriptadorService encriptadorService;	
	
	public UsuarioController(UsuarioService service, UsuarioTransformation transformation,
			EncriptadorService encriptadorService) {
		this.service = service;
		this.transformation = transformation;
		this.encriptadorService = encriptadorService;
	}

    private final Logger logger = LogManager.getLogger(UsuarioController.class);

	/**
	 * Retorna todos os usu�rios registrados no banco
	 * 
	 * @return ResponseEntity - Composi��o de lista de usu�rios e o status HTTP 
	 */
	@GetMapping(value = "/")
	public ResponseEntity<List<UsuarioCanonical>> getUsuarios(){
		List<Usuario> usuario = service.findAll();
		if(usuario.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(transformation.convert(usuario), HttpStatus.OK);
	}
	/**
	 * Retorna o usu�rio com o id informado do banco
	 * 
	 * @param ObjectId id - Id do usuario que dever� ser retornado 
	 * @return ResponseEntity - Composi��o de usu�rio buscado e o status HTTP 
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioCanonical> getUsuarioById(@PathVariable("id") ObjectId id) {
		Optional<Usuario> usuario = Optional.ofNullable(service.findBy_Id(id));
		if(!usuario.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
		}
		return new ResponseEntity<>(transformation.convert(usuario.get()), HttpStatus.OK);
	}
	/**
	 * Salva um usu�rio no banco de dados
	 * 
	 * @param UsuarioCanonical usuarioCanonical- Objeto usu�rio informado pelo usu�rio que ser� inserido no reposit�rio
	 * @return ResponseEntity - Composi��o de usu�rio informado e o status HTTP 
	 */
	@PostMapping(value = "/")
	public ResponseEntity<UsuarioCanonical> createUsuario(@RequestBody UsuarioCanonical usuarioCanonical) {
		String senhaEncriptada = new String();
		
		try {
			 senhaEncriptada = encriptadorService.geraSenhaEncriptada(usuarioCanonical.getSenha());
		} catch (EncriptadorServiceException e) {
			logger.error("Erro encontrado durante a encripta��o da senha informada:\n" + e.getMessage()
			+ "\nCausa:\n" + e.getCause());
			
			return new ResponseEntity<UsuarioCanonical>(usuarioCanonical, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		usuarioCanonical.setSenha(senhaEncriptada);
		usuarioCanonical.set_id(ObjectId.get());
		
		service.save(transformation.convert(usuarioCanonical));
				
		return new ResponseEntity<>(usuarioCanonical, HttpStatus.CREATED);
	}
	/**
	 * Atualiza o usu�rio com o ID informado no banco de dados
	 * 
	 * @param ObjectId id - Id do usu�rio que sofrer� a altera��o
	 * @param UsuarioCanonical usuarioCanonical - Objeto contendo as altera��es do usu�rio em quest�o 
	 * @return ResponseEntity - Composi��o de usu�rio informado/alterado e o status HTTP 
	 */  
	@PutMapping(value = "/{id}")
	public ResponseEntity<UsuarioCanonical> updateUsuario(@PathVariable("id") ObjectId id,
			@RequestBody UsuarioCanonical usuarioCanonical) {
		Optional<Usuario> usuarioOptional = Optional.ofNullable(service.findBy_Id(id));
		String senhaEncriptada = new String();
		
		if(!usuarioOptional.isPresent()) {
			return new ResponseEntity<>(usuarioCanonical, HttpStatus.NO_CONTENT);
		}
		
		try {
			 senhaEncriptada = encriptadorService.geraSenhaEncriptada(usuarioCanonical.getSenha());
		} catch (EncriptadorServiceException e) {
			logger.error("Erro encontrado durante a encript da senha informada:\n" + e.getMessage()
			+ "\nCausa:\n" + e.getCause());
			
			return new ResponseEntity<UsuarioCanonical>(usuarioCanonical, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		usuarioOptional.get().setNome(usuarioCanonical.getNome());
		usuarioOptional.get().setSobrenome(usuarioCanonical.getSobrenome());
		usuarioOptional.get().setEmail(usuarioCanonical.getEmail());
		usuarioOptional.get().setSenha(senhaEncriptada);
		usuarioOptional.get().setNivel(usuarioCanonical.getNivel());
		usuarioOptional.get().setSequencia(usuarioCanonical.getSequencia());
		usuarioOptional.get().setVida(usuarioCanonical.getVida());
		usuarioOptional.get().setEnergia(usuarioCanonical.getEnergia());
		usuarioOptional.get().setDinheiro(usuarioCanonical.getDinheiro());
		
		return new ResponseEntity<>(transformation.convert(service.save(usuarioOptional.get())), HttpStatus.OK);
	}
	/**
	 * Remove o usu�rio do banco de dados
	 * 
	 * @param ObjectId id - Id do usu�rio que ser� excluido
	 * @return ResponseEntity - Status HTTP 
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UsuarioCanonical> deleteUsuario(@PathVariable ObjectId id) {
		Optional<Usuario> usuario = Optional.ofNullable(service.findBy_Id(id));		
		
		if(!usuario.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(transformation.convert(
				service.deleteBy_id(usuario.get().get_id())), HttpStatus.OK);			
	}
}
