public class Carrinho {

    private Item itens[] = new Item[1];
    private double total;
    int index;

    boolean addItem(Item item) {

        if (item.getQuant() > 0 && item.getQuant() <= item.getProduto().getQuant()) {
            for (int i = 0; i < index; i++) {
                if (itens[i].getProduto().getCod() == item.getProduto().getCod()) {
                    if (itens[i].getQuant() + item.getQuant() <= item.getProduto().getQuant()) {
                        itens[i].setQuant(itens[i].getQuant() + item.getQuant()); 
                        System.out.println("Deu certo!");
                    }else {
                        return false;
                    }
                }
            }

            if (itens.length == index) {
                aumentarCapacidade();
            }

            item.setSubtotal(item.getProduto().getPreco() * item.getQuant());
            total += item.getProduto().getPreco() * item.getQuant();
            itens[index] = item;
            index++;

            return true;
        }

        return false;
    }

    public Item[] getItens() {
        return itens;
    }

    public double getTotal() {
        return total;
    }

    void aumentarCapacidade() {

        Item[] m = new Item[itens.length + 1];

        for (int i = 0; i < index; i++) {
            m[i] = itens[i];
        }
        itens = m;
    }
}