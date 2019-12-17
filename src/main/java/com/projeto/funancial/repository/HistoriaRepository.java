package com.projeto.funancial.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.projeto.funancial.model.Historia;

public interface HistoriaRepository extends MongoRepository<Historia, String>{
	Historia getHistoriaBySequenciaAndNivel(Integer sequencia, Integer nivel);
}