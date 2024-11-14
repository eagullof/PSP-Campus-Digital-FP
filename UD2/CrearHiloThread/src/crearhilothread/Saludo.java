/*
* Crea un hilo derivando de la clase Thread*
*/
package crearhilothread;

/**
 *
 * @author eagullof
 */
class Saludo extends Thread {

    // Redefinición del método run() con las instrucciones del hilo
    public void run() {
        System.out.println("¡Hola! Este es el mensaje del hilo.");
    }

    public static void main(String[] args) {
        // Creación del objeto hilo1 de la clase Saludo (el hilo)
        Saludo hilo1 = new Saludo();

        // Iniciar el hilo invocando al método start()
        hilo1.start();
    }
}
