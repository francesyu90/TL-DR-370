import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class Test3 extends HttpServlet {
     void processRequest(HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();

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
                    writer.println(text);
                    out.println("<br>");
                    Scanner sc = new Scanner(text);
                    while(sc.hasNext()){
                        out.println(sc.next() + "<br>");
                    }
                }
            }


            } catch(Exception e) {
                out.println(e);
            }
        
       //  String acode = request.getParameter("acode");
       //  String aname = request.getParameter("aname");
       //  String awebsite = request.getParameter("awebsite");

       //  String statementString = 
       //  "INSERT INTO Airlines(acode, name, website) " +
       //  "VALUES( '" + acode + "','" + aname + "','" + awebsite + "')";

       // Connection conn = ConnectionManager.getInstance().getConnection();
       //  try {
       //      Statement stmt = conn.createStatement();
       //      stmt.executeUpdate(statementString);
       //      stmt.close();
       //      out.println("Insertion Successful!");
       //  }
       //  catch(SQLException e) { out.println(e); }
       //  ConnectionManager.getInstance().returnConnection(conn);

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
