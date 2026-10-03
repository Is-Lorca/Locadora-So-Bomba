package carros;
public class Marca {
    private String nome;

    public Marca(String nome){
       setNome(nome);
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getMarca() {
        return nome;
    }

    @Override
    public String toString(){
        return this.getMarca();
    }
}
