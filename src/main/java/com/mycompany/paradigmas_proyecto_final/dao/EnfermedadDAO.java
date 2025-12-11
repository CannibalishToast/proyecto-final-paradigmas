/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;
import com.mycompany.paradigmas_proyecto_final.models.Enfermedad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnfermedadDAO {

    public List<Enfermedad> getAll() {
        List<Enfermedad> lista = new ArrayList<>();

        String sql = "SELECT * FROM enfermedad";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Enfermedad(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("categoria_id"),
                        rs.getString("recomendacion")
                ));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Enfermedad getById(int id) {
        Enfermedad enf = null;
        String sql = "SELECT * FROM enfermedad WHERE id = ?";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                enf = new Enfermedad(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getInt("categoria_id"),
                        rs.getString("recomendacion")
                );
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enf;
    }
}
