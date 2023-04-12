import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        List<Integer> parametros = leerArchivo("archivo.txt");

        Matriz matrizA = new Matriz(parametros.get(1), parametros.get(2), parametros.get(3));
        Matriz matrizB = new Matriz(parametros.get(1), parametros.get(2), parametros.get(3));
        Matriz matrizC = new Matriz(parametros.get(1), parametros.get(2), parametros.get(3));

        Modo1 modo1 = new Modo1(parametros.get(0), parametros.get(4), parametros.get(1), parametros.get(2), parametros.get(3), matrizA, matrizB, matrizC);

        System.out.println(parametros.get(0));
        System.out.println(parametros.get(1));
        System.out.println(parametros.get(2));
        System.out.println(parametros.get(3));
        System.out.println(parametros.get(4));

        modo1.generarMatricesAleatorias();
        modo1.sumarMatrices(matrizA , matrizB);

        System.out.println("Paso 1");
        modo1.generarReferenciasDePagina();
    }

    public static List<Integer> leerArchivo(String filename) throws IOException {
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
    
}
