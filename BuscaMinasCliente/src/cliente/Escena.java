/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author fywer
 */
public enum Escena {

    INICIAR_SESION {
        @Override
        String titulo() {
            return "Iniciar Sesión";
        }

        @Override
        String archivo() {
            return "/vista/FXMLIniciarSesion.fxml";
        }
    },
    CREAR_CUENTA {
        @Override
        String titulo() {
            return "Crear Cuenta";
        }

        @Override
        String archivo() {
            return "/vista/FXMLCrearCuenta.fxml";
        }
    },
    MULTIJUGADOR {
        @Override
        String titulo() {
            return "Multijugador";
        }

        @Override
        String archivo() {
            return "/vista/FXMLMultijugador.fxml";
        }
    },
    INICIAR_TABLERO {
        @Override
        String titulo() {
            return "Iniciar Juego";
        }

        @Override
        String archivo() {
            return "/vista/FXMLIniciarTablero.fxml";
        }
    };
    abstract String titulo();
    abstract String archivo();
 
}
