package com.projeto.funancial.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.projeto.funancial.model.Usuario;
import com.projeto.funancial.repository.UsuarioRepository;

/**
 * A classe <code>UsuarioService</code> é responsável por manipular as operações CRUD 
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
     * Encontra todos os usuários da collection no banco.
     *
     * @return List<Usuario> - Todos os usuários encontrados.
     */
    public List<Usuario> findAll(){
    	List<Usuario> usuarios = new ArrayList<>();
        this.usuarioRepository.findAll().forEach(usuarios :: add);
        
        return usuarios;
    }
    
    /**
     * Encontra um usuário por seu Id.
     *
     * @param ObjectId _id - A identificação do usuário
     * @return Usuario - O usuário, se existir
     */
    public Usuario findBy_Id(ObjectId id) {
    	return usuarioRepository.findBy_id(id);
    }

    /**
     * Salva um usuário no banco de dados
     *
     * @param Usuario usuario  - O usuário que será salvo no banco de dados
     * @return Usuario - O usuário salvo.
     */
    public Usuario save(Usuario usuario) {   	
    	return usuarioRepository.save(usuario);
    }
    /**
     * Deleta um usuário no banco de dados
     *
     * @param Usuario usuario - o usuário que será deletado.
     */
    public Usuario deleteBy_id(ObjectId id) {
    	return usuarioRepository.deleteBy_id(id);
    }
}