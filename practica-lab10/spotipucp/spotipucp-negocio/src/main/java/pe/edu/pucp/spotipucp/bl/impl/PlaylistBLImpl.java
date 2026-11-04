package pe.edu.pucp.spotipucp.bl.impl;

import pe.edu.pucp.spotipucp.bl.CancionBL;
import pe.edu.pucp.spotipucp.bl.PlaylistBL;
import pe.edu.pucp.spotipucp.bl.UsuarioBL;
import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.bl.error.NoEncontradoException;
import pe.edu.pucp.spotipucp.bl.error.ValidacionException;
import pe.edu.pucp.spotipucp.dao.PlaylistDAO;
import pe.edu.pucp.spotipucp.dao.impl.PlaylistDAOImpl;
import pe.edu.pucp.spotipucp.dao.transacciones.TransactionsManager;
import pe.edu.pucp.spotipucp.modelo.Cancion;
import pe.edu.pucp.spotipucp.modelo.Playlist;
import pe.edu.pucp.spotipucp.modelo.PlaylistCancion;
import pe.edu.pucp.spotipucp.modelo.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaylistBLImpl implements PlaylistBL {
    private final PlaylistDAO playlistDAO = new PlaylistDAOImpl();
    private final UsuarioBL usuarioBL = new UsuarioBLImpl();
    private final CancionBL cancionBL = new CancionBLImpl();

    @Override
    public List<Playlist> findAll() throws BLException {
        try {
            return playlistDAO.findAll();
        } catch (SQLException e) {
            throw new BLException("No se pudo listar las playlists", e);
        }
    }

    @Override
    public Playlist findById(int id) throws BLException {
        try {
            Playlist playlist = playlistDAO.findById(id);
            if (playlist == null) {
                throw new NoEncontradoException("No se encontró la playlist con id: " + id);
            }
            return playlist;
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar la playlist", e);
        }
    }

    @Override
    public List<Playlist> findByUsuario(int idUsuario) throws BLException {
        usuarioBL.findById(idUsuario);
        try {
            return playlistDAO.findByUsuario(idUsuario);
        } catch (SQLException e) {
            throw new BLException("No se pudo listar las playlists del usuario", e);
        }
    }

    @Override
    public Playlist crearPlaylistConCanciones(int idUsuario, String nombre, List<Integer> idsCanciones)
            throws BLException {
        validarNombre(nombre);
        validarCanciones(idsCanciones);
        Usuario usuario = buscarUsuario(idUsuario);

        Playlist playlist = new Playlist();
        playlist.setUsuario(usuario);
        playlist.setNombre(nombre.trim());
        playlist.setFechaCreacion(LocalDate.now());

        List<PlaylistCancion> detalle = new ArrayList<>();
        int orden = 1;
        for (int idCancion : idsCanciones) {
            detalle.add(new PlaylistCancion(buscarCancion(idCancion), orden++));
        }
        playlist.setCanciones(detalle);

        TransactionsManager.iniciar();
        try {
            playlistDAO.insert(playlist);
            TransactionsManager.commit();
        } catch (SQLException e) {
            TransactionsManager.rollback();
            throw new BLException("No se pudo registrar la playlist", e);
        }
        return playlist;
    }

    @Override
    public List<Playlist> generarPlaylistsParaTodos() throws BLException {
        List<Playlist> generadas = new ArrayList<>();
        for (Usuario usuario : usuarioBL.findAll()) {
            List<Integer> idsCanciones = new ArrayList<>();
            for (Cancion cancion : cancionBL.seleccionarCancionesAleatorias()) {
                idsCanciones.add(cancion.getId());
            }
            String nombre = "Playlist de " + usuario.getCodigo();
            generadas.add(crearPlaylistConCanciones(usuario.getId(), nombre, idsCanciones));
        }
        return generadas;
    }

    @Override
    public void delete(int id) throws BLException {
        findById(id);

        TransactionsManager.iniciar();
        try {
            playlistDAO.delete(id);
            TransactionsManager.commit();
        } catch (SQLException e) {
            TransactionsManager.rollback();
            throw new BLException("No se pudo eliminar la playlist", e);
        }
    }

    private void validarNombre(String nombre) throws BLException {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre de la playlist es obligatorio");
        }
        if (nombre.trim().length() > MAX_LONGITUD_NOMBRE) {
            throw new ValidacionException(
                    "El nombre de la playlist no puede exceder " + MAX_LONGITUD_NOMBRE + " caracteres");
        }
    }

    private void validarCanciones(List<Integer> idsCanciones) throws BLException {
        if (idsCanciones == null || idsCanciones.isEmpty()) {
            throw new ValidacionException("La playlist debe tener al menos una canción");
        }
        Set<Integer> unicos = new HashSet<>(idsCanciones);
        if (unicos.size() != idsCanciones.size()) {
            throw new ValidacionException("La playlist no puede tener canciones repetidas");
        }
    }

    private Usuario buscarUsuario(int idUsuario) throws BLException {
        try {
            return usuarioBL.findById(idUsuario);
        } catch (NoEncontradoException e) {
            throw new ValidacionException("No existe el usuario con id " + idUsuario);
        }
    }

    private Cancion buscarCancion(int idCancion) throws BLException {
        try {
            return cancionBL.findById(idCancion);
        } catch (NoEncontradoException e) {
            throw new ValidacionException("No existe la canción con id " + idCancion);
        }
    }
}
