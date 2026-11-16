package repository;

import model.Funcionario;
import model.ValidadorCpf;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FuncionarioRepository {

    private final Map<Integer, Funcionario> armazenamento = new LinkedHashMap<>();

    public void salvar(Funcionario funcionario) {
        armazenamento.put(funcionario.getCodigo(), funcionario);
    }

    public boolean existe(int codigo) {
        return armazenamento.containsKey(codigo);
    }

    public Optional<Funcionario> buscarPorCodigo(int codigo) {
        return Optional.ofNullable(armazenamento.get(codigo));
    }

    public Optional<Funcionario> buscarPorCpf(String cpf) {
        String normalizado = ValidadorCpf.normalizar(cpf);
        return armazenamento.values().stream()
                .filter(f -> f.getCpf().equals(normalizado))
                .findFirst();
    }

    public List<Funcionario> listar() {
        return new ArrayList<>(armazenamento.values());
    }

    public boolean remover(int codigo) {
        return armazenamento.remove(codigo) != null;
    }
}
