import java.io.*;  
import java.sql.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  

public class DebitHistory extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            // Load MySQL Driver
            Class.forName("com.mysql.jdbc.Driver"); 

            String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "123456");

            String query = "SELECT * FROM debit";
            st = con.createStatement();
            rs = st.executeQuery(query);

            // HTML Table
            out.println("<html><head><title>Debit History</title></head><body>");
            out.println("<h2>Debit Transaction History</h2>");
            out.println("<table border='1' cellpadding='5' cellspacing='0'>");
            out.println("<thead><tr><th>S.No</th><th>Name</th><th>Amount</th><th>Date</th></tr></thead>");
            out.println("<tbody>");

            int cnt = 1;
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + cnt + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("amount") + "</td>");
                // Change column name if different (e.g., "debit_date" instead of "DateCredit")
                out.println("<td>" + rs.getDate("DateCredit") + "</td>");
                out.println("</tr>");
                cnt++;
            }

            out.println("</tbody></table>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (st != null) st.close(); } catch (Exception ignored) {}
            try { if (con != null) con.close(); } catch (Exception ignored) {}
        }
    }
}
