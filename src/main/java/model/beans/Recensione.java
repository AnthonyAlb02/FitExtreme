package model.beans;

import java.time.LocalDate;

public class Recensione {

    private int idRecensione;
    private int idUtente;
    private int idArticolo;
    private int voto;
    private String commento;
    private LocalDate dataRecensione;

    public Recensione() {}

    public Recensione(int idRecensione, int idUtente, int idArticolo, int voto) {
        this.idRecensione = idRecensione;
        this.idUtente = idUtente;
        this.idArticolo = idArticolo;
        this.voto = voto;
    }

    public int getIdRecensione() {
    	return idRecensione; 
    	
    }
    
    public void setIdRecensione(int idRecensione) {
    	this.idRecensione = idRecensione; 
    	
    }
    

    public int getIdUtente() {
    	return idUtente; 
    	
    }
    
    public void setIdUtente(int idUtente) {
    	
    	this.idUtente = idUtente; 
    	
    }

    public int getIdArticolo() {
    	
    	return idArticolo; 
    	
    }
    
    public void setIdArticolo(int idArticolo) {
    	this.idArticolo = idArticolo; 
    	
    }

    public int getVoto() {
    	return voto; 
    	
    }
    public void setVoto(int voto) { 
    	
    	this.voto = voto; 
    	
    }

    public String getCommento() {
    	
    	return commento; 
    	
    }
    public void setCommento(String commento) { 
    	
    	this.commento = commento; 
    	
    }

    public LocalDate getDataRecensione() {
    	return dataRecensione; 
    	
    }
    public void setDataRecensione(LocalDate dataRecensione) { 
    	this.dataRecensione = dataRecensione; 
    	
    }
}
