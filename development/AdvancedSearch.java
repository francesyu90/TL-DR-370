import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AdvancedSearch extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conn = ConnectionManager.getInstance().getConnection();
        try { 
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(
                            "SELECT acode, name " +
                            "FROM Airlines");
          
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
        out.println("<li><a href='http://localhost:8081/servlet/DelRoutes'>Delete Routes</a></li>");
        out.println("<li class='active'><a href='http://localhost:8081/servlet/AdvancedSearch'>Advanced Search</a></li>");
        out.println("</ul>");
        out.println("</nav>");
        out.println("<div class='paddingTop'>");
        out.println("<form action='http://localhost:8081/servlet/Routes1'>");
        out.println("<div class='form-group'>");
        out.println("<label for='acode'>Airlines </label>");
        out.println("<select class='form-control' name='acode' id='acode'>");
        while (rset.next()) {
            //  <option value="one">One</option>
            out.println("<option value = '" + rset.getString("acode") + "'> " + rset.getString("name") +
                " </option>");
        }
        out.println("</select>");
        out.println("</div>");
        out.println("<button type='submit' class='btn btn-info'>Search Routes</button>");
        out.println("</form>");
        Statement stmt1 = conn.createStatement();
        ResultSet rset1 = stmt1.executeQuery(
                "SELECT SOURCE LOCATION " +
                "FROM INCOMING_ROUTES " +
                "UNION " +
                "SELECT DESTINATION LOCATION " +
                "FROM OUTGOING_ROUTES");
        out.println("<form action='http://localhost:8081/servlet/Routes2'>");
        out.println("<div class='form-group'>");
        out.println("<label for='location'>Locations </label>");
        out.println("<select class='form-control' name='location' id='location'>");
        while (rset1.next()) {
            //  <option value="one">One</option>
            out.println("<option value = '" + rset1.getString("LOCATION") + "'> " + rset1.getString("LOCATION") +
                " </option>");
        }
        out.println("</select>");
        out.println("</div>");
        out.println("<button type='submit' class='btn btn-info'>Search Routes</button>");
        out.println("</form>");
        stmt1 = conn.createStatement();
        rset1 = stmt1.executeQuery(
                "SELECT DEPID, TO_CHAR(DEP_T, 'YYYY-MM-DD HH24:MI') DEP_T, DESTINATION " +
                "FROM DEPARTURES, OUTGOING_ROUTES " +
                "WHERE DEPARTURES.RNUM = OUTGOING_ROUTES.RNUM " +
                "AND DEPARTURES.ACODE = OUTGOING_ROUTES.ACODE");
        out.println("<form action='http://localhost:8081/servlet/Departures1'>");
        out.println("<div class='form-group'>");
        out.println("<label for='depid'>Departures </label>");
        out.println("<select class='form-control' name='depid' id='depid'>");
        while (rset1.next()) {
            //  <option value="one">One</option>
            out.println("<option value = '" + rset1.getString("depid") + "'> " + rset1.getString("DEP_T") +
                ", " + rset1.getString("DESTINATION") +
                " </option>");
        }
        out.println("</select>");
        out.println("</div>");
        out.println("<button type='submit' class='btn btn-info'>Search Passengers</button>");
        out.println("</form>");
        stmt1 = conn.createStatement();
        rset1 = stmt1.executeQuery(
                "SELECT ARRID, TO_CHAR(ARR_T, 'YYYY-MM-DD HH24:MI') ARR_T, SOURCE " +
                "FROM ARRIVALS, INCOMING_ROUTES " +
                "WHERE ARRIVALS.RNUM = INCOMING_ROUTES.RNUM " +
                "AND ARRIVALS.ACODE = INCOMING_ROUTES.ACODE");
        out.println("<form action='http://localhost:8081/servlet/Arrivals1'>");
        out.println("<div class='form-group'>");
        out.println("<label for='depid'>Arrivals </label>");
        out.println("<select class='form-control' name='arrid' id='arrid'>");
        while (rset1.next()) {
            //  <option value="one">One</option>
            out.println("<option value = '" + rset1.getString("arrid") + "'> " + rset1.getString("ARR_T") +
                ", " + rset1.getString("SOURCE") +
                " </option>");
        }
        out.println("</select>");
        out.println("</div>");
        out.println("<button type='submit' class='btn btn-info'>Search Passengers</button>");
        out.println("</form>");
        stmt1 = conn.createStatement();
        rset1 = stmt1.executeQuery(
                "SELECT PID, NAME " +
                "FROM PASSENGERS");
        out.println("<form action='http://localhost:8081/servlet/Baggage'>");
        out.println("<div class='form-group'>");
        out.println("<label for='pid'>Passengers </label>");
        out.println("<select class='form-control' name='pid' id='pid'>");
        while (rset1.next()) {
            //  <option value="one">One</option>
            out.println("<option value = '" + rset1.getString("pid") + "'> " + rset1.getString("name") +
                " </option>");
        }
        out.println("</select>");
        out.println("</div>");
        out.println("<button type='submit' class='btn btn-info'>Search Baggages</button>");
        out.println("</form>");
        out.println("</div>");  //  end of padding top
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
        stmt.close();
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
