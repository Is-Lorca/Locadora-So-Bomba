package exceptions;

public class VeiculoIndisponivelException extends Exception{
    public VeiculoIndisponivelException(){
        super("Veículo indisponivel, favor escolher outro");
    }

    public VeiculoIndisponivelException(String mensagem){
        super(mensagem);
    }
}

