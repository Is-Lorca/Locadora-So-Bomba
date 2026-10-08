package ui;

import java.util.Scanner;
import locacoes.Locacao;
import persistencia.DadosSistema;
import persistencia.Persistencia;


public class MenuFinalizarLocacao {
    private final Scanner sc = new Scanner(System.in);

    private DadosSistema dados;
    private Persistencia persistencia;

    public MenuFinalizarLocacao(DadosSistema dados,Persistencia persistencia){
        this.dados = dados;
        this.persistencia = persistencia;
    }

    public void finalizar(){
        System.out.println("""
                ==============================
                      FINALIZAR LOCAÇÃO
                ==============================
                """);

        Locacao locacaoEscolhida = escolherLocacao();

        if(locacaoEscolhida == null){
            return;
        }

        System.out.println();
        System.out.println("Cliente: "+ locacaoEscolhida.getCliente().getNome());
        System.out.println("Carro: "+ locacaoEscolhida.getCarro().getModelo()
            + " - "+ locacaoEscolhida.getCarro().getPlaca());

        int kmDevolucao;

        while(true){
            try{
                System.out.print("Quilometragem atual: ");
                kmDevolucao =Integer.parseInt(sc.nextLine());
                break;
            }
            catch(NumberFormatException e){
                System.out.println("Digite um número válido.");
            }
        }

        System.out.print("Data devolução (dd/MM/yyyy): ");
        String dataDevolucao = sc.nextLine();

        System.out.print("Abasteceu o veículo? (S/N): ");
        boolean abasteceu = sc.nextLine().equalsIgnoreCase("S");

        System.out.print("Cidade de entrega: ");
        String cidade = sc.nextLine();

        boolean atraso = !dataDevolucao.equals(locacaoEscolhida.getStringDataPrevista());

        locacaoEscolhida.finalizarLocacao(kmDevolucao, atraso, dataDevolucao, abasteceu, cidade);

        persistencia.salvar(dados);

        System.out.println();
        System.out.println("Locação finalizada com sucesso!");
        System.out.println("Valor final: R$ "+ locacaoEscolhida.getValorTotal());
    }

    private Locacao escolherLocacao(){
        boolean encontrou = false;

        System.out.println("Locações em andamento:");

        for(Locacao locacao : dados.getLocacoes()){
            if(locacao.getStatus().equals("Em Vigor")){
                encontrou = true;

                System.out.println(locacao.getIdLocacao() + " - " + locacao.getCliente().getNome() 
                    + " | " + locacao.getCarro().getModelo());
            }
        }

        if(!encontrou){
            System.out.println("Não existem locações abertas.");
            return null;
        }

        while(true){
            try{
                System.out.print("Número da locação: ");
                int id = Integer.parseInt(sc.nextLine());

                for(Locacao locacao : dados.getLocacoes()){

                    if(locacao.getIdLocacao() == id && locacao.getStatus().equals("Em Vigor")){
                        return locacao;
                    }
                }
                System.out.println("Locação inválida.");
            }
            catch(NumberFormatException e){
                System.out.println("Digite um número.");
            }
        }
    }
}