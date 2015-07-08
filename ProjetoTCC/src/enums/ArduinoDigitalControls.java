/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author MOISES
 */
public enum ArduinoDigitalControls {
    /*
     LIGA E DESLIGA O PINOS DO ARDUINO 
     */

    PIN_01_ON(11),
    PIN_01_OFF(10),
    PIN_02_ON(21),
    PIN_02_OFF(20),
    PIN_03_ON(31),
    PIN_03_OFF(30),
    PIN_04_ON(41),
    PIN_04_OFF(40),
    PIN_05_ON(51),
    PIN_05_OFF(50),
    PIN_06_ON(61),
    PIN_06_OFF(60),
    PIN_07_ON(71),
    PIN_07_OFF(70),
    PIN_08_ON(81),
    PIN_08_OFF(80),
    PIN_09_ON(91),
    PIN_09_OFF(90),
    PIN_10_ON(101),
    PIN_10_OFF(100),
    PIN_11_ON(111),
    PIN_11_OFF(110),
    PIN_12_ON(121),
    PIN_12_OFF(120),
    PIN_13_ON(131),
    PIN_13_OFF(130);

    private int valor;

    ArduinoDigitalControls(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

}
