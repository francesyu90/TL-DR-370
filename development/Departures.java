import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Departures extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response)  						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
	   String year = request.getParameter("year");
        
        Connection conn = ConnectionManager.getInstance().getConnection();
        try { 
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery(
                        "SELECT depid, gnum, dep_t, rnum, acode " +
                        "FROM Departures");

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
                out.println("<li><a href='http://localhost:8081/servlet/Airlines'>Airlines</a></li>");
                out.println("<li><a href='http://localhost:8081/servlet/Routes'>Routes</a></li>");
                out.println("<li class='active'><a href='http://localhost:8081/servlet/Departures'>Departures</a></li>");
                out.println("</ul>");
                out.println("</nav>");
                out.println("<div class='paddingTop'>");
                out.println("<a href='../insertDepartures.html' class='btn btn-info floatRight' role='button'>Insert New Departure</a>");
           		out.println("<h3>Departures");
                out.println("</h3>");
                out.println("<table class='table table-striped table-hover'>");
                out.println("<thead class='thead-inverse'>");
                out.println("<tr>");
                out.println("<th>Departure ID</th>");
                out.println("<th>Gate #</th>");
                out.println("<th>Departure Time</th>");
                out.println("<th>Route #</th>");
                out.println("<th>Airline Code</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
        		while (rset.next()) {

        			out.println("<tr>");
        			out.print (
        				"<td>"+rset.getString("depid")+"</td>"+
                        "<td>"+rset.getString("gnum")+"</td>"+
                        "<td>"+rset.getString("dep_t")+"</td>"+
                        "<td>"+rset.getString("rnum")+"</td>"+
                        "<td>"+rset.getString("acode")+"</td>"
                        );
        				out.println("</tr>");
        		}
                out.println("</tbody>");
        		out.println("</table>"); // end of departures
                out.println("</div>");  //  end of padding top
                out.println("</BODY>");
                out.println("</HTML>");
                // out.close();
                stmt.close();
        }



            catch(SQLException e) { out.println(e); }        
            ConnectionManager.getInstance().returnConnection(conn);
        }
       protected void doGet(HttpServletRequest request, HttpServletResponse response)
        					throws ServletException, IOException {
            processRequest(request, response); }
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
        					throws ServletException, IOException { 
            processRequest(request, response); }
       public String getServletInfo() {  return "Short description"; }
    }
               