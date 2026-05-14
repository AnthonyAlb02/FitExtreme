package model.beans;

import java.time.LocalDate;

public class Utente {

    private int idUtente;
    private String email;
    private LocalDate dataRegistrazione;

    // Attributi utente registrato
    private String passwordHash;
    private String nome;
    private String cognome;
    private String telefono;
    private String indirizzoSpedizione;

    // Attributi guest
    private String sessionId;
    private String ipAddress;

    // Attributi amministratore
    private Integer livelloAccesso;
    private String areaCompetenza;

    // Ruolo
    private String ruolo; // registrato, guest, admin

    public Utente() {}

    public Utente(int idUtente, String email, LocalDate dataRegistrazione, String ruolo) {
        this.idUtente = idUtente;
        this.email = email;
        this.dataRegistrazione = dataRegistrazione;
        this.ruolo = ruolo;
    }

    // GETTER & SETTER
    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDataRegistrazione() { return dataRegistrazione; }
    public void setDataRegistrazione(LocalDate dataRegistrazione) { this.dataRegistrazione = dataRegistrazione; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getIndirizzoSpedizione() { return indirizzoSpedizione; }
    public void setIndirizzoSpedizione(String indirizzoSpedizione) { this.indirizzoSpedizione = indirizzoSpedizione; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public Integer getLivelloAccesso() { return livelloAccesso; }
    public void setLivelloAccesso(Integer livelloAccesso) { this.livelloAccesso = livelloAccesso; }

    public String getAreaCompetenza() { return areaCompetenza; }
    public void setAreaCompetenza(String areaCompetenza) { this.areaCompetenza = areaCompetenza; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }


}
