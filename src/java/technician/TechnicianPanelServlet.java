package technician;

import model.MachineFetcher;
import model.Machine;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/TechnicianPanelServlet")
public class TechnicianPanelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"technician".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userid = (String) session.getAttribute("userid");
        List<Machine> machines = MachineFetcher.getAllMachines();

        request.setAttribute("userid", userid);
        request.setAttribute("machines", machines);
        request.getRequestDispatcher("technicianPanel.jsp").forward(request, response);
    }
}