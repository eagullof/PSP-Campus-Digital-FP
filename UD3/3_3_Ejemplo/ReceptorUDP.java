import java.net.*;
import java.io.*;

public class ReceptorUDP {
    public static void main(String args[]) {
        // Si se pasan argumentos, el programa muestra un mensaje de error y termina
        if (args.length != 0) {
            System.err.println("Uso: java ReceptorUDP");
        } else try {
            // Crea el socket UDP en el puerto 1500 para recibir mensajes
            DatagramSocket sSocket = new DatagramSocket(1500);

            // Crea un buffer para almacenar los datos del mensaje recibido
            byte[] cadena = new byte[1000];

            // Define el objeto DatagramPacket para recibir el mensaje. Usa el buffer para almacenarlo.
            DatagramPacket mensaje = new DatagramPacket(cadena, cadena.length);

            System.out.println("Esperando mensajes..");

            // Inicia un bucle infinito para recibir mensajes continuamente
            while (true) {
                // Recibe un paquete de datos desde el socket y lo almacena en el objeto 'mensaje'
                sSocket.receive(mensaje);

                // Convierte el mensaje recibido a una cadena, usando la longitud del mensaje
                String datos = new String(mensaje.getData(), 0, mensaje.getLength());

                // Muestra el mensaje recibido en la consola
                System.out.println("\tMensaje Recibido: " + datos);
            }
        } catch (SocketException e) {
            // En caso de error al crear el socket, imprime el mensaje de error
            System.err.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            // En caso de error de entrada/salida al recibir los datos, imprime el mensaje de error
            System.err.println("E/S: " + e.getMessage());
        }
    }
}
