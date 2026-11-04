package pe.edu.pucp.spotipucp.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import pe.edu.pucp.spotipucp.bl.PlaylistBL;
import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.bl.impl.PlaylistBLImpl;
import pe.edu.pucp.spotipucp.ws.dto.PlaylistDTO;
import pe.edu.pucp.spotipucp.ws.mapper.PlaylistMapper;

import java.util.List;

@WebService (serviceName = "PlaylistWS",
    targetNamespace = "http://ws.spotipucp.pucp.edu.pe/"
)
public class PlaylistWS {
    private final PlaylistBL playlistBL=new PlaylistBLImpl();

    @WebMethod(operationName = "listarPlaylists")
    public List<PlaylistDTO> listarPlaylists() {
        try{
            return PlaylistMapper.toDTOs(playlistBL.findAll());
        }catch(BLException ex){
            throw new WebServiceException(ex.getMessage());
        }
    }

    @WebMethod(operationName = "obtenerPlaylist")
    public PlaylistDTO obtenerPlaylist(@WebParam(name="id") int id) {
        try{
            return PlaylistMapper.toDTO(playlistBL.findById(id));
        }catch (BLException ex){
            throw new WebServiceException(ex.getMessage());
        }
    }

    @WebMethod(operationName = "listarPlaylistsPorUsuario")
    public List<PlaylistDTO> listarPlaylistsPorUsuario(@WebParam(name="idUsuario") int idUsuario) {
        try{
            return PlaylistMapper.toDTOs(playlistBL.findByUsuario(idUsuario));
        }catch (BLException ex){
            throw new WebServiceException(ex.getMessage());
        }
    }

    @WebMethod(operationName = "registrarPlaylist")
    public int registrarPlaylist(@WebParam(name="idUsuario") int idUsuario,
                                 @WebParam(name="nombre") String nombre,
                                 @WebParam(name="idsCanciones")List<Integer> idsCanciones){
     try {
         return playlistBL.crearPlaylistConCanciones(idUsuario,nombre,idsCanciones).getId();
     }catch (BLException ex){
         throw new WebServiceException(ex.getMessage());
     }
    }

    @WebMethod(operationName = "generarPlaylists")
    public int generarPlaylists(){
        try{
            return playlistBL.generarPlaylistsParaTodos().size();
        }catch (BLException ex){
            throw new WebServiceException(ex.getMessage());
        }
    }

    @WebMethod(operationName = "eliminarPlaylist")
    public void eliminarPlaylist(@WebParam(name="id") int id){
        try {
            playlistBL.delete(id);
        }catch (BLException ex){
            throw new WebServiceException(ex.getMessage());
        }
    }
}
