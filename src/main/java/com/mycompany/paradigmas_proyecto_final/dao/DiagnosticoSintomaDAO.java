/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;
import com.mycompany.paradigmas_proyecto_final.models.DiagnosticoSintoma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoSintomaDAO {

    public void insertar(DiagnosticoSintoma ds) {
        String sql = "INSERT INTO diagnostico_sintoma (diagnostico_id, sintoma_id) VALUES (?, ?)";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, ds.getDiagnosticoId());
            ps.setInt(2, ds.getSintomaId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DiagnosticoSintoma> getByDiagnostico(int diagnosticoId) {
        List<DiagnosticoSintoma> lista = new ArrayList<>();
        String sql = "SELECT * FROM diagnostico_sintoma WHERE diagnostico_id=?";

        try {
            Connection conn = MySQLConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, diagnosticoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new DiagnosticoSintoma(
                        rs.getInt("id"),
                        rs.getInt("diagnostico_id"),
                        rs.getInt("sintoma_id")
                ));
            }

            rs.close();
            ps.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return lista;
    }
}
