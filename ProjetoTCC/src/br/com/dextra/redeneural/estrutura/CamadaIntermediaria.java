package br.com.dextra.redeneural.estrutura;

import br.com.dextra.redeneural.objetos.PojoTreino;

/*
 * TODO implementar.
 * - Retropropagacao da camada intermediaria.
 * - Bias em todos os neuronios.
 */
public class CamadaIntermediaria extends Camada {

	public CamadaIntermediaria(Integer nrNeuronios, Integer nrConexoesEntrada) {
		super(nrNeuronios, nrConexoesEntrada);
	}

	@Override
	public void retropropagarErro(PojoTreino pojoTreino) {
		/*Double entradasUltimaCamada[] = pojoTreino.getEntradasUltimaCamada();

		for (int j = 0; j < entradasUltimaCamada.length; j++) {
			double derivada = this.calcularDerivadaRetropropagacao(entradasUltimaCamada[j]);
			double sigma = derivada * (conexoesSegundaCamada[j] * gradiente);
			for (int k = 0; k < conexoesPrimeiraCamada[j].length; k++) {
				conexoesPrimeiraCamada[j][k] += RedeNeuralMLP.TAXA_APRENDIZADO * sigma * conjuntoTreinamento[k][i];
			}
		}*/
	}

	protected Double calcularDerivadaRetropropagacao(Double entradasUltimaCamada) {
		return entradasUltimaCamada * (1.0 - entradasUltimaCamada);
	}

}