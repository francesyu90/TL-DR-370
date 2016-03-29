import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Arrivals extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response)                       throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm");
        DateFormat dateFormat2 = new SimpleDateFormat("dd MMM");
        DateFormat dateFormat3 = new SimpleDateFormat("dd MMM, yyyy");
        Date currDate = new Date();
       
        
        Connection conn = ConnectionManager.getInstance().getConnection();
        try { 

            
                Statement stmt = conn.createStatement();
                ResultSet rset = stmt.executeQuery(
                        "SELECT arrid, gnum, TO_CHAR(arr_t, 'yyyy-mm-dd hh24:mi') arr_t, Arrivals.RNUM RNUM, Arrivals.ACODE ACODE, source " +
                        "FROM Arrivals, INCOMING_ROUTES " +
                        "WHERE Arrivals.RNUM = INCOMING_ROUTES.RNUM  AND INCOMING_Routes.rnum > 0"
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
                out.println("<li><a href='http://localhost:8081/servlet/Routes'>Routes</a></li>");
                out.println("<li><a href='http://localhost:8081/servlet/Departures'>Departures</a></li>");
                out.println("<li class='active'><a href='http://localhost:8081/servlet/Arrivals'>Arrivals</a></li>");
                out.println("<li><a href='http://localhost:8081/servlet/Passengers'>Passengers</a></li>");
                out.println("</ul>");
                out.println("</nav>");
                out.println("<div class='paddingTop'>");
                out.println("<a href='../insertArrivals.html' class='btn btn-info floatRight' role='button'>Insert New Arrival</a>");
                out.println("<br>");
                out.println("<h3>Arrivals");
                out.println("</h3>");
                out.println("<br>");
                out.println("<table class='table table-striped table-hover'>");
                out.println("<thead class='thead-inverse'>");
                out.println("<tr>");
                out.println("<th>Date</th>");
                out.println("<th>Airline Code</th>");
                out.println("<th>Arrival ID</th>");
                out.println("<th>Arrival Time</th>");
                out.println("<th>From</th>");
                out.println("<th>Gate #</th>");
                out.println("<th>Status</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                while (rset.next()) {

                    Date date = dateFormat.parse(rset.getString("arr_t"));
                    String status = (currDate.before(date))?"Scheduled":"Landed";
                    String dateString = dateFormat1.format(date);
                    String dateS = (currDate.before(date))?dateFormat3.format(date):dateFormat2.format(date);

                    out.println("<tr>");
                    out.print (
                        "<td>"+dateS+"</td>"+
                        "<td><A href=\"http://localhost:8081/servlet/Routes1?acode="+    
                        rset.getString("ACODE")+"\">"+rset.getString("ACODE")+"</A>"+"</td>"+
                        "<td><A href=\"http://localhost:8081/servlet/Arrivals1?arrid="+    
                        rset.getString("arrid")+"\">"+rset.getString("arrid")+"</A>"+"</td>"+
                        "<td>"+dateString+"</td>"+
                        "<td><A href=\"http://localhost:8081/servlet/Routes2?location="+    
                        rset.getString("source")+"\">"+rset.getString("source")+"</A>"+"</td>"+
                        "<td>"+rset.getString("gnum")+"</td>"+
                        "<td>"+ status +"</td>"
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
        //  } catch(ParseException err) { out.println(err); 
        // } catch(SQLException e) { out.println(e); }    

        } catch(Exception e) { out.println(e); }       
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
               