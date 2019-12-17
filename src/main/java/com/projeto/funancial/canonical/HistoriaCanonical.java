package com.projeto.funancial.canonical;

import java.util.List;

import org.bson.types.ObjectId;

import com.projeto.funancial.model.Opcao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriaCanonical {
	private ObjectId _id;
	private String historia;
	private Integer nivel;
	private Integer sequencia;
	private List<Opcao> opcao;
}