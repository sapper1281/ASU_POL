/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.text.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author  apopovkin
 */
public class ASU_POL_MAIN_USER {

    public static int c;
    public static String Inarr[];

    /** Creates a new instance of ASU_POL_STRUK */
    public ASU_POL_MAIN_USER() {
    }

    /*
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception, PatternSyntaxException {
        Vector report_0 = new Vector();
        Vector report_1 = new Vector();

        PrintStream out = System.out;
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Calendar time1 = Calendar.getInstance();
        out.print("Begin: " + dateFormatter3.format(time1.getTime()).toString() + "\n");


        System.out.print("Begin:");
        String DB2 = "jdbc:db2:ASU_POL4";
        String UserID = "db2admin";
        String Password = "11111111";

        Connection conn = null;

        try {
            conn = new Connection_ASU_POL().getConnection(UserID, Password, DB2);

            out.println("Выполнение запроса 1(находим записи которые есть в USER_ASU_TR  и нет в   MAIN_USER_ALL)");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    " select  a1.TAB_NUM,  a1.FN_SHTAT_JOB, "
                    + " a1.LAST_NAME, a1.FIRST_NAME, a1.MIDDLE_NAME,  a1.ID_JOB, "
                    + " a1.ID_ROAD_ASU_TR, a1.RPPS,   a1.PFR, a1.PER,a3.ALL_FN_ROAD "
                    + " from ASU_POL.NEW_USER_ASU_TR as a1 "
                    + " left join ASU_POL.MAIN_USER_ALL as a2 on a1.ID_ROAD_ASU_TR=a2.ID_ROAD_ASU_TR and "
                    + " a1.TAB_NUM=a2.TAB_NUM and "
                    + " a1.LAST_NAME=a2.LAST_NAME and a1.FIRST_NAME=a2.FIRST_NAME "
                    + " and a1.MIDDLE_NAME=a2.MIDDLE_NAME "
                    + " left join  "
                    + " (select ALL_FN_ROAD,ID_ROAD_ASU_TR "
                    + " from  "
                    + " ASU_POL.POLYGON_ROAD   "
                    + " where  "
                    + " DT_VVOD=(select max(DT_VVOD) from ASU_POL.POLYGON_ROAD where r3 ='yes')) as a3 "
                    + " on a1.ID_ROAD_ASU_TR=a3.ID_ROAD_ASU_TR  "
                    + " where a2.ID is null ");
            time1 = Calendar.getInstance();
            out.println("Выполнен запрос 1 находим записи которые есть в USER_ASU_TR  и нет в   MAIN_USER_ALL : " + dateFormatter3.format(time1.getTime()).toString() + "\n");

            while (rs.next()) {
                out.println("Результат 1: ");
                String query = " insert into ASU_POL.MAIN_USER_ALL ( "
                        + " ID_ROAD_ASU_TR, TAB_NUM, ID_JOB,BLOCK_USER, PFR, "
                        + " LAST_NAME, FIRST_NAME, MIDDLE_NAME, DT_BIG, ROAD,  SN_JOB, RPPS "
                        + //" ) values ( ?, ?, ?,0, ?, ?, ?, ?, ?, ?, ? )"+
                        " ) values ( "
                        + rs.getInt("ID_ROAD_ASU_TR") + ", "
                        + rs.getInt("TAB_NUM") + ", "
                        + rs.getInt("ID_JOB") + ", "
                        + "0, "
                        + "'" + rs.getString("PFR") + "', "
                        + "'" + rs.getString("LAST_NAME") + "', "
                        + "'" + rs.getString("FIRST_NAME") + "', "
                        + "'" + rs.getString("MIDDLE_NAME") + "', "
                        + "'" + rs.getString("PER") + "', "
                        + "'" + rs.getString("ALL_FN_ROAD") + "', "
                        + "'" + rs.getString("FN_SHTAT_JOB") + "', "
                        + "'" + rs.getString("RPPS") + "') ";

                PreparedStatement myStmt = conn.prepareStatement(query);
                /*myStmt.setInt(1,rs.getInt("ID_ROAD_ASU_TR"));
                myStmt.setInt(2,rs.getInt("TAB_NUM"));
                myStmt.setInt(3,rs.getInt("ID_JOB"));
                myStmt.setString(4,rs.getString("PFR"));
                myStmt.setString(5,rs.getString("LAST_NAME"));
                myStmt.setString(6,rs.getString("FIRST_NAME"));
                myStmt.setString(7,rs.getString("MIDDLE_NAME"));
                myStmt.setString(8,rs.getString("PER"));
                myStmt.setString(9,rs.getString("FN_SHTAT_JOB"));
                myStmt.setString(10,rs.getString("RPPS"));*/
                myStmt.executeUpdate();
                myStmt.close();
            }
            rs.close();
            stmt.close();
            time1 = Calendar.getInstance();
            out.println("Выполнена обработка запроса 1 запись в   MAIN_USER_ALL записей которые есть в USER_ASU_TR  и нет в   MAIN_USER_ALL : " + dateFormatter3.format(time1.getTime()).toString() + "\n");


            out.println("Выполнение запроса 2(обработка в случае переходе на другую должность)");

            Statement stmt_0 = conn.createStatement();
            ResultSet rs_0 = stmt_0.executeQuery(
                    " select  a2.TAB_NUM,a1.ID_JOB ,a3.ALL_FN_ROAD,a2.ID,a1.SN_SHTAT_JOB "
                    + " from ASU_POL.MAIN_USER_ALL as a2 "
                    + " left  join ASU_POL.NEW_USER_ASU_TR as a1 on a1.ID_ROAD_ASU_TR=a2.ID_ROAD_ASU_TR "
                    + " and a1.TAB_NUM=a2.TAB_NUM and "
                    + " a1.LAST_NAME=a2.LAST_NAME and a1.FIRST_NAME=a2.FIRST_NAME and a1.MIDDLE_NAME=a2.MIDDLE_NAME "
                    + " left join  "
                    + " (select ALL_FN_ROAD,ID_ROAD_ASU_TR "
                    + " from  "
                    + " ASU_POL.POLYGON_ROAD   "
                    + " where  "
                    + " DT_VVOD=(select max(DT_VVOD) from ASU_POL.POLYGON_ROAD where r3 ='yes')) as a3 "
                    + " on a1.ID_ROAD_ASU_TR=a3.ID_ROAD_ASU_TR  "
                    + " where a1.ID_JOB<>a2.ID_JOB and block_user<>1 ");

            time1 = Calendar.getInstance();
            out.println("Выполнен запрос 2 находим записи которые есть в USER_ASU_TR  и в  MAIN_USER_ALL но разные должности : " + dateFormatter3.format(time1.getTime()).toString() + "\n");

            while (rs_0.next()) {
                out.println("Результат 2: " + rs_0.getInt("TAB_NUM"));
                PreparedStatement stmt_up_main_job = conn.prepareStatement(
                        " UPDATE ASU_POL.MAIN_USER_ALL SET  "
                        + " ID_JOB = " + rs_0.getInt("ID_JOB") + ", "
                        + " SN_JOB = '" + rs_0.getString("SN_SHTAT_JOB") + "', "

                        + " ROAD = '" + rs_0.getString("ALL_FN_ROAD") + "' "
                        + " where ID=" + rs_0.getInt("ID") + " ");
                stmt_up_main_job.executeUpdate();
                stmt_up_main_job.close();

            }
            rs_0.close();
            stmt_0.close();

            time1 = Calendar.getInstance();
            out.println("Выполнена обработка запроса 2 обновляем записи MAIN_USER_ALL которые есть в USER_ASU_TR  и в  MAIN_USER_ALL но разные должности : " + dateFormatter3.format(time1.getTime()).toString() + "\n");

            out.println("Выполнение запроса 3(находим записи которые есть в MAIN_USER_ALL не блокированные и нет в   USER_ASU_TR)");

            Statement stmt_1 = conn.createStatement();
            ResultSet rs_1 = stmt_1.executeQuery(
                    " select a2.ID, a2.ID_ROAD_ASU_TR, a2.TAB_NUM, a2.ID_JOB, a2.BLOCK_USER, a2.PFR, "
                    + " a2.LAST_NAME, a2.FIRST_NAME, a2.MIDDLE_NAME, a2.TEL, a2.IP, a2.E_MAIL, a2.DT_BIG,a2.DT_END, a2.ROAD,a2.SN_JOB, a2.RPPS "
                    + " from ASU_POL.MAIN_USER_ALL as a2 "
                    + " left  join ASU_POL.NEW_USER_ASU_TR as a1 on a1.ID_ROAD_ASU_TR=a2.ID_ROAD_ASU_TR "
                    + " and a1.TAB_NUM=a2.TAB_NUM and "
                    + " a1.LAST_NAME=a2.LAST_NAME and a1.FIRST_NAME=a2.FIRST_NAME and a1.MIDDLE_NAME=a2.MIDDLE_NAME "

                    + " where a1.ID_ROAD_ASU_TR is null and block_user<>1 and R3_YES_OR_NIOT is null ");
            time1 = Calendar.getInstance();
            out.println("Выполнен запрос 3 находим записи которые есть в MAIN_USER_ALL не блокированные и нет в   USER_ASU_TR  : " + dateFormatter3.format(time1.getTime()).toString() + "\n");


            while (rs_1.next()) {

                out.println("Результат 3: " + rs_1.getInt("TAB_NUM"));








            out.println("Выполнение запроса 4(администраторы)");

//проверка на примере user

 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!




                             out.println("Выполнение запроса 4_1(администраторы)");

               // находим администраторов систем блокиров пользователей
                Statement stmt_4 = conn.createStatement();
                ResultSet rs_4 = stmt_4.executeQuery(
                        " select "
                        + " a4.LAST_NAME,a4.FIRST_NAME, a4.MIDDLE_NAME,a4.e_mail  "
                        + " from (select ID_SYSTEM "
                        + " from ASU_POL.SOOTV_USER_SYS    "
                        + " where id_user=" + rs_1.getInt("ID") + " and del=0  "
                        + " group by ID_SYSTEM "
                        + " ) as a1  "
                        + " inner join ASU_POL.SOOTV_USER_SYS as a3 on a3.id_system=a1.id_system and a3.id_role=2 and del=0 "
                        + " left join ASU_POL.MAIN_USER_ALL as a4 on a4.id=a3.id_user and a4.block_user<>1 "
                       // + " where   "
                        + "  group by a4.last_name,a4.first_name, a4.middle_name,a4.e_mail  ");








                //находим записи которые есть в SOOTV_USER_SYS соответствующие
                //записям которые есть в MAIN_USER_ALL не блокированные и нет в   USER_ASU_TR
                out.println("Выполнение запроса 3_3(находим записи которые есть в SOOTV_USER_SYS)");
                Statement stmt_3 = conn.createStatement();
                ResultSet rs_3 = stmt_3.executeQuery(
                        " select  a1.ID, a1.ID_USER, a1.ID_SYSTEM, "
                        + " a1.ID_USL, a1.ID_ROLE, a1.DT_BIG_Z, "
                        + " a1.DT_END_Z, a1.DT_BIG, a1.DT_END,a1.ID_STAN,a2.FN_SYSTEM, a1.del,a1.R3_YES_OR_NIOT,a1.DOK_ZAYVKI "
                        + " from ASU_POL.SOOTV_USER_SYS as a1 "
                        + " left join ASU_POL.POLYGON_SYSTEM as a2 on a1.id_system=a2.id_system "
                        + " where a1.id_user=" + rs_1.getInt("ID") + " "
                        + " order by a1.ID;");
                time1 = Calendar.getInstance();
                out.println("Выполнен запрос 3_3 находим записи которые есть в SOOTV_USER_SYS соответствующие   : " + dateFormatter3.format(time1.getTime()).toString() + "\n");









                out.println("Выполнение запроса 3_1 перенос в MAIN_USER_ALL_BLOCK ");
                // удаление из MAIN_USER_ALL и перезапись в MAIN_USER_ALL_BLOCK

                    String query_MAIN_USER_ALL_BLOCK = " insert into ASU_POL.MAIN_USER_ALL_BLOCK ( "
                            + " ID,ID_ROAD_ASU_TR, TAB_NUM, ID_JOB,BLOCK_USER, PFR, "
                            + " LAST_NAME, FIRST_NAME, MIDDLE_NAME, "
                            + " TEL, IP, E_MAIL, "
                            + " DT_BIG,DT_END, ROAD, SN_JOB, RPPS "
                            //+ " ) values (?, ?, ?, ?,?, ?,?,?,?, ?, ?, ?, ?,?, ?,?,?,? )";
                            + " ) values ( "
                            + rs_1.getInt("ID") + ", "
                            + rs_1.getInt("ID_ROAD_ASU_TR") + ", "
                            + rs_1.getInt("TAB_NUM") + ", "
                            + rs_1.getInt("ID_JOB") + ", "
                            + " 1, "
                            + "'" + rs_1.getString("PFR") + "', "
                            + "'" + rs_1.getString("LAST_NAME") + "', "
                            + "'" + rs_1.getString("FIRST_NAME") + "', "
                            + "'" + rs_1.getString("MIDDLE_NAME") + "', "
                            + "'" + rs_1.getString("TEL") + "', "
                            + "'" + rs_1.getString("IP") + "', "
                            + "'" + rs_1.getString("E_MAIL") + "', "
                            + "'" + rs_1.getString("DT_BIG") + "', "
                            + "'" + dateFormatter1.format(time1.getTime()).toString() + "', "
                            + "'" + rs_1.getString("ROAD") + "', "
                            + "'" + rs_1.getString("SN_JOB") + "', "
                            + "'" + rs_1.getString("RPPS") + "') ";
                    
out.println(query_MAIN_USER_ALL_BLOCK);

                    PreparedStatement myStmt_MAIN_USER_ALL_BLOCK = conn.prepareStatement(query_MAIN_USER_ALL_BLOCK);
                    /*  myStmt_MAIN_USER_ALL_BLOCK.setInt(1, rs_MAIN_USER_ALL.getInt("ID"));
                    myStmt_MAIN_USER_ALL_BLOCK.setInt(2, rs_MAIN_USER_ALL.getInt("ID_ROAD_ASU_TR"));
                    myStmt_MAIN_USER_ALL_BLOCK.setInt(3, rs_MAIN_USER_ALL.getInt("TAB_NUM"));
                    myStmt_MAIN_USER_ALL_BLOCK.setInt(4, rs_MAIN_USER_ALL.getInt("ID_JOB"));
                    myStmt_MAIN_USER_ALL_BLOCK.setInt(5, 1);
                    myStmt_MAIN_USER_ALL_BLOCK.setString(6, rs_MAIN_USER_ALL.getString("PFR"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(7, rs_MAIN_USER_ALL.getString("LAST_NAME"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(8, rs_MAIN_USER_ALL.getString("FIRST_NAME"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(9, rs_MAIN_USER_ALL.getString("MIDDLE_NAME"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(10, rs_MAIN_USER_ALL.getString("TEL"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(11, rs_MAIN_USER_ALL.getString("IP"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(12, rs_MAIN_USER_ALL.getString("E_MAIL"));
                    myStmt_MAIN_USER_ALL_BLOCK.setDate(13, rs_MAIN_USER_ALL.getDate("DT_BIG"));
                    myStmt_MAIN_USER_ALL_BLOCK.setDate(14, rs_MAIN_USER_ALL.getDate("DT_END"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(15, rs_MAIN_USER_ALL.getString("PRIZ"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(16, rs_MAIN_USER_ALL.getString("ROAD"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(17, rs_MAIN_USER_ALL.getString("SN_JOB"));
                    myStmt_MAIN_USER_ALL_BLOCK.setString(18, rs_MAIN_USER_ALL.getString("RPPS"));*/
                    myStmt_MAIN_USER_ALL_BLOCK.executeUpdate();
                    myStmt_MAIN_USER_ALL_BLOCK.close();

            
                String query_MAIN_USER_ALL_DEL = " DELETE FROM ASU_POL.MAIN_USER_ALL "
                        + "where ID=" + rs_1.getInt("ID") + " ";

                PreparedStatement myStmt_MAIN_USER_ALL_DEL = conn.prepareStatement(query_MAIN_USER_ALL_DEL);
                myStmt_MAIN_USER_ALL_DEL.executeUpdate();
                myStmt_MAIN_USER_ALL_DEL.close();












                
                out.println("Выполнение запроса 3_2 перенос в SOOTV_USER_STAN_BLOCK ");
                Statement stmt_SOOTV_USER_STAN = conn.createStatement();
                ResultSet rs_SOOTV_USER_STAN = stmt_SOOTV_USER_STAN.executeQuery(
                        " select ID, ID_USER, ID_STAN, DT_BIG, DT_END "
                        + "  from ASU_POL.SOOTV_USER_STAN "
                        + " where  ID_USER=" + rs_1.getInt("ID") + " ");

                while (rs_SOOTV_USER_STAN.next()) {

                    String query_SOOTV_USER_STAN = " insert into ASU_POL.SOOTV_USER_STAN_BLOCK ( ID, ID_USER, ID_STAN, DT_BIG, DT_END "
                            // + " ) values (?, ?, ?, ?, ? )";
                            + " ) values ( "
                            + rs_SOOTV_USER_STAN.getInt("ID") + ","
                            + rs_SOOTV_USER_STAN.getInt("ID_USER") + ","
                            + rs_SOOTV_USER_STAN.getInt("ID_STAN") + ","
                            + "'" + rs_SOOTV_USER_STAN.getDate("DT_BIG") + "',"
                            + "'" + dateFormatter1.format(time1.getTime()).toString() + "')";

                    PreparedStatement myStmt_SOOTV_USER_STAN_BLOCK = conn.prepareStatement(query_SOOTV_USER_STAN);
                    /*  myStmt_SOOTV_USER_STAN_BLOCK.setInt(1, rs_SOOTV_USER_STAN.getInt("ID"));
                    myStmt_SOOTV_USER_STAN_BLOCK.setInt(2, rs_SOOTV_USER_STAN.getInt("ID_USER"));
                    myStmt_SOOTV_USER_STAN_BLOCK.setInt(3, rs_SOOTV_USER_STAN.getInt("ID_STAN"));
                    myStmt_SOOTV_USER_STAN_BLOCK.setDate(4, rs_SOOTV_USER_STAN.getDate("DT_BIG"));
                    myStmt_SOOTV_USER_STAN_BLOCK.setDate(5, dateFormatter1.format(time1.getTime()).toString());*/
                    myStmt_SOOTV_USER_STAN_BLOCK.executeUpdate();
                    myStmt_SOOTV_USER_STAN_BLOCK.close();

                }
                rs_SOOTV_USER_STAN.close();
                stmt_SOOTV_USER_STAN.close();

                String query_SOOTV_USER_STAN_DEL = " DELETE FROM ASU_POL.SOOTV_USER_STAN "
                        + " where ID_USER=" + rs_1.getInt("ID") + "  ";

                PreparedStatement myStmt_SOOTV_USER_STAN_DEL = conn.prepareStatement(query_SOOTV_USER_STAN_DEL);
                myStmt_SOOTV_USER_STAN_DEL.executeUpdate();
                myStmt_SOOTV_USER_STAN_DEL.close();
















                  
                String System_user = "";
                while (rs_3.next()) {

               
                out.println("-Выполнен запрос 3_3_1");
                    System_user = System_user + rs_3.getString("FN_SYSTEM").trim() + ", ";

                    // блокируем записи в   SOOTV_USER_SYS
                    if (rs_3.getInt("del") == 0 || rs_3.getInt("del") == 2) {


                        if(rs_3.getInt("ID_ROLE")==2){


                PreparedStatement stmt_up_main_ID_USER = conn.prepareStatement(
                        " UPDATE ASU_POL.ADMIN_PAS SET  "
                        + " DT_END = '" + dateFormatter1.format(time1.getTime()).toString() + "'  "
                        + " where ID_USER=" + rs_3.getInt("ID_USER") + " ");
                stmt_up_main_ID_USER.executeUpdate();
                stmt_up_main_ID_USER.close();






                        }





                       out.println("-Выполнен запрос 3_3_2");
                          String query_SOOTV_USER_SYS = " insert into ASU_POL.SOOTV_USER_SYS_BLOCK (ID, ID_USER, ID_SYSTEM, ID_USL, ID_ROLE, DT_BIG_Z,"
                                    + " DT_END_Z, DT_BIG, DT_END, ID_STAN, del,R3_YES_OR_NIOT,DOK_ZAYVKI "
                                    // + " ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'yes',' " + rs_SOOTV_USER_SYS.getString("DOK_ZAYVKI").trim() + "' )";

                                    + " ) values ( "
                                    + rs_3.getInt("ID") + ","
                                    + rs_3.getInt("ID_USER") + ","
                                    + rs_3.getInt("ID_SYSTEM") + ","
                                    + rs_3.getInt("ID_USL") + ","
                                    + rs_3.getInt("ID_ROLE") + ","
                                    + " '" + rs_3.getString("DT_BIG_Z") + "',"
                                    + " '" + rs_3.getString("DT_END_Z") + "',"
                                    + " '" + rs_3.getString("DT_BIG") + "',";

                            if (rs_3.getInt("del") == 0) {
                                query_SOOTV_USER_SYS = query_SOOTV_USER_SYS + " '" + dateFormatter1.format(time1.getTime()).toString() + "',";
                            } else {
                                query_SOOTV_USER_SYS = query_SOOTV_USER_SYS + " '" + rs_3.getString("DT_END") + "',";
                            }
                            query_SOOTV_USER_SYS = query_SOOTV_USER_SYS + rs_3.getInt("ID_STAN") + ",";
                            if (rs_3.getInt("del") == 0) {
                                query_SOOTV_USER_SYS = query_SOOTV_USER_SYS + " 3,";
                            } else {
                                query_SOOTV_USER_SYS = query_SOOTV_USER_SYS + rs_3.getInt("del") + ",";
                            }
                            query_SOOTV_USER_SYS = query_SOOTV_USER_SYS + "'yes',' " + rs_3.getString("DOK_ZAYVKI").trim() + "' )";


                            out.println(query_SOOTV_USER_SYS);




                            PreparedStatement myStmt_SOOTV_USER_SYS_BLOCK = conn.prepareStatement(query_SOOTV_USER_SYS);
                            /* myStmt_SOOTV_USER_SYS_BLOCK.setInt(1, rs_SOOTV_USER_SYS.getInt("ID"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setInt(2, rs_SOOTV_USER_SYS.getInt("ID_USER"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setInt(3, rs_SOOTV_USER_SYS.getInt("ID_SYSTEM"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setInt(4, rs_SOOTV_USER_SYS.getInt("ID_USL"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setInt(5, rs_SOOTV_USER_SYS.getInt("ID_ROLE"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setDate(6, rs_SOOTV_USER_SYS.getDate("DT_BIG_Z"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setDate(7, rs_SOOTV_USER_SYS.getDate("DT_END_Z"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setDate(8, rs_SOOTV_USER_SYS.getDate("DT_BIG"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setDate(9, rs_SOOTV_USER_SYS.getDate("DT_END"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setInt(10, rs_SOOTV_USER_SYS.getInt("ID_STAN"));
                            myStmt_SOOTV_USER_SYS_BLOCK.setInt(11, rs_SOOTV_USER_SYS.getInt("del"));*/
                            myStmt_SOOTV_USER_SYS_BLOCK.executeUpdate();
                            myStmt_SOOTV_USER_SYS_BLOCK.close();



                        }
                       out.println("-Выполнен запрос 3_3_3");
                        String query_SOOTV_USER_SYS_DEL_2 = " DELETE FROM ASU_POL.SOOTV_USER_SYS "
                                + " where ID=" + rs_3.getInt("ID") + "  ";

                        PreparedStatement myStmt_SOOTV_USER_SYS_DEL_2 = conn.prepareStatement(query_SOOTV_USER_SYS_DEL_2);
                        myStmt_SOOTV_USER_SYS_DEL_2.executeUpdate();
                        myStmt_SOOTV_USER_SYS_DEL_2.close();
                    }
                
                stmt_3.close();
                rs_3.close();
                time1 = Calendar.getInstance();
                out.println("Выполнен запрос 3_3 обнавление данных  : " + dateFormatter3.format(time1.getTime()).toString() + "\n");








 out.println("Выполнение запроса 4_2(администраторы)");
                while (rs_4.next()) {
 out.println("Выполнение запроса 4_3(администраторы)");
                    ASU_POL_MAIN_USER_CLASS line_0 = new ASU_POL_MAIN_USER_CLASS();
                    line_0.setSN_SYSTEM(System_user);
                    line_0.setLAST_NAME(rs_1.getString("LAST_NAME"));
                    line_0.setFIRST_NAME(rs_1.getString("FIRST_NAME"));
                    line_0.setMIDDLE_NAME(rs_1.getString("MIDDLE_NAME"));
                    line_0.setLAST_NAME_adm(rs_4.getString("LAST_NAME"));
                    line_0.setFIRST_NAME_adm(rs_4.getString("FIRST_NAME"));
                    line_0.setMIDDLE_NAME_adm(rs_4.getString("MIDDLE_NAME"));
                    line_0.setE_MAIL(rs_4.getString("e_mail"));
                    line_0.setRoad_user(rs_1.getString("ROAD").trim());
                    line_0.setpr(0);
                    report_0.add(line_0);
                }
                rs_4.close();
                stmt_4.close();








                

            }
            rs_1.close();
            stmt_1.close();

 out.println("report_0.size()="+report_0.size());








            out.println("Выполнение запроса 6 отправка писем ");
            for (int i = 0; i < report_0.size(); i++) {
                ASU_POL_MAIN_USER_CLASS line0 = (ASU_POL_MAIN_USER_CLASS) report_0.get(i);
                boolean t = false;
                for (int i1 = 0; i1 < report_1.size(); i1++) {
                    ASU_POL_MAIN_USER_CLASS line0_1 = (ASU_POL_MAIN_USER_CLASS) report_1.get(i1);
                    if (!t) {
                    out.println("Выполнение запроса 6_1_0_0 "+line0.getLAST_NAME_adm());
                    out.println("Выполнение запроса 6_1_0_1 "+line0.getLAST_NAME());
                    out.println("Выполнение запроса 6_1_0_2 "+line0.getFIRST_NAME());
                    out.println("Выполнение запроса 6_1_0_3 "+line0.getMIDDLE_NAME());
                      out.println("Выполнение запроса 6_1_0 "+line0.getE_MAIL());
                      out.println("Выполнение запроса 6_1_1 "+line0_1.getE_MAIL());

                        t = line0.getE_MAIL().equals(line0_1.getE_MAIL());
                    }
                }

                if (!t) {
                    ASU_POL_MAIN_USER_CLASS line_1 = new ASU_POL_MAIN_USER_CLASS();
                    line_1.setLAST_NAME_adm(line0.getLAST_NAME_adm());
                    line_1.setFIRST_NAME_adm(line0.getFIRST_NAME_adm());
                    line_1.setMIDDLE_NAME_adm(line0.getMIDDLE_NAME_adm());
                    line_1.setE_MAIL(line0.getE_MAIL());
                    out.println("Выполнение запроса 6_1 "+line0.getE_MAIL());
                    report_1.add(line_1);
                }
            }




            for (int i = 0; i < report_1.size(); i++) {
                ASU_POL_MAIN_USER_CLASS line0_1 = (ASU_POL_MAIN_USER_CLASS) report_1.get(i);
                int k = 0;
                String to =line0_1.getE_MAIL();
                //String to="apopovkin@serw.rzd";
                String us = "<h3>АСУ \"Пользователь\" </h3>"
                        + "<h3>Добрый день: " + line0_1.getLAST_NAME_adm() + " " + line0_1.getFIRST_NAME_adm() + " " + line0_1.getMIDDLE_NAME_adm() + " </h3>"
                        + "<h3>Следующие пользователи блокированы :  </h3>"
                        + // "<h3>Необходимо переоформить заявку:  </h3>"+
                        "<table border='1'>"
                        + "<tr><td>Система:</td><td>Пользователь:</td><td>Структурное подразделение:</td></tr>";

                for (int i1 = 0; i1 < report_0.size(); i1++) {
                    ASU_POL_MAIN_USER_CLASS line0 = (ASU_POL_MAIN_USER_CLASS) report_0.get(i1);

                    if (line0_1.getE_MAIL().equals(line0.getE_MAIL())) {
                        k++;
                        us = us + "<tr><td>" + line0.getSN_SYSTEM() + " </td>"
                                + "<td> " + line0.getLAST_NAME() + " " + line0.getFIRST_NAME()
                                + " " + line0.getMIDDLE_NAME() + " </td>"
                                + "<td> " + line0.getRoad_user() + " </td></tr>";
                        if (k > 15) {
                            us = us + "</table>";
                            out.print("Письмо " + to);

                            String from = "ASU_POL@serw.rzd";
                            String host = "10.58.0.47";
                            Properties properties = System.getProperties();
                            properties.setProperty("mail.smtp.host", host);
                            Session session = Session.getDefaultInstance(properties);

                            try {
                                MimeMessage message = new MimeMessage(session);
                                message.setFrom(new InternetAddress(from));
                                message.addRecipient(Message.RecipientType.TO,
                                        new InternetAddress(to));
                                message.setSubject("Пользователь блокирован");
                                message.setContent(us, "text/html;charset=UTF-8");




                                //       message.setText("данные отправлены");

                                // Send message
                                Transport.send(message);
                                System.out.println("Sent message successfully....");
                            } catch (MessagingException mex) {
                                mex.printStackTrace();
                            }
                            //if (k > 10) {
                            k = 0;
                            us = "<h3>АСУ \"Пользователь\" </h3>"
                                    + "<h3>Добрый день: " + line0_1.getLAST_NAME_adm()
                                    + " " + line0_1.getFIRST_NAME_adm() + " "
                                    + line0_1.getMIDDLE_NAME_adm() + " </h3>"
                                    + "<h3>Следующие пользователи блокированы  :  </h3>"
                                    + // "<h3>Необходимо переоформить заявку:  </h3>"+
                                    "<table border='1'>"
                                    + "<tr><td>Система:</td><td>Пользователь:</td><td>Структурное подразделение:</td></tr>";
                            // }
                        }

                    }



                    if ((i1 == report_0.size() - 1) && k > 0) {
                        out.println("Последнее письмо ");
                        out.println("Письмо " + to);
                        us = us + "</table>";

                        String from = "ASU_POL@serw.rzd";
                        String host = "10.58.0.47";
                        Properties properties = System.getProperties();
                        properties.setProperty("mail.smtp.host", host);
                        Session session = Session.getDefaultInstance(properties);

                        try {
                            MimeMessage message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(from));
                            message.addRecipient(Message.RecipientType.TO,
                                    new InternetAddress(to));
                            message.setSubject("Пользователь блокирован");
                            message.setContent(us, "text/html;charset=UTF-8");




                            //       message.setText("данные отправлены");

                            // Send message
                            Transport.send(message);
                            System.out.println("Sent message successfully....");
                        } catch (MessagingException mex) {
                            mex.printStackTrace();
                        }
                    }
                }
            }

            report_1.clear();
            report_0.clear();

            out.println("Выполнен запрос 6 отправка писем ");


conn.commit();




            } catch (Exception e) {
            e.printStackTrace();
            } finally {
            // Обязательно закрываем соединение с БД!
            new Connection_ASU_POL().closeConnection(conn);
            }


      /*  } catch (Exception e) {
            out.print("DB error" + e + "\n");
            //   Rec_DB(err);
            System.exit(1);
        }*/


    }
}
