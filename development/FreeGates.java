import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class FreeGates extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
        
    Connection conn = ConnectionManager.getInstance().getConnection();
      try { 
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                        "SELECT gnum " +
                        "FROM GATE " +
                        "WHERE IN_USE= 0");
      
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<link rel='stylesheet' type='text/css' href='/CSS/normalize.css'>");
        out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css' integrity='sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7' crossorigin='anonymous'>");
        out.println("<link rel='stylesheet' type='text/css' href='/CSS/styles.css'>");
        out.println("<TITLE>TL:DR-370</TITLE></HEAD>");
        out.println("<BODY>");
        out.println("<nav class='navbar navbar-inverse navbar-fixed-top'>");
        out.println("<div class='container'>");
        out.println("<div class='navbar-header'>");
        out.println("<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#navbar' aria-expanded='false' aria-controls='navbar'>");
        out.println("<span class='sr-only'>Toggle navigation</span>");
        out.println("<span class='icon-bar'></span>");
        out.println("<span class='icon-bar'></span>");
        out.println("<span class='icon-bar'></span></button>");
        out.println("<a class='navbar-brand' href='/'>TL:DR-370</a></div>");
        out.println("<div id='navbar' class='collapse navbar-collapse'>");
        out.println("<ul class='nav navbar-nav'>");
        out.println("<li><a href='/'>Home</a></li>");
        out.println("<li><a href='../flights.html'>Flights</a></li>");
        out.println("<li class='active'><a href='http://localhost:8081/servlet/FreeGates'>Free Gates</a></li>");
        out.println("<li><a href='http://localhost:8081/servlet/DelRoutes'>Delete Routes</a></li>");
        // out.println("<li><a href='http://localhost:8081/servlet/Departures'>Departures</a></li>");
        // out.println("<li><a href='http://localhost:8081/servlet/Arrivals'>Arrivals</a></li>");
        // out.println("<li><a href='http://localhost:8081/servlet/Passengers'>Passengers</a></li>");
        out.println("</ul>");
        out.println("</nav>");
        out.println("<div class='paddingTop'>");
        // out.println("<a href='../insertAirlines.html' class='btn btn-info floatRight' role='button'>Insert New Airline</a>");
        out.println("<br>");
        out.println("<h3>Free Gates");
        out.println("</h3>");
        out.println("<br>");
        out.println("<table class='table table-striped table-hover'>");
        out.println("<thead class='thead-inverse'>");
        out.println("<tr>");
        for(int i = 0; i < 9; i++){
            out.println("<th>Gate #</th>");
        }
        out.println("<th>Gate #</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");

        int counter = 0;
        while (rset.next()) {
            if(counter % 10 == 0)
                out.println("<tr>" + " <td>" + rset.getString("gnum") + "</td>");
            else if(counter % 10 == 9)
                out.println("<td>" + rset.getString("gnum") + "</td>" + "</tr>");
            else
                out.println("<td>"+rset.getString("gnum")+"</td>");
            counter++;
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");  //  end of padding top
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
        // stmt.close();
      }
      catch(SQLException e) { out.println(e); }
      ConnectionManager.getInstance().returnConnection(conn);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                              throws ServletException, IOException { 
      processRequest(request, response);     }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                              throws ServletException, IOException {
      processRequest(request, response);     }
    
    public String getServletInfo() {  return "Movie Servlet 1"; }
}
