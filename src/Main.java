import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

//Daniel Victor e Gabriel Barbosa
//termina esse bagulho namoral dan10
public class Main {
    static Scanner leia = new Scanner(System.in);

    public static void main(String[] args) {
        Sistema sistema = Sistema.getInstancia();

        init(sistema);

        int op;

        do {

            System.out.println("\n MENU INICIAL ");
            System.out.println("1 - Administrador");
            System.out.println("2 - Atendente");
            System.out.println("0 - Sair");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {

                case 1:
                    menuAdm(sistema);
                    break;

                case 2:
                    menuAtend(sistema);
                    break;

                case 0:
                    System.out.println("Encerrando programa!");
                    break;

                default:
                    System.out.println("Digite uma opção válida!");
                    break;
            }

        } while (op != 0);
    }

    public static void init(Sistema sistema) {

        Marca m1 = new Marca(1, "Nike", "Nike Inc.", "11.111.111/0001-11");

        Marca m2 = new Marca(2, "Adidas", "Adidas AG", "22.222.222/0001-22");

        Marca m3 = new Marca(3, "Puma",  "Puma SE", "33.333.333/0001-33");

        sistema.getEstoque().marcas.inserir(m1);
        sistema.getEstoque().marcas.inserir(m2);
        sistema.getEstoque().marcas.inserir(m3);

        Produto p1 = new Produto(1,"Chuteira Mercurial", m1 ,799.90,10);

        Produto p2 = new Produto(2, "Tênis Air Max", m2, 599.99, 8);

        Produto p3 = new Produto(3, "Camisa Real Madrid", m2, 349.90, 15);

        Produto p4 = new Produto(4, "Chuteira Predator", m2, 899.90, 6);

        Produto p5 = new Produto(5, "Camisa Manchester City", m3, 299.90, 12);

        Item i1 = new Item(p1, 1);

        Item i2 = new Item(p3, 2);

        sistema.getCarrinho().addItem(i1);
        sistema.getCarrinho().addItem(i2);
        sistema.finalizarVenda("Gabriel Barbosa", 10, 5, 2026);

        Item i3 = new Item(p2, 1);

        sistema.getCarrinho().addItem(i3);
        sistema.finalizarVenda("Daniel Victor", 15, 5, 2026);

        Item i4 = new Item(p4, 1);

        Item i5 = new Item(p5, 3);

        sistema.getCarrinho().addItem(i4);
        sistema.getCarrinho().addItem(i5);
        sistema.finalizarVenda("Maria Souza", 20, 5, 2026);

        sistema.getEstoque().inserir(p1, 1);
        sistema.getEstoque().inserir(p2, 1);
        sistema.getEstoque().inserir(p3, 2);
        sistema.getEstoque().inserir(p4, 2);
        sistema.getEstoque().inserir(p5, 3);
    }

    static void menuAtend(Sistema sistema) {

        int op;
        System.out.println("Digite o tamanho das colunas");
        int tam = leia.nextInt();
        if (tam < 10){
            System.out.println("Tamanho invalido!");
            tam = 10;
        }

        do {

            System.out.println("\n ATENDENTE ");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Ver carrinho");
            System.out.println("3 - Finalizar venda");
            System.out.println("0 - Cancelar");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {

                case 1:

                    tabela(
                            sistema.getEstoque().getProdutos(),
                            sistema.getEstoque().index,
                            tam
                    );

                    System.out.println("Código do produto:");
                    int cod = leia.nextInt();

                    Produto produto = sistema.getEstoque().getProdutos().get(sistema.getEstoque().indiceCod(cod));

                    if (produto != null) {

                        System.out.println("Quantidade:");
                        int quant = leia.nextInt();
                        leia.nextLine();

                        Item item = new Item();
                        item.setProduto(produto);
                        item.setQuant(quant);
                        item.setSubtotal(produto.getPreco() * quant);

                        if (sistema.getCarrinho().addItem(item)) {
                            System.out.println("Produto adicionado!");
                        } else {
                            System.out.println("Erro ao adicionar.");
                        }
                    } else {
                        System.out.println("Produto não encontrado.");
                    }

                    break;

                case 2:

                    tabela(
                            sistema.getCarrinho().getItens(),
                            sistema.getCarrinho().index,
                            tam
                    );

                    break;

                case 3:

                    System.out.println("Nome do cliente:");
                    String cliente = leia.nextLine();
                    System.out.println("== Data da compra ==");
                    System.out.println("Dia:");
                    int dia = leia.nextInt();
                    System.out.println("Mês:");
                    int mes = leia.nextInt();
                    System.out.println("Ano:");
                    int ano = leia.nextInt();
                    if (sistema.finalizarVenda(cliente, dia, mes, ano)) {
                        System.out.println("Venda finalizada!");
                    } else {
                        System.out.println("Erro ao finalizar venda.");
                    }

                    break;
            }


        } while (op != 0);
        sistema.resetarCarrinho();
    }

