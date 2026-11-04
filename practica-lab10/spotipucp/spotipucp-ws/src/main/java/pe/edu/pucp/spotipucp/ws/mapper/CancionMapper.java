package pe.edu.pucp.spotipucp.ws.mapper;

import pe.edu.pucp.spotipucp.modelo.Cancion;
import pe.edu.pucp.spotipucp.ws.dto.CancionDTO;

import java.util.ArrayList;
import java.util.List;

public final class CancionMapper {
    private CancionMapper(){}


    public static CancionDTO toDTO(Cancion cancion){
        if(cancion==null){
            return null;
        }
        CancionDTO dto=new CancionDTO();

        dto.setId(cancion.getId());
        dto.setArtista(cancion.getArtista());
        dto.setDuracionSegundos(cancion.getDuracionSegundos());
        dto.setTitulo(cancion.getTitulo());
        dto.setGenero(cancion.getGenero());
        return dto;
    }

    public static List<CancionDTO> toDTOs(List<Cancion> cancions){
        if(cancions==null){
            return null;
        }
        List<CancionDTO> dtos=new ArrayList<CancionDTO>();
        for(Cancion cancion:cancions){
            dtos.add(toDTO(cancion));
        }
        return dtos;
    }

    public static Cancion toModelo(CancionDTO dto){
        if(dto==null){
            return null;
        }
        Cancion modelo=new Cancion();
        modelo.setId(dto.getId());
        modelo.setArtista(dto.getArtista());
        modelo.setGenero(dto.getGenero());
        modelo.setDuracionSegundos(dto.getDuracionSegundos());
        modelo.setTitulo(dto.getTitulo());
        return modelo;
    }

}

