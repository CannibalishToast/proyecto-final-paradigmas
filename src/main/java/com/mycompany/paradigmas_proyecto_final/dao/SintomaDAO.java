/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;
import com.mycompany.paradigmas_proyecto_final.models.Sintoma;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SintomaDAO {

    public List<Sintoma> getAll() {
        List<Sintoma> lista = new ArrayList<>();

        String sql = "SELECT id, nombre, descripcion FROM sintoma";
        
        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Sintoma s = new Sintoma(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                lista.add(s);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Sintoma> getByEnfermedad(int enfermedadId) {
        List<Sintoma> lista = new ArrayList<>();

        String sql =
                "SELECT s.id, s.nombre, s.descripcion " +
                "FROM sintoma s " +
                "JOIN enfermedad_sintoma es ON s.id = es.sintoma_id " +
                "WHERE es.enfermedad_id = ?";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, enfermedadId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Sintoma(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
