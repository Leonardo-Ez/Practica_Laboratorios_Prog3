package pe.edu.pucp.spotipucp.dao;

import pe.edu.pucp.spotipucp.modelo.PlaylistCancion;

import java.sql.SQLException;
import java.util.List;

public interface PlaylistCancionDAO {
    List<PlaylistCancion> findByPlaylist(int idPlaylist) throws SQLException;

    // Operaciones de escritura: requieren una transacción activa (TransactionsManager)
    void insertCanciones(int idPlaylist, List<PlaylistCancion> canciones) throws SQLException;
    void deleteByPlaylist(int idPlaylist) throws SQLException;
}
