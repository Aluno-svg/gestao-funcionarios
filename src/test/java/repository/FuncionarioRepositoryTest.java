package repository;

import static org.junit.jupiter.api.Assertions.*;
import model.Funcionario;
import model.FuncionarioCLT;
import model.FuncionarioPJ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioRepositoryTest {

    private FuncionarioRepository repo;
    private Funcionario ana;
    private Funcionario bruno;

    @BeforeEach
    void setUp() {
        repo = new FuncionarioRepository();
        ana = new FuncionarioCLT(1, "Ana", "529.982.247-25", 3000, 0);
        bruno = new FuncionarioPJ(2, "Bruno", "111.444.777-35", 100, 1);
        repo.salvar(ana);
        repo.salvar(bruno);
    }

    @Test
    void salvaEBuscaPorCodigo() {
        assertTrue(repo.existe(1));
        assertSame(ana, repo.buscarPorCodigo(1).orElseThrow());
        assertTrue(repo.buscarPorCodigo(99).isEmpty());
    }

    @Test
    void buscaPorCpfComESemMascara() {
        assertSame(bruno, repo.buscarPorCpf("111.444.777-35").orElseThrow());
        assertSame(bruno, repo.buscarPorCpf("11144477735").orElseThrow());
        assertTrue(repo.buscarPorCpf("123.456.789-09").isEmpty());
    }

    @Test
    void listaNaOrdemDeInsercao() {
        assertEquals(2, repo.listar().size());
        assertSame(ana, repo.listar().get(0));
    }

    @Test
    void removeExistenteEInformaInexistente() {
        assertTrue(repo.remover(1));
        assertFalse(repo.existe(1));
        assertFalse(repo.remover(1));
    }
}
