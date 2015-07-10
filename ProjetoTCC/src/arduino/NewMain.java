/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino;

/**
 *
 * @author MOISES
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      Arduino a = new Arduino("COM5");
      a.lerPorta();
    }
    
}
