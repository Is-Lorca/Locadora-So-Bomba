package documentos;

import carros.Carro;
import java.time.LocalDate;
import java.util.ArrayList;

public class RelatorioFrota extends Relatorio{
    private ArrayList<Carro> carros;

    public RelatorioFrota(String data, ArrayList<Carro> carros){
        super(data);
        this.setTitulo();
        this.setCarros(carros);
    }
     public RelatorioFrota(LocalDate data, ArrayList<Carro> carros){
        super(data);
        this.setTitulo();
        this.setCarros(carros);
    }

    @Override 
    public void setTitulo(){
        super.setTitulo("Relatório Frota");
    }
    public void setCarros(ArrayList<Carro> carros){
        this.carros = carros;
    }

    public ArrayList<Carro> getCarros(){
        return carros;
    }

    public int totalVeiculos(){
        int total = 0;

        for(int i = 0; i < carros.size(); i++){
            total++;
        }
        return total;
    }
    public int totalDisponiveis(){
        int total = 0;
        
        for(int i = 0; i < carros.size(); i++){
            if(carros.get(i).getDisp() == true){
                total++;
            }
        }
        return total;
    }
    public int totalAlugados(){
        int total = 0;
        
        for(int i = 0; i < carros.size(); i++){
            if(carros.get(i).getDisp() == false){
                total++;
            }
        }
        return total;
    }
    public int totalPopular(){
        int total = 0;
        
        for(int i = 0; i < carros.size(); i++){
            if(carros.get(i).getTipo().equals("Popular")){
                total++;
            }
        }
        return total;
    }
    public int totalSedan(){
        int total = 0;
        
        for(int i = 0; i < carros.size(); i++){
            if(carros.get(i).getTipo().equals("Sedan")){
                total++;
            }
        }
        return total;
    }
    public int totalSuv(){
        int total = 0;
        
        for(int i = 0; i < carros.size(); i++){
            if(carros.get(i).getTipo().equals("SUV")){
                total++;
            }
        }
        return total;
    }

    @Override 
    public String gerarConteudo(){
        String texto = "";

        texto += getTitulo() + "\n";
        texto += "========================================\n";
        texto += "Total de veículos: " + totalVeiculos() + "\n";
        texto += "Disponiveis: " + totalDisponiveis() + "\n";
        texto += "Alugados: " + totalAlugados() + "\n";
        texto += "Total Populares: " + totalPopular() + "\n";
        texto += "Total Sedans: " + totalSedan() + "\n";
        texto += "Total SUVs: " + totalSuv() + "\n";
        texto += "========================================\n";

        return texto;
    }
}
