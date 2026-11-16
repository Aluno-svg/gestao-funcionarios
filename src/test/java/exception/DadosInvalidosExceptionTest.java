package exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DadosInvalidosExceptionTest {

    @Test
    void guardaMensagemEEhIllegalArgument() {
        DadosInvalidosException e = new DadosInvalidosException("inválido");
        assertEquals("inválido", e.getMessage());
        assertInstanceOf(IllegalArgumentException.class, e);
    }
}
