/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import cliente.BuscaMinasCliente;
import cliente.Escena;
import cliente.ThreadCliente;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import modelo.Coordenada;
import modelo.Cuenta;
import modelo.Grupo;
import modelo.Partida;
import modelo.Tablero;
import utileria.Dialogo;
import utileria.Funcion;

/**
 * FXML Controller class
 *
 * @author jonathan
 */
public class FXMLIniciarTableroController implements Initializable, IEscenario {

    @FXML
    private ListView<String> lvwGrupo;
    @FXML
    private GridPane gpeTablero;
    @FXML
    private TextField tiempo;

    private BuscaMinasCliente escenario;
    private ThreadCliente servidorPartida;
    private Cuenta cuenta;
    private Grupo grupo;

    private ArrayList<Button> rectangulos;
    public static Map< Button, Coordenada> botonesCoordenadas = new HashMap<>();

    @FXML
    void btnAbandonarPartida(ActionEvent event) {
        boolean confirmacion = Dialogo.confirmacion("¿Está seguro que desea abandonar la partida?");
        if (confirmacion) {
            this.cuenta.getPartida().setEnPartida("desconectado");
            servidorPartida.enviarMensaje(this.cuenta.getPartida());
            escenario.intercambiarEscena(Escena.MULTIJUGADOR, null);
        }

    }

    public void agregarGrupos(List<Partida> partidas) {
        /**
         * Coloca el usurios de cada partida agrupada a la lista de usuarios
         * agrupados.
         */
        ObservableList lista = FXCollections.<String>observableArrayList();

        partidas.forEach((partida) -> {
            lista.add(partida.getCuentaUsuario());
        });
        lvwGrupo.getItems().addAll(lista);

    }

    public void actualizarTablero(Tablero tablero) {
        /**
         * Recorre todos los botones que simula el tablero, 1- la coordenada
         * asociado al boton es igual a la coordenada del parametro de la
         * función, entonces, evalua la coordenada.
         */
        tablero.getCoordenadas().forEach(coordenada -> {
            if (coordenada.isEstaAbiera()) {
                actualizarCoordenada(coordenada);
            }
        });
    }

    public void actualizarCoordenada(Coordenada coordenada) {
        botonesCoordenadas.forEach((boton, valor) -> {
            if (valor.toString().equals(coordenada.toString())) {
                Platform.runLater(() -> {
                    if (coordenada.isEsMina() == true) {
                        boton.setGraphic(Funcion.verImagen("src/resources/imagen/mina.png"));

                    } else if (coordenada.isEsPosibleMina() == true) {
                        boton.setGraphic(Funcion.verImagen("src/resources/imagen/banderin.png"));
                    } else {
                        Label etiqueta = new Label(String.valueOf(valor.getMinasAlrededor()));
                        etiqueta.setStyle("-fx-font-size: 16pt;");
                        boton.setGraphic(etiqueta);
                        boton.setDisable(true);

                    }
                });
            }
        });

    }

    public void iniciarTablero() {
        /**
         * Crea una lista de botones que simulan las cordenadas del tablero,
         * agregar el nuevo boton a una l
         */
        agregarGrupos(grupo.getPartidas());
        rectangulos = new ArrayList();
        int fila = -1;
        int columna = 0;

        for (int i = 0; i < 64; i++) {
            rectangulos.add(i, new Button());
            rectangulos.get(i).setMaxSize(64, 64);

            if ((i) % 8 == 0) {
                fila = fila + 1;
                columna = 0;
            }

            botonesCoordenadas.put(
                    rectangulos.get(i),
                    grupo.getTablero().obtenerCoordenada(fila, columna));

            emitirEventos(i);

            gpeTablero.add(rectangulos.get(i), columna, fila, 1, 1);
            columna = columna + 1;
        }

    }

    private void emitirEventos(int id) {
        //emitir coordenada de la posible bomba
        
        
        rectangulos.get(id).setOnAction(value -> {
            /**
             * busca la coordenada en función del la coordenada del boton
             * presionado.
             */
            Button botonPresionado = (Button) value.getSource();
            Coordenada coord = botonesCoordenadas.get(botonPresionado);
            
            grupo.getTablero().obtenerCoordenada(coord.getX(), coord.getY())
                    .setEstaAbierta(true);
            
            servidorPartida.enviarMensaje(grupo);
           
        });
/*
        //emitir coordenada de la bandera 
        rectangulos.get(id).setOnMouseClicked(value -> {
            if (value.getButton() == MouseButton.SECONDARY) {
                Button botonMina = (Button) value.getSource();
                botonesCoordenadas.get(botonMina).setEsPosibleMina(true);
                servidorPartida.enviarMensaje(botonesCoordenadas.get(botonMina));
            }
        });
*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Platform.runLater(() -> {

            //setEscenario=this
            servidorPartida.procesarEscenario(this);
            //agrega el grupo al la lista de grupos

            //agregarGrupos(grupo.getPartidas());
        });
    }

    @Override
    public void colocarParametro(Object parametro) {
        if (parametro instanceof ArrayList) {
            ((ArrayList) parametro).forEach(param -> {
                if (param instanceof ThreadCliente) {
                    this.servidorPartida = (ThreadCliente) param;
                } else if (param instanceof Cuenta) {
                    this.cuenta = (Cuenta) param;
                } else if (param instanceof Grupo) {
                    this.grupo = (Grupo) param;
                    iniciarTablero();
                }
            });
        }
    }

    @Override
    public void colocarEscenario(BuscaMinasCliente escenario) {
        this.escenario = escenario;
    }
}
