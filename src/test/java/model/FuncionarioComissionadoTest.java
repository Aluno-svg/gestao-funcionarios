package model;

import static org.junit.jupiter.api.Assertions.*;
import exception.DadosInvalidosException;
import org.junit.jupiter.api.Test;

class FuncionarioComissionadoTest {

    private static final String CPF = "123.456.789-09";

    @Test
    void calculaComissaoTributosERemuneracao() {
        FuncionarioComissionado f = new FuncionarioComissionado(3, "Carla", CPF, 2000, 10, 10000);
        assertEquals(1000.0, f.calcularComissao(), 0.001);
        assertEquals(330.0, f.calcularTributos(), 0.001);
        assertEquals(2670.0, f.calcularRemuneracao(), 0.001);
    }

    @Test
    void rejeitaPercentuaisEValoresInvalidos() {
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioComissionado(3, "Carla", CPF, 2000, -1, 0));
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioComissionado(3, "Carla", CPF, 2000, 101, 0));
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioComissionado(3, "Carla", CPF, -2000, 10, 0));
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioComissionado(3, "Carla", CPF, 2000, 10, -1));
    }
}
