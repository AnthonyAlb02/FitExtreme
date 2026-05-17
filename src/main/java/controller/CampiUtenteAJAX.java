package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/campiUtente")
public class CampiUtenteAJAX extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CampiUtenteAJAX() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String campo = request.getParameter("campo");
        String valore = request.getParameter("valore");

        if (campo == null || valore == null) {
            out.print("{\"valid\": false, \"error\": \"Parametri mancanti\"}");
            return;
        }

        valore = valore.trim();

        switch (campo) {

            case "nome":
                if (valore.length() < 2) {
                    out.print("{\"valid\": false, \"error\": \"Il nome è troppo corto\"}");
                } else {
                    out.print("{\"valid\": true}");
                }
                break;

            case "cognome":
                if (valore.length() < 2) {
                    out.print("{\"valid\": false, \"error\": \"Il cognome è troppo corto\"}");
                } else {
                    out.print("{\"valid\": true}");
                }
                break;

            case "telefono":
                if (!valore.matches("^[0-9]{7,15}$")) {
                    out.print("{\"valid\": false, \"error\": \"Telefono non valido\"}");
                } else {
                    out.print("{\"valid\": true}");
                }
                break;

            case "indirizzo":
                if (valore.length() < 5) {
                    out.print("{\"valid\": false, \"error\": \"Indirizzo troppo corto\"}");
                } else {
                    out.print("{\"valid\": true}");
                }
                break;

            default:
                out.print("{\"valid\": false, \"error\": \"Campo non riconosciuto\"}");
                break;
        }
    }
}
