public class Estoque {
    private ControladorMarca marcas = new ControladorMarca();
    private Produto[] produtos = new Produto[10];
    private int index = 0;

    //inserir
    boolean inserir(Produto produto, int codMarca) {

        //validacao nome
        if (produto.getNome().replace(" ", "").isEmpty()) {
            return false;
        }

        //validacao nome repetido
        for (int i = 0; i < index; i++) {
            if (produtos[i].getNome().trim().equalsIgnoreCase(produto.getNome().trim())) {
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

        produto.marca = marcas.buscarMarca(codMarca);

        if (index == produtos.length) {
            aumentarCapacidade();
        }

        produtos[index] = produto;
        produtos[index].cod = index+1;
        index++;

        return true;
    }

    //listar


    Produto[] ordemAlfabetica() {
        Produto[] copia = new Produto[index];

        for (int i = 0; i < index; i++) {
            copia[i] = this.produtos[i];
        }

        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - i - 1; j++) {

                if (copia[j].nome.compareToIgnoreCase(copia[j + 1].nome) > 0) {
                    Produto temp = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temp;
                }
            }
        }
        return copia;
    }

    Produto[] porMarca(Marca marca) {
        Produto[] copia = new Produto[index];
        int x = 0;
        for (int i = 0; i < index; i++) {
            if (produtos[i].getMarca() == marca) {
                copia[x] = produtos[i];
                x++;
            }
        }
        return copia;
    }


    //remover
    boolean remover(int cod,  Sistema sistema) {
        int x = indiceCod(cod);
        if (sistema.produtoPossuiVenda(cod)){return false;}
        if (x != -1) {
            for (int i = x; i < index - 1; i++) {
                produtos[i] = produtos[i + 1];
            }

            produtos[index - 1] = null;
            index--;

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
        if (produto.nome.replace(" ", "").isEmpty()) {
            System.out.println("Erro - Campo vazio");
            return;
        }

        // validacao do preço
        if (produto.preco <= 0) {
            System.out.println("Erro - Preço inválido");
            return;
        }

        // validacao da quantidade
        if (produto.quant <= 0) {
            System.out.println("Erro - Quantidade inválida");
            return;
        }

        // busca da marca
        produto.getMarca() = marcas.buscarMarca(codMarca);

        // validacao marca
        if (produto.getMarca() == null) {
            System.out.println("Marca não encontrada.");
            return;
        }

        produto.cod = produtos[x].cod;
        produtos[x] = produto;

        System.out.println("Produto alterado com sucesso.");
    }

    //aumertar vetor
    void aumentarCapacidade() {
        Produto[] p = new Produto[produtos.length + 10];

        for (int i = 0; i < index; i++) {
            p[i] = produtos[i];
        }

        produtos = p;
    }

    int indiceCod(int cod) {
        for (int i = 0; i < index; i++) {
            if (produtos[i].getCod() == cod) {
                return i;
            }
        }

        return -1;
    }

    boolean existeProdutoDaMarca(int codMarca) {
        for (int i = 0; i < index; i++) {
            if (produtos[i].getMarca().getCod() == codMarca) {
                return true;
            }
        }
        return false;
    }
}