package pe.edu.pucp.spotipucp.ws.mapper;

import pe.edu.pucp.spotipucp.modelo.Usuario;
import pe.edu.pucp.spotipucp.ws.dto.UsuarioDTO;

import java.util.ArrayList;
import java.util.List;

public final class UsuarioMapper {
    private UsuarioMapper() {}

    public static UsuarioDTO toDTO(Usuario usuario){
        if(usuario==null){
            return null;
        }
        UsuarioDTO dto=new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setCorreo(usuario.getCorreo());
        dto.setCodigo(usuario.getCodigo());
        dto.setNombre(usuario.getNombre());
        return dto;
    }

    public static List<UsuarioDTO> toDTOs(List<Usuario> usuarios){
        if(usuarios==null){
            return null;
        }

        List<UsuarioDTO> dtos=new ArrayList<UsuarioDTO>();
        for(Usuario usuario:usuarios){
            dtos.add(toDTO(usuario));
        }
        return dtos;
    }

    public static Usuario toModelo(UsuarioDTO dto){
        if(dto==null){
            return null;
        }
        Usuario modelo=new Usuario();
        modelo.setId(dto.getId());
        modelo.setCorreo(dto.getCorreo());
        modelo.setNombre(dto.getNombre());
        modelo.setCodigo(dto.getCodigo());
        return modelo;

    }

}
