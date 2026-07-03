import java.util.Date;
public class Venda {
    private int codigo;
    private String data;
    private double total;
    private Item itensVendidos[];
    private int index = 0;
    private String nomeCliente;

    boolean realizarVenda(int codigo, String nomeCliente, Carrinho carrinho, int dia, int mes, int ano) {
        if (carrinho == null || carrinho.index == 0) {
            return false;
        }
        if (nomeCliente == null || nomeCliente.isEmpty()) {
            return false;
        }

        for (int i = 0; i < carrinho.index; i++) {
            if (carrinho.itens[i] == null) {
                return false;
            }
            index++;

        }
        if (!dataValida(dia, mes, ano))return false;

        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        this.data = dia + "/" + mes + "/" + ano;
        this.total = carrinho.total;

        this.itensVendidos = new Item[carrinho.index];
        for (int i = 0; i < carrinho.index; i++) {
            this.itensVendidos[i] = carrinho.itens[i];

        }

        return true;
    }

    boolean dataValida(int dia, int mes, int ano) {
        // 1. Validação básica de limites
        if (ano <= 0){
            return false;
        }
        if (mes < 1 || mes > 12) {
            return false;
        }
        if (dia < 1 || dia > 31) {
            return false;
        }

        // 2. Validação de Fevereiro (incluindo ano bissexto)
        if (mes == 2) {
            // Regra do ano bissexto
            boolean isBissexto = (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
            int limiteFevereiro = isBissexto ? 29 : 28;

            if (dia > limiteFevereiro) {
                return false;
            }
        }

        // 3. Meses até Julho (Messes ímpares têm 31, pares têm 30)
        if (mes <= 7 && mes != 2) {
            if (mes % 2 == 0 && dia > 30) {
                return false;
            }
            if (mes % 2 != 0 && dia > 31) { // Essa linha é preciosismo, pois o topo já barra > 31
                return false;
            }
        }

        // 4. Meses após Julho (Meses pares têm 31, ímpares têm 30)
        if (mes > 7) {
            if (mes % 2 == 0 && dia > 31) {
                return false;
            }
            if (mes % 2 != 0 && dia > 30) {
                return false;
            }
        }

        return true;
    }


}