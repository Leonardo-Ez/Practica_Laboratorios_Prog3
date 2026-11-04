package pe.edu.pucp.spotipucp.bl;

import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.modelo.Cancion;

import java.util.List;

public interface CancionBL {
    int CANCIONES_POR_PLAYLIST = 10;

    List<Cancion> findAll() throws BLException;

    // Lanza NoEncontradoException si la canción no existe
    Cancion findById(int id) throws BLException;

    // Retorna CANCIONES_POR_PLAYLIST canciones distintas tomadas al azar del catálogo
    List<Cancion> seleccionarCancionesAleatorias() throws BLException;
}
