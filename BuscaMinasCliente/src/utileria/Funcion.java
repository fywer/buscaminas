/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utileria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author fywer
 */
public class Funcion {

    public static String generarHash(String contrasena) {
        MessageDigest sha256;
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(contrasena.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                stringBuilder.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }

    }
    
    public static ImageView verImagen (String ruta) {
        FileInputStream input;
        try {
           
            input = new FileInputStream(ruta);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            return imageView;
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }     
    
    public static Image obtenerImagen (String ruta) {
        File file = new File(ruta);
        Image image = new Image(file.toURI().toString());
        return image;
    }
}



