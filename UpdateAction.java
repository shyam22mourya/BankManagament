import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateAction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String fieldName = req.getParameter("fieldname");
        String value = req.getParameter("value");
        String acc = req.getParameter("acc");

        String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = "root";
        String pass = "123456";

        // Allowed fields
        List<String> validFields = Arrays.asList("MOBILENO", "EMAIL", "FATHERNAME", "ACCOUNTTYPE", "BALANCE");

        if (!validFields.contains(fieldName.toUpperCase())) {
            out.println("<h3>Invalid field name!</h3>");
            return;
        }

        try {
            // Use latest MySQL driver
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            String sql = "UPDATE accounddata SET " + fieldName + "=? WHERE ACCOUNTNO=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, value);
            ps.setString(2, acc);

            int i = ps.executeUpdate();

            if (i > 0) {
                // Forward back to account details page
                RequestDispatcher rd = req.getRequestDispatcher("accountDetail");
                rd.forward(req, res);
            } else {
                out.println("<h3>No record updated. Please check Account Number!</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace(out); // Print error on the page for debugging
        }
    }
}
