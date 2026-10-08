package carros;

public class Marca {
    private int idMarca;
    private String nome;

    public Marca(int id, String nome){
       this.setNome(nome);
       this.setIdMarca(id);
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setIdMarca(int idMarca){
        this.idMarca = idMarca;
    }

    public String getMarca() {
        return nome;
    }
    public int getIdMarca(){
        return idMarca;
    }

    @Override
    public String toString(){
        return this.getMarca();
    }
}
