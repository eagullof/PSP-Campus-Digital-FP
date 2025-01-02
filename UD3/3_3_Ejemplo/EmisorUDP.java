import java.net.*;
import java.io.*;

public class EmisorUDP {
    public static void main(String args[]) {
        // Comprueba que se pasen exactamente 2 argumentos: una máquina y un mensaje
        if (args.length != 2) {
            System.err.println("Uso: java EmisorUDP maquina mensaje");
        } else try {
            // Crea el socket UDP para enviar el mensaje
            DatagramSocket sSocket = new DatagramSocket();

            // Obtiene la dirección IP de la máquina a la que se enviará el mensaje
            InetAddress maquina = InetAddress.getByName(args[0]);
            int Puerto = 1500;  // Establece el puerto 1500 para la transmisión

            // Convierte el mensaje a un arreglo de bytes
            byte[] cadena = args[1].getBytes();

            // Crea el paquete de datos a enviar, con el mensaje, su longitud, la dirección de la máquina y el puerto
            DatagramPacket mensaje = new DatagramPacket(cadena, args[1].length(), maquina, Puerto);

            // Envía el paquete de datos al receptor
            sSocket.send(mensaje);

            // Cierra el socket una vez enviado el mensaje
            sSocket.close();
        } catch (UnknownHostException e) {
            // Captura el error si la máquina es desconocida
            System.err.println("Desconocido: " + e.getMessage());
        } catch (SocketException e) {
            // Captura cualquier error relacionado con el socket
            System.err.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            // Captura cualquier error de entrada/salida
            System.err.println("E/S: " + e.getMessage());
        }
    }
}
