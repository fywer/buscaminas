/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import cliente.BuscaMinasCliente;
import cliente.Escena;
import cliente.ThreadCliente;
;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modelo.Cuenta;
import modelo.Grupo;
import modelo.Partida;
import utileria.Dialogo;
import utileria.Funcion;

/**
 * FXML Controller class
 *
 * @author jonathan
 */


public class FXMLMultijugadorController implements Initializable, IEscenario {

    @FXML
    private ListView<String> lvwMenu;
    @FXML
    private TableColumn tcnUsuario;
    @FXML
    private TableColumn tcnPartidasGanadas;
    @FXML
    private TableColumn tcnPartidasPerdidas;
    @FXML
    private TableColumn tcnEstadoPartida;

    @FXML
    private Label lblJugador;
    @FXML
    private Label lblPuntaje;

    @FXML
    private ImageView ivwFotoJugador;

    @FXML
    private TableView<Partida> tvwJugadoresConectados;

    private BuscaMinasCliente escenario;
    private Cuenta cuenta;
    private ThreadCliente servidorPartida;
    @FXML
    private VBox vbxPanel;

    @FXML
    void btnIniciarPartida(ActionEvent event) {
        /**
         * Evento que cambia el estado de la partida "en partida" y envia la
         * partida al servidor.
         */
        this.cuenta.getPartida().setEnPartida("en partida");
        this.servidorPartida.enviarMensaje(this.cuenta.getPartida());
    }

    public void agregarPartidas(List<Partida> partidas) {
        /**
         * Agrega la lista de partidas a la tabla de los jugadores conectados.
         */
        ObservableList lista = FXCollections.observableArrayList(partidas);
        tvwJugadoresConectados.setItems(lista);
    }

    public void emitirEventos() {
        lvwMenu.setOnMouseClicked((MouseEvent value) -> {
            /**
             * Evento de mouse para cerrar la sesión e intercambiar la escena
             * inicio de sesion.
             */
            String opcion = lvwMenu.getSelectionModel().getSelectedItem();
            switch (opcion) {
                case "Cerrar Sesión":
                    /**
                     * Cambia el estado de la partida y envia la partida al
                     * servidor, y cierra la conexion del socket.
                     */
                    boolean confirmacion = Dialogo.confirmacion("¿Está seguro que desea salir del juego?");
                    if (confirmacion) {
                        /**
                         * EMITIR SEÑAL 
                         * Cambia el estado de la partida a "deconectado" y envia la partida al servidor.
                         */

                        this.cuenta.getPartida().setEnPartida("desconectado");
                        servidorPartida.enviarMensaje(this.cuenta.getPartida());
                        
                        servidorPartida.cerrar();
                        escenario.intercambiarEscena(Escena.INICIAR_SESION, null);
                    }
                    break;
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vbxPanel.setId("panel-main");
        configurarTablaDePartidas();
        ivwFotoJugador.setImage(Funcion.obtenerImagen("src/resources/imagen/foto.png"));

        Platform.runLater(() -> {
            lblJugador.setText(this.cuenta.getUsuario());

            if (servidorPartida == null) {
                servidorPartida = new ThreadCliente();
                servidorPartida.procesarEscenario(this);
                servidorPartida.conectarCliente("127.0.0.1", 8080);
            }
            /**
             * EMITIR SEÑAL Cambia el estado de la partida a "conectado" y envia la
             * partida al servidor.
             */
            this.cuenta.getPartida().setEnPartida("conectado");
            servidorPartida.enviarMensaje(this.cuenta.getPartida());

            emitirEventos();
        });
    }

    public void desplegarTablero(Grupo grupo) {
        /**
         * Agrega los parametros ThreadCliente y Tablero intercambia la escena
         * tablero.
         */
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(this.servidorPartida);
        parametros.add(this.cuenta);
        parametros.add(grupo);

        Platform.runLater(() -> {
            escenario.intercambiarEscena(Escena.INICIAR_TABLERO, parametros);
        });
    }

    private void configurarTablaDePartidas() {
        /**
         * Configura las columnas de la tabla del mutijugador para conincidirla
         * con los atributos de la entidad Partida.
         */
        tcnUsuario.setCellValueFactory(new PropertyValueFactory<>("cuentaUsuario"));
        tcnPartidasGanadas.setCellValueFactory(new PropertyValueFactory<>("ganadas"));
        tcnPartidasPerdidas.setCellValueFactory(new PropertyValueFactory<>("perdidas"));
        tcnEstadoPartida.setCellValueFactory(new PropertyValueFactory<>("enPartida"));

    }

    @Override
    public void colocarParametro(Object parametro) {
        if (parametro instanceof Cuenta) {
            this.cuenta = (Cuenta) parametro;
        }
    }

    @Override
    public void colocarEscenario(BuscaMinasCliente escenario) {
        this.escenario = escenario;
    }
}
