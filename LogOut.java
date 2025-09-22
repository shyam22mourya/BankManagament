import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  

public class LogOut extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)  
            throws ServletException, IOException {  

        res.setContentType("text/html");
        PrintWriter out = res.getWriter(); 

        // Get existing session, do not create new
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate(); // only invalidate if session exists
        }

        out.println("<script type='text/javascript'>");
        out.println("alert('You have been logged out successfully!');");
        out.println("window.location='home.html';"); // redirect after alert
        out.println("</script>");
    }
}
