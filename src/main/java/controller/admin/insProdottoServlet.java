package controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.DAO.ArticoloDAO;
import model.DAO.CategoriaDAO;
import model.beans.Articolo;
import model.beans.Categoria;

import java.io.File;
import java.util.Collection;

@WebServlet("/admin/insProdotto")
@MultipartConfig
public class insProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public insProdottoServlet() {
        super();
    }

    // ---------------------- GET ----------------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);

        if (sessione == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Boolean isAdmin = (Boolean) sessione.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect(request.getContextPath() + "/accesso-negato.jsp");
            return;
        }

        try {
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Collection<Categoria> categorie = categoriaDAO.doRetrieveAll("");
            request.setAttribute("categorie", categorie);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher =
            getServletContext().getRequestDispatcher("/admin/insProdotto.jsp");
        dispatcher.forward(request, response);
    }

    // ---------------------- POST ----------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);

        if (sessione == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Boolean isAdmin = (Boolean) sessione.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect(request.getContextPath() + "/accesso-negato.jsp");
            return;
        }

        String nome        = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String prezzo      = request.getParameter("prezzo");
        String quantita    = request.getParameter("quantita");
        String categoria   = request.getParameter("categoria");
        Part   filePart    = request.getPart("immagine");

        // ---------------------- VALIDAZIONE ----------------------
        if (nome == null || nome.trim().isEmpty()) {
            request.setAttribute("errore", "Il nome è obbligatorio.");
            doGet(request, response); return;
        }
        if (descrizione == null || descrizione.trim().isEmpty()) {
            request.setAttribute("errore", "La descrizione è obbligatoria.");
            doGet(request, response); return;
        }
        if (prezzo == null || prezzo.trim().isEmpty()) {
            request.setAttribute("errore", "Il prezzo è obbligatorio.");
            doGet(request, response); return;
        }
        try {
            BigDecimal p = new BigDecimal(prezzo);
            if (p.compareTo(BigDecimal.ZERO) <= 0) {
                request.setAttribute("errore", "Il prezzo deve essere maggiore di zero.");
                doGet(request, response); return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errore", "Il prezzo non è valido.");
            doGet(request, response); return;
        }
        if (quantita == null || quantita.trim().isEmpty()) {
            request.setAttribute("errore", "La quantità è obbligatoria.");
            doGet(request, response); return;
        }
        try {
            int q = Integer.parseInt(quantita);
            if (q < 0) {
                request.setAttribute("errore", "La quantità non può essere negativa.");
                doGet(request, response); return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errore", "La quantità non è valida.");
            doGet(request, response); return;
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            request.setAttribute("errore", "La categoria è obbligatoria.");
            doGet(request, response); return;
        }
        try {
            int c = Integer.parseInt(categoria);
            if (c <= 0) {
                request.setAttribute("errore", "Categoria non valida.");
                doGet(request, response); return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errore", "Categoria non valida.");
            doGet(request, response); return;
        }
        if (filePart == null || filePart.getSubmittedFileName() == null
                || filePart.getSubmittedFileName().trim().isEmpty()) {
            request.setAttribute("errore", "L'immagine è obbligatoria.");
            doGet(request, response); return;
        }

        try {
            
        	// ---------------------- SALVATAGGIO IMMAGINE ----------------------
        	String fileName = filePart.getSubmittedFileName();

        	// 1) Salva nel deploy (per servirla subito nell'app)
        	String deployPath = getServletContext().getRealPath("/utilities/immagini/");
        	File deployDir = new File(deployPath);
        	if (!deployDir.exists()) deployDir.mkdirs();
        	filePart.write(deployPath + File.separator + fileName);

        	// 2) Copia nel progetto sorgente (per vederla in Eclipse)
        	String projectPath = "C:\\Users\\aalba\\Desktop\\Progetti\\FitExtreme\\src\\main\\webapp\\utilities\\immagini\\";
        	File projectDir = new File(projectPath);
        	if (!projectDir.exists()) projectDir.mkdirs();

        	java.nio.file.Files.copy(
        	    new File(deployPath + File.separator + fileName).toPath(),
        	    new File(projectPath + File.separator + fileName).toPath(),
        	    java.nio.file.StandardCopyOption.REPLACE_EXISTING
        	);

            // ---------------------- CREAZIONE ARTICOLO ----------------------
            Articolo a = new Articolo();
            a.setNomeArticolo(nome.trim());
            a.setDescrizione(descrizione.trim());
            a.setPrezzoListino(new BigDecimal(prezzo));
            a.setQtaDisponibile(Integer.parseInt(quantita));
            a.setIdCategoria(Integer.parseInt(categoria));
            a.setImmagine(fileName); // solo il nome file nel DB

            ArticoloDAO dao = new ArticoloDAO();
            dao.doSave(a);

            response.sendRedirect(request.getContextPath() + "/admin/prodotti");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore durante l'inserimento del prodotto.");
            doGet(request, response);
        }
    }
}