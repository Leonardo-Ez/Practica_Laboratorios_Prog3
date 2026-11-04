package pe.edu.pucp.spotipucp.dao.impl;

import pe.edu.pucp.spotipucp.dao.PlaylistCancionDAO;
import pe.edu.pucp.spotipucp.dao.transacciones.TransactionsManager;
import pe.edu.pucp.spotipucp.db.DBManager;
import pe.edu.pucp.spotipucp.modelo.PlaylistCancion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PlaylistCancionDAOImpl implements PlaylistCancionDAO {
    @Override
    public List<PlaylistCancion> findByPlaylist(int idPlaylist) throws SQLException {
        String sql = "{call listar_canciones_por_playlist(?)}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id_playlist", idPlaylist);
            try (ResultSet rs = cmd.executeQuery()) {
                List<PlaylistCancion> canciones = new ArrayList<>();
                while (rs.next()) {
                    PlaylistCancion detalle = new PlaylistCancion();
                    detalle.setId(rs.getInt("id_playlist_cancion"));
                    detalle.setOrden(rs.getInt("orden"));
                    detalle.setCancion(CancionDAOImpl.mapear(rs));
                    canciones.add(detalle);
                }
                return canciones;
            }
        }
    }

    @Override
    public void insertCanciones(int idPlaylist, List<PlaylistCancion> canciones) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = "{call insertar_playlist_cancion(?, ?, ?, ?)}";
        try (CallableStatement cmd = conn.prepareCall(sql)) {
            for (PlaylistCancion detalle : canciones) {
                cmd.setInt("p_id_playlist", idPlaylist);
                cmd.setInt("p_id_cancion", detalle.getCancion().getId());
                cmd.setInt("p_orden", detalle.getOrden());
                cmd.registerOutParameter("p_id", Types.INTEGER);

                if (cmd.executeUpdate() == 0) {
                    throw new SQLException("No se pudo insertar la canción en la playlist");
                }

                detalle.setId(cmd.getInt("p_id"));
            }
        }
    }

    @Override
    public void deleteByPlaylist(int idPlaylist) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = "{call eliminar_canciones_por_playlist(?)}";
        try (CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id_playlist", idPlaylist);
            cmd.executeUpdate();
        }
    }
}
