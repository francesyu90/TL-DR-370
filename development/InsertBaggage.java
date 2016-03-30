import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class InsertBaggage extends HttpServlet {
     void processRequest(HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();

        Queue<String> executeStatements = new LinkedList<String>();

        try {

            String filename = "/WEB-INF/classes/pnrB.txt";
            ServletContext context = getServletContext();
            //
            // First get the file InputStream using ServletContext.getResourceAsStream()
            // method.
            //
            InputStream is = context.getResourceAsStream(filename);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);
                PrintWriter writer = response.getWriter();
                String text = "";
                
                //
                // We read the file line by line and later will be displayed on the 
                // browser page.
                //
                reader.readLine();
                while ((text = reader.readLine()) != null) {
                    Scanner sc = new Scanner(text);
                    String bid = sc.next();
                    String weight = sc.next();
                    String pid = sc.next();
                    String executeStatement = 
                    "INSERT INTO Baggage(BID, WEIGHT, PID) VALUES(" +
                    bid + ", " + weight + ", " + pid + ")";
                    executeStatements.add(executeStatement);
                }
            }

            String[] executeStatementsArr = executeStatements.toArray(new String[0]);

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
            out.println("<li><a href='http://localhost:8081/servlet/Routes'>Routes</a></li>");
            out.println("<li><a href='http://localhost:8081/servlet/Departures'>Departures</a></li>");
            out.println("<li><a href='http://localhost:8081/servlet/Arrivals'>Arrivals</a></li>");
            out.println("<li><a href='http://localhost:8081/servlet/Passengers'>Passengers</a></li>");
            out.println("</ul>");
            out.println("</nav>");
            out.println("<div class='paddingTop'>");

            try {
                Statement stmt = conn.createStatement();

                for(int i = 0; i < executeStatementsArr.length; i++){
                    stmt.executeUpdate(executeStatementsArr[i]);
                }
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
            



        } catch(Exception e) {
            e.printStackTrace();
        }

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
