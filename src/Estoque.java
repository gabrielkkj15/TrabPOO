import java.util.ArrayList;
import java.util.List;
public class Estoque {
    protected ControladorMarca marcas = new ControladorMarca();
    private ArrayList<Produto> produtos = new ArrayList<>();

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    //inserir
    boolean inserir(Produto produto, int codMarca) {

        //validacao nome
        if (produto.getNome().replace(" ", "").isEmpty()) {
            return false;
        }

        //validacao nome repetido
        if(!produtos.isEmpty())for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().trim().equalsIgnoreCase(produto.getNome().trim())) {
                return false;
            }
        }

        //validacao preco
        if (produto.getPreco() <= 0){
            return false;
        }


        //validacao qtd
        if (produto.getQuant() <= 0) {
            return false;
        }

        //busca da marca
        if(marcas.buscarMarca(codMarca) == null)return false;

        produto.setMarca(marcas.buscarMarca(codMarca));

        produtos.add(produto);
        return true;
    }

    //listar

    Produto[] ordemAlfabetica() {
        Produto[] copia = produtos.toArray(Produto[]::new);

        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - i - 1; j++) {

                if (copia[j].getNome().compareToIgnoreCase(copia[j + 1].getNome()) > 0) {
                    Produto temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }
        return copia;
    }

    Produto[] porMarca(Marca marca) {
        ArrayList<Produto> a = new ArrayList<>();
        int x = 0;
        for (Produto produto : produtos) {
            if (produto.getMarca() == marca) {
                a.add(produto);
            }
        }
        if (!produtos.isEmpty()){
            return a.toArray(Produto[]::new);
        }
        return null;
    }


    //remover
    boolean remover(int cod,  Sistema sistema) {
        int x = indiceCod(cod);
        if (sistema.produtoPossuiVenda(cod)){return false;}
        if (x != -1) {
            produtos.remove(x);
            System.out.println("Produto removido com sucesso.");
            return true;
        } else {
            System.out.println("Código inválido.");
            return false;
        }
    }

    //alterar
    void alterar(int cod, Produto produto, int codMarca) {

        int x = indiceCod(cod);

        if (x == -1) {
            System.out.println("Código inválido.");
            return;
        }

        // validacao do nome
        if (produto.getNome().replace(" ", "").isEmpty()) {
            System.out.println("Erro - Campo vazio");
            return;
        }

        // validacao do preço
        if (produto.getPreco() <= 0) {
            System.out.println("Erro - Preço inválido");
            return;
        }

        // validacao da quantidade
        if (produto.getQuant() <= 0) {
            System.out.println("Erro - Quantidade inválida");
            return;
        }

        // busca da marca
        produto.setMarca(marcas.buscarMarca(codMarca));

        // validacao marca
        if (produto.getMarca() == null) {
            System.out.println("Marca não encontrada.");
            return;
        }

        Produto produtoExistente = produtos.get(x);

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setQuant(produto.getQuant());
        produtoExistente.setMarca(marcas.buscarMarca(codMarca));

        System.out.println("Produto alterado com sucesso.");
    }


    int indiceCod(int cod) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCod() == cod) {
                return i;
            }
        }

        return -1;
    }

    boolean existeProdutoDaMarca(int codMarca) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getMarca().getCod() == codMarca) {
                return true;
            }
        }
        return false;
    }

}