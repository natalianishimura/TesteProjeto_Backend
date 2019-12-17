package com.projeto.funancial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import com.projeto.funancial.model.Usuario;

public class EstatisticaServiceTest {
	EstatisticasService service = new EstatisticasService();
	private static final Double DINHEIRO_INICIAL = 30d;
	private static final Integer NIVEL_INICIAL = 1;

	@Test
	public void deve_retornar_progresso_no_nivel_do_usuario() {
		// config
		Usuario usuario = new Usuario();
		usuario.setNivel(2);
		Integer diferencaNivelEsperada = usuario.getNivel() - NIVEL_INICIAL;
		// exec
		Integer estatisticaNivel = service.gerarEstatisticaNivel(usuario);
		// check
		assertEquals(diferencaNivelEsperada, estatisticaNivel);
	}

	@Test
	public void deve_retornar_progresso_no_dinheiro_do_usuario() {
		// config
		Usuario usuario = new Usuario();
		usuario.setDinheiro(30);
		Double diferencaEsperada = usuario.getDinheiro().doubleValue() - DINHEIRO_INICIAL;
		// exec
		Double estatisticaDinheiro = service.gerarEstatisticaDinheiro(usuario);
		// check
		assertEquals(diferencaEsperada, estatisticaDinheiro);
	}

	@Test
	public void deve_retornar_zero_no_progresso_nivel_por_nao_ter_tido_nenhum_avanco() {
		// config
		Usuario usuario = new Usuario();
		usuario.setNivel(1);
		// exec
		Integer estatisticaNivel = service.gerarEstatisticaNivel(usuario);
		// check
		assertEquals(0, estatisticaNivel);
	}

	@Test
	public void deve_retornar_zero_no_dinheiro() {
		// config
		Usuario usuario = new Usuario();
		usuario.setDinheiro(30);

		// exec
		Double estatisticaDinheiro = service.gerarEstatisticaDinheiro(usuario);
		// check
		assertEquals(0d, estatisticaDinheiro);
	}

	@Test
	public void deve_retornar_valor_negativo() {
		// config
		Usuario usuario = new Usuario();
		usuario.setDinheiro(20);

		Double valorEsperado = 20 - DINHEIRO_INICIAL;

		// exec
		Double estatisticaDinheiro = service.gerarEstatisticaDinheiro(usuario);
		// check
		assertEquals(valorEsperado, estatisticaDinheiro);
	}

}
