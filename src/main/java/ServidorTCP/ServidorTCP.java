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
    private int port = 65000;

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
            //Crea un socket de servidor con el puerto especifico
            servidor = new ServerSocket(port);

            //crear loop para mantener de manera infinita la comunicacion
            while (true){
                //Escucha para establecer una conexion a este socket
                cliente = servidor.accept();
                //Buffer de 1024 bytes
                datosRecibidos = new byte[1024];
                //leemos el flujo de entrada del socket cliente y almacenamos en buffer de entrada
                bis = new BufferedInputStream(cliente.getInputStream());
                //Leer el flujo de entrada del socket cliente
                    dis = new DataInputStream(cliente.getInputStream());
                //escribir en el flujo de salida del socket cliente
                dos = new DataOutputStream(cliente.getOutputStream());
                //Recibimos el nombre en UTF del archivo y creamos un nombre de archivo
                nombreArchivo = dis.readUTF();
                //le damos una ruta al archivo
                file = "F:\\Tareas\\Programacion\\ArchivosBinariosTCP\\ArchivosServidor\\"+ nombreArchivo;
                //Crea un nuevo archivo con el nombre que le pasamos en UTF
                File as = new File(file);
                //si no existe el archivo ejecuta el if
                if (!as.exists()){
                    //Guardar fichero recibido
                    //crea un nuevo buffer de salida con la secuencia de salida de archivo para escribir en el archivo especificado
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    //loop para escribir datatos en el buffer de salida
                    while ((in=bis.read(datosRecibidos))!=-1){ //lee todos los bytes recibidos hasta que devuelve -1, marca termino del archivo
                        //almacena los bytes ecritos en byteArray en el buffer de salida
                        bos.write(datosRecibidos,0,in);
                    }
                    //libera recursos y cierra el flujo de salida
                    bos.close();
                    //libera recursos y cierra el flujo de entrada
                    dis.close();
                    cliente.close();
                }else{
                    //mensaje si archivo existe
                    dos.writeUTF("El archivo "+nombreArchivo+" no puede transferirse porque ya existe");
                    cliente.close();
                }

            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
