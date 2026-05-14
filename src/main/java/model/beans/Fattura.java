package model.beans;

import java.time.LocalDate;

public class Fattura {

    private int idFattura;
    private int idOrdine;
    private String numeroFattura;
    private LocalDate dataEmissione;

    public Fattura() {}

    public Fattura(int idFattura, int idOrdine, String numeroFattura) {
        this.idFattura = idFattura;
        this.idOrdine = idOrdine;
        this.numeroFattura = numeroFattura;
    }

    public int getIdFattura() { return idFattura; }
    public void setIdFattura(int idFattura) { this.idFattura = idFattura; }

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public String getNumeroFattura() { return numeroFattura; }
    public void setNumeroFattura(String numeroFattura) { this.numeroFattura = numeroFattura; }

    public LocalDate getDataEmissione() { return dataEmissione; }
    public void setDataEmissione(LocalDate dataEmissione) { this.dataEmissione = dataEmissione; }

 
}