    static void menuAdm(Sistema sistema) {

        int op;

        do {

            System.out.println("\n ADMINISTRADOR ");
            System.out.println("1 - CRUD Marcas");
            System.out.println("2 - CRUD Produtos");
            System.out.println("3 - Listagens");
            System.out.println("0 - Voltar");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {

                case 1:
                    menuMarca(sistema);
                    break;

                case 2:
                    menuProduto(sistema);
                    break;

                case 3:
                    menuListagens(sistema);
                    break;
            }

        } while (op != 0);
    }

    static void menuMarca(Sistema sistema) {

        int op;
        System.out.println("Digite o tamanho das colunas");
        int tam = leia.nextInt();
        if (tam < 10){
            System.out.println("Tamanho invalido!");
            tam = 10;
        }

        do {

            System.out.println("\n MENU MARCA ");
            System.out.println("1 - Cadastrar marca");
            System.out.println("2 - Listar marcas");
            System.out.println("3 - Alterar marcas");
            System.out.println("4 - Excluir marcas");
            System.out.println("0 - Voltar");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {

                case 1:

                    Marca marca = new Marca();

                    System.out.println("Nome Fantasia:");
                    marca.setNomeFantasia(leia.nextLine()) ;

                    System.out.println("CNPJ:");
                    marca.setCnpj(leia.nextLine());

                    System.out.println("Fabricante:");
                    marca.setFabricante(leia.nextLine());

                    sistema.getEstoque().marcas.inserir(marca);

                    break;

                case 2:

                    tabela(
                            sistema.getEstoque().marcas.getMarcas(),
                            sistema.getEstoque().marcas.index,
                            tam
                    );

                    break;

                case 3:

                    tabela(
                            sistema.getEstoque().marcas.getMarcas(),
                            sistema.getEstoque().marcas.index,
                            tam
                    );

                    System.out.println("Código:");
                    int cod = leia.nextInt();
                    leia.nextLine();

                    marca = new Marca();

                    System.out.println("Nome Fantasia:");
                    marca.setNomeFantasia(leia.nextLine()) ;

                    System.out.println("CNPJ:");
                    marca.setCnpj(leia.nextLine());

                    System.out.println("Fabricante:");
                    marca.setFabricante(leia.nextLine());

                    sistema.getEstoque().marcas.alterar(cod, marca, sistema.getEstoque());

                    break;

                case 4:

                    tabela(
                            sistema.getEstoque().marcas.getMarcas(),
                            sistema.getEstoque().marcas.index,
                            tam
                    );

                    System.out.println("Código:");
                    cod = leia.nextInt();

                    if (sistema.getEstoque().marcas.removerMarca(cod, sistema.getEstoque())){
                        System.out.println("Marca removida com sucesso!");
                    }else {
                        System.out.println("Erro! Marca não removida!");
                    };

                    break;
            }

        } while (op != 0);
    }

