import java.util.ArrayList;

public class Carrinho {

    private ArrayList<Item> itens = new ArrayList<>();
    private double total;

    boolean addItem(Item item) {

        if (item.getQuant() > 0 && item.getQuant() <= item.getProduto().getQuant()) {

            for (int i = 0; i < itens.size(); i++) {
                Item a = itens.get(i);
                if (a.getProduto().getCod() == item.getProduto().getCod()) {
                    if (a.getQuant() + item.getQuant() <= item.getProduto().getQuant()) {
                        a.setQuant(a.getQuant() + item.getQuant());
                        total += item.getProduto().getPreco() * item.getQuant();
                    }else{
                        return false;
                    }
                }
            }



            item.setSubtotal(item.getProduto().getPreco() * item.getQuant());
            total += item.getProduto().getPreco() * item.getQuant();
            itens.add(item);

            return true;
        }

        return false;
    }

    public Item[] getItens() {
        return itens.toArray(Item[]::new);
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<Item> getCarrinho() {
        return itens;
    }
}