package ui;

import exceptions.TipoSeguroInvalidoException;
import funcionarios.Funcionario;
import java.util.Scanner;
import persistencia.DadosSistema;
import persistencia.Persistencia;

public class MenuPrincipal {

    private final Scanner sc = new Scanner(System.in);

    private Funcionario funcionarioLogado;
    private DadosSistema dados;
    private Persistencia persistencia;

    public MenuPrincipal(DadosSistema dados, Persistencia persistencia, Funcionario funcionarioLogado){
        this.dados = dados;
        this.persistencia = persistencia;
        this.funcionarioLogado = funcionarioLogado;
    }

    public void abrir() throws TipoSeguroInvalidoException{

        int opcao = -1;

        while(opcao != 0){

            System.out.println("""
                    ==============================
                         LOCADORA SÓ BOMBA
                    ==============================

                    1 - Cadastrar Cliente
                    2 - Cadastrar Carro
                    3 - Cadastrar Locação
                    4 - Finalizar Locação
                    5 - Reimprimir Contrato
                    6 - Histórico
                    7 - Relatórios
                    0 - Sair
                    """);

            try{
                System.out.print("Escolha: ");
                opcao = Integer.parseInt(sc.nextLine().trim());
            }
            catch(NumberFormatException e){
                System.out.println("Digite apenas números.");
                continue;
            }

            switch(opcao){
                case 1:
                    cadastrarCliente();
                    break;

                case 2:
                    cadastrarCarro();
                    break;

                case 3:
                    cadastrarLocacao();
                    break;

                case 4:
                    finalizarLocacoes();
                    break;

                case 5:
                    reimprimirContrato();
                    break;

                case 6:
                    historicoLocacoes();
                    break;

                case 7:
                    menuRelatorios();
                    break;

                case 0:
                    persistencia.salvar(dados);
                    System.out.println("Dados salvos.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarCliente(){
        MenuCliente menu = new MenuCliente(dados, persistencia);
        menu.cadastrar();
    }
    private void cadastrarCarro(){
        MenuCarro menu = new MenuCarro(dados, persistencia);
        menu.cadastrar();
    }
    private void cadastrarLocacao() throws TipoSeguroInvalidoException{
        MenuLocacao menu = new MenuLocacao( dados, persistencia, funcionarioLogado);
        menu.cadastrar();
    }
    private void finalizarLocacoes(){
        MenuFinalizarLocacao menu = new MenuFinalizarLocacao(dados, persistencia);
        menu.finalizar();
    }
    private void reimprimirContrato(){
        MenuContrato menu = new MenuContrato(dados);
        menu.reimprimirContrato();
    }
    private void historicoLocacoes(){
        MenuHistorico menu = new MenuHistorico(dados);
        menu.mostrar();
    }
    private void menuRelatorios(){
        MenuRelatorios menu = new MenuRelatorios(dados);
        menu.abrir();
    }

}