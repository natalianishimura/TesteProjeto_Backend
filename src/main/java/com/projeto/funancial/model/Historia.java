package com.projeto.funancial.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Historia {
	@Id 
	private ObjectId _id;
	private String historia;
	private Integer nivel;
	private Integer sequencia;
	private List<Opcao> opcao;
}
