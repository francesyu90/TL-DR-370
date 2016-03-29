import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertDepartures extends HttpServlet {
     void processRequest(HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();

        String depId = request.getParameter("depId");
        String gnum = "0";
        String dateTimeString = request.getParameter("ddate") + " " + request.getParameter("dtime");
        String rnum = request.getParameter("rnum");
        String acode = request.getParameter("acode");

        String statementStringI = 
        "INSERT INTO Departures(depid, gnum, dep_t, rnum, acode) " +
        "VALUES( '" + depId + "','" + gnum + "', TO_DATE('" + dateTimeString + "', 'yyyy-mm-dd hh24:mi'),'" + rnum + "','" + acode + "')";

       Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(statementStringI);


            String statementString = 
            "SELECT GNUM " + 
            "FROM GATE " +
            "MINUS ( " + 
            "SELECT GNUM "+
            "FROM ARRIVALS A " +
            "WHERE 24 * ABS(A.ARR_T - TO_DATE('" + dateTimeString + "', 'yyyy-mm-dd hh24:mi')) < 1 " +
            "UNION " +
            "SELECT GNUM "+
            "FROM DEPARTURES D " +
            "WHERE 24 * ABS(D.DEP_T - TO_DATE('" + dateTimeString + "', 'yyyy-mm-dd hh24:mi')) < 1)" ;
            ResultSet rset = stmt.executeQuery(statementString);

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
            out.println("<li><a href='http://localhost:8081/servlet/Arrivals'>Arrivals</a></li>");
            out.println("<li><a href='http://localhost:8081/servlet/Passengers'>Passengers</a></li>");
            out.println("</ul>");
            out.println("</nav>");
            out.println("<div class='paddingTop'>");
            out.println("<form action='http://localhost:8081/servlet/InsertDepartures1'>");
            out.println("<div class='form-group'>");
            out.println("<label for='depId'>Departure ID</label>");
            out.println("<input type='number' id='depId' name='depId' class='form-control' value='" + depId +"' ></input>");
            out.println("</div>");
            out.println("<div class='form-group'>");
            out.println("<label for='gnum'>Available Gates </label>");
            out.println("<select class='form-control' name='gnum' id='gnum'>");
             while (rset.next()) {
                //  <option value="one">One</option>
                out.println("<option value = '" + rset.getString("gnum") + "'> " + rset.getString("gnum") +
                    " </option>");
            }
            out.println("</select>");
            out.println("</div>");
            out.println("<button type='submit' class='btn btn-info'>Insert</button>");
            out.println("</form>");
            out.println("</div>");  //  end of padding top
            out.println("</BODY>");
            out.println("</HTML>");


            stmt.close();
            
        }
        catch(SQLException e) { out.println(e); }
        ConnectionManager.getInstance().returnConnection(conn);

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
