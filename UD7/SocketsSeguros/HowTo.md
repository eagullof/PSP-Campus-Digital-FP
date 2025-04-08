# Configuración de Sockets Seguros en Java con Autenticación Mutua (Windows)

Este documento explica cómo preparar desde cero una aplicación Java con **SSL/TLS y autenticación mutua** (cliente y servidor con certificados válidos) en Windows, utilizando `keytool`, que viene incluido en el JDK.

---

## Archivos a generar

1. `servidor_keystore.jks` → clave y certificado del servidor  
2. `cliente_keystore.jks` → clave y certificado del cliente  
3. `servidor_truststore.jks` → confía en el certificado del cliente  
4. `cliente_truststore.jks` → confía en el certificado del servidor  
5. `servidor_cert.cer` → certificado público del servidor  
6. `cliente_cert.cer` → certificado público del cliente  

---

## Pasos para generar certificados y truststores

> Asegúrate de trabajar dentro de la carpeta del proyecto, en este caso `SocketsSeguros`.

### 1. Crear el keystore del servidor

```powershell
keytool -genkeypair -alias servidor -keyalg RSA -keysize 2048 `
 -keystore servidor_keystore.jks -storepass changeit -keypass changeit `
 -validity 365 -dname "CN=Servidor, OU=CampusDigital, O=CD, L=Zaragoza, ST=Z, C=ES"
```

---

### 2. Crear el keystore del cliente

```powershell
keytool -genkeypair -alias cliente -keyalg RSA -keysize 2048 `
 -keystore cliente_keystore.jks -storepass changeit -keypass changeit `
 -validity 365 -dname "CN=Cliente, OU=CampusDigital, O=CD, L=Zaragoza, ST=Z, C=ES"
```

---

### 3. Exportar los certificados públicos

```powershell
keytool -export -alias servidor -keystore servidor_keystore.jks -storepass changeit -file servidor_cert.cer
keytool -export -alias cliente -keystore cliente_keystore.jks -storepass changeit -file cliente_cert.cer
```

---

### 4. Crear los truststores (importar certificados)

```powershell
# Cliente confía en el servidor
keytool -import -alias servidor -file servidor_cert.cer -keystore cliente_truststore.jks -storepass changeit -noprompt

# Servidor confía en el cliente
keytool -import -alias cliente -file cliente_cert.cer -keystore servidor_truststore.jks -storepass changeit -noprompt
```

---

## Archivos generados

Al finalizar, deberías tener los siguientes archivos en el directorio de trabajo:

```
servidor_keystore.jks
cliente_keystore.jks
servidor_truststore.jks
cliente_truststore.jks
servidor_cert.cer
cliente_cert.cer
```

---

A partir de este momento ya puedes ejecutar como de costumbre los programas servidor y cliente.