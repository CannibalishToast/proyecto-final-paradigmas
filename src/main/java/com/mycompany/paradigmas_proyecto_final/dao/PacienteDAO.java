/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;
import com.mycompany.paradigmas_proyecto_final.models.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public int insertar(Paciente p) {
        String sql = "INSERT INTO paciente (nombre, edad) VALUES (?, ?)";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getEdad());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Paciente getById(int id) {
        Paciente p = null;
        String sql = "SELECT * FROM paciente WHERE id=?";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad")
                );
            }

            rs.close();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }

        return p;
    }

    public List<Paciente> getAll() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad")
                ));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }

        return lista;
    }
}
