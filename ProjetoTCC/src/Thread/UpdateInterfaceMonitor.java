/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thread;

import br.com.moises.controller.MonitorArduinoController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import model.Dado;
import util.UltimosDados;

/**
 *
 * @author MOISES
 */
   //Thread para ficar atualizando com os ultimos dados
    public class UpdateInterfaceMonitor implements Runnable {
        Dado dado;
        Label label;

    public UpdateInterfaceMonitor(Label lbl) {
        this.label = lbl;
        dado = UltimosDados.getInstance().getDado();
    }
        
        
        
        
        @Override
        public void run() {
            while (true) {              
                dado = UltimosDados.getInstance().getDado();
                try {
                    System.out.println("=========");
                    label.setText(String.valueOf(dado.getLuminosidade()));
                   
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MonitorArduinoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }