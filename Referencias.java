public class Referencias extends Thread {
    
    private EntradaTablaPagina tablaPagina;
    private Memoria memo;

    private int numFallosPagina = 0;


    public Referencias( EntradaTablaPagina tp){

        this.tablaPagina = tp;
    }


    public void verificarReferencia(Integer referencia){

        Boolean estaTP =  this.tablaPagina.isBitPresencia();

        if (estaTP){
            //Esta en RAM
        }
        else { //No esta en RAM
            this.numFallosPagina++;

            Integer refVieja = this.memo.actualizar(referencia);

        }

        
    }



    public void run(){
        for(int i = 0; i < 2; i++){
        verificarReferencia(referencia);
            try {
                sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Fin de la ejecucion crear archivo
    }
}
