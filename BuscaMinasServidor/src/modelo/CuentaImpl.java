/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.EntidadCuentaJpaController;
import entidad.EntidadCuenta;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author fywer
 */
public class CuentaImpl implements ICuenta {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("BuscaMinasServidorPU");

    @Override
    public Cuenta autenticar(Cuenta cuenta) {
        /**
         * Busca la cuenta en la base de datos, si existe regresa la cuenta
         * al cliente RMI.
         */
        EntidadCuentaJpaController jpaCuenta = new EntidadCuentaJpaController(emf);
        cuenta = jpaCuenta.buscarCuenta(cuenta);
        if (cuenta != null ) {
            return cuenta;
        }
        return null;
    }

    @Override
    public boolean registrar(Cuenta cuenta) {
        /**
         * Genera una nueva partida y registra la cuenta el la base de datos.
         */
        EntidadCuentaJpaController jpaCuenta = new EntidadCuentaJpaController(emf);
        try {            
            Partida partida = new Partida();    
            partida.setCuentaUsuario(cuenta.getUsuario());
            cuenta.setPartida(partida);     
            
            EntidadCuenta entidadCuenta = new EntidadCuenta(cuenta);            
            jpaCuenta.create(entidadCuenta);
            
            return true;
        } catch (Exception error) {
            System.out.println("ERROR REGISTRAR" + error.toString());
            System.err.println(error.getMessage());
            System.err.println(cuenta.getUsuario() + " YA HA SIDO REGISTRADO");
        }
        return false;
    }

 
    @Override
    public void recuperar(Cuenta cuenta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
