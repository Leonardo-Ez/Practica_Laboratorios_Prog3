package pe.edu.pucp.spotipucp.ws;


import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import pe.edu.pucp.spotipucp.bl.UsuarioBL;
import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.bl.impl.UsuarioBLImpl;
import pe.edu.pucp.spotipucp.modelo.Usuario;
import pe.edu.pucp.spotipucp.ws.dto.UsuarioDTO;
import pe.edu.pucp.spotipucp.ws.mapper.UsuarioMapper;

import java.util.List;

@WebService(serviceName = "UsuarioWS",
targetNamespace = "http://ws.spotipucp.pucp.edu.pe/"
)
public class UsuarioWS {

    private final UsuarioBL usuarioBL=new UsuarioBLImpl();

    @WebMethod(operationName = "listarUsuarios")
    public List<UsuarioDTO> listarUsuarios() {
        try{
            return UsuarioMapper.toDTOs(usuarioBL.findAll());
        }catch(BLException e){
            throw new WebServiceException(e.getMessage());
        }
    }



}
