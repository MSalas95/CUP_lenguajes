package cup;

import ambientes.tr_tee.enlace.TR_TEE_Enlace;
import ambientes.tr_tee.traduccion.TR_TEE_Traduccion;


public class CUP {
   
    public static void main(String[] args) {
      // TODO c ode application logic here
      TR_TEE_Traduccion trad = new TR_TEE_Traduccion();
      //trad.imprimirTodo();
      TR_TEE_Enlace enl = new TR_TEE_Enlace(trad);
        
    }
    
}
