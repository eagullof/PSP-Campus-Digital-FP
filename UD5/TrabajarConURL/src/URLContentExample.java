import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class URLContentExample {
    public static void main(String[] args) {
        String urlString = "http://ftp.rediris.es/debian/README.mirrors.txt";

        try {
            // 1. Crear objeto URL
            //URL url = new URL(urlString);

            // Usar URI y luego convertirlo a URL
            URI uri = new URI(urlString);
            URL url = uri.toURL();

            // 2. Abrir la conexión con el servidor
            URLConnection connection = url.openConnection();

            // 3. Establecer propiedades de la conexión (opcional)
            connection.setConnectTimeout(5000); // Tiempo máximo de espera (5 segundos)
            connection.setReadTimeout(5000); // Tiempo máximo de espera para leer datos

            // 4. Establecer la conexión manualmente (aunque openStream también la establece)
            connection.connect();

            // 5. Obtener información del recurso
            String contentType = connection.getContentType();
            int contentLength = connection.getContentLength();
            long lastModified = connection.getLastModified();

            System.out.println("Tipo de contenido: " + contentType);
            System.out.println("Tamaño del contenido: " + (contentLength != -1 ? contentLength + " bytes" : "Desconocido"));
            System.out.println("Última modificación: " + (lastModified != 0 ? new java.util.Date(lastModified) : "Desconocida"));

            // 6. Obtener y mostrar el contenido si es de tipo texto
            Object content = connection.getContent();
            if (content instanceof InputStream) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) content))) {
                    String line;
                    System.out.println("\n--- Contenido del archivo ---");
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } else {
                System.out.println("El contenido no es de tipo texto y no puede mostrarse.");
            }

        } catch (Exception e) {
            System.out.println("Error al acceder a la URL: " + e.getMessage());
        }
    }
}
