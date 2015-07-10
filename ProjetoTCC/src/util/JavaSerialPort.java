/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author MOISES
 */
public class JavaSerialPort implements SerialPortEventListener {

    private String serialIn;
    private OutputStream serialOut;
    private int taxa;
    private String portaCom;    
    boolean isIniciado;
    private String input;
    private Map<String, String> mapaPortas = new HashMap<>();
    SerialPort port;
    private static JavaSerialPort instance;

    public static JavaSerialPort getInstance() {
        return instance == null ? instance = new JavaSerialPort() : instance;
    }
    /*CONSTRUTOR PRIVADO*/

    private JavaSerialPort() {
    }

    public void setPort(String portaCom, int taxa) {
        try {
            if (!mapaPortas.containsKey(portaCom)) {
                this.taxa = taxa;
                this.portaCom = portaCom;
                mapaPortas.put(portaCom, portaCom);
                this.initialize();
            }
        } catch (Exception e) {
            System.out.println("Porta de comunicação em uso!!!!!");
        }
    }
    /**
     * INICIA UM CONEXÃO COM A PORTA
     */
    private void initialize() {
        try {
            port = new SerialPort(portaCom);
            port.openPort();
            port.setParams(
                    this.taxa,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Porta com não encontrada!!!!");
            alert.setHeaderText("A porta " + portaCom + " não foi encontrada");
            alert.setContentText("Verifique se o dispositivo está conectado.");
            alert.showAndWait();
        }
    }
    
    /**
     * Método que fecha a comunicação com a porta serial
     */
    
    public void closeOut(){
        try {
            serialOut.close();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível fechar porta COM. >> erro::"+e, ButtonType.OK).showAndWait();
        }
    }
    
    public void closeIn(){
        try {
           // serialIn.close();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível fechar porta COM. >> erro::"+e, ButtonType.OK).showAndWait();
        }
    }
    
    /**
     * @param opcao - Valor a ser enviado pela porta Serial
     */
    
    public void enviaDados(int opcao){
        try {
            port.writeInt(opcao);
           // serialOut.write(opcao);
        } catch (Exception e) {
             new Alert(Alert.AlertType.ERROR, "Não foi possível enviar o dado. >> erro::"+e, ButtonType.OK).showAndWait();
        }
    }
    
    public void enviaDados(byte opcao){
        try {
            port.writeByte(opcao);
           // serialOut.write(opcao);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível enviar o dado. >> erro::"+e, ButtonType.OK).showAndWait();
        }
    }
    
    public void pararLeitura(){
        System.out.println(">>Interrompendo leitura da porta " + portaCom);
        try {
            port.removeEventListener();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível parar a leitura da porta. >> erro::"+e, ButtonType.OK).showAndWait();
        }
    }
    
    public void iniciarLeitura(){
        try {
            if(portaCom.isEmpty()){
                new Alert(Alert.AlertType.ERROR, "Não foi possível detectar uma porta para se conectar >> erro::", ButtonType.OK).showAndWait();
            }else{
               System.out.println("Iniciado leitura da porta " + portaCom);
               serialIn = port.readString();
               port.addEventListener(instance);
               //Verificar se funciona assim
            }
        } catch (Exception e) {
        }
    }
    

    @Override
    public void serialEvent(SerialPortEvent spe) {
        
        switch (spe.getEventType()) {
            case SerialPortEvent.BREAK:
                break;
            case SerialPortEvent.CTS:
                break;
            case SerialPortEvent.DSR:
                break;
            case SerialPortEvent.RXCHAR: {
                String st = "";
                String linha = "";
                while (!serialIn.isEmpty()) {
                    
                    try {
                        //String tmp = Integer.toHexString(portaEntrada.read());
                        input = port.readString(); //= new BufferedReader(new InputStreamReader(port.getInputStream()));
                        
                        // linha = input.readLine();
                        // st += linha;
                    } catch (SerialPortException ex) {
                        Logger.getLogger(JavaSerialPort.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                System.out.println(">>>  Últimos dados coletados :: "+linha);
                ConverterJsonToObject.getInstance().transformToObject(linha);
                break;
            }
            case SerialPortEvent.ERR:
                break;
            case SerialPortEvent.RING:
                break;
            case SerialPortEvent.RLSD:
                break;
            case SerialPortEvent.RXFLAG:
                break;
            case SerialPortEvent.TXEMPTY:
                break;
            

        }
    }

}
