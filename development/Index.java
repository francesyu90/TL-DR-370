import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Index extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("uname");
        String password = request.getParameter("userpw");

        if(!password.equals("370") || !username.equals("admin")){
            out.println("<HTML>");
            out.println("<HEAD>");
            out.println("<link rel='stylesheet' type='text/css' href='/CSS/normalize.css'>");
            out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css' integrity='sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7' crossorigin='anonymous'>");
            out.println("<link rel='stylesheet' type='text/css' href='/CSS/styles.css'>");
            out.println("<TITLE> TL:DR-370</TITLE></HEAD>");
            out.println("<BODY>");
            out.println("Sorry, you are not authorized!");
            out.println("</BODY>");
            out.println("</HTML>");
            out.close();
        }
        
        
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<link rel='stylesheet' type='text/css' href='/CSS/normalize.css'>");
        out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css' integrity='sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7' crossorigin='anonymous'>");
        out.println("<link rel='stylesheet' type='text/css' href='/CSS/styles.css'>");
        out.println("<TITLE>GetDemo Output</TITLE></HEAD>");
        out.println("<BODY>");
        out.println("<nav class='navbar navbar-inverse navbar-fixed-top'><div class='container'><div class='navbar-header'><button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#navbar' aria-expanded='false' aria-controls='navbar'><span class='sr-only'>Toggle navigation</span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></button><a class='navbar-brand' href='#'>TL:DR-370</a></div><div id='navbar' class='collapse navbar-collapse'><ul class='nav navbar-nav'><li class='active'><a href='/'>Home</a></li><li><a href='airlines.html'>Airlines</a></li><li><a href='#contact'>Contact</a></li></ul></div></div></nav>");
        out.println("<div class='paddingTop'>");

        out.println("Hello: " + username + "<br>");
        out.println("Your password was: " + password + "<br>");

        out.println("</div>");  //  end of padding top
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    public String getServletInfo() {  return "Insert"; }
}
