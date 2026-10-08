package ui;

import carros.Carro;
import clientes.Cliente;
import documentos.Contrato;
import exceptions.TipoSeguroInvalidoException;
import exportacao.GerarPdf;
import funcionarios.Funcionario;
import java.util.Scanner;
import locacoes.Locacao;
import persistencia.DadosSistema;
import persistencia.Persistencia;

public class MenuLocacao {
    private final Scanner sc = new Scanner(System.in);

    private DadosSistema dados;
    private Persistencia persistencia;
    private Funcionario funcionarioLogado;

    public MenuLocacao(DadosSistema dados, Persistencia persistencia, Funcionario funcionarioLogado){
        this.dados = dados;
        this.persistencia = persistencia;
        this.funcionarioLogado = funcionarioLogado;
    }

    public void cadastrar() throws TipoSeguroInvalidoException{
        System.out.println("""
                ==============================
                    NOVA LOCAÇÃO
                ==============================
                """);

        Cliente cliente = escolherCliente();

        if(cliente == null){
            return;
        }

        Carro carro = escolherCarro();

        if(carro == null){
            return;
        }

        System.out.print("Data retirada (dd/MM/yyyy): ");
        String retirada = sc.nextLine();

        System.out.print("Data prevista devolução (dd/MM/yyyy): ");
        String devolucao = sc.nextLine();

        System.out.println("""
                    Seguro:

                    1 - Básico | R$""" + carro.calcularSeguro("Básico") + "\n" +
                    "2 - Intermediário | R$" + carro.calcularSeguro("Intermediário") + "\n" +
                    "3 - Premium | R$" + carro.calcularSeguro("Premium")+ "\n");

        String seguro;

        int opcaoSeguro = Integer.parseInt(sc.nextLine());

        switch(opcaoSeguro){
            case 1:
                seguro = "Basico";
                break;
            case 2:
                seguro = "Intermediário";
                break;
            case 3:
                seguro = "Premium";
                break;
            default:
                seguro = "Basico";
        }

        Locacao locacao = new Locacao(dados.gerarIdLocacao(), cliente, carro, funcionarioLogado,
                retirada, devolucao, seguro);
        try{
            locacao.aceiteLocacao();
        }
        catch(Exception e){
            System.out.println("Não foi possível realizar a locação: " + e.getMessage());
            return;
        }

        dados.adicionarLocacao(locacao);

        Contrato contrato = new Contrato(dados.gerarIdContrato(), locacao);

        contrato.marcarComoAssinado();

        dados.adicionarContrato(contrato);

        persistencia.salvar(dados);

        System.out.println();
        System.out.println("Locação realizada com sucesso!");
        System.out.println("Total a pagar inicialmente: " + locacao.getValorTotal());
        System.out.println("Contrato número: " + contrato.getNumeroContrato());

        System.out.println("Gerando PDF do contrato...");

        GerarPdf pdfContrato = new GerarPdf();

        pdfContrato.gerar(contrato);

        System.out.println();
    }

    private Cliente escolherCliente(){
        if(dados.getClientes().isEmpty()){
            System.out.println("Não existem clientes cadastrados.");
            return null;
        }

        System.out.println("Clientes:");

        for(Cliente cliente : dados.getClientes()){
            System.out.println(cliente.getIdCliente() + " - " + cliente.getNome());
        }

        while(true){
            try{
                System.out.print("Escolha o cliente: ");

                int id = Integer.parseInt(sc.nextLine());

                for(Cliente cliente : dados.getClientes()){
                    if(cliente.getIdCliente() == id){
                        return cliente;
                    }
                }

                System.out.println("Cliente não encontrado.");
            }
            catch(NumberFormatException e){
                System.out.println("Digite um número.");
            }
        }
    }

    private Carro escolherCarro(){
        System.out.println();

        System.out.println("Carros disponíveis:");
                
        boolean existeCarro = false;

        for(Carro carro : dados.getCarros()){
            if(carro.getDisp()){
                existeCarro = true;
                System.out.println(carro.getIdCarro() + " - " + carro.getMarca().getMarca() + " " + carro.getModelo() + " | " 
                + carro.getPlaca());
            }
        }

        if(!existeCarro){
            System.out.println("Não existem carros disponíveis no momento.");
            return null;
        }

        while(true){
            try{
                System.out.print("Escolha o carro: ");
                int id = Integer.parseInt(sc.nextLine());

                for(Carro carro : dados.getCarros()){
                    if(carro.getIdCarro() == id && carro.getDisp()){
                        return carro;
                    }
                }

                System.out.println("Carro inválido.");
            }
            catch(NumberFormatException e){
                System.out.println("Digite um número.");

            }
        }
    }
}