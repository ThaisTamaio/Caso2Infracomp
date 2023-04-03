public class MarcoPagina {
    
    // Atributos de la clase MarcoPagina
    private int numPagina; // Número de página asociado al marco
    private int[] contenido; // Contenido almacenado en el marco
    private boolean bitPresencia; // Bit de presencia para saber si la página está en memoria
    private boolean bitModificado; // Bit de modificación para saber si la página ha sido modificada
    private int bitEdad; // Bit de edad para implementar el algoritmo de reemplazo LRU

    // Constructor de la clase MarcoPagina
    public MarcoPagina(int numPagina, int[] contenido, boolean bitPresencia, boolean bitModificado, int bitEdad) {
        this.numPagina = numPagina;
        this.contenido = contenido;
        this.bitPresencia = bitPresencia;
        this.bitModificado = bitModificado;
        this.bitEdad = bitEdad;
    }

    // Métodos getter y setter para los atributos de la clase
    public int getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(int numPagina) {
        this.numPagina = numPagina;
    }

    public int[] getContenido() {
        return contenido;
    }

    public void setContenido(int[] contenido) {
        this.contenido = contenido;
    }

    public boolean isBitPresencia() {
        return bitPresencia;
    }

    public void setBitPresencia(boolean bitPresencia) {
        this.bitPresencia = bitPresencia;
    }

    public boolean isBitModificado() {
        return bitModificado;
    }

    public void setBitModificado(boolean bitModificado) {
        this.bitModificado = bitModificado;
    }

    public int getBitEdad() {
        return bitEdad;
    }

    public void setBitEdad(int bitEdad) {
        this.bitEdad = bitEdad;
    }

}