    static void menuProduto(Sistema sistema) {

        int op;
        System.out.println("Digite o tamanho das colunas");
        int tam = leia.nextInt();
        if (tam < 10){
            System.out.println("Tamanho invalido!");
            tam = 10;
        }

        do {

            System.out.println("\n MENU PRODUTO ");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Alterar produto");
            System.out.println("4 - Excluir produto");
            System.out.println("0 - Voltar");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {

                case 1:

                    Produto produto = new Produto();

                    System.out.println("Código:");
                    produto.setCod(leia.nextInt());
                    leia.nextLine();

                    System.out.println("Nome:");
                    produto.setNome(leia.nextLine());

                    System.out.println("Preço:");
                    produto.setPreco(leia.nextDouble());

                    System.out.println("Quantidade:");
                    produto.setQuant(leia.nextInt());

                    tabela(
                            sistema.getEstoque().marcas.getMarcas(),
                            sistema.getEstoque().marcas.index,
                            tam
                    );

                    System.out.println("Código da marca:");
                    int codMarca = leia.nextInt();

                    sistema.getEstoque().inserir(produto, codMarca);

                    break;

                case 2:
                    tabela(
                            sistema.getEstoque().getProdutos(),
                            sistema.getEstoque().index,
                            tam
                    );
                    break;

                case 3:

                    tabela(
                            sistema.getEstoque().getProdutos(),
                            sistema.getEstoque().index,
                            tam
                    );

                    System.out.println("Código:");
                    int cod = leia.nextInt();
                    leia.nextLine();

                    produto = new Produto();

                    System.out.println("Nome:");
                    produto.setNome(leia.nextLine());

                    System.out.println("Preço:");
                    produto.setPreco(leia.nextDouble());

                    System.out.println("Quantidade:");
                    produto.setQuant(leia.nextInt());

                    tabela(
                            sistema.getEstoque().marcas.getMarcas(),
                            sistema.getEstoque().marcas.index,
                            tam
                    );

                    System.out.println("Código da marca:");
                    codMarca = leia.nextInt();

                    sistema.getEstoque().alterar(
                            cod,
                            produto,
                            codMarca
                    );

                    break;

                case 4:

                    tabela(
                            sistema.getEstoque().getProdutos(),
                            sistema.getEstoque().index,
                            tam
                    );

                    System.out.println("Código:");
                    cod = leia.nextInt();

                    sistema.estoque.remover(cod, sistema);

                    break;
            }

        } while (op != 0);
    }

    static void menuListagens(Sistema sistema) {

        int op;
        System.out.println("Digite o tamanho das colunas");
        int tam = leia.nextInt();
        if (tam < 10){
            System.out.println("Tamanho invalido!");
            tam = 10;
        }
        do {
            System.out.println("\n LISTAGENS ");
            System.out.println("1 - Todos os produtos");
            System.out.println("2 - Produtos em ordem alfabética");
            System.out.println("3 - Todas as marcas");
            System.out.println("4 - Produtos de uma marca");
            System.out.println("5 - Todas as vendas");
            System.out.println("6 - Vendas por dia");
            System.out.println("7 - Buscar venda por código");
            System.out.println("0 - Voltar");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {

                case 1:


                    tabela(
                            sistema.getEstoque().getProdutos(),
                            sistema.getEstoque().index,
                            tam
                    );

                    break;

                case 2:

                    tabela(
                            sistema.getEstoque().ordemAlfabetica(),
                            sistema.getEstoque().index,
                            tam
                    );

                    break;

                case 3:


                    tabela(
                            sistema.getEstoque().marcas.getMarcas(),
                            sistema.getEstoque().marcas.index,
                            tam
                    );

                    break;

                case 4:

                    tabela(sistema.getEstoque().marcas.getMarcas(), tam);

                    System.out.println("Código da marca:");
                    int codMarca = leia.nextInt();
                    leia.nextLine();

                    tabela(
                            sistema.getEstoque().porMarca(sistema.getEstoque().marcas.buscarMarca(codMarca)),
                            sistema.getEstoque().index,
                            tam
                    );

                    break;

                case 5:



                    tabela(
                            sistema.getVendas(),
                            sistema.index,
                            tam
                    );

                    break;

                case 6:

                    System.out.println("Digite a data (dd/mm/yyyy)");
                    String data = leia.nextLine();
                    tabela(sistema.vendasData(data), sistema.index, tam);
                    break;

                case 7:

                    System.out.println("Código da venda:");
                    int codVenda = leia.nextInt();
                    leia.nextLine();
                    if (sistema.buscarVenda(codVenda) != -1) {
                        tabela(sistema.getVendas()[sistema.buscarVenda(codVenda)].getItensVendidos(), sistema.vendas[sistema.buscarVenda(codVenda)].index, tam);
                    }else{
                        System.out.println("Codigo invalido!");
                    }
                    break;
            }

        } while (op != 0);
    }


