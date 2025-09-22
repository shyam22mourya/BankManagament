import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String type = req.getParameter("type");
        String user = req.getParameter("txtname");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        session.setAttribute("name", user);

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "system"
            );

            boolean isValid = false;

            if ("user".equals(type)) {
                String query = "SELECT * FROM employee WHERE name = ? AND password = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, user);
                ps.setString(2, password); // In production: hash password first

                rs = ps.executeQuery();

                if (rs.next()) {
                    isValid = true;
                    out.println("<h3>Welcome, " + user + "</h3>");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("user.html");
                    dispatcher.include(req, res);
                }

            } else{ 
                if("admin".equals(type)) {
                // In production: store admin credentials securely
                if ("simran".equals(user) && "admin".equals(password)) {
                    isValid = true;
                    out.println("<h3>Welcome Admin, " + user + "</h3>");
                    RequestDispatcher dispatcher = req.getRequestDispatcher("user.html");
                    dispatcher.forward(req, res);
                }
            }

            if (!isValid) {
                out.println("<script type='text/javascript'>");
                out.println("alert('Invalid username or password');");
                out.println("window.location = 'index.html';");
                out.println("</script>");
            }

       }
     } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Server error occurred. Please try again later.</h3>");
        }
    }
}