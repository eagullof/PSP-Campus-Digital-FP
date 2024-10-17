package crearmultiplesaccesos;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;

/**
 *
 * @author infoe
 */
public class CrearMultiplesAccesos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nombreFichero;
        File archivo = null;
        RandomAccessFile raf = null;

        // Identificamos el sistema operativo para acceder por su ruta al
        // fichero de forma correcta.
        String osName = System.getProperty("os.name");
        if (osName.toUpperCase().contains("WIN")) { // Windows
            nombreFichero = (args.length > 0) ? args[0] : "valor.txt";
        } else { // GNU/Linux
            nombreFichero = (args.length > 0) ? args[0] : "valor.txt";
        }

        // Redirigimos salida estándar y de error a un fichero
        try {
            PrintStream ps = new PrintStream(
                    new BufferedOutputStream(new FileOutputStream(new File("javalog.txt"), true)), true);
            System.setOut(ps);
            System.setErr(ps);
        } catch (Exception e) {
            System.err.println("Error al redirigir las salidas: " + e.toString());
        }

        archivo = new File(nombreFichero);
        // Preparamos el acceso al fichero
        if (!archivo.exists()) {
            // Si no existe el fichero
            try {
                archivo.createNewFile(); // Lo creamos
                raf = new RandomAccessFile(archivo, "rw"); // Abrimos el fichero
                raf.writeInt(0); // Escribimos el valor inicial 0
                System.out.println("Creado el fichero.");
            } catch (Exception e) {
                System.err.println("Error al crear el fichero: " + e.toString());
            } finally {
                try { // Asegurarnos que se cierra el fichero.
                    if (raf != null)
                        raf.close();
                } catch (Exception e2) {
                    System.err.println("Error al cerrar el fichero: " + e2.toString());
                    System.exit(1); // Si hay error, finalizamos
                }
            }
        }

        // Creamos un grupo de procesos que accederán al mismo fichero
        try {
            for (int i = 0; i <= 25; i++) {
                // Usamos ProcessBuilder para crear el nuevo proceso
                ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "AccesoMultipleFichero.jar", String.valueOf(i), nombreFichero);
                Process nuevoProceso = processBuilder.start(); // Iniciamos el nuevo proceso

                System.out.println("Creado el proceso " + i);
                // Mostramos en consola que hemos creado otro proceso
            }
        } catch (SecurityException ex) {
            System.err.println("Ha ocurrido un error de Seguridad: " +
                    "No se ha podido crear el proceso por falta de permisos.");
        } catch (Exception ex) {
            System.err.println("Ha ocurrido un error, descripción: " + ex.toString());
        }
    }
}
