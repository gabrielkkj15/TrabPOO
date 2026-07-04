public class Produto {
    private int cod;
    private String nome;
    private Marca marca;
    private double preco;
    private int quant;

    //construtores
    public Produto() {

    }

    public Produto(int cod, String nome, Marca marca, double preco, int quant) {
        this.cod = cod;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.quant = quant;
    }

    //getters
    public double getPreco() {
        return preco;
    }
    public String getNome() {
        return nome;
    }

    public Marca getMarca() {
        return marca;
    }

    public int getCod() {
        return cod;
    }

    public int getQuant() {
        return quant;
    }

    //setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }



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
        } else {
            return false;
        }
    }

}
