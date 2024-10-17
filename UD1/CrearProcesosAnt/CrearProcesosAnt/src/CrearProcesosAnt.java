
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author infoe
 */
public class CrearProcesosAnt {

    
    public static void main(String[] args) {
        crearProcesoEditorTexto();
    }

    public static void crearProcesoEditorTexto() {
        try {
            // Obtenemos el nombre del SO
            String osName = System.getProperty("os.name").toLowerCase();
            String command;

            // Determinamos el comando según el sistema operativo
            if (osName.contains("win")) { // Windows
                command = "notepad.exe"; // Comando para abrir el Bloc de notas
            } else { // Linux
                command = "gedit"; // Comando para abrir gedit
            }

            // Creamos el ProcessBuilder
            ProcessBuilder builder = new ProcessBuilder(command);
            
            // Si deseas redirigir la salida del proceso, puedes hacerlo
            builder.inheritIO(); // Esto redirige la salida y errores a la consola actual
            
            // Iniciamos el proceso
            Process proceso = builder.start();

            // Esperamos a que el proceso termine
            int exitCode = proceso.waitFor();

            // Puedes verificar si el proceso terminó correctamente
            if (exitCode == 0) {
                System.out.println("El proceso se completó con éxito.");
            } else {
                System.out.println("El proceso terminó con código de salida: " + exitCode);
            }

        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error al intentar crear el proceso. Descripción: " + ex.toString());
        } catch (InterruptedException ex) {
            System.out.println("El proceso fue interrumpido. Descripción: " + ex.toString());
        }
    }
}
