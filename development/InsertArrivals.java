import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertArrivals extends HttpServlet {
     void processRequest(HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();

        String arrId = request.getParameter("arrId");
        String dateTimeString = request.getParameter("adate") + " " + request.getParameter("atime");
        String rnum = request.getParameter("rnum");
        String acode = request.getParameter("acode");
        String gnumS = "0";
        String statementStringI = 
        "INSERT INTO Arrivals(arrid, gnum, arr_t, rnum, acode) " +
        "VALUES( '" + arrId + "','" + gnumS + "', TO_DATE('" + dateTimeString + "', 'yyyy-mm-dd hh24:mi'),'" + rnum + "','" + acode + "')";

       Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(statementStringI);
            // out.println("Insert successfully!");
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
            out.println("<li><a href='http://localhost:8081/servlet/Departures'>Departures</a></li>");
            out.println("<li class='active'><a href='http://localhost:8081/servlet/Arrivals'>Arrivals</a></li>");
            out.println("<li><a href='http://localhost:8081/servlet/Passengers'>Passengers</a></li>");
            out.println("</ul>");
            out.println("</nav>");
            out.println("<div class='paddingTop'>");
            out.println("<form action='http://localhost:8081/servlet/InsertArrivals1'>");
            out.println("<div class='form-group'>");
            out.println("<label for='arrId'>Arrival ID</label>");
            out.println("<input type='number' id='arrId' name='arrId' class='form-control' value='" + arrId +"' ></input>");
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
        catch(SQLException e) { 
            out.println("<div class='alert alert-danger' role='alert'>");
            out.println("<span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true'></span>");
            out.println(e);
            out.println("</div>"); 
            out.println("</div>");  //  end of padding top
            out.println("</BODY>");
            out.println("</HTML>");
        }
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
