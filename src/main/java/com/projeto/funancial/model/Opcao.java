package com.projeto.funancial.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opcao {
	private Integer sequencia;
	private String resposta;
	private Integer amizade;
	private Integer energia;
	private Integer dinheiro;
	
	public Integer getSequencia() {
		return sequencia;
	}
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
	public Integer getAmizade() {
		return amizade;
	}
	public void setAmizade(Integer amizade) {
		this.amizade = amizade;
	}
	
	public Integer getEnergia() {
		return energia;
	}
	public void setEnergia(Integer energia) {
		this.energia = energia;
	}
	
	public Integer getDinheiro() {
		return dinheiro;
	}
	public void setDinheiro(Integer dinheiro) {
		this.dinheiro = dinheiro;
	}
}
