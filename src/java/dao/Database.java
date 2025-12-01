/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author KIIT0001
 */
public class Database {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "system");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return conn;
        }

    }
    public static void close(Connection conn){
    try{
    conn.close();
    System.out.println("Closed Connection");
    }
    catch(Exception e){
     System.out.println(e.getMessage());
    }
    }

    public static void main(String args[]) {
        Connection conn = dao.Database.getConnection();
        System.out.println(conn);
        dao.Database.close(conn);

    }
}