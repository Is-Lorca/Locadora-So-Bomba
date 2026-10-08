package ui;

import clientes.Cliente;
import clientes.Cnh;
import java.util.Scanner;
import persistencia.DadosSistema;
import persistencia.Persistencia;

public class MenuCliente {

    private final Scanner sc = new Scanner(System.in);

    private DadosSistema dados;
    private Persistencia persistencia;

    public MenuCliente(DadosSistema dados, Persistencia persistencia){
        this.dados = dados;
        this.persistencia = persistencia;
    }

    public void cadastrar(){

        System.out.println("""
                ==============================
                    CADASTRO DE CLIENTE
                ==============================
                """);

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        int idade;

        while(true){

            try{

                System.out.print("Idade: ");
                idade = Integer.parseInt(sc.nextLine());

                break;

            }catch(NumberFormatException e){

                System.out.println("Idade inválida.");

            }

        }

        System.out.println();

        System.out.println("===== CNH =====");

        System.out.print("Número de registro: ");
        String registro = sc.nextLine();

        System.out.print("Categoria: ");
        String categoria = sc.nextLine();

        System.out.print("Primeira habilitação (dd/MM/yyyy): ");
        String primeiraHab = sc.nextLine();

        System.out.print("Última renovação (ENTER caso nunca): ");
        String renovacao = sc.nextLine();

        if(renovacao.isBlank()){
            renovacao = null;
        }

        Cliente cliente = new Cliente(dados.gerarIdCliente(), nome, cpf, telefone, email, endereco, idade,
                null);

        Cnh cnh = new Cnh( registro, categoria, primeiraHab, renovacao);

        cliente.setCnh(cnh);

        dados.adicionarCliente(cliente);

        persistencia.salvar(dados);

        System.out.println();
        System.out.println("Cliente cadastrado com sucesso!");
        System.out.println("ID: " + cliente.getIdCliente());
        System.out.println();
    }

}