/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gnu.io.CommPortIdentifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author MOISES
 */
public class PortasDisponiveis extends Application{
    
        

    private static PortasDisponiveis instance;
    Enumeration<CommPortIdentifier> numPortas;

    public static PortasDisponiveis getInstance() {
        return instance == null ? instance = new PortasDisponiveis() : instance;
    }

    private PortasDisponiveis() {
    }
  /*
    private List<String> portas = new ArrayList<>();

    public List<String> getPortas() {
        new Alert(Alert.AlertType.NONE, "Antes de entrar no tyr", ButtonType.APPLY).show();
        try {
            new Alert(Alert.AlertType.NONE, "Entrou no try", ButtonType.APPLY).show();
            numPortas = CommPortIdentifier.getPortIdentifiers();
            portas.clear();
            while (numPortas.hasMoreElements()) {
                String porta = numPortas.nextElement().getName();
                portas.add(porta);
                System.out.println(">>> Adicionando porta " + porta + " a lista de portas disponiveis!!!!");
            }
            return portas;
        } catch (Exception e) {
            new Alert(Alert.AlertType.NONE, "Não foi possível ler a porta >>"+e, ButtonType.CLOSE).show();
            return null;
        }

    }*/

    

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
//        vBox.getChildren().addAll(
//                comboBoxPorts,
//                textFieldOut,
//                btnSend);
         
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
