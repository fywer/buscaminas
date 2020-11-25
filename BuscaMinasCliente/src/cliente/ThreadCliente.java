/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import controlador.FXMLIniciarTableroController;
import controlador.FXMLMultijugadorController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import modelo.Partida;
import modelo.Tablero;
import controlador.IEscenario;
import modelo.Grupo;

/**
 *
 * @author fywer
 */
public class ThreadCliente  implements Runnable {

    private Socket cliente;
    private FXMLMultijugadorController escenaMulti;
    private FXMLIniciarTableroController escenaJuego;

    private ObjectOutputStream bufferSalida;
    
    
    @Override
    public void run() {

        //SUPOSICIÓN : recibe la última partida que ha sido conecta y la agrega a la lista de patidas de 
        //la clase multijugador
        try {
            while (true) {
                /**
                 * La llegada del mensaje al emisor. (receptarMensaje)
                 */
                ObjectInputStream bufferEntrada = new ObjectInputStream(cliente.getInputStream());
                Object mensaje = bufferEntrada.readObject();

                decodificarMensaje(mensaje);
            }
        } catch (ClassNotFoundException | IOException error) {
            System.out.println("CLIENTE: " + error.toString());
            System.err.println(error.getMessage());
        }

    }

    public void decodificarMensaje(Object mensaje) {
        /**
         * Actualiza el escenario en función del tip    o de mensaje, Filtros por
         * parte del emisor.
         */
        if (mensaje instanceof List) {
            this.escenaMulti.agregarPartidas((List<Partida>) mensaje);
        } else if (mensaje instanceof Tablero) {
            this.escenaJuego.actualizarTablero((Tablero) mensaje);

        } else if (mensaje instanceof Grupo) {  
            this.escenaMulti.desplegarTablero((Grupo) mensaje);
        }
    }

    public void conectarCliente(String host, int port) {
        /**
         * Crea un nuevo socket cliente y lo conecta con el servidor.
         */
        try {
            cliente = new Socket(host, port);
            new Thread(this).start();

        } catch (IOException error) {
            System.out.println("CLIENTE: " + error.toString());
            System.err.println(error.getMessage());
        }
    }

    public void procesarEscenario(IEscenario escena) {
        if (escena instanceof FXMLIniciarTableroController) {
            this.escenaJuego = (FXMLIniciarTableroController) escena;
        } else if (escena instanceof FXMLMultijugadorController) {
            this.escenaMulti = (FXMLMultijugadorController) escena;
        }
    }
                                                            
    public void enviarMensaje(Object mensaje) {
        try {
     
            bufferSalida = new ObjectOutputStream(cliente.getOutputStream());
            bufferSalida.writeObject(mensaje);

        } catch (IOException error) {
            System.err.println("ERROR ENVIAR " + error.toString());
            System.err.println(error.getMessage());
        }
    }

    public boolean cerrar() {
        /**
         *
         */
        if (cliente.isClosed() == false) {
            try {
                cliente.close();
                return true;
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return false;
    }
}
    
