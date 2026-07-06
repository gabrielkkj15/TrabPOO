public class Usuario {
    private String nome, username, tipo, senha;
    private int id;

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        if (senha == null) this.senha = senha;
    }
}
