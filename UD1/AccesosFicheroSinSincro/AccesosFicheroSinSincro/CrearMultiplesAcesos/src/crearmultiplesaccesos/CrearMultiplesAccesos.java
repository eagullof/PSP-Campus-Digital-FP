/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package crearmultiplesaccesos;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author infoe
 */
public class CrearMultiplesAccesos {

     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 
        ProcessBuilder processBuilder; // Definimos una variable de tipo ProcessBuilder
        PrintStream ps;

        try {
            // Redirigir la salida estándar y de error a un archivo
            ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File("javalog.txt"), true)), true);
            System.setOut(ps);
            System.setErr(ps);

            for (int i = 0; i <= 20; i++) {
                // Crear el ProcessBuilder
                processBuilder = new ProcessBuilder("java", "-jar", "AccesoMultipleFichero.jar", String.valueOf(i), "nuevo.txt");

                // Iniciar el nuevo proceso
                Process nuevoProceso = processBuilder.start();

                System.out.println("Creado el proceso " + i);
                // Mostramos en consola que hemos creado otro proceso
            }
        } catch (SecurityException ex) {
            System.err.println("Ha ocurrido un error de Seguridad: " + ex.getMessage() +
                    ". No se ha podido crear el proceso por falta de permisos.");
        } catch (IOException ex) {
            System.err.println("Ha ocurrido un error de E/S, descripción: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Ha ocurrido un error, descripción: " + ex.toString());
        }
    }
    
}
