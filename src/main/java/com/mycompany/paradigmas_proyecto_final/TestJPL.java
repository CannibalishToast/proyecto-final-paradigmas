/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final;

import org.jpl7.Query;

public class TestJPL {
    public static void main(String[] args) {
        Query q = new Query("consult('test.pl')");
        if(q.hasSolution()){
            System.out.println("JPL está funcionando correctamente.");
        }
    }
}
