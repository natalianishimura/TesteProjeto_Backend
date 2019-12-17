package com.projeto.funancial.service;

import com.projeto.funancial.canonical.UsuarioCanonical;

public class JogoService {
	private final int statusInicial = 30;
	
	public UsuarioCanonical iniciarUsuario(UsuarioCanonical usuarioCanonical) {
		usuarioCanonical.setNivel(1);
		usuarioCanonical.setSequencia(1);
		usuarioCanonical.setVida(statusInicial);
		usuarioCanonical.setEnergia(statusInicial);
		usuarioCanonical.setDinheiro(statusInicial);
		
		return usuarioCanonical;
	}
}
