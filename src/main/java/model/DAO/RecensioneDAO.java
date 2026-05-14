package model.DAO;

import model.beans.Recensione;

import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;

public class RecensioneDAO implements DaoInterface<Recensione, Integer> {

    private static final String TABLE_NAME = "Recensione";
    private static final DataSource ds;

   
    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/Symposium");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Recensione doRetrieveByKey(Integer pk) throws SQLException {
        // Recupero una recensione tramite la sua chiave primaria
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Recensione=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractRecensione(rs); // mappo il ResultSet nel bean Recensione
            }
        }
        return null; // se non trovo nulla ritorno null
    }

    @Override
    public Collection<Recensione> doRetrieveAll(String order) throws SQLException {
        // Recupero tutte le recensioni, con eventuale ordinamento
        String query = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            query += " ORDER BY " + order;
        }

        Collection<Recensione> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(extractRecensione(rs)); // aggiungo ogni recensione alla lista
            }
        }
        return lista;
    }

    @Override
    public void doSave(Recensione r) throws SQLException {
        // Inserisco una nuova recensione nel database
        String query = "INSERT INTO " + TABLE_NAME +
                " (ID_Utente, ID_Articolo, Voto, Commento, Data_Recensione) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, r.getIdUtente());
            ps.setInt(2, r.getIdArticolo());
            ps.setInt(3, r.getVoto());
            ps.setString(4, r.getCommento());
            ps.setDate(5, java.sql.Date.valueOf(r.getDataRecensione()));


            ps.executeUpdate(); // eseguo l'INSERT
        }
    }

    @Override
    public void doUpdate(Recensione r) throws SQLException {
        // Aggiorno una recensione esistente
        String query = "UPDATE " + TABLE_NAME +
                " SET ID_Utente=?, ID_Articolo=?, Voto=?, Commento=?, Data_Recensione=? " +
                "WHERE ID_Recensione=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, r.getIdUtente());
            ps.setInt(2, r.getIdArticolo());
            ps.setInt(3, r.getVoto());
            ps.setString(4, r.getCommento());
            ps.setDate(5, java.sql.Date.valueOf(r.getDataRecensione()));

            ps.setInt(6, r.getIdRecensione());

            ps.executeUpdate(); // eseguo l'UPDATE
        }
    }

    @Override
    public boolean doDelete(Integer pk) throws SQLException {
        // Elimino una recensione tramite ID
        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID_Recensione=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            return ps.executeUpdate() > 0; // true se almeno una riga è stata eliminata
        }
    }

    // Metodo di utilità per convertire una riga del ResultSet in un oggetto Recensione
    private Recensione extractRecensione(ResultSet rs) throws SQLException {
        Recensione r = new Recensione();

        r.setIdRecensione(rs.getInt("ID_Recensione"));
        r.setIdUtente(rs.getInt("ID_Utente"));
        r.setIdArticolo(rs.getInt("ID_Articolo"));
        r.setVoto(rs.getInt("Voto"));
        r.setCommento(rs.getString("Commento"));
        r.setDataRecensione(rs.getDate("Data_Recensione").toLocalDate());



        return r;
    }
}
