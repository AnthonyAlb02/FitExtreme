package utilities;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import model.beans.DettaglioOrdine;
import model.beans.Ordine;
import model.beans.Utente;

public class InvoiceService {

    public static byte[] generateInvoicePDFBytes(Ordine ordine, List<DettaglioOrdine> dettagli, Utente utente) throws Exception {

        // 1) Costruisci l'HTML tramite il builder
        String html = InvoiceTemplateBuilder.buildInvoiceHTML(ordine, dettagli, utente);

        // 2) Output in memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 3) Costruzione PDF
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(html, null);
        builder.toStream(baos);
        builder.run();

        return baos.toByteArray();
    }
}
