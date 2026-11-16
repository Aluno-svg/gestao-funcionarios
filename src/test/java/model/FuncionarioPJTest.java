package model;

import static org.junit.jupiter.api.Assertions.*;
import exception.DadosInvalidosException;
import org.junit.jupiter.api.Test;

class FuncionarioPJTest {

    private static final String CPF = "111.444.777-35";

    @Test
    void calculaRemuneracaoPorProjetos() {
        FuncionarioPJ f = new FuncionarioPJ(2, "Bruno", CPF, 1500, 4);
        assertEquals(6000.0, f.calcularRemuneracao(), 0.001);
    }

    @Test
    void semProjetosRemuneracaoZero() {
        assertEquals(0.0, new FuncionarioPJ(2, "Bruno", CPF, 1500, 0).calcularRemuneracao(), 0.001);
    }

    @Test
    void rejeitaValoresInvalidos() {
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioPJ(2, "Bruno", CPF, -1, 1));
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioPJ(2, "Bruno", CPF, 100, -1));
    }

    @Test
    void naoEhTributavel() {
        Object pj = new FuncionarioPJ(2, "Bruno", CPF, 100, 1);
        assertFalse(pj instanceof Tributavel);
    }
}
