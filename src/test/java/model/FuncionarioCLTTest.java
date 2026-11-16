package model;

import static org.junit.jupiter.api.Assertions.*;
import exception.DadosInvalidosException;
import org.junit.jupiter.api.Test;

class FuncionarioCLTTest {

    private static final String CPF = "529.982.247-25";

    @Test
    void calculaTributosERemuneracao() {
        FuncionarioCLT f = new FuncionarioCLT(1, "Ana", CPF, 3000, 500);
        assertEquals(330.0, f.calcularTributos(), 0.001);
        assertEquals(3170.0, f.calcularRemuneracao(), 0.001);
    }

    @Test
    void encapsulamentoEValidacaoNosSetters() {
        FuncionarioCLT f = new FuncionarioCLT(1, "Ana", CPF, 3000, 500);
        f.setAdicionalBeneficios(100);
        assertEquals(100, f.getAdicionalBeneficios());
        assertThrows(DadosInvalidosException.class, () -> f.setAdicionalBeneficios(-1));
        assertThrows(DadosInvalidosException.class, () -> f.setSalarioBase(-1));
    }

    @Test
    void rejeitaDadosInvalidosNoConstrutor() {
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioCLT(1, "Ana", "000.000.000-00", 1000, 0));
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioCLT(1, " ", CPF, 1000, 0));
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioCLT(1, "Ana", null, 1000, 0));
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioCLT(0, "Ana", CPF, 1000, 0));
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioCLT(1, "Ana", CPF, -1000, 0));
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioCLT(1, "Ana", CPF, 1000, -5));
    }
}
