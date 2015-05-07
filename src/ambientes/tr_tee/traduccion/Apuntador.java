package ambientes.tr_tee.traduccion;

public class Apuntador {
    String N;
    String P;

    public String getN() {
        return N;
    }

    public void setN(String N) {
        this.N = N;
    }

    public String getP() {
        return P;
    }

    public void setP(String P) {
        this.P = P;
    }

    public Apuntador(String N, String P) {
        this.N = N;
        this.P = P;
    }
    
    public void incrementar(int num){        
        this.P = String.valueOf(Integer.parseInt(P)+num);
        //System.out.println(N+" "+P);
    }
    
}
