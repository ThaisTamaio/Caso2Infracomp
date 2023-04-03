import java.util.ArrayList;
import java.util.List;

public class Memoria {

    private int tamano; // tamaño de la memoria
    private List<MarcoPagina> marcosPagina;   // lista de marcos de página


    // Constructor que inicializa la lista de marcos de página con valores nulos
    public Memoria(int tamano) {
        this.tamano = tamano;
        marcosPagina = new ArrayList<>();
        for (int i = 0; i < tamano; i++) {
            marcosPagina.add(null);
        }
    }

    // Método para obtener el tamaño de la memoria
    public int getTamano() {
        return tamano;
    }

    // Método para establecer el tamaño de la memoria
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    // Método para obtener el marco de página en el índice especificado
    public MarcoPagina getMarcoPagina(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("El índice está fuera de rango");
        }
        return marcosPagina.get(indice);
    }

    // Método para establecer el marco de página en el índice especificado
    public void setMarcoPagina(int indice, MarcoPagina marcoPagina) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("El índice está fuera de rango");
        }
        marcosPagina.set(indice, marcoPagina);
    }

    public Boolean espacio(){
        Boolean lleno = false;
        for (int i = 0; i < this.tamano; i++){
            if(getMarcoPagina(i)== null){
                lleno =  true;
            }
        }
        return lleno;
    }


    public synchronized Integer actualizar(Integer ref){
        Integer refVieja = null;
        Boolean hayEspacio = espacio();
        if(hayEspacio){
            for (int i = 0; i < this.tamano; i++){
                if(marcosPagina.get(i)== null){
                    marcosPagina.set(i, ref);
                    
                }
            }


        }


    }

}
