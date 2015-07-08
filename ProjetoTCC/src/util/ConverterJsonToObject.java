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

    public static ConverterJsonToObject getInstance() {
        return instance == null ? instance = new ConverterJsonToObject() : instance;
    }

    public Dado transformToObject(String valor) {
        Dado d = null;
        System.err.println("Iniciado conversao");
        try {
            Gson gsom = new Gson();
            d = gsom.fromJson(valor, Dado.class);
            d.setDataColeta(Calendar.getInstance().getTime());
            //Deverá ser pego de uma avaliação do sistema por meio de IA
            d.setIsNoPadrao(1);
            UltimosDados.getInstance().add(d);
            System.out.println("Dado de dentro do objeto"+d.getMagnetismo());
        } catch (Exception e) {
        }

        return d;
    }

}
