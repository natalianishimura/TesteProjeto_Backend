package com.projeto.funancial.transformation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projeto.funancial.beanUtil.UsuarioBeanUtil;
import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.model.Usuario;

/**
 * A classe <code>UsuarioTransformation</code> � utilizada como intermedi�rio entre as classes 
 * <code>UsuarioBeanUtil</code> e <code>UsuarioController</code> com o fim de reduzir acoplamento.
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 */
@Service
public class UsuarioTransformation {
	private UsuarioBeanUtil usuarioBeanUtil;
	
	public UsuarioTransformation(UsuarioBeanUtil usuarioBeanUtil) {
		this.usuarioBeanUtil = usuarioBeanUtil;
	}
	
	/**
     * Transformar� um objeto do tipo UsuarioCanonical em um objeto do tipo Usuario
     *
     * @param UsuarioCanonical usuarioCanonical - O objeto do tipo UsuarioCanonical que ser� transformado em um Usuario.
     * @return Usuario - O objeto Usuario ap�s transforma��o.
     */
	public Usuario convert(UsuarioCanonical usuarioCanonical) {
		return usuarioBeanUtil.toUsuario(usuarioCanonical);
	}
	
	/**
     * Transformar� um objeto do tipo Usuario em um objeto do tipo UsuarioCanonical 
     *
     * @param Usuario usuario - O objeto do tipo Usuario que ser� transformado em um UsuarioCanonical.
     * @return UsuarioCanonical - O objeto UsuarioCanonical  ap�s transforma��o.
     */
	public UsuarioCanonical convert(Usuario usuario) {
		return usuarioBeanUtil.toUsuarioCanonical(usuario);
	}
	
	/**
     * Transformar� um objeto do tipo Collection<Usuario> em um objeto do tipo List<UsuarioCanonical> 
     *
     * @param Collection<Usuario> usuarios - O objeto do tipo Collection<Usuario> que ser� transformado em um List<UsuarioCanonical>.
     * @return List<UsuarioCanonical> - O objeto List<UsuarioCanonical> ap�s transforma��o.
     */	
	public List<UsuarioCanonical> convert(Collection<Usuario> usuarios){
		return usuarios
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}
}
