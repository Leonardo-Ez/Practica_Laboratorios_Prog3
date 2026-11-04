package pe.edu.pucp.spotipucp.dao.impl;

import pe.edu.pucp.spotipucp.dao.UsuarioDAO;
import pe.edu.pucp.spotipucp.db.DBManager;
import pe.edu.pucp.spotipucp.modelo.Usuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    @Override
    public List<Usuario> findAll() throws SQLException {
        String sql = "{call listar_usuarios()}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql);
             ResultSet rs = cmd.executeQuery()) {

            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                usuarios.add(mapear(rs));
            }
            return usuarios;
        }
    }

    @Override
    public Usuario findById(int id) throws SQLException {
        String sql = "{call buscar_usuario_por_id(?)}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id", id);
            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setCodigo(rs.getString("codigo"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setCorreo(rs.getString("correo"));
        return usuario;
    }
}
