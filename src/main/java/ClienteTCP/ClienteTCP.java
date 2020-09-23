package ClienteTCP;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class ClienteTCP {
    //Atributos
    //lectura y almacenamiento de datos en buffer de entrada
    private BufferedInputStream bis;

    //Buffer interno para almacenar datos de salida
    private BufferedOutputStream bos;

    //Numero entero
    private int in;

    //Envuelve un Array de valores de tipo primitivo byte en un objeto
    private byte[] byteArray;

    //Socket para conectar al servidor
    private Socket cliente;

    //Puerto de conexion
    private int port = 65000;

    //Direccion Ip maquina
    private String address = "localhost";

    //Flujo de entrada de datos
    private  DataInputStream dis;

    //Flujo de datos de salida
    private DataOutputStream dos;

    //ruta del fichero que se quiere transferir
    private String filename;

    //Nombre del archivo que se quiere transferir
    private String nombre;

    //varaible para seguir o no con el programa
    private boolean segui = true;

    //Scanner
    private Scanner teclado = new Scanner(System.in);

    //Constructor
    public ClienteTCP() {
    }

    //Metodos

    /**
     * Contiene instrucciones que debe ejecutar el cliente
     */
    public void clienteTCP(){
        try{
            while (segui) {
                //Mostrar contenido directorio
                mostrarDirectorio();

                //Mensaje del archivo que se quiere transferir
                System.out.println("Ingrese nombre del archivo que quiere subir");
                //Respuesta
                nombre = teclado.next();

                //ruta del archivo que se va a transferir
                filename = "F:\\Tareas\\ArchivosCliente\\" + nombre;
                //Crear un localfile de tipo File con la ruta del archivo
                File localFile = new File(filename);

                //Creamos un socket de transmision en la direccion y puerto especifico
                cliente = new Socket(address, port);

                //Leemos el flujo de datos de localfile y los almacenamos en el buffer de entrada
                bis = new BufferedInputStream(new FileInputStream(localFile));
                //almacenamos el flujo de salida del socket
                bos = new BufferedOutputStream(cliente.getOutputStream());

                //Enviar el nombre del fichero al servidor

                //Crea un flujo de salida de datos para escribir datos
                dos = new DataOutputStream(cliente.getOutputStream());//flujo de datos hacia el servidor
                //crea un flujo de datos de entrada para leer datos
                dis = new DataInputStream(cliente.getInputStream());
                //escribe en UTF el nombre del archivo que se va a transferir
                dos.writeUTF(localFile.getName());
                //Enviar fichero

                //buffer para leer y escribir bloques de 8kb
                byteArray = new byte[8192];
                //loop para escribir en bystes
                while ((in = bis.read(byteArray)) != -1) { //lee todos los bytes hasta que devuelve -1, marca termino del archivo
                    //almacena los bytes ecritos en byteArray en el buffer de salida
                    bos.write(byteArray, 0, in);
                }
                //libera recursos y cierra el flujo de entrada
                bis.close();
                //libera recursos y cierra el flujo de salida
                bos.close();
            }
        } catch (Exception e) {
            //mensaje de error
            System.err.println(e);
        }
    }

    /**
     * Metodo para mostrar contenido del directorio
     */
    public void mostrarDirectorio(){
        //Crear objeto de clase File y le pasamos la ruta del directorio
        File directorio = new File("F:\\Tareas\\ArchivosCliente\\");
        //Almacenamos el contenido del directorio en un arreglo de String
        String[] list = directorio.list();
        //Ordenamos alfabeticamente
        Arrays.sort(list);
        //Imprimir contenido del directorio
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);

        }
    }
}


