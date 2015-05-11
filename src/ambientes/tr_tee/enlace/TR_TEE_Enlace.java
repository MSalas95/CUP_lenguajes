package ambientes.tr_tee.enlace;

import ambientes.tr_tee.traduccion.TR_TEE_Traduccion;

public class TR_TEE_Enlace {
    
    TR_TEE_Traduccion Traduccion;
    
    private Rutina[]rutinas;
    private String[] GLOBAL;
    private int AI;
    
    private int inicio_global;

    public TR_TEE_Enlace(TR_TEE_Traduccion Traduccion) {
        this.Traduccion = Traduccion;
        AI = 300;
        llenarDatos();
        //imprimirGlobal();
        //rutinas[0].imprimirCS();
        //imprimirAI();
        run(rutinas[0].getNombre_rutina());
        //rutinas[0].imprimirCS();
        //rutinas[0].imprimirDS();
    }
    
    private void llenarDatos(){
        
        int i = 300;
        
        rutinas = new Rutina[Traduccion.rutinas.length];
        GLOBAL = new String[Traduccion.global.size()];
                
        //guarda los CS en ejecucion
        for (int j = 0; j < rutinas.length; j++) {

            rutinas[j] = new Rutina(Traduccion.rutinas[j].getCS().size(),
                    Traduccion.rutinas[j].getRA().size());
            rutinas[j].setInicio_cs(i);
            rutinas[j].setNombre_rutina(Traduccion.rutinas[j].getNombre());
            for (int k = 0; k < rutinas[j].CS.length; k++) {
                rutinas[j].CS[k] = Traduccion.rutinas[j].getCS().get(k);
                i++;
            }           

        }
        
        i=400;
        //guarda los RA en ejecucion
        for (int j = 0; j < rutinas.length; j++) {
            rutinas[j].setInicio_ds(i);
            for (int k = 0; k < rutinas[j].DS.length; k++) {
                rutinas[j].DS[k] = Traduccion.rutinas[j].getRA().get(k).getValor();
                i++;
            }
        }
        
        i=500;
        //Guardar las variables globales en el arreglo de ejecucion
        inicio_global = i;
        for (int j = 0; j < Traduccion.global.size(); j++) {
            GLOBAL[j] = Traduccion.global.get(j).getValor();
       }  
        
    }
    
    private void run(String nombre_rutina){
        
        
        
        for(int i=0;i<rutinas[buscarRutina(nombre_rutina)].CS.length;i++){
            
            String linea = rutinas[buscarRutina(nombre_rutina)].CS[i];
            String[] arr_lineas;
            arr_lineas = linea.split(" ");
            
            //si es una asignacion
            if (linea.contains("<-")){
                realizarOperacion(linea);
                linea = ponerValoresReales(arr_lineas);
                rutinas[buscarRutina(nombre_rutina)].CS[i] = linea;
                
            }
            
            // si es una llamada a una subrutina
            if ((arr_lineas[0].equalsIgnoreCase("AI"))&&(!linea.contains("("))) {               
                run(arr_lineas[2].split(",")[0]);              
                rutinas[buscarRutina(nombre_rutina)].CS[i] = linea.replace(arr_lineas[2], valorReal(arr_lineas[2], "l"));
            }
            
        }        
    }
    
    private void realizarAsignacion(String linea){
        
        String[] operacion;
        operacion = linea.split("<-");
        
        
    }
    
    private double realizarOperacion(String operaciones){
        
        String[] valores;
        valores = operaciones.split(" ");
        
        for (String item:valores){
            if (item.contains("(")){
                String valor_buscar =  item.substring(item.indexOf("(")+1, item.indexOf(")"));
                System.out.println(valor_buscar);
            }
        }
        
        return 0;
    }
    
    private String ponerValoresReales(String[] lineas){
        String retorno="";
        for (String item:lineas){
            if (item.contains("(")){
                String valor_buscar =  item.substring(item.indexOf("(")+1, item.indexOf(")"));
                
                if (item.contains("GLOBAL")){                    
                    item = item.replace(valor_buscar, valorReal(valor_buscar,"g"));
                } else {                    
                    item = item.replace(valor_buscar, valorReal(valor_buscar,"l"));
                }
                
            } else {
                
            }           
            retorno+=item+" ";
        }
        return retorno;
    }
    
    private String valorReal(String item,String tipo){
        
        String[] arr_items = item.split(",");
        int posicion;
        
        if(!tipo.equals("g")){
            posicion = rutinas[buscarRutina(arr_items[0])].getInicio_ds() + Integer.parseInt(arr_items[1]);
        }else{
            posicion = inicio_global + Integer.parseInt(arr_items[1]);
        }
        
        return String.valueOf(posicion);
    }
    
    private int buscarRutina(String nombre_rutina){
        nombre_rutina = nombre_rutina.toUpperCase();
        for (int i = 0; i < rutinas.length; i++) {
            
            if (rutinas[i].getNombre_rutina().equals(nombre_rutina)) {
                return i;
            }
        }
        return -1;
    }
    
    private int resolverLinea(String linea){
        return 0;
    }
    
        
    private void imprimirGlobal(){
        
        System.out.println("----GLOBAL----"); 
        int i = 0;
        for (String var : GLOBAL) {
            int linea = inicio_global + i;
            System.out.println(linea + "  "+var);      
            i++;
        }        
        System.out.println("--------------");  
        
    }    
    
    private void imprimirAI(){
        System.out.println("---AI---");
        System.out.println(AI);
        System.out.println("--------");
    }
    
    
    
    
}
