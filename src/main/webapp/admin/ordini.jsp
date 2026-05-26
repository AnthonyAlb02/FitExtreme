<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.beans.Ordine" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Gestione Ordini</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/utilities/css/admin.css">
</head>
<body>

<div class="admin-content">

    <h1 class="admin-title">Gestione Ordini</h1>

    <!-- FILTRI -->
    <div style="margin-bottom: 20px; display: flex; gap: 10px; align-items: center; flex-wrap: wrap;">

        <input type="text"
               id="filtroNome"
               placeholder="Filtra per nome utente..."
               oninput="filtraOrdini()"
               style="padding: 9px 14px; border-radius: 6px; border: 1px solid #ddd;
                      font-family: 'Poppins', sans-serif; font-size: 0.9rem; width: 220px;">

        <input type="date"
               id="filtroData"
               oninput="filtraOrdini()"
               style="padding: 9px 14px; border-radius: 6px; border: 1px solid #ddd;
                      font-family: 'Poppins', sans-serif; font-size: 0.9rem;">

        <button onclick="resetFiltri();" class="btn btn-secondary">Reset</button>

    </div>

    <table class="admin-table" id="tabellaOrdini">
        <thead>
            <tr>
                <th>ID Ordine</th>
                <th>Utente</th>
                <th>Data</th>
                <th>Stato</th>
                <th>Importo</th>
            </tr>
        </thead>
        <tbody>
        <%
            Collection<Ordine> ordini = (Collection<Ordine>) request.getAttribute("ordini");
            Map<Integer, String> nomiUtenti = (Map<Integer, String>) request.getAttribute("nomiUtenti");

            if (ordini != null) {
                for (Ordine o : ordini) {
                    String nomeUtente = nomiUtenti != null
                        ? nomiUtenti.getOrDefault(o.getIdUtente(), "Utente #" + o.getIdUtente())
                        : "Utente #" + o.getIdUtente();
        %>
        <tr data-nome="<%= nomeUtente.toLowerCase() %>"
            data-data="<%= o.getDataOrdine() %>"
            onclick="location.href='<%= request.getContextPath() %>/admin/dettaglioOrdine?id=<%= o.getIdOrdine() %>'"
            style="cursor: pointer;">
            <td><%= o.getIdOrdine() %></td>
            <td><%= nomeUtente %></td>
            <td><%= o.getDataOrdine() %></td>
            <td><%= o.getStatoAvanzamento() %></td>
            <td>€ <%= o.getImportoTotale() %></td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

</div>

<script>
function filtraOrdini() {
    const filtroNome = document.getElementById("filtroNome").value.trim().toLowerCase();
    const filtroData = document.getElementById("filtroData").value;
    const righe = document.querySelectorAll("#tabellaOrdini tbody tr");

    righe.forEach(riga => {
        const nome = riga.dataset.nome;
        const data = riga.dataset.data;

        const nomeOk = nome.includes(filtroNome);
        const dataOk = filtroData === "" || data === filtroData;

        riga.style.display = (nomeOk && dataOk) ? "" : "none";
    });
}

function resetFiltri() {
    document.getElementById("filtroNome").value = "";
    document.getElementById("filtroData").value = "";
    filtraOrdini();
}
</script>

</body>
</html>