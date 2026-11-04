package pe.edu.pucp.spotipucp.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private Usuario usuario;
    private String nombre;
    private LocalDate fechaCreacion;
    private List<PlaylistCancion> canciones = new ArrayList<>();

    public Playlist() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<PlaylistCancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<PlaylistCancion> canciones) {
        this.canciones = canciones;
    }

    public int getDuracionTotalSegundos() {
        int total = 0;
        for (PlaylistCancion detalle : canciones) {
            total += detalle.getCancion().getDuracionSegundos();
        }
        return total;
    }
}
