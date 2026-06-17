import java.util.Date;
import java.util.Scanner;

//Daniel Victor e Gabriel Barbosa

public class Main {
    static Scanner leia = new Scanner(System.in);

    public static void main(String[] args) {
        Sistema sistema = new Sistema();

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

        Marca m1 = new Marca();
        m1.cod = 1;
        m1.nomeFantasia = "Nike";
        m1.fabricante = "Nike Inc.";
        m1.cnpj = "11.111.111/0001-11";

        Marca m2 = new Marca();
        m2.cod = 2;
        m2.nomeFantasia = "Adidas";
        m2.fabricante = "Adidas AG";
        m2.cnpj = "22.222.222/0001-22";

        Marca m3 = new Marca();
        m3.cod = 3;
        m3.nomeFantasia = "Puma";
        m3.fabricante = "Puma SE";
        m3.cnpj = "33.333.333/0001-33";

        sistema.estoque.marcas.inserir(m1);
        sistema.estoque.marcas.inserir(m2);
        sistema.estoque.marcas.inserir(m3);

        Produto p1 = new Produto();
        p1.cod = 1;
        p1.nome = "Chuteira Mercurial";
        p1.preco = 799.90;
        p1.quant = 10;

        Produto p2 = new Produto();
        p2.cod = 2;
        p2.nome = "Tênis Air Max";
        p2.preco = 599.90;
        p2.quant = 8;

        Produto p3 = new Produto();
        p3.cod = 3;
        p3.nome = "Camisa Real Madrid";
        p3.preco = 349.90;
        p3.quant = 15;

        Produto p4 = new Produto();
        p4.cod = 4;
        p4.nome = "Chuteira Predator";
        p4.preco = 899.90;
        p4.quant = 6;

        Produto p5 = new Produto();
        p5.cod = 5;
        p5.nome = "Camisa Manchester City";
        p5.preco = 299.90;
        p5.quant = 12;

        Item i1 = new Item();
        i1.produto = p1;
        i1.quant = 1;
        i1.subtotal = p1.preco * i1.quant;

        Item i2 = new Item();
        i2.produto = p3;
        i2.quant = 2;
        i2.subtotal = p3.preco * i2.quant;

        sistema.carrinho.addItem(i1);
        sistema.carrinho.addItem(i2);
        sistema.finalizarVenda("Gabriel Barbosa", 10, 5, 2026);

        Item i3 = new Item();
        i3.produto = p2;
        i3.quant = 1;
        i3.subtotal = p2.preco * i3.quant;

        sistema.carrinho.addItem(i3);
        sistema.finalizarVenda("Daniel Victor", 15, 5, 2026);

        Item i4 = new Item();
        i4.produto = p4;
        i4.quant = 1;
        i4.subtotal = p4.preco * i4.quant;

        Item i5 = new Item();
        i5.produto = p5;
        i5.quant = 3;
        i5.subtotal = p5.preco * i5.quant;

        sistema.carrinho.addItem(i4);
        sistema.carrinho.addItem(i5);
        sistema.finalizarVenda("Maria Souza", 20, 5, 2026);

        sistema.estoque.inserir(p1, 1);
        sistema.estoque.inserir(p2, 1);
        sistema.estoque.inserir(p3, 2);
        sistema.estoque.inserir(p4, 2);
        sistema.estoque.inserir(p5, 3);
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
                            sistema.estoque.produtos,
                            sistema.estoque.index,
                            tam
                    );

                    System.out.println("Código do produto:");
                    int cod = leia.nextInt();

                    Produto produto =
                            sistema.estoque.produtos[sistema.estoque.indiceCod(cod)];

