package ui;

import exceptions.TipoSeguroInvalidoException;
import funcionarios.Funcionario;
import funcionarios.Login;
import persistencia.DadosSistema;
import persistencia.Persistencia;

public class Main {

    public static void main(String[] args) throws TipoSeguroInvalidoException {
        // Cria a classe Persistencia
        Persistencia persistencia = new Persistencia();

        // Carrega os dados salvos
        DadosSistema dados = persistencia.carregar();

        // Carrega as marcas que nossa Locadora tem (funcionários não podem adicionar nem excluir)
        dados.carregarMarcasPadrao();

        // Religa todas as referências (Marca, Cliente, Carro, Funcionário...)
        dados.reconstruirReferencias();

        // Atualiza os próximos IDs
        dados.atualizarProximosIds();

        // Caso seja a primeira execução do programa, cria um funcionário administrador.
        if (dados.getFuncionarios().isEmpty()) {
            Funcionario admin = new Funcionario(dados.gerarIdFuncionario(), null,
                    "Isis Lorca",
                    "111.111.111-11",
                    "(00) 00000-0000"
            );
            Login adminLog = new Login(12345, 12345678);

            admin.setFuncLogin(adminLog);

            dados.adicionarFuncionario(admin);

            persistencia.salvar(dados);

            System.out.println("Administrador padrão criado.");
            System.out.println("Usuário: 12345");
            System.out.println("Senha: 12345678");
            System.out.println();
        }

        MenuLogin menuLogin = new MenuLogin(dados, persistencia);

        Funcionario funcionarioLogado = menuLogin.entrarLogin();

        if (funcionarioLogado != null) {

            MenuPrincipal menuPrincipal = new MenuPrincipal(dados, persistencia, funcionarioLogado);

            menuPrincipal.abrir();
        }

        System.out.println("Sistema encerrado.");
    }
}