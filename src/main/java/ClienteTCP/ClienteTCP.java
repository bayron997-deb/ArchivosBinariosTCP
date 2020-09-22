package ClienteTCP;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
    private String filename;

    /**
     * nombre del coso
     */
    private String nombre;

    /**
     * contenido del coso
     */
    private String contenido;

    /**
     *
     */
    private Scanner teclado = new Scanner(System.in);
    //Constructor

    public ClienteTCP() {
    }

    //Metodo
    public void clienteTCP(){
        try{
            //crear un archivo binario
            System.out.println("introduce un nombre");
            nombre = teclado.next();
            FileOutputStream fos = new FileOutputStream("F:\\Tareas\\Programacion\\ArchivosBinariosTCP\\ArchivosCliente\\"+nombre+".dat");
            DataOutputStream salida = new DataOutputStream(fos);
            System.out.println("introduce un contenido");
            contenido = teclado.next();
            salida.writeUTF(contenido);
            filename = "F:\\Tareas\\Programacion\\ArchivosBinariosTCP\\ArchivosCliente\\"+nombre+".dat";
            //enviar un archivo
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


