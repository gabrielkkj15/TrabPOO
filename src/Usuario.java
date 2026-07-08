public class Usuario {
    private int id;
    private String nome;
    private String username;
    private String tipo;
    private String senha;

    public Usuario() {
    }

    public Usuario(int id, String nome, String username, String tipo, String senha) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.tipo = tipo;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
