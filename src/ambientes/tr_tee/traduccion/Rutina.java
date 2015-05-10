package ambientes.tr_tee.traduccion;

import java.util.ArrayList;

public class Rutina {
    
    String nombre;
    ArrayList<String> CS;
    ArrayList<Variable> RA;

    public Rutina(String nombre) {
        this.nombre = nombre;
        this.CS = new ArrayList<>();
        this.RA = new ArrayList<>();        
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<String> getCS() {
        return CS;
    }

    public ArrayList<Variable> getRA() {
        return RA;
    }

    

    public void insertCS(String valor) {
        this.CS.add(nombre);
    }
    
    public void insertRA(String llave,String valor) {
        Variable var = new Variable(llave, valor);        
        this.RA.add(var);
    }
    
    public void imprimirRA(){        
            
        System.out.println("----RA " + nombre + " ----");
        int i=0;
        for (Variable var : RA) {
            if (i==0) {
                System.out.println(var.getClave());
            } else {
                System.out.println(var.getClave()+" = "+var.getValor());
            }
              
            
            i++;
        }        
        System.out.println("--------------");        
    
    }
    
    public void imprimirCS(){
        System.out.println("----CS " + nombre + " ----");        
        for (String var : CS) {
            System.out.println(var);            
        }        
        System.out.println("--------------");  
    }
    
    
    
}
