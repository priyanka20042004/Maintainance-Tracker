package model;

import dao.Database;
import model.Machine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MachineFetcher {

    public static List<Machine> getAllMachines() {
        List<Machine> machines = new ArrayList<>();
        String query = "SELECT NAME, DESCRIPTION, DEPARTMENT, LATITUDE, LONGITUDE, IMAGE_PATH, MACHINE_ID FROM MACHINE_INFO";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Machine m = new Machine(
                    rs.getString("NAME"),
                    rs.getString("DESCRIPTION"),
                    rs.getString("DEPARTMENT"),
                    rs.getDouble("LATITUDE"),
                    rs.getDouble("LONGITUDE"),
                    rs.getString("IMAGE_PATH"),
                    rs.getString("MACHINE_ID")
                );
                machines.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return machines;
    }
}