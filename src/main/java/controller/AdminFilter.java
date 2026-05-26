package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // 1) Utente non loggato → login
        if (session == null || session.getAttribute("utente") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2) Controllo ruolo — isAdmin è sempre Boolean grazie alla LoginServlet corretta
        Object isAdminAttr = session.getAttribute("isAdmin");

        if (!(isAdminAttr instanceof Boolean) || !(Boolean) isAdminAttr) {
            resp.sendRedirect(req.getContextPath() + "/accesso-negato.jsp");
            return;
        }

        // 3) Utente admin → continua
        chain.doFilter(request, response);
    }

    @Override public void init(FilterConfig f) {}
    @Override public void destroy() {}
}