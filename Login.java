
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String type = req.getParameter("user_type");
        String user = req.getParameter("username");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        RequestDispatcher rd;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            Connection c = DriverManager.getConnection(url, "root", "123456");

            if ("user".equals(type)) {

                session.setAttribute("user", user);
                session.setAttribute("pass", password);

                PreparedStatement ps = c.prepareStatement("SELECT password FROM Employee WHERE name=? AND password=? ");
                ps.setString(1, user);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String dbPassword = rs.getString("password");
                    if (password.equals(dbPassword)) {
                        out.println("Welcome " + user);
                        rd = req.getRequestDispatcher("user.html");
                        rd.forward(req, res);
                    } else {
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Invalid Password !');");
                        out.println("</script>");
                        rd = req.getRequestDispatcher("login.html");
                        rd.include(req, res);
                    }
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Invalid username and password!');");
                    out.println("</script>");
                    rd = req.getRequestDispatcher("login.html");
                    rd.include(req, res);
                }
            } else if ("admin".equals(type)) { // admin
                session.setAttribute("user", "virendra");
                session.setAttribute("pass", "password");
                if (user.equals("virendra") && password.equals("password")) {
                    out.println("Welcome " + user);
                    rd = req.getRequestDispatcher("admin.html");
                    rd.forward(req, res);
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('!Invalid password ');");
                    out.println("</script>");
                    rd = req.getRequestDispatcher("login.html");
                    rd.include(req, res);
                }
            } else {
                out.println("<script type='text/javascript'>");
                out.println("alert('Please select userType!');");
                out.println("</script>");
                rd = req.getRequestDispatcher("login.html");
                rd.include(req, res);
            }

        } catch (Exception e) {
            out.println("Exception: " + e.getMessage());
        }
    }
}
