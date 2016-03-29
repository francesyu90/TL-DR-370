import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class InsertPassengers extends HttpServlet {
     void processRequest(HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();

        Queue<String> executeStatements = new LinkedList<String>();

        try {

            String filename = "/WEB-INF/classes/pnr.txt";
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
                    String pid = sc.next();
                    String name = sc.next() + " " + sc.next();
                    String gid = sc.next();
                    String dob = sc.next();
                    String pob = sc.next() + " " + sc.next();
                    String depid = sc.next();
                    String arrid = sc.next();
                    String executeStatement = 
                    "INSERT INTO Passengers(PID, NAME, GOV_ISSUED_ID, DOB, POB, DEPID, ARRID) VALUES(" +
                    pid + ", '" + name + "', " + gid + ", " + 
                    "TO_DATE('" + dob + "', 'yyyy-MM-dd'), '" +  pob + "', " + depid + ", " + arrid + ")";
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
            out.println(e);
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
