
public class TablaPagina {

    // Un arreglo de EntradaTablaPagina para almacenar las entradas de la tabla de página
    private EntradaTablaPagina[] tabla;


    // Constructor para crear una tabla de página con tamaño especificado
    public TablaPagina(int tam) {
        // Crea una nueva instancia de EntradaTablaPagina con el tamaño especificado
        tabla = new EntradaTablaPagina[tam];
        // Inicializa cada entrada en la tabla de página con el índice correspondiente
        for (int i = 0; i < tam; i++) {
            tabla[i] = new EntradaTablaPagina(i);
        }
    }

    // Método para buscar una página en la tabla de página y devolver su marco asociado
    // También actualiza el bit de edad de las entradas de la tabla de página
    public int buscarPaginaEnTabla(int numPagina) {
        int numMarco = -1;
        // Itera a través de cada entrada en la tabla de página
        for (int i = 0; i < tabla.length; i++) {
            EntradaTablaPagina entrada = tabla[i];
            // Si la entrada coincide con el número de página y está presente en la memoria física
            if (entrada.getNumPagina() == numPagina && entrada.isBitPresencia()) {
                // Asigna el número de marco de la entrada a la variable numMarco
                numMarco = entrada.getNumMarco();
                // Establece el bit de edad de la entrada a 0
                entrada.setBitEdad(0);
            // Si la entrada está presente en la memoria física pero no coincide con el número de página
            } else if (entrada.isBitPresencia()) {
                // Incrementa el bit de edad de la entrada en 1
                entrada.setBitEdad(entrada.getBitEdad() + 1);
            }
        }
        // Devuelve el número de marco asociado con el número de página especificado (si se encontró)
        return numMarco;
    }

    // Método para actualizar una entrada en la tabla de página con la información especificada
    public void actualizarEntradaTabla(int numPagina, int numMarco, boolean bitPresencia, boolean bitModificado) {
        // Obtiene la entrada de la tabla de página correspondiente al número de página especificado
        EntradaTablaPagina entrada = tabla[numPagina];
        // Actualiza los atributos de la entrada con la información especificada
        entrada.setNumMarco(numMarco);
        entrada.setBitPresencia(bitPresencia);
        entrada.setBitModificado(bitModificado);
        entrada.setBitEdad(0);
    }

    // Métodos adicionales de la clase TablaPagina

    // Getter y Setter para el arreglo de EntradaTablaPagina
    public EntradaTablaPagina[] getTabla() {
        return tabla;
    }

    public void setTabla(EntradaTablaPagina[] tabla) {
        this.tabla = tabla;
    }
}
