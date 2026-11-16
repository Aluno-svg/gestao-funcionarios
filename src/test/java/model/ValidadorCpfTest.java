package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ValidadorCpfTest {

    @Test
    void aceitaCpfValidoComESemMascara() {
        assertTrue(ValidadorCpf.valido("529.982.247-25"));
        assertTrue(ValidadorCpf.valido("52998224725"));
    }

    @Test
    void rejeitaCpfInvalido() {
        assertFalse(ValidadorCpf.valido("529.982.247-24"));
        assertFalse(ValidadorCpf.valido("111.111.111-11"));
        assertFalse(ValidadorCpf.valido("123"));
        assertFalse(ValidadorCpf.valido(null));
    }

    @Test
    void normalizaRemovendoNaoDigitos() {
        assertEquals("52998224725", ValidadorCpf.normalizar("529.982.247-25"));
        assertEquals("", ValidadorCpf.normalizar(null));
    }
}