                    if (produto != null) {

                        System.out.println("Quantidade:");
                        int quant = leia.nextInt();
                        leia.nextLine();

                        Item item = new Item();
                        item.produto = produto;
                        item.quant = quant;
                        item.subtotal = produto.preco * quant;

                        if (sistema.carrinho.addItem(item)) {
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
                            sistema.carrinho.itens,
                            sistema.carrinho.index,
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
                    marca.nomeFantasia = leia.nextLine();

                    System.out.println("CNPJ:");
                    marca.cnpj = leia.nextLine();

                    System.out.println("Fabricante:");
                    marca.fabricante = leia.nextLine();

                    sistema.estoque.marcas.inserir(marca);

                    break;

                case 2:

                    tabela(
                            sistema.estoque.marcas.marcas,
                            sistema.estoque.marcas.index,
                            tam
                    );

                    break;

                case 3:

                    tabela(
                            sistema.estoque.marcas.marcas,
                            sistema.estoque.marcas.index,
                            tam
                    );

                    System.out.println("Código:");
                    int cod = leia.nextInt();
                    leia.nextLine();

                    marca = new Marca();

                    System.out.println("Nome Fantasia:");
                    marca.nomeFantasia = leia.nextLine();

                    System.out.println("CNPJ:");
                    marca.cnpj = leia.nextLine();

                    System.out.println("Fabricante:");
                    marca.fabricante = leia.nextLine();

                    sistema.estoque.marcas.alterar(cod, marca, sistema.estoque);

                    break;

                case 4:

                    tabela(
                            sistema.estoque.marcas.marcas,
                            sistema.estoque.marcas.index,
                            tam
                    );

                    System.out.println("Código:");
                    cod = leia.nextInt();

                    if (sistema.estoque.marcas.removerMarca(cod, sistema.estoque)){
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
                    produto.cod = leia.nextInt();
                    leia.nextLine();

                    System.out.println("Nome:");
                    produto.nome = leia.nextLine();

                    System.out.println("Preço:");
                    produto.preco = leia.nextDouble();

                    System.out.println("Quantidade:");
                    produto.quant = leia.nextInt();

                    tabela(
                            sistema.estoque.marcas.marcas,
                            sistema.estoque.marcas.index,
                            tam
                    );

                    System.out.println("Código da marca:");
                    int codMarca = leia.nextInt();

                    sistema.estoque.inserir(produto, codMarca);

                    break;

                case 2:
                    tabela(
                            sistema.estoque.produtos,
                            sistema.estoque.index,
                            tam
                    );
                    break;

                case 3:

                    tabela(
                            sistema.estoque.produtos,
                            sistema.estoque.index,
                            tam
                    );

                    System.out.println("Código:");
                    int cod = leia.nextInt();
                    leia.nextLine();

                    produto = new Produto();

                    System.out.println("Nome:");
                    produto.nome = leia.nextLine();

                    System.out.println("Preço:");
                    produto.preco = leia.nextDouble();

                    System.out.println("Quantidade:");
                    produto.quant = leia.nextInt();

                    tabela(
                            sistema.estoque.marcas.marcas,
                            sistema.estoque.marcas.index,
                            tam
                    );

                    System.out.println("Código da marca:");
                    codMarca = leia.nextInt();

                    sistema.estoque.alterar(
                            cod,
                            produto,
                            codMarca
                    );

                    break;

                case 4:

                    tabela(
                            sistema.estoque.produtos,
                            sistema.estoque.index,
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
                            sistema.estoque.produtos,
                            sistema.estoque.index,
                            tam
                    );

                    break;

                case 2:

                    tabela(
                            sistema.estoque.ordemAlfabetica(),
                            sistema.estoque.index,
                            tam
                    );

                    break;

                case 3:


                    tabela(
                            sistema.estoque.marcas.marcas,
                            sistema.estoque.marcas.index,
                            tam
                    );

                    break;

                case 4:

                    tabela(
                            sistema.estoque.marcas.marcas,
                            sistema.estoque.marcas.index,
                            tam
                    );

                    System.out.println("Código da marca:");
                    int codMarca = leia.nextInt();
                    leia.nextLine();

                    tabela(
                            sistema.estoque.porMarca(sistema.estoque.marcas.buscarMarca(codMarca)),
                            sistema.estoque.index,
                            tam
                    );

                    break;

                case 5:



                    tabela(
                            sistema.vendas,
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
                        tabela(sistema.vendas[sistema.buscarVenda(codVenda)].itensVendidos, sistema.vendas[sistema.buscarVenda(codVenda)].index, tam);
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

    public static void tabela(Marca marcas[], int index, int tam) {

        if (index == 0) {
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

        for (int i = 0; i < index; i++) {

            if (marcas[i] == null) {
                continue;
            }

            System.out.printf(formato,
                    formatarTexto(String.valueOf(marcas[i].cod), tam),
                    formatarTexto(marcas[i].nomeFantasia, tam),
                    formatarTexto(marcas[i].cnpj, tam),
                    formatarTexto(marcas[i].fabricante, tam)
            );
        }
    }

    public static void tabela(Produto produtos[], int index, int tam) {

        if (index == 0) {
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

        for (int i = 0; i < index; i++) {

            if (produtos[i] == null) {
                continue;
            }

            System.out.printf(formato,
                    formatarTexto(String.valueOf(produtos[i].cod), tam),
                    formatarTexto(produtos[i].nome, tam),
                    formatarTexto(produtos[i].marca.nomeFantasia, tam),
                    formatarTexto(String.format("%.2f", produtos[i].preco), tam),
                    formatarTexto(String.valueOf(produtos[i].quant), tam)
            );
        }
    }

    public static void tabela(Venda vendas[], int index, int tam) {

        if (index == 0) {
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

        for (int i = 0; i < index; i++) {

            if (vendas[i] == null) {
                continue;
            }

            System.out.printf(formato,
                    formatarTexto(String.valueOf(vendas[i].codigo), tam),
                    formatarTexto(vendas[i].nomeCliente, tam),
                    formatarTexto(String.format("%.2f", vendas[i].total), tam)
            );
        }
    }

    public static void tabela(Item itens[], int index, int tam) {

        if (index == 0) {
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

        for (int i = 0; i < index; i++) {

            if (itens[i] == null) {
                continue;
            }

            System.out.printf(formato,
                    formatarTexto(itens[i].produto.nome, tam),
                    formatarTexto(String.valueOf(itens[i].quant), tam),
                    formatarTexto(String.format("%.2f", itens[i].subtotal), tam)
            );
        }
    }
}