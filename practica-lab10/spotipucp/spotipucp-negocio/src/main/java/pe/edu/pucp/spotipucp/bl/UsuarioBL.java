package pe.edu.pucp.spotipucp.bl;

import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.modelo.Usuario;

import java.util.List;

public interface UsuarioBL {
    List<Usuario> findAll() throws BLException;

    // Lanza NoEncontradoException si el usuario no existe
    Usuario findById(int id) throws BLException;
}
