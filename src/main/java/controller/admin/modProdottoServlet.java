package controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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

@WebServlet("/admin/modProdotto")
@MultipartConfig
public class modProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    RequestDispatcher dispatcher;

    public modProdottoServlet() {
        super();
    }

    // ---------------------- GET ----------------------
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        dispatcher = getServletContext().getRequestDispatcher("/admin/modProdotto.jsp");

        HttpSession sessione = request.getSession(false);

        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        Boolean isAdmin = (Boolean) sessione.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("../errorePermessi");
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("prodotti");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            ArticoloDAO dao = new ArticoloDAO();
            Articolo prodotto = dao.doRetrieveByKey(id);

            if (prodotto == null) {
                response.sendRedirect("prodotti");
                return;
            }

            request.setAttribute("prodotto", prodotto);

            // ⭐ Carico categorie
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Collection<Categoria> categorie = categoriaDAO.doRetrieveAll("");
            request.setAttribute("categorie", categorie);

            dispatcher.forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("prodotti");
        }
    }

    // ---------------------- POST ----------------------
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessione = request.getSession(false);

        if (sessione == null) {
            response.sendRedirect("../login");
            return;
        }

        Boolean isAdmin = (Boolean) sessione.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("../errorePermessi");
            return;
        }

        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String prezzo = request.getParameter("prezzo");
        String quantita = request.getParameter("quantita");
        String categoria = request.getParameter("categoria");

        // ⭐ Nuova immagine (opzionale)
        Part filePart = request.getPart("immagine");
        String nuovoNomeImmagine = null;

        try {
            ArticoloDAO dao = new ArticoloDAO();
            Articolo a = dao.doRetrieveByKey(Integer.parseInt(id));

            if (a == null) {
                response.sendRedirect("prodotti");
                return;
            }

            // ⭐ Se è stata caricata una nuova immagine
            if (filePart != null && filePart.getSubmittedFileName() != null &&
                !filePart.getSubmittedFileName().trim().isEmpty()) {

                nuovoNomeImmagine = filePart.getSubmittedFileName();

                // Percorso deploy
                String deployPath = getServletContext().getRealPath("/utilities/immagini/");
                File deployDir = new File(deployPath);
                if (!deployDir.exists()) deployDir.mkdirs();

                filePart.write(deployPath + File.separator + nuovoNomeImmagine);

                // Percorso progetto
                String projectPath = "C:\\Users\\aalba\\Desktop\\Progetti\\FitExtreme\\src\\main\\webapp\\utilities\\immagini\\";
                File projectDir = new File(projectPath);
                if (!projectDir.exists()) projectDir.mkdirs();

                Files.copy(
                    new File(deployPath + File.separator + nuovoNomeImmagine).toPath(),
                    new File(projectPath + File.separator + nuovoNomeImmagine).toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );

                // Aggiorno immagine nel DB
                a.setImmagine(nuovoNomeImmagine);
            }

            // Aggiorno gli altri campi
            a.setNomeArticolo(nome);
            a.setDescrizione(descrizione);
            a.setPrezzoListino(new BigDecimal(prezzo));
            a.setQtaDisponibile(Integer.parseInt(quantita));
            a.setIdCategoria(Integer.parseInt(categoria));

            dao.doUpdate(a);

            sessione.setAttribute("messaggioSuccesso", "Prodotto modificato correttamente.");
            response.sendRedirect("prodotti");
            return;

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();

            request.setAttribute("errore", "Errore durante la modifica del prodotto.");
            dispatcher = getServletContext().getRequestDispatcher("/admin/modProdotto.jsp");
            dispatcher.forward(request, response);
        }
    }
}
