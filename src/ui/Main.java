package ui;

import carros.Carro;
import carros.Marca;
import carros.Popular;
import exceptions.TipoSeguroInvalidoException;

public class Main {
    public static void main(String[] args) throws TipoSeguroInvalidoException{
        Marca mc = new Marca("Volkswagen");
        Carro carro = new Popular(
            "123A", mc, "Gol G5", 2012, "Vermelho", 0, true, "Oriente", "03/10/2024", 0);
        
        // Checar cnh e cliente -> ver se funcionam

        /* 
        Faltam:
        - Funcionario;
        - Locação (muito importante);
        - Exception -> VeiculoIndisponivelException e CNHVencidaException
        - Interface Impressao;
        - Classe de Persistencia;
        */

        // Scanner scanner = new Scanner(System.in);
        // Carros[] cars = new Carros[3];
        // LinkedList<Marca> marcas = new LinkedList<>(); //LinkedList <Tipo de coisas> nomeVar = nova 
        //                                                // LinkedList<> -> para ter mais flexibilidade 
        //                                                // deixamos vazio, generico();
        // // Marca[] marcas = new Marca[3];
        // int posAtualCarros = 0; // Aqui são os indices das arrays

        // int opt = -1;
        // while(opt != 0){
        //     System.out.println("==== Bem-vindo a locadora Só Bomba ====");
        //     System.out.println("|       Digite a opção desejada       |");
        //     System.out.println("|         1 - Cadastrar Marca         |");
        //     System.out.println("|         2 - Cadastrar Carro         |");
        //     System.out.println("|          3 - Listar Carros          |");
        //     System.out.println("|          4 - Listar Marcas          |");
        //     System.out.println("|              0 - Sair               |");
        //     try{
        //         opt = Integer.parseInt(scanner.nextLine());
        //     } catch (NumberFormatException ne){
        //         System.err.println("Erro! Favor inserir um número!");
        //         scanner.nextLine();
        //     }
        //     switch (opt) {
        //         case 1:
        //             System.out.println("\nÓtimo, criaremos a marca então. ");
        //             System.out.println("Digite o nome da marca: ");
        //             String nome = scanner.nextLine();
        //             Marca m = new Marca(nome);
        //             marcas.add(m);
        //             break;
        //         case 2:
        //             System.out.println("\nDigite o nome da marca: ");
        //             break;
        //         case 3:
        //             System.out.println("\nDigite o nome da marca: ");
        //             break;
        //         case 4:
        //             System.out.println("\nSão as marcas já cadastradas: ");
        //             int pNaLista = 0;
        //             for (Marca ma : marcas) {
        //                 System.out.println("Código: " + Integer.toString(pNaLista));
        //                 System.out.println(ma);
        //                 System.out.println("==================\n");
        //                 pNaLista += 1;
        //             }
        //             break;
        //         case 0:
        //             System.out.println("\nAté a próxima!");
        //             break;
        //         default:
        //             System.out.println("\nEscreva uma opção válida");;
        //             break;
        //     }
        // }
    }
}