import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        //Modo 1

        List<Integer> parametros = leerArchivoModo1("inicial.txt");

        Modo1 modo1 = new Modo1(parametros.get(0), parametros.get(1), parametros.get(2), parametros.get(3));

        modo1.generarReferenciasDePagina();

        //Modo 2

        List<Integer> paginas = leerArchivoModo2("entrada.txt");

        Modo2 modo2 = new Modo2(10000, paginas);

        Integer fallosPagina = modo2.ejecutarModo2();

        System.out.println("Fallos de página: " + fallosPagina);
    
    }

    public static List<Integer> leerArchivoModo1(String filename) throws IOException {
        List<Integer> parametros = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("./data/" + filename));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("TP = ")) {
                parametros.add(Integer.parseInt(line.substring(5).trim()));
            } else if (line.startsWith("NF = ")) {
                parametros.add(Integer.parseInt(line.substring(5).trim()));
            } else if (line.startsWith("NC = ")) {
                parametros.add(Integer.parseInt(line.substring(5).trim()));
            } else if (line.startsWith("TE = ")) {
                parametros.add(Integer.parseInt(line.substring(5).trim()));
            } else if (line.startsWith("MP = ")) {
                parametros.add(Integer.parseInt(line.substring(5).trim()));
            }
        }
        reader.close();
        return parametros;
    }

    public static List<Integer> leerArchivoModo2(String filename) throws IOException {
        List<Integer> paginas = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("./data/" + filename));
        String linea;
        while ((linea = reader.readLine()) != null) {
            if (!linea.startsWith("TP") && !linea.startsWith("NF") && !linea.startsWith("NC") && !linea.startsWith("NR")) {
                String[] partes = linea.split(",");
                int numPagina = Integer.parseInt(partes[1]);
                paginas.add(numPagina);
            }
        }
        reader.close();
        return paginas;
    }

    
}
