/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moises.controller;

import arduino.Arduino;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import model.Dado;
import util.UltimosDados;

/**
 * FXML Controller class
 *
 * @author MOISES
 */
public class MonitorArduinoController implements Initializable {
    SimpleDateFormat horaFormatada = new SimpleDateFormat("HH:mm:ss");
    private Arduino arduino;
    @FXML
    Button btnIniciarMonitoramento;
    @FXML
    Button btnPararMonitoramento;
    @FXML
    Button btnSalvarDatabase;
    @FXML
    Button btnNaoSalvarDatabase;
    @FXML
    private Button btnLuminosidade;
    @FXML
    private Button btnMagnetismo;
    @FXML
    private Button btnTemperatura;
    @FXML
    private Button btnUmidade;
    @FXML
    private ToggleButton tbAlarmeIncendio;
    @FXML
    private ToggleButton tbAlarmeMagnetismo;
    @FXML
    private ToggleButton tbAquecedor;
    @FXML
    private ToggleButton tbArcondicionado;

    @FXML
    private Label lblLuminosidade;
    @FXML
    private Label lblMagnetismo;
    @FXML
    private Label lblTemperatura;
    @FXML
    private Label lblUmidade;

    ToggleGroup grupoTagle = new ToggleGroup();

    UpdateT update;
    Thread t;

    Dado d;
    
    @FXML
    private LineChart<String,Number> lcGrafico;
    
//    @FXML
//    private ScatterChart<String,Number> lcGrafico;
    
