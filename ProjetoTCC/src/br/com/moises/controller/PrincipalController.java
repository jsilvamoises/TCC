/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moises.controller;

import arduino.JavaSerialPort;
import inicio.MonitorArduino;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MOISES
 */
public class PrincipalController implements Initializable {

    @FXML
    Button btMonitorar;

    @FXML
    Button btnSair;

    @FXML
    private TextField lblTeste;

    List<String> lista = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        btMonitorar.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                new MonitorArduino().start(new Stage());
                System.out.println("Oi estou funcionando");
            }
        });

        btnSair.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                  
            }
        });
        
       // new Alert(Alert.AlertType.NONE, "Iniciano", ButtonType.APPLY).show();
      teste();
    }

    void teste() {
        try {
            lista = JavaSerialPort.getInstance().getPortas();
            lblTeste.setText(lista.get(0));
        } catch (Exception e) {
            lblTeste.setText(e+"");
            System.out.println("e");
        }

    }

}
