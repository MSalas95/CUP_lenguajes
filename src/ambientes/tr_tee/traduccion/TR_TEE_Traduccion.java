package ambientes.tr_tee.traduccion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TR_TEE_Traduccion {

    Apuntador AI;
    public ArrayList<Variable> global;
    public Rutina[] rutinas;

    public TR_TEE_Traduccion() {

        try {

            init();
            global = new ArrayList<>();
            AI = new Apuntador(rutinas[0].getNombre(),"0");
            rutinas[0].insertRA("{PR}","SOP");            
            run(rutinas[0].getNombre());
            ponerValoresPredeterminados();
           
            
        } catch (FileNotFoundException ex) {
        } catch (IOException ex){
        }

    }

    private void init() throws FileNotFoundException, IOException {

        List<String> nombres_rutinas;
        nombres_rutinas = new ArrayList<>();

        File A = new File("programa.p");
        BufferedReader br;
        br = new BufferedReader(new FileReader(A));
        StringTokenizer tokens;

        for (String linea; (linea = br.readLine()) != null;) {
            if (!linea.equals("")){
                linea = acomodarLinea(linea); 
                
                tokens = new StringTokenizer(linea, " ");

                while (tokens.hasMoreTokens()) {
                    String token = tokens.nextToken();
                    if ((token.equalsIgnoreCase("programa")) || (token.equalsIgnoreCase("subprograma"))) {
                        nombres_rutinas.add(tokens.nextToken());
                    }                
                }
            }
        }
        br.close();
        crearRutinas(nombres_rutinas);
    }

    private void crearRutinas(List<String> nombres_rutinas) {
        rutinas = new Rutina[nombres_rutinas.size()];
        for (int i = 0; i < nombres_rutinas.size(); i++) {
            rutinas[i] = new Rutina(nombres_rutinas.get(i));
        }

    }
    
    private void run(String nombre_rutina) throws FileNotFoundException, IOException {
        File A = new File("programa.p");
        BufferedReader br;
        br = new BufferedReader(new FileReader(A));
                     
        boolean trigger = false;
        
        for (String linea; (linea = br.readLine()) != null;) {
            if (!linea.equals("")){
                linea = acomodarLinea(linea);
                
                String[] tokens_array;
                tokens_array = linea.split(" ");  
                
                
                
                if ((tokens_array[0].equalsIgnoreCase("programa")) || (tokens_array[0].equalsIgnoreCase("subprograma"))){
                    if(tokens_array[1].equals(nombre_rutina)){
                        trigger = true;         
                    }           
                }
                if (trigger==true){
                    
                    if(!linea.equalsIgnoreCase("fin")){
                        runCode(linea, tokens_array);
                    } else {
                        
                        String nombre_asignacion_cs;  
                        nombre_asignacion_cs = "AI <- RA("+rutinas[rutinaActual()].getNombre().toLowerCase()+","+"0)";
                        rutinas[rutinaActual()].CS.add(nombre_asignacion_cs);
                        
                        AI.setP(rutinas[rutinaActual()].RA.get(0).valor);
                        AI.setN(rutinas[rutinaActual()].RA.get(0).clave);
                        
                        //imprimirAI();
                        break;
                    }
                }
            }                
        }
        br.close();
    }
    
        
    
    private void runCode(String linea, String[] tokens_array) throws IOException{
                  

        /** Si son variables globales guardarla en la parte de GLOBAL **/

        if (tokens_array[0].equalsIgnoreCase("global")) {                    
            if (tokens_array.length>1) {                        
                for (int i = 1; i < tokens_array.length; i++) {

                    String[] tk = tokens_array[i].split("=");                            

                    Variable var;

                    if (tk.length==1) {
                        var = new Variable(tk[0], "");
                        global.add(var);
                    } else {
                        var = new Variable(tk[0], tk[1]);
                        global.add(var);
                    }                             
                }
            }                    
        }  
                
        /** Si son variables locales guardarlas en el RA**/

        if (tokens_array[0].equalsIgnoreCase("local")) {                    
            if (tokens_array.length>1) {                        
                for (int i = 1; i < tokens_array.length; i++) {

                    String[] tk = tokens_array[i].split("=");                            

                    Variable var;

                    if (tk.length==1) {
                        var = new Variable(tk[0], "");
                        rutinas[rutinaActual()].RA.add(var);
                    } else {
                        var = new Variable(tk[0], tk[1]);
                        rutinas[rutinaActual()].RA.add(var);
                    }                             
                }
            }                    
        }


        /** Si es una asignacion **/
        
        if (linea.contains("<-")) {            
            rutinas[rutinaActual()].CS.add(sustituirVariables(tokens_array));
            AI.incrementar(1);
            
        }
                

        /** Si es una llamada a una subrutina */

         if (tokens_array[0].equalsIgnoreCase("llamar")) {   
             cambiarPR(tokens_array[1]);
             String nombre_asignacion_cs;
             nombre_asignacion_cs = "RA("+tokens_array[1].toLowerCase()+","+"0) <- AI + 2";
             rutinas[rutinaActual()].CS.add(nombre_asignacion_cs);
             
             nombre_asignacion_cs = "AI <- "+ tokens_array[1]+","+"0";
             rutinas[rutinaActual()].CS.add(nombre_asignacion_cs);
             AI.setN(tokens_array[1]);
             AI.setP("0");
             run(tokens_array[1]);
         }
        
    }
    private void cambiarPR(String nombre_rutina){
        if (rutinas[buscarRutina(nombre_rutina)].RA.isEmpty()) {
            rutinas[buscarRutina(nombre_rutina)].RA.add((new Variable(AI.getN(),String.valueOf(Integer.parseInt(AI.getP())+2))));
            return;
        }
        rutinas[buscarRutina(nombre_rutina)].RA.get(0).setClave("{PR}");
        rutinas[buscarRutina(nombre_rutina)].RA.get(0).setValor("{PR}");
    }
    
    private int rutinaActual(){ 
        return buscarRutina(AI.getN());
    }
    public int buscarRutina(String nombre){
        for (int i = 0; i < rutinas.length; i++) {
            if (rutinas[i].nombre.equals(nombre)) {
                return i;
            }
        }
        return -1;
    }
    
    private String acomodarLinea(String linea){
        linea = linea.replaceAll(",", " ");
        linea = linea.trim().replaceAll(" +", " ");
        linea = linea.replaceAll("\t", "");
        linea = linea.replaceAll(" =", "=");
        linea = linea.replaceAll("= ", "=");
        linea = linea.replaceAll(" = ", "=");
        return linea;
    }
    
        
    private String sustituirVariables(String[] tokens){
        
        String result = "";
        
        for (int i = 0; i < tokens.length; i++) {
            tokens[i]=buscarVariable(tokens[i]);
            result += tokens[i]+ " ";            
        }
        
        return result;
    }
    
    private String buscarVariable(String nombre){
                
        for (int j = 0; j < rutinas[rutinaActual()].RA.size(); j++) {
            String elemento = rutinas[rutinaActual()].RA.get(j).clave;
            if (nombre.equals(elemento)) {
                return "RA("+AI.getN().toLowerCase()+","+j+")";
            }
        } 
        
        int i=0;
        for (Variable item : global) {
            
            String elemento = item.getClave();
            if (nombre.equals(elemento)) {
                return "GLOBAL(g"+","+i+")";
            }
            i++;
        }
        
        return nombre;
    }
    
    public void imprimirAI(){
        System.out.println("---AI---");
        System.out.println(AI.getN()+","+AI.getP());
        System.out.println("--------");
    }
    
    public void imprimirGlobal(){        
        System.out.println("----GLOBAL----");        
        for (Variable var : global) {
            System.out.println(var.getClave()+" = "+var.getValor());            
        }        
        System.out.println("--------------");        
    }
    
    public void imprimirRutinas(){
        for (Rutina rutina: rutinas) {
            System.out.println("");
            System.out.println("------ Rutina "+rutina.getNombre()+" -------");
            System.out.println("");
            rutina.imprimirRA();
            rutina.imprimirCS();
        }
    }
    
    
    public void imprimirTodo(){
        imprimirAI();
        imprimirGlobal();
        imprimirRutinas();        
    }
    
    private void ponerValoresPredeterminados(){
        
       /* if (rutinas.length>0) {
            AI.setN(rutinas[0].getNombre());
            AI.setP("0");
            
            for (Rutina rutina : rutinas) {
                if (rutina.RA.size() > 0) {
                    rutina.RA.get(0).setClave("{PR}");
                    rutina.RA.get(0).setValor("{PR}");
                }
            }            
        }*/
    }   
}