     @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private List<Dado> dados = new ArrayList<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    
    private void gerarGrafico(){
        if(dados.size()>0){
        lcGrafico.getData().clear();
        
       // final CategoryAxis xAxis = new CategoryAxis();
      //  final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Sensores");
      //  final LineChart<String,Number> lineChart = 
               // new LineChart<>(xAxis,yAxis);
        
       // lcGrafico = lineChart;
        
        lcGrafico.setTitle("Monitoramento de ambiente");
                          
        XYChart.Series temperatura = new XYChart.Series();
        temperatura.setName("Temperatura");
        
        XYChart.Series umidade = new XYChart.Series();
        umidade.setName("Umidade");
        
        XYChart.Series luminosidade = new XYChart.Series();
        luminosidade.setName("Luminosidade");
        
        XYChart.Series magnetismo = new XYChart.Series();
        magnetismo.setName("Magnetismo");
        
        for(Dado dd:dados){
            temperatura.getData().add(new XYChart.Data(horaFormatada.format(dd.getDataColeta()), dd.getTemperatura()/100));
            umidade.getData().add(new XYChart.Data(horaFormatada.format(dd.getDataColeta()), dd.getUmidade()/100));
            luminosidade.getData().add(new XYChart.Data(horaFormatada.format(dd.getDataColeta()), dd.getLuminosidade()/100));
            magnetismo.getData().add(new XYChart.Data(horaFormatada.format(dd.getDataColeta()), dd.getMagnetismo()/100));
        }
        
        /*
        temperatura.getData().add(new XYChart.Data("Jan", 23));
        temperatura.getData().add(new XYChart.Data("Feb", 14));
        temperatura.getData().add(new XYChart.Data("Mar", 15));
        temperatura.getData().add(new XYChart.Data("Apr", 24));
        temperatura.getData().add(new XYChart.Data("May", 34));
        temperatura.getData().add(new XYChart.Data("Jun", 36));
        temperatura.getData().add(new XYChart.Data("Jul", 22));
        temperatura.getData().add(new XYChart.Data("Aug", 45));
        temperatura.getData().add(new XYChart.Data("Sep", 43));
        temperatura.getData().add(new XYChart.Data("Oct", 17));
        temperatura.getData().add(new XYChart.Data("Nov", 29));
        temperatura.getData().add(new XYChart.Data("Dec", 25));
        
        
        umidade.getData().add(new XYChart.Data("Jan", 33));
        umidade.getData().add(new XYChart.Data("Feb", 34));
        umidade.getData().add(new XYChart.Data("Mar", 25));
        umidade.getData().add(new XYChart.Data("Apr", 44));
        umidade.getData().add(new XYChart.Data("May", 39));
        umidade.getData().add(new XYChart.Data("Jun", 16));
        umidade.getData().add(new XYChart.Data("Jul", 55));
        umidade.getData().add(new XYChart.Data("Aug", 54));
        umidade.getData().add(new XYChart.Data("Sep", 48));
        umidade.getData().add(new XYChart.Data("Oct", 27));
        umidade.getData().add(new XYChart.Data("Nov", 37));
        umidade.getData().add(new XYChart.Data("Dec", 29));
        
       
        luminosidade.getData().add(new XYChart.Data("Jan", 44));
        luminosidade.getData().add(new XYChart.Data("Feb", 35));
        luminosidade.getData().add(new XYChart.Data("Mar", 36));
        luminosidade.getData().add(new XYChart.Data("Apr", 33));
        luminosidade.getData().add(new XYChart.Data("May", 31));
        luminosidade.getData().add(new XYChart.Data("Jun", 26));
        luminosidade.getData().add(new XYChart.Data("Jul", 22));
        luminosidade.getData().add(new XYChart.Data("Aug", 25));
        luminosidade.getData().add(new XYChart.Data("Sep", 43));
        luminosidade.getData().add(new XYChart.Data("Oct", 44));
        luminosidade.getData().add(new XYChart.Data("Nov", 45));
        luminosidade.getData().add(new XYChart.Data("Dec", 44));
        
        
        magnetismo.getData().add(new XYChart.Data("Jan", 44));
        magnetismo.getData().add(new XYChart.Data("Feb", 35));
        magnetismo.getData().add(new XYChart.Data("Mar", 36));
        magnetismo.getData().add(new XYChart.Data("Apr", 33));
        magnetismo.getData().add(new XYChart.Data("May", 31));
        magnetismo.getData().add(new XYChart.Data("Jun", 26));
        magnetismo.getData().add(new XYChart.Data("Jul", 22));
        magnetismo.getData().add(new XYChart.Data("Aug", 25));
        magnetismo.getData().add(new XYChart.Data("Sep", 43));
        magnetismo.getData().add(new XYChart.Data("Oct", 44));
        magnetismo.getData().add(new XYChart.Data("Nov", 45));
        magnetismo.getData().add(new XYChart.Data("Dec", 44));
        */
              
        lcGrafico.getData().addAll(temperatura, umidade, luminosidade, magnetismo);
       
       }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        arduino = new Arduino();
        // TODO

        btnIniciarMonitoramento.setOnAction((ActionEvent event) -> {
            arduino.lerPorta();
            System.out.println("Oi estou funcionando");
        });

        btnPararMonitoramento.setOnAction((ActionEvent event) -> {
            arduino.pararDeReceberDados();
        });

        btnSalvarDatabase.setOnAction((ActionEvent event) -> {
            arduino.salvarDadosNoBanco();
        });

        btnNaoSalvarDatabase.setOnAction((ActionEvent event) -> {
            arduino.naoSalvarDadosNoBanco();
        });

        //ATIVA / DESATIVA ALARME DE INCENDIO
        tbAlarmeIncendio.setOnMousePressed((MouseEvent event) -> {
            boolean armado = !tbAlarmeIncendio.isSelected();
            System.out.println(armado);
            System.out.println(event.getEventType());
            if (armado) {
                ligando();
                tbAlarmeIncendio.getStyleClass().clear();
                tbAlarmeIncendio.getStyleClass().add("tb-pressed");
                arduino.ligarAlarmeIncendio();
            } else {
                desligando();
                tbAlarmeIncendio.getStyleClass().clear();
                tbAlarmeIncendio.getStyleClass().add("tb-unpressed");
                arduino.desligarAlarmeIncendio();
            }
        });

        //ATIVA / DESATIVA ALARME DE MAGNETISMO
        tbAlarmeMagnetismo.setOnMousePressed((MouseEvent event) -> {
            boolean armado = !tbAlarmeMagnetismo.isSelected();

            if (armado) {
                ligando();
                tbAlarmeMagnetismo.getStyleClass().clear();
                tbAlarmeMagnetismo.getStyleClass().add("tb-pressed");
                arduino.ligarAlarmeMagnetico();
            } else {
                desligando();
                tbAlarmeMagnetismo.getStyleClass().clear();
                tbAlarmeMagnetismo.getStyleClass().add("tb-unpressed");
                arduino.desligarAlarmeMagnetico();
            }
        });

        //ATIVA / DESATIVA AQUECEDOR
        tbAquecedor.setOnMousePressed((MouseEvent event) -> {
            boolean armado = !tbAquecedor.isSelected();

            if (armado) {
                ligando();
                tbAquecedor.getStyleClass().clear();
                tbAquecedor.getStyleClass().add("tb-pressed");
                arduino.ligarAquecedor();
            } else {
                desligando();
                tbAquecedor.getStyleClass().clear();
                tbAquecedor.getStyleClass().add("tb-unpressed");
                arduino.desligarAquecedor();
            }
        });

        //ATIVA / DESATIVA ARCONDICIONADO
        tbArcondicionado.setOnMousePressed((MouseEvent event) -> {
            boolean armado = !tbArcondicionado.isSelected();

            if (armado) {
                ligando();
                tbArcondicionado.getStyleClass().clear();
                tbArcondicionado.getStyleClass().add("tb-pressed");
                arduino.ligarArcondicionado();
            } else {
                desligando();
                tbArcondicionado.getStyleClass().clear();
                tbArcondicionado.getStyleClass().add("tb-unpressed");
                arduino.desligarArcondicionado();
            }
        });
        gerarGrafico();
        setStyle();
        iniciaTela();
    }
    
    
    private void setStyle(){
        tbArcondicionado.getStyleClass().add("tb-unpressed");
        tbAlarmeIncendio.getStyleClass().add("tb-unpressed");
        tbAlarmeMagnetismo.getStyleClass().add("tb-unpressed");
        tbAquecedor.getStyleClass().add("tb-unpressed");
        
        btnLuminosidade.getStyleClass().add("tb-unpressed");
        btnMagnetismo.getStyleClass().add("tb-unpressed");
        btnTemperatura.getStyleClass().add("tb-unpressed");
        btnUmidade.getStyleClass().add("tb-unpressed");
        
        lblTemperatura.getStyleClass().add("label-info");
        lblMagnetismo.getStyleClass().add("label-info");
        lblLuminosidade.getStyleClass().add("label-info");
        lblUmidade.getStyleClass().add("label-info");
    }

