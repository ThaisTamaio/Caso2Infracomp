import java.util.ArrayList;

public class AlgoritmoEnvejecimiento implements Runnable {
    private int[][] tablaPaginas;
    private ArrayList<Integer> listaMarcosPagina;
    private boolean envejecimientoActivo;

    public AlgoritmoEnvejecimiento(int[][] tablaPaginas, ArrayList<Integer> listaMarcosPagina) {
        this.tablaPaginas = tablaPaginas;
        this.listaMarcosPagina = listaMarcosPagina;
        this.envejecimientoActivo = true;
    }

    public void run() {
        while (envejecimientoActivo) {
            synchronized (listaMarcosPagina) {
                for (int i = 0; i < listaMarcosPagina.size(); i++) {
                    int marcoPagina = listaMarcosPagina.get(i);
                    int paginaVirtual = tablaPaginas[marcoPagina][0];
                    int bitReferencia = tablaPaginas[marcoPagina][1] >> 7;
                    int bitModificacion = tablaPaginas[marcoPagina][1] >> 6;
                    if (bitReferencia == 0 && bitModificacion == 0) {
                        // Si la entrada no ha sido referenciada ni modificada, reemplazarla
                        listaMarcosPagina.remove((Integer) marcoPagina);
                        listaMarcosPagina.add(marcoPagina);
                        tablaPaginas[marcoPagina][0] = -1;
                        tablaPaginas[marcoPagina][1] = 0;
                        break;
                    } else {
                        // Si la entrada ha sido referenciada o modificada, actualizarla
                        tablaPaginas[marcoPagina][1] &= ~(1 << 7);
                        if (bitReferencia == 1) {
                            tablaPaginas[marcoPagina][1] |= (1 << 6);
                        }
                    }
                }
            }

            // Esperar 500 milisegundos antes de volver a ejecutar el algoritmo
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Error en thread de envejecimiento: " + e.getMessage());
            }
        }
    }
    
    public synchronized void liberarMarcoPagina(int marcoPagina) {
        listaMarcosPagina.add(marcoPagina);
        notifyAll();
    }
}
