import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DelRoutes1 extends HttpServlet {
     void processRequest(HttpServletRequest request, HttpServletResponse response) 
                            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        String rnumber = request.getParameter("rnumber");

        String statementString = 
        "DELETE FROM ROUTES " +
        "WHERE RNUM = " + rnumber;


       Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            Statement stmt = conn.createStatement();
            if(stmt.executeUpdate(statementString) > 0)
            out.println("Deletion Successful!");
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
