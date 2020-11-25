/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utileria;

import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author fywer
 */
public class Validacion {
    //validar texto
    public static boolean texto(TextField texto) {
        texto.setStyle(null);
        Pattern auto = Pattern.compile("^([0-9A-Za-zñÑáéíóúÁÉÍÓÚ]+\\s?[0-9A-Za-zñÑáéíóúÁÉÍÓÚ,&.-:+%=/?@#]*\\s?)+$");
        boolean valido = auto.matcher(texto.getText()).find();
        if (valido && (texto.getText().length() < 45) ) return true;
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");return false;
    }
    
    //validar correo
    public static boolean correo(TextField texto) {
        texto.setStyle(null);
        Pattern correo = Pattern.compile("^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        boolean valido = correo.matcher(texto.getText()).find();
        if (valido) return true;
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");return false;
    }
    
    //validar fecha
    public static boolean fecha(DatePicker texto) {
        texto.setStyle(null);
        Pattern fecha = Pattern.compile("^\\d{4}-\\d{2}-\\d{1,2}$");
        boolean valido = fecha.matcher( (texto.getValue() == null) ? "" : texto.getValue().toString() ).find();        
        if (valido) return true;
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");return false;
    }
    
    //validar combo
    public static boolean combo(ComboBox texto) {
        texto.setStyle(null);
        if (texto.getValue() != null) 
            return true;
        else 
            texto.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        return false;
    }
    
}
