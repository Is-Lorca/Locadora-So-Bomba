package documentos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import locacoes.Locacao;

public class Contrato implements Impressao{
    private String titulo;
    private int numeroContrato;
    private transient Locacao locacoes; // conteudo que nao deve ser serializado -> processo de transformar um objeto da memória em uma sequência de bytes
    private int idLocacao;
    private LocalDate dataEmissao;
    private String termos;
    private boolean assinado;

    public Contrato(int id, Locacao locacoes){
        this.setTitulo();
        this.setNumeroContrato(id);
        this.setLocacoes(locacoes);
        this.setDataEmissao();
        this.setAssinado();
        this.setTermos();
    }

    public void setTitulo(){
        this.titulo = "Contrato de Locação\n\n";
    }
    public void setNumeroContrato(int id){
        this.numeroContrato = id;
    }
    public void setLocacoes(Locacao locacoes){
        this.locacoes = locacoes;
        if(locacoes != null){
            this.idLocacao = locacoes.getIdLocacao();
        }
    }
    public void setIdLocacoes(int idLocacao){
        this.idLocacao = idLocacao;
    }
    public void setDataEmissao(){
        this.dataEmissao = LocalDate.now();
    }
    public void setAssinado(){
        this.assinado = false;
    }
    public void setAssinado(boolean assinado){
        this.assinado = assinado;
    }  
    public void setTermos(){
        String termo = "";
        termo += 
            "CLÁUSULA 1 - DO OBJETO\n" +
            "O presente contrato tem como objeto a locação do veículo descrito neste documento ao LOCATÁRIO, " +
            "pelo período previamente acordado entre as partes.\n\n" +

            "CLÁUSULA 2 - DA UTILIZAÇÃO DO VEÍCULO\n" +
            "O veículo deverá ser utilizado exclusivamente para fins lícitos, respeitando integralmente as leis de trânsito vigentes.\n" +
            "- É proibido utilizar o veículo para competições ou atividades esportivas.\n" +
            "- É proibido transportar carga acima da capacidade permitida.\n" +
            "- É proibido emprestar ou ceder o veículo a terceiros não autorizados pela locadora.\n\n" +

            "CLÁUSULA 3 - DA HABILITAÇÃO\n" +
            "O LOCATÁRIO declara possuir Carteira Nacional de Habilitação (CNH) válida e compatível com a categoria do veículo locado.\n" +
            "Caso seja constatada irregularidade na habilitação, a locadora poderá cancelar a locação imediatamente.\n\n" +

            "CLÁUSULA 4 - DO ESTADO DO VEÍCULO\n" +
            "O LOCATÁRIO declara receber o veículo em perfeitas condições de funcionamento, conservação e limpeza, " +
            "conforme vistoria realizada no momento da retirada.\n\n" +

            "CLÁUSULA 5 - DA DEVOLUÇÃO\n" +
            "O veículo deverá ser devolvido na data e horário estabelecidos, nas mesmas condições em que foi entregue " +
            "e acompanhado de todos os acessórios fornecidos.\n" +
            "A devolução em local diferente do contratado poderá gerar cobrança adicional.\n\n" +

            "CLÁUSULA 6 - DA QUILOMETRAGEM\n" +
            "A quilometragem será registrada no momento da retirada e da devolução do veículo.\n" +
            "Qualquer adulteração ou inconsistência poderá acarretar cobrança de multas e demais medidas cabíveis.\n\n" +

            "CLÁUSULA 7 - DAS MULTAS E INFRAÇÕES\n" +
            "Todas as multas, infrações de trânsito e demais penalidades ocorridas durante o período da locação serão de responsabilidade do LOCATÁRIO.\n\n" +

            "CLÁUSULA 8 - DOS DANOS\n" +
            "O LOCATÁRIO compromete-se a comunicar imediatamente qualquer acidente, dano ou defeito ocorrido durante a utilização do veículo.\n" +
            "Quando não cobertos pelo seguro contratado, os custos de reparo serão de responsabilidade do LOCATÁRIO.\n\n" +

            "CLÁUSULA 9 - DO SEGURO\n" +
            "O seguro contratado possuirá cobertura conforme a modalidade escolhida no ato da locação.\n" +
            "Situações decorrentes de dolo, negligência ou uso indevido do veículo poderão não estar cobertas.\n\n" +

            "CLÁUSULA 10 - DO CANCELAMENTO\n" +
            "A locadora poderá cancelar a locação caso sejam constatadas informações falsas, inadimplência ou descumprimento das cláusulas deste contrato.\n\n" +

            "CLÁUSULA 11 - DA RESPONSABILIDADE\n" +
            "A locadora não se responsabiliza por objetos pessoais deixados no interior do veículo.\n\n" +

            "CLÁUSULA 12 - DO FORO\n" +
            "Fica eleito o foro da comarca onde a locação foi realizada para dirimir eventuais dúvidas oriundas deste contrato.\n\n" +

            "DECLARAÇÃO FINAL\n" +
            "O LOCATÁRIO declara ter lido, compreendido e aceitado todas as cláusulas e condições estabelecidas neste contrato.\n\n\n" +

            "ASSINATURAS\n\n" +
            "________________________________________\n" +
            (!getAssinado() ? "   " + locacoes.getCliente().getNome() + "\n" : "   (Contrato Pendente de Assinatura)\n") + 
            "LOCATÁRIO\n\n" +
            "________________________________________\n" +
            "REPRESENTANTE DA LOCADORA\n\n" +
            "________________________________________\n" +
            "DATA: " + getStringDataEmissao() + "\n";
        this.termos = termo;
    }
    
    public String getTitulo(){
        return titulo;
    }
    public int getNumeroContrato(){
        return numeroContrato;
    }
    public Locacao getLocacoes(){
        return locacoes;
    }
    public int getIdLocacao(){
        return idLocacao;
    }
    public LocalDate getDataEmissao(){
        return dataEmissao;
    }
    public String getStringDataEmissao(){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataEmissao.format(formata);
    }
    public boolean getAssinado(){
        return assinado;
    }
    public String getTermos(){
        return termos;
    }

    public void marcarComoAssinado(){
        this.setAssinado(true);
    }

    @Override 
    public String gerarConteudo(){
        String texto = "";
        texto += getTitulo();
        texto += getTermos();

        return texto;
    }

    @Override 
    public boolean equals(Object obj){
        Contrato contrato = Contrato.class.cast(obj);
        if(this.getDataEmissao().equals(contrato.getDataEmissao()) && 
            this.getNumeroContrato() == contrato.getNumeroContrato()){
                return true;
            }
        else{
            return false;
        }
    }

    @Override 
    public String toString(){
        return """
                Contrato
                Numero: """ + getNumeroContrato() + "\n" +
                "Cliente: " + getLocacoes().getCliente().getNome() + "\n" +
                "Assinado: " + (getAssinado()? "Sim": "Não") + "\n" +
                "Funcionário: " + getLocacoes().getFuncionario().getNome();
    }
}
