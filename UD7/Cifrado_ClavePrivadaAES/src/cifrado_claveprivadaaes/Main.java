package cifrado_claveprivadaaes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.*;

public class Main {

    public static void main(String[] args) {
        SecretKey clave = null;
        IvParameterSpec iv = null;

        try {
            System.out.println("=== INICIO DEL PROCESO ===");

            // Cifrado del fichero original
            System.out.println("1. Cifrando el fichero original...");
            Object[] resultado = cifrarFichero("fichero.txt");
            clave = (SecretKey) resultado[0];
            iv = (IvParameterSpec) resultado[1];
            System.out.println("1.1 Cifrado completado con éxito.\n");

            // Descifrado del fichero cifrado
            System.out.println("2. Descifrando el fichero...");
            descifrarFichero("fichero.txt.cifrado", clave, iv, "fichero.descifrado");
            System.out.println("2.1 Descifrado completado con éxito.\n");

            System.out.println("=== PROCESO FINALIZADO ===");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método que cifra un fichero y devuelve la clave y el IV usados
    private static Object[] cifrarFichero(String file) throws Exception {
        FileInputStream fe = new FileInputStream(file);
        FileOutputStream fs = new FileOutputStream(file + ".cifrado");

        // 1. Generar clave AES de 128 bits
        System.out.println("-> Generando clave AES de 128 bits...");
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey clave = keyGen.generateKey();
        System.out.println("   Clave generada correctamente.");

        // 2. Generar IV (vector de inicialización) aleatorio de 16 bytes
        System.out.println("-> Generando IV aleatorio...");
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[16];
        random.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        System.out.println("   IV generado correctamente.");

        // 3. Crear el objeto Cipher para cifrar con AES en modo CBC
        Cipher cifrador = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cifrador.init(Cipher.ENCRYPT_MODE, clave, iv);
        System.out.println("-> Cifrador AES/CBC/PKCS5Padding inicializado en modo CIFRADO.");

        // 4. Escribir el IV al inicio del fichero cifrado
        fs.write(ivBytes);
        System.out.println("-> IV guardado al inicio del fichero cifrado.");

        // 5. Leer el fichero original y cifrarlo por bloques
        byte[] buffer = new byte[1024];
        int bytesLeidos;
        while ((bytesLeidos = fe.read(buffer)) != -1) {
            byte[] bufferCifrado = cifrador.update(buffer, 0, bytesLeidos);
            if (bufferCifrado != null) {
                fs.write(bufferCifrado);
            }
        }
        // Escribir los últimos bytes del cifrado
        byte[] finalCifrado = cifrador.doFinal();
        if (finalCifrado != null) fs.write(finalCifrado);

        // Cerrar archivos
        fe.close();
        fs.close();

        System.out.println("-> Fichero cifrado generado: " + file + ".cifrado");
        return new Object[]{clave, iv};
    }

    // Método que descifra un fichero cifrado con AES
    private static void descifrarFichero(String file1, SecretKey clave, IvParameterSpec iv, String file2) throws Exception {
        FileInputStream fe = new FileInputStream(file1);
        FileOutputStream fs = new FileOutputStream(file2);

        System.out.println("-> Descifrador AES/CBC/PKCS5Padding inicializado en modo DESCIFRADO.");

        // Leer el IV desde el inicio del fichero cifrado
        byte[] ivBytes = new byte[16];
        fe.read(ivBytes);
        IvParameterSpec ivParam = new IvParameterSpec(ivBytes);
        Cipher cifrador = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cifrador.init(Cipher.DECRYPT_MODE, clave, ivParam);

        // Leer y descifrar el contenido por bloques
        byte[] buffer = new byte[1024];
        int bytesLeidos;
        while ((bytesLeidos = fe.read(buffer)) != -1) {
            byte[] bufferDescifrado = cifrador.update(buffer, 0, bytesLeidos);
            if (bufferDescifrado != null) {
                fs.write(bufferDescifrado);
            }
        }
        // Escribir los últimos bytes del descifrado
        byte[] finalDescifrado = cifrador.doFinal();
        if (finalDescifrado != null) fs.write(finalDescifrado);

        fe.close();
        fs.close();

        System.out.println("-> Fichero descifrado generado: " + file2);
    }
}
