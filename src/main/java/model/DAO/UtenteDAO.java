package model.DAO;

import model.beans.Utente;

import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;

public class UtenteDAO implements DaoInterface<Utente, Integer> {

    private static final String TABLE_NAME = "Utente";
    private static final DataSource ds;

    // Recupero il DataSource tramite JNDI (stesso stile del progetto di esempio)
    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ecommerce");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Utente doRetrieveByKey(Integer pk) throws SQLException {
        // Recupero un utente tramite la sua chiave primaria
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Utente=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUtente(rs); // mappo il ResultSet nel bean Utente
            }
        }
        return null; // se non trovo nulla ritorno null
    }
    public Utente doRetrieveByEmail(String email) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE Email=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUtente(rs);
            }
        }
        return null;
    }
    public int countUsers() {
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }




    

    @Override
    public Collection<Utente> doRetrieveAll(String order) throws SQLException {
        // Recupero tutti gli utenti, con eventuale ordinamento
        String query = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            switch (order) {
                case "nome_asc":
                    query += " ORDER BY Nome ASC";
                    break;
                case "nome_desc":
                    query += " ORDER BY Nome DESC";
                    break;
                case "cognome_asc":
                    query += " ORDER BY Cognome ASC";
                    break;
                case "cognome_desc":
                    query += " ORDER BY Cognome DESC";
                    break;
                case "email_asc":
                    query += " ORDER BY Email ASC";
                    break;
                case "email_desc":
                    query += " ORDER BY Email DESC";
                    break;
                default:
                    
                    break;
            }
        }


        Collection<Utente> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(extractUtente(rs)); // aggiungo ogni utente alla lista
            }
        }
        return lista;
    }

    @Override
    public void doSave(Utente u) throws SQLException {
        // Inserisco un nuovo utente nel database
        String query = "INSERT INTO " + TABLE_NAME +
                " (Email, Data_Registrazione, Password_Hash, Nome, Cognome, Telefono, " +
                "Indirizzo_Spedizione, Session_ID, IP_Address, Livello_Accesso, Area_Competenza, Ruolo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, u.getEmail());
            ps.setDate(2, java.sql.Date.valueOf(u.getDataRegistrazione()));
            ps.setString(3, u.getPasswordHash());
            ps.setString(4, u.getNome());
            ps.setString(5, u.getCognome());
            ps.setString(6, u.getTelefono());
            ps.setString(7, u.getIndirizzoSpedizione());
            ps.setString(8, u.getSessionId());
            ps.setString(9, u.getIpAddress());
            ps.setObject(10, u.getLivelloAccesso());
            ps.setString(11, u.getAreaCompetenza());
            ps.setString(12, u.getRuolo());

            ps.executeUpdate(); // eseguo l'INSERT
        }
    }

    @Override
    public void doUpdate(Utente u) throws SQLException {
        // Aggiorno un utente esistente
        String query = "UPDATE " + TABLE_NAME +
                " SET Email=?, Data_Registrazione=?, Password_Hash=?, Nome=?, Cognome=?, Telefono=?, " +
                "Indirizzo_Spedizione=?, Session_ID=?, IP_Address=?, Livello_Accesso=?, Area_Competenza=?, Ruolo=? " +
                "WHERE ID_Utente=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, u.getEmail());
            ps.setDate(2, java.sql.Date.valueOf(u.getDataRegistrazione()));
            ps.setString(3, u.getPasswordHash());
            ps.setString(4, u.getNome());
            ps.setString(5, u.getCognome());
            ps.setString(6, u.getTelefono());
            ps.setString(7, u.getIndirizzoSpedizione());
            ps.setString(8, u.getSessionId());
            ps.setString(9, u.getIpAddress());
            ps.setObject(10, u.getLivelloAccesso());
            ps.setString(11, u.getAreaCompetenza());
            ps.setString(12, u.getRuolo());
            ps.setInt(13, u.getIdUtente());

            ps.executeUpdate(); // eseguo l'UPDATE
        }
    }

    @Override
    public boolean doDelete(Integer pk) throws SQLException {
        // Elimino un utente tramite ID
        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID_Utente=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            return ps.executeUpdate() > 0; // true se almeno una riga è stata eliminata
        }
    }

    // Metodo di utilità per convertire una riga del ResultSet in un oggetto Utente
    private Utente extractUtente(ResultSet rs) throws SQLException {
        Utente u = new Utente();

        u.setIdUtente(rs.getInt("ID_Utente"));
        u.setEmail(rs.getString("Email"));
        u.setDataRegistrazione(rs.getDate("Data_Registrazione").toLocalDate());
        u.setPasswordHash(rs.getString("Password_Hash"));
        u.setNome(rs.getString("Nome"));
        u.setCognome(rs.getString("Cognome"));
        u.setTelefono(rs.getString("Telefono"));
        u.setIndirizzoSpedizione(rs.getString("Indirizzo_Spedizione"));
        u.setSessionId(rs.getString("Session_ID"));
        u.setIpAddress(rs.getString("IP_Address"));
        u.setLivelloAccesso((Integer) rs.getObject("Livello_Accesso"));
        u.setAreaCompetenza(rs.getString("Area_Competenza"));
        u.setRuolo(rs.getString("Ruolo"));

        return u;
    }
}
