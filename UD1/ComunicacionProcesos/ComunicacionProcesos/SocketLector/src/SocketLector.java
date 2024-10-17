
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author infoe
 */
public class SocketLector {

/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Socket canal = null; //Socket para establecer el canal de conexión con el escritor
        BufferedReader entrada = null; //Para el stream de lectura
        String valorEntrada = null; //Valores que iremos leyendo del canal        
        try{
            canal = new Socket("localhost", 12345 );
            //Pedimos establecer una conexión en el equipo local con el puerto 12345
            //donde debe estar escuchando el proceso escritor
            }catch (Exception ex){
                System.err.println("No se ha podido establecer conexión.");
                System.err.println(ex.toString());
        }
        if (canal != null)  //Si hemos podido establecer la conexión. Tenemos
                            //un canal de comunicación
            try{
               entrada = new BufferedReader(new InputStreamReader(canal.getInputStream()));
               //Obtemenos el objeto que representa el strean de entrada en el canal
               //Lector con buffer, para no perder ningún dato
               while ((valorEntrada = entrada.readLine())!= null){
                   //Mientras que haya datos que leer
                   System.out.println(valorEntrada);
                   System.out.println("**");
               }                  
            }catch (Exception ex){
               System.err.println("No se ha podido establecer conexión.");
               System.err.println(ex.toString());
            }finally{
                //Nos aseguramos de que se cierran los recursos que estamos utilizando
                if (entrada != null)
                    try{
                        entrada.close();
                    }catch (IOException ex) {
                        System.err.println("Se ha producido un error al cerrar el InputStreamReader.");
                        System.err.println(ex.toString());
                    }
                if (canal != null)
                    try{
                        canal.close();
                    }catch (IOException ex) {
                        System.err.println("Se ha producido un error al cerrar el Socket.");
                        System.err.println(ex.toString());
                    }
            }
    }

    
}
