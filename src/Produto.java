public class Produto {
    int cod;
    String nome;
    Marca marca;
    double preco;
    int quant;

    boolean decrementar(int quant) {
        if (this.quant >= quant && quant > 0) {
            this.quant = this.quant - quant;
            return true;
        } else if (quant <= 0) {
            return false;
        } else {
            return false;
        }
    }

    boolean incrementar(int quant) {
        if (quant > 0) {
            this.quant = this.quant + quant;
            return true;
        } else{
            return false;
        }
    }

}
