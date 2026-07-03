public class Carrinho {

    private Item itens[] = new Item[1];
    private double total;
    int index;

    boolean addItem(Item item) {

        if (item.quant > 0 && item.quant <= item.produto.quant) {
            for (int i = 0; i < index; i++) {
                if (itens[i].produto.cod == item.produto.cod) {
                    if (itens[i].quant + item.quant <= item.produto.quant) {
                        itens[i].quant += item.quant;
                        System.out.println("Deu certo!");
                    }else {
                        return false;
                    }
                }
            }

            if (itens.length == index) {
                aumentarCapacidade();
            }

            item.subtotal = item.produto.preco * item.quant;
            total += item.produto.preco * item.quant;
            itens[index] = item;
            index++;

            return true;
        }

        return false;
    }

    void aumentarCapacidade() {

        Item[] m = new Item[itens.length + 1];

        for (int i = 0; i < index; i++) {
            m[i] = itens[i];
        }
        itens = m;
    }
}