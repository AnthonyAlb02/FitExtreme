package model.DAO;

import model.beans.Categoria;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class CategoriaDAO implements DaoInterface<Categoria, Integer> {

    private static final String TABLE_NAME = "Categoria";
    private static final DataSource ds;

  
    static {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ecommerce");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Categoria doRetrieveByKey(Integer pk) throws SQLException {
        // Recupero una categoria tramite la sua chiave primaria
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Categoria=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractCategoria(rs); // mappatura del  ResultSet nel bean Categoria
            }
        }
        return null; 
    }

    @Override
    public Collection<Categoria> doRetrieveAll(String order) throws SQLException {
        // Recupero tutte le categorie, con eventuale ordinamento
        String query = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.isEmpty()) {
            switch (order) {
                case "nome_asc":
                    query += " ORDER BY Nome ASC";
                    break;
                case "nome_desc":
                    query += " ORDER BY Nome DESC";
                    break;
                case "id_asc":
                    query += " ORDER BY ID_Categoria ASC";
                    break;
                case "id_desc":
                    query += " ORDER BY ID_Categoria DESC";
                    break;
                default:
                 
                    break;
            }
        }


        Collection<Categoria> lista = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(extractCategoria(rs)); // aggiungo ogni categoria alla lista
            }
        }
        return lista;
    }

    @Override
    public void doSave(Categoria c) throws SQLException {
        // Inserisco una nuova categoria nel database
        String query = "INSERT INTO " + TABLE_NAME + " (Nome) VALUES (?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, c.getNome());
            ps.executeUpdate(); // eseguo l'INSERT
        }
    }

    @Override
    public void doUpdate(Categoria c) throws SQLException {
        // Aggiorno una categoria esistente
        String query = "UPDATE " + TABLE_NAME + " SET Nome=? WHERE ID_Categoria=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, c.getNome());
            ps.setInt(2, c.getIdCategoria());
            ps.executeUpdate(); // eseguo l'UPDATE
        }
    }

    @Override
    public boolean doDelete(Integer pk) throws SQLException {
        // Elimino una categoria tramite ID
        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID_Categoria=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, pk);
            return ps.executeUpdate() > 0; // true se almeno una riga è stata eliminata
        }
    }

    // Metodo di utilità per convertire una riga del ResultSet in un oggetto Categoria
    private Categoria extractCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setIdCategoria(rs.getInt("ID_Categoria"));
        c.setNome(rs.getString("Nome"));
        return c;
    }
}
