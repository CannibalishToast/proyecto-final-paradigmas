/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;
import com.mycompany.paradigmas_proyecto_final.models.Diagnostico;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {

    public int insertar(int pacienteId) {
        String sql = "INSERT INTO diagnostico (paciente_id) VALUES (?)";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, pacienteId);
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

    public Diagnostico getById(int id) {
        Diagnostico diag = null;
        String sql = "SELECT * FROM diagnostico WHERE id=?";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                diag = new Diagnostico(
                        rs.getInt("id"),
                        rs.getInt("paciente_id"),
                        rs.getTimestamp("fecha").toLocalDateTime()
                );
            }

            rs.close();
            ps.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return diag;
    }

    public List<Diagnostico> getByPaciente(int pacienteId) {
        List<Diagnostico> lista = new ArrayList<>();

        String sql = "SELECT * FROM diagnostico WHERE paciente_id=? ORDER BY fecha DESC";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, pacienteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Diagnostico(
                        rs.getInt("id"),
                        rs.getInt("paciente_id"),
                        rs.getTimestamp("fecha").toLocalDateTime()
                ));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return lista;
    }
}
