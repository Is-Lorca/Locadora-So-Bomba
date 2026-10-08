package persistencia;

import carros.Carro;
import carros.Marca;
import clientes.Cliente;
import documentos.Contrato;
import funcionarios.Funcionario;
import java.util.ArrayList;
import locacoes.Locacao;

public class DadosSistema {
    private int proximoIdMarca;
    private int proximoIdCarro;
    private int proximoIdCliente;
    private int proximoIdFuncionario;
    private int proximoIdLocacao;
    private int proximoIdContrato;
    private ArrayList<Marca> marcas;
    private ArrayList<Carro> carros;
    private ArrayList<Cliente> clientes;
    private ArrayList<Funcionario> funcionarios;
    private ArrayList<Locacao> locacoes;
    private ArrayList<Contrato> contratos;


    public DadosSistema(){
        proximoIdMarca = 1;
        proximoIdCarro = 1;
        proximoIdCliente = 1;
        proximoIdFuncionario = 1;
        proximoIdLocacao = 1;
        proximoIdContrato = 1;
        marcas = new ArrayList<>();
        carros = new ArrayList<>();
        clientes = new ArrayList<>();
        funcionarios = new ArrayList<>();
        locacoes = new ArrayList<>();
        contratos = new ArrayList<>();
    }

    public int gerarIdMarca(){
        return proximoIdMarca++;
    }
    public int gerarIdCarro(){
        return proximoIdCarro++;
    }
    public int gerarIdCliente(){
        return proximoIdCliente++;
    }
    public int gerarIdFuncionario(){
        return proximoIdFuncionario++;
    }
    public int gerarIdLocacao(){
        return proximoIdLocacao++;
    }
    public int gerarIdContrato(){
        return proximoIdContrato++;
    }
    public void setMarcas(ArrayList<Marca> marcas){
        this.marcas = marcas;
    }
    public void setCarros(ArrayList<Carro> carros){
        this.carros = carros;
    }
    public void setClientes(ArrayList<Cliente> clientes){
        this.clientes = clientes;
    }
    public void setFuncionarios(ArrayList<Funcionario> funcionarios){
        this.funcionarios = funcionarios;
    }
    public void setLocacoes(ArrayList<Locacao> locacoes){
        this.locacoes = locacoes;
    }
    public void setContratos(ArrayList<Contrato> contratos){
        this.contratos = contratos;
    }

    public int getProximoIdMarca(){
        return proximoIdMarca;
    }
    public int getProximoIdCarro(){
        return proximoIdCarro;
    }
    public int getProximoIdCliente(){
        return proximoIdCliente;
    }
    public int getProximoIdFuncionario(){
        return proximoIdFuncionario;
    }
    public int getProximoIdLocacao(){
        return proximoIdLocacao;
    }
    public int getProximoIdContrato(){
        return proximoIdContrato;
    }
    public ArrayList<Marca> getMarcas(){
        return marcas;
    }
    public ArrayList<Carro> getCarros(){
        return carros;
    }
    public ArrayList<Cliente> getClientes(){
        return clientes;
    }
    public ArrayList<Funcionario> getFuncionarios(){
        return funcionarios;
    }
    public ArrayList<Locacao> getLocacoes(){
        return locacoes;
    }
    public ArrayList<Contrato> getContratos(){
        return contratos;
    }

    public void reconstruirReferencias() {
        for (Carro carro : carros){
            carro.setMarca(buscarMarcaPorId(carro.getIdMarca()));
        }
        for(Locacao locacao : locacoes){
            locacao.setCliente(buscarClientePorId(locacao.getIdCliente()));
            locacao.setCarro(buscarCarroPorId(locacao.getIdCarro()));
            locacao.setFuncionario(buscarFuncionarioPorId(locacao.getIdFuncionario()));
        }
        for(Contrato contrato : contratos){
            contrato.setLocacoes(buscarLocacaoPorId(contrato.getIdLocacao()));
        }
    }

    public Marca buscarMarcaPorId(int id){
        for (Marca marca : marcas){
            if(marca.getIdMarca() == id){
                return marca;
            }
        }
        return null;
    }
    public Funcionario buscarFuncionarioPorId(int id){
        for(Funcionario funcionario : funcionarios){
            if(funcionario.getIdFuncionario() == id){
                return funcionario;
            }
        }
        return null;
    }

    public void atualizarProximosIds(){
        for(Carro carro : carros){
            if(carro.getIdCarro() >= proximoIdCarro){
                proximoIdCarro = carro.getIdCarro() + 1;
            }
        }

        for(Marca marca : marcas){
            if(marca.getIdMarca() >= proximoIdMarca){
                proximoIdMarca = marca.getIdMarca() + 1;
            }
        }

        for(Cliente cliente : clientes){
            if(cliente.getIdCliente() >= proximoIdCliente){
                proximoIdCliente = cliente.getIdCliente() + 1;
            }
        }

        for(Funcionario funcionario : funcionarios){
            if(funcionario.getIdFuncionario() >= proximoIdFuncionario){
                proximoIdFuncionario = funcionario.getIdFuncionario() + 1;
            }
        }

        for(Locacao locacao : locacoes){
            if(locacao.getIdLocacao() >= proximoIdLocacao){
                proximoIdLocacao = locacao.getIdLocacao() + 1;
            }
        }

        for(Contrato contrato : contratos){
            if(contrato.getNumeroContrato() >= proximoIdContrato){
                proximoIdContrato = contrato.getNumeroContrato() + 1;
            }
        }
    }

    public Cliente buscarClientePorId(int id){
        for(Cliente cliente : clientes){
            if(cliente.getIdCliente() == id){
                return cliente;
            }
        }
        return null;
    }

    public Locacao buscarLocacaoPorId(int id){
        for(Locacao locacao : locacoes){
            if(locacao.getIdLocacao() == id){
                return locacao;
            }
        }
        return null;
    }

    public Carro buscarCarroPorId(int id){
        for(Carro carro : carros){
            if(carro.getIdCarro() == id){
                return carro;
            }
        }
        return null;
    }

    public void carregarMarcasPadrao(){
        if(!marcas.isEmpty()){
            return;
        }

        marcas.add(new Marca(gerarIdMarca(), "Chery"));
        marcas.add(new Marca(gerarIdMarca(), "Lifan"));
        marcas.add(new Marca(gerarIdMarca(), "Ford"));
        marcas.add(new Marca(gerarIdMarca(), "Renault"));
        marcas.add(new Marca(gerarIdMarca(), "Fiat"));
        marcas.add(new Marca(gerarIdMarca(), "Jeep"));
        marcas.add(new Marca(gerarIdMarca(), "Peugeot"));
        marcas.add(new Marca(gerarIdMarca(), "Volkswagen"));
        marcas.add(new Marca(gerarIdMarca(), "Chevrolet"));
    }

    // Métodos auxiliares
    public void adicionarMarca(Marca marca){
        marcas.add(marca);
    }
    public void adicionarCarro(Carro carro){
        carros.add(carro);
    }
    public void adicionarCliente(Cliente cliente){
        clientes.add(cliente);
    }
    public void adicionarFuncionario(Funcionario funcionario){
        funcionarios.add(funcionario);
    }
    public void adicionarLocacao(Locacao locacao){
        locacoes.add(locacao);
    }
    public void adicionarContrato(Contrato contrato){
        contratos.add(contrato);
    }

}