
import java.io.*;  
import java.sql.*;  
import java.util.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  
 
public class TransferHistory extends HttpServlet{

    protected void  doGet  (HttpServletRequest req , HttpServletResponse res )
                                                   throws ServletException ,IOException{
          
       res.setContentType("text/html");
       PrintWriter out = res.getWriter(); 

        try{
                 // Load Driver
           Class.forName("com.mysql.jdbc.Driver"); 
          String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
           Connection con = DriverManager.getConnection(  url, "root", "123456");
              String query = "select * from TRANSACTION ";
     
              Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
         out.println("<table border='1' >");
         out.println("<thead> <tr> <th>id </th><th>name </th> <th>Sender </th><th>Receiver</th> <th>amount </th>  <th>Date</th> ");
         out.println("<tbody>");
            while(rs.next()){
                out.println("<tr> ");
                out.println("<td>"+rs.getInt("id") +"</td>");
                out.println("<td>"+rs.getString("name") +"</td>"); 
                out.println("<td>"+rs.getString("sender")+"</td>");  
                out.println("<td>"+rs.getString("RECIEVER") +"</td>"); 
                out.println("<td>"+rs.getString("amount") +"</td>");
                out.println("<td>"+rs.getDate("DATETRANSACTION") +"</td>");
               out.println("</tr> ");
            }
         
        } 
        catch(Exception e ){
            out.println("<p>"+e.getMessage()+"</p>")  ; 




        }       
                 
    }
}
