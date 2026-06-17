public class ControladorMarca {
    Marca[] marcas = new Marca[10];
    int index = 0;

    boolean inserir(Marca marca) {

        if (marca.nomeFantasia.replace(" ", "").isEmpty()) {
            return false;
        }

        if (marca.cnpj.replace(" ", "").isEmpty()) {
            return false;
        }

        if (marca.fabricante.replace(" ", "").isEmpty()) {
            return false;
        }

        marca.cod = index + 1;

        if (index == marcas.length) {
            aumentarCapacidade();
        }

        marcas[index] = marca;
        index++;
        return  true;
    }

    Marca buscarMarca(int cod) {
        int i = indiceCod(cod);
        if (i != -1) {
            return marcas[i];
        }
        return null;
    }

    Marca[] listar() {
        Marca[] copia = new Marca[index];

        for (int i = 0; i < index; i++) {
            copia[i] = this.marcas[i];
        }

        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - i - 1; j++) {

                if (copia[j] != null && copia[j+1] != null &&
                        copia[j].nomeFantasia != null && copia[j+1].nomeFantasia != null) {

                    if (copia[j].nomeFantasia.compareToIgnoreCase(copia[j + 1].nomeFantasia) > 0) {

                        Marca temp = copia[j];
                        copia[j] = copia[j + 1];
                        copia[j + 1] = temp;
                    }
                }
            }
        }

        return copia;
    }

    boolean removerMarca(int cod, Estoque estoque) {
        if (estoque.existeProdutoDaMarca(cod)){
            return false;
        }
        if (indiceCod(cod) != -1) {
            int x = indiceCod(cod);

            for (int i = x; i < index - 1; i++) {
                marcas[i] = marcas[i + 1];
            }

            marcas[index - 1] = null;
            index--;
            return true;
        } else {
            return false;
        }
    }

    void alterar(int cod, Marca marca, Estoque estoque) {
        int x = indiceCod(cod);

        if (x == -1) {
            return;
        }

        if (marca.nomeFantasia.replace(" ", "").isEmpty()) {
            return;
        }

        if (marca.cnpj.replace(" ", "").isEmpty()) {
            return;
        }

        if (marca.fabricante.replace(" ", "").isEmpty()) {
            return;
        }

        marca.cod = marcas[x].cod;
        marcas[x] = marca;
        for (int i = 0; i < estoque.index; i++) {
            if(estoque.produtos[i].marca.cod == marca.cod){
                estoque.produtos[i].marca = marca;
            }
        };
    }

    void aumentarCapacidade() {
        Marca[] m = new Marca[marcas.length + 10];

        for (int i = 0; i < index; i++) {
            m[i] = marcas[i];
        }

        marcas = m;
    }

    int indiceCod(int cod) {
        if (index > 0) {
            for (int i = 0; i < index; i++) {
                if (marcas[i].cod == cod) {
                    return i;
                }
            }
        }
        return -1;
    }
}