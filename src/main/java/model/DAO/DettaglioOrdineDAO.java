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

    // 🔥 DataSource corretto
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
            query += " ORDER BY " + order;
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
                " (ID_Ordine, ID_Articolo, Quantita, Prezzo_Acquisto, Subtotale) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, d.getIdOrdine());
            ps.setInt(2, d.getIdArticolo());
            ps.setInt(3, d.getQuantita());
            ps.setBigDecimal(4, d.getPrezzoAcquisto());
            ps.setBigDecimal(5, d.getSubtotale());

            ps.executeUpdate();
        }
    }

    // 🔥 Recupera tutti i dettagli di un ordine + nome articolo
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

                // 🔥 Recupero nome articolo
                Articolo art = articoloDAO.doRetrieveByKey(d.getIdArticolo());
                if (art != null) {
                    d.setNomeArticolo(art.getNomeArticolo());
                }

                lista.add(d);
            }
        }

        return lista;
    }

    @Override
    public void doUpdate(DettaglioOrdine d) throws SQLException {
        String query = "UPDATE " + TABLE_NAME +
                " SET ID_Ordine=?, ID_Articolo=?, Quantita=?, Prezzo_Acquisto=?, Subtotale=? " +
                "WHERE ID_Dettaglio=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, d.getIdOrdine());
            ps.setInt(2, d.getIdArticolo());
            ps.setInt(3, d.getQuantita());
            ps.setBigDecimal(4, d.getPrezzoAcquisto());
            ps.setBigDecimal(5, d.getSubtotale());
            ps.setInt(6, d.getIdDettaglio());

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

    // 🔥 Metodo di utilità per mappare il ResultSet
    private DettaglioOrdine extractDettaglio(ResultSet rs) throws SQLException {
        DettaglioOrdine d = new DettaglioOrdine();

        d.setIdDettaglio(rs.getInt("ID_Dettaglio"));
        d.setIdOrdine(rs.getInt("ID_Ordine"));
        d.setIdArticolo(rs.getInt("ID_Articolo"));
        d.setQuantita(rs.getInt("Quantita"));
        d.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
        d.setSubtotale(rs.getBigDecimal("Subtotale"));

        return d;
    }
}
