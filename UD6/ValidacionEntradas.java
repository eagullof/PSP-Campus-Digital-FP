import java.util.Scanner;
import java.util.regex.*;

public class ValidacionEntradas {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Validación del número de teléfono
        System.out.print("Introduce un número de teléfono (formato 000-000000): ");
        String telefono = sc.nextLine();

        if (validarTelefono(telefono)) {
            System.out.println("Teléfono válido");
        } else {
            System.out.println("Formato de teléfono incorrecto.");
        }

        // Validación del DNI
        System.out.print("Introduce un DNI (formato 12345678-A): ");
        String dni = sc.nextLine();

        if (validarDNI(dni)) {
            System.out.println("DNI válido");
        } else {
            System.out.println("Formato de DNI incorrecto.");
        }

        sc.close();
    }

    // Método para validar número de teléfono con expresión regular
    public static boolean validarTelefono(String telefono) {
        Pattern pat = Pattern.compile("^[0-9]{3}-[0-9]{6}$");
        Matcher mat = pat.matcher(telefono);
        return mat.find();
    }

    // Método para validar DNI con expresión regular
    public static boolean validarDNI(String dni) {
        Pattern pat = Pattern.compile("^[0-9]{8}-[a-zA-Z]$");
        Matcher mat = pat.matcher(dni);
        return mat.find();
    }
}
