
import java.io.*;  
import java.sql.*;  
import java.util.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  

public class CheckBalance extends HttpServlet {
    
    public void doGet ( HttpServletRequest req , HttpServletResponse res)  
                            throws  ServletException , IOException{
                    
       String ac = req.getParameter("ac");
       res.setContentType("text/html");
       PrintWriter out = res.getWriter();
              try{

                  // Load Driver
           Class.forName("com.mysql.jdbc.Driver"); 
          String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        Connection  con = DriverManager.getConnection(  url, "root", "123456");
            
          String query = "SELECT BALANCE FROM AccoundData WHERE ACCOUNTNO = ?";
          PreparedStatement ps = con.prepareStatement(query);
          ps.setString(1, ac);
          ResultSet  rs = ps.executeQuery();

               


        if(rs.next()){
           out.println("<h2> balance "+rs.getLong("BALANCE")+"</h2>"); 
        }

        RequestDispatcher rd = req.getRequestDispatcher("user.html");
        rd.include(req, res);
        
    }catch(Exception e ){
         out.println("<p>"+e.getMessage()+"</p>");

    }
  
    }
}
