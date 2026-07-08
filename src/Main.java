import java.util.ArrayList;
import java.util.Scanner;

// Daniel Victor e Gabriel Barbosa
// Sistema de Vendas POO
public class Main {
    static Scanner leia = new Scanner(System.in);
    static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        Sistema sistema = Sistema.getInstancia();
        init(sistema);

        int op;

        do {
            System.out.println("\n=== SISTEMA DE VENDAS ===");
            System.out.println("1 - Realizar Login");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            op = leia.nextInt();
            leia.nextLine(); // limpar buffer

            switch (op) {
                case 1:
                    System.out.print("Username: ");
                    String username = leia.nextLine();
                    System.out.print("Senha: ");
                    String senha = leia.nextLine();

                    Usuario u = login(sistema, username, senha);
                    if (u != null) {
                        usuarioLogado = u;
                        System.out.println("\nLogin com sucesso! Bem-vindo, " + u.getNome() + " (" + u.getTipo() + ").");
                        
                        if (u.getTipo().equalsIgnoreCase("admin")) {
                            menuPrincipalAdmin(sistema);
                        } else if (u.getTipo().equalsIgnoreCase("atendente")) {
                            menuAtend(sistema, u);
                        } else {
                            System.out.println("Tipo de usuário inválido ou sem privilégios.");
                        }
                        usuarioLogado = null; // Logout
                    } else {
                        System.out.println("Usuário ou senha incorretos.");
                    }
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

    public static Usuario login(Sistema sistema, String username, String senha) {
        for (Usuario u : sistema.getUsuarios().getUsuarios()) {
            if (u.getUsername().equals(username) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }

    public static void init(Sistema sistema) {
        // Criando usuários padrão
        Usuario admin = new Usuario(0, "Administrador", "admin", "admin", "admin");
        Usuario atendente = new Usuario(1, "Atendente Padrão", "atendente", "atendente", "123");
        
        sistema.getUsuarios().inserir(admin);
        sistema.getUsuarios().inserir(atendente);

        Marca m1 = new Marca(1, "Nike", "Nike Inc.", "11.111.111/0001-11");
        Marca m2 = new Marca(2, "Adidas", "Adidas AG", "22.222.222/0001-22");
        Marca m3 = new Marca(3, "Puma", "Puma SE", "33.333.333/0001-33");

        sistema.getEstoque().marcas.inserir(m1);
        sistema.getEstoque().marcas.inserir(m2);
        sistema.getEstoque().marcas.inserir(m3);

        Produto p1 = new Produto(1, "Chuteira Mercurial", m1, 799.90, 10);
        Produto p2 = new Produto(2, "Tênis Air Max", m2, 599.99, 8);
        Produto p3 = new Produto(3, "Camisa Real Madrid", m2, 349.90, 15);
        Produto p4 = new Produto(4, "Chuteira Predator", m2, 899.90, 6);
        Produto p5 = new Produto(5, "Camisa Manchester City", m3, 299.90, 12);

        sistema.getEstoque().inserir(p1, 1);
        sistema.getEstoque().inserir(p2, 1);
        sistema.getEstoque().inserir(p3, 2);
        sistema.getEstoque().inserir(p4, 2);
        sistema.getEstoque().inserir(p5, 3);

        // Vendas iniciais com atendente padrão
        Item i1 = new Item(p1, 1);
        Item i2 = new Item(p3, 2);
        sistema.getCarrinho().addItem(i1);
        sistema.getCarrinho().addItem(i2);
        sistema.finalizarVenda("Gabriel Barbosa", atendente, 10, 5, 2026);

        Item i3 = new Item(p2, 1);
        sistema.getCarrinho().addItem(i3);
        sistema.finalizarVenda("Daniel Victor", atendente, 15, 5, 2026);

        Item i4 = new Item(p4, 1);
        Item i5 = new Item(p5, 3);
        sistema.getCarrinho().addItem(i4);
        sistema.getCarrinho().addItem(i5);
        sistema.finalizarVenda("Maria Souza", atendente, 20, 5, 2026);
    }

    static void menuPrincipalAdmin(Sistema sistema) {
        int op;
        do {
            System.out.println("\n=== MENU PRINCIPAL (ADMIN) ===");
            System.out.println("1 - Menu Administrador (Painel)");
            System.out.println("2 - Menu Atendente (Vendas)");
            System.out.println("0 - Logout");
            System.out.print("Opção: ");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {
                case 1:
                    menuAdm(sistema);
                    break;
                case 2:
                    menuAtend(sistema, usuarioLogado);
                    break;
                case 0:
                    System.out.println("Fazendo logout...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (op != 0);
    }

    static void menuAtend(Sistema sistema, Usuario vendedor) {
        int op;
        System.out.println("Digite o tamanho das colunas para exibição:");
        int tam = leia.nextInt();
        if (tam < 10) {
            System.out.println("Tamanho inválido! Ajustado para 10.");
            tam = 10;
        }

        do {
            System.out.println("\n=== PAINEL DE ATENDENTE (Vendedor: " + vendedor.getNome() + ") ===");
            System.out.println("1 - Adicionar produto ao carrinho");
            System.out.println("2 - Ver carrinho");
            System.out.println("3 - Finalizar venda");
            System.out.println("0 - Voltar/Sair");
            System.out.print("Opção: ");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {
                case 1:
                    tabelaProdutos(sistema.getEstoque().getProdutos(), tam);

                    System.out.println("Código do produto:");
                    int cod = leia.nextInt();

                    int indice = sistema.getEstoque().indiceCod(cod);
                    if (indice != -1) {
                        Produto produto = sistema.getEstoque().getProdutos().get(indice);
                        System.out.println("Quantidade:");
                        int quant = leia.nextInt();
                        leia.nextLine();

                        Item item = new Item();
                        item.setProduto(produto);
                        item.setQuant(quant);
                        item.setSubtotal(produto.getPreco() * quant);

                        if (sistema.getCarrinho().addItem(item)) {
                            System.out.println("Produto adicionado ao carrinho!");
                        } else {
                            System.out.println("Erro ao adicionar (Estoque insuficiente ou quantidade inválida).");
                        }
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 2:
                    tabelaItens(sistema.getCarrinho().getItens(), tam);
                    break;

                case 3:
                    System.out.println("Nome do cliente:");
                    String cliente = leia.nextLine();
                    System.out.println("== Data da compra ==");
                    System.out.print("Dia: ");
                    int dia = leia.nextInt();
                    System.out.print("Mês: ");
                    int mes = leia.nextInt();
                    System.out.print("Ano: ");
                    int ano = leia.nextInt();
                    
                    if (sistema.finalizarVenda(cliente, vendedor, dia, mes, ano)) {
                        System.out.println("Venda finalizada com sucesso!");
                    } else {
                        System.out.println("Erro ao finalizar venda (verifique se há itens no carrinho ou data válida).");
                    }
                    break;
            }
        } while (op != 0);
        sistema.resetarCarrinho();
    }

    static void menuAdm(Sistema sistema) {
        int op;
        do {
            System.out.println("\n=== PAINEL ADMINISTRATIVO ===");
            System.out.println("1 - CRUD Marcas");
            System.out.println("2 - CRUD Produtos");
            System.out.println("3 - CRUD Usuários");
            System.out.println("4 - Listagens e Relatórios");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

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
                    menuUsuario(sistema);
                    break;
                case 4:
                    menuListagens(sistema);
                    break;
            }
        } while (op != 0);
    }

    static void menuMarca(Sistema sistema) {
        int op;
        System.out.println("Digite o tamanho das colunas:");
        int tam = leia.nextInt();
        if (tam < 10) {
            tam = 10;
        }

        do {
            System.out.println("\n=== GERENCIAMENTO DE MARCAS ===");
            System.out.println("1 - Cadastrar marca");
            System.out.println("2 - Listar marcas");
            System.out.println("3 - Alterar marcas");
            System.out.println("4 - Excluir marcas");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {
                case 1:
                    Marca marca = new Marca();
                    System.out.println("Nome Fantasia:");
                    marca.setNomeFantasia(leia.nextLine());
                    System.out.println("CNPJ:");
                    marca.setCnpj(leia.nextLine());
                    System.out.println("Fabricante:");
                    marca.setFabricante(leia.nextLine());

                    if (sistema.getEstoque().marcas.inserir(marca)) {
                        System.out.println("Marca cadastrada com sucesso!");
                    } else {
                        System.out.println("Erro ao cadastrar marca (campos inválidos).");
                    }
                    break;

                case 2:
                    tabelaMarcas(sistema.getEstoque().marcas.getMarcas(), tam);
                    break;

                case 3:
                    tabelaMarcas(sistema.getEstoque().marcas.getMarcas(), tam);
                    System.out.println("Código da Marca a ser alterada:");
                    int cod = leia.nextInt();
                    leia.nextLine();

                    marca = new Marca();
                    System.out.println("Novo Nome Fantasia:");
                    marca.setNomeFantasia(leia.nextLine());
                    System.out.println("Novo CNPJ:");
                    marca.setCnpj(leia.nextLine());
                    System.out.println("Novo Fabricante:");
                    marca.setFabricante(leia.nextLine());

                    sistema.getEstoque().marcas.alterar(cod, marca, sistema.getEstoque());
                    System.out.println("Marca alterada com sucesso.");
                    break;

                case 4:
                    tabelaMarcas(sistema.getEstoque().marcas.getMarcas(), tam);
                    System.out.println("Código da Marca a ser removida:");
                    cod = leia.nextInt();

                    if (sistema.getEstoque().marcas.removerMarca(cod, sistema.getEstoque())) {
                        System.out.println("Marca removida com sucesso!");
                    } else {
                        System.out.println("Erro! Marca não removida (verifique se há produtos associados a ela).");
                    }
                    break;
            }
        } while (op != 0);
    }

    static void menuProduto(Sistema sistema) {
        int op;
        System.out.println("Digite o tamanho das colunas:");
        int tam = leia.nextInt();
        if (tam < 10) {
            tam = 10;
        }

        do {
            System.out.println("\n=== GERENCIAMENTO DE PRODUTOS ===");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Alterar produto");
            System.out.println("4 - Excluir produto");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

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

                    tabelaMarcas(sistema.getEstoque().marcas.getMarcas(), tam);
                    System.out.println("Código da marca:");
                    int codMarca = leia.nextInt();

                    if (sistema.getEstoque().inserir(produto, codMarca)) {
                        System.out.println("Produto cadastrado com sucesso!");
                    } else {
                        System.out.println("Erro ao cadastrar produto (verifique campos, valores ou marca válida).");
                    }
                    break;

                case 2:
                    tabelaProdutos(sistema.getEstoque().getProdutos(), tam);
                    break;

                case 3:
                    tabelaProdutos(sistema.getEstoque().getProdutos(), tam);
                    System.out.println("Código do Produto a ser alterado:");
                    int cod = leia.nextInt();
                    leia.nextLine();

                    produto = new Produto();
                    System.out.println("Novo Nome:");
                    produto.setNome(leia.nextLine());
                    System.out.println("Novo Preço:");
                    produto.setPreco(leia.nextDouble());
                    System.out.println("Nova Quantidade:");
                    produto.setQuant(leia.nextInt());

                    tabelaMarcas(sistema.getEstoque().marcas.getMarcas(), tam);
                    System.out.println("Código da nova marca:");
                    codMarca = leia.nextInt();

                    sistema.getEstoque().alterar(cod, produto, codMarca);
                    break;

                case 4:
                    tabelaProdutos(sistema.getEstoque().getProdutos(), tam);
                    System.out.println("Código do Produto a ser removido:");
                    cod = leia.nextInt();

                    if (sistema.getEstoque().remover(cod, sistema)) {
                        System.out.println("Produto removido!");
                    } else {
                        System.out.println("Erro ao remover produto (verifique se já possui vendas vinculadas).");
                    }
                    break;
            }
        } while (op != 0);
    }

    static void menuUsuario(Sistema sistema) {
        int op;
        System.out.println("Digite o tamanho das colunas:");
        int tam = leia.nextInt();
        if (tam < 10) {
            tam = 10;
        }

        do {
            System.out.println("\n=== GERENCIAMENTO DE USUÁRIOS ===");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Alterar usuário");
            System.out.println("4 - Excluir usuário");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {
                case 1:
                    Usuario novoUsuario = new Usuario();
                    System.out.println("Nome:");
                    novoUsuario.setNome(leia.nextLine());
                    System.out.println("Username:");
                    novoUsuario.setUsername(leia.nextLine());
                    System.out.println("Tipo (admin/atendente):");
                    novoUsuario.setTipo(leia.nextLine());
                    System.out.println("Senha:");
                    novoUsuario.setSenha(leia.nextLine());

                    if (sistema.getUsuarios().inserir(novoUsuario)) {
                        System.out.println("Usuário cadastrado com sucesso!");
                    } else {
                        System.out.println("Erro ao cadastrar usuário (username duplicado ou campos vazios).");
                    }
                    break;

                case 2:
                    tabelaUsuarios(sistema.getUsuarios().getUsuarios(), tam);
                    break;

                case 3:
                    tabelaUsuarios(sistema.getUsuarios().getUsuarios(), tam);
                    System.out.println("ID do Usuário a ser alterado:");
                    int id = leia.nextInt();
                    leia.nextLine();

                    Usuario usuarioAlt = new Usuario();
                    System.out.println("Novo Nome:");
                    usuarioAlt.setNome(leia.nextLine());
                    System.out.println("Novo Username:");
                    usuarioAlt.setUsername(leia.nextLine());
                    System.out.println("Novo Tipo (admin/atendente):");
                    usuarioAlt.setTipo(leia.nextLine());
                    System.out.println("Nova Senha:");
                    usuarioAlt.setSenha(leia.nextLine());

                    sistema.getUsuarios().alterar(id, usuarioAlt);
                    System.out.println("Operação de alteração realizada.");
                    break;

                case 4:
                    tabelaUsuarios(sistema.getUsuarios().getUsuarios(), tam);
                    System.out.println("ID do Usuário a ser excluído:");
                    id = leia.nextInt();
                    leia.nextLine();

                    if (sistema.getUsuarios().removerUsuario(id, sistema)) {
                        System.out.println("Usuário removido com sucesso!");
                    } else {
                        System.out.println("Erro! Usuário não pôde ser removido (possui vendas vinculadas ou ID inexistente).");
                    }
                    break;
            }
        } while (op != 0);
    }

    static void menuListagens(Sistema sistema) {
        int op;
        System.out.println("Digite o tamanho das colunas:");
        int tam = leia.nextInt();
        if (tam < 10) {
            tam = 10;
        }
        do {
            System.out.println("\n=== LISTAGENS E RELATÓRIOS ===");
            System.out.println("1 - Todos os produtos");
            System.out.println("2 - Produtos em ordem alfabética");
            System.out.println("3 - Todas as marcas");
            System.out.println("4 - Produtos de uma marca específica");
            System.out.println("5 - Todas as vendas");
            System.out.println("6 - Vendas por dia");
            System.out.println("7 - Buscar venda por código");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            op = leia.nextInt();
            leia.nextLine();

            switch (op) {
                case 1:
                    tabelaProdutos(sistema.getEstoque().getProdutos(), tam);
                    break;

                case 2:
                    tabelaProdutos(sistema.getEstoque().ordemAlfabetica(), tam);
                    break;

                case 3:
                    tabelaMarcas(sistema.getEstoque().marcas.getMarcas(), tam);
                    break;

                case 4:
                    tabelaMarcas(sistema.getEstoque().marcas.getMarcas(), tam);
                    System.out.println("Código da marca:");
                    int codMarca = leia.nextInt();
                    leia.nextLine();

                    tabelaProdutos(sistema.getEstoque().porMarca(sistema.getEstoque().marcas.buscarMarca(codMarca)), tam);
                    break;

                case 5:
                    tabelaVendas(sistema.getVendas(), tam);
                    break;

                case 6:
                    System.out.println("Digite a data (dd/mm/yyyy):");
                    String data = leia.nextLine();
                    tabelaVendas(sistema.vendasData(data), tam);
                    break;

                case 7:
                    System.out.println("Código da venda:");
                    int codVenda = leia.nextInt();
                    leia.nextLine();
                    int idxVenda = sistema.buscarVenda(codVenda);
                    if (idxVenda != -1) {
                        tabelaItens(sistema.getVendas()[idxVenda].getItensVendidos(), tam);
                    } else {
                        System.out.println("Código de venda inválido!");
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

    // Exibições de tabela usando sobrecarga de nomes distintos para evitar colisão por apagamento de tipos genéricos
    public static void tabelaMarcas(Marca[] marcas, int tam) {
        if (marcas == null || marcas.length == 0) {
            System.out.println("\nNenhuma marca cadastrada.");
            return;
        }

        String formato = "%-" + tam + "s %-" + tam + "s %-" + tam + "s %-" + tam + "s%n";
        System.out.printf(formato, "COD", "NOME", "CNPJ", "FABRICANTE");

        for (Marca marca : marcas) {
            if (marca == null) continue;
            System.out.printf(formato,
                    formatarTexto(String.valueOf(marca.getCod()), tam),
                    formatarTexto(marca.getNomeFantasia(), tam),
                    formatarTexto(marca.getCnpj(), tam),
                    formatarTexto(marca.getFabricante(), tam)
            );
        }
    }

    public static void tabelaMarcas(ArrayList<Marca> marcas, int tam) {
        if (marcas == null) {
            System.out.println("\nNenhuma marca cadastrada.");
            return;
        }
        tabelaMarcas(marcas.toArray(new Marca[0]), tam);
    }

    public static void tabelaProdutos(Produto[] produtos, int tam) {
        if (produtos == null || produtos.length == 0) {
            System.out.println("\nNenhum produto cadastrado.");
            return;
        }

        String formato = "%-" + tam + "s %-" + tam + "s %-" + tam + "s %-" + tam + "s %-" + tam + "s%n";
        System.out.printf(formato, "COD", "NOME", "MARCA", "PREÇO", "QUANT");

        for (Produto produto : produtos) {
            if (produto == null) continue;
            System.out.printf(formato,
                    formatarTexto(String.valueOf(produto.getCod()), tam),
                    formatarTexto(produto.getNome(), tam),
                    formatarTexto(produto.getMarca() != null ? produto.getMarca().getNomeFantasia() : "N/A", tam),
                    formatarTexto(String.format("%.2f", produto.getPreco()), tam),
                    formatarTexto(String.valueOf(produto.getQuant()), tam)
            );
        }
    }

    public static void tabelaProdutos(ArrayList<Produto> produtos, int tam) {
        if (produtos == null) {
            System.out.println("\nNenhum produto cadastrado.");
            return;
        }
        tabelaProdutos(produtos.toArray(new Produto[0]), tam);
    }

    public static void tabelaVendas(Venda[] vendas, int tam) {
        if (vendas == null || vendas.length == 0) {
            System.out.println("\nNenhuma venda cadastrada.");
            return;
        }

        String formato = "%-" + tam + "s %-" + tam + "s %-" + tam + "s %-" + tam + "s %-" + tam + "s%n";
        System.out.printf(formato, "COD", "CLIENTE", "VENDEDOR", "TOTAL", "DATA");

        for (Venda venda : vendas) {
            if (venda == null) continue;
            System.out.printf(formato,
                    formatarTexto(String.valueOf(venda.getCodigo()), tam),
                    formatarTexto(venda.getNomeCliente(), tam),
                    formatarTexto(venda.getVendedor() != null ? venda.getVendedor().getNome() : "N/A", tam),
                    formatarTexto(String.format("%.2f", venda.getTotal()), tam),
                    formatarTexto(venda.getData(), tam)
            );
        }
    }

    public static void tabelaVendas(ArrayList<Venda> vendas, int tam) {
        if (vendas == null) {
            System.out.println("\nNenhuma venda cadastrada.");
            return;
        }
        tabelaVendas(vendas.toArray(new Venda[0]), tam);
    }

    public static void tabelaItens(Item[] itens, int tam) {
        if (itens == null || itens.length == 0) {
            System.out.println("\nCarrinho vazio ou sem itens.");
            return;
        }

        String formato = "%-" + tam + "s %-" + tam + "s %-" + tam + "s%n";
        System.out.printf(formato, "PRODUTO", "QUANT", "SUBTOTAL");

        for (Item item : itens) {
            if (item == null) continue;
            System.out.printf(formato,
                    formatarTexto(item.getProduto() != null ? item.getProduto().getNome() : "N/A", tam),
                    formatarTexto(String.valueOf(item.getQuant()), tam),
                    formatarTexto(String.format("%.2f", item.getSubtotal()), tam)
            );
        }
    }

    public static void tabelaUsuarios(Usuario[] usuarios, int tam) {
        if (usuarios == null || usuarios.length == 0) {
            System.out.println("\nNenhum usuário cadastrado.");
            return;
        }

        String formato = "%-" + tam + "s %-" + tam + "s %-" + tam + "s %-" + tam + "s%n";
        System.out.printf(formato, "ID", "NOME", "USERNAME", "TIPO");

        for (Usuario u : usuarios) {
            if (u == null) continue;
            System.out.printf(formato,
                    formatarTexto(String.valueOf(u.getId()), tam),
                    formatarTexto(u.getNome(), tam),
                    formatarTexto(u.getUsername(), tam),
                    formatarTexto(u.getTipo(), tam)
            );
        }
    }

    public static void tabelaUsuarios(ArrayList<Usuario> usuarios, int tam) {
        if (usuarios == null) {
            System.out.println("\nNenhum usuário cadastrado.");
            return;
        }
        tabelaUsuarios(usuarios.toArray(new Usuario[0]), tam);
    }
}