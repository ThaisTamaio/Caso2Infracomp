import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class interfaz {
    
    public static Integer[] vc = new Integer[6];

    public static String mode = "";


    public static void main(String[] args) throws IOException {   
        Scanner scan = new Scanner(System.in);

        System.out.println("Ingrese el nombre del archivo: " );   
        String archivoEntrada = scan.next();
        scan.close();

    

        // Contendio del archivo
        Integer nf= 0;
        Integer nc= 0;
        Integer te= 0;
        Integer tp= 0;
        Integer mp= 0;
        Integer nr= 0;
        vc[0] = tp;
        vc[1] = nf;
        vc[2] = nc;
        vc[3] = nr;
        vc[4] = mp;
        vc[5] = te;

        File file = new File("./lib"+ archivoEntrada +".txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        FileReader fr1 = new FileReader(file); //Este fileReader lee el ArchivoEntrada.txt
        int i;
        while ((i=fr1.read()) != -1)
        {
            int linea = (int) i;
            if (!tp.equals(0))
            {
                tp = linea;
                vc[0]=linea;
            }
            else if (!nf.equals(0))
            {
                nf = linea;
                vc[1]=linea;
            }
            else if (!nc.equals(0))
            {
                nc = linea;
                vc[2]=linea;
            }
            else if (!nr.equals(0)) //hasta acá hay configuración modo 1
            {   
                nr = linea;
                vc[3]=linea;
            }
            else if (!mp.equals(0))
            {   
                mp = linea;
                vc[4]=linea;
            }
            else if (!te.equals(0)) //si están todos completos es configruración modo 2
            {
                te = linea;
                vc[5]=linea;
            }
            Boolean sentinel = true;
            for (int j=0; i<6;j++)
            {
                if (vc[j].equals(0) && j <3)
                {
                    throw new Error("0 is not valid. ");
                }
                else if (vc[j].equals(0) && j > 3)
                {
                    mode = "mode1";
                }
                if (vc[j].equals(0))
                {
                    sentinel = false;
                }
            }
            if (sentinel)
            {
                mode = "mode2";
            }
            
        }
        
        
        
        
        
       
        

        //Iniciar algoritmo de enevejecimeinto 
      

        //Iniciar Referencias 

        
    }
}
