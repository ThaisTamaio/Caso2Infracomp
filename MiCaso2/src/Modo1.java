import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Modo1 {

    private Integer TP; // Tamaño de la página en bytes
    private Integer MP; // Número de marcos de página disponibles
    private Integer NF; // Número de filas de la matriz
    private Integer NC; // Número de columnas de la matriz
    private Integer TE; // Tamaño de los elementos de la matriz
    Matriz matrizA; // Creación de la matriz A
    Matriz matrizB; // Creación de la matriz B
    Matriz matrizC; // Creación de la matriz B

    public Modo1(Integer TP, Integer MP, Integer NF, Integer NC, Integer TE, Matriz MatrizA, Matriz MatrizB, Matriz MatrizC) {
        this.TP = TP;
        this.MP = MP;
        this.NF = NF;
        this.NC = NC;
        this.TE = TE;
        this.matrizA = MatrizA;
        this.matrizB = MatrizB;
        this.matrizC = MatrizC;
    }

    public void generarMatricesAleatorias() { // Método para generar las matrices A y B aleatorias
        matrizA.generarMatrizAleatoria(); // Genera la matriz A aleatoria
        matrizB.generarMatrizAleatoria(); // Genera la matriz B aleatoria
    }

    public Matriz sumarMatrices(Matriz matrizA, Matriz matrizB)
    {
        // Crear una nueva matriz para almacenar el resultado de la suma
        Matriz matrizC = new Matriz (NF, NC, TE);
    
        // Recorrer las filas y columnas de las matrices de entrada
        for(int i = 0; i < NF; i++){
            for(int j = 0; j < NC; j++)
            {
                // Sumar los elementos correspondientes de las dos matrices de entrada
                matrizC.getMatriz() [i][j] = matrizA.getMatriz()[i][j] + matrizB.getMatriz()[i][j];
            }
        }
    
        // Devolver la matriz resultante
        return matrizC;
    }    

    public void generarReferenciasDePagina() {
        // Calcular tamaño de página y de matriz
        int tamañoPagina = TP * TE;
        int tamañoMatriz = NF * NC * TE;
        // Calcular cantidad de páginas que ocupa una matriz completa
        int páginasPorMatriz = (tamañoMatriz + tamañoPagina - 1) / tamañoPagina;
        // Calcular total de referencias a generar
        int totalReferencias = NF * NC * 3;
        // Crear objeto StringBuilder para almacenar el resultado
        StringBuilder resultado = new StringBuilder();
    
        // Agregar información básica al resultado
        resultado.append("TP=").append(TP).append("\n");
        resultado.append("NF=").append(NF).append("\n");
        resultado.append("NC=").append(NC).append("\n");
        resultado.append("NR=").append(totalReferencias).append("\n");
        
        // Crear listas para almacenar referencias de cada matriz
        List<String> listaMatrizA = new ArrayList<String>();
        List<String> listaMatrizB = new ArrayList<String>();
        List<String> listaMatrizC = new ArrayList<String>();
        
        // Iterar sobre cada matriz y cada elemento
        for (int k = 0; k < 3; k++) {
            // Asignar letra a la matriz
            char matriz = (char) ('A' + k);
            // Iterar sobre cada fila de la matriz
            for (int i = 0; i < NF; i++) {
                // Calcular número de página virtual y desplazamiento de la fila
                int paginaVirtualFila = i / (TP / NC);
                int desplazamientoFila = (i % (TP / NC)) * NC;
                // Iterar sobre cada columna de la matriz
                for (int j = 0; j < NC; j++) {
                    // Calcular número de página virtual y desplazamiento de la columna
                    int paginaVirtualColumna = j / (TP / NF);
                    int desplazamientoColumna = j % (TP / NF);
                    // Calcular número de página virtual y desplazamiento de la referencia
                    int paginaVirtual = paginaVirtualFila * MP + paginaVirtualColumna + k * páginasPorMatriz;
                    int desplazamiento = (desplazamientoFila + desplazamientoColumna) * TE;
                    // Crear cadena de referencia con formato "[Matriz-Fila-Columna],PaginaVirtual,Desplazamiento"
                    String referencia = "[" + matriz + "-" + i + "-" + j + "]," + paginaVirtual + "," + desplazamiento + "\n";
                    
                    // Agregar referencia a la lista correspondiente según la matriz
                    switch(matriz) {
                        case 'A':
                            listaMatrizA.add(referencia);
                            break;
                        case 'B':
                            listaMatrizB.add(referencia);
                            break;
                        case 'C':
                            listaMatrizC.add(referencia);
                            break;
                    }
                }
            }
        }
    
        // Agregar las referencias de cada matriz al resultado
        for (int i = 0; i < listaMatrizA.size(); i++) 
        {
            resultado.append(listaMatrizA.get(i));
            resultado.append(listaMatrizB.get(i));
            resultado.append(listaMatrizC.get(i));
        }
        
        // Crear nombre de archivo único con marca de tiempo
        String nombreArchivo = "salida.txt";
        
        // Crear archivo en la carpeta data
        String rutaArchivo = "data/" + nombreArchivo;
        File archivoSalida = new File(rutaArchivo);
        
        // Guardar resultados en archivo de texto
        try (PrintWriter out = new PrintWriter(new FileWriter(archivoSalida))) {
            out.print(resultado.toString());
        } catch (IOException e) {
            System.err.println("Error al guardar archivo: " + e.getMessage());
        }
    }
}
