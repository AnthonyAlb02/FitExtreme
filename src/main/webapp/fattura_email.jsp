<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.beans.DettaglioOrdine" %>
<%@ page import="model.beans.Ordine" %>
<%@ page import="model.beans.Utente" %>

<%
    Ordine ordine = (Ordine) request.getAttribute("ordine");
    Utente utente = (Utente) request.getAttribute("utente");
    java.util.List<DettaglioOrdine> dettagli = (java.util.List<DettaglioOrdine>) request.getAttribute("dettagli");

    Object imponibile = request.getAttribute("imponibile");
    Object iva = request.getAttribute("iva");
    Object totale = request.getAttribute("totale");
    Object dataFormattata = request.getAttribute("dataFormattata");
%>

<table width="600" align="center" cellpadding="0" cellspacing="0" 
       style="background:#ffffff; border:1px solid #ddd; font-family:Arial;">

    <!-- HEADER -->
    <tr>
        <td style="background:#FFC266; padding:20px; text-align:center; color:#fff; font-size:24px; font-weight:bold;">
            FIT<span style="color:#8C5A2B;">EXTREME</span>
        </td>
    </tr>

    <!-- TITOLO -->
    <tr>
        <td style="padding:20px; text-align:center; font-size:20px; font-weight:bold; border-bottom:1px solid #eee;">
            Fattura n. <%= ordine.getIdOrdine() %>
        </td>
    </tr>

    <!-- DATI CLIENTE -->
    <tr>
        <td style="padding:20px; font-size:14px;">
            <strong>Intestata a:</strong><br/>
            <%= utente.getNome() %> <%= utente.getCognome() %><br/>
            <%= utente.getEmail() %><br/><br/>

            <strong>Data:</strong> <%= dataFormattata %><br/>
            <strong>Ordine:</strong> #<%= ordine.getIdOrdine() %>
        </td>
    </tr>

    <!-- DETTAGLI PRODOTTI -->
    <tr>
        <td style="padding:20px;">
            <table width="100%" cellpadding="6" cellspacing="0" style="border-collapse:collapse; font-size:13px;">
                <tr style="background:#FFC266; color:#fff;">
                    <th align="left">Articolo</th>
                    <th align="right">Q.tà</th>
                    <th align="right">Prezzo</th>
                  
                </tr>

                <% for (DettaglioOrdine d : dettagli) { %>
                <tr style="border-bottom:1px solid #eee;">
                    <td><%= d.getNomeArticolo() %></td>
                    <td align="right"><%= d.getQuantita() %></td>
                    <td align="right"><%= d.getPrezzoAcquisto() %> €</td>
                   
                </tr>
                <% } %>
            </table>
        </td>
    </tr>

    <!-- TOTALI -->
    <tr>
        <td style="padding:20px;">
            <table width="100%" cellpadding="6" cellspacing="0" style="font-size:14px;">
                <tr>
                    <td>Imponibile</td>
                    <td align="right"><%= imponibile %> €</td>
                </tr>
                <tr>
                    <td>IVA (22%)</td>
                    <td align="right"><%= iva %> €</td>
                </tr>
                <tr style="background:#FFC266; color:#fff; font-weight:bold;">
                    <td>Totale</td>
                    <td align="right"><%= totale %> €</td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- FOOTER -->
    <tr>
        <td style="background:#f3f3f3; padding:15px; text-align:center; font-size:12px; color:#777;">
            © FitExtreme – Grazie per il tuo acquisto
        </td>
    </tr>

</table>
