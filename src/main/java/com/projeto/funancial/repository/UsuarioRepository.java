package com.projeto.funancial.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.projeto.funancial.model.Usuario;

/**
 * A interface <code>UsuarioRepository</code> é utilizada para representar as operações CRUD 
 * (Create, Retrieve, Update and Delete) da interface 
 * <code>org.springframework.data.mongodb.repository.MongoRepository</code>.
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 **/
public interface UsuarioRepository extends MongoRepository<Usuario, String>{
	Usuario findBy_id(ObjectId id);
	Usuario deleteBy_id(ObjectId id);
}