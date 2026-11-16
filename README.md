# Sistema de Gerenciamento de Funcionários e Folha de Pagamento

Projeto Java 17 com Maven e JUnit 5, desenvolvido com POO em camadas.

## Estrutura
- `model`: `Funcionario` (abstrata), `FuncionarioCLT`, `FuncionarioPJ`, `FuncionarioComissionado`, `Tributavel`, `Identificavel`, `ValidadorCpf`
- `repository`: `FuncionarioRepository` (armazenamento em memória)
- `service`: `FuncionarioService` (regras de negócio; sobrecarga de `buscarFuncionario`)
- `exception`: `FuncionarioNaoEncontradoException`, `FuncionarioJaCadastradoException`, `DadosInvalidosException`
- `view`: `FuncionarioView` (menu de console)

## Regras de remuneração
- **CLT**: salário-base + benefícios − tributos (11% do salário-base)
- **PJ**: valor por projeto × quantidade de projetos (sem tributos)
- **Comissionado**: (salário-base + comissão) − tributos (11% sobre o bruto)

## Como executar
```bash
mvn compile exec:java -Dexec.mainClass=Main
```
Ou rode a classe `Main` pela IDE.

## Como executar os testes
```bash
mvn test
```

## TDD
Cada classe de produção tem sua classe de teste. Os commits devem seguir o ciclo Red, Green e Refactor.
