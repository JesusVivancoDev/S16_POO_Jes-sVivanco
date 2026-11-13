# Empresa de Seguridad - CRUD JavaFX + MySQL

Requisitos: JDK 17 o superior, MySQL 8 y un IDE (IntelliJ, Eclipse o NetBeans) con soporte Maven.

## Pasos
1. En MySQL ejecuta `sql/empresa_seguridad.sql` (crea la base, la tabla y 5 datos de prueba).
2. En `src/main/java/com/seguridad/Conexion.java` ajusta USUARIO y PASSWORD de tu MySQL.
3. Abre la carpeta como proyecto Maven en tu IDE (File > Open > pom.xml) y espera a que descargue dependencias.
4. Ejecuta con `mvn javafx:run`, o ejecuta la clase `com.seguridad.Launcher` desde el IDE.

## Generar el JAR (reto extra)
`mvn clean package` genera `target/empresa-seguridad-1.0.jar`.
Ejecútalo con `java -jar target/empresa-seguridad-1.0.jar`
(el JAR se arma para el sistema operativo donde lo compilaste).
