import java.util.Date;

public class Sistema {

    Estoque estoque = new Estoque();
    Carrinho carrinho = new Carrinho();

    Venda vendas[] = new Venda[10];
    int index = 0;

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
            if (this.vendas[i].data.equals(data)) {
                vendas[j++] = this.vendas[i];
            }
        }
        return vendas;
    }

    boolean produtoPossuiVenda(int codProduto) {
        for (int i = 0; i < index; i++) {

            Venda venda = vendas[i];

            for (int j = 0; j < venda.index; j++) {
                if (venda.itensVendidos[j].produto.cod == codProduto) {
                    return true;
                }
            }
        }

        return false;
    }

    int buscarVenda(int cod){
        for (int i = 0; i < index; i++) {
            if (vendas[i].codigo == cod){
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