
package ejecutarvarioshilos;

/**
 *
 * @author eagullof
 */
public class HiloRunnable implements Runnable {
    //clase que implementa Runnable
    public void run(){
        //redefinimos run() con el código asociado al hilo
        for (int i = 1; i <= 5; i++) {
            System.out.println("  Hilo_Runnable");
        }
    }
}