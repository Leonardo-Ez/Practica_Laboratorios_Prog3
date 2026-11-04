package pe.edu.pucp.spotipucp.app;

import pe.edu.pucp.spotipucp.bl.PlaylistBL;
import pe.edu.pucp.spotipucp.bl.error.BLException;
import pe.edu.pucp.spotipucp.bl.impl.PlaylistBLImpl;
import pe.edu.pucp.spotipucp.modelo.Cancion;
import pe.edu.pucp.spotipucp.modelo.Playlist;
import pe.edu.pucp.spotipucp.modelo.PlaylistCancion;

// Programa de prueba de la capa de negocio (no forma parte de lo que se pide implementar).
// Úselo para verificar que la BD y la BL funcionan ANTES de construir los servicios web.
public class Programa {
    public static void main(String[] args) {
        PlaylistBL playlistBL = new PlaylistBLImpl();

        try {
            playlistBL.generarPlaylistsParaTodos();

            for (Playlist playlist : playlistBL.findAll()) {
                imprimirPlaylist(playlist);
            }
        } catch (BLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void imprimirPlaylist(Playlist playlist) {
        System.out.printf("[%d] %s - %s (%s, %d s)%n",
                playlist.getId(), playlist.getNombre(), playlist.getUsuario().getNombre(),
                playlist.getFechaCreacion(), playlist.getDuracionTotalSegundos());
        for (PlaylistCancion detalle : playlist.getCanciones()) {
            Cancion cancion = detalle.getCancion();
            System.out.printf("    %d. %s - %s%n",
                    detalle.getOrden(), cancion.getTitulo(), cancion.getArtista());
        }
    }
}
