/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cliente;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 *
 * @author infoe
 */
public class Cliente {

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
      int valor = 0;

        try{
            //Rediregimos salida y error estándar a un fichero
            PrintStream ps = new PrintStream(
                             new BufferedOutputStream(new FileOutputStream(
                             new File("javalog_cliente.txt"),true)), true);
            System.setOut(ps);
            System.setErr(ps);
        }catch(Exception e){
            System.err.println("Cliente. No he podido redirigir salidas.");
        }
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
      while(valor<9){//leeremos 10 datos
         try{
            raf = new RandomAccessFile(archivo,"rwd"); //Abrimos el fichero
            //***************
            //Sección crítica
            bloqueo = raf.getChannel().lock();
            //bloqueamos el canal de acceso al fichero. Obtenemos el objeto que
            //representa el bloqueo para después poder liberarlo
            System.out.println("Cliente: ENTRA sección");
            // Lectura del fichero
            if (raf.length() != 0){
               valor = raf.readInt(); //leemos el valor
               raf.setLength(0); //vaciar el fichero
               System.out.println("Cliente: valor leído " +  valor );
            }else
                System.out.println("Cliente: no puede leer.");
            System.out.println("Cliente: SALE sección");
            bloqueo.release(); //Liberamos el bloqueo del canal del fichero
            bloqueo = null;
            //Fin sección crítica
            //*******************
            Thread.sleep(1000);//simulamos tiempo de procesamiento del dato
        }catch(Exception e){
            System.err.println("Cliente. Error al acceder al fichero.");
            System.err.println(e.toString());
        }finally{
            try{
                if( null != bloqueo) bloqueo.release();
                if( null != raf ) raf.close();
            }catch (Exception e2){
                System.err.println("Cliente. Error al cerrar el fichero.");
                System.err.println(e2.toString());
                System.exit(1);  //Si hay error, finalizamos
            }
        }        
        }

    }    
}
