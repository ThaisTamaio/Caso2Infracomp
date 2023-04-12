import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        Matriz matrizC = new Matriz (NF, NC, TE);

            for(int i = 0; i < NF; i++){
                for(int j = 0; j < NC; j++)
                {
                    matrizC.getMatriz() [i][j] = matrizA.getMatriz()[i][j] + matrizB.getMatriz()[i][j];
                }
            }

        return matrizC;
    }

    public void generarReferenciasDePagina() {
        int tamañoPagina = TP * TE;
        int tamañoMatriz = NF * NC * TE;
        int páginasPorMatriz = (tamañoMatriz + tamañoPagina - 1) / tamañoPagina;
        int totalReferencias = NF * NC * 3;
        StringBuilder resultado = new StringBuilder();
        resultado.append("TP=").append(TP).append("\n");
        resultado.append("NF=").append(NF).append("\n");
        resultado.append("NC=").append(NC).append("\n");
        resultado.append("NR=").append(totalReferencias).append("\n");
        for (int k = 0; k < 3; k++) {
            char matriz = (char) ('A' + k);
            for (int i = 0; i < NF; i++) {
                int paginaVirtualFila = i / (TP / NC);
                int desplazamientoFila = (i % (TP / NC)) * NC;
                for (int j = 0; j < NC; j++) {
                    int paginaVirtualColumna = j / (TP / NF);
                    int desplazamientoColumna = j % (TP / NF);
                    int paginaVirtual = paginaVirtualFila * MP + paginaVirtualColumna + k * páginasPorMatriz;
                    int desplazamiento = (desplazamientoFila + desplazamientoColumna) * TE;
                    resultado.append("[").append(matriz).append("-").append(i).append("-").append(j).append("],").append(paginaVirtual).append(",").append(desplazamiento).append("\n");
                }
            }
        }
    
        // Crear nombre de archivo único con marca de tiempo
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String nombreArchivo = "salida_" + dateTime.format(formatter) + ".txt";
        
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
