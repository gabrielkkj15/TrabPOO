import java.util.Date;
public class Sistema {

    private Estoque estoque = new Estoque();
    private Carrinho carrinho = new Carrinho();

    //Singleton dan vibecoder 10
    private static Sistema instancia;
    // Construtor privado
    private Sistema() {
        estoque = new Estoque();
        carrinho = new Carrinho();
    }

    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    private Venda vendas[] = new Venda[10];
    private int index = 0;

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public int getIndex() {
        return index;
    }

    public Venda getVendas() {
        return vendas;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    boolean finalizarVenda(String cliente, int dia, int mes, int ano) {

        Venda venda = new Venda();

        boolean deuCerto =
                venda.realizarVenda(index + 1, cliente, carrinho, dia, mes, ano);

        if (deuCerto) {

            for (int i = 0; i < carrinho.index; i++) {

                carrinho.itens[i].produto.decrementar(
                        carrinho.itens[i].quant
                );
            }

            if (vendas.length == index)aumentarCapacidade();

            vendas[index] = venda;
            index++;

            carrinho = new Carrinho();

            return true;
        }

        return false;
    }

    void resetarCarrinho(){
        this.carrinho = new Carrinho();
    }

    Venda[] vendasData(String data) {
        int j = 0;
        Venda[] vendas = new Venda[index];

        for (int i = 0; i < index; i++) {
            if (this.vendas[i].getData().equals(data)) {
                vendas[j++] = this.vendas[i];
            }
        }
        return vendas;
    }

    boolean produtoPossuiVenda(int codProduto) {
        for (int i = 0; i < index; i++) {

            Venda venda = vendas[i];

            for (int j = 0; j < venda.getIndex(); j++) {
                if (venda.getItensVendidos()[j].getProduto().getCod() == codProduto) {
                    return true;
                }
            }
        }

        return false;
    }

    int buscarVenda(int cod){
        for (int i = 0; i < index; i++) {
            if (vendas[i].getCodigo() == cod){
                return i;
            }
        }
        return -1;
    }

    void aumentarCapacidade() {
        Venda[] p = new Venda[vendas.length + 10];

        for (int i = 0; i < index; i++) {
            p[i] = vendas[i];
        }

         vendas = p;
    }

}