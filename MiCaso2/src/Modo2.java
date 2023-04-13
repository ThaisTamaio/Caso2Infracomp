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
    private static Map<Integer, Integer> envejecimiento = new HashMap<Integer, Integer>();

    public Integer ejecutarModo2()
    {
        cargaInicialPaginas();
        Actualizador.detenerActualizador();
        AlgoritmoEnvejecimiento.detenerEnvejecimiento();
        return fallosPagina;
    }

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

    public static void updateTP() {
        int valorEnvejecimiento = Integer.MAX_VALUE; // Inicializa el valor de envejecimiento con el máximo valor posible
        int victima = -1; // Inicializa la página víctima con un valor imposible
    
        // Sincroniza el acceso a las estructuras de datos compartidas
        synchronized (paginasMemoriaReal) {
            synchronized (paginasEnUso) {
                synchronized (paginasMemoriaVirtual) {
                    synchronized (envejecimiento) {
                        while (paginasMemoriaVirtual.size() > 0) { // Mientras haya páginas en memoria virtual
                            for (Map.Entry<Integer, Integer> paginasEnvejecimiento : envejecimiento.entrySet()) { // Recorre el mapa de envejecimiento
                                Integer paginaEnvejecimiento = paginasEnvejecimiento.getKey(); // Obtiene el número de página
    
                                Integer bits = 10000000; // Inicializa una variable con un valor de bits
                                String bitsStr = Integer.toString(bits); // Convierte el valor de bits a una cadena de caracteres
    
                                int decimal = 0;
                                for (int i = 0; i < bitsStr.length(); i++) { // Convierte la cadena de caracteres a un valor decimal
                                    decimal <<= 1;
                                    if (bitsStr.charAt(i) == '1') {
                                        decimal |= 1;
                                    }
                                }
    
                                if (decimal < valorEnvejecimiento) { // Si el valor de envejecimiento es menor que el anterior mínimo encontrado
                                    valorEnvejecimiento = decimal; // Actualiza el valor mínimo
                                    victima = paginaEnvejecimiento; // Actualiza la página víctima
                                }
                            }
    
                            int paginaNueva = (int) paginasMemoriaVirtual.get(0); // Obtiene la primera página en memoria virtual
    
                            if (!paginasEnUso.contains(paginaNueva)) { // Si la página no está en uso
                                paginasEnUso.add(paginaNueva); // La marca como en uso
                            }
    
                            int posicionPaginaMenos = paginasMemoriaReal.indexOf(victima); // Obtiene la posición de la página víctima en la memoria real
    
                            paginasMemoriaVirtual.remove(0); // Elimina la página en memoria virtual
                            paginasMemoriaReal.remove(posicionPaginaMenos); // Elimina la página víctima de la memoria real
                            envejecimiento.remove(victima); // Elimina la entrada correspondiente a la página víctima del mapa de envejecimiento
                            paginasMemoriaReal.add(paginaNueva); // Agrega la nueva página a la memoria real
                        }
                    }
                }
            }
        }
    }
    

    public static void algoritmoEnvejecimiento()
    {
        synchronized(envejecimiento)
        {
            synchronized(paginasEnUso)
            {
                // Iterar sobre el mapa de envejecimiento
                for (Map.Entry<Integer, Integer> paginasEnvejecimiento : envejecimiento.entrySet()) {
                    Integer paginaEnvejecimiento = paginasEnvejecimiento.getKey();
                    Integer bits = paginasEnvejecimiento.getValue();
                    if (!paginasEnUso.contains(paginaEnvejecimiento)) {
                        Integer pagina = paginaEnvejecimiento;
                        // Realizar el corrimiento hacia la izquierda
                        bits <<= 1;
                        // Modificar el primer dígito (poner un 1 en el segundo bit más significativo)
                        bits = (bits & 0x7F) | 0x40;
                        // Actualizar el valor del contador de envejecimiento en el mapa
                        envejecimiento.put(pagina, bits);
                    }
                }
                // Iterar sobre el conjunto de páginas en uso
                for (Integer pagina : paginasEnUso) {
                    Integer bits = envejecimiento.get(pagina);
                    if (bits == null) {
                        // Si la página no está en el mapa de envejecimiento, se agrega con un contador de envejecimiento inicial de 10000000
                        envejecimiento.put(pagina, 10000000);
                    }
                    else {
                        // Si la página ya está en el mapa de envejecimiento, se actualiza su contador de envejecimiento
                        bits = bits / 10;  // Elimina el último dígito
                        bits = 0b10000000 | bits;  // Agrega el 1 al principio
                        envejecimiento.put(pagina, bits);
                    }
                }
            }
        }
        // Se borran las páginas en uso del conjunto de páginas en uso
        paginasEnUso.clear();
    }
}