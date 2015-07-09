/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino;

import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import util.UltimosDados;

/**
 *
 * @author MOISES
 */
public class Arduino {

    private ControlePorta arduino;

//    public Arduino() {
//        arduino = ControlePorta.getInstance();
//        arduino.setPort("COM5", 9600);
//        //  arduino = new ControlePorta("COM5", 9600);
//    }
    
    public Arduino(String porta) {
        arduino = ControlePorta.getInstance();
        arduino.setPort(porta, 9600);
        //  arduino = new ControlePorta("COM5", 9600);
    }
    
    public Arduino(String porta, int frequencia) {
        arduino = ControlePorta.getInstance();
        arduino.setPort(porta, frequencia);
        //  arduino = new ControlePorta("COM5", 9600);
    }

    public void salvarDadosNoBanco() {
        UltimosDados.getInstance().setIsSavingInDatabase(true);
    }

    public void naoSalvarDadosNoBanco() {
        UltimosDados.getInstance().setIsSavingInDatabase(false);
    }

    public void ligar() {
        arduino.enviaDados(131);
    }

    public void desligar() {
        arduino.enviaDados(130);
    }

    public void pararDeEnviarDados() {
        arduino.closeOut();
    }

    public void pararDeReceberDados() {
        try {
            arduino.pararLeitura();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    public void lerPorta() {
        try {
            arduino.iniciarLeitura();
        } catch (TooManyListenersException | IOException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void escreverNaPorta(byte[] valor) {
        arduino.enviaDados(valor);
    }

    public void escreverNaPorta(int valor) {
        arduino.enviaDados(valor);
    }

    public void comunicacaoArduino(JButton button) {

        if ("Ligar".equals(button.getActionCommand())) {
            arduino.enviaDados(131);
            System.out.println(button.getText());//Imprime o nome do botão pressionado
        } else if ("Desligar".equals(button.getActionCommand())) {
            arduino.enviaDados(130);
            System.out.println(button.getText());//Imprime o nome do botão pressionado
        } else if ("Ler".equals(button.getActionCommand())) {
            try {
                arduino.iniciarLeitura();
            } catch (TooManyListenersException | IOException ex) {
                Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("*********");
            System.out.println(button.getText() + "\n ");//Imprime o nome do botão pressionado
        } else {
            arduino.closeOut();
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
        arduino.enviaDados(100);
    }
    public void ligarAlarmeMagnetico(){
        arduino.enviaDados(101);
    }
    
    public void desligarAlarmeIncendio(){
        arduino.enviaDados(110);
    }
    public void ligarAlarmeIncendio(){
        arduino.enviaDados(111);
    }
    
    public void desligarAquecedor(){
        arduino.enviaDados(120);
    }
    public void ligarAquecedor(){
        arduino.enviaDados(121);
    }
    
    public void desligarArcondicionado(){
        arduino.enviaDados(130);
    }
    public void ligarArcondicionado(){
        arduino.enviaDados(131);
    }
}
