package pe.edu.pucp.spotipucp.dao;

import pe.edu.pucp.spotipucp.modelo.Playlist;

import java.sql.SQLException;
import java.util.List;

public interface PlaylistDAO {
    List<Playlist> findAll() throws SQLException;
    Playlist findById(int id) throws SQLException;
    List<Playlist> findByUsuario(int idUsuario) throws SQLException;

    // Operaciones de escritura: requieren una transacción activa (TransactionsManager)
    void insert(Playlist playlist) throws SQLException;
    void delete(int id) throws SQLException;
}
