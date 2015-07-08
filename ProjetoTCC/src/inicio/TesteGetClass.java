/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inicio;

import util.URLView;



/**
 *
 * @author MOISES
 */
public class TesteGetClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      new TesteGetClass().teste();
    
    }
    
    void teste(){
        System.out.println(URLView.getInstance().getUrl("Principal"));
        // System.out.println(getClass().getClassLoader().getResource("/src/br/com/moises/view/Principal.fxml"));
    }
    
}
