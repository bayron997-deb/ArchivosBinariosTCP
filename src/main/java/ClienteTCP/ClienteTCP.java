package ClienteTCP;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

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

    /**
     * Fichero que se quiere transferir
     */
    final String filename = "F:\\Tareas\\Programacion\\ArchivosBinariosTCP\\ArchivosCliente\\Hola.txt.txt";
    //Constructor

    public ClienteTCP() {
    }

    //Metodo
    public void clienteTCP(){
        try{
            final File localFile = new File(filename);
            enchufe = new Socket("localhost",59000);
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bos = new BufferedOutputStream(enchufe.getOutputStream());
            //Enviar el nombre del fichero
            dos = new DataOutputStream(enchufe.getOutputStream());
            dos.writeUTF(localFile.getName());
            //Enviar fichero
            byteArray = new byte[8192];
            while ((in=bis.read(byteArray))!=-1){
                bos.write(byteArray,0,in);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}


