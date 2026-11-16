package service;

import static org.junit.jupiter.api.Assertions.*;
import exception.DadosInvalidosException;
import exception.FuncionarioJaCadastradoException;
import exception.FuncionarioNaoEncontradoException;
import model.FuncionarioCLT;
import model.FuncionarioComissionado;
import model.FuncionarioPJ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.FuncionarioRepository;

class FuncionarioServiceTest {

    private FuncionarioService service;

    @BeforeEach
    void setUp() throws Exception {
        service = new FuncionarioService(new FuncionarioRepository());
        service.cadastrar(new FuncionarioCLT(1, "Ana Souza", "529.982.247-25", 3000, 500));
        service.cadastrar(new FuncionarioPJ(2, "Bruno Lima", "111.444.777-35", 1500, 4));
        service.cadastrar(new FuncionarioComissionado(3, "Carla Dias", "123.456.789-09", 2000, 10, 10000));
    }

    @Test
    void cadastraEListaFuncionarios() {
        assertEquals(3, service.listar().size());
    }

    @Test
    void rejeitaCodigoDuplicado() {
        assertThrows(FuncionarioJaCadastradoException.class, () ->
                service.cadastrar(new FuncionarioPJ(1, "Outro", "987.654.321-00", 10, 1)));
    }

    @Test
    void rejeitaCpfDuplicado() {
        assertThrows(FuncionarioJaCadastradoException.class, () ->
                service.cadastrar(new FuncionarioPJ(10, "Outro", "529.982.247-25", 10, 1)));
    }

    @Test
    void rejeitaFuncionarioNulo() {
        assertThrows(DadosInvalidosException.class, () -> service.cadastrar(null));
    }

    @Test
    void buscaPorCodigo() throws Exception {
        assertEquals("Ana Souza", service.buscarFuncionario(1).getNome());
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario(99));
    }

    @Test
    void buscaPorCpf() throws Exception {
        assertEquals("Bruno Lima", service.buscarFuncionario("111.444.777-35").getNome());
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("000.000.000-00"));
        assertThrows(DadosInvalidosException.class, () -> service.buscarFuncionario(" "));
    }

    @Test
    void buscaPorNomeExatoEParcial() throws Exception {
        assertEquals(1, service.buscarFuncionario("ana souza", true).size());
        assertEquals(1, service.buscarFuncionario("carla", false).size());
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("ana", true));
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("zzz", false));
    }

    @Test
    void removeFuncionario() throws Exception {
        service.remover(2);
        assertEquals(2, service.listar().size());
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.remover(2));
    }

    @Test
    void calculaTotalDaFolha() {
        assertEquals(3170.0 + 6000.0 + 2670.0, service.calcularTotalFolha(), 0.001);
    }

    @Test
    void geraRelatorioComTodosOsFuncionariosETotal() {
        String r = service.gerarRelatorio();
        assertTrue(r.contains("Ana Souza"));
        assertTrue(r.contains("Bruno Lima"));
        assertTrue(r.contains("Carla Dias"));
        assertTrue(r.contains("TOTAL DA FOLHA"));
    }

    @Test
    void repositorioNuloEhRejeitado() {
        assertThrows(DadosInvalidosException.class, () -> new FuncionarioService(null));
    }
}
