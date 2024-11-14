
package ejecutarvarioshilos;

/**
 *
 * @author eagullof
 */
public class Main {

   public static void main(String[] args) {
        //creamos 2 hilos del tipo Hilo_Thread con 2 constructores
        //diferentes
        Thread hilo1 = new HiloThread("Esther");
        Thread hilo2 = new HiloThread();

        //creamos un hilo Runnable en un paso
        Thread hilo3 = new Thread(new HiloRunnable());

        //ponemos en marcha los 3 hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
    }

}
