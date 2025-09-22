import java.io.*;
import java.rmi.ServerException;
import java.sql.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  


public class Delete  extends HttpServlet{
     protected void doGet(HttpServletRequest  req , HttpServletResponse  res) throws ServerException , IOException{
         String acc = req.getParameter("acc");
         res.setContentType("text/html");
         PrintWriter out = res.getWriter(); 
           out.println("<script type='text/javascript'>");
                        out.println("alert('Delete user ');");
                        out.println("</script>");
         
         try{
    
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            Connection c = DriverManager.getConnection(url, "root", "123456");
              
            String sql = "delete from AccoundData where ACCOUNTNO = ? ";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, acc);
            int i = ps.executeUpdate(); 
            if(i > 0 ){
                 RequestDispatcher rd = req.getRequestDispatcher("accountDetail");
                 rd.forward(req ,res);
            }
            
         }catch (Exception e ){
             e.printStackTrace();
         }
     }
}
