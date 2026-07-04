public class Marca {
    private int cod;
    private String nomeFantasia;
    private String fabricante;
    private String cnpj;

    //construtores
    public Marca() {

    }

    public Marca(int cod, String nomeFantasia, String fabricante, String cnpj ) {
        this.cod = cod;
        this.nomeFantasia = nomeFantasia;
        this.fabricante = fabricante;
        this.cnpj = cnpj;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }
    
    public String getFabricante() {
        return fabricante;
    }
}
