import java.util.ArrayList;

public class ControladorUsuario {
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    boolean inserir(Usuario usuario) {
        if (usuario == null) {
            return false;
        }

        if (usuario.getNome() == null || usuario.getNome().replace(" ", "").isEmpty()) {
            return false;
        }

        if (usuario.getUsername() == null || usuario.getUsername().replace(" ", "").isEmpty()) {
            return false;
        }

        if (usuario.getTipo() == null || usuario.getTipo().replace(" ", "").isEmpty()) {
            return false;
        }

        if (usuario.getSenha() == null || usuario.getSenha().replace(" ", "").isEmpty()) {
            return false;
        }

        // Validação de username repetido (similar ao que Estoque faz com produtos)
        if (!usuarios.isEmpty()) {
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getUsername().trim().equalsIgnoreCase(usuario.getUsername().trim())) {
                    return false;
                }
            }
        }

        usuario.setId(usuarios.size());
        usuarios.add(usuario);
        return true;
    }

    Usuario buscarUsuario(int id) {
        int i = indiceId(id);
        if (i != -1) {
            for (int j = 0; j < usuarios.size(); j++) {
                if (usuarios.get(j).getId() == id) {
                    return usuarios.get(j);
                }
            }
        }
        return null;
    }

    Usuario[] listar() {
        Usuario[] copia = usuarios.toArray(Usuario[]::new);
        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - i - 1; j++) {
                if (copia[j] != null && copia[j+1] != null &&
                        copia[j].getNome() != null && copia[j+1].getNome() != null) {
                    if (copia[j].getNome().compareToIgnoreCase(copia[j + 1].getNome()) > 0) {
                        Usuario temp = copia[j];
                        copia[j] = copia[j + 1];
                        copia[j + 1] = temp;
                    }
                }
            }
        }
        return copia;
    }

    boolean removerUsuario(int id, Sistema sistema) {
        if (sistema.usuarioPossuiVenda(id)) {
            return false;
        }
        int x = indiceId(id);
        if (x != -1) {
            usuarios.remove(x);
            return true;
        } else {
            return false;
        }
    }

    void alterar(int id, Usuario usuario) {
        int x = indiceId(id);

        if (x == -1) {
            return;
        }

        if (usuario == null ||
                usuario.getNome() == null ||
                usuario.getUsername() == null ||
                usuario.getTipo() == null ||
                usuario.getSenha() == null) {
            return;
        }

        if (usuario.getNome().replace(" ", "").isEmpty()) {
            return;
        }

        if (usuario.getUsername().replace(" ", "").isEmpty()) {
            return;
        }

        if (usuario.getTipo().replace(" ", "").isEmpty()) {
            return;
        }

        if (usuario.getSenha().replace(" ", "").isEmpty()) {
            return;
        }

        Usuario usuarioExistente = usuarios.get(x);
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setTipo(usuario.getTipo());
        usuarioExistente.setSenha(usuario.getSenha());
    }

    int indiceId(int id) {
        if (!usuarios.isEmpty()) {
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getId() == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
