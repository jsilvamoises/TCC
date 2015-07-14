/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moises.controller;

import resources.background.IBackGround;

import arduino.Arduino;
import arduino.JavaSerialPort;
import componentes.lcd.ComponentsUtil;
import componentes.lcd.IClock;
import componentes.lcd.ITButton;
import enums.ButtonsStat;
import eu.hansolo.enzo.lcd.Lcd;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import componentes.lcd.LCD;
import eu.hansolo.enzo.experimental.tbutton.TButton;
import eu.hansolo.enzo.lcd.LcdClock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import model.Dado;
import model.table.DadoTable;
import util.SystemInfo;
import util.UltimosDados;

/*
 * FXML Controller class
 *
 * @author MOISES
 */
public class MonitorArduinoController implements Initializable {

    SimpleDateFormat horaFormatada = new SimpleDateFormat("HH:mm:ss");
    private Arduino arduino;
    @FXML
    private Label lblRelogio;
    @FXML
    private ToolBar tbCabecalho;

    //BOTÕES    
    @FXML
    private Button btnAquecedor,btnArcondicionado;
//    @FXML
//    private Button btnArcondicionado;
    @FXML
    private Button btnAlarmeMagnetismo;
    @FXML
    private Button btnAlarmeIncendio;
    @FXML
    private Button btnIniciarLeituraDePorta;
    @FXML
    private Button btnPararLeituraDePorta;
    @FXML
    private Button btnSalvarDatabase;
    @FXML
    private Button btnLuminosidade;
    @FXML
    private Button btnMagnetismo;
    @FXML
    private Button btnTemperatura;
    @FXML
    private Button btnProcessarAutomatico;
    @FXML
    private Button btnUmidade;
    //togglebuttons enzo

    @FXML
    private Label lblInfoProgressMemoria;
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
    TableRow<DadoTable> row = new TableRow<>();
    ObservableList<DadoTable> lista = FXCollections.observableArrayList();
    //VARIAVEIS BOOLEANAS
    boolean estaProcessandoAutomatico;
    boolean estaOnColetandoAmostras;
    boolean estaOnAlterarEstilo;
    boolean estaOnAquecedor;
    boolean estaOnArCondicionado;
    boolean estaOnAlarmeIncendio;
    boolean estaOnAlarmeMagnetismo;
    boolean estaOnEstiloBotoesLateral;
    Dado d;
    @FXML
    private LineChart<String, Number> lcGrafico;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ProgressBar progressMemoria;
    @FXML
    private VBox vBoxInfo;
    @FXML
    private VBox vBoxLeft;
    @FXML
    GridPane gridPane;
    //COMPONENTES ENZO
    private Lcd lcdTemperatura;
    private Lcd lcdUmidade;
    private Lcd lcdLuminosidade;
    private Lcd lcdMagnetismo;
    @FXML
    Pane paneCommand;
    //TOGGLEBUTTON ENZO
    private TButton tbArcondicionado;
    private TButton tbAquecedor;
    private TButton tbAlarmMagnetismo;
    private TButton tbAlarmeIncendio;
    private LcdClock clock;
    private List<Dado> dados = new ArrayList<>();

    private Background backDefaut = IBackGround.BACKGROUND_WHITE;


    /*
    ############################################################################
    #############     CRIA OS LED DA LATERAL DIREITA      ######################
    ############################################################################
    */
    private void criarLCDS() {
        lcdTemperatura = LCD.getInstance().getLctType(LCD.LCDType.LCD_TEMPERATURA);
        lcdMagnetismo = LCD.getInstance().getLctType(LCD.LCDType.LCD_MAGNETISMO);
        lcdLuminosidade = LCD.getInstance().getLctType(LCD.LCDType.LCD_LUMINOSIDADE);
        lcdUmidade = LCD.getInstance().getLctType(LCD.LCDType.LCD_UNIDADE);
        vBoxInfo.getChildren().addAll(lcdTemperatura, lcdUmidade, lcdLuminosidade, lcdMagnetismo);
    }
    
