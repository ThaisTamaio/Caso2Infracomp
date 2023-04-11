public class Paginacion {
    private Memoria memoria;
    private TablaPagina tabla;

    public Paginacion(int tamanoMemoria, int tamanoTabla) {
        // Se crea una nueva instancia de Memoria y TablaPagina con los tamaños especificados
        memoria = new Memoria(tamanoMemoria);
        tabla = new TablaPagina(tamanoTabla);
    }

    public void cargarPaginaEnMemoria(int numPagina, int[] contenido) {
        // Busca un marco libre en memoria para cargar la página
        int numMarco = buscarMarcoLibre();
        if (numMarco == -1) {
            // Si no hay marcos libres, se selecciona una víctima para reemplazar
            numMarco = seleccionarVictima();
            MarcoPagina victima = memoria.getMarcoPagina(numMarco);
            if (victima.isBitModificado()) {
                // Si la página víctima fue modificada, se guarda en disco antes de reemplazarla
                guardarPaginaEnDisco(victima);
            }
            // Actualiza la entrada correspondiente en la tabla de páginas
            tabla.actualizarEntradaTabla(victima.getNumPagina(), -1, false, false);
        }
        // Crea un nuevo marco para la página y lo guarda en memoria
        MarcoPagina nuevoMarco = new MarcoPagina(numPagina, contenido, true, false, 0);
        memoria.setMarcoPagina(numMarco, nuevoMarco);
        // Actualiza la entrada correspondiente en la tabla de páginas
        tabla.actualizarEntradaTabla(numPagina, numMarco, true, false);
    }

    private int buscarMarcoLibre() {
        // Busca el primer marco libre en memoria
        int numMarcoLibre = -1;
        for (int i = 0; i < memoria.getTamano(); i++) {
            if (memoria.getMarcoPagina(i) == null) {
                numMarcoLibre = i;
                break;
            }
        }
        return numMarcoLibre;
    }

    private int seleccionarVictima() {
        // Busca la página víctima a reemplazar en memoria
        int numVictima = 0;
        int maxEdad = -1;
        for (int i = 0; i < memoria.getTamano(); i++) {
            MarcoPagina marco = memoria.getMarcoPagina(i);
            if (marco.isBitPresencia()) {
                // Si el marco está en uso, busca su entrada correspondiente en la tabla de páginas
                EntradaTablaPagina entrada = tabla.getTabla()[marco.getNumPagina()];
                int edad = entrada.getBitEdad();
                if (edad > maxEdad) {
                    // Si la edad es mayor que la edad máxima encontrada hasta ahora,
                    // se actualiza la edad máxima y se selecciona ese marco como víctima
                    maxEdad = edad;
                    numVictima = i;
                }
            } else {
                // Si el marco no está en uso, se selecciona como víctima
                numVictima = i;
                break;
            }
        }
        return numVictima;
    }

    private void guardarPaginaEnDisco(MarcoPagina marco) {
        // TODO: No se que va acá pero debe ser el código para guardar la página en disco
    }

    // Getters y Setters
    public Memoria getMemoria() {
        return memoria;
    }

    public void setMemoria(Memoria memoria) {
        this.memoria = memoria;
    }

    public TablaPagina getTabla() {
        return tabla;
    }

    public void setTabla(TablaPagina tabla) {
        this.tabla = tabla;
    }
}
