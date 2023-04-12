import java.util.ArrayList;
import java.util.List;

public class Actualizador extends Thread {
    private int[][] tablaPaginas;
    private ArrayList<Integer> listaMarcosPagina;
    private List<Referencia> referencias;
    private int indiceProximaReferencia;

    public Actualizador(int[][] tablaPaginas, ArrayList<Integer> listaMarcosPagina, List<Referencia> referencias) {
        this.tablaPaginas = tablaPaginas;
        this.listaMarcosPagina = listaMarcosPagina;
        this.referencias = referencias;
        this.indiceProximaReferencia = 0;
    }

    @Override
    public void run() {
        while (true) {
            if (indiceProximaReferencia < referencias.size()) {
                Referencia referencia = referencias.get(indiceProximaReferencia);
                actualizar(referencia);
                indiceProximaReferencia++;
            }
        }
    }

    private int obtenerMarcoPagina(int paginaVirtual) {
        int marcoPagina = -1;
        for (int i = 0; i < listaMarcosPagina.size(); i++) {
            if (listaMarcosPagina.get(i) == -1) {
                marcoPagina = i;
                listaMarcosPagina.set(i, paginaVirtual);
                break;
            }
        }
        return marcoPagina;
    }

    private void actualizarTablaPaginas(int[][] tablaPaginas,int paginaVirtual, int marcoPagina) {
        int nc = tablaPaginas[0].length;
        int fila = paginaVirtual / nc;
        int columna = paginaVirtual % nc;
        tablaPaginas[fila][columna] = marcoPagina;
    }

    public void actualizarMarcoPagina(int[][] tablaPaginas, ArrayList<Integer> listaMarcosPagina, int paginaVirtual, int marcoPagina) {
        // Buscar la entrada correspondiente en la tabla de páginas
        int nf = tablaPaginas.length;
        int nc = tablaPaginas[0].length;
        int fila = paginaVirtual / nc;
        int columna = paginaVirtual % nc;
        if (fila >= nf) {
            throw new IllegalArgumentException("Número de página virtual fuera de rango");
        }
        if (tablaPaginas[fila][columna] == marcoPagina) {
            // La entrada ya está actualizada, no se necesita hacer nada
            return;
        }
        if (tablaPaginas[fila][columna] != -1) {
            // La entrada está ocupada por otro marco de página, se debe liberar primero
            int marcoAnterior = tablaPaginas[fila][columna];
            listaMarcosPagina.set(marcoAnterior, -1);
        }
        // Actualizar la entrada en la tabla de páginas
        tablaPaginas[fila][columna] = marcoPagina;
        // Actualizar la lista de marcos de página
        listaMarcosPagina.set(marcoPagina, paginaVirtual);
    }

    public void actualizar(Referencia referencia) {
        int paginaVirtual = referencia.getPaginaVirtual();
        int marcoPagina = obtenerMarcoPagina(paginaVirtual);
        actualizarTablaPaginas(tablaPaginas, paginaVirtual, marcoPagina);
        actualizarMarcoPagina(tablaPaginas, listaMarcosPagina, paginaVirtual, marcoPagina);
    }
    
}
