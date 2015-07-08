/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import enums.ArduinoDigitalControls;

/**
 *
 * @author MOISES
 */
public class SystemGC {
    public static void main(String[] args) {
        System.gc();
        for(ArduinoDigitalControls i:ArduinoDigitalControls.values()){
            System.out.println("Nome : "+i.name() + ", valor: " + i.getValor());
        }
        System.out.print(ArduinoDigitalControls.PIN_01_OFF.getValor());
    }
}
