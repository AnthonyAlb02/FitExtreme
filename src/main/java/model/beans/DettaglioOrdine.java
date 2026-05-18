package model.beans;

import java.math.BigDecimal;

public class DettaglioOrdine {

    private int idDettaglio;
    private int idOrdine;
    private int idArticolo;
    private int quantita;
    private BigDecimal prezzoAcquisto;
    private BigDecimal subtotale;

    // ➕ Campi NON presenti nel DB, utili per JSP e ricevute
    private String nomeArticolo;
    private String immagine;   // ⭐ NUOVO CAMPO

    public DettaglioOrdine() {}

    public DettaglioOrdine(int idDettaglio, int idOrdine, int idArticolo, int quantita) {
        this.idDettaglio = idDettaglio;
        this.idOrdine = idOrdine;
        this.idArticolo = idArticolo;
        this.quantita = quantita;
    }

    public int getIdDettaglio() { return idDettaglio; }
    public void setIdDettaglio(int idDettaglio) { this.idDettaglio = idDettaglio; }

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public int getIdArticolo() { return idArticolo; }
    public void setIdArticolo(int idArticolo) { this.idArticolo = idArticolo; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public BigDecimal getPrezzoAcquisto() { return prezzoAcquisto; }
    public void setPrezzoAcquisto(BigDecimal prezzoAcquisto) { this.prezzoAcquisto = prezzoAcquisto; }

    public BigDecimal getSubtotale() { return subtotale; }
    public void setSubtotale(BigDecimal subtotale) { this.subtotale = subtotale; }

    // ➕ Getter/Setter per nome articolo
    public String getNomeArticolo() { return nomeArticolo; }
    public void setNomeArticolo(String nomeArticolo) { this.nomeArticolo = nomeArticolo; }

    // ⭐ Getter/Setter per immagine articolo
    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }

    @Override
    public String toString() {
        return "DettaglioOrdine{" +
                "idDettaglio=" + idDettaglio +
                ", idOrdine=" + idOrdine +
                ", idArticolo=" + idArticolo +
                ", quantita=" + quantita +
                ", nomeArticolo='" + nomeArticolo + '\'' +
                ", immagine='" + immagine + '\'' +
                '}';
    }
}
