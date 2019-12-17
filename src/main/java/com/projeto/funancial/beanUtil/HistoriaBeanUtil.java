package com.projeto.funancial.beanUtil;

import org.springframework.stereotype.Service;

import com.projeto.funancial.canonical.HistoriaCanonical;
import com.projeto.funancial.model.Historia;

@Service
public class HistoriaBeanUtil {
	public Historia toHistoria(HistoriaCanonical historiaCanonical) {
		return Historia
				.builder()
				._id(historiaCanonical.get_id())
				.historia(historiaCanonical.getHistoria())
				.nivel(historiaCanonical.getNivel())
				.sequencia(historiaCanonical.getSequencia())
				.opcao(historiaCanonical.getOpcao())
				.build();
	}
	
	public HistoriaCanonical toHistoriaCanonical(Historia historia) {
		return HistoriaCanonical
				.builder()
				._id(historia.get_id())
				.historia(historia.getHistoria())
				.nivel(historia.getNivel())
				.sequencia(historia.getSequencia())
				.opcao(historia.getOpcao())
				.build();
	}
}
