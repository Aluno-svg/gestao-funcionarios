package model;

import exception.DadosInvalidosException;
import java.util.Locale;

public abstract class Funcionario implements Identificavel {

    private final int codigo;
    private String nome;
    private String cpf;
    private double salarioBase;

    protected Funcionario(int codigo, String nome, String cpf, double salarioBase) {
        if (codigo <= 0) {
            throw new DadosInvalidosException("O código deve ser maior que zero.");
        }
        this.codigo = codigo;
        setNome(nome);
        setCpf(cpf);
        setSalarioBase(salarioBase);
    }

    public abstract double calcularRemuneracao();

    @Override
    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public final void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public String getCpf() {
        return cpf;
    }

    public final void setCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new DadosInvalidosException("O CPF é obrigatório.");
        }
        if (!ValidadorCpf.valido(cpf)) {
            throw new DadosInvalidosException("CPF inválido: " + cpf);
        }
        this.cpf = ValidadorCpf.normalizar(cpf);
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public final void setSalarioBase(double salarioBase) {
        if (Double.isNaN(salarioBase) || salarioBase < 0) {
            throw new DadosInvalidosException("O salário-base não pode ser negativo.");
        }
        this.salarioBase = salarioBase;
    }

    public String exibirDados() {
        return String.format(Locale.forLanguageTag("pt-BR"),
                "Código: %d | Tipo: %s | Nome: %s | CPF: %s | Remuneração: R$ %.2f",
                codigo, getClass().getSimpleName(), nome, cpf, calcularRemuneracao());
    }

    @Override
    public String toString() {
        return exibirDados();
    }
}
