/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;
import com.mycompany.paradigmas_proyecto_final.models.DiagnosticoEnfermedad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoEnfermedadDAO {

    public void insertar(DiagnosticoEnfermedad de) {
        String sql = "INSERT INTO diagnostico_enfermedad (diagnostico_id, enfermedad_id, coincidencia_porcentaje) VALUES (?, ?, ?)";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, de.getDiagnosticoId());
            ps.setInt(2, de.getEnfermedadId());
            if (de.getCoincidenciaPorcentaje() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, de.getCoincidenciaPorcentaje());
            }

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DiagnosticoEnfermedad> getByDiagnostico(int diagnosticoId) {
        List<DiagnosticoEnfermedad> lista = new ArrayList<>();
        String sql = "SELECT * FROM diagnostico_enfermedad WHERE diagnostico_id=?";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, diagnosticoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new DiagnosticoEnfermedad(
                        rs.getInt("id"),
                        rs.getInt("diagnostico_id"),
                        rs.getInt("enfermedad_id"),
                        (Integer) rs.getObject("coincidencia_porcentaje")
                ));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return lista;
    }
}
