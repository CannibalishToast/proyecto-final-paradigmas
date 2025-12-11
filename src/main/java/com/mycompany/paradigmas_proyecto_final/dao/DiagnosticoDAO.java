/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;
import com.mycompany.paradigmas_proyecto_final.models.Diagnostico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {

    // ============================================================
    // INSERTAR DIAGNÓSTICO
    // ============================================================
    public int insertar(int pacienteId) {
        String sql = "INSERT INTO diagnostico (paciente_id) VALUES (?)";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, pacienteId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ============================================================
    // OBTENER DIAGNÓSTICO POR ID
    // ============================================================
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

    // ============================================================
    // OBTENER POR PACIENTE
    // ============================================================
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

    // ============================================================
    // ESTADÍSTICAS GLOBALES
    // ============================================================

    public int contarPorEnfermedad(int enfermedadId) {

        String sql = """
                SELECT COUNT(*) FROM diagnostico_enfermedad 
                WHERE enfermedad_id = ?
                """;

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, enfermedadId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

            rs.close();
            ps.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return 0;
    }

    public int contarPorSintoma(int sintomaId) {

        String sql = """
                SELECT COUNT(*) FROM diagnostico_sintoma
                WHERE sintoma_id = ?
                """;

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sintomaId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

            rs.close();
            ps.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return 0;
    }

    // ============================================================
    // ESTADÍSTICAS POR PACIENTE (NUEVO)
    // ============================================================

    /**
     * Cuenta cuántas veces un paciente fue diagnosticado con una enfermedad.
     */
    public int contarPorEnfermedadYPaciente(int enfermedadId, int pacienteId) {

        String sql = """
                SELECT COUNT(*)
                FROM diagnostico_enfermedad de
                JOIN diagnostico d ON d.id = de.diagnostico_id
                WHERE de.enfermedad_id = ?
                AND d.paciente_id = ?
                """;

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, enfermedadId);
            ps.setInt(2, pacienteId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

            rs.close();
            ps.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return 0;
    }

    /**
     * Cuenta cuántas veces un paciente tuvo un síntoma seleccionado.
     */
    public int contarPorSintomaYPaciente(int sintomaId, int pacienteId) {

        String sql = """
                SELECT COUNT(*)
                FROM diagnostico_sintoma ds
                JOIN diagnostico d ON d.id = ds.diagnostico_id
                WHERE ds.sintoma_id = ?
                AND d.paciente_id = ?
                """;

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, sintomaId);
            ps.setInt(2, pacienteId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

            rs.close();
            ps.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return 0;
    }
}

    // ============================================================
    // OBTENER TODO PARA EL C
