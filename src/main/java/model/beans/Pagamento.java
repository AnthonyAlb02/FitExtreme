package model.beans;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pagamento {

    private int idPagamento;
    private int idOrdine;
    private LocalDate dataTransazione;
    private String metodoPagamento;
    private BigDecimal importoSaldato;

    public Pagamento() {}

    public Pagamento(int idPagamento, int idOrdine, BigDecimal importoSaldato) {
        this.idPagamento = idPagamento;
        this.idOrdine = idOrdine;
        this.importoSaldato = importoSaldato;
    }

    public int getIdPagamento() {
    	return idPagamento; 
    	
    }
    
    
    public void setIdPagamento(int idPagamento) {
    	
    	this.idPagamento = idPagamento; 
    	
    }
    

    public int getIdOrdine() {
    	return idOrdine; 
    	
    }
    
    
    public void setIdOrdine(int idOrdine) {
    	
    	this.idOrdine = idOrdine; 
    	
    }
    

    public LocalDate getDataTransazione() {
    	return dataTransazione; 
    	
    }
    
    
    public void setDataTransazione(LocalDate dataTransazione) {
    	this.dataTransazione = dataTransazione; 
    	
    }
    

    public String getMetodoPagamento() {
    	return metodoPagamento; 
    	
    }
    
    public void setMetodoPagamento(String metodoPagamento) {
    	
    	this.metodoPagamento = metodoPagamento; 
    	
    }
    

    public BigDecimal getImportoSaldato() { 
    	return importoSaldato; 
    	
    }
    public void setImportoSaldato(BigDecimal importoSaldato) {
    	this.importoSaldato = importoSaldato; 
    	
    
    }


}
