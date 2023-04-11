import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Simulador {
    
    private String archivo_referencias;
    private TablaPagina tabla_paginas;
    private int marcos_disponibles;
    private AlgoritmoEnvejecimiento algoritmo_envejecimiento;
    
    public Simulador(String archivo_referencias, TablaPagina tabla_paginas, int marcos_disponibles, AlgoritmoEnvejecimiento algoritmo_envejecimiento) {
        this.archivo_referencias = archivo_referencias;
        this.tabla_paginas = tabla_paginas;
        this.marcos_disponibles = marcos_disponibles;
        this.algoritmo_envejecimiento = algoritmo_envejecimiento;
    }
    
    public void ejecutarSimulador() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Scanner scanner = new Scanner(new File(archivo_referencias));
                    while (scanner.hasNextInt()) {
                        int referencia = scanner.nextInt();
                        int numPagina = referencia / 256;
                        algoritmo_envejecimiento.getMarco(numPagina);
                    }
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    algoritmo_envejecimiento.actualizarEdades();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public int contarFallosPagina() {
        return algoritmo_envejecimiento.getContadorFallos();
    }
    
}
