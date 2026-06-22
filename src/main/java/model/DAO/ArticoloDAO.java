	package model.DAO;
	
	import model.beans.Articolo;
	
	import javax.naming.*;
	import javax.sql.*;
	
	import java.math.BigDecimal;
	import java.sql.*;
	import java.util.*;
	
	public class ArticoloDAO implements DaoInterface<Articolo, Integer> {
	
	    private static final String TABLE_NAME = "Articolo";
	    private static final DataSource ds;
	
	    static {
	        try {
	            Context ctx = new InitialContext();
	            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ecommerce");
	        } catch (NamingException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
	    public Collection<Articolo> doRetrieveHomeList(int limit) throws SQLException {
	        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY RAND() LIMIT ?";
	        Collection<Articolo> lista = new ArrayList<>();
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.setInt(1, limit);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) lista.add(extractArticolo(rs));
	        }
	        return lista;
	    }
	
	    @Override
	    public Articolo doRetrieveByKey(Integer pk) throws SQLException {
	        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Articolo=?";
	
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	
	            ps.setInt(1, pk);
	            ResultSet rs = ps.executeQuery();
	
	            if (rs.next()) {
	                return extractArticolo(rs);
	            }
	        }
	        return null;
	    }
	
	    @Override
	    public Collection<Articolo> doRetrieveAll(String order) throws SQLException {
	        String query = "SELECT * FROM " + TABLE_NAME;
	        if (order != null && !order.isEmpty()) {
	            switch (order) {
	                case "nome_asc":
	                    query += " ORDER BY Nome_Articolo ASC";
	                    break;
	                case "nome_desc":
	                    query += " ORDER BY Nome_Articolo DESC";
	                    break;
	                case "prezzo_asc":
	                    query += " ORDER BY Prezzo_Listino ASC";
	                    break;
	                case "prezzo_desc":
	                    query += " ORDER BY Prezzo_Listino DESC";
	                    break;
	                case "qta_asc":
	                    query += " ORDER BY Qta_Disponibile ASC";
	                    break;
	                case "qta_desc":
	                    query += " ORDER BY Qta_Disponibile DESC";
	                    break;
	                default:
	                   
	                    break;
	            }
	        }
	
	
	        Collection<Articolo> lista = new ArrayList<>();
	
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query);
	             ResultSet rs = ps.executeQuery()) {
	
	            while (rs.next()) {
	                lista.add(extractArticolo(rs));
	            }
	        }
	        return lista;
	    }
	
	    public Collection<Articolo> doRetrieveByCategoria(int idCategoria) throws SQLException {
	        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID_Categoria=?";
	
	        Collection<Articolo> lista = new ArrayList<>();
	
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	
	            ps.setInt(1, idCategoria);
	            ResultSet rs = ps.executeQuery();
	
	            while (rs.next()) {
	                lista.add(extractArticolo(rs));
	            }
	        }
	        return lista;
	    }
	
	    public Collection<Articolo> doSearch(String keyword) throws SQLException {
	        String query = "SELECT * FROM Articolo WHERE Nome_Articolo LIKE ? OR Descrizione LIKE ?";
	
	        Collection<Articolo> lista = new ArrayList<>();
	
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	
	            String pattern = "%" + keyword + "%";
	            ps.setString(1, pattern);
	            ps.setString(2, pattern);
	
	            ResultSet rs = ps.executeQuery();
	
	            while (rs.next()) {
	                lista.add(extractArticolo(rs));
	            }
	        }
	        return lista;
	    }
	
	    public Collection<Articolo> doFilter(Integer idCategoria, BigDecimal min, BigDecimal max) throws SQLException {
	
	        StringBuilder query = new StringBuilder("SELECT * FROM Articolo WHERE 1=1");
	
	        if (idCategoria != null) query.append(" AND ID_Categoria = ?");
	        if (min != null) query.append(" AND Prezzo_Listino >= ?");
	        if (max != null) query.append(" AND Prezzo_Listino <= ?");
	
	        Collection<Articolo> lista = new ArrayList<>();
	
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query.toString())) {
	
	            int index = 1;
	
	            if (idCategoria != null) ps.setInt(index++, idCategoria);
	            if (min != null) ps.setBigDecimal(index++, min);
	            if (max != null) ps.setBigDecimal(index++, max);
	
	            ResultSet rs = ps.executeQuery();
	
	            while (rs.next()) {
	                lista.add(extractArticolo(rs));
	            }
	        }
	
	        return lista;
	    }
	
	    public Collection<Articolo> doFilterOrder(Integer idCategoria, BigDecimal min, BigDecimal max, String order)
	            throws SQLException {
	
	        StringBuilder query = new StringBuilder("SELECT * FROM Articolo WHERE 1=1");
	
	        if (idCategoria != null) query.append(" AND ID_Categoria = ?");
	        if (min != null) query.append(" AND Prezzo_Listino >= ?");
	        if (max != null) query.append(" AND Prezzo_Listino <= ?");
	
	        if (order != null && !order.isEmpty()) {
	            switch (order) {
	                case "prezzo_asc":
	                    query.append(" ORDER BY Prezzo_Listino ASC");
	                    break;
	                case "prezzo_desc":
	                    query.append(" ORDER BY Prezzo_Listino DESC");
	                    break;
	                case "nome_asc":
	                    query.append(" ORDER BY Nome_Articolo ASC");
	                    break;
	                case "nome_desc":
	                    query.append(" ORDER BY Nome_Articolo DESC");
	                    break;
	                default:
	                    break;
	            }
	        }
	
	        Collection<Articolo> lista = new ArrayList<>();
	
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query.toString())) {
	
	            int index = 1;
	
	            if (idCategoria != null) ps.setInt(index++, idCategoria);
	            if (min != null) ps.setBigDecimal(index++, min);
	            if (max != null) ps.setBigDecimal(index++, max);
	
	            ResultSet rs = ps.executeQuery();
	
	            while (rs.next()) {
	                lista.add(extractArticolo(rs));
	            }
	        }
	
	        return lista;
	    }
	
	    @Override
	    public void doSave(Articolo a) throws SQLException {
	        String query = "INSERT INTO " + TABLE_NAME +
	                " (Nome_Articolo, Descrizione, Prezzo_Listino, Qta_Disponibile, ID_Categoria, Immagine) " +
	                "VALUES (?, ?, ?, ?, ?, ?)";
	
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	
	            ps.setString(1, a.getNomeArticolo());
	            ps.setString(2, a.getDescrizione());
	            ps.setBigDecimal(3, a.getPrezzoListino());
	            ps.setInt(4, a.getQtaDisponibile());
	            ps.setObject(5, a.getIdCategoria());
	            ps.setString(6, a.getImmagine());
	
	            ps.executeUpdate();
	        }
	    }
	
	    @Override
	    public void doUpdate(Articolo a) throws SQLException {
	        String query = "UPDATE " + TABLE_NAME +
	                " SET Nome_Articolo=?, Descrizione=?, Prezzo_Listino=?, Qta_Disponibile=?, ID_Categoria=?, Immagine=? " +
	                "WHERE ID_Articolo=?";
	
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	
	            ps.setString(1, a.getNomeArticolo());
	            ps.setString(2, a.getDescrizione());
	            ps.setBigDecimal(3, a.getPrezzoListino());
	            ps.setInt(4, a.getQtaDisponibile());
	            ps.setObject(5, a.getIdCategoria());
	            ps.setString(6, a.getImmagine());
	            ps.setInt(7, a.getIdArticolo());
	
	            ps.executeUpdate();
	        }
	    }
	
	    @Override
	    public boolean doDelete(Integer pk) throws SQLException {
	        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID_Articolo=?";
	
	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(query)) {
	
	            ps.setInt(1, pk);
	            return ps.executeUpdate() > 0;
	        }
	    }
	
	    private Articolo extractArticolo(ResultSet rs) throws SQLException {
	        Articolo a = new Articolo();
	
	        a.setIdArticolo(rs.getInt("ID_Articolo"));
	        a.setNomeArticolo(rs.getString("Nome_Articolo"));
	        a.setDescrizione(rs.getString("Descrizione"));
	        a.setPrezzoListino(rs.getBigDecimal("Prezzo_Listino"));
	        a.setQtaDisponibile(rs.getInt("Qta_Disponibile"));
	        a.setIdCategoria((Integer) rs.getObject("ID_Categoria"));
	        a.setImmagine(rs.getString("Immagine"));
	
	        return a;
	    }
	    public int countProducts() {
	        String query = "SELECT COUNT(*) FROM Articolo";

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

	}
	
