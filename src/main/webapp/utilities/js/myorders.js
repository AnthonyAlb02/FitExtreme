function filtraOrdini() {
    const data = document.getElementById("dataFiltro").value;

    fetch("filtraOrdini?data=" + data)
        .then(res => res.json())
        .then(ordini => {

            const container = document.getElementById("listaOrdini");
            container.innerHTML = "";

            if (ordini.length === 0) {
                container.innerHTML = `<p class="no-orders">Nessun ordine trovato per questa data.</p>`;
                return;
            }

            ordini.forEach(o => {

                const card = `
                    <div class="order-card fade-in">

                        <div class="order-header">
                            <span class="order-id">Ordine #${o.id}</span>
                            <span class="order-date">${o.data}</span>
                        </div>

                        <div class="order-body">
                            <div class="order-row">
                                <span>Totale:</span>
                                <strong>€ ${o.totale}</strong>
                            </div>

                            <div class="order-row">
                                <span>Stato:</span>
                                <span class="status">—</span>
                            </div>
                        </div>

                        <a class="btn-primary btn-small"
                           href="dettaglioOrdine?id=${o.id}">
                            Vedi dettagli
                        </a>

                    </div>
                `;

                container.innerHTML += card;
            });
        });
}
