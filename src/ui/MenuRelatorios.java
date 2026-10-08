package ui;

import documentos.RelatorioFinanceiro;
import documentos.RelatorioFrota;
import documentos.RelatorioLocacao;
import exportacao.GerarPdf;
import java.time.LocalDate;
import java.util.Scanner;
import persistencia.DadosSistema;

public class MenuRelatorios {
    private Scanner sc;
    private DadosSistema dados;

    public MenuRelatorios(DadosSistema dados){
        this.dados = dados;
        this.sc = new Scanner(System.in);
    }

    public void abrir(){
        int escolha = -1;

        while(escolha != 0){
            System.out.println("""
                    
                    ==============================
                            RELATÓRIOS
                    ==============================
                    
                    1 - Relatório Financeiro
                    2 - Relatório Frota
                    3 - Relatório Locações
                    
                    0 - Voltar
                    
                    """);

            try{
                escolha = Integer.parseInt(sc.nextLine());
            }
            catch(NumberFormatException e){
                escolha = -1;

            }

            switch(escolha){
                case 1:
                    relatorioFinanceiro();
                    break;

                case 2:
                    relatorioFrota();
                    break;

                case 3:
                    relatorioLocacoes();
                    break;

                case 0:
                    System.out.println("Voltando...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void relatorioFinanceiro(){
        System.out.println("""
                
                Gerando relatório financeiro...
                
                """);

        RelatorioFinanceiro relatorio = new RelatorioFinanceiro(LocalDate.now(), dados.getLocacoes(),
                    String.valueOf(LocalDate.now().getYear()));

        finalizarRelatorio(relatorio);
    }

    private void relatorioFrota(){
        System.out.println("""
                
                Gerando relatório da frota...
                
                """);

        RelatorioFrota relatorio = new RelatorioFrota(LocalDate.now(), dados.getCarros());

        finalizarRelatorio(relatorio);
    }

    private void relatorioLocacoes(){

        System.out.println("""
                Informe o período
                """);


        System.out.print("Data inicial (dd/MM/yyyy): ");
        String inicial = sc.nextLine();

        System.out.print("Data final (dd/MM/yyyy): ");
        String finalData = sc.nextLine();

        RelatorioLocacao relatorio = new RelatorioLocacao(LocalDate.now(),
                inicial,finalData,dados.getLocacoes());

        finalizarRelatorio(relatorio);
    }

    private void finalizarRelatorio(documentos.Impressao relatorio){

        System.out.println("""  
                ==============================
                      RELATÓRIO GERADO
                ==============================

                """);

        System.out.println(relatorio.gerarConteudo());

        System.out.println("""
                
                -------------------------------
                        Criando PDF...
                -------------------------------
                
                """);

        GerarPdf pdf = new GerarPdf();
        pdf.gerar(relatorio);

        System.out.println("PDF criado com sucesso!");
    }
}