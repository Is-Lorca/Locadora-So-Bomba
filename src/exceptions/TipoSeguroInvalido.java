package exceptions;

public class TipoSeguroInvalido extends Exception{
    public TipoSeguroInvalido(){
        super("Tipo de seguro inválido, favor escolher entre 'Básico', 'Intermediário' ou 'Premium'.");
    }

    public TipoSeguroInvalido(String mensagem){
        super(mensagem);
    }
}
