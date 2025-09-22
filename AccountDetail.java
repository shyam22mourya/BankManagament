import java.io.*;  
import java.sql.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  

public class AccountDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = "root";
        String pass = "123456";

        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM accounddata");
            // Load Driver (newer JDBC auto-loads, but keep if needed)
            Class.forName("com.mysql.jdbc.Driver"); 

            out.println("<html><body>");
            out.println("<table border='1'>");

            out.println("<thead><tr>"
                    + "<th>ID</th><th>Name</th><th>Account No</th><th>Aadhar</th>"
                    + "<th>Mobile No</th><th>Father Name</th><th>Balance</th>"
                    + "<th>Gender</th><th>Update Date</th><th>Update</th><th>Delete</th></tr></thead>");

            out.println("<tbody>");
            int cnt = 1;

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + cnt + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getLong("ACCOUNTNO") + "</td>");
                out.println("<td>" + rs.getLong("ADHAR") + "</td>");
                out.println("<td>" + rs.getLong("MOBILENO") + "</td>");
                out.println("<td>" + rs.getString("FatherName") + "</td>");
                out.println("<td>" + rs.getLong("BALANCE") + "</td>");
                out.println("<td>" + rs.getString("GENDER") + "</td>");
                out.println("<td>" + rs.getDate("UPDATEDATE") + "</td>");
                out.println("<td><a href='update.html'> update</a>" + "</td>");
                out.println("<td><a href='delete?acc="+rs.getLong("ACCOUNTNO")+"'> Delete</a></td>");
                out.println("</tr>");
                cnt++;
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }
}
