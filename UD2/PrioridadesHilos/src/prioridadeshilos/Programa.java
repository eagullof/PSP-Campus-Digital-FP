/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prioridadeshilos;

/**
 *
 * @author eagullof
 */
public class Programa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int contador = 5;

        //vectores para hilos de distintas prioridades
        Thread[] hiloMIN = new Thread[contador];
        Thread[] hiloNORM = new Thread[contador];
        Thread[] hiloMAX = new Thread[contador];

        //crea los hilos de prioridad mínima
        for (int i = 0; i < contador; i++) {
            hiloMIN[i] = new Hilo(Thread.MIN_PRIORITY);
        }

        //crea los hilos de prioridad normal 
        for (int i = 0; i < contador; i++) {
            hiloNORM[i] = new Hilo();
        }

        //crea los hilos de máxima prioridad
        for (int i = 0; i < contador; i++) {
            hiloMAX[i] = new Hilo(Thread.MAX_PRIORITY);
        }

        System.out.println("Hilos en proceso, espera......\nLos de mayor "
                + "prioridad tienden a terminar antes...\n");

        //inicia los hilos
        for (int i = 0; i < contador; i++) {
            hiloMIN[i].start();
            hiloNORM[i].start();
            hiloMAX[i].start();
        }

    }
    
}
