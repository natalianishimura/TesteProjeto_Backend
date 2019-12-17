package com.projeto.funancial.controller;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.funancial.model.Estatistica;
import com.projeto.funancial.model.Usuario;
import com.projeto.funancial.service.EstatisticasService;
import com.projeto.funancial.service.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping(value = "/usuario/{id}/estatisticas")
public class EstatisticaController {
	private UsuarioService usuarioService;
	private EstatisticasService estatisticaService;

	public EstatisticaController(UsuarioService usuarioService, EstatisticasService estatisticaService) {
		this.usuarioService = usuarioService;
		this.estatisticaService = estatisticaService;
	}
	
	//public EstatisticaController() {}

	@GetMapping
	public ResponseEntity<Estatistica> getUserStatistics(@PathVariable("id") ObjectId id) {
		Optional<Usuario> usuarioDB = Optional.ofNullable(usuarioService.findBy_Id(id));
		
		if (!usuarioDB.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		Integer estatisticaNivel = estatisticaService.gerarEstatisticaNivel(usuarioDB.get());
		Double estatisticaDinheiro = estatisticaService.gerarEstatisticaDinheiro(usuarioDB.get());
		
		Estatistica estatisticaUsuario = new Estatistica(estatisticaDinheiro, estatisticaNivel);
		return new ResponseEntity<>(estatisticaUsuario, HttpStatus.OK);

	}

}
