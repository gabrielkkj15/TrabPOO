import java.util.ArrayList;

public class ControladorMarca {
    private ArrayList<Marca> marcas = new ArrayList<>();

    boolean inserir(Marca marca) {

        if (marca.getNomeFantasia().replace(" ", "").isEmpty()) {
            return false;
        }

        if (marca.getCnpj().replace(" ", "").isEmpty()) {
            return false;
        }

        if (marca.getFabricante().replace(" ", "").isEmpty()) {
            return false;
        }

        marca.setCod(marcas.size());

        marcas.add(marca);
        return  true;
    }

    Marca buscarMarca(int cod) {
        int i = indiceCod(cod);
        if (i != -1) {
            for (int j = 0; j < marcas.size(); j++) {
                if (marcas.get(j).getCod()==cod)return marcas.get(j);
            }
        }
        return null;
    }

    Marca[] listar() {
        Marca[] copia = marcas.toArray(Marca[]::new);
        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - i - 1; j++) {

                if (copia[j] != null && copia[j+1] != null &&
                        copia[j].getNomeFantasia() != null && copia[j+1].getNomeFantasia() != null) {

                    if (copia[j].getNomeFantasia().compareToIgnoreCase(copia[j + 1].getNomeFantasia()) > 0) {

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
            marcas.remove(indiceCod(cod));
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

        if (marca == null ||
                marca.getNomeFantasia() == null ||
                marca.getCnpj() == null ||
                marca.getFabricante() == null) {
            return;
        }
        if (marca.getNomeFantasia().replace(" ", "").isEmpty()) {
            return;
        }

        if (marca.getCnpj().replace(" ", "").isEmpty()) {
            return;
        }

        if (marca.getFabricante().replace(" ", "").isEmpty()) {
            return;
        }

        //Mesma referencia! Altera em 1 altera nos 2
        Marca marcaExistente = marcas.get(x);

        marcaExistente.setNomeFantasia(marca.getNomeFantasia());
        marcaExistente.setCnpj(marca.getCnpj());
        marcaExistente.setFabricante(marca.getFabricante());
    }


    int indiceCod(int cod) {
        if (!marcas.isEmpty()) {
            for (int i = 0; i < marcas.size(); i++) {
                if (marcas.get(i).getCod() == cod) {
                    return i;
                }
            }
        }
        return -1;
    }
}