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
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import modelo.Cuenta;
import modelo.ICuenta;
import modelo.Perfil;
import utileria.Dialogo;
import utileria.Funcion;
import utileria.Validacion;

/**
 * FXML Controller class
 *
 * @author jonathan
 */
public class FXMLCrearCuentaController implements Initializable, IEscenario {

    @FXML
    private ListView<String> lvwMenu;
    @FXML
    private PasswordField tfdContrasena;
    @FXML
    private TextField tfdUsuario;
    @FXML
    private TextField tfdCorreo;
    @FXML
    private DatePicker dprNacimiento;
    @FXML
    private ComboBox<String> cbxGenero;
    
    private BuscaMinasCliente escenario;
    private ICuenta stub;
 
    @FXML
    private VBox vbxPanel;
    
    private int validarCampos() {
        int verificador = 32;
        if (Validacion.correo(tfdCorreo) == true) verificador >>= 1;
        if (Validacion.texto(tfdContrasena) == true) verificador >>= 1;
        if (Validacion.texto(tfdUsuario) == true) verificador >>= 1;
        if (Validacion.fecha(dprNacimiento) == true) verificador >>= 1;
        if (Validacion.combo(cbxGenero) == true) verificador >>= 1;
        return verificador;
    }
    
    private void limpiarCampos() {
        tfdContrasena.setText("");
        tfdUsuario.setText("");
        tfdCorreo.setText("");
        dprNacimiento.setValue(LocalDate.now() );
        cbxGenero.setValue("");
    }
    
    private Cuenta obtenerCuenta() {
        String contrasena = tfdContrasena.getText();
        String usuario = tfdUsuario.getText();
        String correo = tfdCorreo.getText();
        Date nacimiento = new Date(dprNacimiento.getValue().toEpochDay());
        String genero = cbxGenero.getValue();
        
        if (genero.equals("Hombre")) genero = "M";
        else if (genero.equals("Mujer")) genero = "F";
        
        Perfil perfil = new Perfil(genero, correo, nacimiento, usuario);
        String password = Funcion.generarHash(contrasena);
        if (password != null) {
            return new Cuenta(usuario, password, null, perfil);
        }
        return null;
    }
    
     @FXML
    void btnCrearCuenta(ActionEvent event) {
        
        int valido = validarCampos();
        if (valido == 1 ) {
            Cuenta cuentaVerificada = obtenerCuenta();
            boolean registrado = false;

            try {
                registrado = stub.registrar(cuentaVerificada);
            } catch (RemoteException error) {
                System.err.println("ERROR REGISTRAR" + error.toString());
                System.err.println(error.getMessage());
            }


            if (registrado == true) {
                Dialogo.informacion("El perfil ha sido registrado");
                escenario.intercambiarEscena(Escena.INICIAR_SESION, null);
            }
            else if (registrado == false) {
                Dialogo.advertencia("El usuario ya ha sido registrado");
                limpiarCampos();
            }
            
        } else Dialogo.advertencia("Los campos no ha sido válidos");
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vbxPanel.setId("panel-main");
        Platform.runLater(() -> {
            try {
                stub = (ICuenta) escenario.registroRMI().lookup("//localhost/cuentas");
            } catch (RemoteException error) {
                System.err.println("ERROR INICIAL RMI" + error.toString());
                System.err.println(error.getMessage());
                
            } catch (NotBoundException error) {
                System.err.println("ERROR INICIAL2 RMI" + error.toString());
                System.err.println(error.getMessage());
            }
        });
        
        lvwMenu.setOnMouseClicked(value -> {
            String opcion = lvwMenu.getSelectionModel().getSelectedItem();
            switch (opcion) {
                case "Iniciar Sesión":
                    boolean confirmacion = Dialogo.confirmacion("¿Está seguro que desea cancelar el registro?");
                    if (confirmacion) {
                        escenario.intercambiarEscena(Escena.INICIAR_SESION, null);
                    }
                    break;
            }
        });
    }

    
    @Override
    public void colocarParametro(Object parametro) {
    }

    @Override
    public void colocarEscenario(BuscaMinasCliente escenario) {
        this.escenario = escenario;
    }
}
