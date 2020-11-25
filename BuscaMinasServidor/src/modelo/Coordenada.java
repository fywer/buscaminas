/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author fywer
 */
public class Coordenada implements Serializable {
     private int x;
    private int y; 
    private boolean esMina;
    private boolean esPosibleMina;
    private int minasAlrededor;
    private boolean estaAbierta;

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
    
    public Coordenada(int fila, int columna) {
        this.esMina = false;
        this.esPosibleMina = false;
        this.x = fila;
        this.y = columna;
        this.minasAlrededor = 0;
        this.estaAbierta = false;
    }
        
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the esMina
     */
    public boolean isEsMina() {
        return esMina;
    }

    /**
     * @param esMina the esMina to set
     */
    public void setEsMina(boolean esMina) {
        this.esMina = esMina;
    }

 

    /**
     * @return the minasAlrededor
     */
    public int getMinasAlrededor() {
        return minasAlrededor;
    }

    /**
     * @param minasAlrededor the minasAlrededor to set
     */
    public void setMinasAlrededor(int minasAlrededor) {
        this.minasAlrededor = minasAlrededor;
    }

    /**
     * @return the esPosibleMina
     */
    public boolean isEsPosibleMina() {
        return esPosibleMina;
    }

    /**
     * @param esPosibleMina the esPosibleMina to set
     */
    public void setEsPosibleMina(boolean esPosibleMina) {
        this.esPosibleMina = esPosibleMina;
    }

    /**
     * @return the estaAbiera
     */
    public boolean isEstaAbiera() {
        return estaAbierta;
    }

    /**
     * @param estaAbierta the estaAbierta to set
     */
    public void setEstaAbierta(boolean estaAbierta) {
        this.estaAbierta = estaAbierta;
        
    }
}
