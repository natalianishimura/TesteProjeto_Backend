package com.projeto.funancial.transformation;

import com.projeto.funancial.beanUtil.HistoriaBeanUtil;
import com.projeto.funancial.canonical.HistoriaCanonical;
import com.projeto.funancial.model.Historia;

public class HistoriaTransformation {
	private HistoriaBeanUtil historiaBeanUtil;
	
	public HistoriaTransformation(HistoriaBeanUtil historiaBeanUtil) {
		this.historiaBeanUtil = historiaBeanUtil;
	}
	
	public Historia convert(HistoriaCanonical historiaCanonical) {
		return historiaBeanUtil.toHistoria(historiaCanonical);
	}

	public HistoriaCanonical convert(Historia historia) {
		return historiaBeanUtil.toHistoriaCanonical(historia);
	}
}
