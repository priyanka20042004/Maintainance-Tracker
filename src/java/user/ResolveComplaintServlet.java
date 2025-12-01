package user;

import dao.Database;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/resolveComplaint")
public class ResolveComplaintServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String complaintId = request.getParameter("complaint_id");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Database.getConnection();
            String sql = "UPDATE COMPLAINTS SET STATUS = 'resolved' WHERE COMPLAINT_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, complaintId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("technicianPanel.jsp");
            } else {
                response.getWriter().println("Failed to update complaint status.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Exception: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}