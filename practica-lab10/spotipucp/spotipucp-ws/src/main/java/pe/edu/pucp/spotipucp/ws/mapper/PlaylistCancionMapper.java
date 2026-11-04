package pe.edu.pucp.spotipucp.ws.mapper;

import pe.edu.pucp.spotipucp.modelo.PlaylistCancion;
import pe.edu.pucp.spotipucp.ws.dto.PlaylistCancionDTO;

import java.util.ArrayList;
import java.util.List;

public final class PlaylistCancionMapper {

    private PlaylistCancionMapper() {}

    public static PlaylistCancionDTO toDTO(PlaylistCancion playlistCancion){
        if(playlistCancion==null){
            return null;
        }
        PlaylistCancionDTO dto=new PlaylistCancionDTO();

        dto.setId(playlistCancion.getId());
        dto.setCancion(CancionMapper.toDTO(playlistCancion.getCancion()));
        dto.setOrden(playlistCancion.getOrden());
        return dto;
    }

    public static List<PlaylistCancionDTO> toDTOs(List<PlaylistCancion> playlistCancion){
        if(playlistCancion==null){
            return null;
        }
        List<PlaylistCancionDTO> dtos=new ArrayList<PlaylistCancionDTO>();
        for(PlaylistCancion playlist: playlistCancion){
            dtos.add(toDTO(playlist));
        }
        return dtos;
    }

    public static PlaylistCancion toModelo(PlaylistCancionDTO dto){
        if(dto==null){
            return null;
        }
        PlaylistCancion modelo=new PlaylistCancion();
        modelo.setId(dto.getId());
        modelo.setOrden(dto.getOrden());
        modelo.setCancion(CancionMapper.toModelo(dto.getCancion()));
        return modelo;
    }


    public static List<PlaylistCancion> toModelos(List<PlaylistCancionDTO> dtos) {
        if (dtos==null){
            return null;
        }
        List<PlaylistCancion> detalles = new ArrayList<>();
        for (PlaylistCancionDTO dto : dtos) {
            detalles.add(toModelo(dto));
        }
        return detalles;
    }

}
