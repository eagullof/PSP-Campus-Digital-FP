package ejecutarvarioshilos;

/**
 *
 * @author eagullof
 */
public class HiloThread extends Thread {
//clase que extiende a Thread con 2 constructores

    String nombre = "Hilo_derviaThread";

    public HiloThread(String nb) {
        //constructor 1
        nombre = nb;
    }

    public HiloThread() {
        //constructor 2
    }

    @Override
    public void run() {
        //redefinimos run() con el código asociado al hilo
        for (int i = 1; i <= 5; i++) {
            System.out.println(nombre);
        }
    }
}