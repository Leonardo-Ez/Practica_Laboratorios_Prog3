package pe.edu.pucp.spotipucp.ws.mapper;

import pe.edu.pucp.spotipucp.modelo.Playlist;
import pe.edu.pucp.spotipucp.ws.dto.PlaylistDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class PlaylistMapper {

    private PlaylistMapper(){}

    public static PlaylistDTO toDTO(Playlist playlist){
        if(playlist==null){
            return null;
        }
        PlaylistDTO dto=new PlaylistDTO();
        dto.setId(playlist.getId());
        dto.setCanciones(PlaylistCancionMapper.toDTOs(playlist.getCanciones()));
        dto.setFechaCreacion(playlist.getFechaCreacion().toString());
        dto.setUsuario(UsuarioMapper.toDTO(playlist.getUsuario()));
        dto.setNombre(playlist.getNombre());
        dto.setDuracionTotalSegundos(playlist.getDuracionTotalSegundos());
        return dto;
    }

    public static List<PlaylistDTO> toDTOs(List<Playlist> playlists){
        if(playlists==null){
            return null;
        }
        List<PlaylistDTO> dtos=new ArrayList<PlaylistDTO>();
        for(Playlist playlist:playlists){
            dtos.add(toDTO(playlist));
        }
        return dtos;
    }

    public static Playlist toModelo(PlaylistDTO playlistDTO){
        if(playlistDTO==null){
            return null;
        }
        Playlist playlist=new Playlist();
        playlist.setId(playlistDTO.getId());
        playlist.setUsuario(UsuarioMapper.toModelo(playlistDTO.getUsuario()));
        playlist.setCanciones(PlaylistCancionMapper.toModelos(playlistDTO.getCanciones()));
        playlist.setNombre(playlistDTO.getNombre());
        playlist.setFechaCreacion(LocalDate.parse(playlistDTO.getFechaCreacion()));
        return playlist;
    }


}
