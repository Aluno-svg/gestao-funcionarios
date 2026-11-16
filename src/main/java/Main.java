import repository.FuncionarioRepository;
import service.FuncionarioService;
import view.FuncionarioView;

public class Main {
    public static void main(String[] args) {
        FuncionarioService service = new FuncionarioService(new FuncionarioRepository());
        new FuncionarioView(service).iniciar();
    }
}
