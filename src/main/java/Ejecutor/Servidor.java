package Ejecutor;

import ServidorTCP.ServidorTCP;

public class Servidor {
    //Atributo
    /**
     * Objeto de la clase ServidorTCP
     */
    public static ServidorTCP server = new ServidorTCP();
    /**
     * Metodo para ejecutar Server
     * @param args
     */
    public static void main(String[] args) {
        //mensaje de bienvenida
        System.out.println("Bienvenido Servidor");
        server.servidorTCP();
    }
}
