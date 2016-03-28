import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Flights extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    String date = request.getParameter("date");
    String time = request.getParameter("time");
    String dateTimeString = date + " " + time;
    out.println(dateTimeString);
        
    // Connection conn = ConnectionManager.getInstance().getConnection();
    // try { 


    //     Statement stmt = conn.createStatement();
    //     ResultSet rset = stmt.executeQuery(
    //                     "SELECT acode, name, website " +
    //                     "FROM Airlines");
      
    //     out.println("<HTML>");
    //     out.println("<HEAD>");
    //     out.println("<link rel='stylesheet' type='text/css' href='/CSS/normalize.css'>");
    //     out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css' integrity='sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7' crossorigin='anonymous'>");
    //     out.println("<link rel='stylesheet' type='text/css' href='/CSS/styles.css'>");
    //     out.println("<TITLE>TL:DR-370</TITLE></HEAD>");
    //     out.println("<BODY>");
    //     out.println("<nav class='navbar navbar-inverse navbar-fixed-top'>");
    //     out.println("<div class='container'>");
    //     out.println("<div class='navbar-header'>");
    //     out.println("<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#navbar' aria-expanded='false' aria-controls='navbar'>");
    //     out.println("<span class='sr-only'>Toggle navigation</span>");
    //     out.println("<span class='icon-bar'></span>");
    //     out.println("<span class='icon-bar'></span>");
    //     out.println("<span class='icon-bar'></span></button>");
    //     out.println("<a class='navbar-brand' href='/'>TL:DR-370</a></div>");
    //     out.println("<div id='navbar' class='collapse navbar-collapse'>");
    //     out.println("<ul class='nav navbar-nav'>");
    //     out.println("<li><a href='/'>Home</a></li>");
    //     out.println("<li class='active'><a href='../flights.html'>Flights</a></li>");
    //     // out.println("<li><a href='http://localhost:8081/servlet/Routes'>Routes</a></li>");
    //     // out.println("<li><a href='http://localhost:8081/servlet/Departures'>Departures</a></li>");
    //     // out.println("<li><a href='http://localhost:8081/servlet/Arrivals'>Arrivals</a></li>");
    //     // out.println("<li><a href='http://localhost:8081/servlet/Passengers'>Passengers</a></li>");
    //     out.println("</ul>");
    //     out.println("</nav>");
    //     out.println("<div class='paddingTop'>");
        
    //     /*

    
    //     */

    //     // out.println("<h3>");
    //     // out.println("Chosen date & time: " + date + " "+ time);
    //     // out.println("</h3>");

    //     out.println("</div>");  //  end of padding top
    //     out.println("</BODY>");
    //     out.println("</HTML>");
    //     out.close();
    //     // stmt.close();
    //   }
    //   catch(SQLException e) { out.println(e); }
    //   ConnectionManager.getInstance().returnConnection(conn);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                              throws ServletException, IOException { 
      processRequest(request, response);     }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                              throws ServletException, IOException {
      processRequest(request, response);     }
    
    public String getServletInfo() {  return "Movie Servlet 1"; }
}
