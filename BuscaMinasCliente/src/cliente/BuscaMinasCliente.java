/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.net.URL;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import controlador.IEscenario;

/**
 *
 * @author fywer
 */
public class BuscaMinasCliente extends Application {

    private Stage pantalla;
    private Pane escena;
    private Registry registro;

    @Override
    public void init() {
        /**
         * Obtiene la ruta las politicas del servidor rmi e inicializa el
         * regitro rmi;
         */
        URL path = getClass().getResource("/resources/politica/client.policy");
        System.setProperty("java.security.policy", path.toString());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        try {
            registro = LocateRegistry.getRegistry("127.0.0.1");
        } catch (RemoteException error) {
            System.out.println("SERVIDOR DE PARTIDAs: "  + error.getMessage());
        }
    }

    @Override
    public void start(Stage pantalla) {
        /**
         * Genera una nueva escena (Pane) .
         */
        Pane root = new Pane();
        this.pantalla = pantalla;
        this.escena = root;
        this.intercambiarEscena(Escena.INICIAR_SESION, null);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("resources/estilo/main.css");
        pantalla.setScene(scene);
        pantalla.show();

    }

    public void intercambiarEscena(final Escena view, Object parametro) {
        /**
         * Busca escena, obtiene la lista de escenarios, si, el escenario esta
         * vacio entonces, la escena es agregada al escenario, de lo contrario,
         * la escena anterior es eliminada y agrega la nueva escena.
         *
         */
        FXMLLoader fxml = new FXMLLoader(getClass().getResource(view.archivo()));

        try {
            Pane escena_fxml = fxml.load();
            if (escena.getChildren().isEmpty()) {
                escena.getChildren().add(escena_fxml);
            } else {
                escena.getChildren().remove(0);
                escena.getChildren().add(0, escena_fxml);
                this.pantalla.setWidth(655);
                this.pantalla.setHeight(520);
            }
            this.pantalla.setTitle(view.titulo());
            IEscenario controlador = ((IEscenario) fxml.getController());
            controlador.colocarEscenario(this);

            if (parametro != null) {
                controlador.colocarParametro(parametro);
            }

        } catch (IOException error) {
            System.err.println("ERROR ESCENA " + error.toString());
            System.err.println(error.getMessage());

        }
    }
    
    
    public Registry registroRMI() {
        return registro;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Application.launch(args);

    }

}
