package locacoes;

import carros.Carro;
import clientes.Cliente;
import exceptions.TipoSeguroInvalidoException;
import funcionarios.Funcionario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Locacao {
    private Cliente cliente;
    private Carro carro;
    private Funcionario funcionario;
    private LocalDate dataRetirada; // quando tirou o carro
    private LocalDate dataPrevista; // quando deve devolver
    private LocalDate dataDevolucaoReal;
    private int kmRetirada;
    private int kmDevolucao;
    private String status; // cancelado - finalizado - em vigor
    private double valorTotal;
    private boolean adicionalAtraso;

    public Locacao(Cliente cliente, Carro carro, Funcionario funcionario, String dataRetirada, String dataPrevista){
        this.setCliente(cliente);
        this.setCarro(carro);
        this.setFuncionario(funcionario);
        this.setDataRetirada(dataRetirada);
        this.setDataPrevista(dataPrevista);
        this.setDataDevolucao(null);
        this.setKmRetirada();
        this.setStatus("Em Vigor");
        this.setValorTotal(0.0);
        this.setAdAtraso(false);
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    public void setCarro(Carro carro){
        this.carro = carro;
    }
    public void setFuncionario(Funcionario funcionario){
        this.funcionario = funcionario;
    }
    public void setDataRetirada(String dataRetirada){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(dataRetirada, formata);
        this.dataRetirada = dataNova;
    }
    public void setDataPrevista(String dataPrevista){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(dataPrevista, formata);
        this.dataRetirada = dataNova;
    }
    public void setDataDevolucao(String dataDevolucao){
        if(dataDevolucao.equals(null)){
            DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNova = LocalDate.parse(dataDevolucao, formata);
            this.dataDevolucaoReal = dataNova;
        }
        else{
            this.dataDevolucaoReal = null;
        }
    }
    public void setKmRetirada(){
        this.kmRetirada = carro.getQuilom();
    }
    public void setKmDevolucao(int kmDevo){
        this.kmDevolucao = kmDevo;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setValorTotal(double valorTotal){
        this.valorTotal = valorTotal;
    }
    public void setAdAtraso(boolean adicional){
        this.adicionalAtraso = adicional;
    }

    public Cliente getCliente(){
        return cliente;
    }
    public Carro getCarro(){
        return carro;
    }
    public Funcionario getFuncionario(){
        return funcionario;
    }
    public LocalDate getDataRetirada(){
        return dataRetirada;
    }
    public String getStringDataRetirada(){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
        return dataRetirada.format(formata);
    }
    public LocalDate getDataPrevista(){
        return dataRetirada;
    }
    public String getStringDataPrevista(){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
        return dataPrevista.format(formata);
    }
    public LocalDate getDataDevolucao(){
        return dataDevolucaoReal;
    }
    public String getStringDataDevolucao(){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
        return dataDevolucaoReal.format(formata);
    }
    public int getKmRetirada(){
        return kmRetirada;
    }
    public int getKmDevolucao(){
        return kmDevolucao;
    }
    public String getStatus(){
        return status;
    }
    public double getValorTotal(){
        return valorTotal;
    }
    public boolean getAdAtraso(){
        return adicionalAtraso;
    }

    // Importante pontuar as regras de negocio aqui:
    /* Para calcular a diaria usamos (trabalhamos com quilometragem livre):
        1. Dias de aluguel * Taxa fixa de cada tipo de carro (todos os sedans, populares e SUVs possuem a mesma taxa);
        2. Seguros contra roubo, etc -> existem tipos;
            Acrescimos:
            1. Se o cliente não abasteceu o carro antes de entregar, cobramos uma taxa de combustivel;
            2. Se o cliente entregou em uma cidade diferente;
            3. Se o cliente tiver entre 18 e 25 anos ou carteira de habilitação < 2 anos;
            4. Se o cliente passou o tempo para a entrega do carro.
        Logo, obtemos a seguinte formula:
        Total = (taxaBase x dias) + seguro (+ 
                    TaxaCombst. + TaxaOutraCidade + TaxaIdadeCNH + (12% da taxaBase * (POR dia de atraso - 1hr de tolerancia))
    */
    
    public int calcularDias(LocalDate dataAntes, LocalDate dataDepois){
        return (int) ChronoUnit.DAYS.between(dataAntes, dataDepois);
    }
    public int calcularHoras(LocalDate dataAntes, LocalDate dataDepois){
        return 0;
    }
    public void finalizarLocacao(
        int kmDevolucao, boolean adAtraso, LocalDate dataDevolucaoReal, boolean abasteceu, String cidadeEntregue){
        if(adAtraso){
            int dias = calcularDias(dataPrevista, dataDevolucaoReal);
            double addAtraso = carro.calcularDiaria(dias); // **** arrumar, deve ser horas
            double total = getValorTotal() + addAtraso;
            setValorTotal(total);
        }
        if(!abasteceu){
            double adicional = 100.00;
            double total = getValorTotal() + adicional;
            setValorTotal(total);
        }
        if(!carro.getCidadeAt().equals(cidadeEntregue)){
            double adicional = 120.00;
            double total = getValorTotal() + adicional;
            setValorTotal(total);
        }
        String novoStatus = "Finalizado";
        setStatus(novoStatus);
        carro.setQuilom(kmDevolucao);
        carro.alterarDisponibilidade();
    }
    public void cancelarLocacao(){
        String novoStatus = "Cancelado";
        setStatus(novoStatus);
        carro.alterarDisponibilidade();
    }

    public double calcularValorTotal() throws TipoSeguroInvalidoException{
        int dias = calcularDias(dataRetirada, dataPrevista);
        double tarifaDia = carro.calcularDiaria(dias);
        double seguro = carro.calcularSeguro();
        double total = tarifaDia + seguro;
        if(cliente.getIdade() == 18 || cliente.getIdade() <= 25){
            total += 95.00;
        }
        else{
            if(cliente.getCnh().tempoDeHabilitacao() < 2){
                total += 95.00;
            }
        }
        return total;
    }
}
