package ui;

import funcionarios.Funcionario;
import funcionarios.Login;
import java.util.Scanner;
import persistencia.DadosSistema;
import persistencia.Persistencia;

public class MenuLogin {
    private DadosSistema dados;
    private Persistencia persistencia;
    private Scanner sc;

    public MenuLogin(DadosSistema dados, Persistencia persistencia) {
        this.dados = dados;
        this.persistencia = persistencia;
        this.sc = new Scanner(System.in);
    }

    public Funcionario entrarLogin() {
        int tentativas = 0;

        while (tentativas < 3) {

            System.out.println("""
                    ==============================
                              LOGIN
                    ==============================
                    """);

            int usuario;
            int senha;

            try {

                System.out.print("Usuário: ");
                usuario = Integer.parseInt(sc.nextLine().trim());

                System.out.print("Senha: ");
                senha = Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Usuário e senha devem conter apenas números.");
                System.out.println();

                continue;
            }

            for (Funcionario funcionario : dados.getFuncionarios()) {

                Login login = funcionario.getFuncLogin();

                if (login == null) {
                    continue;
                }
                
                if (login.getUsuario() == usuario &&
                        login.getSenha() == senha) {

                    System.out.println();
                    System.out.println("Bem-vindo(a), " + funcionario.getNome() + "!");
                    System.out.println();

                    return funcionario;
                }

            }

            tentativas++;

            System.out.println();
            System.out.println("Usuário ou senha incorretos.");
            System.out.println("Tentativa " + tentativas + " de 3.");
            System.out.println();

        }

        System.out.println("Número máximo de tentativas atingido.");
        System.out.println("Sistema encerrado.");

        return null;
    }

    public void esqueciSenha() {

        int tentativas = 0;

        while (tentativas < 3) {

            System.out.println("""
                    ==============================
                            RECUPERAR SENHA
                    ==============================
                    """);

            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            int usuario;

            try {

                System.out.print("Usuário: ");
                usuario = Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Usuário inválido.");
                System.out.println();

                continue;
            }

            for (Funcionario funcionario : dados.getFuncionarios()) {

                Login login = funcionario.getFuncLogin();

                if (login == null) {
                    continue;
                }

                if (funcionario.getCpf().equals(cpf)
                        && login.getUsuario() == usuario) {

                    System.out.print("Nova senha: ");

                    try {

                        int novaSenha = Integer.parseInt(sc.nextLine().trim());

                        login.setSenha(novaSenha);

                        persistencia.salvar(dados);

                        System.out.println();
                        System.out.println("Senha alterada com sucesso.");
                        System.out.println();

                        return;

                    } catch (NumberFormatException e) {

                        System.out.println("Senha inválida.");
                        System.out.println();

                        continue;
                    }

                }

            }

            tentativas++;

            System.out.println();
            System.out.println("Dados não encontrados.");
            System.out.println("Tentativa " + tentativas + " de 3.");
            System.out.println();

        }

        System.out.println("Número máximo de tentativas atingido.");
        System.out.println("Sistema encerrado.");

        System.exit(0);

    }

}