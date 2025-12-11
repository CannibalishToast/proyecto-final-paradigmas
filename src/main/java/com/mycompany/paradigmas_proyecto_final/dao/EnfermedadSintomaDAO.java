/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradigmas_proyecto_final.dao;

/**
 *
 * @author danie
 */

import com.mycompany.paradigmas_proyecto_final.database.MySQLConnection;
import com.mycompany.paradigmas_proyecto_final.models.EnfermedadSintoma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnfermedadSintomaDAO {

    /** Inserta relación enfermedad-síntoma */
    public void insertar(EnfermedadSintoma es) {
        String sql = "INSERT INTO enfermedad_sintoma (enfermedad_id, sintoma_id) VALUES (?, ?)";

        try (Connection conn = MySQLConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, es.getEnfermedadId());
            ps.setInt(2, es.getSintomaId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Obtiene todas las relaciones enfermedad_sintoma */
    public List<EnfermedadSintoma> getAll() {
        List<EnfermedadSintoma> lista = new ArrayList<>();
        String sql = "SELECT id, enfermedad_id, sintoma_id FROM enfermedad_sintoma";

        try (Connection conn = MySQLConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new EnfermedadSintoma(
                        rs.getInt("id"),
                        rs.getInt("enfermedad_id"),
                        rs.getInt("sintoma_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /** Obtiene síntomas por ID de enfermedad */
    public List<EnfermedadSintoma> getByEnfermedad(int enfermedadId) {
        List<EnfermedadSintoma> lista = new ArrayList<>();
        String sql = "SELECT id, enfermedad_id, sintoma_id FROM enfermedad_sintoma WHERE enfermedad_id = ?";

        try (Connection conn = MySQLConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, enfermedadId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new EnfermedadSintoma(
                        rs.getInt("id"),
                        rs.getInt("enfermedad_id"),
                        rs.getInt("sintoma_id")
                ));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /** Borra todas las relaciones (opcional, útil para recargar) */
    public void deleteAll() {
        String sql = "DELETE FROM enfermedad_sintoma";

        try (Connection conn = MySQLConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

