package br.com.dextra.mlp;

public class ExecutorRedeNeuralMLP1 {

 //   private static double[][] CONJUNTO_TREINAMENTO = {
        //0.20,0.25,0.58},
    //{1,1,1}

    // {0.32, 0.2, 0.55, 0.23, 0.15,0.01},
    // {1.0, 1.0, 1.0, 1.0, 1.0,1.0}
//        {0.65, 0.64, 0.57, 0.35, 1.08, 0.48, 0.57, 0.53},
//        {0.66, 1.07, 0.56, 0.97, 1.03, 0.48, 0.56, 1.03,},
//        {0.67, 1.15, 0.55, 1.07, 0.53, 0.48, 0.55, 1.14,},
//        {0.65, 1.12, 0.54, 1.06, 1.17, 0.48, 0.54, 0.89,},
//        {0.66, 0.56, 0.53, 0.64, 1.02, 0.48, 0.53, 0.55,},
//        {0.67, 0.53, 0.52, 0.53, 1.07, 0.48, 0.52, 0.36,},
//        {0, 0.54, 0.51, 1.06, 0.35, 0.48, 0.51, 0.64,},
//        {0, 0.35, 0.5, 0.57, 0.64, 0.48, 0.5, 0.41,},
//        {0, 0.42, 0.49, 0, 0, 0, 0.49, 0.4,},
//        {0, 0.42, 0.49, 0, 0, 0, 0.49, 0.4,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0.04, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01,},
//        {0, 0.01, 0, 0.01, 0.01, 0, 0, 0.01,},
//        {1.43683494283946, 2.74472069421434, 3.04639247583293, 2.86848923435541, 3.70285918496119, 1.97632531230394, 2.14520356313511, 3.06748524387635,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {0, 0, 0, 0, 0, 0, 0, 0,},
//        {1, 1, 1, 1, 1, 1, 1, 1}
//    };

    private static double[] VALORES_ESPERADOS 
             =  {0, 1, 0, 1};
    //{0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
      

    // = {0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0};
//	private static double[][] CONJUNTO_TREINAMENTO = {
//		{0.0, 0.0, 1.0, 1.0},
//		{0.0, 1.0, 0.0, 1.0},
//		{1.0, 1.0, 1.0, 1.0} // bias
//	};
    private static double[][] CONJUNTO_TREINAMENTO = {
        {0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0},
        {0.0, 0.5, 0.5, 1.0, 0.0, 0.0, 1.0},
        {0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0},
        {1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0},
        {0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0},
        {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0} // bias
    };
//    private static double[] VALORES_ESPERADOS = {1.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0};
    //private static double[] VALORES_ESPERADOS = {0.0, 1.0, 1.0, 0.0};
//    private static double[][] CONJUNTO_TREINAMENTO = {
//		{1.0, 0.0,1.0,0.0},
//		{1.0,0.0,0.0,1.0},
//		{1.0, 1.0,1.0,1.0} // bias
//	};
//        
//         private static double[] VALORES_ESPERADOS = {1.0, 0.0,0.0,1.0};
    public static void main(String[] args) {
        System.gc();
        int nrNeuroniosPrimeiraCamada = 6;
        int nrNeuroniosEntrada = 3;

        RedeNeuralMLP rede = new RedeNeuralMLP(nrNeuroniosPrimeiraCamada, nrNeuroniosEntrada);

        System.out.println("Teste antes do treinamento: --------------------------------------------------------------------------- \n");
        ExecutorRedeNeuralMLP1.imprimirTesteDeClassificacao(rede);

        rede.treinar(ExecutorRedeNeuralMLP1.CONJUNTO_TREINAMENTO, ExecutorRedeNeuralMLP1.VALORES_ESPERADOS);

        System.out.println("\n Teste depois do treinamento: ----------------------------------------------------------------------- \n");
        ExecutorRedeNeuralMLP1.imprimirTesteDeClassificacao(rede);

    }

    private static void imprimirTesteDeClassificacao(RedeNeuralMLP rede) {
        rede.classificar(new double[]{0.050, 0.210, 0.090, 0.560, 0.180, 0.360, 1.000});
      //  rede.classificar(new double[]{10, 3, 1});

        //  rede.classificar(new double[]{0.01,1.0});
        //rede.classificar(new double[]{33, 64, 35, 36, 37, 168, 38, 42, 40, 40, 41, 0, 0, 0, 0, 0, 1});
        //rede.classificar(new double[]{115, 105, 108, 118, 97, 49, 50, 51, 52, 52, 0, 0, 0, 0, 0, 0, 1}
        //  );
        //rede.classificar(new double[]{0.7,0.9,1.0});
        // rede.classificar(new double[]{0.14,0.14,1.0});
        // rede.classificar(new double[]{0.0,0.0,1.0});
        //rede.classificar(new double[]{0.67, 0.53, 0.52, 0.53, 1.07, 0.48, 0.52, 0.36,});
        // rede.classificar(new double[]{0.67, 1.15, 0.55, 1.07, 0.53, 0.48, 0.55, 1.14,});
        /*
         rede.classificar(new double[] { 0.0, 0.0, 1.0 });
         rede.classificar(new double[] { 0.0, 1.0, 1.0 });
         rede.classificar(new double[] { 1.0, 0.0, 1.0 });
         rede.classificar(new double[] { 1.0, 1.0, 1.0 });*/
//        rede.classificar(new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0});
//
//        rede.classificar(new double[]{1.0, 0.5, 1.0, 1.0, 1.0, 1.0});
//        rede.classificar(new double[]{1.0, 0.5, 1.0, 1.0, 1.0, 1.0});
//        rede.classificar(new double[]{1.0, 0.5, 1.0, 1.0, 1.0, 1.0});
//        rede.classificar(new double[]{1.0, 0.5, 1.0, 1.0, 1.0, 1.0});
//        rede.classificar(new double[]{1.0, 0.5, 1.0, 1.0, 1.0, 1.0});
//        rede.classificar(new double[]{1.0, 0.5, 1.0, 1.0, 1.0, 1.0});
//        rede.classificar(new double[]{1.0, 0.5, 1.0, 1.0, 1.0, 1.0});
    }

}
