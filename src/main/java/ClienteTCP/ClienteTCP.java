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

    //Envuelve un Array de valores de tipo primitivo byte en un objeto ( 8kb )
    private byte[] byteArray = new byte[8192];

    //Socket para conectar al servidor
    private Socket cliente;

    //Puerto de conexion
    private int port = 64000;

    //Direccion Ip maquina
    private String address = "localhost";

    //Flujo de entrada de datos
    private DataInputStream dis;

    //Flujo de datos de salida
    private DataOutputStream dos;

    //ruta del fichero que se quiere transferir
    private String ruta;

    //Nombre del archivo que se quiere transferir
    private String nombre;

    //varaible para seguir o no con el programa
    private boolean segui = true;

    //Scanner
    private Scanner teclado = new Scanner(System.in);

    //Respuesta servidor
    private boolean respuesta;
    //Mensajes
    private String mensaje;

    //Constructor
    public ClienteTCP() {
    }

    //Metodos

    /**
     * Contiene instrucciones que debe ejecutar el cliente
     */
    public void clienteTCP() {
        try {
            while (segui) {
                //Socket cliente en una direccion y puertoo especifico
                cliente = new Socket(address, port); //(se conecta)***

                //Menu de seleccion
                menu();

                //Respuesta del archivo que se quiere transferir
                nombre = teclado.next();

                //ruta del archivo que se va a transferir
                ruta = "F:\\Tareas\\ArchivosCliente\\" + nombre;

                //Crear un archivo con la ruta
                File archivoEnvio = new File(ruta);


                //Creamos un nuevo buffer para leer y almacenar los datos de archivoEnvio
                bis = new BufferedInputStream(new FileInputStream(archivoEnvio));
                //Creamos un nuevo buffer para almacenar y escribir en el flujo de salida del socket
                bos = new BufferedOutputStream(cliente.getOutputStream());

                //Crea un flujo de salida  para escribir datos en la entrada del socket
                dos = new DataOutputStream(cliente.getOutputStream());//---------------------->flujo de datos hacia el servidor desde cliente
                //crea un flujo de datos de entrada para leer datos de la salida del socket
                dis = new DataInputStream(cliente.getInputStream()); //----------------------->flujo de datos desde servidor a cliente

                //Envio de nombre de archivo que se quiere pasar

                //escribe en UTF el nombre del archivo que se va a transferir
                dos.writeUTF(archivoEnvio.getName());//---------------------------------> Primera solicitud de cliente a servido. ¿Puedo subir este archivo?

                //Respuesta del servdor a la solicitud
                respuesta = dis.readBoolean();//---------------------------->Almacena la respuesta del servidor si se puede

                //Si Respuesta es valida no existe archivo con igual nombre
                if (respuesta == true) {
                    System.out.println("Servidor aceptó su solicitud");

                    //Enviar fichero

                    //loop para escribir en bytes
                    while ((in = bis.read(byteArray)) != -1) { //lee todos los bytes hasta que devuelve -1 que marca termino del archivo
                        //almacena los bytes ecritos en byteArray en el buffer de salida
                        bos.write(byteArray, 0, in);//--------------------------------------->Solicitud de escritura
                    }
                    //cerrar los flujos de datos
                    bis.close();
                    bos.close();
                    dos.close();
                    dis.close();
                }
                //Si respuesta es no valida Existe un archivo con igual nombre
                if (respuesta == false) {
                    //recibir flujo de datos de salida del servidor
                    mensaje = dis.readUTF();//--------------> recibimos solicitud de respuesta de confirmacion
                    //mensaje del servidor
                    System.out.println(mensaje);
                    //cerrar los flujos de datos
                    bis.close();
                    bos.close();
                    dos.close();
                    dis.close();

                }
            }
        } catch (Exception e) {
            //mensaje de error
            System.err.println(e);

        }
    }

    /**
     * Metodo para mostrar contenido del directorio
     */
    public void mostrarDirectorio() {
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

    public void menu() {
        //Mensaje del archivo que se quiere transferir
        System.out.println("En su directorio tiene estos archivos");
        //Mostrar contenido directorio
        mostrarDirectorio();
        System.out.println("Ingrese el nombre del archivo que desea transferir al servidor");
    }
}


