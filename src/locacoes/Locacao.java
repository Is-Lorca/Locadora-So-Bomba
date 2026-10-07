package locacoes;

import carros.Carro;
import clientes.Cliente;
import exceptions.CNHVencidaException;
import exceptions.TipoSeguroInvalidoException;
import exceptions.VeiculoIndisponivelException;
import funcionarios.Funcionario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Locacao {
    // criando especie de Id
    private static int contador = 0;

    private Cliente cliente;
    private int numeroLocacao;
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
    private String seguro;

    public Locacao(Cliente cliente, Carro carro, Funcionario funcionario, String dataRetirada, String dataPrevista, String seguro){
        this.setCliente(cliente);
        this.numeroLocacao = ++contador;
        this.setCarro(carro);
        this.setFuncionario(funcionario);
        this.setDataRetirada(dataRetirada);
        this.setDataPrevista(dataPrevista);
        this.setDataDevolucao("");
        this.setKmRetirada();
        this.setStatus("Em Vigor");
        this.setValorTotal(0.0);
        this.setAdAtraso(false);
        this.setSeguro(seguro);
    }
    
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    public void setCarro(Carro carro){
        this.carro = carro;
        carro.setDisp(false);
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
        this.dataPrevista = dataNova;
    }
    public void setDataDevolucao(String dataDevolucao){
        if(!dataDevolucao.equals("")){
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
    public void setSeguro(String seguro){
        this.seguro = seguro;
    }

    public Cliente getCliente(){
        return cliente;
    }
    public int getNumLocacao(){
        return numeroLocacao;
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
        return dataPrevista;
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
    public String getSeguro(){
        return seguro;
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
                    TaxaCombst. + TaxaOutraCidade + TaxaIdadeCNH + (12% da taxaBase * (POR dia de atraso))
    */
    
    public int calcularDias(LocalDate dataAntes, LocalDate dataDepois){
        return (int) ChronoUnit.DAYS.between(dataAntes, dataDepois);
    }
    public void aceiteLocacao() throws CNHVencidaException, TipoSeguroInvalidoException, VeiculoIndisponivelException{
        if(!getCliente().getCnh().estaVencida()){
            if(getCarro().getDisp()){
                calcularValorTotal(); // para mostrar total (ao menos até o momento do contrato inicial)
                getCarro().alterarDisponibilidade();
            }
            else{
                throw new VeiculoIndisponivelException(); // veículo não disponivel
            }
        }
        else{
            throw new CNHVencidaException(); // para aparecer: locacao negada
        }
    }
    public void finalizarLocacao(
        int kmDevolucao, boolean adAtraso, String dataDevolucaoReal, boolean abasteceu, String cidadeEntregue){
        if(adAtraso){
            DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataDevolucao = LocalDate.parse(dataDevolucaoReal, formata);
            int dias = calcularDias(dataPrevista, dataDevolucao);
            double addAtraso = carro.calcularDiaria(dias) * 0.12; // **** arrumar, deve ser horas
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

    public void calcularValorTotal() throws TipoSeguroInvalidoException{
        int dias = calcularDias(dataRetirada, dataPrevista);
        double tarifaDia = carro.calcularDiaria(dias);
        double precoSeguro = carro.calcularSeguro(getSeguro());
        double total = tarifaDia + precoSeguro;
        if(cliente.getIdade() == 18 || cliente.getIdade() <= 25){
            total += 95.00;
        }
        else{
            if(cliente.getCnh().tempoDeHabilitacao() < 2){
                total += 95.00;
            }
        }
        setValorTotal(total);
    }

    @Override 
    public boolean equals(Object obj){
        Locacao locacao = Locacao.class.cast(obj);
        if(this.getCliente().getCpf().equals(locacao.getCliente().getCpf()) &&
            this.getNumLocacao() == locacao.getNumLocacao() &&
            this.getFuncionario().getCpf().equals(locacao.getFuncionario().getCpf()) &&
            this.getCarro().getPlaca().equals(locacao.getCarro().getPlaca()) &&
            this.getDataPrevista().equals(locacao.getDataPrevista())){
                return true;
            }
        else{
            return false;
        }
    }

    @Override 
    public String toString(){
        return """
                Locacao
                Numero: """ + getNumLocacao() + "\n" +
                "Cliente: " + getCliente().getNome() + "\n" +
                "Carro: " + getCarro().getModelo() + "\n" +
                "-> Placa: " + getCarro().getPlaca() + "\n" +
                "-> Marca: " + getCarro().getMarca() + "\n" +
                "Seguro: " + getSeguro()  + "\n" +
                "Total: " + getValorTotal()  + "\n" +
                "Periodo: " + getDataRetirada() + " -> " + getDataPrevista() + "\n" +
                "Status: " + getStatus();
    }
}
