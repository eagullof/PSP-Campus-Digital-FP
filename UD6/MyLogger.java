import java.io.*;

import java.util.logging.*;



public class MyLogger {



  public static void main(String[] args) { 

    Logger logger = Logger.getLogger("MyLog");

    FileHandler fh;


    try {

      // Configuro el logger y establezco el formato

      fh = new FileHandler("MyLogFile.log", true);

      logger.addHandler(fh);

      logger.setUseParentHandlers(false);
      
      logger.setLevel(Level.ALL);

      SimpleFormatter formatter = new SimpleFormatter();

      fh.setFormatter(formatter);

      // Añado un mensaje al log   

      logger.log(Level.FINE,"Mi primer log FINE");
      logger.log(Level.SEVERE,"Mi primer log SEVERE");
      logger.log(Level.WARNING,"Mi primer log");
      logger.log(Level.FINER,"Mi primer log FINER");
      logger.log(Level.INFO,"Mi primer log INFO");

    } catch (SecurityException e) {

      e.printStackTrace() ;

    } catch (IOException e) {

      e.printStackTrace() ;

    }

  }

}