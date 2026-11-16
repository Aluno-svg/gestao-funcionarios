package model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class FuncionarioTest {

    @Test
    void comportamentoPolimorficoExecutaImplementacaoDoObjeto() {
        Funcionario clt = new FuncionarioCLT(1, "Ana", "529.982.247-25", 3000, 500);
        Funcionario pj = new FuncionarioPJ(2, "Bruno", "111.444.777-35", 1500, 4);
        Funcionario com = new FuncionarioComissionado(3, "Carla", "123.456.789-09", 2000, 10, 10000);

        assertEquals(3170.0, clt.calcularRemuneracao(), 0.001);
        assertEquals(6000.0, pj.calcularRemuneracao(), 0.001);
        assertEquals(2670.0, com.calcularRemuneracao(), 0.001);
    }

    @Test
    void todasAsSubclassesSaoIdentificaveis() {
        List<Funcionario> lista = List.of(
                new FuncionarioCLT(1, "Ana", "529.982.247-25", 3000, 0),
                new FuncionarioPJ(2, "Bruno", "111.444.777-35", 100, 1),
                new FuncionarioComissionado(3, "Carla", "123.456.789-09", 1000, 5, 0));
        for (Funcionario f : lista) {
            Identificavel i = f;
            assertEquals(f.getCodigo(), i.getCodigo());
        }
    }

    @Test
    void cltEComissionadoSaoTributaveis() {
        Object clt = new FuncionarioCLT(1, "Ana", "529.982.247-25", 3000, 0);
        Object com = new FuncionarioComissionado(3, "Carla", "123.456.789-09", 1000, 5, 0);
        assertTrue(clt instanceof Tributavel);
        assertTrue(com instanceof Tributavel);
    }

    @Test
    void cpfEhArmazenadoSemMascaraENomeSemEspacos() {
        Funcionario f = new FuncionarioCLT(1, "  Ana  ", "529.982.247-25", 3000, 0);
        assertEquals("52998224725", f.getCpf());
        assertEquals("Ana", f.getNome());
    }

    @Test
    void exibirDadosContemInformacoesPrincipais() {
        String texto = new FuncionarioPJ(2, "Bruno", "111.444.777-35", 1500, 4).exibirDados();
        assertTrue(texto.contains("Bruno"));
        assertTrue(texto.contains("FuncionarioPJ"));
        assertTrue(texto.contains("11144477735"));
    }
}
