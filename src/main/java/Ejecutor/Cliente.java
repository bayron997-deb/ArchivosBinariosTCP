package Ejecutor;

import ClienteTCP.ClienteTCP;

public class Cliente {
    //Atributos
    /**
     * Objeto de la clase ClienteTCP
     */
    public static ClienteTCP cliente = new ClienteTCP();

    /**
     * Metodo para ejecutar cliente
     * @param args
     */
    public static void main(String[] args) {
        //mensaje de bienvenida
        System.out.println("Bienvenido cliente");
        cliente.clienteTCP();
    }
}
