public class Referencia {
    private String matriz;
    private int paginaVirtual; // número de página virtual
    private int desplazamiento; // desplazamiento dentro de la página

    public Referencia(String matriz, int paginaVirtual, int desplazamiento) {
        this.matriz = matriz;
        this.paginaVirtual = paginaVirtual;
        this.desplazamiento = desplazamiento;
    }

    // getters para los atributos
    public String getMatriz() {
        return matriz;
    }

    public int getPaginaVirtual() {
        return paginaVirtual;
    }

    public int getDesplazamiento() {
        return desplazamiento;
    }

}