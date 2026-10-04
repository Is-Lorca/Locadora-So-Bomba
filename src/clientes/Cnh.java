package clientes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Cnh {
    private String numRegistro;
    private String categoria;
    private LocalDate dataPrimeiraHabil;
    private LocalDate dataUltRenovacao;
    private int validade;
    
    public Cnh(String registro, String categoria, String dataPrimeiraHab, String ultRenovacao){
        this.setNumRegistro(registro);
        this.setCategoria(categoria);
        this.setDataPrimHabil(dataPrimeiraHab);
        this.setDataUltRenovacao(ultRenovacao);
    }

    public void setNumRegistro(String registro){
        if(registro == null){
            System.out.println("CPF não pode ser nulo");
        }

        String registroLimpo = registro.replaceAll("[^0-9]", "");

        if (registroLimpo.length() != 9){
            System.out.println("Tamanho de CPF inválido");
        }
        else{
            this.numRegistro = registroLimpo;
        }
    }
    public void setCategoria(String categoria){
        this.categoria = categoria;
    }
    public void setDataPrimHabil(String dataPrimeiraHab){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataNova = LocalDate.parse(dataPrimeiraHab, formata);
        this.dataPrimeiraHabil = dataNova;
    }
    public void setDataUltRenovacao(String ultRenovacao){
        if(ultRenovacao != null){
            DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNova = LocalDate.parse(ultRenovacao, formata);

            this.dataUltRenovacao = dataNova;
        }
        else{
            this.dataUltRenovacao = null;
        }
    }
    public void setValidade(int anos){
        this.validade = anos;
    }

    public String getCnhRegist(){
        return numRegistro;
    }
    public String getCnhCategoria(){
        return categoria;
    }
    public LocalDate getPrimeiraHabil(){
        return dataPrimeiraHabil;
    }
    public String getStringPrimeirHabil(){
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
        return dataPrimeiraHabil.format(formata);
    }
    public String getStringUltRenov(){
        if(dataUltRenovacao != null){
            DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            return dataUltRenovacao.format(formata);
        }
        else{
            return "Nunca renovada";
        }
    }
    public LocalDate getUltRenovacao(){
        return dataUltRenovacao;
    }
    public int getCnhValidade(){
        return validade;
    }

    public boolean estaVencida(){
        if(getUltRenovacao() != null){
            LocalDate anoVencimento = getUltRenovacao().plusYears(validade);
                
            if((LocalDate.now()).isEqual(anoVencimento)){
                return true;
            }
            else if((LocalDate.now()).isAfter(anoVencimento)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            LocalDate anoVencimento = getPrimeiraHabil().plusYears(validade);

            if((LocalDate.now()).isEqual(anoVencimento)){
                return true;
            }
            else if((LocalDate.now()).isAfter(anoVencimento)){
                return true;
            }
            else{
                return false;
            }
        }
    }
    public int tempoDeHabilitacao() throws ArithmeticException{
        long anosL = ChronoUnit.YEARS.between(getPrimeiraHabil(), LocalDate.now());
        try {
            int anos = Math.toIntExact(anosL);
            return anos;
        } catch (ArithmeticException e) {
            System.out.println("Não foi possível converter o valor em Long para Int");
        }
        return 0;
    }

    @Override 
    public boolean equals(Object obj){
        Cnh cnh = Cnh.class.cast(obj);
        if(this.getCnhRegist().equals(cnh.getCnhRegist())){
                return true;
            }
        else{
            return false;
        }
    }

    @Override 
    public String toString(){
        return """
        
               CNH:
               Numero de Registro: """ + getCnhRegist() + "\n" +
               "Categoria: " + getCnhCategoria() + "\n" +
               "Primeira Habilitação: " + getStringPrimeirHabil() + "\n" +
               "Validade: " + getCnhValidade() + "\n" +
               "Ultima renovação: " + getStringUltRenov() + "\n" +
               "Esta vencida: " + (estaVencida()? "Sim": "Não");
    }
}
