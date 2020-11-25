/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

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
public interface IMensaje {
    static Map<Socket, Date> clientes = new HashMap<>();
   
    public List<Socket> obtenerClientes(Integer idGrupo);
    public void unicast(Socket socket, Object mensaje);
    public void multicast(List<Socket> sockets, Object mensaje);
    public void broadcast(Object mensaje);
}
