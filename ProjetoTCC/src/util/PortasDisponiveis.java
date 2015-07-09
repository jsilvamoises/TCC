/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gnu.io.CommPortIdentifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author MOISES
 */
public class PortasDisponiveis {
    private static  PortasDisponiveis instance;
    Enumeration<CommPortIdentifier> numPortas;
    public static PortasDisponiveis getInstance(){
      return  instance==null?instance = new PortasDisponiveis():instance;
    }

    private PortasDisponiveis() {
    }
    
    
    private List<String> portas = new ArrayList<>();
    
    public List<String> getPortas(){
        numPortas = CommPortIdentifier.getPortIdentifiers();
        portas.clear();
        while(numPortas.hasMoreElements()){
            String porta = numPortas.nextElement().getName();
            portas.add(porta);
            System.out.println(">>> Adicionando porta "+porta+" a lista de portas disponiveis!!!!");
        }
        return portas;
        
    }

   
    public static void main(String[] args) {
        PortasDisponiveis.getInstance().teste();
    }
    
    void teste(){
        PortasDisponiveis.getInstance().getPortas();
    }
    
}
