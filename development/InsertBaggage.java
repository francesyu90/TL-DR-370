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
            try {
                Statement stmt = conn.createStatement();

                for(int i = 0; i < executeStatementsArr.length; i++){
                    stmt.executeUpdate(executeStatementsArr[i]);
                }
                stmt.close();
                out.println("Insertion Successful!");
            }
            catch(SQLException e) { out.println(e); }
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
