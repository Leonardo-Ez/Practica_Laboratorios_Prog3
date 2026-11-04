package pe.edu.pucp.spotipucp.bl;

import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.modelo.Playlist;

import java.util.List;

public interface PlaylistBL {
    int MAX_LONGITUD_NOMBRE = 120;

    List<Playlist> findAll() throws BLException;

    // Lanza NoEncontradoException si la playlist no existe
    Playlist findById(int id) throws BLException;

    // Lanza NoEncontradoException si el usuario no existe
    List<Playlist> findByUsuario(int idUsuario) throws BLException;

    /*
     * Registra (en una transacción) una playlist para el usuario con las canciones indicadas.
     * - Solo se usa el id del usuario y de cada canción; el resto se completa desde la BD.
     * - Fecha de creación = fecha actual; orden de las canciones = 1, 2, 3... según la lista.
     * Lanza ValidacionException si el nombre es vacío/largo, si no hay canciones,
     * si hay canciones repetidas, o si el usuario o alguna canción no existen.
     * Retorna la playlist registrada (con su id y el detalle completo).
     */
    Playlist crearPlaylistConCanciones(int idUsuario, String nombre, List<Integer> idsCanciones) throws BLException;

    // Genera y registra una playlist aleatoria ("Playlist de <codigo>") para cada usuario
    List<Playlist> generarPlaylistsParaTodos() throws BLException;

    // Lanza NoEncontradoException si la playlist no existe
    void delete(int id) throws BLException;
}
