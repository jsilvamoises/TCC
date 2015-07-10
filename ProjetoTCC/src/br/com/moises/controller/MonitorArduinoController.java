/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moises.controller;

import arduino.Arduino;
import arduino.JavaSerialPort;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Dado;
import model.table.DadoTable;
import util.UltimosDados;

/**
 * FXML Controller class
 *
 * @author MOISES
 */
public class MonitorArduinoController implements Initializable, EventHandler<KeyEvent> {

    SimpleDateFormat horaFormatada = new SimpleDateFormat("HH:mm:ss");
    private Arduino arduino;
    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Pane paneCommand;
    //BOTÕES
    @FXML
    private Button btnIniciarMonitoramento;
    @FXML
    private Button btnPararMonitoramento;
    @FXML
    private Button btnSalvarDatabase;
//    @FXML
//    private Button btnNaoSalvarDatabase;
    @FXML
    private Button btnLuminosidade;
    @FXML
    private Button btnMagnetismo;
    @FXML
    private Button btnTemperatura;
    @FXML
    private Button btProcessarAutomatico;
    @FXML
    private Button btnUmidade;
    // TOGGLEBUTTON
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

    //VARIAVEIS DA TABELA
    @FXML
    private TableView<DadoTable> tvUltimosDados;
    @FXML
    private TableColumn tcAlarmeIncendio;
    @FXML
    private TableColumn tcAlarmeMagnetismo;
    @FXML
    private TableColumn tcAquecedor;
    @FXML
    private TableColumn tcArCondicionado;
    @FXML
    private TableColumn tcIdentificador;
    @FXML
    private TableColumn tcLuminosidade;
    @FXML
    private TableColumn tcMagnetismo;
    @FXML
    private TableColumn tcTemperatura;
    @FXML
    private TableColumn tcUmidade;

    @FXML
    private TableColumn<?, ?> tcStatusGeral;
    boolean estaColetandoAmostras;
    TableRow<DadoTable> row = new TableRow<>();

