public class Actualizador extends Thread {

    private static boolean updateTP;

    public Actualizador(){
        updateTP = true;
    }

    public static void detenerActualizador(){
        updateTP = false;
    }
    
    public void run(){

        while (updateTP){

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {e.printStackTrace();}

            Modo2.updateTP();

        }
    }
}
