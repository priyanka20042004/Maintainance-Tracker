package machine;

import dao.Database;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Files;
import java.sql.*;
import java.util.UUID;

@WebServlet("/RegisterMachineServlet")
@MultipartConfig
public class RegisterMachineServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Fetch form data
        String name = request.getParameter("name");
        String desc = request.getParameter("description");
        String dept = request.getParameter("department");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        Part filePart = request.getPart("image");

        // File upload logic
        String submittedName = filePart.getSubmittedFileName();
        if (submittedName == null || submittedName.trim().isEmpty()) {
            response.sendError(400, "File is missing");
            return;
        }

        String fileName = new File(submittedName).getName();

        // Create /uploads path if not exists
        String uploadPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        File uploadedFile = new File(uploadDir, fileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, uploadedFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        // Generate machine ID
        String machineId = "MACH" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();

        // Insert into DB
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO MACHINE_INFO " +
                     "(MACHINE_ID, NAME, DESCRIPTION, IMAGE_PATH, DEPARTMENT, LATITUDE, LONGITUDE) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, machineId);
            ps.setString(2, name);
            ps.setString(3, desc);
            ps.setString(4, "uploads/" + fileName);
            ps.setString(5, dept);
            ps.setString(6, latitude);
            ps.setString(7, longitude);
            ps.executeUpdate();

        } catch (SQLException e) {
            response.sendError(500, "Database Error: " + e.getMessage());
            return;
        }

        // Generate QR code
        String qrPath = getServletContext().getRealPath("/qrcodes");
        File qrDir = new File(qrPath);
        if (!qrDir.exists()) qrDir.mkdirs();

        File qrFile = new File(qrDir, machineId + ".png");
        try (OutputStream os = new FileOutputStream(qrFile)) {
            BitMatrix matrix = new QRCodeWriter().encode(machineId, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToStream(matrix, "PNG", os);
        } catch (Exception e) {
            response.sendError(500, "QR Code Generation Error: " + e.getMessage());
            return;
        }

        // Show success
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<h2>Machine Registered Successfully</h2>");
            out.println("<p>Machine ID: <strong>" + machineId + "</strong></p>");
            out.println("<img src='qrcodes/" + machineId + ".png' alt='QR Code' />");
            out.println("<br><a href='register_machine.jsp'>Register Another</a>");
        }
    }
}
