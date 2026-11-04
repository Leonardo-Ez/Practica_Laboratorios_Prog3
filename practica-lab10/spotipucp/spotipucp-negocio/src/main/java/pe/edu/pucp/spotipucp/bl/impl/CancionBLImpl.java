package pe.edu.pucp.spotipucp.bl.impl;

import pe.edu.pucp.spotipucp.bl.CancionBL;
import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.bl.error.NoEncontradoException;
import pe.edu.pucp.spotipucp.dao.CancionDAO;
import pe.edu.pucp.spotipucp.dao.impl.CancionDAOImpl;
import pe.edu.pucp.spotipucp.modelo.Cancion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CancionBLImpl implements CancionBL {
    private final CancionDAO cancionDAO = new CancionDAOImpl();

    @Override
    public List<Cancion> findAll() throws BLException {
        try {
            return cancionDAO.findAll();
        } catch (SQLException e) {
            throw new BLException("No se pudo listar las canciones", e);
        }
    }

    @Override
    public Cancion findById(int id) throws BLException {
        try {
            Cancion cancion = cancionDAO.findById(id);
            if (cancion == null) {
                throw new NoEncontradoException("No se encontró la canción con id: " + id);
            }
            return cancion;
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar la canción", e);
        }
    }

    @Override
    public List<Cancion> seleccionarCancionesAleatorias() throws BLException {
        List<Cancion> catalogo = findAll();
        if (catalogo.size() < CANCIONES_POR_PLAYLIST) {
            throw new BLException("El catálogo no tiene suficientes canciones para generar una playlist");
        }

        Collections.shuffle(catalogo);
        return new ArrayList<>(catalogo.subList(0, CANCIONES_POR_PLAYLIST));
    }
}
