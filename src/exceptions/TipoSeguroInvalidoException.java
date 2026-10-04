package exceptions;

public class TipoSeguroInvalidoException extends Exception{
    public TipoSeguroInvalidoException(){
        super("Tipo de seguro inválido, favor escolher entre 'Básico', 'Intermediário' ou 'Premium'.");
    }

    public TipoSeguroInvalidoException(String mensagem){
        super(mensagem);
    }
}
