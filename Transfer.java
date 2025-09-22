
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Transfer extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    String sender = req.getParameter("send_ac");
    String reciver = req.getParameter("rece_ac");
    long amount = Long.parseLong(req.getParameter("amount"));

    Connection con = null;
    PreparedStatement pre = null;
    ResultSet rs = null;

    try {

      // Load Driver
      Class.forName("com.mysql.jdbc.Driver");
      String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
      con = DriverManager.getConnection(url, "root", "123456");

      String query = "select BALANCE from AccoundData where ACCOUNTNO = ? ";
      String query1 = "update AccoundData set Balance = ? where ACCOUNTNO = ? ";

      pre = con.prepareStatement(query);
      pre.setString(1, sender);
      rs = pre.executeQuery();

      if (rs.next()) {
        long amountpre = Long.parseLong(rs.getString(1));
        long newAmountSent = amountpre - amount;

        pre = con.prepareStatement(query1);
        pre.setLong(1, newAmountSent);
        pre.setString(2, sender);
        int i = pre.executeUpdate();
      }

      pre = con.prepareStatement(query);
      pre.setString(1, reciver);
      rs = pre.executeQuery();

      if (rs.next()) {
        long amountRecive = Long.parseLong(rs.getString(1));
        long newAmountReci = amountRecive + amount;

        pre = con.prepareStatement(query1);
        pre.setLong(1, newAmountReci);
        pre.setString(2, reciver);
        int i2 = pre.executeUpdate();
      }
      // transfer history
      String tra = "insert into  transaction (NAME, SENDER, RECIEVER, AMOUNT, DATETRANSACTION) values (? , ? , ? , ? , CURRENT_TIMESTAMP)";

      HttpSession session = req.getSession(); 
      String name = (String) session.getAttribute("user");

      pre = con.prepareStatement(tra);
      pre.setString(1, name);
      pre.setString(2, sender);
      pre.setString(3, reciver);
      pre.setLong(4, amount);
      pre.executeUpdate();

      out.println("transfer successfully ...");

      RequestDispatcher rd = req.getRequestDispatcher("user.html");
      rd.include(req, res);

    } catch (Exception e) {
      out.println("<h3>Error: " + e.getMessage() + "</h3>");
      e.printStackTrace(out);
    }

  }

}
