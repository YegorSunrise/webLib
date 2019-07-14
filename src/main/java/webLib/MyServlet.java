package webLib;

import model.Exmpl;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
// http://pvrworld-wildfly.blogspot.com/2016/11/getting-started-guide-wildfly.html как настроить mySQL в JBoss
@WebServlet(urlPatterns = "/")
public class MyServlet extends HttpServlet {

    @Inject
    private Exmpl exmpl;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session == null) {
            resp.getWriter().append("nothing to show");
        } else {
            resp.getWriter().append("USE service method to show.\n"
                    + " getMethod: " + req.getMethod() + "\n"
                    + " getServerPort: " + req.getServerPort() + "\n"
                    + " req.getServletContext() " + req.getServletContext() + "\n"
                    + "session.getId()" + session.getId() + "\n"
                    + "req.getAuthType() " + req.getAuthType() + "\n"
                    + "req.getContextPath() " + req.getContextPath() + "\n"
                    + "req.getPathInfo() " + req.getPathInfo() + "\n"
                    + "req.getRequestURI() " + req.getRequestURI() + "\n"
                    + "Arrays.toString(req.getCookies()) " + Arrays.toString(req.getCookies()) + "\n"
                    + "req.getLocalName() " + req.getLocalName());
            resp.getWriter().append("============||============").append("\n").append(exmpl.getInner());
        }
    }
}
