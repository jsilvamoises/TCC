/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.lcd;

import eu.hansolo.enzo.experimental.tbutton.TButton;
import eu.hansolo.enzo.experimental.tbutton.TButtonBuilder;
import javafx.scene.paint.Color;

/**
 *
 * @author MOISES
 */
public class ITButton {
    private static ITButton instance;
    public static ITButton getInstance(){
        return instance == null? instance = new ITButton():instance;
    }
    
    public TButton getButton(String texto, Color ledColor){
        return  TButtonBuilder.create()
                                .prefWidth(150)
                                .prefHeight(150)
                                .text(texto)
                                .ledColor(ledColor)                
                                .build();
    }
}
