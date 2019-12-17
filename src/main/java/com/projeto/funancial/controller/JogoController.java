package com.projeto.funancial.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.funancial.canonical.HistoriaCanonical;
import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.service.HistoriaService;
import com.projeto.funancial.service.JogoService;
import com.projeto.funancial.service.UsuarioService;
import com.projeto.funancial.transformation.HistoriaTransformation;
import com.projeto.funancial.transformation.UsuarioTransformation;

@RestController
@RequestMapping("/jogo")
@CrossOrigin
public class JogoController {
	private UsuarioService usuarioService;
	private UsuarioTransformation usuarioTransformation;
	private JogoService jogoService;
	private HistoriaService historiaService;
	private HistoriaTransformation historiaTransformation;
	
	public JogoController(UsuarioService usuarioService, UsuarioTransformation usuarioTransformation, JogoService jogoService, 
			HistoriaService historiaService, HistoriaTransformation historiaTransformation) {
		this.usuarioService = usuarioService;
		this.usuarioTransformation = usuarioTransformation; 
		this.jogoService = jogoService;
		this.historiaService = historiaService;
		this.historiaTransformation = historiaTransformation;
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<List<Object>> iniciarJogo(@RequestBody UsuarioCanonical usuarioCanonical) { 
		List<Object> usuarioHistoria = new ArrayList<Object>();
		
		usuarioCanonical = jogoService.iniciarUsuario(usuarioCanonical);
		usuarioCanonical = usuarioTransformation.convert(
				usuarioService.save(usuarioTransformation.convert(usuarioCanonical)));
		
		HistoriaCanonical historiaCanonical = historiaTransformation.convert(
				historiaService.getHistoriaBySequenciaAndNivel(usuarioCanonical.getSequencia(), usuarioCanonical.getNivel()));
		
		usuarioHistoria.add(usuarioCanonical);
		usuarioHistoria.add(historiaCanonical);
		
		return new ResponseEntity<>(usuarioHistoria, HttpStatus.OK);
	}
}
