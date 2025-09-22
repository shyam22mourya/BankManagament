import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;

public class ChangePass extends HttpServlet{

        public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {

            String oldPass = req.getParameter("curr_pass");
            String newPass = req.getParameter("new_pass");
            String conPass = req.getParameter("conform_pass");

            res.setContentType("text/html");
            PrintWriter out = res.getWriter();

            HttpSession session = req.getSession(); // false = don’t create new if absent
            String pass = "";
           pass = (String) session.getAttribute("pass");

                
            try {

                // Load Driver
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/Bank?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
                Connection con = DriverManager.getConnection(url, "root", "123456");
                String query = "update employee  set password = ? where password = ?";
                
                RequestDispatcher rd;
                
                    if (newPass.equals(conPass)) {
                        PreparedStatement pre = con.prepareStatement(query);
                        pre.setString(1, newPass);
                        
                        pre.setString(2, pass);
                        
                        int i = pre.executeUpdate();

                            out.println("<h4>Password change successfully </h4>");
                            rd = req.getRequestDispatcher("user.html");
                            rd.include(req, res);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "passowrd  match", "Alert", JOptionPane.WARNING_MESSAGE);
                        rd = req.getRequestDispatcher("changePassword.html");
                        rd.include(req, res);
                    }
                

            } catch (Exception e) {
                out.println(e.getMessage());
            }

        }
    }
