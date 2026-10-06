package documentos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Relatorio implements Impressao{
    private LocalDate dataGeracao;
    private String titulo;

    public Relatorio(String dataGeracao){
        this.setDataGeracao(dataGeracao);
        this.setTitulo();
    }

    public void setDataGeracao(String data){
        if(!data.equals("")){
            DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNova = LocalDate.parse(data, formata);
            this.dataGeracao = dataNova;
        }
        else{
            this.dataGeracao = LocalDate.now();
        }
    }
    protected void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public abstract void setTitulo();


    public String getStringDataGeracao(){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
        return dataGeracao.format(formata);
    }
    public LocalDate getDataGeracao(){
        return dataGeracao;
    }
    public String getTitulo(){
        return titulo;
    }

}
