package ServidorTCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    //Atributos

    //ServerSocket espera a que lleguen solicitudes a traves de la red
    private ServerSocket servidor=null;

    //Socket cliente
    private Socket cliente=null;

    //lectura y almacenamiento de datos en buffer entrada
    private BufferedInputStream bis = null;

    //Buffer interno para almacenar datos de salida
    private BufferedOutputStream bos = null;

    //Envuelve un Array de valores de tipo primitivo byte en un objeto (1kb)
    private byte[] datosRecibidos = new byte[1024];

    //Numero entero
    private int in;

    //Ruta donde se almacena el archivo
    private String ruta;

    //String nombre Archivo
    private String nombreArchivo;

    //permite leer tipos de datos primitivos de un flujo de datos de entrada
    private DataInputStream dis = null;

    //Permite escribir tipo de datos primitivos en un flujo de datos de salida
    private DataOutputStream dos = null;

    //puerto de conexion
    private int port = 64000;

    //Constructor
    public ServidorTCP() {
    }

    //Metodos

    /**
     * Metodoq que tendra todos los procedimientos que hace un servidorTCP
     */
    public void servidorTCP() {
        //Try-Catch para evitar posibles errores
        try {
            //Crea un socket de servidor con el puerto especifico
            servidor = new ServerSocket(port);

            //crear loop para mantener de manera infinita la comunicacion
            while (true) {
                //Escucha para establecer una conexion a este socket
                cliente = servidor.accept();
                System.out.println("Cliente aceptado");

                //Creamos un buffer que lee la salida del socket y la almacena en el buffer de entrada
                bis = new BufferedInputStream(cliente.getInputStream());

                //crea un flujo de datos de entrada para leer datos de salida del socket
                dis = new DataInputStream(cliente.getInputStream());

                //Crea un flujo de salida  para escribir datos en la entrada del socket
                dos = new DataOutputStream(cliente.getOutputStream());

                //Recibo nombre de cliente

                //Recibimos el nombre en UTF del archivo
                nombreArchivo = dis.readUTF();//----------------->Recibimos la informacion de la primera solicitud

                System.out.println("El nombre del archivo a transferir es " + nombreArchivo);

                //le damos una ruta al archivo
                ruta = "F:\\Tareas\\Programacion\\ArchivosBinariosTCP\\ArchivosServidor\\" + nombreArchivo;

                System.out.println("La ruta del archivo sera " + ruta);

                //Crea un nuevo archivo con la ruta que le asignamos
                File archivoRecibo = new File(ruta);

                //verificador si existe o no el archivo
                if (!archivoRecibo.exists()) {
                    //Escribe en el flujo de salida un boleano como respuesta
                    dos.writeBoolean(true); //------------------------------> Responder con un boleano a cliente si se puede procesar su solicitud
                    System.out.println("No existe archivo con el nombre " + nombreArchivo + "... subiendo archivo");

                    //Guardar fichero recibido

                    //crea un nuevo buffer de salida con la secuencia de salida de archivo para escribir en el archivorecibo
                    bos = new BufferedOutputStream(new FileOutputStream(ruta));

                    //Loop que lee todos los datos recibidos
                    while ((in = bis.read(datosRecibidos))!= -1){ //-----------------> recibe los datos que el cliente escribe (-1 indica final de la secuencia de datos recibidos)
                        //almacena los bytes ecritos en byteArray en el buffer de salida
                        bos.write(datosRecibidos,0,in);
                    }
                    //cerrar los flujos de datos
                    bis.close();
                    bos.close();
                    dos.close();
                    dis.close();
                    cliente.close();
                    System.out.println("El archivo se ha recibido por completo y sin errores");
                    System.out.println("Cliente desonectado");
                } else {
                    //Mensaje al servidor
                    dos.writeBoolean(false); //------------------------------> Responder con un boleano a cliente si se puede procesar su solicitud

                    dos.writeUTF("El archivo '"+archivoRecibo.getName()+"' ya existe en el servidor"+"\n");//-----------> solicitud de respuesta al cliente

                    System.out.println("Servidor contiene el archivo");

                    //cerrar los flujos de datos
                    dos.close();
                    dis.close();
                    cliente.close();
                    System.out.println("Cliente desonectado");
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
