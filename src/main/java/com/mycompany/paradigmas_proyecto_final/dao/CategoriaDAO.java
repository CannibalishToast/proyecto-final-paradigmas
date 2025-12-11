/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;
import com.mycompany.paradigmas_proyecto_final.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> getAll() {
        List<Categoria> list = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return list;
    }

    public Categoria getById(int id) {
        Categoria cat = null;
        String sql = "SELECT * FROM categoria WHERE id=?";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cat = new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
            }

            rs.close();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }

        return cat;
    }
}

