public class Item {
    private Produto produto;
    private int quant;
    private double subtotal;

    public Item() {
    }

    public Item(Produto produto, int quant) {
        this.produto = produto;
        this.quant = quant;
        this.subtotal = produto.getPreco() * this.quant;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuant() {
        return quant;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
