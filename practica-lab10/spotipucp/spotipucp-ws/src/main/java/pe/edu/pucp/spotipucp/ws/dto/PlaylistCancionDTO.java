package pe.edu.pucp.spotipucp.ws.dto;

public class PlaylistCancionDTO {
    private int id;
    private CancionDTO cancion;
    private int orden;

    public PlaylistCancionDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CancionDTO getCancion() {
        return cancion;
    }

    public void setCancion(CancionDTO cancion) {
        this.cancion = cancion;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
