/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final;

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;

public class TestDB {
    public static void main(String[] args) {
        MySQLConnection conn = MySQLConnection.getInstance();
        System.out.println(conn.getConnection());
    }
}

