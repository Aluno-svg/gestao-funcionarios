package view;

import exception.DadosInvalidosException;
import exception.FuncionarioJaCadastradoException;
import exception.FuncionarioNaoEncontradoException;
import model.Funcionario;
import model.FuncionarioCLT;
import model.FuncionarioComissionado;
import model.FuncionarioPJ;
import service.FuncionarioService;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class FuncionarioView {

    private final FuncionarioService service;
    private final Scanner in = new Scanner(System.in);

    public FuncionarioView(FuncionarioService service) {
        this.service = service;
    }

    public void iniciar() {
        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            String opcao = in.nextLine().trim();
            try {
                switch (opcao) {
                    case "1" -> cadastrar();
                    case "2" -> listar();
                    case "3" -> buscar();
                    case "4" -> remover();
                    case "5" -> exibirDados();
                    case "6" -> System.out.println(service.gerarRelatorio());
                    case "7" -> System.out.printf(Locale.forLanguageTag("pt-BR"),
                            "Total da folha: R$ %.2f%n", service.calcularTotalFolha());
                    case "0" -> rodando = false;
                    default -> System.out.println("Opção inválida.");
                }
            } catch (FuncionarioJaCadastradoException | FuncionarioNaoEncontradoException
                     | DadosInvalidosException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Erro: valor numérico inválido.");
            }
        }
        System.out.println("Encerrado.");
    }

    private void exibirMenu() {
        System.out.println("""

                ===== GESTÃO DE FUNCIONÁRIOS =====
                1 - Cadastrar funcionário
                2 - Listar funcionários
                3 - Buscar funcionário
                4 - Remover funcionário
                5 - Exibir dados completos
                6 - Gerar relatório
                7 - Total da folha
                0 - Sair""");
        System.out.print("Opção: ");
    }

    private void cadastrar() throws FuncionarioJaCadastradoException {
        int codigo = lerInt("Código: ");
        String nome = lerTexto("Nome: ");
        String cpf = lerTexto("CPF: ");
        String tipo = lerTexto("Tipo (1-CLT, 2-PJ, 3-Comissionado): ");
        Funcionario f;
        switch (tipo) {
            case "1" -> f = new FuncionarioCLT(codigo, nome, cpf,
                    lerDouble("Salário-base: "), lerDouble("Adicional de benefícios: "));
            case "2" -> f = new FuncionarioPJ(codigo, nome, cpf,
                    lerDouble("Valor por projeto: "), lerInt("Quantidade de projetos: "));
            case "3" -> f = new FuncionarioComissionado(codigo, nome, cpf,
                    lerDouble("Salário-base: "), lerDouble("Percentual de comissão (0-100): "),
                    lerDouble("Total de vendas: "));
            default -> throw new DadosInvalidosException("Tipo de funcionário inválido.");
        }
        service.cadastrar(f);
        System.out.println("Funcionário cadastrado.");
    }

    private void listar() {
        List<Funcionario> lista = service.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        lista.forEach(f -> System.out.println(f.exibirDados()));
    }

    private void buscar() throws FuncionarioNaoEncontradoException {
        String criterio = lerTexto("Buscar por (1-código, 2-CPF, 3-nome exato, 4-nome parcial): ");
        switch (criterio) {
            case "1" -> System.out.println(service.buscarFuncionario(lerInt("Código: ")).exibirDados());
            case "2" -> System.out.println(service.buscarFuncionario(lerTexto("CPF: ")).exibirDados());
            case "3" -> service.buscarFuncionario(lerTexto("Nome: "), true)
                    .forEach(f -> System.out.println(f.exibirDados()));
            case "4" -> service.buscarFuncionario(lerTexto("Nome: "), false)
                    .forEach(f -> System.out.println(f.exibirDados()));
            default -> System.out.println("Critério inválido.");
        }
    }

    private void remover() throws FuncionarioNaoEncontradoException {
        service.remover(lerInt("Código: "));
        System.out.println("Funcionário removido.");
    }

    private void exibirDados() throws FuncionarioNaoEncontradoException {
        System.out.println(service.buscarFuncionario(lerInt("Código: ")).exibirDados());
    }

    private String lerTexto(String rotulo) {
        System.out.print(rotulo);
        return in.nextLine().trim();
    }

    private int lerInt(String rotulo) {
        return Integer.parseInt(lerTexto(rotulo));
    }

    private double lerDouble(String rotulo) {
        return Double.parseDouble(lerTexto(rotulo).replace(',', '.'));
    }
}
