import java.util.ArrayList;
import java.util.List;

public class Actualizador extends Thread{

    private List<Referencia> referencias;
    private int[][] tablaPaginas;
    private ArrayList<Integer> listaMarcosPagina;
    private int punteroReferencia = 0;
    private int punteroMarcoPagina = 0;
    private int fallosPagina = 0;
    private Thread threadEnvejecimiento;
    private boolean envejecimientoActivo;

    public Actualizador(List<Referencia> referencias, int[][] tablaPaginas,
            ArrayList<Integer> listaMarcosPagina) {
        this.referencias = referencias;
        this.tablaPaginas = tablaPaginas;
        this.listaMarcosPagina = listaMarcosPagina;
        this.envejecimientoActivo = true;
    }

    public void run(Integer TP) {
        threadEnvejecimiento = new Thread(new AlgoritmoEnvejecimiento(tablaPaginas, listaMarcosPagina));
        threadEnvejecimiento.start();

        while (punteroReferencia < referencias.size()) {
            // Obtener la siguiente referencia
            Referencia referencia = referencias.get(punteroReferencia);
            punteroReferencia++;

            // Determinar si la página virtual correspondiente está en memoria real
            int marcoPagina = tablaPaginas[referencia.getPaginaVirtual()][referencia.getDesplazamiento() / TP];
            if (marcoPagina == -1) {
                // Se produce una falla de página, seleccionar un marco de página en memoria real para reemplazar
                fallosPagina++;
                try {
                    marcoPagina = seleccionarMarcoPagina(referencia.getPaginaVirtual());
                } catch (Exception e) { e.printStackTrace();}
                // Cargar la nueva página en el marco de página seleccionado
                cargarPagina(marcoPagina, referencia.getPaginaVirtual());
            }
            // Actualizar el bit de referencia de la entrada en la tabla de páginas
            tablaPaginas[referencia.getPaginaVirtual()][referencia.getDesplazamiento() / TP] |= 0x80;

            // Esperar 2 milisegundos antes de procesar la siguiente referencia
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Error en thread de actualización de tabla de páginas: " + e.getMessage());
            }
        }
        // Detener el thread de envejecimiento al terminar la actualización de la tabla de páginas
        envejecimientoActivo = false;
        try {
            threadEnvejecimiento.join();
        } catch (InterruptedException e) {
            System.out.println("Error al detener el thread de envejecimiento: " + e.getMessage());
        }
    }

    private int seleccionarMarcoPagina(int paginaVirtual) throws Exception {
        ArrayList<Integer> marcosPagina = new ArrayList<>();
        synchronized (tablaPaginas) {
            for (int i = 0; i < tablaPaginas[paginaVirtual].length; i++) {
                if (tablaPaginas[paginaVirtual][i] == -1) {
                    return listaMarcosPagina.get(i);
                }
                marcosPagina.add(tablaPaginas[paginaVirtual][i]);
            }
        }
    
        int marcoPagina = -1;
        int puntero = 0;
        synchronized (listaMarcosPagina) {
            while (listaMarcosPagina.isEmpty()) {
                try {
                    listaMarcosPagina.wait();
                } catch (InterruptedException e) {
                    System.out.println("Error al esperar un marco de página libre: " + e.getMessage());
                }
            }
    
            boolean encontrado = false;
            int contador = 0; // variable contador para evitar un bucle infinito
            while (!encontrado && contador < marcosPagina.size()) { // se agrega una condición de salida adicional
                int marco = marcosPagina.get(puntero);
                int referencia = tablaPaginas[paginaVirtual][marco] & 1;
                int modificacion = (tablaPaginas[paginaVirtual][marco] >> 1) & 1;
                if (referencia == 0 && modificacion == 0) {
                    marcoPagina = marco;
                    encontrado = true;
                } else {
                    tablaPaginas[paginaVirtual][marco] &= ~(1 << 0); // borrar bit de referencia
                    if (referencia == 1) {
                        tablaPaginas[paginaVirtual][marco] |= (1 << 1); // establecer bit de modificación
                    } else {
                        puntero = (puntero + 1) % marcosPagina.size(); // avanzar puntero
                    }
                    contador++; // se incrementa el contador en cada iteración del bucle
                }
            }
    
            if (!encontrado) {
                throw new Exception("No hay marcos de página disponibles para reemplazar.");
            }
    
            listaMarcosPagina.remove((Integer) marcoPagina);
        }
    
        return marcoPagina;
    }
    
    private void cargarPagina(int marcoPagina, int paginaVirtual) {
        synchronized (tablaPaginas) {
            // Actualizar la entrada correspondiente en la tabla de páginas
            for (int i = 0; i < listaMarcosPagina.size(); i++) {
                if (listaMarcosPagina.get(i) == marcoPagina) {
                    synchronized (listaMarcosPagina) {
                        tablaPaginas[paginaVirtual][i] = marcoPagina;
                    }
                }
            }
        }
    }    

    public static void inicializarTablaPaginas(int[][] tablaPaginas) {
        for (int i = 0; i < tablaPaginas.length; i++) {
            for (int j = 0; j < tablaPaginas[i].length; j++) {
                tablaPaginas[i][j] = -1;
            }
        }
    }

    public static void inicializarListaMarcosPagina(ArrayList<Integer> listaMarcosPagina) {
        for (int i = 0; i < listaMarcosPagina.size(); i++) {
            listaMarcosPagina.set(i, i);
        }
    }

}

