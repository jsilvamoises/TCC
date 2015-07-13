/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes.lcd;

import eu.hansolo.enzo.experimental.tbutton.TButton;
import javafx.scene.control.Button;

/**
 *
 * @author MOISES
 */
public class ComponentsUtil {
    private static ComponentsUtil instance;
    public static ComponentsUtil getInstance(){
        return instance == null?instance = new ComponentsUtil():instance;
    }
    public void desabilitarBotoesControleManual(Object object[]) {
        
        for (Object o :object ) {
            if(o instanceof Button){
                ((Button)o).setDisable(true);
            }else if(o instanceof TButton){
                ((TButton)o).setDisable(true);
            }
            
        }
    }
    
    public void habilitarBotoesControleManual(Object object[]) {
        for (Object o :object ) {
            if(o instanceof Button){
                ((Button)o).setDisable(false);
            }else if(o instanceof TButton){
                ((TButton)o).setDisable(false);
            }
            
        }
    }
    
    public void setDeselected(TButton tb[]){
        for(TButton t:tb){
            t.setSelected(false);
        }
        
    }
    public void setSelected(TButton tb[]){
        for(TButton t:tb){
            t.setSelected(true);
        }
        
    }
    
//    public void habilitarBotoesControleManual(Array object) {
//        for (Object o :object ) {
//            if(o instanceof Button){
//                ((Button)o).setDisable(false);
//            }else if(o instanceof TButton){
//                ((TButton)o).setDisable(false);
//            }
//            
//        }
//    }
}