    private void criarClock(){
        clock = IClock.getInstance().getActiveClock(250,57);
        //lblRelogio.getChildrenUnmodifiable().add(clock);
        tbCabecalho.getItems().add(0, clock);
    }

    private void criarLateralCommandButtons() {
        tbArcondicionado = ITButton.getInstance().getButton("OFF", Color.AQUAMARINE);
        tbAquecedor = ITButton.getInstance().getButton("OFF", Color.AQUAMARINE);
        tbAlarmMagnetismo = ITButton.getInstance().getButton("OFF", Color.AQUAMARINE);
        tbAlarmeIncendio = ITButton.getInstance().getButton("OFF", Color.AQUAMARINE);
        //======================ADICIONA O LISTENERS DOS EVENTOS ===============
        //ALARME MAGNETISMO
        tbAlarmMagnetismo.setOnDeselect((TButton.SelectEvent event) -> {
            desativarAlarmeMagnetismo();
            salvarInformacaoDeComandos();
        });
        tbAlarmMagnetismo.setOnSelect((TButton.SelectEvent event) -> {
            ativarAlarmeMagnetismo();
            salvarInformacaoDeComandos();
        });
        //ALARME INCENDIO
        tbAlarmeIncendio.setOnDeselect((TButton.SelectEvent event) -> {
            desativarAlarmeIncendio();
            salvarInformacaoDeComandos();
        });
        tbAlarmeIncendio.setOnSelect((TButton.SelectEvent event) -> {
            ativarAlarmeIncencio();
            salvarInformacaoDeComandos();
        });
        //AR CONDICIONADO
        tbArcondicionado.setOnSelect((TButton.SelectEvent event) -> {
            ativarArCondicionado();
            salvarInformacaoDeComandos();
        });
        tbArcondicionado.setOnDeselect((TButton.SelectEvent event) -> {
            desativarArCondicionado();
            salvarInformacaoDeComandos();
        });
        //AQUECEDOR
        tbAquecedor.setOnDeselect((TButton.SelectEvent event) -> {
            desativarAquecedor();
            salvarInformacaoDeComandos();
        });
        tbAquecedor.setOnSelect((TButton.SelectEvent event) -> {
            ativarAquecedor();
            salvarInformacaoDeComandos();
        });
        //TEXTO DE INDICAÇÃO
        //======================================================================
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        //======================================================================
        Text arCondicionado = new Text("Arcondicionado");
        arCondicionado.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
        arCondicionado.setFill(Color.RED);
        arCondicionado.setEffect(r);
        //======================================================================
        Text Aquecedor = new Text("Aquecedor");
        Aquecedor.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
        Aquecedor.setFill(Color.RED);
        Aquecedor.setEffect(r);
        //======================================================================
        Text alarmMagnetismo = new Text("Magnetismo");
        alarmMagnetismo.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
        alarmMagnetismo.setFill(Color.RED);
        alarmMagnetismo.setEffect(r);
        //======================================================================
        Text AlarmeIncendio = new Text("Incêndio");
        AlarmeIncendio.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
        AlarmeIncendio.setFill(Color.RED);
        AlarmeIncendio.setEffect(r);
        //======================================================================
        gridPane.add(arCondicionado, 1, 0);
        gridPane.add(tbArcondicionado, 2, 0);

        gridPane.add(Aquecedor, 1, 1);
        gridPane.add(tbAquecedor, 2, 1);

        gridPane.add(alarmMagnetismo, 1, 2);
        gridPane.add(tbAlarmMagnetismo, 2, 2);

        gridPane.add(AlarmeIncendio, 1, 3);
        gridPane.add(tbAlarmeIncendio, 2, 3);

    }

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

