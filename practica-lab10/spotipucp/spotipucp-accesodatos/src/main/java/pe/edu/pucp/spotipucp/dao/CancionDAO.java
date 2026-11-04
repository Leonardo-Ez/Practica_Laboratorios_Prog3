package pe.edu.pucp.spotipucp.dao;

import pe.edu.pucp.spotipucp.modelo.Cancion;

import java.sql.SQLException;
import java.util.List;

public interface CancionDAO {
    List<Cancion> findAll() throws SQLException;
    Cancion findById(int id) throws SQLException;
}
