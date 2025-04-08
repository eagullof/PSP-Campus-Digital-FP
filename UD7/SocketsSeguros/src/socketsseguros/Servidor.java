/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package socketsseguros;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;

/**
 *
 * @author eagullof
 */

public class Servidor {

    public static void main(String[] args) throws Exception {
        System.out.println("[Servidor] Inicializando servidor seguro...");

        // Cargar keystore (clave y certificado del servidor)
        System.out.println("[Servidor] Cargando keystore...");
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("servidor_keystore.jks"), "changeit".toCharArray());

        // Cargar truststore (certificados confiables de clientes)
        System.out.println("[Servidor] Cargando truststore...");
        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(new FileInputStream("servidor_truststore.jks"), "changeit".toCharArray());

        // Crear gestores de claves y confianza
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, "changeit".toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ts);

        // Crear contexto SSL
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        // Crear socket seguro
        SSLServerSocketFactory ssf = sc.getServerSocketFactory();
        SSLServerSocket servidor = (SSLServerSocket) ssf.createServerSocket(8443);
        servidor.setNeedClientAuth(true);

        System.out.println("[Servidor] Servidor esperando conexiones seguras en el puerto 8443...");

        while (true) {
            SSLSocket cliente = (SSLSocket) servidor.accept();
            System.out.println("[Servidor] Conexión aceptada desde: " + cliente.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));

            String mensajeCliente = in.readLine();
            System.out.println("[Servidor] Mensaje recibido del cliente: " + mensajeCliente);

            String respuesta = "Hola cliente seguro, tu mensaje fue recibido.";
            out.write(respuesta + "\n");
            out.flush();
            System.out.println("[Servidor] Respuesta enviada al cliente.");

            cliente.close();
            System.out.println("[Servidor] Conexión cerrada.");
            System.out.println("-------------------------------------------");
        }
    }
}
