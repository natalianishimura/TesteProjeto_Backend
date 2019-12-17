package com.projeto.funancial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.projeto.funancial.model.Historia;

public class HistoriaServiceTest {
	HistoriaService historia = new HistoriaService();
	
	@Test
	public void deve_retornar_historia_por_sequencia_e_nivel() {

		Historia historia = new Historia();
		
		historia.setSequencia(2);
		historia.setNivel(1);
		
		Integer historiaSequenciaNivel = service.getHistoriaBySequenciaAndNivel(historia.getSequencia(), historia.getNivel());
		
		assertEquals(historiaSequenciaNivel);
	}
}