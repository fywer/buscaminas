/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fywer
 */
public class MensajeImpl implements IMensaje {
    static Map<Integer, List<Socket>> grupoClientes = new HashMap<>();
    
    static long idHoraActual;
    
    
    @Override
    public void unicast(Socket cliente, Object mensaje) {
        /**
         * Envia el mensaje a un cliente que este conectado.
         */
        try {
            ObjectOutputStream bufferSalida = new ObjectOutputStream(cliente.getOutputStream());
            bufferSalida.writeObject(mensaje);
        } catch (IOException error) {
            System.err.println(error);
        }
    }
    
    @Override
    public void multicast(List<Socket> sockets, Object mensaje) {
        /**
         * Envia el mensaje a un grupo de clientes.
         */
        sockets.forEach(socket -> {
            try {
                ObjectOutputStream bufferSalida = new ObjectOutputStream(socket.getOutputStream());
                bufferSalida.writeObject(mensaje);
            } catch (IOException error) {
                System.err.println(error);
            }
        });
    }
    
    @Override
    public void broadcast(Object mensaje) {
        /**
         * Envia el mensaje a cada cliente que estan conectados al servidor.
         */
        clientes.forEach((cliente, hora) -> {
            try {
                ObjectOutputStream bufferSalida = new ObjectOutputStream(cliente.getOutputStream());
                bufferSalida.writeObject(mensaje);
            } catch (IOException error) {
                System.err.println(error);
            }
        });
    }
    
    
    
    public boolean estaEnPartida(Socket cliente) {
        for (Integer grupo : grupoClientes.keySet()) {
            List<Socket> sockets = grupoClientes.get(grupo);
            for (Socket socket : sockets) {
                if (socket.getPort() == cliente.getPort()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<Socket> agruparClientes(Integer idGrupo) {
        /**
         * Todos los sockets que no esten: "en el mismo grupo"  las agrupa
         * en una lista de sockets temporales y los agrega al grupo.
         */
        List<Socket> tempClientes = new ArrayList<>();
        idHoraActual = new Date().getTime();
        
        clientes.forEach((cliente, hora) -> {
            if (hora.getTime() < idHoraActual) {
                if (!estaEnPartida(cliente)) {
                    tempClientes.add((Socket) cliente);
                }               
            } 
        });
        
        grupoClientes.put(idGrupo, tempClientes);
        return tempClientes;
    }    
    @Override
    public List<Socket> obtenerClientes (Integer idGrupo) {
        return grupoClientes.get(idGrupo);
    }
        
    public void desconectarCliente(Socket cliente) {
        /**
         * Desconecta el cliente que han perdido la conexion con el servidor y 
         * los elimina de la listas de clientes.
         */
        try {
            IMensaje.clientes.remove(cliente);
            cliente.close();
            
            //log
            System.out.println("SERVIDOR DE PARTIDAs: se ha desconectado " + cliente);

        } catch (IOException error) {
            System.err.println("ERROR DESCONECTAR CLIENTE " + error.toString());
            System.out.println(error.getMessage());
        }
    }
    
     public void mostrarGrupo() {
        System.out.println("CLIENTES DEL SERVIDOR");
        grupoClientes.forEach((id, cliente) -> {
            System.out.println(id + " : " + cliente);
        }) ;
     }  
        
    
}
