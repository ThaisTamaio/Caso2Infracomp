import java.util.Random;

public class Matriz {
    
    private int[][] matriz; // Matriz de enteros
    private int nf; // Número de filas
    private int nc; // Número de columnas
    private int te; // Tamaño de los elementos
    
    public Matriz(int nf, int nc, int te) { // Constructor de la clase Matriz
        this.nf = nf;
        this.nc = nc;
        this.te = te;
        matriz = new int[nf][nc]; // Creación de la matriz con nf filas y nc columnas
    }
    
    public void generarMatrizAleatoria() { // Método para generar una matriz aleatoria
        Random random = new Random();
        int rango = (int) Math.pow(2, te); // El rango de los elementos de la matriz es de 0 a 2^te - 1
        for (int i = 0; i < nf; i++) { // Ciclo para recorrer las filas de la matriz
            for (int j = 0; j < nc; j++) { // Ciclo para recorrer las columnas de la matriz
                matriz[i][j] = random.nextInt(rango); // Genera un número aleatorio entre 0 y rango-1 y lo asigna a la posición i,j de la matriz
            }
        }
    }
    
    // Otros métodos de la clase Matriz
    
    // Getter y Setter
    public int[][] getMatriz() { // Getter para la matriz
        return matriz;
    }

    public void setMatriz(int[][] matriz) { // Setter para la matriz
        this.matriz = matriz;
    }

    public int getNf() { // Getter para el número de filas
        return nf;
    }

    public void setNf(int nf) { // Setter para el número de filas
        this.nf = nf;
    }

    public int getNc() { // Getter para el número de columnas
        return nc;
    }

    public void setNc(int nc) { // Setter para el número de columnas
        this.nc = nc;
    }

    public int getTe() { // Getter para el tamaño de los elementos
        return te;
    }

    public void setTe(int te) { // Setter para el tamaño de los elementos
        this.te = te;
    }
}