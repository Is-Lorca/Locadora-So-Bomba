package documentos;

import exceptions.TipoSeguroInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import locacoes.Locacao;

public class RelatorioLocacao extends Relatorio{
    private LocalDate periodoInicial;
    private LocalDate periodoFinal;
    private ArrayList<Locacao> locacoes;

    public RelatorioLocacao(String dataGeracao, String periodoInicial, String periodoFinal, ArrayList<Locacao> locacoes){
        super(dataGeracao);
        this.setTitulo();
        this.setPeriodoInicial(periodoInicial);
        this.setPeriodoFinal(periodoFinal);
        this.setLocacoes(locacoes);
    }
    public RelatorioLocacao(LocalDate dataGeracao, String periodoInicial, String periodoFinal, ArrayList<Locacao> locacoes){
        super(dataGeracao);
        this.setTitulo();
        this.setPeriodoInicial(periodoInicial);
        this.setPeriodoFinal(periodoFinal);
        this.setLocacoes(locacoes);
    }
    @Override 
    public void setTitulo(){
        super.setTitulo("Relatório Locação\n");
    }
    public void setPeriodoInicial(String prdInicial){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(prdInicial, formata);
        this.periodoInicial = dataNova;
    }
    public void setPeriodoFinal(String prdFinal){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(prdFinal, formata);
        this.periodoFinal = dataNova;
    }
    public void setLocacoes(ArrayList<Locacao> locacoes){
        this.locacoes = locacoes;
    }

    public String getPeriodoInicial(){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
        return periodoInicial.format(formata);
    }
    public String getPeriodoFinal(){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
        return periodoFinal.format(formata);
    }
    public ArrayList<Locacao> getLocacoes(){
        return locacoes;
    }

    @Override 
    public String gerarConteudo(){
        String texto = "";

        texto += getTitulo();
        texto += "==================================================\n";
        texto += "Período: ";
        texto += periodoInicial + " --> " + periodoFinal;
        texto += "\n...............................................\n";
        for(int i = 0; i < locacoes.size(); i++){
            texto += "Locação: " + locacoes.get(i).getNumLocacao() + "\n";
            texto += "Cliente: " + locacoes.get(i).getCliente().getNome() + "\n";
            texto += "Veículo: " + locacoes.get(i).getCarro().getTipo() + " -- " + locacoes.get(i).getCarro().getModelo() + "\n";
            try {
                texto += "Seguro: " + locacoes.get(i).getSeguro() + " - Preço: R$" + locacoes.get(i).getCarro().calcularSeguro(locacoes.get(i).getSeguro()) + "\n";
            } catch (TipoSeguroInvalidoException ex) {
                texto += "Seguro: " + locacoes.get(i).getSeguro() + "\n";
            }
            texto += "Status: " + locacoes.get(i).getStatus() + "\n";
            texto += "Valor: R$" + locacoes.get(i).getValorTotal() + "\n";
            texto += "...............................................\n";
        }
        return texto;
    }
}
