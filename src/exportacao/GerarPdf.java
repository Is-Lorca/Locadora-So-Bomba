package exportacao;

// aponta algo errado mas esta funcionando -\o/-

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;

import documentos.Impressao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import documentos.Contrato;

public class GerarPdf {
    // Variáveis de controle de página que precisam ser acessadas globalmente no método
    private PDPageContentStream contentStreamAtual = null;
    private float yAtual = 750; // Controla a altura atual do cursor
    private final float limiteRodape = 50f; // Quando o Y chegar aqui, cria uma nova página
    private final float espacamentoLinha = 16f; // O valor do leading
    

    public GerarPdf() {

    }

    public void gerar(Impressao criado){
        String texto = criado.gerarConteudo();

        // Divide a string onde existe o \n
        String[] linhas = texto.split("\n");
        
        // Pega o título do nosso arquivo
        String titulo = linhas[0];
        
        // Cria as "diretrizes" para nomearmos o documento 
        String nomePdf = titulo.replace(" ", "");
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String data = dataAtual.format(formata);
        
        // Cria o caminho do arquivo
        String caminhoArquivo;
        if(criado instanceof Contrato){
            Contrato contrato = (Contrato) criado;
            caminhoArquivo = nomePdf + "-N°" + contrato.getNumeroContrato() + "_" + data + ".pdf";
        }
        else{
            caminhoArquivo = nomePdf + "_" + data + ".pdf";
        }
    
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            // Definição das margens
            float margemEsquerda = 70f;  
            float margemDireita = 70f;   
            float larguraDaPagina = pagina.getMediaBox().getWidth();
            float larguraUtil = larguraDaPagina - margemEsquerda - margemDireita;

            // Instancia as fontes (Modo correto PDFBox 3.x)
            PDFont fonteNegrito = new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD);
            PDFont fonteCorpo = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
            
            // Abre o primeiro contentStream manualmente (não usamos try-with-resources aqui porque ele pode mudar)
            contentStreamAtual = new PDPageContentStream(documento, pagina);
            contentStreamAtual.beginText();
            contentStreamAtual.setLeading(espacamentoLinha);
            
            float tamanhoTitulo = 16;
            yAtual = 750; // Define a altura inicial da primeira página

            // --- Bloco do Título ---
            if (titulo.startsWith("Relatório")) {
                contentStreamAtual.setFont(fonteNegrito, tamanhoTitulo);
                contentStreamAtual.newLineAtOffset(100, yAtual); 
                contentStreamAtual.showText(titulo);
                    
                contentStreamAtual.setFont(fonteCorpo, 12);   
                contentStreamAtual.newLine(); 
                yAtual -= espacamentoLinha;
                
                // Ajusta o cursor para a margem padrão das próximas linhas
                contentStreamAtual.newLineAtOffset(margemEsquerda - 100, 0);
            } else {
                contentStreamAtual.setFont(fonteNegrito, tamanhoTitulo);   
                float larguraDoTitulo = (fonteNegrito.getStringWidth(titulo) / 1000f) * tamanhoTitulo;   
                float xTitulo = (larguraDaPagina - larguraDoTitulo) / 2f;
                
                contentStreamAtual.newLineAtOffset(xTitulo, yAtual); 
                contentStreamAtual.showText(titulo);
                
                contentStreamAtual.setFont(fonteCorpo, 12);   
                contentStreamAtual.newLine();              
                yAtual -= espacamentoLinha;
                
                float voltarParaMargemEsquerda = margemEsquerda - xTitulo;
                contentStreamAtual.newLineAtOffset(voltarParaMargemEsquerda, 0);
            }

