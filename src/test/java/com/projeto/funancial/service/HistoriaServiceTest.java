package com.projeto.funancial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import com.projeto.funancial.model.Historia;
import com.projeto.funancial.service.HistoriaService;

public class HistoriaServiceTest {

	@Test
	public void retornar_historia_por_sequencia_e_nivel() {
		HistoriaRepository repositorio = Mockito.mock(HistoriaRepository.class);
		Historia historia = new Historia();

		historia.setSequencia(2);
		historia.setNivel(1);

		Integer historiaEsperada = historia.getSequencia().getNivel();

		HistoriaService service = new HistoriaService(repositorio);
		
		Integer historiaSequenciaNivel = service.getHistoriaBySequenciaAndNivel(historia.getSequencia(), historia.getNivel());
		
		assertEquals(historiaEsperada, historiaSequenciaNivel);
	}

	@Test
	public void retornar_falso_por_sequencia_e_nivel_inexistente(){
		HistoriaRepository repositorio = Mockito.mock(HistoriaRepository.class);
		Historia historia = new Historia();

		historia.setSequencia(159);
		historia.setNivel(11000);

		HistoriaService service = new HistoriaService(repositorio);
		
		Integer historiaSequenciaNivel = service.getHistoriaBySequenciaAndNivel(historia.getSequencia(), historia.getNivel());
		
		assertFalse(historiaSequenciaNivel);
	}
}