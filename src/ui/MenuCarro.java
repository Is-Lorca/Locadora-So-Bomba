package ui;

import carros.Carro;
import carros.Marca;
import carros.Popular;
import carros.Sedan;
import carros.Suv;
import java.util.Scanner;
import persistencia.DadosSistema;
import persistencia.Persistencia;

public class MenuCarro {

    private final Scanner sc = new Scanner(System.in);

    private DadosSistema dados;
    private Persistencia persistencia;

    public MenuCarro(DadosSistema dados, Persistencia persistencia){
        this.dados = dados;
        this.persistencia = persistencia;
    }

    public void cadastrar(){

        if(dados.getMarcas().isEmpty()){
            System.out.println("Não existem marcas cadastradas.");
            return;
        }

        System.out.println("""
                ==============================
                    CADASTRO DE CARRO
                ==============================
                """);

        System.out.println("Marcas cadastradas:");

        for(Marca marca : dados.getMarcas()){

            System.out.println(marca.getIdMarca() + " - " + marca.getMarca());

        }

        Marca marcaEscolhida = null;

        while(marcaEscolhida == null){

            try{

                System.out.print("ID da marca: ");
                int idMarca = Integer.parseInt(sc.nextLine());

                for(Marca marca : dados.getMarcas()){

                    if(marca.getIdMarca() == idMarca){

                        marcaEscolhida = marca;
                        break;

                    }

                }

                if(marcaEscolhida == null){

                    System.out.println("Marca inexistente.");

                }

            }
            catch(NumberFormatException e){

                System.out.println("Digite apenas números.");

            }

        }

        System.out.print("Placa: ");
        String placa = sc.nextLine();

        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        int ano;

        while(true){

            try{

                System.out.print("Ano: ");
                ano = Integer.parseInt(sc.nextLine());
                break;

            }
            catch(NumberFormatException e){

                System.out.println("Ano inválido.");

            }

        }

        System.out.print("Cor: ");
        String cor = sc.nextLine();

        int km;

        while(true){

            try{

                System.out.print("Quilometragem: ");
                km = Integer.parseInt(sc.nextLine());
                break;

            }
            catch(NumberFormatException e){

                System.out.println("Valor inválido.");

            }

        }

        System.out.print("Cidade atual: ");
        String cidade = sc.nextLine();

        int tipo;

        while(true){

            try{

                System.out.println();
                System.out.println("Tipo");

                System.out.println("1 - Popular");
                System.out.println("2 - Sedan");
                System.out.println("3 - SUV");

                tipo = Integer.parseInt(sc.nextLine());

                if(tipo >= 1 && tipo <= 3){

                    break;

                }

                System.out.println("Tipo inválido.");

            }
            catch(NumberFormatException e){

                System.out.println("Digite apenas números.");

            }

        }

        Carro carro = null;

        switch(tipo){

            case 1:
                carro = new Popular(dados.gerarIdCarro(), placa, marcaEscolhida, modelo, ano, cor, km,
                        true, cidade, null, 0);
                break;

            case 2:
                carro = new Sedan(dados.gerarIdCarro(), placa, marcaEscolhida, modelo, ano, cor, km,
                        true, cidade, null, 0);
                break;

            case 3:
                carro = new Suv(dados.gerarIdCarro(), placa, marcaEscolhida, modelo, ano, cor, km,
                        true, cidade, null, 0);
                break;

        }

        dados.adicionarCarro(carro);

        persistencia.salvar(dados);

        System.out.println();
        System.out.println("Carro cadastrado com sucesso!");
        System.out.println("ID: " + carro.getIdCarro());
        System.out.println();

    }

}