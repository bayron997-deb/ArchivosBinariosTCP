package ServidorTCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    //Atributos

    //ServerSocket espera a que lleguen solicitudes a travez de la red
    private ServerSocket servidor;

    //Socket cliente
    private Socket cliente;

    //lectura y almacenamiento de datos en buffer entrada
    private BufferedInputStream bis;

    //Buffer interno para almacenar datos de salida
    private BufferedOutputStream bos;

    //Envuelve un Array de valores de tipo primitivo byte en un objeto
    private byte[] datosRecibidos;

    //Numero entero
    private int in;

    //String llamado file
    private String file;

    //String nombre Archivo
    private String nombreArchivo;

    //permite leer tipos de datos primitivos de un flujo de datos de entrada
    private DataInputStream dis;

    //Permite escribir tipo de datos primitivos en un flujo de datos de salida
    private DataOutputStream dos;

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

                //Buffer de 1024 bytes
                datosRecibidos = new byte[8192];

                //leemos el flujo de entrada del socket cliente y almacenamos en buffer de entrada
                bis = new BufferedInputStream(cliente.getInputStream());

                //Leer el flujo de entrada del socket cliente
                dis = new DataInputStream(cliente.getInputStream());

                //escribir en el flujo de salida del socket cliente
                dos = new DataOutputStream(cliente.getOutputStream());

                //Recibimos el nombre en UTF del archivo y creamos un nombre de archivo
                nombreArchivo = dis.readUTF();//(Recibimos nombre desde el cliente) 1 solicitud

                System.out.println("El nombre del archivo a transferir es " + nombreArchivo);

                //le damos una ruta al archivo
                file = "F:\\Tareas\\Programacion\\ArchivosBinariosTCP\\ArchivosServidor\\" + nombreArchivo;
                System.out.println("La ruta del archivo sera " + file);

                //Crea un nuevo archivo con la ruta que le asignamos
                File as = new File(file);

                //verificador si existe o no el archivo
                if (!as.exists()) {
                    dos.writeBoolean(true); //(Mandamos señal al cliente de que no existe el archivo) 1 respuesta
                    System.out.println("No existe archivo con el nombre " + nombreArchivo + "... subiendo archivo");
                    //Guardar fichero recibido

                    //crea un nuevo buffer de salida con la secuencia de salida de archivo para escribir en el archivo especificado
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    //loop para escribir datatos en el buffer de salida
                    while ((in = bis.read(datosRecibidos)) != -1) { //lee todos los bytes recibidos hasta que devuelve -1, marca termino del archivo 1 Solicitud
                        //almacena los bytes ecritos en byteArray en el buffer de salida
                        bos.write(datosRecibidos, 0, in);
                        System.out.println("hshhshhshshhs");
                    }
                    dos.writeUTF("El archivo se ha recibido por completo y sin errores se llama " + as.getName()); //1 respuesta
                    System.out.println("El archivo se ha recibido por completo y sin errores");//SOLITUD
                } else {
                    dos.writeBoolean(false);//(mandamos señal para que no ejecute) 1R
                    System.out.println("Si existe archivo con el nombre " + nombreArchivo);

                    //Escribe mensaje en el flujo de salido para confirmar el archivo ya existe
                    dos.writeUTF("Servidor: no se puede subir el archivo " + nombreArchivo + " porque ya existe\n"); //1Respuesta
                }

            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
