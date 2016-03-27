import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Test1 extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response)  						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
	   String acode = request.getParameter("acode");
        
        Connection conn = ConnectionManager.getInstance().getConnection();
        try { 
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(
                "SELECT ROUTES.RNUM, PLANE_MODEL, SOURCE LOCATION, TO_CHAR(IN_T, 'hh24:mm') TIME, ROUTES.ACODE " +
                "FROM ROUTES, INCOMING_ROUTES " +
                "WHERE ROUTES.RNUM = INCOMING_ROUTES.RNUM AND ROUTES.ACODE='" + acode + "' " + 
                "UNION " +
                "SELECT ROUTES.RNUM, PLANE_MODEL, DESTINATION LOCATION, TO_CHAR(OUT_T, 'hh24:mm') TIME, ROUTES.ACODE " +
                "FROM ROUTES, OUTGOING_ROUTES " +
                "WHERE ROUTES.RNUM = OUTGOING_ROUTES.RNUM AND ROUTES.ACODE='" + acode + "'"
            );
          

       		out.println("<table>");
    		while (rset.next()) {
    			out.println("<tr>");
    			out.print (
    				"<td>"+rset.getString("RNUM")+"</td>" + 
    				"<td>"+rset.getString("PLANE_MODEL")+"</td>");
    				out.println("</tr>");
    			}
    			out.println("</table>");
                stmt.close();
            }
            catch(SQLException e) { out.println(e); }        
            ConnectionManager.getInstance().returnConnection(conn);
        }
       protected void doGet(HttpServletRequest request, HttpServletResponse response)
        					throws ServletException, IOException {
            processRequest(request, response); }
       protected void doPost(HttpServletRequest request, HttpServletResponse response)
        					throws ServletException, IOException { 
            processRequest(request, response); }
       public String getServletInfo() {  return "Short description"; }
    }
               