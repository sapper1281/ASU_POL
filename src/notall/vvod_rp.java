/*Добавление раб персонала в main
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
public class vvod_rp {

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
                    " select a1.TAB_NUM,  a1.FN_SHTAT_JOB,  "
                    + "  a1.LAST_NAME, a1.FIRST_NAME, a1.MIDDLE_NAME,  a1.ID_JOB,  "
                    + "  a1.ID_ROAD_ASU_TR, a1.RPPS,   a1.PFR, a1.PER,a1.ALL_FN_ROAD,a2.* "
                    + " 		 from( "
                    + "    select  a1.TAB_NUM,  a1.FN_SHTAT_JOB,  "
                    + "    a1.LAST_NAME, a1.FIRST_NAME, a1.MIDDLE_NAME,  a1.ID_JOB,  "
                    + "    a1.ID_ROAD_ASU_TR, a1.RPPS,   a1.PFR, a1.PER,a3.ALL_FN_ROAD "
                    + " 			 from ASU_POL.NEW_USER_ASU_TR as a1  "
                    + " 		 left join (select a1.id,a1.TAB_NUM,a1.LAST_NAME,a1.FIRST_NAME,a1.MIDDLE_NAME,a1.RPPS "
                    + " 	     from ASU_POL.MAIN_USER_ALL as a1 "
                    + " 	     left  join ASU_POL.POLYGON_ROAD as a2 on a2.ID_ROAD_ASU_TR=a1.ID_ROAD_ASU_TR "
                    + " 	     where (a2.DT_VVOD=(select max(DT_VVOD) from ASU_POL.POLYGON_ROAD) and r3='yes') "
                    + " 		 and a2.CODE_ROAD_ASU_TR is not null) as a2 "
                    + " 		 on a1.RPPS=a2.RPPS and  "
                    + "   a1.TAB_NUM=a2.TAB_NUM and  "
                    + "   a1.LAST_NAME=a2.LAST_NAME and a1.FIRST_NAME=a2.FIRST_NAME  "
                    + "   and a1.MIDDLE_NAME=a2.MIDDLE_NAME  "
                    + " 		 left join   "
                    + "  (select id,ALL_FN_ROAD,ID_ROAD_ASU_TR  "
                    + "  from ASU_POL.POLYGON_ROAD    "
                    + "  where  DT_VVOD=(select max(DT_VVOD) from ASU_POL.POLYGON_ROAD)and r3='yes') as a3  "
                    + "  on a1.ID_ROAD_ASU_TR=a3.ID_ROAD_ASU_TR   "
                    + " 		where  "
                    + " 		a3.id is not null and a2.ID is null  "
                    + " 		) as a1 "
                    + " 		 left join (select a1.* "
                    + " from ASU_POL.MAIN_USER_ALL as a1 "
                    + " left  join ASU_POL.POLYGON_ROAD as a2 on a2.ID_ROAD_ASU_TR=a1.ID_ROAD_ASU_TR "
                    + " where  "
                    + " (a2.DT_VVOD=(select max(DT_VVOD) from ASU_POL.POLYGON_ROAD) and r3='yes')  "
                    + " and a2.CODE_ROAD_ASU_TR is not null "
                    + " ) as a2 on a1.TAB_NUM=a2.TAB_NUM and  "
                    + "    a1.LAST_NAME=a2.LAST_NAME and a1.FIRST_NAME=a2.FIRST_NAME  "
                    + "    and a1.MIDDLE_NAME=a2.MIDDLE_NAME and a1.ID_ROAD_ASU_TR=a2.ID_ROAD_ASU_TR "
                    + " 	 where a2.id is not null");
            int kol = 1;
            PrintStream out = System.out;
            while (rs_0.next()) {
                out.println("kol: " + kol);
                PreparedStatement stmt_up_main_job = conn.prepareStatement(
                        " UPDATE ASU_POL.MAIN_USER_ALL SET  "
                        + " RPPS = '" + rs_0.getString("RPPS") + "' "
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
