package exportacao;

// aponta algo errado mas esta funcionando -\o/-

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;

import documentos.Impressao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GerarPdf {
    
    public static void gerar(Impressao criado){
        String texto = criado.gerarConteudo();

        // Divide a string onde existe o \n
        String[] linhas = texto.split("\n");

        String titulo = linhas[0].replace(" ", "");
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = dataAtual.format(formata);

        String caminhoArquivo = titulo + "_" + data + ".pdf";
    
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            // Abre o fluxo de conteúdo para manipular a página
            // O try-with-resources garante que o stream seja fechado no final
            try (PDPageContentStream contentStream = new PDPageContentStream(documento, pagina)) {
                
                // 1. Inicia o bloco de texto
                contentStream.beginText();
                
                // 2. Define a fonte e o tamanho (Fonte padrão do PDFBox 2.x)
                PDType1Font fonte = new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD);
                contentStream.setFont(fonte, 18);
                
                // 3. Define a posição inicial do texto (X=100, Y=700 é perto do topo)
                contentStream.newLineAtOffset(100, 700);
                
                // Define o espaçamento entre as linhas (leading)
                contentStream.setLeading(14.5f);

                // Percorre as linhas aplicando a quebra no PDF
                for (String linha : linhas) {
                    contentStream.showText(linha);
                    contentStream.newLine(); // Substitui o efeito do \n, movendo o cursor para baixo
                }

                // 5. Encerra o bloco de texto
                contentStream.endText();
                
            } // O contentStream é fechado automaticamente aqui

            documento.save(new File(caminhoArquivo));
            System.out.println("PDF com texto gerado com sucesso em: " + new File(caminhoArquivo).getAbsolutePath());

        } catch (Exception e) {
            System.err.println("Erro ao manipular o PDF: " + e.getMessage());
        }
    }

}
