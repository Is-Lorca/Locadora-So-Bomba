package carros;

import exceptions.TipoSeguroInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Suv extends Carro{
    private double manutencao = 3500.00; // a cada 12 meses
    private double revisao = 850.00; // troca de oleo, etc -> a cada 10.000km
    private double tarifaBase = 300.00;
    private String tipo = "SUV";

    public Suv(
        String placa, Marca mrc, String mod, int ano, String cor, int km, boolean disp, String cidAt, 
        String ultManut, int ultRevis, String seguro){
            super(placa, mrc, mod, ano, cor, km, disp, cidAt, ultManut, ultRevis, seguro);
    }

    public double getTarifaBase(){
        return tarifaBase;
    }

    @Override 
    public double calcularDiaria(int dias){
        return tarifaBase * dias;
    }

    @Override 
    public double calcularSeguro() throws TipoSeguroInvalidoException{
        if(getSeguro() == null){
            throw new TipoSeguroInvalidoException("O tipo não pode ser nulo.");
        }

        if(getSeguro().equals("Basico")){
            return 105.00;
        }
        else if (getSeguro().equals("Intermediario")) {
            return 115.00;
        }
        else if (getSeguro().equals("Premium")){
            return 140.00;
        }
        else{
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
               Tipo: SUV
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
