/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import modelo.CuentaImpl;
import modelo.ICuenta;

/**
 *
 * @author fywer
 */
public class BuscaMinasServidor {

    private ServerSocket servidor;
    static Map<ProcesoCliente, Date> procesos = new HashMap<>();
    
    public BuscaMinasServidor() {

        /**
         * Obtiena  la ruta de los archivos de la politica del rmi,
         * e inicializa la interfaz de la cuenta.
        */
        
        
        
        URL path = getClass().getResource("/resources/politica/server.policy");
        System.setProperty("java.security.policy", path.toString());
        ICuenta stubCuenta = new CuentaImpl();
        try {
            ICuenta stub = (ICuenta) UnicastRemoteObject.exportObject( stubCuenta, 0);
            Registry registro = LocateRegistry.getRegistry();
            registro.rebind("//localhost/cuentas", stub);
            System.out.println("El servicio de cuentas ha sido iniciado.");
        } catch (RemoteException error) {
            System.out.println("SERVIDOR DE PARTIDAs: " + error.toString());
            System.err.println(error.getMessage());
        }
        
    }

    
    public void iniciarServidor() {
        
        /**
         * Inicializa el servidor, y acepta la peticion del los clientes 
         * e inicializa los hilos.
         */
        
        int puerto = 8080;
        try {
            servidor = new ServerSocket(puerto);
            while (true) {
                //log
                System.out.println("# loop servidor #");
                Socket cliente = servidor.accept();
                
                //log
                System.out.println("SERVIDOR DE PARTIDAs: se ha conectado " + cliente);
                ProcesoCliente proceso = new ProcesoCliente(cliente);
                proceso.start();
                procesos.put(proceso, new Date());
                //log
                System.out.println("# nuevo hilo #");
            }

        } catch (IOException error) {
            System.out.println("SERVIDOR DE PARTIDAs: " + error.toString());
            System.err.println(error.getMessage());
            error.getStackTrace();
        }
    }

    


    
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BuscaMinasServidor servidorMaestro = new BuscaMinasServidor();
        servidorMaestro.iniciarServidor();

    }

}
