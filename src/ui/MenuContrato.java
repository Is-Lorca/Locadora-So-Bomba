package ui;

import documentos.Contrato;
import exportacao.GerarPdf;
import java.util.Scanner;
import persistencia.DadosSistema;

public class MenuContrato {
    Scanner sc = new Scanner(System.in);

    private DadosSistema dados;

    public MenuContrato(DadosSistema dados){
        this.dados = dados;
    }

    public void reimprimirContrato(){
        System.out.println("""
                ==============================
                    REIMPRIMIR CONTRATO
                ==============================
                """);
        System.out.print("Número do contrato: ");

        int numero;

        try{
            numero = Integer.parseInt(sc.nextLine());
        }
        catch(NumberFormatException e){
            System.out.println("Número inválido.");
            return;
        }

        Contrato encontrado = null;

        for(Contrato contrato : dados.getContratos()){
            if(contrato.getNumeroContrato() == numero){
                encontrado = contrato;
                break;
            }
        }

        if(encontrado == null){
            System.out.println("Contrato não encontrado.");
            return;
        }

        System.out.println("\nContrato encontrado:");
        System.out.println(encontrado);

        System.out.println("""
                Deseja gerar novamente o PDF?
                1 - Sim
                0 - Voltar

                """);
        int opcao = Integer.parseInt(sc.nextLine());

        if(opcao == 1){
            GerarPdf pdf = new GerarPdf();

            pdf.gerar(encontrado);
            System.out.println("Contrato reimpresso!");
        }
    }
}