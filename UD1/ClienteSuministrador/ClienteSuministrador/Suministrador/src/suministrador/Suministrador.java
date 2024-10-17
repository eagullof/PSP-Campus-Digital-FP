/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package suministrador;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 *
 * @author infoe
 */
public class Suministrador {

    /**
     * @param args Argumentos de la línea de comando
     * El primer argumento pasado, será la ruta del fichero
     */
    public static void main(String[] args) {
      int orden = 0;
      String nombreFichero = "";
      File archivo = null;
      RandomAccessFile raf = null;
      FileLock bloqueo = null;
      
       //Identificamos el sistema operativo para poder acceder por su ruta al
      //fichero de forma correcta.
      String osName = System.getProperty("os.name");
      if (osName.toUpperCase().contains("WIN")){ //Windows
        if (args.length > 0)
            nombreFichero = args[0];
            //Hemos recibido la ruta del fichero en la línea de comandos
        else{
            nombreFichero = "buffer.txt";
            //Fichero que se utilizará por defecto
          }
      }else{ //GNU/Linux
          if (args.length > 0)
            nombreFichero = args[0];
          //Hemos recibido la ruta del fichero en la línea de comandos
          else{
               nombreFichero = "buffer.txt";
               //Fichero que se utilizará por defecto
        }
      }
      //Preparamos el acceso al fichero
      archivo = new File(nombreFichero);
      int i = 0;
      while(i<10){//escribiremos 10 datos
         try{
            raf = new RandomAccessFile(archivo,"rwd"); //Abrimos el fichero
            //***************
            //Sección crítica
            bloqueo = raf.getChannel().lock();
            //bloqueamos el canal de acceso al fichero. Obtenemos el objeto que
            //representa el bloqueo para después poder liberarlo
            System.out.println("Suministrador: ENTRA sección");
            // Lectura del fichero
            if (raf.length() == 0){
               raf.writeInt(i); //escribimos el valor
               System.out.println("Suministrador: valor escrito " + i);
               i ++;
            }else
                System.out.println("Suministrador: no puede escribir. " + i);
            System.out.println("Suministrador: SALE sección");
            bloqueo.release(); //Liberamos el bloqueo del canal del fichero
            bloqueo = null;
            //Fin sección crítica
            //*******************
            Thread.sleep(500); //Simulamos tiempo de creación del dato
        }catch(Exception e){
            System.err.println("Suministrador. Error al acceder al fichero.");
            System.err.println(e.toString());
        }finally{
            try{
                if( null != bloqueo) bloqueo.release();
                if( null != raf ) raf.close();                
            }catch (Exception e2){
                System.err.println("Suministrador. Error al cerrar el fichero.");
                System.err.println(e2.toString());
                System.exit(1);  //Si hay error, finalizamos
            }
        }
        }

    }

}
