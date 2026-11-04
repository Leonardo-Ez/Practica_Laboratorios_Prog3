package pe.edu.pucp.spotipucp.ws.dto;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDTO {
    private int id;
    private UsuarioDTO usuario;
    private String nombre;
    private String fechaCreacion;
    private List<PlaylistCancionDTO> canciones=new ArrayList<>();
    private int duracionTotalSegundos;

    public int getDuracionTotalSegundos() {
        return duracionTotalSegundos;
    }

    public void setDuracionTotalSegundos(int duracionTotalSegundos) {
        this.duracionTotalSegundos = duracionTotalSegundos;
    }

    public PlaylistDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<PlaylistCancionDTO> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<PlaylistCancionDTO> canciones) {
        this.canciones = canciones;
    }




}
