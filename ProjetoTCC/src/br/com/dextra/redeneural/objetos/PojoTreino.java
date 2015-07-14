package br.com.dextra.redeneural.objetos;

public class PojoTreino {

	private Double entradasUltimaCamada[];
	private Double[] entradas, gradientes;
	private Double fatorAdaptacao;

	public PojoTreino(Double[] entradas, Double[] entradasUltimaCamada, Double[] gradientes, Double fatorAdaptacao) {
		this.entradasUltimaCamada = entradasUltimaCamada;
		this.entradas = entradas;
		this.gradientes = gradientes;
		this.fatorAdaptacao = fatorAdaptacao;
	}

	public Double[] getEntradasUltimaCamada() {
		return entradasUltimaCamada;
	}

	public Double[] getEntradas() {
		return entradas;
	}

	public Double[] getGradientes() {
		return gradientes;
	}

	public Double getFatorAdaptacao() {
		return fatorAdaptacao;
	}

}