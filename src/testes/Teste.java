package testes;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;

import carros.Carro;
import carros.Marca;
import carros.Popular;
import carros.Sedan;
import clientes.Cliente;
import clientes.Cnh;
import documentos.Contrato;
import exceptions.CNHVencidaException;
import exceptions.TipoSeguroInvalidoException;
import exceptions.VeiculoIndisponivelException;
import funcionarios.Funcionario;
import funcionarios.Login;
import locacoes.Locacao;
import persistencia.DadosSistema;
import persistencia.LocalDateAdapter;
import persistencia.Persistencia;

public class Teste {
    public static void main(String[] args) throws TipoSeguroInvalidoException, CNHVencidaException, VeiculoIndisponivelException{
        // Testando classe de persistencia
        // Salvando {
        // Persistencia persistencia = new Persistencia();
        // DadosSistema dados = new DadosSistema();

        // // Marca
        // Marca renault = new Marca(dados.gerarIdMarca(),"Renault");
        // dados.adicionarMarca(renault);

        // // Carro
        // Carro kwid = new Popular(
        //     dados.gerarIdCarro(),"13CD", renault, "Zen", 2018, "Preto", 
        //     0, true, "Americana", null, 0);
        // dados.adicionarCarro(kwid);

        // // Cliente
        // Cliente cliente = new Cliente(dados.gerarIdCliente(), "Amanda Silva", "123.456.789-11", "(11) 94948-1110", "amanda_silva@gamil.com", "Rua C", 35, null);
        // Cnh cnhAmanda = new Cnh("123456789", "B", "20/12/2006", null);
        // cliente.setCnh(cnhAmanda);
        // dados.adicionarCliente(cliente);

        // // Funcionário
        // Funcionario funcionario = new Funcionario(dados.gerarIdFuncionario(),null, "Alexandre Maranto", "123.456.789-11", "(11) 99999-0000");
        // Login loginAlex = new Login(12345, 12345678);
        // funcionario.setFuncLogin(loginAlex);
        // dados.adicionarFuncionario(funcionario);

        // // Locação
        // Locacao locacao = new Locacao(dados.gerarIdLocacao(), cliente, kwid, funcionario, "01/10/2026",
        //         "05/10/2026", "Completo");
        // dados.adicionarLocacao(locacao);´

        // Contrato contrato = new Contrato(dados.gerarIdContrato(),locacao);
        // dados.adicionarContrato(contrato);

        // persistencia.salvar(dados);

        // // Salva
        // persistencia.salvar(dados);

        // System.out.println("SALVO!");
        //}

        // Carregando{
        // Persistencia persistencia = new Persistencia();

        // DadosSistema dados = persistencia.carregar();

        // System.out.println("Marcas: " + dados.getMarcas().size());
        // System.out.println("Carros: " + dados.getCarros().size());
        // System.out.println("Clientes: " + dados.getClientes().size());
        // System.out.println("Funcionários: " + dados.getFuncionarios().size());
        // System.out.println("Locações: " + dados.getLocacoes().size());

        // Locacao loc = dados.getLocacoes().get(0);

        // System.out.println(loc.getCliente());
        // System.out.println(loc.getCarro());
        // System.out.println(loc.getFuncionario());

        // Contrato contrato = dados.getContratos().get(0);

        // System.out.println(contrato);

        // System.out.println(contrato.getLocacoes().getCliente().getNome());

        // System.out.println(contrato.getLocacoes().getCarro().getModelo());
        //}

        // Testes iniciais de como as classes estao funcionando + criação de PDF{
        // // Lidando com Marca + Carros
        // ArrayList<Marca> marcas = new ArrayList<>();
        // Marca marca = new Marca("Renault");
        // marcas.add(marca);

        // ArrayList<Carro> carros = new ArrayList<>();
        // Carro kwidVerm = new Popular("12AB", marca, "Zen", 2018, "Vermelho", 0, true, "Americana", null, 0);
        // Carro kwidPret = new Popular("13CD", marca, "Zen", 2018, "Preto", 0, true, "Americana", null, 0) ;
        // carros.add(kwidPret);
        // carros.add(kwidVerm);

        // // Lidando com Funcionários
        // ArrayList<Funcionario> funcionarios = new ArrayList<>();
        // Funcionario funcionario = new Funcionario(null, "Alexandre Maranto", "123.456.789-11", "(11) 99999-0000");
        // Login loginAlex = new Login(12345, 12345678);
        // funcionario.setFuncLogin(loginAlex);
        // funcionarios.add(funcionario);

        // // Lidando com Clientes
        // ArrayList<Cliente> clientes = new ArrayList<>();
        // Cliente amanda = new Cliente("Amanda Silva", "123.456.789-11", "(11) 94948-1110", "amanda_silva@gamil.com", "Rua C", 35, null);
        // Cliente sebastiao = new Cliente("Sebastiao Camargo", "321.654.987-24", "(12) 99987-1232", "sebasCamargo@gmail.com", "Alameda Z", 23, null);
        // Cnh cnhAmanda = new Cnh("123456789", "B", "20/12/2006", null);
        // Cnh cnhSebastiao = new Cnh("928765432", "AB", "02/02/2023", null);
        // amanda.setCnh(cnhAmanda);
        // sebastiao.setCnh(cnhSebastiao);
        // clientes.add(amanda);
        // clientes.add(sebastiao);

        // // Lidando com as Locações
        // ArrayList<Locacao> locacoes = new ArrayList<>();
        // Locacao locacao = new Locacao(sebastiao, kwidPret, funcionario, "01/12/2025", "03/12/2025", "Intermediario");
        // Locacao locacao2 = new Locacao(sebastiao, kwidVerm, funcionario, "20/01/2026", "27/01/2026", "Básico");
        // locacoes.add(locacao);
        // locacoes.add(locacao2);

        // locacao.aceiteLocacao();
        // locacao.finalizarLocacao(100, false, "03/12/2025", true, "Americana");
        // locacao2.aceiteLocacao();
        
        // // Contratos
        // ArrayList<Contrato> contratos = new ArrayList<>();
        // Contrato contratoLocacao = new Contrato(locacao);
        // Contrato contratoLocacao2 = new Contrato(locacao2);
        // contratos.add(contratoLocacao);
        // contratos.add(contratoLocacao2);
        
        // contratoLocacao.marcarComoAssinado();
        // System.out.println(contratoLocacao.getAssinado());
        // GerarPdf pdfContLoc = new GerarPdf();
        // pdfContLoc.gerar(contratoLocacao);
        
        // contratoLocacao2.marcarComoAssinado();
        // System.out.println(contratoLocacao2.getAssinado());
        // GerarPdf pdfContLoc2 = new GerarPdf();
        // pdfContLoc2.gerar(contratoLocacao2);

        // // Relatorios
        // Relatorio relatorioFrota = new RelatorioFrota(LocalDate.now(), carros);
        // GerarPdf pdfRelatFrot = new GerarPdf();
        // pdfRelatFrot.gerar(relatorioFrota);
        
        // Relatorio relatorioFinanceiro = new RelatorioFinanceiro(LocalDate.now(), locacoes, "2026");
        // GerarPdf pdfRelatFinan = new GerarPdf();
        // pdfRelatFinan.gerar(relatorioFinanceiro);

        // Relatorio relatorioLocacao = new RelatorioLocacao(LocalDate.now(), "02/02/2026", "02/03/2026", locacoes);
        // GerarPdf pdfRelatLoc = new GerarPdf();
        // pdfRelatLoc.gerar(relatorioLocacao);
    // }
    }
}
