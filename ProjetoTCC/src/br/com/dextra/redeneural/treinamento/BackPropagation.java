package br.com.dextra.redeneural.treinamento;

import br.com.dextra.redeneural.estrutura.PerceptronMultiplasCamadas;

public class BackPropagation {

	private static final Double FATOR_ADAPTACAO = 0.03;
	private static final Double ERRO_MAXIMO = 0.05;
	private static final Integer NR_MAXIMO_EPOCAS = 100000;

	protected PerceptronMultiplasCamadas mlp;
	private Integer nrEpocas;
	private Double erros[];

	public BackPropagation(Integer nrEntradas, Integer nrNeuroniosCamadaIntermediaria, Integer nrNeuroniosCamadaSaida) {
		this.mlp = new PerceptronMultiplasCamadas(nrEntradas, nrNeuroniosCamadaIntermediaria, nrNeuroniosCamadaSaida);
		this.iniciarVetorDeErros(nrNeuroniosCamadaSaida);
	}

	public void iniciarVetorDeErros(int nrNeuroniosCamadaSaida) {
		erros = new Double[nrNeuroniosCamadaSaida];
		for (int i = 0; i < erros.length; i++) {
			erros[i] = 1.0;
		}
	}

	public Double[] classificar(Double entradas[]) {
		Double saidas[] = mlp.propagarSinais(entradas);
		for (int i = 0; i < saidas.length; i++) {
			saidas[i] = new Double(Math.round(saidas[i].doubleValue()));
		}
		return saidas;
	}

	public void treinar(Double entradas[][], Double esperados[][]) {
		this.nrEpocas = 0;
		while (erroMaiorQueMinimo() && naoAtingiuMaximoDeEpocas()) {
			for (int i = 0; i < entradas[0].length; i++) {
				Double saidas[] = mlp.propagarSinais(entradas[i]);
				this.processarErros(esperados, saidas[i], i);
				Double gradientes[] = this.calcularGradientesDeRetropopagacao(saidas);
				mlp.retropropagarErro(entradas[i], gradientes, BackPropagation.FATOR_ADAPTACAO);
			}
			this.nrEpocas++;
		}
	}

	private Double[] calcularGradientesDeRetropopagacao(Double saidas[]) {
		Double gradientes[] = new Double[saidas.length];
		for (int i = 0; i < gradientes.length; i++) {
			gradientes[i] = saidas[i] * (1 - saidas[i]) * erros[i];
		}
		return gradientes;
	}

	private boolean naoAtingiuMaximoDeEpocas() {
		return nrEpocas < BackPropagation.NR_MAXIMO_EPOCAS;
	}

	private boolean erroMaiorQueMinimo() {
		Double eqm = this.calcularErroQuadraticoMedio(erros);
		return Math.abs(eqm) > BackPropagation.ERRO_MAXIMO;
	}

	private Double calcularErroQuadraticoMedio(Double erros[]) {
		Double eqm = 0d;
		for (int i = 0; i < erros.length; i++) {
			eqm += (erros[i] * erros[i]);
		}
		return eqm / erros.length;
	}

	private void processarErros(Double[][] esperados, Double saida, int i) {
		for (int j = 0; j < esperados[i].length; j++) {
			erros[j] = esperados[i][j] - saida;
		}
	}

}