package com.projeto.funancial.beanUtil;


import org.springframework.stereotype.Service;

import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.model.Usuario;

/**
 * A classe <code>UsuarioBeanUtil</code> é responsável por operações utilitárias que 
 * englobam os objetos Usuario e UsuarioCanonical 
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 */
@Service
public class UsuarioBeanUtil {
	
	/**
     * Transforma um objeto do tipo UsuarioCanonical em outro objeto do tipo Usuario.
     *
     * @param UsuarioCanonical usuarioCanonical - Será transformado em Usuario.
     * @return Usuario - UsuarioCanonical transformado em Usuario.
     */
	public Usuario toUsuario(UsuarioCanonical usuarioCanonical) {
		return Usuario
				.builder()
				.email(usuarioCanonical.getEmail())
				.nome(usuarioCanonical.getNome())
				.senha(usuarioCanonical.getSenha())
				.sobrenome(usuarioCanonical.getSobrenome())
				.nivel(usuarioCanonical.getNivel())
				.sequencia(usuarioCanonical.getSequencia())
				.vida(usuarioCanonical.getVida())
				.energia(usuarioCanonical.getEnergia())
				.dinheiro(usuarioCanonical.getDinheiro())
				.build();
	}
	
	/**
     * Transforma um objeto do tipo Usuario em outro objeto do tipo UsuarioCanonical.
     *
     * @param Usuario usuario - Será transformado em UsuarioCanonical.
     * @return UsuarioCanonical - Usuario transformado em UsuarioCanonical.
     */
	public UsuarioCanonical toUsuarioCanonical(Usuario usuario) {
		return UsuarioCanonical
				.builder()
				.email(usuario.getEmail())
				.nome(usuario.getNome())
				.senha(usuario.getSenha())
				.sobrenome(usuario.getSobrenome())
				.nivel(usuario.getNivel())
				.sequencia(usuario.getSequencia())
				.vida(usuario.getVida())
				.energia(usuario.getEnergia())
				.dinheiro(usuario.getDinheiro())
				.build();
	}
}
