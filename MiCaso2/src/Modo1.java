import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
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

    public Integer calcularTamPaginaMatriz()
    {
        Integer tamPaginaMatriz = 0;
        tamPaginaMatriz = matrizA.getTamanioBytes() / TP;
        tamPaginaMatriz = (int) Math.ceil(tamPaginaMatriz);
        return tamPaginaMatriz;
    }

    public List<Integer> calcularReferenciasPagina(Integer tamPaginaMatriz) {
        List<Integer> referencias = new ArrayList<>();
        
        // Recorremos las tres matrices en orden de fila y columna
        for (int m = 0; m < 3; m++) {
            Matriz matriz;
            if (m == 0) {
                matriz = matrizA;
            } else if (m == 1) {
                matriz = matrizB;
            } else {
                matriz = matrizC;
            }
            for (int i = 0; i < matriz.getNf(); i++) {
                for (int j = 0; j < matriz.getNc(); j++) {
                    int pos = i * matriz.getNc() + j;
                    int pagina = pos / tamPaginaMatriz;
                    int offset = pos % tamPaginaMatriz;
                    int referencia = pagina * TP + offset * matriz.getTe();
                    referencias.add(referencia);
                }
            }
        }
        
        return referencias;
    }    
    

    public HashMap<Integer, Integer> calcularTablaPaginas(List<Integer> referencias) throws Exception {
        HashMap<Integer, Integer> tablaPaginas = new HashMap<>();
    
        // Inicializamos la tabla de páginas con -1 (marco de página no asignado)
        for (int i = 0; i < referencias.size(); i++) {
            tablaPaginas.put(i, -1);
        }
    
        // Asignamos marcos de página a cada página de las matrices
        int marcosAsignados = 0;
        try {
            for (int i = 0; i < referencias.size(); i++) {
                int pagina = referencias.get(i) / TP;
                if (tablaPaginas.get(pagina) == -1) {
                    // Si la página no tiene marco asignado, asignamos uno disponible
                    tablaPaginas.put(pagina, marcosAsignados);
                    marcosAsignados++;
                    // Si ya hemos asignado todos los marcos disponibles, salimos del bucle
                    if (marcosAsignados == MP) {
                        break;
                    }
                }
            }
    
            if (marcosAsignados < MP) {
                throw new Exception("No se han asignado todos los marcos de página disponibles");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    
        return tablaPaginas;
    }

    public void generarArchivoSalida(HashMap<Integer, Integer> tablaPaginas, List<Integer> referencias, int TP, Matriz matriz) {
        int NR = referencias.size();
    
        // Crear nombre de archivo único con marca de tiempo
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String nombreArchivo = "salida_" + dateTime.format(formatter) + ".txt";
    
        // Crear archivo en la carpeta data
        String rutaArchivo = "data/" + nombreArchivo;
        File archivoSalida = new File(rutaArchivo);
    
        try (PrintWriter writer = new PrintWriter(archivoSalida)) {
            writer.println("TP=" + TP);
            writer.println("NF=" + NF);
            writer.println("NC=" + NC);
            writer.println("NR=" + NR);
            for (int i = 0; i < referencias.size(); i++) {
                int pagina = referencias.get(i) / TP;
                int offset = referencias.get(i) % TP;
                int marco = tablaPaginas.get(pagina);
                String referencia = "[A-" + (i / NC) + "-" + (i % NC) + "]";
                writer.println(referencia + "," + marco + "," + offset);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
