package ServidorTCP;

import java.io.*;
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

    /**
     *
     */
    private DataInputStream dis;

    //Constructor

    public ServidorTCP() {
    }

    //Metodos
    /**
     * Metodoq que tendra todos los procedimientos que hace un servidorTCP
     */
    public void servidorTCP(){
        //Try-Catch para evitar posibles errores
        try{
            //Dejamos el objeto en el puerto de comunicacion 60000 (tiene un amplio rango de puertos)
            servidor = new ServerSocket(60000);

            //crear loop para mantener de manera infinita la comunicacion
            while (true){
                //Escucha para establecer una conexion a este socket
                enchufe = servidor.accept();
                //Buffer de 1024 bytes
                datosRecibidos = new byte[1024];
                //
                bis = new BufferedInputStream(enchufe.getInputStream());
                //Leer y escribir datos en el socket
                dis = new DataInputStream(enchufe.getInputStream());
                //Recibir nombre del fichero
                file = dis.readUTF();
                file = file.substring(file.indexOf('\\')+1,file.length());
                //Guardar fichero recibido
                bos = new BufferedOutputStream(new FileOutputStream(file));
                while ((in=bis.read(datosRecibidos))!=-1){
                    bos.write(datosRecibidos,0,in);
                }
                bos.close();
                dis.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
