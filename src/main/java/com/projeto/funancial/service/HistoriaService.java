package com.projeto.funancial.service;

import org.springframework.stereotype.Service;

import com.projeto.funancial.model.Historia;
import com.projeto.funancial.repository.HistoriaRepository;

@Service
public class HistoriaService {
    private HistoriaRepository historiaRepository;
	
    public HistoriaService(HistoriaRepository historiaRepository) {
    	this.historiaRepository = historiaRepository;
    }

    public Historia getHistoriaBySequenciaAndNivel(Integer sequencia, Integer nivel) {
    	return historiaRepository.getHistoriaBySequenciaAndNivel(sequencia, nivel);
    }
    
}