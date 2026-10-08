package funcionarios;

public class Login {
    private Integer usuario;
    private int senha;

    public Login(Integer user, int senha){
        this.setUsuario(user);
        this.setSenha(senha);
    }

    public void setUsuario(Integer user){
        if(user != null){
            this.usuario = user;
        }
    }
    public void setSenha(int senha){
        int digitos = String.valueOf(Math.abs(senha)).length();
        if(digitos < 8){
            System.out.println("Senha não cadastrada, ela deve ter no mínimo 8 digitos.");
        }
        else{
            this.senha = senha;
        }
    }

    public Integer getUsuario(){
        return usuario;
    }
    public int getSenha(){
        return senha;
    }

    public boolean autenticar(Integer userEntra, int senhaEntra){
        if(userEntra.equals(getUsuario()) && senhaEntra == getSenha()){
            return true;
        }
        else{
            return false;
        }
    }

    public void alterarSenha(int novaSenha){
        int digitos = String.valueOf(Math.abs(novaSenha)).length();
        if(digitos < 8){
            System.out.println("Senha não cadastrada, ela deve ter no mínimo 8 digitos.");
        }
        else{
            this.senha = novaSenha;
        }
    }

    @Override 
    public boolean equals(Object obj){
        Login login = Login.class.cast(obj);
        if(this.getUsuario().equals(login.getUsuario())){
            return true;
        }
        else{
            return false;
        }
    }

    @Override 
    public String toString(){
        return "Não é possivel acessar informações.";
    }
}
