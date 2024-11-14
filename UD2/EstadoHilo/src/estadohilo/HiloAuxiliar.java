/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estadohilo;

/**
 *
 * @author eagullof
 */
public class HiloAuxiliar extends Thread{
//código del hilo
  @Override
  public void run(){
    for(int i=10;i>=1;i--)
      System.out.print(i+",");
  }
}