package com.projeto.funancial.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.projeto.funancial.model.Usuario;
import com.projeto.funancial.repository.UsuarioRepository;

/**
 * A classe <code>UsuarioService</code> � respons�vel por manipular as opera��es CRUD 
 * (Create, Retrieve, Update and Delete)
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 */
@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
	
    public UsuarioService(UsuarioRepository usuarioRepository) {
    	this.usuarioRepository = usuarioRepository;
    }
    /**
     * Encontra todos os usu�rios da collection no banco.
     *
     * @return List<Usuario> - Todos os usu�rios encontrados.
     */
    public List<Usuario> findAll(){
    	List<Usuario> usuarios = new ArrayList<>();
        this.usuarioRepository.findAll().forEach(usuarios :: add);
        
        return usuarios;
    }
    
    /**
     * Encontra um usu�rio por seu Id.
     *
     * @param ObjectId _id - A identifica��o do usu�rio
     * @return Usuario - O usu�rio, se existir
     */
    public Usuario findBy_Id(ObjectId id) {
    	return usuarioRepository.findBy_id(id);
    }

    /**
     * Salva um usu�rio no banco de dados
     *
     * @param Usuario usuario  - O usu�rio que ser� salvo no banco de dados
     * @return Usuario - O usu�rio salvo.
     */
    public Usuario save(Usuario usuario) {   	
    	return usuarioRepository.save(usuario);
    }
    /**
     * Deleta um usu�rio no banco de dados
     *
     * @param Usuario usuario - o usu�rio que ser� deletado.
     */
    public Usuario deleteBy_id(ObjectId id) {
    	return usuarioRepository.deleteBy_id(id);
    }
}