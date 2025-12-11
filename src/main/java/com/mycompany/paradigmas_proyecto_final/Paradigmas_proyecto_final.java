/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.paradigmas_proyecto_final;

import com.mycompany.paradigmas_proyecto_final.dao.SintomaDAO;

/**
 *
 * @author danie
 */
public class Paradigmas_proyecto_final {

    public static void main(String[] args) {
    SintomaDAO dao = new SintomaDAO();
    dao.getAll().forEach(s -> System.out.println(s.getNombre()));
}

}
