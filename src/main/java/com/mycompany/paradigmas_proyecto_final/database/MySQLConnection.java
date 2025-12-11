/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static MySQLConnection instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/sistema_experto";
    private final String USER = "root";
    private final String PASSWORD = "mc4A5T8d";

    private MySQLConnection() {
        conectar();
    }

    public static MySQLConnection getInstance() {
        if (instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }

    /** Abre la conexión si está cerrada */
    private void conectar() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión a MySQL establecida.");
            }
        } catch (SQLException e) {
            System.out.println("Error conectando a MySQL: " + e.getMessage());
        }
    }

    /** Devuelve siempre la misma conexión mientras siga viva */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                conectar();
            }
        } catch (SQLException ignored) {}
        return connection;
    }
}
