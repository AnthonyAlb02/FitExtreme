package model.DAO;

import model.beans.Recensione;

import javax.naming.*;
import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class RecensioneDAO implements DaoInterface<Recensione, Integer> {

    private static final String TABLE_NAME = "Recensione";
    private static final DataSource ds;

    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ecommerce"); // CORRETTO
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Recensione doRetrieveByKey(Integer pk) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Recensione=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractRecensione(rs);
            }
        }
        return null;
    }

    @Override
    public Collection<Recensione> doRetrieveAll(String order) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            query += " ORDER BY " + order;
        }

        Collection<Recensione> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(extractRecensione(rs));
            }
        }
        return lista;
    }

    @Override
    public void doSave(Recensione r) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME +
                " (ID_Utente, ID_Articolo, Voto, Commento, Data_Recensione) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, r.getIdUtente());
            ps.setInt(2, r.getIdArticolo());
            ps.setInt(3, r.getVoto());
            ps.setString(4, r.getCommento());
            ps.setDate(5, Date.valueOf(r.getDataRecensione()));

            ps.executeUpdate();
        }
    }

    @Override
    public void doUpdate(Recensione r) throws SQLException {
        String query = "UPDATE " + TABLE_NAME +
                " SET ID_Utente=?, ID_Articolo=?, Voto=?, Commento=?, Data_Recensione=? " +
                "WHERE ID_Recensione=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, r.getIdUtente());
            ps.setInt(2, r.getIdArticolo());
            ps.setInt(3, r.getVoto());
            ps.setString(4, r.getCommento());
            ps.setDate(5, Date.valueOf(r.getDataRecensione()));
            ps.setInt(6, r.getIdRecensione());

            ps.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Integer pk) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID_Recensione=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            return ps.executeUpdate() > 0;
        }
    }

    // ⭐ NUOVO: tutte le recensioni di un articolo
    public List<Recensione> doRetrieveByArticolo(int idArticolo) throws SQLException {
        String sql = "SELECT * FROM Recensione WHERE ID_Articolo=? ORDER BY Data_Recensione DESC";

        List<Recensione> lista = new ArrayList<>();

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idArticolo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(extractRecensione(rs));
            }
        }

        return lista;
    }

    // ⭐ NUOVO: controlla se un utente ha già recensito
    public boolean hasUserReviewed(int idUtente, int idArticolo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Recensione WHERE ID_Utente=? AND ID_Articolo=?";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUtente);
            ps.setInt(2, idArticolo);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

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
