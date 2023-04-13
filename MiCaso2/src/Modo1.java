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

    public Modo1(Integer TP, Integer MP, Integer NF, Integer NC, Integer TE) {
        this.TP = TP;
        this.MP = MP;
        this.NF = NF;
        this.NC = NC;
        this.TE = TE;
    }

    public void generarReferenciasDePagina() {
        // Calcular total de referencias a generar
        int totalReferencias = NF * NC * 3; // Se calcula el total de referencias que se generarán multiplicando el número de filas por el número de columnas por 3.
        int[] numPaginas = new int[totalReferencias]; // Se crea un arreglo para almacenar los números de página de cada referencia.
        int[] posiciones = new int[totalReferencias]; // Se crea un arreglo para almacenar las posiciones de cada referencia en la página.
        // Crear objeto StringBuilder para almacenar el resultado
        StringBuilder resultado = new StringBuilder(); // Se crea un objeto StringBuilder para almacenar las referencias de página generadas.
    
        // Agregar información básica al resultado
        resultado.append("TP=").append(TP).append("\n"); // Se agrega el tamaño de página al resultado.
        resultado.append("NF=").append(NF).append("\n"); // Se agrega el número de filas al resultado.
        resultado.append("NC=").append(NC).append("\n"); // Se agrega el número de columnas al resultado.
        resultado.append("NR=").append(totalReferencias).append("\n"); // Se agrega el número total de referencias al resultado.

        // Generar las referencias de página para cada elemento de la matriz.
        for (int i = 0; i < NF; i++) {
            for (int j = 0; j < NC; j++) {
                for (int k = 0; k < 3; k++) {
                    // Se calcula la posición de la referencia en la matriz.
                    int posicion = (i * NC + j) * TE + k * NF * NC * TE;
                    // Se calcula el número de página de la referencia.
                    int numPagina = posicion / TP;
                    // Se calcula el desplazamiento de la referencia en la página.
                    int desplazamiento = posicion % TP;
                    // Se almacena el número de página y la posición de la referencia en los arreglos correspondientes.
                    numPaginas[i * NC * 3 + j * 3 + k] = numPagina;
                    posiciones[i * NC * 3 + j * 3 + k] = desplazamiento;
                }
            }
        }

        // Agregar las referencias de cada matriz al resultado.
        for (int i = 0; i < totalReferencias; i++) 
        {
            // Se agrega al resultado la referencia de página generada en el formato "[M-N-P],Q,R",
            // donde M es la matriz a la que pertenece la referencia (A, B o C), N es el índice de fila,
            // P es el índice de columna, Q es el número de página y R es la posición en la página.
            resultado.append(String.format("[%c-%d-%d],%d,%d\n", (char) ('A' + i % 3), i / (NF * NC), i / 3 % NF, numPaginas[i], posiciones[i]));
        }
        
        // Crear nombre de archivo único con marca de tiempo
        String nombreArchivo = "entrada.txt";
        
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
