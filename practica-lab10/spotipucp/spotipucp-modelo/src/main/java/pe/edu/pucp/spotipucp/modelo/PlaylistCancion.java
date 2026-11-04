package pe.edu.pucp.spotipucp.modelo;

public class PlaylistCancion {
    private int id;
    private Cancion cancion;
    private int orden;

    public PlaylistCancion() {
    }

    public PlaylistCancion(Cancion cancion, int orden) {
        this.cancion = cancion;
        this.orden = orden;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
