package ClienteTCP;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class ClienteTCP {
    //Atributos
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

    //ruta del fichero que se quiere transferir
    private String filename;

    //Nombre del archivo que se quiere transferir
    private String nombre;

    //varaible para seguir o no con el programa
    private boolean segui = true;

    //seleccion del usuarioo
    private int seleccion;

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
                //Mensaje
                System.out.println("Ingrese nombre del archivo que quiere subir");
                //Respuesta
                nombre = teclado.next();
                filename = "F:\\Tareas\\ArchivosCliente\\" + nombre;
                //enviar un archivo
                final File localFile = new File(filename);
                enchufe = new Socket("localhost", 57000);
                bis = new BufferedInputStream(new FileInputStream(localFile));
                bos = new BufferedOutputStream(enchufe.getOutputStream());
                //Enviar el nombre del fichero
                dos = new DataOutputStream(enchufe.getOutputStream());
                dos.writeUTF(localFile.getName());
                //Enviar fichero
                byteArray = new byte[8192];
                while ((in = bis.read(byteArray)) != -1) {
                    bos.write(byteArray, 0, in);
                }
                bis.close();
                bos.close();
                System.out.println("Desea subir otro archivo 1: si");
                seleccion = teclado.nextInt();
                if (seleccion==1){
                    segui = true;
                }else{
                    segui=false;
                }
            }
        } catch (Exception e) {
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


