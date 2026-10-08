package carros;

import exceptions.TipoSeguroInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Popular extends Carro{
    private double manutencao = 1500.00; // a cada 12 meses
    private double revisao = 550.00; // revisões comuns (troca de oleo, etc) -> a cada 10.000 km
    private double tarifaBase = 90.00;

    public Popular(
        int id, String placa, Marca mrc, String mod, int ano, String cor, int km, boolean disp, String cidAt, 
        String ultManut, int ultRevis){
            super(id, placa, mrc, mod, ano, cor, km, disp, cidAt, ultManut, ultRevis);
            this.setTipo();
    }

    public double getTarifaBase(){
        return tarifaBase;
    }
    @Override 
    public void setTipo(){
        this.setTipo("Popular");;
    }
    
    // Importante pontuar as regras de negocio aqui:
    /* Para calcular a diaria usamos (trabalhamos com quilometragem livre):
        1. Dias de aluguel;
        2. Seguros contra roubo, etc -> existem tipos;
        3. Taxa fixa de cada tipo de carro (todos os sedans, populares e SUVs possuem a mesma taxa);
            Acrescimos:
            1. Se o cliente não abasteceu o carro antes de entregar, cobramos uma taxa de combustivel;
            2. Se o cliente entregou em uma cidade diferente;
            3. Se o cliente tiver entre 18 e 25 anos ou carteira de habilitação < 2 anos;
            4. Se o cliente passou o tempo para a entrega do carro.
        Logo, obtemos a seguinte formula:
        Total = (taxaBase x dias) + seguro (+ 
                    TaxaCombst. + TaxaOutraCidade + TaxaIdadeCNH + (12% da taxaBase * (POR HORA de atraso - 1hr de tolerancia))
    */
    @Override 
    public double calcularDiaria(int dias){
        return tarifaBase * dias;
    }

    // Tipos de seguro: Básico, Intermediario, Premium
    /* 
        Basico cobre: Danos ao veículo (batidas, roubo, furto);
        Intermediario: Danos ao veiculo + pessoais causados a outras pessoas;
        Premium: Todos acima + incluisão dos vidros, pneus e assistência 24h;
    */ 
    @Override 
    public double calcularSeguro(String seguro) throws TipoSeguroInvalidoException{
        char letra = seguro.toLowerCase().charAt(0);

        if(letra == 'b'){ // básico
            return 25.00;
        }
        else if (letra == 'i') { // intermediario
            return 35.00;
        }
        else if (letra == 'p'){ // premium
            return 60.00;
        }
        else{
            // Lidamos com o erro no Main
            throw new TipoSeguroInvalidoException();
        }
    }

    @Override 
    public double taxaHoraAtraso(){
        int porcentagem = 12;
        return (getTarifaBase() * porcentagem) / 100;
    }

    @Override 
    public void precisaManutencao(){
        // checando se precisa de revisão
        if(getPrecisaRevisao()){
            System.out.printf("O carro %s da placa %s precisa de revisão.\n", getModelo(), getPlaca());
        }
        else{
            System.out.println("O carro esta com a revisão em dia.");
        }

        // checando se precisa de manutenção
        if(getUltManutencao() != null){
            // long = usado para guardar numero inteiro de 64 bits, ChronoUnit retorna long 
            long meses = ChronoUnit.MONTHS.between(getUltManutencao(), LocalDate.now());
            if (meses >= 12){
                System.out.printf("O carro %s da placa %s precisa de manutenção.\n", getModelo(), getPlaca());
            }
            else{
                System.out.println("O carro está com a manutenção em dia.");
            }
        }
        else{
            System.out.println("O carro está com a manutenção em dia.");
        }
    }

    @Override 
    public void manutencaoRealizada(String diaManutencao){
        DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate novaData = LocalDate.parse(diaManutencao, formatacao);
        
        if (getUltManutencao() != null && getUltManutencao().equals(novaData)) {
            System.out.println("Data inserida e data da ultima manutenção são iguais, data não atualizada.");
        }
        else {
            setUltManutencao(diaManutencao);
        }
    }

    @Override 
    public void revisaoRealizada(int quilometragem){
        setPrecisaRevisao(false);
        setUltRevisao(quilometragem);
    }

    // Subscrevendo método toString
    @Override 
    public String toString(){
        return """
        
               Carro
               Tipo: Popular
               Placa: """ + getPlaca() + "\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Ano: " + getAno() + "\n" +
               "Tarifa base: " + getTarifaBase() + "\n" +
               "Cor: " + getCor() + "\n" +
               "Quilometragem: " + getQuilom() + "\n" +
               "Cidade atual: " + getCidadeAt() + "\n" +
               "Disponivel: " + (getDisp() ? "Sim": "Não");
    }
}
