package carros;

import exceptions.TipoSeguroInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Carro {
    private String placa;
    private Marca marca;
    private String modelo;
    private int ano;
    private String cor;
    private int quilometragem;
    private boolean disponivel;
    private String cidadeAtual;
    private LocalDate ultimManutencao; // armazena data da ultima manutencao
    private int ultimRevisao; // armazena km da ultima revisao
    private boolean precisaRevisao = false;

    public Carro(
        String placa, Marca mrc, String mod, int ano, String cor, int km, boolean disp, String cidAt, 
        String ultManut, int ultRevis) {
        this.setPlaca(placa);
        this.setMarca(mrc);
        this.setModelo(mod);
        this.setAno(ano);
        this.setCor(cor);
        this.setQuilom(km);
        this.setDisp(disp);
        this.setCidadeAt(cidAt);
        this.setUltManutencao(ultManut);
        this.setUltRevisao(ultRevis);
    }

    // Setters:
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public void setMarca(Marca mrc) {
        if(mrc!= null){
            this.marca = mrc;
        }
        else{
            this.marca = null;
        }
    }
    public void setModelo(String mod) {
        this.modelo = mod;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }
    public void setQuilom(int km){
        this.quilometragem = km;
    }
    public void setDisp(boolean disp){
        this.disponivel = disp;
    }
    public void setCidadeAt(String cidAt){
        this.cidadeAtual = cidAt;
    }
    public void setUltManutencao(String ultManut){
        if(ultManut != null){
            // Criamos um formatador
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            // Formatamos a String que recebemos
            this.ultimManutencao = LocalDate.parse(ultManut, formatador);
        }
        else{
            this.ultimManutencao = null;
        }
    }
    public void setUltRevisao(int ultRevis){
        this.ultimRevisao = ultRevis;
    }
    public void setPrecisaRevisao(boolean precisaRevisao){
        this.precisaRevisao = precisaRevisao;
    }
    
    // Getters:
    public String getPlaca() {
        return placa;
    }
    public Marca getMarca() {
        return marca;
    }
    public String getModelo() {
        return modelo;
    }
    public int getAno() {
        return ano;
    }
    public String getCor() {
        return cor;
    }
    public int getQuilom() {
        return quilometragem;
    }
    public boolean getDisp() {
        return disponivel;
    }
    public String getCidadeAt() {
        return cidadeAtual;
    }
    public LocalDate getUltManutencao() {
        return ultimManutencao;
    }
    public String getStringUltManut(){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
        return ultimManutencao.format(formata);
    }
    public int getUltRevisao() {
        return ultimRevisao;
    }
    public boolean getPrecisaRevisao(){
        return precisaRevisao;
    }

    // Métodos que classes filhas devem ter:
    public abstract double calcularDiaria(int dias);
    public abstract double calcularSeguro(String tipoSeguro) throws TipoSeguroInvalidoException;
    public abstract double taxaHoraAtraso();
    public abstract void precisaManutencao(); // checa se carro precisa de manutencao e/ou revisao
    public abstract void manutencaoRealizada(String diaManutencao); // Usada para atualizar a data da manutenção
    public abstract void revisaoRealizada(int quilometragem);

    public void mostrarDados() {
        System.out.printf("Placa: %s\n", getPlaca());
        System.out.printf("Marca: %s\n", getMarca());
        System.out.printf("Modelo: %s\n", getModelo());
        System.out.printf("Ano: %d\n", getAno());
        System.out.printf("Cor: %s\n", getCor());
        System.out.printf("Quilometragem: %d\n", getQuilom());
        if (disponivel){
            System.out.printf("Disponivel: sim\n");
        }
        else{
            System.out.printf("Disponivel: não\n");
        }
        System.out.printf("Cidade do aluguel: %s\n\n", getCidadeAt());
    }

    public void alterarDisponibilidade(){
        if (disponivel){
            this.setDisp(false);
        }
        else{
            this.setDisp(true);
        }
    }

    public void registrarQuilometragem(int quilom){
        // Temos que colocar aqui o calculo da necessidade de revisão (para facilitar)
        if (quilom < getQuilom() || quilom == getQuilom()){
            System.out.print("Valor de atualização invalido");
        }
        else{
            this.setQuilom(quilom);

            // Se a diferença entre a ultima revisao e a nova quilometragem atingiu 10.000 km...
            if((this.getQuilom() - this.getUltRevisao()) >= 10000){
                this.precisaRevisao = true;
            }
        }
    }

    // Subscrevendo o metodo equals
    @Override
    public boolean equals(Object obj) {
        Carro car = Carro.class.cast(obj); // Método Casting, especificando o Objeto para a classe carro
        if (this.marca.getMarca().equals(car.marca.getMarca())
                && this.getPlaca().equals(car.getPlaca())
                && (this.getAno() ==  car.getAno())
            && this.getModelo().equals(car.getModelo())) {
            return true;
        } else {
            return false;
        }
    }
    
    // Subscrevendo método toString
    @Override 
    public String toString(){
        return """
        
               Carro: 
               Tipo: Carro
               Placa: """ + getPlaca() + "\n" +
               "Marca: " + getMarca() + "\n" +
               "Modelo: " + getModelo() + "\n" +
               "Ano: " + getAno() + "\n" +
               "Cor: " + getCor() + "\n" +
               "Quilometragem: " + getQuilom() + "\n" +
               "Cidade atual: " + getCidadeAt() + "\n" +
               "Disponivel: " + (getDisp() ? "Sim": "Não");
    }
}
