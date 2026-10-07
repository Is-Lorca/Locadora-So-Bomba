package documentos;

import java.time.LocalDate;
import java.util.ArrayList;
import locacoes.Locacao;

public class RelatorioFinanceiro extends Relatorio{
    private ArrayList<Locacao> locacoes;
    private String periodo;

    public RelatorioFinanceiro(String data, ArrayList<Locacao> locacoes, String ano){
        super(data);
        this.setTitulo();
        this.setPeriodo(ano);
        this.setLocacoes(locacoes);
    }
    public RelatorioFinanceiro(LocalDate data, ArrayList<Locacao> locacoes, String ano){
        super(data);
        this.setTitulo();
        this.setPeriodo(ano);
        this.setLocacoes(locacoes);
    }
    // adicionar setters e getters 
    @Override 
    public void setTitulo(){
        super.setTitulo("Relatório Financeiro");
    }
    public void setPeriodo(String ano){
        this.periodo = ano;
    }
    public void setLocacoes(ArrayList<Locacao> locacoes){
        this.locacoes = locacoes;
    }
   
    public String getPeriodo(){
        return periodo;
    }
    public ArrayList<Locacao> getLocacoes(){
        return locacoes;
    }
    
    public double receita(){
        double receita = 0;
        for(int i = 0; i < locacoes.size(); i++){
            if(locacoes.get(i).getStatus().equals("Finalizado"))
            receita += locacoes.get(i).getValorTotal();
        }
        return receita;
    }
    public int totaLocacoes(){ // apenas as que concluiram 
        int totalLocacao = 0;
        for(int i = 0; i < locacoes.size(); i++) {
            if(locacoes.get(i).getStatus().equals("Finalizado")){
                totalLocacao ++;
            }
        }
        return totalLocacao;
    }
    public double mediaLocacao(){
        double receita = receita();
        int locacoes = totaLocacoes();
        double media = receita/locacoes;
        return media;
    }

    @Override 
    public String gerarConteudo(){
        String texto = "";

        texto += getTitulo() + "\n";
        texto += "========================================\n";
        texto += "         " + getPeriodo() + "\n\n";
        texto += "Receita: R$" + receita() + "\n";
        texto += "Total de Locações: " + totaLocacoes() + "\n";
        texto += "Média de locações: " + mediaLocacao() + "\n";
        texto += "==========================================";

        return texto;
    }
}