    ObservableList<DadoTable> lista = FXCollections.observableArrayList();
    boolean estaProcessandoAutomatico;
    Dado d;
    @FXML
    private LineChart<String, Number> lcGrafico;
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
    private void gerarGrafico() {
        if (dados.size() > 0) {
            lcGrafico.getData().clear();

            xAxis.setLabel("Sensores");

            lcGrafico.setTitle("Monitoramento de ambiente");

            XYChart.Series temperatura = new XYChart.Series();
            temperatura.setName("Temperatura");

            XYChart.Series umidade = new XYChart.Series();
            umidade.setName("Umidade");

            XYChart.Series luminosidade = new XYChart.Series();
            luminosidade.setName("Luminosidade");

            XYChart.Series magnetismo = new XYChart.Series();
            magnetismo.setName("Magnetismo");
            try {
                dados.stream().forEach((dd) -> {
                    try {
                        temperatura.getData().add(new XYChart.Data(horaFormatada.format(dd.getDataColeta()), dd.getTemperatura() / 100));
                        umidade.getData().add(new XYChart.Data(horaFormatada.format(dd.getDataColeta()), dd.getUmidade() / 100));
                        luminosidade.getData().add(new XYChart.Data(horaFormatada.format(dd.getDataColeta()), dd.getLuminosidade() / 100));
                        magnetismo.getData().add(new XYChart.Data(horaFormatada.format(dd.getDataColeta()), dd.getMagnetismo() / 100));
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                });
            } catch (Exception e) {
                System.out.println(e);
            }

            lcGrafico.getData().addAll(temperatura, umidade, luminosidade, magnetismo);

        }
    }

    private void configuraTabela() {
        tcIdentificador.setCellValueFactory(new PropertyValueFactory("identificador"));
        tcTemperatura.setCellValueFactory(new PropertyValueFactory("temperatura"));
        tcUmidade.setCellValueFactory(new PropertyValueFactory("umidade"));
        tcLuminosidade.setCellValueFactory(new PropertyValueFactory("luminosidade"));
        tcMagnetismo.setCellValueFactory(new PropertyValueFactory("magnetismo"));
        tcAlarmeMagnetismo.setCellValueFactory(new PropertyValueFactory("statusAlarmeMagnetismo"));
        tcAlarmeIncendio.setCellValueFactory(new PropertyValueFactory("statusAlarmeIncendio"));
        tcAquecedor.setCellValueFactory(new PropertyValueFactory("statusAquecedor"));
        tcArCondicionado.setCellValueFactory(new PropertyValueFactory("statusArCondidiconado"));
        tcStatusGeral.setCellValueFactory(new PropertyValueFactory("statusGeral"));

        row.getStyleClass().add("tb-pressed");

    }

    private void preencherTabela() {
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
                            //Verifica se existe um item na tabela e ativa a cor de botão pressionado
                            if (dados.size() == 1) {
                                ativarToggleButton(d);
                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        if (!dados.isEmpty()) {

                            lista.clear();
                            try {
                                dados.stream().forEach((dado) -> {
                                    //row.getStyleClass().add("tb-pressed");

                                    lista.add(new DadoTable(dado.getIdentificador(), dado.getTemperatura(), dado.getUmidade(), dado.getLuminosidade(), dado.getMagnetismo(), dado.getStatusAlarmeMagnestismo(), dado.getStatusAlarmeIncencio(), dado.getStatusAquecedor(), dado.getStatusArcondicionado(), dado.getStatusGeral()));
                                });
                                tvUltimosDados.setItems(lista);

                            } catch (Exception e) {
                                System.out.println(e);
                            }

                        }

                    });

                    Thread.sleep(1000);
                }
            }
        };

        new Thread(t).start();

        //============
    }

    private void desabilitarBotoes() {
        btnIniciarMonitoramento.setDisable(false);
        btnPararMonitoramento.setDisable(true);
        btnSalvarDatabase.setDisable(true);
        btProcessarAutomatico.setDisable(true);        
        //DESAHABILITA OS TOGGLEBUTTONS
        tbAlarmeIncendio.setDisable(true);
        tbAlarmeMagnetismo.setDisable(true);
        tbAquecedor.setDisable(true);
        tbArcondicionado.setDisable(true);
    }

    private void habilitarBotoes() {
        btnIniciarMonitoramento.setDisable(true);
        btnPararMonitoramento.setDisable(false);
        btnSalvarDatabase.setDisable(false);
        btProcessarAutomatico.setDisable(false);
        //HABILITA OS TOGGLEBUTTONS
        tbAlarmeIncendio.setDisable(false);
        tbAlarmeMagnetismo.setDisable(false);
        tbAquecedor.setDisable(false);
        tbArcondicionado.setDisable(false);
    }

    private void ativarToggleButton(Dado d) {
        if (d.getStatusAlarmeIncencio() == 1) {
            tbAlarmeIncendio.getStyleClass().clear();
            tbAlarmeIncendio.getStyleClass().add("tb-pressed");
            tbAlarmeIncendio.setSelected(true);

        }
        if (d.getStatusAlarmeMagnestismo() == 1) {
            tbAlarmeMagnetismo.getStyleClass().clear();
            tbAlarmeMagnetismo.getStyleClass().add("tb-pressed");
            tbAlarmeMagnetismo.setSelected(true);

        }
        if (d.getStatusAquecedor() == 1) {
            tbAquecedor.getStyleClass().clear();
            tbAquecedor.getStyleClass().add("tb-pressed");
            tbAquecedor.setSelected(true);

        }
        if (d.getStatusArcondicionado() == 1) {
            tbArcondicionado.getStyleClass().clear();
            tbArcondicionado.getStyleClass().add("tb-pressed");
            tbArcondicionado.setSelected(true);
        }
    }

    private boolean lerPortasArduino() {

        List<String> choises = JavaSerialPort.getInstance().getPortas();
        if (!choises.isEmpty()) {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choises.get(0), choises);

            dialog.setTitle("Seleção de portas disponíveis");
            dialog.setHeaderText("Portas encontrada!!!");
            dialog.setContentText("Selecione a porta desejada!!!");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                arduino = new Arduino(result.get());
                arduino.lerPorta();
            }
            return true;
        } else {
            desabilitarBotoes();
            new Alert(Alert.AlertType.INFORMATION, "Não foi possível detectar uma dispositivo com comunição serial puglado a esse PC!!", ButtonType.OK).show();
            return false;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnIniciarMonitoramento.setOnAction((ActionEvent event) -> {
            habilitarBotoes();
            lerPortasArduino();
        });

        btnPararMonitoramento.setOnAction((ActionEvent event) -> {
            dados.clear();
            estaColetandoAmostras = false;
            estaProcessandoAutomatico = false;
            desabilitarBotoes();
            arduino.pararDeReceberDados();
        });

        btnSalvarDatabase.setOnAction((ActionEvent event) -> {
            //Colocar um validador se realmente deseja para o monitoramento automatico

            if (!estaColetandoAmostras) {
                if (estaProcessandoAutomatico) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmação de parada de processo!!!!");
                    alert.setHeaderText("Outro processo está em execução deseja parar?");
                    alert.setContentText("Realmente deseja parar o processamento\n automático?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        estaColetandoAmostras = true;
                        arduino.salvarDadosNoBanco();
                        btnSalvarDatabase.setText("Coletando dados");
                        estaProcessandoAutomatico = false;
                    }
                } else {
                    estaColetandoAmostras = true;
                    arduino.salvarDadosNoBanco();
                    btnSalvarDatabase.setText("Coletando dados");
                }

            } else {
                estaColetandoAmostras = false;
                arduino.naoSalvarDadosNoBanco();
                btnSalvarDatabase.setText("Parado");
            }
//            estaColetandoAmostras = true;  //Flag de verificação se as amostras estãos sendo salvas no banco
//            btnNaoSalvarDatabase.setDisable(false);
//            btnSalvarDatabase.setDisable(true);
//            arduino.salvarDadosNoBanco();
        });

