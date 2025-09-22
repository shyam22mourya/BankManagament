
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NewAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();


        String user = req.getParameter("username");
        String adhar = req.getParameter("adhar");
        String mobile = req.getParameter("phone");
        String email = req.getParameter("email");
        String father = req.getParameter("father");
        String accountType = req.getParameter("accountType");
        String gender = req.getParameter("gender");
        String balance = req.getParameter("balance");

        try {

            // random number of account
            Random random = new Random();
            String s = "1234567890";
            char[] otp = new char[11];

            for (int i = 0; i < 11; i++) {
                otp[i] = s.charAt(random.nextInt(s.length()));
            }

            String strArray[] = new String[otp.length];
            for (int i = 0; i < otp.length; i++) {
                strArray[i] = String.valueOf(otp[i]);
            }

            String s1 = Arrays.toString(strArray);

            StringBuilder accNoBuilder = new StringBuilder();
            for (String num : strArray) {
                accNoBuilder.append(num);
            }
            String accountNo = accNoBuilder.toString();

            // Load Driver
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            Connection con = DriverManager.getConnection(url, "root", "123456");

            // Insert Query
            String query = "INSERT INTO AccoundData(name, ACCOUNTNO, ADHAR, MOBILENO, EMAIL, FATHERNAME, ACCOUNTTYPE, BALANCE, GENDER, UPDATEDATE) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setLong(2, Long.parseLong(accountNo));
            ps.setLong(3, Long.parseLong(adhar));
            ps.setLong(4, Long.parseLong(mobile));
            ps.setString(5, email);
            ps.setString(6, father);
            ps.setString(7, accountType);
            ps.setLong(8, Long.parseLong(balance));
            ps.setString(9, gender);

            int i = ps.executeUpdate();

            if (i > 0) {
                
                out.println("<h3>Account created successfully!</h3>");
                out.println("<p>Account Number: " + accountNo + "</p>");
            } else {
                out.println("<h3>Failed to create account.</h3>");
            }

            // Fetch inserted data
            String query1 = "SELECT * FROM AccoundData WHERE name = ?";
            PreparedStatement ps2 = con.prepareStatement(query1);
            ps2.setString(1, user);

            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                out.println("<h3>User Details:</h3>");
                out.println("Name: " + rs.getString("name") + "<br>");
                out.println("Account No: " + rs.getLong("ACCOUNTNO") + "<br>");
                out.println("Adhar: " + rs.getLong("ADHAR") + "<br>");
                out.println("Mobile: " + rs.getLong("MOBILENO") + "<br>");
                out.println("Email: " + rs.getString("EMAIL") + "<br>");
                out.println("Father Name: " + rs.getString("FATHERNAME") + "<br>");
                out.println("Account Type: " + rs.getString("ACCOUNTTYPE") + "<br>");
                out.println("Balance: " + rs.getLong("BALANCE") + "<br>");
                out.println("Gender: " + rs.getString("GENDER") + "<br>");
                out.println("Update Date: " + rs.getTimestamp("UPDATEDATE") + "<br>");
            }

            RequestDispatcher rd = req.getRequestDispatcher("user.html");
            rd.include(req, res);

            con.close();

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
    }
}
