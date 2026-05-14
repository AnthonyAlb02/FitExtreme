package model.beans;

import java.math.BigDecimal;

public class Articolo {

    private int idArticolo;
    private String nomeArticolo;
    private String descrizione;
    private BigDecimal prezzoListino;
    private int qtaDisponibile;

    private Integer idCategoria; 
    private String immagine;   // ⭐ NUOVO CAMPO

    public Articolo() {}

    public Articolo(int idArticolo, String nomeArticolo, BigDecimal prezzoListino) {
        this.idArticolo = idArticolo;
        this.nomeArticolo = nomeArticolo;
        this.prezzoListino = prezzoListino;
    }

    public int getIdArticolo() {
        return idArticolo;
    }

    public void setIdArticolo(int idArticolo) {
        this.idArticolo = idArticolo;
    }

    public String getNomeArticolo() {
        return nomeArticolo;
    }

    public void setNomeArticolo(String nomeArticolo) {
        this.nomeArticolo = nomeArticolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getPrezzoListino() {
        return prezzoListino;
    }

    public void setPrezzoListino(BigDecimal prezzoListino) {
        this.prezzoListino = prezzoListino;
    }

    public int getQtaDisponibile() {
        return qtaDisponibile;
    }

    public void setQtaDisponibile(int qtaDisponibile) {
        this.qtaDisponibile = qtaDisponibile;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    // ⭐ GETTER E SETTER PER L’IMMAGINE
    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    @Override
    public String toString() {
        return nomeArticolo + " (" + prezzoListino + "€)";
    }
}
