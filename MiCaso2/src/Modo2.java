import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Modo2 {

    private int MP;
    private List<Integer> paginas;

    public Modo2(Integer MP, List<Integer> paginas) {
        this.MP = MP;
        this.paginas = paginas;
    }

    //Los fallos de página serán retornados

    private static Integer fallosPagina = 0;

    //Listas que indican la ubicación de las páginas en memoria real y virtual

    public static List<Integer> paginasMemoriaReal = new ArrayList<Integer>();
    public static List<Integer> paginasMemoriaVirtual = new ArrayList<Integer>();
    public static List<Integer> paginasEnUso = new ArrayList<Integer>();

    //Tabla necesaria para ejecutar el algortimo de envejecimiento
    private static Map<Integer, Integer> tablaEnvejecimiento = new HashMap<Integer, Integer>();

    public void cargaInicialPaginas()
    {
        // Recorre la lista de páginas y las carga en memoria
        for (int i = 0; i < paginas.size(); i++)
        {
            // Obtiene la página actual
            Integer paginaActual = paginas.get(i);
    
            // Sincroniza el acceso a la lista de páginas en memoria real y de páginas en uso
            synchronized(paginasMemoriaReal)
            {
                synchronized(paginasEnUso)
                {
                    // Si la página actual ya está en memoria real pero no está en uso, la agrega a la lista de páginas en uso
                    if(paginasMemoriaReal.contains(paginaActual) && !paginasEnUso.contains(paginaActual))
                    {
                        paginasEnUso.add(paginaActual);
                    }
                    // Si la página actual no está en memoria real
                    else if (!paginasMemoriaReal.contains(paginaActual))
                    {
                        // Si hay espacio en memoria real, la agrega a la lista de páginas en memoria real y de páginas en uso
                        if (paginasMemoriaReal.size() < MP)
                        {
                            // Si la página actual no está en uso, la agrega a la lista de páginas en uso
                            if (!paginasEnUso.contains(paginaActual))
                            {
                                paginasEnUso.add(paginaActual);
                            }
    
                            // Agrega la página actual a la lista de páginas en memoria real
                            paginasMemoriaReal.add(paginaActual);
                        }
                        // Si no hay espacio en memoria real, la agrega a la lista de páginas en memoria virtual
                        else if (!paginasMemoriaVirtual.contains(paginaActual))
                        {
                            synchronized (paginasMemoriaVirtual)
                            {
                                paginasMemoriaVirtual.add(paginaActual);
                            }
                        }
    
                        // Incrementa el contador de fallos de página
                        fallosPagina++;
                    }
                }
            }
        }
    }

    public static void updateTP()
    {

    }

    public static void algoritmoEnvejecimiento()
    {

    }

}