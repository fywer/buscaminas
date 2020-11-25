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
public interface IPartida {
    static List<Partida> partidas = new ArrayList<>();
    public List<Partida> obtenerPartidas();
    public void registrarPartida(Partida partida);
}
