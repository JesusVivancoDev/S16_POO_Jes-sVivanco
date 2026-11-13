package com.seguridad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // ====== CAMBIA ESTOS DATOS SEGUN TU MYSQL ======
    private static final String HOST     = "localhost";
    private static final String PUERTO   = "3306";
    private static final String BASE     = "empresa_seguridad";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "root123";
    // ===============================================

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BASE
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
