/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino;

import javax.swing.JButton;
import util.UltimosDados;

/**
 *
 * @author MOISES
 */
public class Arduino {

    public static int DIG_PORTA_01_OFF = 10;
    public static int DIG_PORTA_01_ON = 11;
    public static int DIG_PORTA_02_OFF = 20;
    public static int DIG_PORTA_02_ON = 21;
    public static int DIG_PORTA_03_OFF = 30;
    public static int DIG_PORTA_03_ON = 31;
    public static int DIG_PORTA_04_OFF = 40;
    public static int DIG_PORTA_04_ON = 41;
    public static int DIG_PORTA_05_OFF = 50;
    public static int DIG_PORTA_05_ON = 51;
    public static int DIG_PORTA_06_OFF = 60;
    public static int DIG_PORTA_06_ON = 61;
    public static int DIG_PORTA_07_OFF = 70;
    public static int DIG_PORTA_07_ON = 71;
    public static int DIG_PORTA_08_OFF = 80;
    public static int DIG_PORTA_08_ON = 81;
    public static int DIG_PORTA_09_OFF = 90;
    public static int DIG_PORTA_09_ON = 91;
    public static int DIG_PORTA_10_OFF = 100;
    public static int DIG_PORTA_10_ON = 101;
    public static int DIG_PORTA_11_OFF = 110;
    public static int DIG_PORTA_11_ON = 111;
    public static int DIG_PORTA_12_OFF = 120;
    public static int DIG_PORTA_12_ON = 121;
    public static int DIG_PORTA_13_OFF = 130;
    public static int DIG_PORTA_13_ON = 131;

    private JavaSerialPort javaSerialPort;
    /*
     ############################################################################
     #             METODO CONSTRUTOR QUE RECEBER UMA PORTA POR PARAM            #
     ############################################################################
     */

    public Arduino(String porta) {
        javaSerialPort = JavaSerialPort.getInstance();
        javaSerialPort.setPort(porta, 9600);
    }
    /*
     ############################################################################
     #             METODO CONSTRUTOR QUE RECEBER UMA PORTA / FREQUENCIA         #
     ############################################################################
     */

    public Arduino(String porta, int frequencia) {
        javaSerialPort = JavaSerialPort.getInstance();
        javaSerialPort.setPort(porta, frequencia);
    }
    /*
     ############################################################################
     #             INDICA QUE OS VALORES COLETADOS É PARA SER PERSISTIDO        #
     ############################################################################
     */

    public void salvarDadosNoBanco() {
        UltimosDados.getInstance().setIsSavingInDatabase(true);
    }
    /*
     ############################################################################
     #         INDICA QUE OS VALORES COLETADOS NÃO É PARA SER PERSISTIDO        #
     ############################################################################
     */

    public void naoSalvarDadosNoBanco() {
        UltimosDados.getInstance().setIsSavingInDatabase(false);
    }

    public void pararDeEnviarDados() {
        javaSerialPort.closeOut();
    }

    public void pararDeReceberDados() {
        try {
            javaSerialPort.pararLeitura();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void lerPorta() {
        javaSerialPort.iniciarLeitura();
    }

    public void escreverNaPorta(byte valor) {
        javaSerialPort.enviaDados(valor);
    }

    public void escreverNaPorta(int valor) {
        javaSerialPort.enviaDados(valor);
    }

    public void comunicacaoArduino(JButton button) {

        if ("Ligar".equals(button.getActionCommand())) {
            javaSerialPort.enviaDados(131);
            System.out.println(button.getText());//Imprime o nome do botão pressionado
        } else if ("Desligar".equals(button.getActionCommand())) {
            javaSerialPort.enviaDados(130);
            System.out.println(button.getText());//Imprime o nome do botão pressionado
        } else if ("Ler".equals(button.getActionCommand())) {
            javaSerialPort.iniciarLeitura();
            System.out.println("*********");
            System.out.println(button.getText() + "\n ");//Imprime o nome do botão pressionado
        } else {
            javaSerialPort.closeOut();
            System.out.println(button.getText());//Imprime o nome do botão pressionado
        }
    }

    
    public void write(int porta) {
        try {
            javaSerialPort.enviaDados(porta);
        } catch (Exception e) {
             throw new IllegalArgumentException("O valor passada não é válido!!!!", e);
        }
    }

    @Deprecated
    public void desligarAlarmeMagnetico() {
        javaSerialPort.enviaDados(100);
    }

    @Deprecated
    public void ligarAlarmeMagnetico() {
        javaSerialPort.enviaDados(101);
    }

    @Deprecated
    public void desligarAlarmeIncendio() {
        javaSerialPort.enviaDados(110);
    }

    @Deprecated
    public void ligarAlarmeIncendio() {
        javaSerialPort.enviaDados(111);
    }

    @Deprecated
    public void desligarAquecedor() {
        javaSerialPort.enviaDados(120);
    }

    @Deprecated
    public void ligarAquecedor() {
        javaSerialPort.enviaDados(121);
    }

    @Deprecated
    public void desligarArcondicionado() {
        javaSerialPort.enviaDados(130);
    }

    @Deprecated
    public void ligarArcondicionado() {
        javaSerialPort.enviaDados(131);
    }
}
