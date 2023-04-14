import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nInstrucciones:");
        System.out.println("\nPara correr el modo 1: Modificar el archivo inicial.txt con los parámetros con los cuáles se desee ejecutar el programa. Guardar el archivo y ejecutarlo desde main.");
        System.out.println("\nPara correr el modo 2: Verificar que en el archivo entrada.txt estén generadas referencias de página (las cuales se puede generar con el modo 1), luego correr el modo 2 desde el programa.");

        while (true) {
            System.out.println("\nIngrese la opción a ejecutar (1 para Modo 1, 2 para Modo 2, o 0 para salir):");
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer de entrada

            if (opcion == 1) {
                List<Integer> parametros = leerArchivoModo1("inicial.txt");

                Modo1 modo1 = new Modo1(parametros.get(0), parametros.get(1), parametros.get(2), parametros.get(3));

                modo1.generarReferenciasDePagina();

            } else if (opcion == 2) {
                List<Integer> parametros = leerArchivoModo1("inicial.txt");

                List<Integer> paginas = leerArchivoModo2("entrada.txt");

                Modo2 modo2 = new Modo2(parametros.get(4), paginas);

                Integer fallosPagina = modo2.ejecutarModo2();

                System.out.println("\nFallos de página: " + fallosPagina);

            } else if (opcion == 0) {
                break;

            } else {
                System.out.println("Opción inválida.");
            }
        }

        sc.close();
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
