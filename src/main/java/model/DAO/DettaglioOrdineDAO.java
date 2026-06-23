package model.DAO;

import model.beans.DettaglioOrdine;
import model.beans.Articolo;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DettaglioOrdineDAO implements DaoInterface<DettaglioOrdine, Integer> {

    private static final String TABLE_NAME = "Dettaglio_Ordine";
    private static DataSource ds;

    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ecommerce");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DettaglioOrdine doRetrieveByKey(Integer pk) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Dettaglio=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractDettaglio(rs);
            }
        }
        return null;
    }

    @Override
    public Collection<DettaglioOrdine> doRetrieveAll(String order) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            switch (order) {
                case "quantita_asc": query += " ORDER BY Quantita ASC"; break;
                case "quantita_desc": query += " ORDER BY Quantita DESC"; break;
                case "prezzo_asc": query += " ORDER BY Prezzo_Acquisto ASC"; break;
                case "prezzo_desc": query += " ORDER BY Prezzo_Acquisto DESC"; break;
                case "subtotale_asc": query += " ORDER BY Subtotale ASC"; break;
                case "subtotale_desc": query += " ORDER BY Subtotale DESC"; break;
            }
        }

        Collection<DettaglioOrdine> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(extractDettaglio(rs));
            }
        }
        return lista;
    }

    @Override
    public void doSave(DettaglioOrdine d) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME +
                " (ID_Ordine, ID_Articolo, Quantita, Prezzo_Acquisto, Subtotale, Nome_Articolo, Immagine) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, d.getIdOrdine());
            ps.setObject(2, d.getIdArticolo()); // può essere NULL
            ps.setInt(3, d.getQuantita());
            ps.setBigDecimal(4, d.getPrezzoAcquisto());
            ps.setBigDecimal(5, d.getSubtotale());
            ps.setString(6, d.getNomeArticolo());
            ps.setString(7, d.getImmagine());

            ps.executeUpdate();
        }
    }

    // ⭐ Recupera tutti i dettagli di un ordine + gestisce articoli eliminati
    public List<DettaglioOrdine> doRetrieveByOrdine(int idOrdine) throws SQLException {

        String query = "SELECT * FROM Dettaglio_Ordine WHERE ID_Ordine=?";
        List<DettaglioOrdine> lista = new ArrayList<>();

        ArticoloDAO articoloDAO = new ArticoloDAO();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idOrdine);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DettaglioOrdine d = extractDettaglio(rs);

                //  Recupero articolo completo
                Articolo art = articoloDAO.doRetrieveByKey(d.getIdArticolo());

                if (art != null) {
                    // Articolo ancora esistente
                    d.setNomeArticolo(art.getNomeArticolo());
                    d.setImmagine(art.getImmagine());
                } else {
                    // Articolo eliminato → uso i dati salvati nel dettaglio ordine
                    d.setNomeArticolo(rs.getString("Nome_Articolo"));
                    d.setImmagine(rs.getString("Immagine"));
                }

                lista.add(d);
            }
        }

        return lista;
    }

    @Override
    public void doUpdate(DettaglioOrdine d) throws SQLException {
        String query = "UPDATE " + TABLE_NAME +
                " SET ID_Ordine=?, ID_Articolo=?, Quantita=?, Prezzo_Acquisto=?, Subtotale=?, Nome_Articolo=?, Immagine=? " +
                "WHERE ID_Dettaglio=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, d.getIdOrdine());
            ps.setObject(2, d.getIdArticolo());
            ps.setInt(3, d.getQuantita());
            ps.setBigDecimal(4, d.getPrezzoAcquisto());
            ps.setBigDecimal(5, d.getSubtotale());
            ps.setString(6, d.getNomeArticolo());
            ps.setString(7, d.getImmagine());
            ps.setInt(8, d.getIdDettaglio());

            ps.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Integer pk) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID_Dettaglio=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            return ps.executeUpdate() > 0;
        }
    }

    //  Mappa il ResultSet → DettaglioOrdine
    private DettaglioOrdine extractDettaglio(ResultSet rs) throws SQLException {
        DettaglioOrdine d = new DettaglioOrdine();

        d.setIdDettaglio(rs.getInt("ID_Dettaglio"));
        d.setIdOrdine(rs.getInt("ID_Ordine"));
        d.setIdArticolo(rs.getInt("ID_Articolo"));
        d.setQuantita(rs.getInt("Quantita"));
        d.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
        d.setSubtotale(rs.getBigDecimal("Subtotale"));

        //  Dati salvati nel dettaglio ordine
        d.setNomeArticolo(rs.getString("Nome_Articolo"));
        d.setImmagine(rs.getString("Immagine"));

        return d;
    }
}
