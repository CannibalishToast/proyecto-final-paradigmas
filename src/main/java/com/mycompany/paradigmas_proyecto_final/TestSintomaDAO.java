/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final;

import com.mycompany.paradigmas_proyecto_final.dao.SintomaDAO;
import com.mycompany.paradigmas_proyecto_final.models.Sintoma;

/**
 *
 * @author danie
 */
public class TestSintomaDAO {
    public static void main(String[] args) {

    SintomaDAO dao = new SintomaDAO();

    System.out.println("--- PROBANDO getById(1) ---");
    Sintoma s = dao.getById(1);

    if (s != null) {
        System.out.println("ID: " + s.getId());
        System.out.println("Nombre: " + s.getNombre());
        System.out.println("Descripcion: " + s.getDescripcion());
    } else {
        System.out.println("No encontrado");
    }
}

}
