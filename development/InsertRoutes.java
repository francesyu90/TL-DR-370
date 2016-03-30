import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertRoutes extends HttpServlet {
     void processRequest(HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        String acode = request.getParameter("acode");
        String rnum = request.getParameter("rnum");
        String plmodel = request.getParameter("plmodel");

         String statementString = 
        "INSERT INTO Routes(rnum, plane_model, acode) " +
        "VALUES( " + rnum + ",'" + plmodel + "','" + acode + "')";

        String test = request.getParameter("checkRoute");
        String place = request.getParameter("place");
        String time = request.getParameter("time");

       

        //mm:ss


        String statementStringI = null;

        if (test.equals("in")) {
            statementStringI =  "INSERT INTO Incoming_Routes(rnum, acode, source, in_T) " +
        "VALUES( " + rnum + ",'" + acode + "','" + place + "', TO_DATE('"+ time +"', 'hh24:mi'))";
        }else{
             statementStringI =  "INSERT INTO Outgoing_Routes(rnum, acode, destination, out_T) " +
        "VALUES( " + rnum + ",'" + acode + "','" + place + "', TO_DATE('"+ time +"', 'hh24:mi'))";
        }

        Connection conn = ConnectionManager.getInstance().getConnection();

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

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(statementString);
            stmt.executeUpdate(statementStringI);
            stmt.close();
            out.println("<div class='alert alert-success' role='alert'>");
            out.println("Insertion Successful!");
            out.println("</div>");
            out.println("</div>");  //  end of padding top
            out.println("</BODY>");
            out.println("</HTML>");
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
