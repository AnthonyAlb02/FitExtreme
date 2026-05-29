package model.DAO;

import model.beans.Ordine;

import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class OrdineDAO implements DaoInterface<Ordine, Integer> {

    private static final String TABLE_NAME = "Ordine";
    private static final DataSource ds;

    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ecommerce");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    // -------------------------------
    //   METODO DI ESTRAZIONE COMPLETO
    // -------------------------------
    private Ordine extractOrdine(ResultSet rs) throws SQLException {
        Ordine o = new Ordine();

        o.setIdOrdine(rs.getInt("ID_Ordine"));
        o.setIdUtente(rs.getInt("ID_Utente"));
        o.setIdAmministratore((Integer) rs.getObject("ID_Amministratore"));
        o.setDataOrdine(rs.getDate("Data_Ordine").toLocalDate());
        o.setStatoAvanzamento(rs.getString("Stato_Avanzamento"));
        o.setImportoTotale(rs.getBigDecimal("Importo_Totale"));

        return o;
    }

    // ⭐ Estrae ordine + nome utente (o "Utente eliminato")
    private Ordine extractOrdineWithUser(ResultSet rs) throws SQLException {
        Ordine o = extractOrdine(rs);

        String nome = rs.getString("nome");
        String cognome = rs.getString("cognome");

        if (nome == null || cognome == null) {
            o.setNomeUtente("Utente eliminato");
        } else {
            o.setNomeUtente(nome + " " + cognome);
        }

        return o;
    }

    // ---------------------------------------
    //   RECUPERA ORDINI PER UTENTE + DATA
    // ---------------------------------------
    public List<Ordine> doRetrieveByUserAndDate(int idUtente, LocalDate data) throws SQLException {

        String query =
                "SELECT o.*, u.nome, u.cognome " +
                "FROM Ordine o " +
                "LEFT JOIN Utente u ON o.ID_Utente = u.ID_Utente " +
                "WHERE o.ID_Utente=?";

        if (data != null) {
            query += " AND DATE(o.Data_Ordine) = ?";
        }

        query += " ORDER BY o.ID_Ordine DESC";

        List<Ordine> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUtente);

            if (data != null) {
                ps.setDate(2, java.sql.Date.valueOf(data));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(extractOrdineWithUser(rs));
            }
        }

        return lista;
    }

    // -------------------------------
    //   RECUPERA ORDINE PER PK
    // -------------------------------
    @Override
    public Ordine doRetrieveByKey(Integer pk) throws SQLException {

        String query =
                "SELECT o.*, u.nome, u.cognome " +
                "FROM Ordine o " +
                "LEFT JOIN Utente u ON o.ID_Utente = u.ID_Utente " +   // <--- spazio aggiunto
                "WHERE o.ID_Ordine=?";                                 // <--- ora è valido

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractOrdineWithUser(rs);
            }
        }
        return null;
    }

    // -------------------------------
    //   CONTA ORDINI
    // -------------------------------
    public int countOrders() {
        String query = "SELECT COUNT(*) FROM Ordine";

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

    // -------------------------------
    //   SALVA ORDINE + RITORNA PK
    // -------------------------------
    public int doSaveAndReturnKey(Ordine o) throws SQLException {

        String query = "INSERT INTO " + TABLE_NAME +
                " (ID_Utente, ID_Amministratore, Data_Ordine, Stato_Avanzamento, Importo_Totale) " +
                "VALUES (?, ?, ?, ?, ?)";

        int generatedKey = -1;

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, o.getIdUtente());
            ps.setObject(2, o.getIdAmministratore());
            ps.setDate(3, java.sql.Date.valueOf(o.getDataOrdine()));
            ps.setString(4, o.getStatoAvanzamento());
            ps.setBigDecimal(5, o.getImportoTotale());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
        }

        return generatedKey;
    }

    // -------------------------------
    //   RECUPERA TUTTI GLI ORDINI
    // -------------------------------
    @Override
    public Collection<Ordine> doRetrieveAll(String order) throws SQLException {

        String query =
                "SELECT o.*, u.nome, u.cognome " +
                "FROM Ordine o " +
                "LEFT JOIN Utente u ON o.ID_Utente = u.ID_Utente";

        if (order != null) {
            switch (order) {
                case "data_asc":
                    query += " ORDER BY o.Data_Ordine ASC";
                    break;
                case "data_desc":
                    query += " ORDER BY o.Data_Ordine DESC";
                    break;
                case "importo_asc":
                    query += " ORDER BY o.Importo_Totale ASC";
                    break;
                case "importo_desc":
                    query += " ORDER BY o.Importo_Totale DESC";
                    break;
                case "id_asc":
                    query += " ORDER BY o.ID_Ordine ASC";
                    break;
            }
        }

        Collection<Ordine> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(extractOrdineWithUser(rs));
            }
        }

        return lista;
    }

    // -------------------------------
    //   RECUPERA ORDINI PER UTENTE
    // -------------------------------
    public List<Ordine> doRetrieveByUser(int idUtente) throws SQLException {

        String query =
                "SELECT o.*, u.nome, u.cognome " +
                "FROM Ordine o " +
                "LEFT JOIN Utente u ON o.ID_Utente = u.ID_Utente " +
                "WHERE o.ID_Utente=? " +
                "ORDER BY o.ID_Ordine DESC";

        List<Ordine> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(extractOrdineWithUser(rs));
            }
        }

        return lista;
    }

    // -------------------------------
    //   SALVA ORDINE
    // -------------------------------
    @Override
    public void doSave(Ordine o) throws SQLException {

        String query = "INSERT INTO " + TABLE_NAME +
                " (ID_Utente, ID_Amministratore, Data_Ordine, Stato_Avanzamento, Importo_Totale) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, o.getIdUtente());
            ps.setObject(2, o.getIdAmministratore());
            ps.setDate(3, java.sql.Date.valueOf(o.getDataOrdine()));
            ps.setString(4, o.getStatoAvanzamento());
            ps.setBigDecimal(5, o.getImportoTotale());

            ps.executeUpdate();
        }
    }

    // -------------------------------
    //   UPDATE ORDINE
    // -------------------------------
    @Override
    public void doUpdate(Ordine o) throws SQLException {

        String query = "UPDATE " + TABLE_NAME +
                " SET ID_Utente=?, ID_Amministratore=?, Data_Ordine=?, Stato_Avanzamento=?, Importo_Totale=? " +
                "WHERE ID_Ordine=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, o.getIdUtente());
            ps.setObject(2, o.getIdAmministratore());
            ps.setDate(3, java.sql.Date.valueOf(o.getDataOrdine()));
            ps.setString(4, o.getStatoAvanzamento());
            ps.setBigDecimal(5, o.getImportoTotale());
            ps.setInt(6, o.getIdOrdine());

            ps.executeUpdate();
        }
    }

    // -------------------------------
    //   DELETE ORDINE
    // -------------------------------
    @Override
    public boolean doDelete(Integer pk) throws SQLException {

        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID_Ordine=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            return ps.executeUpdate() > 0;
        }
    }
}
