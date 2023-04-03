public class AlgoritmoEnvejecimiento {
    
    private TablaPagina tablaPagina;
    private Memoria memoria;
    private int punteroActual;
    private int contadorFallos;
    
    public AlgoritmoEnvejecimiento(TablaPagina tablaPagina, Memoria memoria) {
        this.tablaPagina = tablaPagina;
        this.memoria = memoria;
        this.punteroActual = 0;
        this.contadorFallos = 0;
    }
    
    public MarcoPagina getMarco(int numPagina) {
        int numMarco = tablaPagina.buscarPaginaEnTabla(numPagina);
        if (numMarco == -1) {
            numMarco = buscarMarcoVictima();
            reemplazarMarco(numMarco, numPagina);
            contadorFallos++;
        }
        MarcoPagina marco = memoria.getMarcoPagina(numMarco);
        marco.setBitPresencia(true);
        tablaPagina.actualizarEntradaTabla(numPagina, numMarco, true, false);
        return marco;
    }
    
    private int buscarMarcoVictima() {
        int numMarcoVictima = -1;
        int maxEdad = -1;
        int tamano = memoria.getTamano();
        for (int i = 0; i < tamano; i++) {
            MarcoPagina marco = memoria.getMarcoPagina(punteroActual);
            EntradaTablaPagina entrada = tablaPagina.getTabla()[marco.getNumPagina()];
            if (!entrada.isBitPresencia()) {
                numMarcoVictima = punteroActual;
                break;
            } else if (entrada.getBitEdad() > maxEdad) {
                numMarcoVictima = punteroActual;
                maxEdad = entrada.getBitEdad();
            }
            punteroActual = (punteroActual + 1) % tamano;
        }
        return numMarcoVictima;
    }
    
    private void reemplazarMarco(int numMarcoVictima, int numPagina) {
        MarcoPagina marcoVictima = memoria.getMarcoPagina(numMarcoVictima);
        EntradaTablaPagina entrada = tablaPagina.getTabla()[marcoVictima.getNumPagina()];
        entrada.setBitPresencia(false);
        if (entrada.isBitModificado()) {
            // Guardar el contenido del marco víctima en memoria secundaria
        }
        MarcoPagina nuevoMarco = new MarcoPagina(numPagina, null, true, false, 0);
        memoria.setMarcoPagina(numMarcoVictima, nuevoMarco);
    }
    
    public int getContadorFallos() {
        return contadorFallos;
    }

    public void actualizarEdades() {
    int tamano = memoria.getTamano();
    for (int i = 0; i < tamano; i++) {
        MarcoPagina marco = memoria.getMarcoPagina(i);
        EntradaTablaPagina entrada = tablaPagina.getTabla()[marco.getNumPagina()];
        if (entrada.isBitPresencia()) {
            entrada.setBitEdad(entrada.getBitEdad() >> 1);
            if (marco.getNumPagina() == punteroActual) {
                entrada.setBitEdad(entrada.getBitEdad() | (1 << 31));
            }
        }
    }
}
    
}
