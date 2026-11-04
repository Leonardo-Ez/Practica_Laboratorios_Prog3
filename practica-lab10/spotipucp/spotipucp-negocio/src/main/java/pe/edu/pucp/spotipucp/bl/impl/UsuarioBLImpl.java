package pe.edu.pucp.spotipucp.bl.impl;

import pe.edu.pucp.spotipucp.bl.UsuarioBL;
import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.bl.error.NoEncontradoException;
import pe.edu.pucp.spotipucp.dao.UsuarioDAO;
import pe.edu.pucp.spotipucp.dao.impl.UsuarioDAOImpl;
import pe.edu.pucp.spotipucp.modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

public class UsuarioBLImpl implements UsuarioBL {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    public List<Usuario> findAll() throws BLException {
        try {
            return usuarioDAO.findAll();
        } catch (SQLException e) {
            throw new BLException("No se pudo listar los usuarios", e);
        }
    }

    @Override
    public Usuario findById(int id) throws BLException {
        try {
            Usuario usuario = usuarioDAO.findById(id);
            if (usuario == null) {
                throw new NoEncontradoException("No se encontró el usuario con id: " + id);
            }
            return usuario;
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar el usuario", e);
        }
    }
}
