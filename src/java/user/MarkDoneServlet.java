package servlets;

import dao.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/markDone")
public class MarkDoneServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String complaintId = request.getParameter("complaint_id");

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE COMPLAINTS SET STATUS = 'done' WHERE COMPLAINT_ID = ?")) {

            stmt.setString(1, complaintId);
            stmt.executeUpdate();

            response.sendRedirect("technicianPanel.jsp"); // Refresh panel

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}