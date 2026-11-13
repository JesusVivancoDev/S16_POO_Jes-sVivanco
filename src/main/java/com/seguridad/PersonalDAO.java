package com.seguridad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonalDAO {

    public List<Personal> listar() throws SQLException {
        List<Personal> lista = new ArrayList<>();
        String sql = "SELECT id_personal, nombres, apellidos, dni, telefono, cargo, turno, estado "
                   + "FROM personal ORDER BY id_personal";
        try (Connection cn = Conexion.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Personal(
                        rs.getInt("id_personal"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("cargo"),
                        rs.getString("turno"),
                        rs.getString("estado")));
            }
        }
        return lista;
    }

    public void insertar(Personal p) throws SQLException {
        String sql = "INSERT INTO personal (nombres, apellidos, dni, telefono, cargo, turno, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = Conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDni());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCargo());
            ps.setString(6, p.getTurno());
            ps.setString(7, p.getEstado());
            ps.executeUpdate();
        }
    }

    public void actualizar(Personal p) throws SQLException {
        String sql = "UPDATE personal SET nombres=?, apellidos=?, dni=?, telefono=?, "
                   + "cargo=?, turno=?, estado=? WHERE id_personal=?";
        try (Connection cn = Conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setString(3, p.getDni());
            ps.setString(4, p.getTelefono());
            ps.setString(5, p.getCargo());
            ps.setString(6, p.getTurno());
            ps.setString(7, p.getEstado());
            ps.setInt(8, p.getIdPersonal());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM personal WHERE id_personal=?";
        try (Connection cn = Conexion.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
