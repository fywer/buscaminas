/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author fywer
 */
public final class Tablero implements Serializable {
    private ArrayList<Coordenada> coordenadas;
    //dimensión es es el tamaño del tablero puesto que es un arreglo lineal
    private final int dimension; 
    private int numeroMinas;
    //tamanio es el tamaño del tablero
    private final int tamanio;
    
    public Tablero(int tamanio, int numeroMinas) {
        this.tamanio = tamanio;
        this.dimension = tamanio*tamanio;
        coordenadas = new ArrayList();
        
        int fila = -1;
        int columna = 0;
        
        for (int i = 0; i < dimension; i ++) {

            if ((i)%tamanio == 0) {
                fila = fila + 1;
                columna = 0;
            }
            
            coordenadas.add(new Coordenada(fila, columna));
            columna = columna + 1;
        }        
        generarMinas(numeroMinas);
    }
    
    public void generarMinas(int numeroMinas) {
        int i = 0;
        while (i < numeroMinas) {
            int posicion = (int) (Math.random() * dimension);           
            if (getCoordenadas().get(posicion).isEsMina() != true) {
                getCoordenadas().get(posicion).setEsMina(true);
                i ++ ;
            }         
        }
        calcularMinasAlrededor();
    }
    
    private void asignarNumeroMinas(int fila, int limite) {
        for(int columna = limite - 2 ; columna <= limite; columna ++) {
            try {
                Coordenada coor = obtenerCoordenada(fila, columna);
                if (coor.isEsMina() == false) {
                    coor.setMinasAlrededor(coor.getMinasAlrededor() + 1);
                }
            } catch (IndexOutOfBoundsException error ) {
                continue;
            }
        }
    }
    
    private void calcularMinasAlrededor() {
        int fila = -1;
        int columna = 0;
        for (int i = 0; i < dimension; i ++) {
            
            if ((i)%tamanio == 0) {
                fila = fila + 1;
                columna = 0;
            }
            if (getCoordenadas().get(i).isEsMina() == true) {
                asignarNumeroMinas(fila - 1, columna + 1 );
                asignarNumeroMinas(fila + 1, columna + 1 );
                asignarNumeroMinas(fila, columna + 1 );
            }
            columna = columna + 1;
        }
    }
    
    public Coordenada obtenerCoordenada(int fila, int columna) {
        return coordenadas.get(fila * tamanio + columna); 
       
    }

    /**
     * @return the coordenadas
     */
    public ArrayList<Coordenada> getCoordenadas() {
        return coordenadas;
    }

    /**
     * @param coordenadas the coordenadas to set
     */
    public void setCoordenadas(ArrayList<Coordenada> coordenadas) {
        this.coordenadas = coordenadas;
    }
    
}
