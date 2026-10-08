package ui;

import java.util.Scanner;
import locacoes.Locacao;
import persistencia.DadosSistema;

public class MenuHistorico {
    private final Scanner sc = new Scanner(System.in);

    private DadosSistema dados;

    public MenuHistorico(DadosSistema dados){
        this.dados = dados;
    }

    public void mostrar(){
        System.out.println("""
                ==============================
                    HISTÓRICO DE LOCAÇÕES
                ==============================
                """);

        boolean encontrou = false;

        for(Locacao locacao : dados.getLocacoes()){
            if(locacao.getStatus().equals("Finalizado")){
                encontrou = true;
                System.out.println("""
                        
                        ------------------------------
                                    LOCAÇÃO
                        ------------------------------
                        """);

                System.out.println( "Número: "+ locacao.getIdLocacao());
                System.out.println("Cliente: "+ locacao.getCliente().getNome());
                System.out.println("CNH: "+ locacao.getCliente().getCnh().getCnhRegist());
                System.out.println("Carro: "+ locacao.getCarro().getModelo());
                System.out.println("Placa: "+ locacao.getCarro().getPlaca());
                System.out.println("Funcionário: "+ locacao.getFuncionario().getNome());
                System.out.println("Valor pago: R$ "+ locacao.getValorTotal());
            }
        }
        if(!encontrou){
            System.out.println("Nenhuma locação finalizada encontrada.");
        }
        System.out.println();
        System.out.println("Pressione ENTER para voltar.");
        sc.nextLine();
    }

}