/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ambientes.tr_tee.enlace;


/**
 *
 * @author manuel
 */
public class Rutina {    
    
    private String nombre_rutina;
    
    public String[] CS;
    public String[] DS;
    
    private int inicio_cs;
    private int inicio_ds;

    public Rutina(int cs_size, int ds_size) {
        CS = new String[cs_size];
        DS = new String[ds_size];
    }

    

    public int getInicio_cs() {
        return inicio_cs;
    }

    public int getInicio_ds() {
        return inicio_ds;
    }

    public void setInicio_cs(int inicio_cs) {
        this.inicio_cs = inicio_cs;
    }

    public void setInicio_ds(int inicio_ds) {
        this.inicio_ds = inicio_ds;
    }

    public String getNombre_rutina() {
        return nombre_rutina;
    }

    public void setNombre_rutina(String nombre_rutina) {
        this.nombre_rutina = nombre_rutina;
    }
    
    
    
    public void imprimirCS(){
        System.out.println("----CS "+nombre_rutina+" ----");
        int i=0;
        for (String var : CS) {
            int linea = inicio_cs +i;
            System.out.println(linea+"   "+var);  
            i++;
        }        
        System.out.println("--------------");  
    }
    
    public void imprimirDS(){
        System.out.println("----DS "+nombre_rutina+" ----"); 
        int i=0;
        for (String var : DS) {
            int linea = inicio_ds +i;
            System.out.println(linea +"  "+var);
            i++;
        }        
        System.out.println("--------------");  
    }
    
    
    
    
    
}
