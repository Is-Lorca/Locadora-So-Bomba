package persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import carros.Carro;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;

public class Persistencia {
    private Gson gson;

    public Persistencia(){
        gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(Carro.class, new CarroAdapter())
            .setPrettyPrinting()
            .create();
    }

    public void salvar(DadosSistema dados){
        Arquivos.criarPastas();

        try(FileWriter arquivo = new FileWriter(Arquivos.DADOS_SISTEMA)){
            gson.toJson(dados, arquivo);
        }
        catch(IOException e){
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public DadosSistema carregar() {
        try (FileReader arquivo = new FileReader(Arquivos.DADOS_SISTEMA)) {
            Type tipo = new TypeToken<DadosSistema>() {}.getType();

            DadosSistema dados = gson.fromJson(arquivo, tipo);

            if (dados == null) {
                return new DadosSistema();
            }

            dados.reconstruirReferencias();
            dados.atualizarProximosIds();

            return dados;
        } 
        catch (IOException e) {
            return new DadosSistema();
        }
    }
}