/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException; 
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;
import javafx.scene.control.Alert;
import javax.swing.JOptionPane;
import util.ConverterJsonToObject;

/**
 *
 * @author MOISES
 */
public class ControlePorta implements SerialPortEventListener {

    private InputStream serialIn;
    private OutputStream serialOut;
    private int taxa;
    private String portaCom;
    SerialPort port;

    boolean isIniciado = false;

    private BufferedReader input;

    private Map<String, String> mapaPortas = new HashMap<>();

    private static ControlePorta instance;

    public static ControlePorta getInstance() {
        return instance == null ? instance = new ControlePorta() : instance;
    }

    /**
     * Construtor da classe ControlePorta
     *
     * @param portaCom
     * @param taxa - Taxa de transferência da porta serial geralmente é 9600
     */
    private ControlePorta() {

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
     * Médoto que verifica se a comunicação com a porta serial está ok
     */
    private void initialize() {
        try {
            //Define uma variável portId do tipo CommPortIdentifier para realizar a comunicação serial
            CommPortIdentifier portId = null;

            try {
                //Tenta verificar se a porta COM informada existe
                portId = CommPortIdentifier.getPortIdentifier(this.portaCom);

                //Abre porta COM
                port = (SerialPort) portId.open("Comunicação serial", this.taxa);
                serialOut = port.getOutputStream();

                port.setSerialPortParams(
                        this.taxa,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
            } catch (NoSuchPortException | PortInUseException | IOException | UnsupportedCommOperationException e) {
                //Caso a porta COM não exista será exibido um erro 
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Porta com não encontrada!!!!");
                alert.setHeaderText("A porta " + portaCom + " não foi encontrada");
                alert.setContentText("Verifique se o dispositivo está conectado.");
                alert.showAndWait();
                // JOptionPane.showMessageDialog(null, "Porta COM não encontrada", "Porta COM", JOptionPane.PLAIN_MESSAGE);
            }

        } catch (Exception e) {
        };
    }

    /**
     * Método que fecha a comunicação com a porta serial
     */
    public void closeOut() {
        try {
            serialOut.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível fechar porta COM.",
                    "Fechar porta COM", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void closeIn() {
        try {
            serialIn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível fechar porta COM.",
                    "Fechar porta COM", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * @param opcao - Valor a ser enviado pela porta Serial
     */
    public void enviaDados(int opcao) {
        try {
            serialOut.write(opcao); // Escreve o valor na porta serial
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível enviar o dado. ",
                    "Enviar dados", JOptionPane.PLAIN_MESSAGE);
        }

    }

    public void enviaDados(byte[] opcao) {
        try {
            serialOut.write(opcao); // Escreve o valor na porta serial
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível enviar o dado. ",
                    "Enviar dados", JOptionPane.PLAIN_MESSAGE);
        }

    }

    public void pararLeitura() {
        System.out.println(">>Interrompendo leitura da porta " + portaCom);
        port.removeEventListener();

    }

    public void iniciarLeitura() throws TooManyListenersException, IOException {
        System.out.println("Iniciado leitura da porta " + portaCom);

        serialIn = port.getInputStream();
        port.addEventListener(this);
        port.notifyOnDataAvailable(true);


}

@Override
        public void serialEvent(SerialPortEvent spe) {
        switch (spe.getEventType()) {
            case SerialPortEvent.BI:
                break;
            case SerialPortEvent.CD:
                break;
            case SerialPortEvent.CTS:
                break;
            case SerialPortEvent.DATA_AVAILABLE: {
                String st = "";
                String linha = "";
                // Dados d = new Dados();
                try {
                    while (serialIn.available() > 0) {

                        //String tmp = Integer.toHexString(portaEntrada.read());
                        input = new BufferedReader(new InputStreamReader(port.getInputStream()));

                        linha = input.readLine();
                        st += linha;

                    }
                    // System.out.println("Setando dados");
                    //  d.setDados(linha);
                    //  System.out.println("Criando objeto repositorio");
                    //new Repository().salvar(d);
                    // System.out.println(">>"+linha);
                    System.out.println(linha);
                    ConverterJsonToObject.getInstance().transformToObject(linha);
                } catch (IOException ex) {
                    // Logger.getLogger(GUJSerialPort.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case SerialPortEvent.DSR:
                break;
            case SerialPortEvent.FE:
                break;
            case SerialPortEvent.OE:
                break;
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.PE:
                break;
            case SerialPortEvent.RI:
                break;

        }
    }

}


//        try {
//            serialIn = port.getInputStream();
//            port.addEventListener(this);
//            port.notifyOnDataAvailable(true);
//            Thread thread2 = new Thread() {
//
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ie) {
//                    }
//                }
//
//            };
//            try {
//
//                input = new BufferedReader(new InputStreamReader(port.getInputStream()));
//
//                String linha = "";
//
//                //while(input.ready()){
//                linha = input.readLine();
//                // }
//
//                ConverterJsonToObject.getInstance().transformToObject(linha);
//                System.out.println(linha);
//            } catch (Exception e) {
//            }
            // thread = new Thread((Runnable) this);
        //  thread.start();
//    }
//    catch (IOException | TooManyListenersException e
//
//    
//        ) {
//            e.printStackTrace();
//    }
