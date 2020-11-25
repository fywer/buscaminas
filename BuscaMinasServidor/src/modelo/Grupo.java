/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fywer
 */
public class Grupo implements Serializable {
    private Integer idGrupo = 0;
    private List<Partida> partidas;
    private Tablero tablero;

    public Grupo(List<Partida> partidas) {
        this.partidas = partidas;
        idGrupo = idGrupo + 1;
    }

    /**
     * @return the idGrupo
     */
    public Integer getIdGrupo() {
        return idGrupo;
    }

    /**
     * @param idGrupo the idGrupo to set
     */
    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * @return the partidas
     */
    public List<Partida> getPartidas() {
        return partidas;
    }

    /**
     * @param partidas the partidas to set
     */
    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    /**
     * @return the tablero
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * @param tablero the tablero to set
     */
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }    
    
}
