/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import cliente.BuscaMinasCliente;

/**
 *
 * @author fywer
 */
public interface IEscenario {
    public void colocarParametro(Object parametro ); 
    public void colocarEscenario(BuscaMinasCliente escenario);
}