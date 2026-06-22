<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.beans.Ordine" %>
<%@ page import="model.beans.DettaglioOrdine" %>
<%@ page import="model.beans.Utente" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    Ordine ordine = (Ordine) request.getAttribute("ordine");
    List<DettaglioOrdine> dettagli = (List<DettaglioOrdine>) request.getAttribute("dettagli");
    Utente utente = (Utente) session.getAttribute("utente");

    BigDecimal totale = (BigDecimal) request.getAttribute("totale");
    BigDecimal iva = (BigDecimal) request.getAttribute("iva");
    BigDecimal imponibile = (BigDecimal) request.getAttribute("imponibile");

    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String dataFormattata = ordine.getDataOrdine().format(df);
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Fattura n. <%= ordine.getIdOrdine() %></title>
    <style>

        /* ============================
           STILE BROWSER
        ============================ */
        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: Arial, sans-serif;
            font-size: 13px;
            color: #333;
            background: #f4f4f4;
            padding: 30px 20px;
        }

        .no-print {
            text-align: center;
            margin-bottom: 24px;
        }

        .btn-stampa {
            background: #FFC266;
            border: none;
            padding: 12px 32px;
            font-size: 15px;
            font-weight: 700;
            border-radius: 8px;
            cursor: pointer;
            color: #fff;
        }

        .btn-stampa:hover { background: #8C5A2B; }

        .btn-indietro {
            background: #e0e0e0;
            border: none;
            padding: 12px 24px;
            font-size: 15px;
            font-weight: 700;
            border-radius: 8px;
            cursor: pointer;
            color: #333;
            margin-right: 12px;
        }

        .btn-indietro:hover { background: #bbb; }

        .invoice-box {
            max-width: 800px;
            margin: 0 auto;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.10);
            overflow: hidden;
        }

        .invoice-header {
            background: #FFC266;
            padding: 24px 36px;
        }

        .invoice-header .brand {
            font-size: 26px;
            font-weight: 900;
            color: #fff;
            letter-spacing: 3px;
        }

        .invoice-header .brand span { color: #8C5A2B; }

        .invoice-body { padding: 30px 36px; }

        .info-table { width: 100%; margin-bottom: 24px; }
        .info-table td { vertical-align: top; padding: 0; }
        .col-left { width: 60%; }
        .col-right { width: 40%; text-align: right; }

        .label {
            font-size: 10px;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 1px;
            color: #8C5A2B;
            margin-bottom: 4px;
        }

        .value { font-size: 13px; color: #333; line-height: 1.7; }
        .value strong { font-size: 15px; color: #1a1a1a; }

        .invoice-title {
            text-align: center;
            font-size: 20px;
            font-weight: 700;
            color: #333;
            border-top: 2px solid #FFC266;
            border-bottom: 2px solid #FFC266;
            padding: 10px 0;
            margin-bottom: 24px;
        }

        .client-box {
            background: #fff8f0;
            border-left: 4px solid #FFC266;
            border-radius: 6px;
            padding: 14px 18px;
            margin-bottom: 24px;
        }

        table.products {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            font-size: 13px;
        }

        table.products thead tr { background: #FFC266; color: #fff; }
        table.products thead th { padding: 10px 12px; text-align: left; font-size: 12px; font-weight: 700; }
        table.products thead th.r { text-align: right; }
        table.products tbody tr { border-bottom: 1px solid #eee; }
        table.products tbody tr:nth-child(even) { background: #fafafa; }
        table.products tbody td { padding: 10px 12px; }
        table.products tbody td.r { text-align: right; }

        table.totals {
            width: 40%;
            margin-left: 60%;
            border-collapse: collapse;
            font-size: 13px;
        }

        table.totals td { padding: 6px 12px; }
        table.totals td.r { text-align: right; }
        table.totals tr.sep td { border-top: 1px solid #ddd; }
        table.totals tr.total-final td {
            background: #FFC266;
            color: #fff;
            font-weight: 700;
            font-size: 15px;
            padding: 10px 12px;
        }

        .invoice-footer {
            background: #f9f9f9;
            border-top: 1px solid #eee;
            padding: 16px 36px;
            text-align: center;
            font-size: 11px;
            color: #aaa;
        }

        /* ============================
           MEDIA QUERY STAMPA / PDF
        ============================ */
        @media print {

            body {
                background: #fff !important;
                padding: 0 !important;
            }

            .no-print { display: none !important; }

            .invoice-box {
                box-shadow: none !important;
                border-radius: 0 !important;
                max-width: 100% !important;
            }

            .invoice-header {
                background: #FFC266 !important;
                -webkit-print-color-adjust: exact;
                print-color-adjust: exact;
            }

            table.products thead tr {
                background: #FFC266 !important;
                -webkit-print-color-adjust: exact;
                print-color-adjust: exact;
            }

            table.totals tr.total-final td {
                background: #FFC266 !important;
                -webkit-print-color-adjust: exact;
                print-color-adjust: exact;
            }

            .client-box {
                background: #fff8f0 !important;
                -webkit-print-color-adjust: exact;
                print-color-adjust: exact;
            }

            table { page-break-inside: avoid; }
            tr { page-break-inside: avoid; }
        }
			

    </style>
</head>
<body>

    <!-- BOTTONI SOLO NEL BROWSER -->
    <div class="no-print">
       <a href="<%= request.getContextPath() %>/home"
   class="btn-indietro">
    &#8592; Torna alla Home
</a>

        <button class="btn-stampa" onclick="window.print()">&#128438; Scarica / Stampa PDF</button>
    </div>

    <div class="invoice-box">

        <!-- HEADER -->
        <div class="invoice-header">
            <div class="brand">FIT<span>EXTREME</span></div>
        </div>

        <!-- BODY -->
        <div class="invoice-body">

            <!-- INFO AZIENDA + DATI FATTURA -->
            <table class="info-table">
                <tr>
                    <td class="col-left">
                        <div class="label">Emittente</div>
                        <div class="value">
                            <strong>FitExtreme S.r.l.</strong><br/>
                            Via dello Sport, 1<br/>
                            00100 Roma (RM)<br/>
                            P.IVA: 12345678901<br/>
                            info@fitextreme.it
                        </div>
                    </td>
                    <td class="col-right">
                        <div class="label">Data fattura</div>
                        <div class="value"><%= dataFormattata %></div>
                        <br/>
                        <div class="label">Numero ordine</div>
                        <div class="value">#<%= ordine.getIdOrdine() %></div>
                    </td>
                </tr>
            </table>

            <!-- TITOLO -->
            <div class="invoice-title">Fattura n. <%= ordine.getIdOrdine() %></div>

            <!-- CLIENTE -->
            <div class="label">Intestata a</div>
            <div class="client-box">
                <strong><%= utente.getNome() %> <%= utente.getCognome() %></strong><br/>
                Email: <%= utente.getEmail() %>
            </div>

            <!-- PRODOTTI -->
            <div class="label" style="margin-bottom:8px;">Dettaglio ordine</div>
            <table class="products">
                <thead>
                    <tr>
                        <th>Articolo</th>
                        <th class="r">Quantit&#224;</th>
                        <th class="r">Prezzo unitario</th>
                        <th class="r">Subtotale</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (DettaglioOrdine d : dettagli) { %>
                    <tr>
                        <td><%= d.getNomeArticolo() %></td>
                        <td class="r"><%= d.getQuantita() %></td>
                        <td class="r"><%= d.getPrezzoAcquisto() %> &#8364;</td>
                        <td class="r"><%= d.getSubtotale() %> &#8364;</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>

            <!-- TOTALI -->
            <table class="totals">
                <tr>
                    <td>Imponibile</td>
                    <td class="r"><%= imponibile %> &#8364;</td>
                </tr>
                <tr class="sep">
                    <td>IVA (22%)</td>
                    <td class="r"><%= iva %> &#8364;</td>
                </tr>
                <tr class="total-final">
                    <td>Totale IVA inclusa</td>
                    <td class="r"><%= totale %> &#8364;</td>
                </tr>
            </table>

        </div>

        <!-- FOOTER -->
        <div class="invoice-footer">
            FitExtreme S.r.l. &#8212; Grazie per il tuo acquisto! &#8212; www.fitextreme.it
        </div>

    </div>

</body>
</html>
