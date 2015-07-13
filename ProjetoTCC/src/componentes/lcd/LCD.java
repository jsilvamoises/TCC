/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.lcd;

import eu.hansolo.enzo.lcd.Lcd;
import eu.hansolo.enzo.lcd.LcdBuilder;

/**
 *
 * @author MOISES
 */
public class LCD {

    public enum LCDType {

        LCD_TEMPERATURA,
        LCD_UNIDADE,
        LCD_LUMINOSIDADE,
        LCD_MAGNETISMO
    }

    private static LCD instance;

    public static LCD getInstance() {
        return instance == null ? instance = new LCD() : instance;
    }
   
    public Lcd getLctType(LCDType type) {
        Lcd lcd;
        lcd = LcdBuilder.create()
                .prefWidth(1280)
                .prefHeight(400)
                .lcdDesign(Lcd.LcdDesign.FLAT_CLOUDS)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .decimals(3)
                .animationDurationInMs(1500)
                .minMeasuredValueDecimals(2)
                .minMeasuredValueVisible(true)
                .maxMeasuredValueDecimals(2)
                .maxMeasuredValueVisible(true)
                .formerValueVisible(true)
                .threshold(26)
                .thresholdVisible(true)
                .trendVisible(true)
                .numberSystemVisible(false)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        switch (type) {
            case LCD_LUMINOSIDADE:
                lcd.titleProperty().setValue("Luminosidade");
                break;
            case LCD_MAGNETISMO:
                lcd.titleProperty().setValue("Magnetismo");
                break;
            case LCD_TEMPERATURA:
                lcd.titleProperty().setValue("Temperatura");
                lcd.unitProperty().set("ºC");
                lcd.unitVisibleProperty().set(true);             
                break;
            case LCD_UNIDADE:
                lcd.titleProperty().setValue("Umidade");
                break;
        }
        return lcd;
    }
}
