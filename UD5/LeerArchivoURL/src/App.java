import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class App {
    public static void main(String[] args) {
        String fileUrl = "http://ftp.rediris.es/debian/README.mirrors.txt";
        String outputFile = "descargas/fichero.txt";

        try {
            // URL url = new URL(fileUrl); DEPRECATED
            
            // Usar URI y luego convertirlo a URL
            URI uri = new URI(fileUrl);
            URL url = uri.toURL();

            // Try-with-resources para gestionar automáticamente el cierre de flujos
            try (InputStream flujoIn = url.openStream();
                 FileOutputStream flujoOutFile = new FileOutputStream(outputFile)) {

                byte[] buffer = new byte[4096];
                int bytesLeidos;
                int totalBytesLeidos = 0;

                while ((bytesLeidos = flujoIn.read(buffer)) > 0) {
                    flujoOutFile.write(buffer, 0, bytesLeidos);
                    totalBytesLeidos += bytesLeidos;
                }

                System.out.println("Archivo descargado correctamente. Total bytes leídos: " + totalBytesLeidos);

            }
        } catch (Exception e) {
            System.out.println("Error al descargar el archivo: " + e.getMessage());
        }
    }
}
