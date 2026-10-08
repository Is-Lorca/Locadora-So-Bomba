package persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Arquivos {

    private Arquivos(){
        // para impedir de criar um Arquivo arquivo = new Arquivo()
    }

    public static final String PASTA_DADOS = "dados";
    public static final String DADOS_SISTEMA = PASTA_DADOS + "/dadosSistema.json";
    public static final String CARROS = PASTA_DADOS + "/carros.json";
    public static final String MARCAS = PASTA_DADOS + "/marcas.json";
    public static final String CLIENTES = PASTA_DADOS + "/clientes.json";
    public static final String FUNCIONARIOS = PASTA_DADOS + "/funcionarios.json";
    public static final String LOCACOES = PASTA_DADOS + "/locacoes.json";
    public static final String CONTRATOS = PASTA_DADOS + "/contratos.json";

    public static void criarPastas(){
        File pasta = new File(PASTA_DADOS);

        if(!pasta.exists()){
            pasta.mkdirs();
        }
    }

    public static void salvar(String caminho, String conteudo){
        criarPastas();

        try(FileWriter escritor = new FileWriter(caminho)){
            escritor.write(conteudo);
        }
        catch(IOException erro){
            System.out.println("Erro ao salvar arquivo: " + erro.getMessage());
        }
    }

    public static String ler(String caminho){
        File arquivo = new File(caminho);

        if(!arquivo.exists()){
            return "";
        }

        try{
            return Files.readString(Path.of(caminho));
        }
        catch(IOException erro){
            System.out.println("Erro ao ler arquivo: " + erro.getMessage());
            return "";
        }
    }
}