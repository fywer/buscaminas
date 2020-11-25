/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fywer
 */
public class PartidaImpl implements IPartida {

    private static List<Grupo> grupoPartidas = new ArrayList<>();

    @Override
    public List<Partida> obtenerPartidas() {
        /**
         * Regresa la lista de partidas.
         */
        return partidas;
    }

    @Override
    public void registrarPartida(Partida partidaNueva) {
        /**
         * Verifica si el usuario ha estado conectado y genera un id de grupo y
         * agrega la patida nueva a la lista de partidas.
         */
        String usuario = partidaNueva.getCuentaUsuario();
        boolean haEstadoConectado = haEstadoConectado(usuario);

        if (!haEstadoConectado) {
            partidas.add(partidaNueva);
        }
    }

    public void desconectarPartida(Partida partidaDesconectada) {
        /**
         * Obtiene el usuario de la partida desconectada y la busca en la lista
         * de partida, si la encuentra la partida que ha sido desconecta cambia
         * el estado a "desconectado".
         */
        String usuario = partidaDesconectada.getCuentaUsuario();

        partidas.forEach(partida -> {
            System.out.println("SALIDA 5" + partidas);
            if (partida.getCuentaUsuario().equals(usuario)) {
                partida.setEnPartida("desconectado");
            }
        });

    }

    public void asignarTurno(Partida partida) {
        
    }
    
    public Grupo agruparPartidas(Integer TOTAL_GRUPOS ) {
        /**
         * Todas las partidas que no esten: "en partida", las agrupa en una
         * lista de partidas temporales y las agrega a un grupo.
         */
        List<Partida> grupoTempPartidas = new ArrayList<>();

        partidas.forEach(partida -> {
            /**
             * Agrupa en función de los estados de la partida."
             */
            if (partida.getEnPartida().equals("conectado") ) {
                partida.setEnPartida("en partida");
                grupoTempPartidas.add(partida);
            }
        });

        
        /**
         * Crea un nuevo tablero para el grupo.
         */
        Tablero tablero = new Tablero(8, 10);       
        partidas.get(0).setTurno(true);
        
        Grupo grupo = new Grupo(grupoTempPartidas);
        grupo.setIdGrupo(TOTAL_GRUPOS + 1);
        grupo.setTablero(tablero);
        
        grupoPartidas.add(grupo);
        return grupo;
    }

    public boolean haEstadoConectado(String usuario) {
        /**
         * Busca el la lista de partidas, si el usuario ha estado conectado.
         */
        for (Partida partida : partidas) {
            if (partida.getCuentaUsuario().equals(usuario)) {
                partida.setEnPartida("conectado");
                return true;
            }
        }
        return false;
    }

    /**
     * @return the grupoPartidas
     */
    public static List<Grupo> getGrupoPartidas() {
        return grupoPartidas;
    }

    /**
     * @param aGrupoPartidas the grupoPartidas to set
     */
    public static void setGrupoPartidas(List<Grupo> aGrupoPartidas) {
        grupoPartidas = aGrupoPartidas;
    }
}
