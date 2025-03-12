/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliente;

import java.io.*;
import java.net.*;
import java.util.Date;

class Cliente {

    static final String HOST = "localhost";
    static final int Puerto = 2000;

    public Cliente() {
        String datos = new String();
        String num_cliente = new String();

        // para leer del teclado
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            // Me conecto al puerto
            Socket sCliente = new Socket(HOST, Puerto);

            // Creo los flujos de entrada y salida
            DataInputStream flujo_entrada = new DataInputStream(sCliente.getInputStream());
            DataOutputStream flujo_salida = new DataOutputStream(sCliente.getOutputStream());

            // CUERPO DEL ALGORITMO
            datos = flujo_entrada.readUTF();
            long tiempo1 = Long.valueOf(datos);
            long tiempo2 = (new Date()).getTime();
            System.out.println("\n El tiempo es:" + (tiempo2 - tiempo1) + " milisegundos.");

            // Se cierra la conexión
            sCliente.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] arg) {
        new Cliente();
    }
}
