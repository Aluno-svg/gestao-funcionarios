package model;

import exception.DadosInvalidosException;

public class FuncionarioComissionado extends Funcionario implements Tributavel {

    public static final double ALIQUOTA = 0.11;

    private double percentualComissao;
    private double totalVendas;

    public FuncionarioComissionado(int codigo, String nome, String cpf, double salarioBase,
                                   double percentualComissao, double totalVendas) {
        super(codigo, nome, cpf, salarioBase);
        setPercentualComissao(percentualComissao);
        setTotalVendas(totalVendas);
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public final void setPercentualComissao(double percentualComissao) {
        if (Double.isNaN(percentualComissao) || percentualComissao < 0 || percentualComissao > 100) {
            throw new DadosInvalidosException("O percentual de comissão deve estar entre 0 e 100.");
        }
        this.percentualComissao = percentualComissao;
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public final void setTotalVendas(double totalVendas) {
        if (Double.isNaN(totalVendas) || totalVendas < 0) {
            throw new DadosInvalidosException("O total de vendas não pode ser negativo.");
        }
        this.totalVendas = totalVendas;
    }

    public double calcularComissao() {
        return totalVendas * percentualComissao / 100.0;
    }

    @Override
    public double calcularTributos() {
        return (getSalarioBase() + calcularComissao()) * ALIQUOTA;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + calcularComissao() - calcularTributos();
    }
}
