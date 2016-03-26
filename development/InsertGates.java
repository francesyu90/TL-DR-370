import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertGates extends HttpServlet {
     void processRequest(HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();

        int numOfGates = 100;
        int in_use = 0;
        String[] statementStrings = new String[numOfGates];
        for(int i = 0; i < numOfGates; i++){
            statementStrings[i] =  
            "INSERT INTO Gate(gnum, in_use) " +
            "VALUES( '" + i + "','" + in_use + "')";
        }


       Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            Statement stmt = conn.createStatement();
            for(int i = 0; i < numOfGates; i++){
                stmt.executeUpdate(statementStrings[i]);
            }
            stmt.close();
            out.println("Insertion Successful!");
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
