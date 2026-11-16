package exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FuncionarioJaCadastradoExceptionTest {

    @Test
    void guardaMensagemEEhExceptionChecada() {
        FuncionarioJaCadastradoException e = new FuncionarioJaCadastradoException("duplicado");
        assertEquals("duplicado", e.getMessage());
        assertFalse(RuntimeException.class.isAssignableFrom(FuncionarioJaCadastradoException.class));
    }

    @Test
    void podeSerLancada() {
        assertThrows(FuncionarioJaCadastradoException.class, () -> {
            throw new FuncionarioJaCadastradoException("x");
        });
    }
}
