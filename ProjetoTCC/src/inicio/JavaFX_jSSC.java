/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inicio;

/**
 *
 * @author MOISES
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
 
public class JavaFX_jSSC extends Application {
     
    ObservableList<String> portList;
     
    private void detectPort(){
         
        portList = FXCollections.observableArrayList();
 
        String[] serialPortNames = SerialPortList.getPortNames();
        for(String name: serialPortNames){
            System.out.println(name);
            portList.add(name);
        }
    }
     
    @Override
    public void start(Stage primaryStage) {
         
        detectPort();
 
        final ComboBox comboBoxPorts = new ComboBox(portList);
        final TextField textFieldOut = new TextField();
        Button btnSend = new Button("Send");
         
        btnSend.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent t) {
 
                if(comboBoxPorts.getValue() != null &&
                    !comboBoxPorts.getValue().toString().isEmpty()){
                     
                    String stringOut = textFieldOut.getText();
                     
                    try {
                        SerialPort serialPort =
                            new SerialPort(comboBoxPorts.getValue().toString());                       
                         
                        serialPort.openPort();
                        serialPort.setParams(
                                SerialPort.BAUDRATE_9600,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                        serialPort.writeBytes(stringOut.getBytes());
                        serialPort.closePort();
                         
                    } catch (SerialPortException ex) {
                        Logger.getLogger(
                            JavaFX_jSSC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     
                }else{
                    System.out.println("No SerialPort selected!");
                }
            }
        });
         
        VBox vBox = new VBox();
        vBox.getChildren().addAll(
                comboBoxPorts,
                textFieldOut,
                btnSend);
         
        StackPane root = new StackPane();
        root.getChildren().add(vBox);
         
        Scene scene = new Scene(root, 300, 250);
         
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
     
}

