import java.io.*;  
import java.sql.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  

public class SignUp extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
         
        String username = req.getParameter("username");
        String password = req.getParameter("password");  
        String email = req.getParameter("email");
        String numberStr = req.getParameter("number");

        resp.setContentType("text/html");
        PrintWriter pr = resp.getWriter();

        try {

          Class.forName("com.mysql.jdbc.Driver"); 
          String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
           Connection con = DriverManager.getConnection(  url, "root", "123456");
           String query = "INSERT INTO Employee (NAME, PASSWORD, EMAIL, CONTECT_NO) VALUES (?, ?, ?, ?)";

           PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);  
            ps.setString(3, email);
            
            long number = Long.parseLong(numberStr);
            ps.setLong(4, number);

            int i = ps.executeUpdate();
             
            if (i > 0) {
                pr.println("You have successfully signed up.");
                RequestDispatcher rd = req.getRequestDispatcher("login.html");
                rd.forward(req, resp);
            } else {
                pr.println("Failed to sign up.");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println(e.getMessage());
        } 
    }
}
