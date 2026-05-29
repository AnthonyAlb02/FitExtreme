package model.beans;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Ordine {

    private int idOrdine;
    private int idUtente;
    private Integer idAmministratore; // può essere null
    private LocalDate dataOrdine;
    private String statoAvanzamento;
    private BigDecimal importoTotale;

    
    private String nomeUtente;

    public Ordine() {}

    public Ordine(int idOrdine, int idUtente, LocalDate dataOrdine, String statoAvanzamento) {
        this.idOrdine = idOrdine;
        this.idUtente = idUtente;
        this.dataOrdine = dataOrdine;
        this.statoAvanzamento = statoAvanzamento;
    }

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public Integer getIdAmministratore() { return idAmministratore; }
    public void setIdAmministratore(Integer idAmministratore) { this.idAmministratore = idAmministratore; }

    public LocalDate getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(LocalDate dataOrdine) { this.dataOrdine = dataOrdine; }

    public String getStatoAvanzamento() { return statoAvanzamento; }
    public void setStatoAvanzamento(String statoAvanzamento) { this.statoAvanzamento = statoAvanzamento; }

    public BigDecimal getImportoTotale() { return importoTotale; }
    public void setImportoTotale(BigDecimal importoTotale) { this.importoTotale = importoTotale; }

    // ⭐ Getter/Setter aggiunti
    public String getNomeUtente() { return nomeUtente; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }

    @Override
    public String toString() {
        return "Ordine #" + idOrdine +
                " - Utente: " + (nomeUtente != null ? nomeUtente : idUtente) +
                " - Totale: " + importoTotale;
    }
}
