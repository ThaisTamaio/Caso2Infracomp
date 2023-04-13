import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Modo2 {
    private int TP;
    private int NF;
    private int NC;
    private int NR;
    private List<Referencia> referencias;

    public void leerArchivo() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("entrada.txt"));
        String linea = br.readLine();

        // Leer los valores de TP, NF, NC y NR
        while (linea != null) {
            if (linea.startsWith("TP=")) {
                TP = Integer.parseInt(linea.substring(3));
            } else if (linea.startsWith("NF=")) {
                NF = Integer.parseInt(linea.substring(3));
            } else if (linea.startsWith("NC=")) {
                NC = Integer.parseInt(linea.substring(3));
            } else if (linea.startsWith("NR=")) {
                NR = Integer.parseInt(linea.substring(3));
                break;
            }
            linea = br.readLine();
        }

        // Leer las referencias de memoria virtual
        referencias = new ArrayList<>();
        for (int i = 0; i < NR; i++) {
            linea = br.readLine();
            String[] partes = linea.split(",");
            String[] coords = partes[0].substring(1, partes[0].length() - 1).split("-");
            String matriz = coords[0];
            int paginaVirtual = Integer.parseInt(partes[1]);
            int desplazamiento = Integer.parseInt(partes[2]);
            Referencia referencia = new Referencia(matriz, paginaVirtual, desplazamiento);
            referencias.add(referencia);
        }

        br.close();
    }

    public int[][] crearTablaPaginas() {
        int[][] tabla = new int[NF][NC];
        for (int i = 0; i < NF; i++) {
            for (int j = 0; j < NC; j++) {
                tabla[i][j] = -1; // -1 indica que no hay mapeo a marco de página correspondiente
            }
        }
        return tabla;
    }

    public ArrayList<Integer> crearListaMarcosPagina() {
        int numMarcosPagina = (int) Math.ceil((double) NR / (TP * NF * NC));
        ArrayList<Integer> listaMarcosPagina = new ArrayList<>(numMarcosPagina);
        for (int i = 0; i < numMarcosPagina; i++) {
            listaMarcosPagina.add(-1); // -1 indica que el marco de página está vacío
        }
        return listaMarcosPagina;
    }
    
}