            // --- Bloco do Corpo do Texto (Com quebra de página automática) ---
            for (int i = 1; i < linhas.length; i++) {
                String linhaAtual = ArrayGetSafe(linhas, i);

                // Se for uma linha vazia, pula linha e desconta do limite vertical
                if (linhaAtual.isEmpty()) {
                    // Verifica se pular essa linha vazia estoura a página
                    if (yAtual - espacamentoLinha < limiteRodape) {
                        criarNovaPagina(documento, margemEsquerda);
                    } else {
                        contentStreamAtual.newLine();
                        yAtual -= espacamentoLinha;
                    }
                    continue;
                }
                
                // CORREÇÃO: Descobre a fonte correta baseado no conteúdo original da linha
                PDFont fonteAtiva = (linhaAtual.startsWith("CLÁUSULA") || 
                                     linhaAtual.startsWith("DECLARAÇÃO") || 
                                     linhaAtual.startsWith("ASSINATURAS")) ? fonteNegrito : fonteCorpo;
                
                // Quebra o parágrafo longo em sub-linhas menores
                List<String> subLinhas = quebrarLinha(linhaAtual, fonteAtiva, 12, larguraUtil);
                
                // Imprime cada sub-linha gerada controlando a altura vertical
                for (String subLinha : subLinhas) {
                    // Se a próxima linha for estourar o rodapé, cria uma página nova antes de escrever!
                    if (yAtual - espacamentoLinha < limiteRodape) {
                        criarNovaPagina(documento, margemEsquerda);
                    }
                    
                    // CORREÇÃO: Aqui aplicamos a fonteAtiva correta dinamicamente
                    contentStreamAtual.setFont(fonteAtiva, 12);
                    contentStreamAtual.showText(subLinha);
                    contentStreamAtual.newLine();
                    
                    yAtual -= espacamentoLinha; // Decrementa a altura atual do cursor
                }
            }

            // Fecha o último bloco de texto e o stream atual antes de salvar
            contentStreamAtual.endText();
            contentStreamAtual.close();

            caminhoArquivo = verificarArquivo(caminhoArquivo);
            documento.save(new File(caminhoArquivo));
            System.out.println("PDF gerado com sucesso em: " + new File(caminhoArquivo).getPath());
            
        } catch (Exception e) {
            System.err.println("Erro ao manipular o PDF: " + e.getMessage());
        }
    }

    /**
     * Fecha a página atual e cria uma nova estrutura de página limpa para continuar o texto
     */
    private void criarNovaPagina(PDDocument documento, float margemEsquerda) throws Exception {
        // 1. Fecha o fluxo da página que estava cheia
        contentStreamAtual.endText();
        contentStreamAtual.close();
        
        // 2. Cria e adiciona a nova folha
        PDPage novaPagina = new PDPage();
        documento.addPage(novaPagina);
        
        // 3. Abre um novo fluxo para a nova folha
        contentStreamAtual = new PDPageContentStream(documento, novaPagina);
        contentStreamAtual.beginText();
        contentStreamAtual.setLeading(espacamentoLinha);
        
        // 4. Reseta o cursor para o topo da folha nova e define a margem esquerda inicial
        yAtual = 750; 
        contentStreamAtual.newLineAtOffset(margemEsquerda, yAtual);
    }

    private List<String> quebrarLinha(String texto, PDFont fonte, float tamanhoFonte, float larguraMaxima) throws Exception {
        List<String> linhasResultantes = new ArrayList<>();
        String[] palavras = texto.split(" ");
        StringBuilder linhaAtual = new StringBuilder();

        for (String palavra : palavras) {
            if (linhaAtual.length() == 0) {
                linhaAtual.append(palavra);
            } else {
                String testarLinha = linhaAtual.toString() + " " + palavra;
                float larguraTestada = (fonte.getStringWidth(testarLinha) / 1000f) * tamanhoFonte;

                if (larguraTestada <= larguraMaxima) {
                    linhaAtual.append(" ").append(palavra);
                } else {
                    linhasResultantes.add(linhaAtual.toString());
                    linhaAtual = new StringBuilder(palavra);
                }
            }
        }

        if (linhaAtual.length() > 0) {
            linhasResultantes.add(linhaAtual.toString());
        }

        return linhasResultantes;
    }

    private String verificarArquivo(String caminho){
        File arquivo = new File(caminho);
        int contadorArquivo = 1;
        String novoCaminho = caminho;

        while(arquivo.exists()){
            String nome = caminho.substring(0, caminho.lastIndexOf("."));
            novoCaminho = nome + "_" + contadorArquivo + ".pdf";
            arquivo = new File(novoCaminho);
            contadorArquivo++;
        }

        return novoCaminho;
    }

    // Método utilitário apenas para garantir segurança no trim() das strings
    private String ArrayGetSafe(String[] array, int index) {
        if (index >= array.length || array[index] == null) return "";
        return array[index].trim();
    }
}