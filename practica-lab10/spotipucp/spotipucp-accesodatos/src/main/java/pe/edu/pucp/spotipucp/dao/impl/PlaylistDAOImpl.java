package pe.edu.pucp.spotipucp.dao.impl;

import pe.edu.pucp.spotipucp.dao.PlaylistCancionDAO;
import pe.edu.pucp.spotipucp.dao.PlaylistDAO;
import pe.edu.pucp.spotipucp.dao.transacciones.TransactionsManager;
import pe.edu.pucp.spotipucp.db.DBManager;
import pe.edu.pucp.spotipucp.modelo.Playlist;
import pe.edu.pucp.spotipucp.modelo.Usuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAOImpl implements PlaylistDAO {
    private final PlaylistCancionDAO playlistCancionDAO = new PlaylistCancionDAOImpl();

    @Override
    public List<Playlist> findAll() throws SQLException {
        String sql = "{call listar_playlists()}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql);
             ResultSet rs = cmd.executeQuery()) {
            return mapearLista(rs);
        }
    }

    @Override
    public Playlist findById(int id) throws SQLException {
        String sql = "{call buscar_playlist_por_id(?)}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id", id);
            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public List<Playlist> findByUsuario(int idUsuario) throws SQLException {
        String sql = "{call listar_playlists_por_usuario(?)}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id_usuario", idUsuario);
            try (ResultSet rs = cmd.executeQuery()) {
                return mapearLista(rs);
            }
        }
    }

    @Override
    public void insert(Playlist playlist) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = "{call insertar_playlist(?, ?, ?, ?)}";
        try (CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id_usuario", playlist.getUsuario().getId());
            cmd.setString("p_nombre", playlist.getNombre());
            cmd.setDate("p_fecha_creacion", Date.valueOf(playlist.getFechaCreacion()));
            cmd.registerOutParameter("p_id", Types.INTEGER);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar la playlist");
            }

            playlist.setId(cmd.getInt("p_id"));
        }

        playlistCancionDAO.insertCanciones(playlist.getId(), playlist.getCanciones());
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        playlistCancionDAO.deleteByPlaylist(id);

        String sql = "{call eliminar_playlist(?)}";
        try (CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id", id);
            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar la playlist");
            }
        }
    }

    private List<Playlist> mapearLista(ResultSet rs) throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        while (rs.next()) {
            playlists.add(mapear(rs));
        }
        return playlists;
    }

    private Playlist mapear(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(rs.getInt("id_usuario"));
        usuario.setCodigo(rs.getString("codigo_usuario"));
        usuario.setNombre(rs.getString("nombre_usuario"));
        usuario.setCorreo(rs.getString("correo_usuario"));

        Playlist playlist = new Playlist();
        playlist.setId(rs.getInt("id"));
        playlist.setUsuario(usuario);
        playlist.setNombre(rs.getString("nombre"));
        playlist.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
        playlist.setCanciones(playlistCancionDAO.findByPlaylist(playlist.getId()));
        return playlist;
    }
}
