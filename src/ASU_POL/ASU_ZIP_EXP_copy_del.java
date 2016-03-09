/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL;

/**
 *
 * @author apopovkin
 */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.text.*;
import javax.mail.*;
import javax.mail.internet.*;

public class ASU_ZIP_EXP_copy_del {

    public static void main(String[] args) throws Exception, PatternSyntaxException {

        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar DT_NOW_1 = Calendar.getInstance();
        String date = dateFormatter1.format(DT_NOW_1.getTime()).toString();
        Vector report_sys = new Vector();
        Vector report_asuzir = new Vector();
        Vector report_user_main = new Vector();
        PrintStream out = System.out;

        System.out.print("Begin:");
        String DB2 = "jdbc:db2:ASU_POL4";
        String UserID = "db2admin";
        String Password = "11111111";

        Connection conn = null;

        try {
            conn = new Connection_ASU_POL().getConnection(UserID, Password, DB2);


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    " select a1.T_NO,a1.ID_ZAYAV,a1.ID_REG,a1.ID_RES,a1.LN_USER,a1.FN_USER,a1.SN_USER "
                    + "from ASU_POL.ASU_ZIR_ALL_ZAYAV a1  "
                     + "inner join ASU_POL.MAIN_USER_ALL a2 on ID_ROAD_ASU_TR=ORGTX_1 and a2.tab_num=a1.T_NO and  "
                     + "a2.LAST_NAME=a1.LN_USER and a2.FIRST_NAME=a1.FN_USER and a2.MIDDLE_NAME=a1.SN_USER    "
                     + "inner join ASU_POL.SOOTV_USER_SYS  a3 on a2.id=a3.id_user and a3.ID_SYSTEM=a1.ID_RES   "
                     + "where  a3.del=0  ");
int i=1;
            while (rs.next()) {

               out.println(i+"  -  "+rs.getInt("T_NO"));
i++;
                PreparedStatement stmt_up_main_job = conn.prepareStatement(
                        " UPDATE ASU_POL.ASU_ZIR_ALL_ZAYAV SET  "
                        + " VVED = 1 "
                        + " where  "
                        + " ID_ZAYAV=" + rs.getInt("ID_ZAYAV") + " and  "
                        + " ID_REG='" + rs.getString("ID_REG") + "' and ID_RES=" + rs.getInt("ID_RES") + " and  "
                        + " LN_USER='" + rs.getString("LN_USER") + "' and FN_USER='" + rs.getString("FN_USER") + "' and "
                        + " SN_USER='" + rs.getString("SN_USER") + "' ");
                stmt_up_main_job.executeUpdate();

                stmt_up_main_job.close();
            }
            rs.close();
            stmt.close();

conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Обязательно закрываем соединение с БД!
            new Connection_ASU_POL().closeConnection(conn);
        }
    }
}
