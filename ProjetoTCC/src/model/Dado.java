/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author MOISES
 */
@Entity
@Table(name = "tbl_dado")
public class Dado implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataColeta;
    private float temperatura;
    private float umidade;
    private float luminosidade;
    private float magnetismo;   
    private String identificador;
    private byte statusAlarmeMagnestismo;
    private byte statusAlarmeIncencio;
    private byte statusAquecedor;
    private byte statusArcondicionado;
    private byte statusGeral;

    public Dado() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(Date dataColeta) {
        this.dataColeta = dataColeta;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getUmidade() {
        return umidade;
    }

    public void setUmidade(float umidade) {
        this.umidade = umidade;
    }

    public float getLuminosidade() {
        return luminosidade;
    }

    public void setLuminosidade(float luminosidade) {
        this.luminosidade = luminosidade;
    }

    public float getMagnetismo() {
        return magnetismo;
    }

    public void setMagnetismo(float magnetismo) {
        this.magnetismo = magnetismo;
    }

    

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public byte getStatusAlarmeMagnestismo() {
        return statusAlarmeMagnestismo;
    }

    public void setStatusAlarmeMagnestismo(byte statusAlarmeMagnestismo) {
        this.statusAlarmeMagnestismo = statusAlarmeMagnestismo;
    }

    public byte getStatusAlarmeIncencio() {
        return statusAlarmeIncencio;
    }

    public void setStatusAlarmeIncencio(byte statusAlarmeIncencio) {
        this.statusAlarmeIncencio = statusAlarmeIncencio;
    }

    public byte getStatusAquecedor() {
        return statusAquecedor;
    }

    public void setStatusAquecedor(byte statusAquecedor) {
        this.statusAquecedor = statusAquecedor;
    }

    public byte getStatusArcondicionado() {
        return statusArcondicionado;
    }

    public void setStatusArcondicionado(byte statusArcondicionado) {
        this.statusArcondicionado = statusArcondicionado;
    }

    public byte getStatusGeral() {
        return statusGeral;
    }

    public void setStatusGeral(byte statusGeral) {
        this.statusGeral = statusGeral;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dado other = (Dado) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
       return  String.valueOf(id);
    }

    

}
