package com.projeto.funancial.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A classe <code>Usuario</code> é um POJO (Plain Old Java Object) para representar 
 * a collection funancial.usuario do MongoDB.
 *
 * @author guilhermeguis@outlook.com
 * @version 1.0
 * @since JDK1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
	@Id 
	private ObjectId _id;
	private String email;
	private String nome;
	private String sobrenome;
	private String senha;
	private Integer nivel;
	private Integer sequencia;
	private Integer vida;
	private Integer energia;
	private Integer dinheiro;
}
