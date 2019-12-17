package com.projeto.funancial.service;

import org.springframework.stereotype.Service;

import com.projeto.funancial.model.Usuario;

@Service
public class EstatisticasService {
	private static final Double DINHEIRO_INICIAL = 30d;
	private static final Integer NIVEL_INICIAL = 1;

	public EstatisticasService() {
	}

	public Double gerarEstatisticaDinheiro(Usuario usuario) {
		Double dinheiroAtual = usuario.getDinheiro().doubleValue();
		Double dinheiroEstatistica = dinheiroAtual - DINHEIRO_INICIAL;
		return dinheiroEstatistica;
	}

	public Integer gerarEstatisticaNivel(Usuario usuario) {
		Integer nivelAtual = usuario.getNivel();
		Integer nivelEstatistica = nivelAtual - NIVEL_INICIAL;
		return nivelEstatistica;
	}
}
