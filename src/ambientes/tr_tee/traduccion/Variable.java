package ambientes.tr_tee.traduccion;


public class Variable {
    String clave;
    String valor;

    public Variable(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }    

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
    
}
