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

    // 🔥 Recupera ordini filtrati per data (se presente) e ordinati dal più recente
    public List<Ordine> doRetrieveByUserAndDate(int idUtente, LocalDate data) throws SQLException {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Utente=?";

        if (data != null) {
            query += " AND DATE(Data_Ordine) = ?";
        }

        // ⭐ Ordina dal più recente al meno recente
        query += " ORDER BY ID_Ordine DESC";

        List<Ordine> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUtente);

            if (data != null) {
                ps.setDate(2, java.sql.Date.valueOf(data));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(extractOrdine(rs));
            }
        }

        return lista;
    }

    @Override
    public Ordine doRetrieveByKey(Integer pk) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Ordine=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractOrdine(rs);
            }
        }
        return null;
    }
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


    // 🔥 Salva ordine e ritorna ID generato
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

    @Override
    public Collection<Ordine> doRetrieveAll(String order) throws SQLException {

        String query = "SELECT * FROM " + TABLE_NAME;

        // Evita NullPointerException
        if (order != null) {
            switch (order) {
                case "data_asc":
                    query += " ORDER BY Data_Ordine ASC";
                    break;
                case "data_desc":
                    query += " ORDER BY Data_Ordine DESC";
                    break;
                case "importo_asc":
                    query += " ORDER BY Importo_Totale ASC";
                    break;
                case "importo_desc":
                    query += " ORDER BY Importo_Totale DESC";
                    break;
                case "id_asc":
                    query += " ORDER BY ID_Ordine ASC";
                    break;
            }
        }

        Collection<Ordine> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(extractOrdine(rs));
            }
        }

        return lista;
    }

    // ⭐ Metodo usato dalla pagina "ordini.jsp"
    //    → ORA ordina correttamente dal più recente
    public List<Ordine> doRetrieveByUser(int idUtente) throws SQLException {

        String query = "SELECT * FROM Ordine WHERE ID_Utente=? ORDER BY ID_Ordine DESC";

        List<Ordine> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(extractOrdine(rs));
            }
        }

        return lista;
    }

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

    @Override
    public boolean doDelete(Integer pk) throws SQLException {

        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID_Ordine=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            return ps.executeUpdate() > 0;
        }
    }

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
}
