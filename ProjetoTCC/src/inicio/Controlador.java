/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inicio;


import java.awt.Dimension;
import static java.awt.Toolkit.getDefaultToolkit;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.URLView;

/**
 *
 * @author MOISES
 */
public class Controlador extends Application{
   
    @Override
    public void start(Stage stage)  {
       
         Parent   root = null;
       
        URL a = URLView.getInstance().getUrl("Principal");
        System.out.println(a);
       
        try {
         root = FXMLLoader.load(a);
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Dimension d = getDefaultToolkit().getScreenSize();
         
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Controlador");
        //stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.setHeight(d.height);
        stage.setWidth(d.width);
        stage.setWidth(stage.getMaxWidth());
        stage.setHeight(stage.getMaxHeight());
       
        stage.show();
    }
    public static void main(String[] args) {
        launch(Controlador.class,args);
    }
    
    
}
