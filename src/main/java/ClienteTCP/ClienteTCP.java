package ClienteTCP;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClienteTCP {
    //Atributos
    /**
     *Escribir Tipo de datos primitivos en un flujo de entrada
     */
    private DataInputStream input;

    /**
     *Proporciona almacenamiento en búfer de datos de entrada
     */
    private BufferedInputStream bis;

    /**
     *Proporciona almacenamiento en búfer de datos de salida
     */
    private BufferedOutputStream bos;

    /**
     * Entero
     */
    private int in;

    /**
     *Envuelve un Array de valores de tipo primitivo byte en un objeto
     */
    private byte[] byteArray;

    /**
     * Punto de comunicacion entre dos maquinas
     */
    private Socket enchufe;

    /**
     * Escribir Tipo de datos primitivos en un flujo de salida
     */
    private DataOutputStream dos;
}

