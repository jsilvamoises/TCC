/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.table;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author MOISES
 */
public class DadoTable {

    private StringProperty identificador;
    private FloatProperty temperatura;
    private FloatProperty umidade;
    private FloatProperty luminosidade;
    private FloatProperty magnetismo;
    private IntegerProperty statusAlarmeMagnetismo;
    private IntegerProperty statusAlarmeIncendio;
    private IntegerProperty statusAquecedor;
    private IntegerProperty statusArCondidiconado;
    private IntegerProperty statusGeral;

    public DadoTable() {
    }
    
    
    
    public DadoTable(String identificador, Float temperatura, Float umidade, Float luminosidade, Float magnetismo, byte statusAlarmeMagnetismo, byte statusAlarmeIncendio, byte statusAquecedor, byte statusArCondidiconado, byte statusGeral) {
        //Dados coletados dos sensores
        this.identificador = new SimpleStringProperty(identificador);
        this.temperatura = new SimpleFloatProperty(temperatura);
        this.umidade = new SimpleFloatProperty(umidade);
        this.luminosidade = new SimpleFloatProperty(luminosidade);
        this.magnetismo = new SimpleFloatProperty(magnetismo);
        //Dados enviados aos sensores
        this.statusAlarmeMagnetismo = new SimpleIntegerProperty(statusAlarmeMagnetismo);
        this.statusAlarmeIncendio = new SimpleIntegerProperty(statusAlarmeIncendio);
        this.statusAquecedor = new SimpleIntegerProperty(statusAquecedor);
        this.statusArCondidiconado = new SimpleIntegerProperty(statusArCondidiconado);
        this.statusGeral = new SimpleIntegerProperty(statusGeral);
        
        //
        
        
    }

    public StringProperty identificadorProperty() {
        return identificador;
    }

    public FloatProperty temperaturaProperty() {
        return temperatura;
    }

    public FloatProperty umidadeProperty() {
        return umidade;
    }

    public FloatProperty luminosidadeProperty() {
        return luminosidade;
    }

    public FloatProperty magnetismoProperty() {
        return magnetismo;
    }

    public IntegerProperty statusAlarmeMagnetismoProperty() {
        return statusAlarmeMagnetismo;
    }

    public IntegerProperty statusAlarmeIncendioProperty() {
        return statusAlarmeIncendio;
    }

    public IntegerProperty statusAquecedorProperty() {
        return statusAquecedor;
    }

    public IntegerProperty statusArCondidiconadoProperty() {
        return statusArCondidiconado;
    }

    public IntegerProperty statusGeralProperty() {
        return statusGeral;
    }
    
    
    

}