        row.getStyleClass().add("on");

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
                                ativarBotoesComUltimoStatus(d);
                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        if (!dados.isEmpty()) {

                            lista.clear();
                            try {
                                dados.stream().forEach((dado) -> {
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
        //DESABILITA BOTOTÕES
        Button botoes[] = {btnPararLeituraDePorta, btnSalvarDatabase, btnProcessarAutomatico,
            btnAquecedor, btnArcondicionado, btnAlarmeIncendio, btnAlarmeMagnetismo};
        TButton tbuttons[] = {tbAlarmMagnetismo, tbAlarmeIncendio, tbAquecedor, tbArcondicionado};
        ComponentsUtil.getInstance().desabilitarBotoesControleManual(tbuttons);
        ComponentsUtil.getInstance().desabilitarBotoesControleManual(botoes);
        //RESETA AS VARIAVEIS DE COMANDOS
        estaOnColetandoAmostras = false;
        estaOnAlarmeIncendio = false;
        estaOnAquecedor = false;
        estaOnAlarmeMagnetismo = false;
        btnIniciarLeituraDePorta.setDisable(false);
    }

    private void habilitarBotoes() {
        //HABILITA OS BOTÕES
        TButton tbuttons[] = {tbAlarmMagnetismo, tbAlarmeIncendio, tbAquecedor, tbArcondicionado};
        Button botoes[] = {btnIniciarLeituraDePorta, btnPararLeituraDePorta, btnSalvarDatabase, btnProcessarAutomatico,
            btnAquecedor, btnArcondicionado, btnAlarmeIncendio, btnAlarmeMagnetismo};
        ComponentsUtil.getInstance().habilitarBotoesControleManual(tbuttons);
        ComponentsUtil.getInstance().habilitarBotoesControleManual(botoes);
        btnIniciarLeituraDePorta.setDisable(true);
    }

    private void ativarBotoesComUltimoStatus(Dado d) {
        //ATIVA BOÃO AQUECEDOR COM O ULTIMO ESTADO ENCONTRADO
        tbAquecedor.setSelected(d.getStatusAquecedor() == 1);
        estaOnAquecedor = d.getStatusAquecedor() == 1;

        tbArcondicionado.setSelected(d.getStatusArcondicionado() == 1);
        estaOnArCondicionado = d.getStatusArcondicionado() == 1;

        tbAlarmMagnetismo.setSelected(d.getStatusAlarmeMagnestismo() == 1);
        estaOnAlarmeMagnetismo = d.getStatusAlarmeMagnestismo() == 1;

        tbAlarmeIncendio.setSelected(d.getStatusAlarmeIncencio() == 1);
        estaOnAlarmeIncendio = d.getStatusAlarmeIncencio() == 1;
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

        btnIniciarLeituraDePorta.setOnAction((ActionEvent event) -> {
            iniciarLeituraDePorta();
        });

        btnPararLeituraDePorta.setOnAction((ActionEvent event) -> {
            pararLeituraDePorta();
        });

        btnSalvarDatabase.setOnAction((ActionEvent event) -> {
            if (estaOnColetandoAmostras) {
                pararDeSalvarDados();
            } else {
                if (estaProcessandoAutomatico) {
                    if (retorno()) {
                        iniciarSalvarNoBanco();
                    }
                } else {
                    iniciarSalvarNoBanco();
                }
            }
        });

        /*
         * #####################################################################
         * ############# EVENTO BOTÃO DE ATIVAÇÃO AUTOMATICO/MANUAL ############             
         * #####################################################################
         */
        btnProcessarAutomatico.setOnAction((ActionEvent event) -> {
            //VERIFICA SE ESTA SENDO SALVO NO BANCO E CANCELA A OPÇÃO CASO O USUARIO QUEIRA
            if (estaProcessandoAutomatico) {
                pararProcessamentoAutomatico();
                Button ativar[] = {btnAlarmeIncendio, btnAlarmeMagnetismo, btnAquecedor, btnArcondicionado};
                ComponentsUtil.getInstance().habilitarBotoesControleManual(ativar);
            } else {
                if (estaOnColetandoAmostras) {
                    if (retorno()) {
                        iniciarProcessamentoAutomatico();
                    }
                } else {
                    iniciarProcessamentoAutomatico();
                }
            }
        });
        //EVENTO DO BOTÃO DE LIGAR / DESLIGAR O AQUECEDOR
        btnAquecedor.setOnAction((ActionEvent event) -> {
            if (estaOnAquecedor) {
                desativarAquecedor();
            } else {
                ativarAquecedor();
            }
            System.out.println("Estado do aquecedor" + estaOnAquecedor);
        });
        //EVENTO DO BOTÃO LIGAR / DESLIGAR AR CONDICIONADO
        btnArcondicionado.setOnAction((ActionEvent event) -> {
            if (estaOnArCondicionado) {
                desativarArCondicionado();
            } else {
                ativarArCondicionado();
            }
        });
        //EVENTO ATIVA / DESATIVA ALARME DE MAGNETISMO
        btnAlarmeMagnetismo.setOnAction((ActionEvent event) -> {
            if (estaOnAlarmeMagnetismo) {
                desativarAlarmeMagnetismo();
            } else {
                ativarAlarmeMagnetismo();
            }
        });
        //EVENTO ATIVA / DESATIVA ALARME DE INCENDIO
        btnAlarmeIncendio.setOnAction((ActionEvent event) -> {
            if (estaOnAlarmeIncendio) {
                desativarAlarmeIncendio();
            } else {
                ativarAlarmeIncencio();
            }
        });
        criarClock();
        criarLCDS();// Cria os led de temperatura / umidade / magnetismo / luminosidade
        criarLateralCommandButtons(); // Cria os botões laterais esquerdo
        desabilitarBotoes();//Desabilita todos os botões que não podem ser usados quando não está conectado;
        configuraTabela(); //Configura a tabela de dados     
        setStyle(); // Seta estilos aos botões
        iniciaTela(); // Começa atualizar a tela via thread
        preencherTabela(); // Inicia a thread que vai preencher a tabela        
        threadGerenciarStatusBotoes();

    }
    /*
     ############################################################################
     #### DEFINE O VALOR PADRÃO DE INICIALIZAÇÃO DOS TEXTOS E BACK DOS BOTÕES ###
     ############################################################################
     */

    private void setStyle() {
        //BOTÕES LATERAL ESQUERDA
        setStatBtnAquecedor(ButtonsStat.OFF);
        setStatBtnArCondicionado(ButtonsStat.OFF);
        setStatBtnAlarmeMagnetismo(ButtonsStat.OFF);
        setStatBtnAlarmeIncendio(ButtonsStat.OFF);
        //BOTOES SUPERIORES
        setStatBtnSalvarDatabase(ButtonsStat.OFF);
        setStatBtnProcessarAutomatico(ButtonsStat.OFF);

    }

    void iniciaTela() {
        Task t = new Task() {

            @Override
            protected Object call() throws Exception {
                while (true) {
                    Platform.runLater(() -> {
                        try {
                            dados = UltimosDados.getInstance().getDados();
                            if (dados.size() == 1) {
                                d = dados.get(0);
                                ativarBotoesComUltimoStatus(d);
                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        if (!dados.isEmpty()) {
                            gerarGrafico();

                            atualizarValoresLcds();
                        }
                    });
                    Thread.sleep(500);
                }
            }
        };

        new Thread(t).start();
    }

    /*
     * #########################################################################
     * ############### THREAD QUE ALTERA O BACKGROUD DOS BOTOES ################
     * #########################################################################
     */
    void threadGerenciarStatusBotoes() {
        Task t = new Task() {

            @Override
            protected Object call() throws Exception {

                while (true) {

                    Platform.runLater(() -> {
                        gerenciarBotoesAtivos();
                    });

                    Thread.sleep(500);
                }
            }
        };

        new Thread(t).start();
    }

    /*
     * #########################################################################
     * ############ VERIFICAÇÃO DE BOTÕES PARA ALTERAÇÃO DE STATUS #############
     * #########################################################################
     */
    private void piscarBotoes() {
        //EXECUTA OPERAÇÕES CASO O PROCESSAMENTO ESTEJA NO MODO AUTOMATICO
        if (estaProcessandoAutomatico) {
            alterarBackgraund(btnProcessarAutomatico, IBackGround.BACKGROUND_WHITE, IBackGround.BACKGROUND_DARKGREEN);
        }
        //EXECUTA OPERAÇOES CASO ESTEJA SENDO FEITO COLETA DE AMOSTRAS
        if (estaOnColetandoAmostras) {
            alterarBackgraund(btnSalvarDatabase, IBackGround.BACKGROUND_WHITE, IBackGround.BACKGROUND_DARKGREEN);
        }
        //PISCA O BOTÃO BTN AQUECEDOR CASO ESTEJA ESTEJA LIGADO
        if (estaOnAquecedor) {
            alterarBackgraund(btnAquecedor, IBackGround.BACKGROUND_WHITE, IBackGround.BACKGROUND_DARKGREEN);
        }
        // PISCA O BOTÃO BTN ALARME INCENCIO CASO ESTEJA ON
        if (estaOnAlarmeIncendio) {
            alterarBackgraund(btnAlarmeIncendio, IBackGround.BACKGROUND_WHITE, IBackGround.BACKGROUND_DARKGREEN);
        }
        // PISCA O BOTÃO BTN ARCONDICIONADO CASO ESTAJA LIGADO
        if (estaOnArCondicionado) {
            alterarBackgraund(btnArcondicionado, IBackGround.BACKGROUND_WHITE, IBackGround.BACKGROUND_DARKGREEN);
        }
        // PISCA O BOTÃO BTN ALARME MAGNETISMO CASO ESTEJA ON
        if (estaOnAlarmeMagnetismo) {
            alterarBackgraund(btnAlarmeMagnetismo, IBackGround.BACKGROUND_WHITE, IBackGround.BACKGROUND_DARKGREEN);
        }
    }

    /*
     * #########################################################################
     * #################### ALTERA O BACKGROUND DOS BOTÕES #####################
     * #########################################################################
     */
    private void alterarBackgraund(Button botão, Background b1, Background b2) {
        if (botão.getBackground().equals(b1)) {
            botão.setBackground(b2);
        } else {
            botão.setBackground(b1);
        }

    }
    /*
     * #########################################################################
     * ################# VALIDA SE PODE CANCELAR OUTRO PROCESSO ################
     * #########################################################################
     */
    public boolean retorno() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de parada de processo!!!!");
        alert.setHeaderText("Outro processo está em execução deseja parar?");
        alert.setContentText("Essa ação encerrará outros processos caso estejam"
                + " sendo executado, "
                + "\nrealmente deseja parar o outro processo?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
    /*
     * #########################################################################
     * ########################## AÇÕES DOS BOTÕES #############################
     * =========================================================================
     * #########################################################################
     * ####################### PARA DE SALVAR NO BANCO #########################
     * #########################################################################
     */
    private void pararDeSalvarDados() {
        estaOnColetandoAmostras = false;
        arduino.naoSalvarDadosNoBanco();
        setStatBtnSalvarDatabase(ButtonsStat.OFF);
    }
    /*
     * #########################################################################
     * ####################### SALVA COLETA DE DADOS NO BANCO ##################
     * #########################################################################
     */
    private void iniciarSalvarNoBanco() {
        pararProcessamentoAutomatico();
        arduino.salvarDadosNoBanco();
        estaOnColetandoAmostras = true;
        setStatBtnSalvarDatabase(ButtonsStat.ON);
    }
    /*
     * #########################################################################
     * ######################## PARA O MODO AUTOMATICO #########################
     * #########################################################################
     */
    private void pararProcessamentoAutomatico() {
        estaProcessandoAutomatico = false;
        TButton tb[]={tbAlarmMagnetismo,tbAlarmeIncendio,tbAquecedor,tbArcondicionado};        
        ComponentsUtil.getInstance().habilitarBotoesControleManual(tb);
        setStatBtnProcessarAutomatico(ButtonsStat.OFF);        
    }
    /*
     * #########################################################################
     * ####################### INICIA O MODO AUTOMATICO ########################
     * #########################################################################
     */
    /* ESSE METODO INICIA A VERIFICAÇÃO DOS DADOS VINDOS DA PORTA SERIAL
     * PARA QUE POSSA TOMAR DECISÕES, TODOS OS PINOS DE ENVIO DE DADOS DEVE SER
     * DESLIGADO. O SISTEMA QUE VERIFICARÁ SE É PRECISO OU NÃO LIGAR ESSES SEN-
     * SORES
     */
    private void iniciarProcessamentoAutomatico() {
        pararDeSalvarDados();//PARA DE COLETAR AMOSTRAS
        estaProcessandoAutomatico = true; //DEFINE QUE O PROCESSAMENTO É AUTO
        setStatBtnProcessarAutomatico(ButtonsStat.ON); //MUDA O STATUS DO BTN
        //======================================================================
        desativarAlarmeIncendio();
        setStatBtnAlarmeIncendio(ButtonsStat.OFF);

        desativarAlarmeMagnetismo();
        setStatBtnAlarmeMagnetismo(ButtonsStat.OFF);

        desativarAquecedor();
        setStatBtnAquecedor(ButtonsStat.OFF);

        desativarArCondicionado();
        setStatBtnArCondicionado(ButtonsStat.OFF);
        //Desativa botões que não é para ficar habilitado nesse processamento
        Button botoes[] = {btnAlarmeIncendio, btnAlarmeMagnetismo, btnAquecedor, btnArcondicionado};
        TButton tb[]={tbAlarmMagnetismo,tbAlarmeIncendio,tbAquecedor,tbArcondicionado};
        ComponentsUtil.getInstance().setDeselected(tb);
        ComponentsUtil.getInstance().desabilitarBotoesControleManual(tb);
        ComponentsUtil.getInstance().desabilitarBotoesControleManual(botoes);
    }
    /*
     ##########################################################################
     #                    LIGA E DESLIGA PINO DO ARDUINO                      #
     ##########################################################################
     #                                                                        #
     # ALARME DE MAGNETISMO                                                   #
     # PINO 10 - 100 - DESLIGA                                                #
     # PINO 10 - 101 - LIGA                                                   #
     #========================================================================#
     # ALARME DE INCENDIO                                                     #
     # PINO 11 - 110 - DESLIGA                                                #
     # PINO 11 - 111 - LIGA                                                   #
     #========================================================================#
     # AQUECEDOR                                                              #
     # PINO 12 - 120 - DESLIGA                                                #
     # PINO 12 - 121 - LIGA                                                   #
     #========================================================================#
     # ARCONDICIONADO                                                         #
     # PINO 13 - 130 - DESLIGA                                                #
     # PINO 13 - 131 - LIGA                                                   #
     ##########################################################################
     */
    private void desativarAlarmeMagnetismo() {
        arduino.write(Arduino.DIG_PORTA_10_OFF);
        estaOnAlarmeMagnetismo = false;
        setStatBtnAlarmeMagnetismo(ButtonsStat.OFF);
    }
    /*
     * #########################################################################
     * # ATIVA ALARME DE MAGNETISMO #
     * #########################################################################
     */
    private void ativarAlarmeMagnetismo() {
        arduino.write(Arduino.DIG_PORTA_10_ON);
        estaOnAlarmeMagnetismo = true;
        setStatBtnAlarmeMagnetismo(ButtonsStat.ON);
    }
    /*
     * #########################################################################
     * # DESATIVAR ALARME DE INCENDIO #
     * #########################################################################
     */
    private void desativarAlarmeIncendio() {
        //PORTA 11
        arduino.write(Arduino.DIG_PORTA_11_OFF);
        estaOnAlarmeIncendio = false;
        setStatBtnAlarmeIncendio(ButtonsStat.OFF);
    }
    /*
     * #########################################################################
     * # ATIVAR ALARME DE INCENDIO #
     * #########################################################################
     */
    private void ativarAlarmeIncencio() {
        //PORTA 11
        arduino.write(Arduino.DIG_PORTA_11_ON);
        estaOnAlarmeIncendio = true;
        setStatBtnAlarmeIncendio(ButtonsStat.ON);
    }
    /*
     * #########################################################################
     * # DESLIgAR ARCONDICIONADO #
     * #########################################################################
     */
    private void desativarArCondicionado() {
        //porta 13
        arduino.write(Arduino.DIG_PORTA_13_OFF);
        estaOnArCondicionado = false;
        setStatBtnArCondicionado(ButtonsStat.OFF);
    }
    /*
     * #########################################################################
     * # LIGAR AR CONDICIONADO #
     * #########################################################################
     */
    private void ativarArCondicionado() {
        //porta 13
        arduino.write(Arduino.DIG_PORTA_13_ON);
        estaOnArCondicionado = true;
        tbAquecedor.setSelected(false);
        desativarAquecedor();
        setStatBtnArCondicionado(ButtonsStat.ON);
    }
    /*
     * #########################################################################
     * # DESATIVAR AQUECEDOR #
     * #########################################################################
     */
    private void desativarAquecedor() {
        //porta 12
        arduino.write(Arduino.DIG_PORTA_12_OFF);
        estaOnAquecedor = false;
        setStatBtnAquecedor(ButtonsStat.OFF);

    }
    /*
     * #########################################################################
     * # ATIVAR AQUECEDOR #
     * #########################################################################
     */
    private void ativarAquecedor() {
        //porta 12       
        arduino.write(Arduino.DIG_PORTA_12_ON);
        estaOnAquecedor = true;
        tbArcondicionado.setSelected(false);
        desativarArCondicionado();
        setStatBtnAquecedor(ButtonsStat.ON);
    }
    /*
     * #########################################################################
     * ######################## INICIAR LEITURA DE PORTA #######################
     * #########################################################################
     */
    public void iniciarLeituraDePorta() {
        habilitarBotoes();
        lerPortasArduino();
    }
    /*
     * #########################################################################
     * ######################## PARAR LEITURA DE PORTA #######################
     * #########################################################################
     */
    public void pararLeituraDePorta() {
        arduino.pararDeReceberDados();
        pararDeSalvarDados();
        pararProcessamentoAutomatico();
        desabilitarBotoes();
        dados.clear();
        //ZERA OS STATUS DA VARIAVEIS DE CONTROLE DE TEMPO DE EXECUÇÃO
        estaOnAlarmeIncendio = false;
        estaOnAlarmeMagnetismo = false;
        estaOnAquecedor = false;
        estaOnArCondicionado = false;
        estaOnColetandoAmostras = false;
        estaProcessandoAutomatico = false;
    }
    /*
     ############################################################################
     ############# SET OS TEXTOS DOS BOTÕES DE ACORDO COM O STATUS ATUAL ########
     ############################################################################
     */
    private void setStatBtnProcessarAutomatico(ButtonsStat stat) {
        switch (stat) {
            case OFF:
                btnProcessarAutomatico.setBackground(IBackGround.BACKGROUND_WHITE);
                btnProcessarAutomatico.setText("MANUAL");

                break;
            case ON:
                btnProcessarAutomatico.setBackground(backDefaut);
                btnProcessarAutomatico.setText("AUTOMATICO");
                break;
        }
    }
    private void setStatBtnSalvarDatabase(ButtonsStat stat) {
        switch (stat) {
            case OFF:
                btnSalvarDatabase.setBackground(IBackGround.BACKGROUND_WHITE);
                btnSalvarDatabase.setText("SALVAR OFF");
                break;
            case ON:
                btnSalvarDatabase.setBackground(backDefaut);
                btnSalvarDatabase.setText("SALVAR ON");
                break;
        }
    }
    private void setStatBtnAquecedor(ButtonsStat stat) {
        switch (stat) {
            case OFF:
                btnAquecedor.setBackground(IBackGround.BACKGROUND_WHITE);
                btnAquecedor.setText("AQUECEDOR. OFF");
                tbAquecedor.setText("OFF");
                break;
            case ON:
                btnAquecedor.setBackground(backDefaut);
                btnAquecedor.setText("AQUECEDOR. ON");
                tbAquecedor.setText("ON");
                break;
        }
    }
    private void setStatBtnArCondicionado(ButtonsStat stat) {
        switch (stat) {
            case OFF:
                btnArcondicionado.setBackground(IBackGround.BACKGROUND_WHITE);
                btnArcondicionado.setText("AR COND. OFF");
                tbArcondicionado.setText("OFF");
                break;
            case ON:
                btnArcondicionado.setBackground(backDefaut);
                btnArcondicionado.setText("AR COND. ON");
                tbArcondicionado.setText("ON");
                break;
        }
    }
    private void setStatBtnAlarmeIncendio(ButtonsStat stat) {
        switch (stat) {
            case OFF:
                btnAlarmeIncendio.setBackground(IBackGround.BACKGROUND_WHITE);
                btnAlarmeIncendio.setText("ALARME INCEND. OFF");
                tbAlarmeIncendio.setText("OFF");
                break;
            case ON:
                btnAlarmeIncendio.setBackground(backDefaut);
                btnAlarmeIncendio.setText("ALARME INCEND. ON");
                tbAlarmeIncendio.setText("ON");
                break;
        }
    }
    private void setStatBtnAlarmeMagnetismo(ButtonsStat stat) {
        switch (stat) {
            case OFF:
                btnAlarmeMagnetismo.setBackground(IBackGround.BACKGROUND_WHITE);
                btnAlarmeMagnetismo.setText("ALARME MAGNET. OFF");
                tbAlarmMagnetismo.setText("OFF");
                break;
            case ON:
                btnAlarmeMagnetismo.setBackground(backDefaut);
                btnAlarmeMagnetismo.setText("ALARME MAGNET. ON");
                tbAlarmMagnetismo.setText("ON");
                break;
        }
    }
    private void gerenciarBotoesAtivos() {
        atualizarProgressBarMemoria();
        piscarBotoes();
        //Após piscar os botões altera o background defaul
        if (backDefaut.equals(IBackGround.BACKGROUND_WHITE)) {
            backDefaut = IBackGround.BACKGROUND_DARKGREEN;
        } else {
            backDefaut = IBackGround.BACKGROUND_WHITE;
        }
    }
    private void atualizarProgressBarMemoria() {
        progressMemoria.setProgress(SystemInfo.getUsedMemoryInPercent());
        lblInfoProgressMemoria.setText("Memória usada:: " + SystemInfo.getUsedMemoryInMB() + " de:: " + SystemInfo.getTotalMemoryInMB());
        Runtime.getRuntime().gc();
    }
    private void atualizarValoresLcds() {
        lcdTemperatura.setValue(d.getTemperatura());
        lcdUmidade.setValue(d.getUmidade());
        lcdMagnetismo.setValue(d.getMagnetismo());
        lcdLuminosidade.setValue(d.getLuminosidade());
    }
    private void  salvarInformacaoDeComandos(){
        SaveThread st = new SaveThread();
        new Thread(st).start();
    }
    
    private class SaveThread implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                dados = UltimosDados.getInstance().getDados();
                d = dados.get(0);
                new repository.Repository().salvar(d);
            } catch (InterruptedException ex) {
                Logger.getLogger(MonitorArduinoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}