//        btnNaoSalvarDatabase.setOnAction((ActionEvent event) -> {
//            estaColetandoAmostras = false; //Flag de verificação se as amostras estãos sendo salvas no banco
//            btnNaoSalvarDatabase.setDisable(true);
//            btnSalvarDatabase.setDisable(false);
//            arduino.naoSalvarDadosNoBanco();
//        });
        /**
         * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
         * @ ADICIONA LISTNER PARA OS TOGGLEBUTTONS @
         * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
         */
        tbAquecedor.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F1) {
                    System.out.println("KkkkkKKkKkKKkKKKkkKK");
                }
            }
        });
        //ATIVA / DESATIVA ALARME DE INCENDIO
        tbAlarmeIncendio.setOnMousePressed((MouseEvent event) -> {
            boolean armado = !tbAlarmeIncendio.isSelected();
            System.out.println(armado);
            if (armado) {

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
        /**
         * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
         * @ BOTÃO DE ATIVAÇÃO AUTOMATICO/MANUAL @
         * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
         */
        btProcessarAutomatico.setOnAction((ActionEvent event) -> {
            //VERIFICA SE ESTA SENDO SALVO NO BANCO E CANCELA A OPÇÃO CASO O USUARIO QUEIRA
            if (!estaProcessandoAutomatico) {
                if (estaColetandoAmostras) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmação de parada de processo!!!!");
                    alert.setHeaderText("Outro processo está em execução deseja parar?");
                    alert.setContentText("Realmente deseja parar o processamento\n automático?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        estaProcessandoAutomatico = true;
                        arduino.naoSalvarDadosNoBanco();
                        btProcessarAutomatico.setText("AUTOMATICO");
                        estaColetandoAmostras = false;
                        System.out.println("Processando automaticamente");
                    }
                } else {
                    estaProcessandoAutomatico = true;
                    btProcessarAutomatico.setText("AUTOMATICO");
                    System.out.println("Processando automaticamente");
                }

            } else {
                estaProcessandoAutomatico = false;
                btProcessarAutomatico.setText("Parado");
                System.out.println("Processando automaticamente parado");
            }

        });
        desabilitarBotoes();//Desabilita todos os botões que não podem ser usados quando não está conectado;
        configuraTabela(); //Configura a tabela de dados     
        setStyle(); // Seta estilos aos botões
        iniciaTela(); // Começa atualizar a tela via thread
        preencherTabela(); // Inicia a thread que vai preencher a tabela
        piscaLabel();//pisca label

    }

    //ACIONA O BOTAO ALARME INCEDIO
    private void acionarTBAlarmeIncendio() {
        boolean armado = !tbAlarmeIncendio.isSelected();
        System.out.println(armado);
        if (armado) {

            tbAlarmeIncendio.getStyleClass().clear();
            tbAlarmeIncendio.getStyleClass().add("tb-pressed");
            arduino.ligarAlarmeIncendio();
        } else {
            desligando();
            tbAlarmeIncendio.getStyleClass().clear();
            tbAlarmeIncendio.getStyleClass().add("tb-unpressed");
            arduino.desligarAlarmeIncendio();
        }
    }

    private void acionarTBAlarmeMagnetismo() {
        boolean armado = !tbAlarmeMagnetismo.isSelected();

        if (armado) {

            tbAlarmeMagnetismo.getStyleClass().clear();
            tbAlarmeMagnetismo.getStyleClass().add("tb-pressed");
            arduino.ligarAlarmeMagnetico();
        } else {
            desligando();
            tbAlarmeMagnetismo.getStyleClass().clear();
            tbAlarmeMagnetismo.getStyleClass().add("tb-unpressed");
            arduino.desligarAlarmeMagnetico();
        }
    }

    private void acionarTBAquecedor() {
        boolean armado = !tbAquecedor.isSelected();
        if (armado) {

            tbAquecedor.getStyleClass().clear();
            tbAquecedor.getStyleClass().add("tb-pressed");
            arduino.ligarAquecedor();
        } else {
            desligando();
            tbAquecedor.getStyleClass().clear();
            tbAquecedor.getStyleClass().add("tb-unpressed");
            arduino.desligarAquecedor();
        }
    }

    private void acionarTBArCondicionado() {
        boolean armado = !tbArcondicionado.isSelected();
        if (armado) {

            tbArcondicionado.getStyleClass().clear();
            tbArcondicionado.getStyleClass().add("tb-pressed");
            arduino.ligarArcondicionado();
        } else {
            desligando();
            tbArcondicionado.getStyleClass().clear();
            tbArcondicionado.getStyleClass().add("tb-unpressed");
            arduino.desligarArcondicionado();
        }

    }

    private void setStyle() {
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
                                ativarToggleButton(d);
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

                    Thread.sleep(1000);
                }
            }
        };

        new Thread(t).start();
    }

    public void verificaPrimeiroDadoVindoDoBando() {
        boolean size = true;
        Task t = new Task() {

            @Override
            protected Object call() throws Exception {

                while (isTrue()) {//*

                    Platform.runLater(() -> {

//                        try {
//                            dados = UltimosDados.getInstance().getDados();
//                            if (dados.size() > 0) {
//                                d = dados.get(0);
//                                ativarToggleButton(d);
//                            }
//
//                        } catch (Exception e) {
//                            System.out.println(e);
//                        }
//
//                        if (!dados.isEmpty()) {
//                            gerarGrafico();
//
//                            lblLuminosidade.setText("" + d.getLuminosidade());
//                            lblMagnetismo.setText("" + d.getMagnetismo());
//                            lblTemperatura.setText("" + d.getTemperatura());
//                            lblUmidade.setText("" + d.getUmidade());
//                        }
                    });

                    Thread.sleep(1000);
                }//*
                return null;
            }
        };

        new Thread(t).start();
    }

    public boolean isTrue() {

        return dados.isEmpty();
    }

    public void desligando() {
        System.out.println("Desligando");
    }

    @Override
    public void handle(KeyEvent event) {
        System.out.println("HHHHHH");
        if (event.getCode() == KeyCode.F10) {
            System.out.println("KKKK pressionou A");
        }
    }

    void piscaLabel() {
        Task t = new Task() {

            @Override
            protected Object call() throws Exception {
                boolean statusLabel = false;
                while (true) {

                    Platform.runLater(() -> {
                        //EXECUTA OPERAÇÕES CASO O PROCESSAMENTO ESTEJA NO MODO AUTOMATICO
                        if (estaProcessandoAutomatico) {
                            if (btProcessarAutomatico.getStyleClass().contains("automatico")) {

                                btProcessarAutomatico.getStyleClass().remove("automatico");
                                btProcessarAutomatico.getStyleClass().add("manual");
                            } else {
                                btProcessarAutomatico.getStyleClass().remove("manual");
                                btProcessarAutomatico.getStyleClass().add("automatico");
                            }
                        } else {
                            btProcessarAutomatico.getStyleClass().remove("automatico");
                            btProcessarAutomatico.getStyleClass().remove("manual");

                        }
                        //EXECUTA OPERAÇOES CASO ESTEJA SENDO FEITO COLETA DE AMOSTRAS
                        if (estaColetandoAmostras) {
                            if (btnSalvarDatabase.getStyleClass().contains("automatico")) {

                                btnSalvarDatabase.getStyleClass().remove("automatico");
                                btnSalvarDatabase.getStyleClass().add("manual");
                            } else {

                                btnSalvarDatabase.getStyleClass().remove("manual");
                                btnSalvarDatabase.getStyleClass().add("automatico");
                            }
                        } else {
                            btnSalvarDatabase.getStyleClass().remove("automatico");
                            btnSalvarDatabase.getStyleClass().remove("manual");

                        }

                    });

                    Thread.sleep(500);
                }
            }
        };

        new Thread(t).start();
    }

}
