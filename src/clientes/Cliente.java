package clientes;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.MaskFormatter;

public class Cliente {
    private String nome;
    private String cpf; 
    private String telefone; 
    private String email;
    private String endereco;
    private int idade;
    private Cnh cnh;
        
    public Cliente(
        String nome, String cpf, String telefone, String email, String endereco, int idade, Cnh cnh){
        this.setNome(nome);
        this.setCpf(cpf);
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setEndereco(endereco);
        this.setIdade(idade);
        this.setCnh(cnh);
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
    public void setEmail(String email){
        if(email == null){
            throw new IllegalArgumentException("O email não pode ser nulo.");
        }

        String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new IllegalArgumentException("E-mail com formato inválido.");
        }

        this.email = email.toLowerCase().trim();
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public void setIdade(int idade){
        this.idade = idade;
    }
    public void setCnh(Cnh cnh){
        if(cnh!= null){
            this.cnh = cnh;
            validadeCnh();
        }
        else{
            this.cnh = null;
        }
    }
    public void validadeCnh(){
        if(idade <= 49){
            cnh.setValidade(10);
        }
        else if(idade == 50 || idade >= 69){
            cnh.setValidade(5);
        }
        else{
            cnh.setValidade(3);
        }
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
    public String getEmail(){
        return email;
    }
    public String getEndereco(){
        return endereco;
    }
    public int getIdade(){
        return idade;
    }
    public Cnh getCnh(){
        return cnh;
    }

    public void alterarEndereco(String novoEndereco){
        this.setEndereco(novoEndereco);
    }
    public void alterarTelefone(String novoTelefone){
        this.setTelefone(novoTelefone);
    }
   
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
    
    @Override 
    public boolean equals(Object obj){
        Cliente cliente = Cliente.class.cast(obj);
        if(this.getCnh().equals(cliente.getCnh()) &&
            this.getCpf().equals(cliente.getCpf())){
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
            
                   Cliente: 
                   CPF: """ + mascara(getCpf(), "###.###.###-##") + "\n" +
                   "Nome: " + getNome() + "\n" +
                   "Telefone: " + mascara(getTelefone(), "(##) #####-####") + "\n" +
                   "Email: " + getEmail() + "\n" +
                   "Endereço: " + getEndereco() + "\n" +
                   "Idade: " + getIdade() + "\n" +
                    "" + (cnh != null? cnh.toString(): "CNH não cadastrada");
        } catch (ParseException e) {
            return """
            
                   Cliente:
                   CPF: """ + getCpf() + "\n" +
                   "Nome: " + getNome() + "\n" +
                   "Telefone: " + getTelefone() + "\n" +
                   "Email: " + getEmail() + "\n" +
                   "Endereço: " + getEndereco() + "\n" +
                   "Idade: " + getIdade() + "\n" +
                   "" + (cnh != null? cnh.toString(): "CNH não cadastrada");
        }
    }
}
