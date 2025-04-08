package firmadigital;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class Main {

  public static void main(String[] args) {
    String texto = "texto de prueba para ser firmado";
    KeyPair clave = generarClaves();
    byte[] textoFirmado = hacerFirma(texto.getBytes(), clave.getPrivate());
    if (verificarFirma(texto.getBytes(), clave.getPublic(), textoFirmado)) {
      System.out.println("Firma realizada y verificada correctamente");
    } else {
      System.out.println("Firma incorrecta");
    }
  }

  public static KeyPair generarClaves() {
    try {
      KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
      generador.initialize(2048); // se recomienda mínimo 2048 bits
      return generador.generateKeyPair();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static byte[] hacerFirma(byte[] datos, PrivateKey clave) {
    try {
      Signature firma = Signature.getInstance("SHA256withRSA");
      firma.initSign(clave);
      firma.update(datos);
      return firma.sign();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static boolean verificarFirma(byte[] datos, PublicKey clave, byte[] textoFirmado) {
    try {
      Signature firma = Signature.getInstance("SHA256withRSA");
      firma.initVerify(clave);
      firma.update(datos);
      return firma.verify(textoFirmado);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
