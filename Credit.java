import java.io.*;  
import java.sql.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  

public class Credit extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String accountNo = req.getParameter("ac");
        long credit = Long.parseLong(req.getParameter("amount"));

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "123456");

            // 1. Get current balance
            String query = "SELECT BALANCE FROM AccoundData WHERE ACCOUNTNO = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, accountNo);
            rs = ps.executeQuery();
                long newbalance = 0 ;
            if (rs.next()) {
                long amount = rs.getLong("BALANCE");
                 newbalance = amount + credit;

                // 2. Update balance
                String updateQuery = "UPDATE AccoundData SET BALANCE = ? WHERE ACCOUNTNO = ?";
                PreparedStatement ps1 = con.prepareStatement(updateQuery);
                ps1.setLong(1, newbalance);
                ps1.setString(2, accountNo);

                int i = ps1.executeUpdate();

                if (i > 0) {
                    out.println("<h3>Credit successful!</h3>");
                    out.println("<p>New balance: " + newbalance + "</p>");
                } else {
                    out.println("<h3>Update failed! Account not found.</h3>");
                }
             
                // this line for transfer ; 
             String query3 = "insert into credit (NAME, AMOUNT, DATECREDIT) values ( ? , ? , CURRENT_TIMESTAMP )"; 
              HttpSession session = req.getSession(); 
            String name = (String) session.getAttribute("user");
                ps = con.prepareStatement(query3);
                ps.setString(1, name);
                ps.setLong(2, amount);
                 i = ps.executeUpdate();

                ps.close();

            } else {
                out.println("<h3>Account number not found!</h3>");
            }

            // Include user.html
            RequestDispatcher rd = req.getRequestDispatcher("user.html");
            rd.include(req, res);

        } catch (Exception e) {
            out.println("<h3>Error: Something went wrong.</h3>");
            out.println(e); 
            e.printStackTrace(); // log in server logs, not in browser
        } 
    }
}
