/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package notall;

import ASU_POL.Connection_ASU_POL;
import org.apache.poi.hssf.usermodel.*;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;

/**
 *
 * @author apopovkin
 */
public class vvod_id_sootv_sys_log {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String UserID = "db2admin";
        String Password = "11111111";
        String DB2 = "jdbc:db2:ASU_POL4";
        Connection conn = null;
        try {

            conn = new Connection_ASU_POL().getConnection(UserID, Password, DB2);

            Statement stmt_0 = conn.createStatement();
            ResultSet rs_0 = stmt_0.executeQuery(
                    " select ID, ID_USER, IP, DT_TIME, INFO, ID_SOOTV_USER_SYS "
                    +" from ASU_POL.ADMIN_LOGS where  ID_SOOTV_USER_SYS is null ");
            int kol = 1;
            PrintStream out = System.out;
            while (rs_0.next()) {
                out.println("kol: " + kol);

               String[] sd=  rs_0.getString("INFO").split(" ");
                out.println( sd.length+"====="+sd[sd.length-2]);

                PreparedStatement stmt_up_main_job = conn.prepareStatement(
                        " UPDATE ASU_POL.ADMIN_LOGS SET  "
                        + " ID_SOOTV_USER_SYS = " + sd[sd.length-2]+ " "
                        + " where ID=" + rs_0.getInt("ID") + " ");
                stmt_up_main_job.executeUpdate();
                stmt_up_main_job.close();
                kol++;

            }
            rs_0.close();
            stmt_0.close();


            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Обязательно закрываем соединение с БД!
            new Connection_ASU_POL().closeConnection(conn);
        }

    }
}
