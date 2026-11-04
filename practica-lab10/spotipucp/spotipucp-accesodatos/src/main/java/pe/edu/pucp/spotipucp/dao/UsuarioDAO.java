package pe.edu.pucp.spotipucp.dao;

import pe.edu.pucp.spotipucp.modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {
    List<Usuario> findAll() throws SQLException;
    Usuario findById(int id) throws SQLException;
}
