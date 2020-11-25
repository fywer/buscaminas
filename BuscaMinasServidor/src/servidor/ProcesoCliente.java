/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.Partida;
import modelo.PartidaImpl;
import modelo.Coordenada;
import modelo.Grupo;
import modelo.Tablero;

/**
 *
 * @author fywer
 */
public class ProcesoCliente extends Thread {

    private final Socket cliente;
    private final MensajeImpl mensajeImpl;
    private final PartidaImpl partidaImpl;
    private static Integer TOTAL_GRUPOS = 0;

    public ProcesoCliente(Socket cliente) {
        this.mensajeImpl = new MensajeImpl();
        this.cliente = cliente;
        this.partidaImpl = new PartidaImpl();
        IMensaje.clientes.put(cliente, new Date());

    }

    @Override
    public void run() {

        /**
         * Recibe el mensaje de cliente, En caso de que cliente pierda la
         * conexión con el servidor de partidas, entonces se elimina de la lista
         * de clientes.
         */
        try {

            while (true) {
                /**
                 * La llegada del mensaje al receptor. (receptarMensaje)
                 */
                ObjectInputStream bufferEntrada = new ObjectInputStream(cliente.getInputStream());
                Object mensaje = bufferEntrada.readObject();
                decodificarMensaje(mensaje);
            }
        } catch (IOException | ClassNotFoundException error) {
            System.out.println("SERVIDOR DE PARTIDAs: " + error.toString());
            System.err.println(error.getMessage());
        }
    }

    public void decodificarMensaje(Object mensaje) {
        /**
         * Fitros por parte del receptor.
         */
        if (mensaje instanceof Partida) {

            Partida partida = (Partida) mensaje;
            List<Partida> partidas;
            /**
             * Intención del cliente.
             */
            switch (partida.getEnPartida()) {
                case "conectado":
                    /**
                     * si el estado de la partida es "conectado" entonces,
                     * registra la partida en una lista de partidas y regresa al
                     * cliente la lista de partidas actualizada.
                     */
                    this.partidaImpl.registrarPartida(partida);
                    partidas = (List<Partida>) this.partidaImpl.obtenerPartidas();
                    mensajeImpl.broadcast(partidas);
                    break;
                case "en partida":
                    /**
                     * si el estado de la partida es "en partida" entonces,
                     * actualiza el grupo de la partidas y, envia el nuevo
                     * tablero grupo de clientes al grupo con el id de grupo
                     * igual.
                     */
                    List<Socket> sockets;
                    Grupo grupo = this.partidaImpl.agruparPartidas(TOTAL_GRUPOS);
                    TOTAL_GRUPOS = TOTAL_GRUPOS + 1;
                    List<Socket> sokets = mensajeImpl.agruparClientes(grupo.getIdGrupo());

                    partidas = (List<Partida>) this.partidaImpl.obtenerPartidas();
                    mensajeImpl.broadcast(partidas);

                    //obtener la lista de sokets actuales y envia el grupo.
                    sockets = this.mensajeImpl.obtenerClientes(grupo.getIdGrupo());
                    System.out.println("SALIDA 4: " + sockets);
                    mensajeImpl.multicast(sockets, grupo);

                    break;
                case "desconectado":
                    /**
                     * si el estado de la partida es "desconectado" entonces,
                     * cambia el estado de la partida a desconectada y, envia a
                     * todos los cliente la lista de las partidas
                     */
                    this.partidaImpl.desconectarPartida(partida);
                    partidas = (List<Partida>) this.partidaImpl.obtenerPartidas();
                    mensajeImpl.broadcast(partidas);
                    break;
            }

        } else if (mensaje instanceof Grupo) {
            /**
             * si el mensaje es de tipo coordenada entonces, el jugador ha
             * destapado un mina.
             */
            //EL PRODUCTOR DE TABLEROS
            Grupo grupo = (Grupo) mensaje;
            grupo.getPartidas().forEach(partida -> {
                
                if (partida.isTurno() == true) {
                    Integer idGrupo = grupo.getIdGrupo();
                    System.out.println("ID DE GRUPO:" + idGrupo);
                    List<Socket> sockets = this.mensajeImpl.obtenerClientes(idGrupo);
                    System.out.println("MULTICAST: " + sockets);

                    mensajeImpl.multicast(sockets, grupo.getTablero());
                }
            });

        }
    }

}
