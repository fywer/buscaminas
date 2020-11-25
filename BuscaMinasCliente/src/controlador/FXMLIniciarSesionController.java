/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import cliente.BuscaMinasCliente;
import cliente.Escena;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Cuenta;
import modelo.ICuenta;
import utileria.Dialogo;
import utileria.Funcion;
import utileria.Validacion;

/**
 * FXML Controller class
 *
 * @author jonathan
 */
public class FXMLIniciarSesionController implements Initializable, IEscenario {

    @FXML
    private TextField tfdUsuario;
    @FXML
    private PasswordField tfdContrasena;
    @FXML
    private ListView<String> lvwMenu;
    @FXML
    private ImageView ievPortada;

    private BuscaMinasCliente escenario;

    private ICuenta stubCuenta;
    
    @FXML
    private VBox vbxPanel;

    
    @FXML
    void btnIniciarSesion(ActionEvent event) {
        /**
         * Autentica el usuario y la contraseña en el RMI y despliega la tabla
         * multijugador.
         */
        int valido = validarCampos();
        if (valido == 1) {
            Cuenta cuentaValidada = obtenerCuenta();

            try {
                Cuenta cuenta = stubCuenta.autenticar(cuentaValidada);
                desplegarMultijugador(cuenta);
            } catch (RemoteException error) {
                System.out.println("SERVIDOR DE PARTIDAs: " + error.toString());
                System.err.println(error.getMessage());
                error.getStackTrace();
            }

        } else {
            Dialogo.advertencia("Los campos no ha sido válidos");
        }

    }

    private int validarCampos() {
        /**
         * Verifica que los campos usuario y contraseñan ha sido correctamente
         * capturados.
         */
        int verificador = 4;
        if (Validacion.texto(tfdUsuario) == true) {
            verificador >>= 1;
        }
        if (Validacion.texto(tfdContrasena) == true) {
            verificador >>= 1;
        }
        return verificador;
    }

    public void conectarClienteRMI() {
        /**
         * Conecta con el servidor de cuentas.
         */
        try {
            this.stubCuenta = (ICuenta) escenario.registroRMI().lookup("//localhost/cuentas");
        } catch (RemoteException error) {
            System.err.println("ERROR INICIAL RMI" + error.toString());
            System.err.println(error.getMessage());

        } catch (NotBoundException error) {
            System.err.println("ERROR INICIAL2 RMI" + error.toString());
            System.err.println(error.getMessage());
        }
    }

    public void emitirEventos() {
        lvwMenu.setOnMouseClicked(value -> {
            /**
             * Evento del mouse para intercambiar la escena de registrar nueva
             * cuenta.
             */
            String opcion = lvwMenu.getSelectionModel().getSelectedItem();
            switch (opcion) {
                case "Crear Cuenta":
                    escenario.intercambiarEscena(Escena.CREAR_CUENTA, null);
                    break;
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ievPortada.setImage(Funcion.obtenerImagen("src/resources/imagen/portada.png"));
        vbxPanel.setId("panel-main");
        Platform.runLater(() -> {
            conectarClienteRMI();
        });
        
        emitirEventos();

    }    
     
    public void desplegarMultijugador(Cuenta cuenta) {
        /**
         * Pasa la cuenta del usuario a la escena multijugador.
         *
         */
        if (cuenta != null) {
            Dialogo.informacion("Bienvenido: " + cuenta.getUsuario());
            escenario.intercambiarEscena(Escena.MULTIJUGADOR, cuenta);
        } else if (cuenta == null) {
            Dialogo.advertencia("El usuario o contraseña no han sido indentificados");
        }
    }

    
     private Cuenta obtenerCuenta() {
        /**
         * Captura los campos usuario y la contraseña hasheada y regresa la
         * cuenta.
         */
        String usuario = tfdUsuario.getText();
        String contrasena = tfdContrasena.getText();
        String password = Funcion.generarHash(contrasena);
        if (password != null) {
            return new Cuenta(usuario, password);
        }
        return null;
    }
    
    
    @Override
    public void colocarParametro(Object parametro) {
        //this.cuenta = cuenta;
    }

    @Override
    public void colocarEscenario(BuscaMinasCliente escenario) {
        this.escenario = escenario;
    }
}
