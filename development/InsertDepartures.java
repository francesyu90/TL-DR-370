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
        String gnum = request.getParameter("gnum");
        String depT = request.getParameter("depT");
        String rnum = request.getParameter("rnum");
        String acode = request.getParameter("acode");

        String statementString = 
        "INSERT INTO Departures(depid, gnum, dep_t, rnum, acode) " +
        "VALUES( '" + depId + "','" + gnum + "', TO_DATE('" + depT + "', 'yyyy-mm-dd hh24:mi'),'" + rnum + "','" + acode + "')";


       Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(statementString);
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
