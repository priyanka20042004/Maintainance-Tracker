package user;

import dao.Database;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.UUID;
import javax.servlet.annotation.WebServlet;

@WebServlet("/raiseComplaint")
public class RaiseComplaintServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String userId = request.getParameter("userId");
        String machineId = request.getParameter("machineId");
        String description = request.getParameter("issueDescription");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Database.getConnection();
            String complaintId = "CMP" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            String sql = "INSERT INTO COMPLAINTS (COMPLAINT_ID, USER_ID, MACHINE_ID, DESCRIPTION, STATUS, TECHNICIAN_ID) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, complaintId);
            stmt.setString(2, userId.trim());
            stmt.setString(3, machineId.trim());
            stmt.setString(4, description.trim());
            stmt.setString(5, "unassigned");
            stmt.setNull(6, Types.VARCHAR); // Explicitly set TECHNICIAN_ID to null

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("raiseComplaint.jsp?msg=raised");
            } else {
                response.sendRedirect("raiseComplaint.jsp?msg=error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("raiseComplaint.jsp?msg=error");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ignored) {}
        }
    }
}
