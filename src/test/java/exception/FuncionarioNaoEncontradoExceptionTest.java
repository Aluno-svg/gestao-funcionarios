package exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FuncionarioNaoEncontradoExceptionTest {

    @Test
    void guardaMensagemEEhExceptionChecada() {
        FuncionarioNaoEncontradoException e = new FuncionarioNaoEncontradoException("sumiu");
        assertEquals("sumiu", e.getMessage());
        assertFalse(RuntimeException.class.isAssignableFrom(FuncionarioNaoEncontradoException.class));
    }

    @Test
    void podeSerLancada() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> {
            throw new FuncionarioNaoEncontradoException("x");
        });
    }
}
