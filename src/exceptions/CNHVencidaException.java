package exceptions;

public class CNHVencidaException extends Exception{
    public CNHVencidaException(){
        super("Impossivel realizar aluguel, CNH vencida.");
    }

    public CNHVencidaException(String mensagem){
        super(mensagem);
    }
}
