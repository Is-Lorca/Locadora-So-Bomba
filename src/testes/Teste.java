package testes;

import carros.Carro;
import carros.Marca;
import carros.Popular;
import clientes.Cliente;
import clientes.Cnh;
import documentos.Contrato;
import exceptions.CNHVencidaException;
import exceptions.TipoSeguroInvalidoException;
import exportacao.GerarPdf;
import funcionarios.Funcionario;
import funcionarios.Login;
import java.util.ArrayList;
import locacoes.Locacao;
import documentos.Relatorio;
import documentos.RelatorioFinanceiro;
import documentos.RelatorioFrota;
import documentos.RelatorioLocacao;

public class Teste {
    public static void main(String[] args) throws TipoSeguroInvalidoException, CNHVencidaException{
        // Lidando com Marca + Carros
        ArrayList<Marca> marcas = new ArrayList<>();
        Marca marca = new Marca("Renault");
        marcas.add(marca);

        ArrayList<Carro> carros = new ArrayList<>();
        Carro kwidVerm = new Popular("12AB", marca, "Zen", 2018, "Vermelho", 0, true, "Americana", null, 0);
        Carro kwidPret = new Popular("13CD", marca, "Zen", 2018, "Preto", 0, true, "Americana", null, 0) ;
        carros.add(kwidPret);
        carros.add(kwidVerm);

        // Lidando com Funcionários
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        Funcionario funcionario = new Funcionario(null, "Alexandre Maranto", "123.456.789-11", "(11) 99999-0000");
        Login loginAlex = new Login(12345, 12345678);
        funcionario.setFuncLogin(loginAlex);
        funcionarios.add(funcionario);

        // Lidando com Clientes
        ArrayList<Cliente> clientes = new ArrayList<>();
        Cliente amanda = new Cliente("Amanda Silva", "123.456.789-11", "(11) 94948-1110", "amanda_silva@gamil.com", "Rua C", 35, null);
        Cliente sebastiao = new Cliente("Sebastiao Camargo", "321.654.987-24", "(12) 99987-1232", "sebasCamargo@gmail.com", "Alameda Z", 23, null);
        Cnh cnhAmanda = new Cnh("123456789", "B", "20/12/2006", null);
        Cnh cnhSebastiao = new Cnh("928765432", "AB", "02/02/2023", null);
        amanda.setCnh(cnhAmanda);
        sebastiao.setCnh(cnhSebastiao);
        clientes.add(amanda);
        clientes.add(sebastiao);

        // Lidando com as Locações
        ArrayList<Locacao> locacoes = new ArrayList<>();
        Locacao locacao = new Locacao(sebastiao, kwidPret, funcionario, "01/12/2025", "03/12/2025", "Intermediario");
        Locacao locacao2 = new Locacao(sebastiao, kwidVerm, funcionario, "20/01/2026", "27/01/2026", "Básico");
        locacoes.add(locacao);
        locacoes.add(locacao2);

        locacao.aceiteLocacao();
        locacao.finalizarLocacao(100, false, "03/12/2025", true, "Americana");
        locacao2.aceiteLocacao();

        // Contratos
        ArrayList<Contrato> contratos = new ArrayList<>();
        Contrato contratoLocacao = new Contrato(locacao);
        Contrato contratoLocacao2 = new Contrato(locacao2);
        contratos.add(contratoLocacao);
        contratos.add(contratoLocacao2);
        
        contratoLocacao.marcarComoAssinado();
        System.out.println(contratoLocacao.getAssinado());
        GerarPdf pdfContLoc = new GerarPdf();
        pdfContLoc.gerar(contratoLocacao);
        
        contratoLocacao2.marcarComoAssinado();
        System.out.println(contratoLocacao2.getAssinado());
        GerarPdf pdfContLoc2 = new GerarPdf();
        pdfContLoc2.gerar(contratoLocacao2);

        // Relatorios
        Relatorio relatorioFrota = new RelatorioFrota(LocalDate.now(), carros);
        GerarPdf pdfRelatFrot = new GerarPdf();
        pdfRelatFrot.gerar(relatorioFrota);
        
        Relatorio relatorioFinanceiro = new RelatorioFinanceiro(LocalDate.now(), locacoes, "2026");
        GerarPdf pdfRelatFinan = new GerarPdf();
        pdfRelatFinan.gerar(relatorioFinanceiro);

        Relatorio relatorioLocacao = new RelatorioLocacao(LocalDate.now(), "02/02/2026", "02/03/2026", locacoes);
        GerarPdf pdfRelatLoc = new GerarPdf();
        pdfRelatLoc.gerar(relatorioLocacao);
    }
}
