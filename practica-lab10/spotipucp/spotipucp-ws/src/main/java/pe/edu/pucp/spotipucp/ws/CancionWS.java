package pe.edu.pucp.spotipucp.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import pe.edu.pucp.spotipucp.bl.CancionBL;
import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.bl.impl.CancionBLImpl;
import pe.edu.pucp.spotipucp.modelo.Cancion;
import pe.edu.pucp.spotipucp.ws.dto.CancionDTO;
import pe.edu.pucp.spotipucp.ws.mapper.CancionMapper;

import java.util.List;

@WebService(serviceName = "CancionWS",
targetNamespace = "http://ws.spotipucp.pucp.edu.pe/"
)
public class CancionWS {

    private final CancionBL cancionBL=new CancionBLImpl();

    @WebMethod(operationName = "listarCanciones")
    public List<CancionDTO> listarCanciones(){
        try{
            return CancionMapper.toDTOs(cancionBL.findAll());
        }catch(BLException e){
            throw new WebServiceException(e.getMessage());
        }
    }

    @WebMethod(operationName = "seleccionarCancionesAleatorias")
    public List<CancionDTO> seleccionarCancionesAleatorias(){
        try{
            return CancionMapper.toDTOs(cancionBL.seleccionarCancionesAleatorias());
        }catch (BLException e){
            throw new WebServiceException(e.getMessage());
        }
    }


}
