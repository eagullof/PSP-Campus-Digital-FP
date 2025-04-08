/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socketsseguros;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

/**
 *
 * @author eagullof
 */
public class Cliente {

    public static void main(String[] args) throws Exception {
        System.out.println("[Cliente] Iniciando conexión segura con el servidor...");

        // Cargar keystore (clave y certificado del cliente)
        System.out.println("[Cliente] Cargando keystore...");
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("cliente_keystore.jks"), "changeit".toCharArray());

        // Cargar truststore (certificado del servidor)
        System.out.println("[Cliente] Cargando truststore...");
        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(new FileInputStream("cliente_truststore.jks"), "changeit".toCharArray());

        // Crear gestores
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, "changeit".toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ts);

        // Crear contexto SSL
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        // Crear socket seguro hacia el servidor
        SSLSocketFactory ssf = sc.getSocketFactory();
        SSLSocket socket = (SSLSocket) ssf.createSocket("localhost", 8443);
        System.out.println("[Cliente] Conectado al servidor de forma segura.");

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String mensaje = "Hola servidor seguro, soy el cliente.";
        out.write(mensaje + "\n");
        out.flush();
        System.out.println("[Cliente] Mensaje enviado al servidor: " + mensaje);

        String respuesta = in.readLine();
        System.out.println("[Cliente] Respuesta recibida del servidor: " + respuesta);

        socket.close();
        System.out.println("[Cliente] Conexión cerrada.");
    }
}
