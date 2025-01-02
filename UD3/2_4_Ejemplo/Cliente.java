import java.io.*;   
import java.net.*;  

class Cliente {
    static final String HOST = "localhost";  // Dirección IP del servidor, en este caso se conecta a localhost (máquina local)
    static final int Puerto = 2000;         // Puerto al que se conecta el cliente

    public Cliente() {  
        try {
            // Se establece una conexión al servidor en el puerto 2000 de la máquina local
            Socket sCliente = new Socket(HOST, Puerto);

            // Se obtiene un flujo de entrada para leer datos del servidor
            InputStream aux = sCliente.getInputStream(); 
            DataInputStream flujo_entrada = new DataInputStream(aux);

            // Se imprime el mensaje recibido del servidor (en este caso, el servidor enviará un mensaje de saludo)
            System.out.println(flujo_entrada.readUTF());

            // Se cierra la conexión con el servidor
            sCliente.close();
        } catch (Exception e) {
            // Si ocurre algún error, se imprime el mensaje de error
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] arg) {
        new Cliente();  // Crear una nueva instancia de la clase Cliente para que se ejecute el programa
    }
}
