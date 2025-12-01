package auth;
/*
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import dao.Database;

public class LoginSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        Connection conn = null;

        try {
            conn = Database.getConnection();

            String query = "SELECT * FROM USER_LOGIN WHERE USER_ID = ? AND PASSWORD = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful");
            } else {
                System.out.println("Login denied");
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            Database.close(conn);
        }

        sc.close();
    }
}*/