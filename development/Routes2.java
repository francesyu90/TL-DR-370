import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Routes2 extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response)                       throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String location = request.getParameter("location");
        out.println(location);
        
        Connection conn = ConnectionManager.getInstance().getConnection();
        try { 
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(
                "SELECT I.RNUM RNUM, I.ACODE ACODE, SOURCE LOCATION, TO_CHAR(IN_T, 'hh24:mm') TIME, PLANE_MODEL PLMODEL " +
                "FROM INCOMING_ROUTES I, ROUTES R " +
                "WHERE I.RNUM = R.RNUM AND SOURCE ='" + location + "' " + 
                "AND R.ACODE = I.ACODE " + 
                "UNION " +
                "SELECT O.RNUM RNUM, O.ACODE ACODE, DESTINATION LOCATION, TO_CHAR(OUT_T, 'hh24:mm') TIME, PLANE_MODEL PLMODEL " +
                "FROM OUTGOING_ROUTES O, ROUTES R " +
                "WHERE O.RNUM = R.RNUM AND DESTINATION ='" + location + "' " +
                "AND O.ACODE = R.ACODE "
            );

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
            out.println("<li class='active'><a href='http://localhost:8081/servlet/Routes'>Routes</a></li>");
            out.println("<li><a href='http://localhost:8081/servlet/Departures'>Departures</a></li>");
            out.println("<li><a href='http://localhost:8081/servlet/Arrivals'>Arrivals</a></li>");
            out.println("<li><a href='http://localhost:8081/servlet/Passengers'>Passengers</a></li>");
            out.println("</ul>");
            out.println("</nav>");
            out.println("<div class='paddingTop'>");
            out.println("<a href='../insertRoutes.html' class='btn btn-info floatRight' role='button'>Insert New Route</a>");
            out.println("<br>");
            out.println("<h3>Routes with Location " + location);
            out.println("</h3>");
            out.println("<br>");
            out.println("<table class='table table-striped table-hover'>");
            out.println("<thead class='thead-inverse'>");
            out.println("<tr>");
            out.println("<th>Route #</th>");
            out.println("<th>Airline Code</th>");
            out.println("<th>Aircraft</th>");
            out.println("<th>Planned Time</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            while (rset.next()) {
                out.println("<tr>");
                out.print (
                    "<td>"+rset.getString("RNUM")+"</td>"+
                    "<td><A href=\"http://localhost:8081/servlet/Routes1?acode="+    
                    rset.getString("ACODE")+"\">"+rset.getString("ACODE")+"</A>"+"</td>"+
                    "<td>"+rset.getString("PLMODEL")+"</td>"+
                    "<td>"+rset.getString("TIME")+"</td>"
                    );
                    out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>"); // end of incoming routes



            out.println("</div>");  //  end of padding top
            out.println("</BODY>");
            out.println("</HTML>");

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
               