    public static String formatarTexto(String texto, int tam) {

        if (texto == null) {
            return "";
        }

        if (texto.length() > tam) {
            return texto.substring(0, tam - 3) + "...";
        }

        return texto;
    }

    public static void tabela(ArrayList<Marca> marcas, int tam) {

        if (marcas.isEmpty()) {
            System.out.println("\nNenhuma marca cadastrada.");
            return;
        }

        String formato =
                "%-" + tam + "s %-" + tam + "s %-" + tam + "s %-" + tam + "s%n";

        System.out.printf(formato,
                "COD",
                "NOME",
                "CNPJ",
                "FABRICANTE"
        );

        for (int i = 0; i < marcas.size(); i++) {

            Marca marca = marcas.get(i);

            if (marca == null) {
                continue;
            }

            System.out.printf(formato,
                    formatarTexto(String.valueOf(marca.getCod()), tam),
                    formatarTexto(marca.getNomeFantasia(), tam),
                    formatarTexto(marca.getCnpj(), tam),
                    formatarTexto(marca.getFabricante(), tam)
            );
        }
    }

    public static void tabela(ArrayList<Produto> produtos, int tam) {

        if (produtos.isEmpty()) {
            System.out.println("\nNenhum produto cadastrado.");
            return;
        }

        String formato =
                "%-" + tam + "s %-" + tam + "s %-" + tam + "s %-" + tam + "s %-" + tam + "s%n";

        System.out.printf(formato,
                "COD",
                "NOME",
                "MARCA",
                "PREÇO",
                "QUANT"
        );

        for (int i = 0; i < produtos.size(); i++) {

            Produto produto = produtos.get(i);

            if (produto == null) {
                continue;
            }

            System.out.printf(formato,
                    formatarTexto(String.valueOf(produto.getCod()), tam),
                    formatarTexto(produto.getNome(), tam),
                    formatarTexto(produto.getMarca().getNomeFantasia(), tam),
                    formatarTexto(String.format("%.2f", produto.getPreco()), tam),
                    formatarTexto(String.valueOf(produto.getQuant()), tam)
            );
        }
    }

    public static void tabela(ArrayList<Venda> vendas, int tam) {

        if (vendas.isEmpty()) {
            System.out.println("\nNenhuma venda cadastrada.");
            return;
        }

        String formato =
                "%-" + tam + "s %-" + tam + "s %-" + tam + "s%n";

        System.out.printf(formato,
                "COD",
                "CLIENTE",
                "TOTAL"
        );

        for (int i = 0; i < vendas.size(); i++) {

            Venda venda = vendas.get(i);

            if (venda == null) {
                continue;
            }

            System.out.printf(formato,
                    formatarTexto(String.valueOf(venda.getCodigo()), tam),
                    formatarTexto(vendas.get(i).getNomeCliente(), tam),
                    formatarTexto(String.format("%.2f", venda.getTotal()), tam)
            );
        }
    }

    public static void tabela(ArrayList<Item> itens, int tam) {

        if (itens.isEmpty()) {
            System.out.println("\nCarrinho vazio.");
            return;
        }

        String formato =
                "%-" + tam + "s %-" + tam + "s %-" + tam + "s%n";

        System.out.printf(formato,
                "PRODUTO",
                "QUANT",
                "SUBTOTAL"
        );

        for (int i = 0; i < itens.size(); i++) {

            Item item = itens.get(i);

            if (item == null) {
                continue;
            }

            System.out.printf(formato,
                    formatarTexto(item.getProduto().getNome(), tam),
                    formatarTexto(String.valueOf(item.getQuant()), tam),
                    formatarTexto(String.format("%.2f", itens.get(i).getSubtotal()), tam)
            );
        }
    }
}