package ServidorTCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    //Atributos
    /**
     *ServerSocket espera a que lleguen solicitudes a travez de la red
     */
    private ServerSocket servidor;

    /**
     *Punto de comunicacion entre dos maquinas
     */
    private Socket enchufe;

    /**
     *Escribir Tipo de datos primitivos en un flujo de salida
     */
    private DataOutputStream salida;

    /**
     *Proporciona almacenamiento en búfer de datos de entrada
     */
    private BufferedInputStream bis;

    /**
     *Proporciona almacenamiento en búfer de datos de salida
     */
    private BufferedOutputStream bos;

    /**
     *Envuelve un valor de tipo primitivo byte en un objeto
     */
    private byte[] datosRecibidos;

    /**
     * Entero
     */
    private int in;

    /**
     * String
     */
    private String file;
}
