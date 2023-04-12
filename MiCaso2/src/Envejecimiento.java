import java.util.List;

public class Envejecimiento extends Thread {
    private Actualizador actualizador;
    private List<Referencia> referencias;

    public Envejecimiento(Actualizador actualizador, List<Referencia> referencias) {
        this.actualizador = actualizador;
        this.referencias = referencias;
    }

    @Override
    public void run() {
        for (Referencia referencia : referencias) {
            actualizador.actualizar(referencia);
            try {
                Thread.sleep(100); // dormir el thread por 100 milisegundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
