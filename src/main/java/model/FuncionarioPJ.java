package model;

import exception.DadosInvalidosException;

public class FuncionarioPJ extends Funcionario {

    private double valorPorProjeto;
    private int quantidadeProjetos;

    public FuncionarioPJ(int codigo, String nome, String cpf, double valorPorProjeto, int quantidadeProjetos) {
        super(codigo, nome, cpf, 0.0);
        setValorPorProjeto(valorPorProjeto);
        setQuantidadeProjetos(quantidadeProjetos);
    }

    public double getValorPorProjeto() {
        return valorPorProjeto;
    }

    public final void setValorPorProjeto(double valorPorProjeto) {
        if (Double.isNaN(valorPorProjeto) || valorPorProjeto < 0) {
            throw new DadosInvalidosException("O valor por projeto não pode ser negativo.");
        }
        this.valorPorProjeto = valorPorProjeto;
    }

    public int getQuantidadeProjetos() {
        return quantidadeProjetos;
    }

    public final void setQuantidadeProjetos(int quantidadeProjetos) {
        if (quantidadeProjetos < 0) {
            throw new DadosInvalidosException("A quantidade de projetos não pode ser negativa.");
        }
        this.quantidadeProjetos = quantidadeProjetos;
    }

    @Override
    public double calcularRemuneracao() {
        return valorPorProjeto * quantidadeProjetos;
    }
}
