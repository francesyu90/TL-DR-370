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
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(statementString);
            stmt.executeUpdate(statementStringI);
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
