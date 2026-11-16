package service;

import exception.DadosInvalidosException;
import exception.FuncionarioJaCadastradoException;
import exception.FuncionarioNaoEncontradoException;
import model.Funcionario;
import repository.FuncionarioRepository;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FuncionarioService {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        if (repository == null) {
            throw new DadosInvalidosException("O repositório é obrigatório.");
        }
        this.repository = repository;
    }

    public void cadastrar(Funcionario funcionario) throws FuncionarioJaCadastradoException {
        if (funcionario == null) {
            throw new DadosInvalidosException("O funcionário é obrigatório.");
        }
        if (repository.existe(funcionario.getCodigo())) {
            throw new FuncionarioJaCadastradoException(
                    "Já existe funcionário com o código " + funcionario.getCodigo() + ".");
        }
        if (repository.buscarPorCpf(funcionario.getCpf()).isPresent()) {
            throw new FuncionarioJaCadastradoException(
                    "Já existe funcionário com o CPF " + funcionario.getCpf() + ".");
        }
        repository.salvar(funcionario);
    }

    public List<Funcionario> listar() {
        return repository.listar();
    }

    public Funcionario buscarFuncionario(int codigo) throws FuncionarioNaoEncontradoException {
        return repository.buscarPorCodigo(codigo).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionário não encontrado (código " + codigo + ")."));
    }

    public Funcionario buscarFuncionario(String cpf) throws FuncionarioNaoEncontradoException {
        if (cpf == null || cpf.isBlank()) {
            throw new DadosInvalidosException("O CPF é obrigatório.");
        }
        return repository.buscarPorCpf(cpf).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionário não encontrado (CPF " + cpf + ")."));
    }

    public List<Funcionario> buscarFuncionario(String nome, boolean buscaExata)
            throws FuncionarioNaoEncontradoException {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome é obrigatório.");
        }
        String alvo = nome.trim().toLowerCase(PT_BR);
        List<Funcionario> encontrados = repository.listar().stream()
                .filter(f -> {
                    String atual = f.getNome().toLowerCase(PT_BR);
                    return buscaExata ? atual.equals(alvo) : atual.contains(alvo);
                })
                .collect(Collectors.toList());
        if (encontrados.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Nenhum funcionário encontrado para o nome \"" + nome + "\".");
        }
        return encontrados;
    }

    public void remover(int codigo) throws FuncionarioNaoEncontradoException {
        if (!repository.remover(codigo)) {
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado (código " + codigo + ").");
        }
    }

    public double calcularTotalFolha() {
        return repository.listar().stream().mapToDouble(Funcionario::calcularRemuneracao).sum();
    }

    public String gerarRelatorio() {
        StringBuilder sb = new StringBuilder("=== RELATÓRIO DE FUNCIONÁRIOS ===\n");
        for (Funcionario f : repository.listar()) {
            sb.append(String.format(PT_BR, "%d - %s (%s): R$ %.2f%n",
                    f.getCodigo(), f.getNome(), f.getClass().getSimpleName(), f.calcularRemuneracao()));
        }
        sb.append(String.format(PT_BR, "TOTAL DA FOLHA: R$ %.2f%n", calcularTotalFolha()));
        return sb.toString();
    }
}
