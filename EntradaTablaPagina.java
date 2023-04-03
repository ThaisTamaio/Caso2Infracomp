public class EntradaTablaPagina {

    private int numPagina;
    private int numMarco;
    private boolean bitPresencia;
    private boolean bitModificado;
    private int bitEdad;

    public EntradaTablaPagina(int numPagina) {
    this.numPagina = numPagina;
    this.numMarco = -1; // inicialmente no tiene marco asignado
    this.bitPresencia = false; // inicialmente no está en memoria
    this.bitModificado = false; // inicialmente no ha sido modificado
    this.bitEdad = 0; // inicialmente su edad es cero
    }

    public int getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(int numPagina) {
        this.numPagina = numPagina;
    }

    public int getNumMarco() {
        return numMarco;
    }

    public void setNumMarco(int numMarco) {
        this.numMarco = numMarco;
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