    void iniciaTela() {
        Task t = new Task() {

            @Override
            protected Object call() throws Exception {

                while (true) {

                    Platform.runLater(() -> {
                        try {
                            dados = UltimosDados.getInstance().getDados();
                            if (dados.size() > 0) {
                                d = dados.get(0);
                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        if (!dados.isEmpty()) {
                            gerarGrafico();
                            lblLuminosidade.setText("" + d.getLuminosidade());
                            lblMagnetismo.setText("" + d.getMagnetismo());
                            lblTemperatura.setText("" + d.getTemperatura());
                            lblUmidade.setText("" + d.getUmidade());
                        }

                    });

                    Thread.sleep(500);
                }
            }
        };

        new Thread(t).start();
    }

    public void ligando() {
        System.out.println("Ligando");
    }

    public void desligando() {
        System.out.println("Desligando");
    }

    public void stopUpdate() {
        if (t.isAlive()) {
            t.interrupt();
        }
    }

    //metodo de atulização
    class UpdateT implements Runnable {

        @Override
        public void run() {
            while (true) {
                dados = UltimosDados.getInstance().getDados();
                System.out.println("Tamanho da lista >> " + dados.size());

                if (dados.size() > 1) {
                    System.out.println("Tamanho da lista >> " + dados.size());
                    String s = String.valueOf(dados.get(0).getLuminosidade());
                    lblLuminosidade.setText(s);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MonitorArduinoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public static void atualizarTela() {

    }

}
