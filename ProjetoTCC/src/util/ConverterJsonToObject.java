/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.gson.Gson;
import java.util.Calendar;
import model.Dado;

/**
 *
 * @author MOISES
 */
public class ConverterJsonToObject {

    private static ConverterJsonToObject instance;

    private ConverterJsonToObject() {
    }
    /**
     * Cria apenas uma instancia da classe
     * @return 
     */
    public static ConverterJsonToObject getInstance() {
        return instance == null ? instance = new ConverterJsonToObject() : instance;
    }
    /**
     * Converte uma string json em um objeto Java
     * @param valor
     * @return 
     */
    public Dado transformToObject(String valor) {
        Dado d = null;
        System.out.println(">>>Convertendo ojeto json para ojeto Java");
        try {
            Gson gsom = new Gson();
            d = gsom.fromJson(valor, Dado.class);
            d.setDataColeta(Calendar.getInstance().getTime());         
            
            UltimosDados.getInstance().add(d);            
        } catch (Exception e) {
        }

        return d;
    }

}
