/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utileria;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author fywer
 */
public class Dialogo {

    public static void informacion(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Diálogo de Información");
        alert.setHeaderText("BuscaMinasApp");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void advertencia(String mensaje) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Diálogo de Advertencia");
        alert.setHeaderText("BuscaMinasApp");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public static boolean confirmacion(String mensaje) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de Confirmación");
        alert.setHeaderText("BuscaMinasApp");
        alert.setContentText(mensaje);   
        Optional<ButtonType> resul = alert.showAndWait();
        if (resul.get() == ButtonType.OK ) return true;
        else return false;
    }

}
