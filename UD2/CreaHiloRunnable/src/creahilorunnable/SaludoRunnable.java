/*
 *Crea un hilo implementando la interfaz Runnable
 */
package creahilorunnable;

/**
 *
 * @author eagullof
 */
class SaludoRunnable implements Runnable {
    // Redefinición del método run() con las instrucciones del hilo
    public void run() {
        System.out.println("¡Hola! Este es el mensaje del hilo.");
    }

    public static void main(String[] args) {
        // Crear un objeto de la clase SaludoRunnable
        SaludoRunnable saludo = new SaludoRunnable();

        // Crear el hilo pasando el objeto saludo a Thread
        Thread hilo1 = new Thread(saludo);

        // Iniciar el hilo invocando al método start()
        hilo1.start();
    }
}

