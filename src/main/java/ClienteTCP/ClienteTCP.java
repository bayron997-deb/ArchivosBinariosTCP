package ClienteTCP;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
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
    private String nombre;
    private boolean segui = true;
    private int seleccion;

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
            while (segui) {
                mostrarDirectorio();
                System.out.println("Ingrese nombre mas la extension");
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
    public void mostrarDirectorio(){
        File directorio = new File("F:\\Tareas\\ArchivosCliente\\");
        String[] list = directorio.list();
        Arrays.sort(list);
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);

        }
    }
}


