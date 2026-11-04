package pe.edu.pucp.spotipucp.dao.impl;

import pe.edu.pucp.spotipucp.dao.CancionDAO;
import pe.edu.pucp.spotipucp.db.DBManager;
import pe.edu.pucp.spotipucp.modelo.Cancion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancionDAOImpl implements CancionDAO {
    @Override
    public List<Cancion> findAll() throws SQLException {
        String sql = "{call listar_canciones()}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql);
             ResultSet rs = cmd.executeQuery()) {

            List<Cancion> canciones = new ArrayList<>();
            while (rs.next()) {
                canciones.add(mapear(rs));
            }
            return canciones;
        }
    }

    @Override
    public Cancion findById(int id) throws SQLException {
        String sql = "{call buscar_cancion_por_id(?)}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id", id);
            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    static Cancion mapear(ResultSet rs) throws SQLException {
        Cancion cancion = new Cancion();
        cancion.setId(rs.getInt("id"));
        cancion.setTitulo(rs.getString("titulo"));
        cancion.setArtista(rs.getString("artista"));
        cancion.setGenero(rs.getString("genero"));
        cancion.setDuracionSegundos(rs.getInt("duracion_segundos"));
        return cancion;
    }
}
