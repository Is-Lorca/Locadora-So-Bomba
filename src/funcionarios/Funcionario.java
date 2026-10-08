package funcionarios;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class Funcionario {
    private int idFuncionario;
    private Login funcLogin;
    private String nome;
    private String cpf;
    private String telefone;

    public Funcionario(int id, Login user, String nome, String cpf, String telefone){
        this.setIdFunc(id);
        this.setFuncLogin(user);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setTelefone(telefone);
    }

    public void setIdFunc(int idFuncionario){
        this.idFuncionario = idFuncionario;
    }
    public void setFuncLogin(Login user){
        this.funcLogin = user;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCpf(String cpf){ 
        if(cpf == null){
            System.out.println("CPF não pode ser nulo");
        }

        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        if (cpfLimpo.length() != 11 ){
            System.out.println("Tamanho de CPF inválido");
        }
        else{
            this.cpf = cpfLimpo;
        }
    }
    public void setTelefone(String telefone){
        if(telefone == null){
            System.out.println("Telefone não pode ser nulo");
        }

        String telefoneLimpo = telefone.replaceAll("[^0-9]", "");

        
        if (telefoneLimpo.length() == 11 || telefoneLimpo.length() == 10){
            this.telefone = telefoneLimpo;
        }
        else{
            System.out.println("Tamanho de telefone inválido: " + telefoneLimpo + " TAMANHO: " + telefoneLimpo.length());
        }
    }

    // Para melhor visualização dos dados
    public String mascara(String texto, String padrao) throws ParseException{
        try {
            MaskFormatter mascara = new MaskFormatter(padrao);
            mascara.setValueContainsLiteralCharacters(false);
            return mascara.valueToString(texto);
        } 
        catch (ParseException parse) {
            return texto;
        }
    }

    public int getIdFuncionario(){
        return idFuncionario;
    }
    public Login getFuncLogin(){
        return funcLogin;
    }
    public String getNome(){
        return nome;
    }
    public String getCpf(){
        return cpf;
    }
    public String getTelefone(){
        return telefone;
    }

    @Override 
    public boolean equals(Object obj){
        Funcionario func = Funcionario.class.cast(obj);
        if(this.getCpf().equals(func.getCpf())){
            return true;
        }
        else{
            return false;
        }
    }

    @Override 
    public String toString(){
        try {
            return """

                    Funcionario:
                    Nome: """ + getNome() + "\n" +
                    "CPF: " + mascara(getCpf(), "###.###.###-##") + "\n" +
                    "Telefone: " + mascara(getTelefone(), "(##) #####-####") + "\n" +
                    "Usuario: " + (getFuncLogin() != null? getFuncLogin().getUsuario(): "funcionario ainda não possui cadastro de usuario");
        } catch (ParseException e) {
            return """
            
                    Funcionario:
                    Nome: """ + getNome() + "\n" +
                    "CPF: " + getCpf() + "\n" +
                    "Telefone: " + getTelefone() + "\n" +
                    "Usuario " + (getFuncLogin() != null? getFuncLogin().getUsuario(): "funcionario ainda não possui cadastro de usuario");
        }
    }
}
