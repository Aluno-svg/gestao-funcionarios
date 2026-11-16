package model;

import exception.DadosInvalidosException;

public class FuncionarioCLT extends Funcionario implements Tributavel {

    public static final double ALIQUOTA = 0.11;

    private double adicionalBeneficios;

    public FuncionarioCLT(int codigo, String nome, String cpf, double salarioBase, double adicionalBeneficios) {
        super(codigo, nome, cpf, salarioBase);
        setAdicionalBeneficios(adicionalBeneficios);
    }

    public double getAdicionalBeneficios() {
        return adicionalBeneficios;
    }

    public final void setAdicionalBeneficios(double adicionalBeneficios) {
        if (Double.isNaN(adicionalBeneficios) || adicionalBeneficios < 0) {
            throw new DadosInvalidosException("O adicional de benefícios não pode ser negativo.");
        }
        this.adicionalBeneficios = adicionalBeneficios;
    }

    @Override
    public double calcularTributos() {
        return getSalarioBase() * ALIQUOTA;
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + adicionalBeneficios - calcularTributos();
    }
}
