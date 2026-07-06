import java.util.ArrayList;
import java.util.Date;
public class Sistema {

    private Estoque estoque = new Estoque();
    private Carrinho carrinho = new Carrinho();
    private ArrayList<Venda> vendas = new ArrayList<>();

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

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public Venda[] getVendas() {
        return vendas.toArray(Venda[]::new);
    }

    public Estoque getEstoque() {
        return estoque;
    }

    boolean finalizarVenda(String cliente, int dia, int mes, int ano) {

        Venda venda = new Venda();

        boolean deuCerto = venda.realizarVenda(vendas.size(), cliente, carrinho, dia, mes, ano);

        if (deuCerto) {
            vendas.add(venda);
            for (int i = 0; i < carrinho.getCarrinho().size(); i++) {
                carrinho.getItens()[i].getProduto().decrementar(carrinho.getItens()[i].getQuant());
            }

            carrinho = new Carrinho();
            return true;
        }

        return false;
    }

    void resetarCarrinho(){
        this.carrinho = new Carrinho();
    }

    Venda[] vendasData(String data) {
        if(vendas.isEmpty())return null;
        int j = 0;
        Venda[] vendas = getVendas();

        for (int i = 0; i < this.vendas.size(); i++) {
            if (vendas[i].getData().equals(data)) {
                vendas[j++] = this.vendas.get(i);
            }
        }
        return vendas;
    }

    boolean produtoPossuiVenda(int codProduto) {
        for (int i = 0; i < vendas.size(); i++) {

            Venda venda = vendas.get(i);

            for (int j = 0; j < venda.getItensVendidos().length; j++) {
                if (venda.getItensVendidos()[j].getProduto().getCod() == codProduto) {
                    return true;
                }
            }
        }

        return false;
    }

    int buscarVenda(int cod){
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getCodigo() == cod){
                return i;
            }
        }
        return -1;
    }

}