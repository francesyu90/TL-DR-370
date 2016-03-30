import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DelRoutes extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
        
    Connection conn = ConnectionManager.getInstance().getConnection();
      try { 
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(
                        "SELECT rnum, acode, source location, to_char(in_t, 'hh24:mi') time " +
                        "FROM Incoming_Routes " + 
                        "WHERE rnum > 0 " +
                        "UNION " +
                        "SELECT rnum, acode, destination location, to_char(out_t, 'hh24:mi') time " + 
                        "FROM Outgoing_Routes " +
                        "WHERE rnum > 0");
      
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("<link rel='stylesheet' type='text/css' href='/CSS/normalize.css'>");
        out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css' integrity='sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7' crossorigin='anonymous'>");
        out.println("<link rel='stylesheet' type='text/css' href='/CSS/styles.css'>");
        out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js'></script>");
        out.println("<script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js'></script>");
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
        out.println("<li class='active'><a href='http://localhost:8081/servlet/DelRoutes'>Delete Routes</a></li>");
        // out.println("<li><a href='http://localhost:8081/servlet/Departures'>Departures</a></li>");
        // out.println("<li><a href='http://localhost:8081/servlet/Arrivals'>Arrivals</a></li>");
        // out.println("<li><a href='http://localhost:8081/servlet/Passengers'>Passengers</a></li>");
        out.println("</ul>");
        out.println("<ul class='nav navbar-nav navbar-right'>");
        out.println("<li>");
        out.println("<button id='DelRButton' type='button' class='btn btn-danger' data-toggle='modal' data-target='#myModal'>Delete Route</button>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</nav>");
        out.println("<div class='paddingTop'>");
        // out.println("<button type='button' class='btn btn-danger floatRight' data-toggle='modal' data-target='#myModal'>Delete Route</button>");
        out.println("<div id='myModal' class='modal fade' role='dialog'>");
        out.println("<div class='modal-dialog'>");
        out.println("<div class='modal-content'>");
        out.println("<div class='modal-header'>");
        out.println("<button type='button' class='close' data-dismiss='modal'>&times;</button>");
        out.println("<h4 class='modal-title'>Delete Route</h4>");
        out.println("</div>");
        out.println("<div class='modal-body'>");
        // out.println("<p>Some text in the modal.</p>");
        out.println("<form action='http://localhost:8081/servlet/DelRoutes1' id='DelRoute'>");
        out.println("<div class='form-group'>");
        out.println("<label for='rnumber'>Route #</label>");
        out.println("<input type='number' class='form-control' id='rnumber' name='rnumber'>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("<div class='modal-footer'>");
        out.println("<div class='btn-group' role='group' aria-label='...'>");
        out.println("<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>");
        out.println("<input type='submit' class='btn btn-danger' form='DelRoute' />");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<br>");
        out.println("<h3>Routes");
        out.println("</h3>");
        out.println("<br>");
        out.println("<table class='table table-striped table-hover'>");
        out.println("<thead class='thead-inverse'>");
        out.println("<th>" + "Route #"+ "</th>");
        out.println("<th>" + "Airline Code"+ "</th>");
        out.println("<th>" + "Location"+ "</th>");
        out.println("<th>" + "Scheduled Time"+ "</th>");
        out.println("</thead>");
        out.println("<tbody>");
        // out.println("<tr>");
        while (rset.next()) {
                out.println(
                    "<tr>" +
                    "<td>" + rset.getString("rnum") + "</td>" +
                    "<td><A href=\"http://localhost:8081/servlet/Routes1?acode="+    
                    rset.getString("acode")+"\">"+rset.getString("acode")+"</A>"+"</td>"+
                    "<td><A href=\"http://localhost:8081/servlet/Routes2?location="+    
                    rset.getString("location")+"\">"+rset.getString("location")+"</A>"+"</td>"+
                    "<td>" + rset.getString("time") + "</td>" +
                    "</tr>" 
                );
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
