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

    private JavaSerialPort javaSerialPort;

//    public Arduino() {
//        arduino = ControlePorta.getInstance();
//        arduino.setPort("COM5", 9600);
//        //  arduino = new ControlePorta("COM5", 9600);
//    }
    
    public Arduino(String porta) {
        javaSerialPort = JavaSerialPort.getInstance();
        javaSerialPort.setPort(porta, 9600);
        //  arduino = new ControlePorta("COM5", 9600);
    }
    
    public Arduino(String porta, int frequencia) {
        javaSerialPort = JavaSerialPort.getInstance();
        javaSerialPort.setPort(porta, frequencia);
        //  arduino = new ControlePorta("COM5", 9600);
    }

    public void salvarDadosNoBanco() {
        UltimosDados.getInstance().setIsSavingInDatabase(true);
    }

    public void naoSalvarDadosNoBanco() {
        UltimosDados.getInstance().setIsSavingInDatabase(false);
    }

    public void ligar() {
        javaSerialPort.enviaDados(131);
    }

    public void desligar() {
        javaSerialPort.enviaDados(130);
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

    /*
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     @                    LIGA E DESLIGA PINO DO ARDUINO                      @
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     @                                                                        @
     @ ALARME DE MAGNETISMO                                                   @
     @ 100 - DESLIGA                                                          @
     @ 101 - LIGA                                                             @
     @========================================================================@
     @ ALARME DE INCENDIO                                                     @
     @ 110 - DESLIGA                                                          @
     @ 111 - LIGA                                                             @
     @========================================================================@
     @ AQUECEDOR                                                              @
     @ 120 - DESLIGA                                                          @
     @ 121 - LIGA                                                             @
     @========================================================================@
     @ ARCONDICIONADO                                                         @
     @ 130 - DESLIGA                                                          @
     @ 131 - LIGA                                                             @
     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     */
    
    public void desligarAlarmeMagnetico(){
        javaSerialPort.enviaDados(100);
    }
    public void ligarAlarmeMagnetico(){
        javaSerialPort.enviaDados(101);
    }
    
    public void desligarAlarmeIncendio(){
        javaSerialPort.enviaDados(110);
    }
    public void ligarAlarmeIncendio(){
        javaSerialPort.enviaDados(111);
    }
    
    public void desligarAquecedor(){
        javaSerialPort.enviaDados(120);
    }
    public void ligarAquecedor(){
        javaSerialPort.enviaDados(121);
    }
    
    public void desligarArcondicionado(){
        javaSerialPort.enviaDados(130);
    }
    public void ligarArcondicionado(){
        javaSerialPort.enviaDados(131);
    }
}
