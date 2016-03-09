/*
 * TEXT To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL;

import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;

/**
 *
 * @author apopovkin
 */
public class ASU_POL_VVOD_POL_TEXT {

    private static SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private static void updateTable(Connection conn, String[] param, String beg_dt) throws SQLException {

        for (int i = 0; i < param.length; i++) {
            System.out.println(i + "  -  '" + param[i]+"'");
        }


        StringTokenizer fio = new StringTokenizer(param[9], " ");
        /*int i=0;
        while (i<param[5].length()){
        System.out.println("'"+param[5].charAt(i)+"'");
        i++;}*/
        System.out.println("Размер массива" + fio.countTokens());
        //String[] fioPOL = new String[fio.countTokens()];
        String[] fioPOL = new String[3];
        fioPOL[0] = "";
        fioPOL[1] = "";
        fioPOL[2] = "";

        int kPOL = 0;
        while (fio.hasMoreTokens()) {
            if (kPOL < 3) {
                fioPOL[kPOL] = fio.nextToken();
                System.out.println("ФИО " + kPOL + ":  " + fioPOL[kPOL]);

            } else {
                fioPOL[2] = fioPOL[2] + " " + fio.nextToken();
                System.out.println("ФИО " + kPOL + ":  " + fioPOL[2]);

            }

            kPOL++;
        }

        try {






            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into ASU_POL.NEW_USER_ASU_TR (PODR, TAB_NUM, SN_SHTAT_JOB, FN_SHTAT_JOB, KAT,"
                    + " LAST_NAME, FIRST_NAME, MIDDLE_NAME, ID_JOB, ID_ROAD_ASU_TR, RPPS, "
                    + " CODE_ROAD_ASU_TR, RAZD_PER, PFR, PER ) values "
                    + "( "
                    //+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                    + "'" + param[0] + "', "
                    + Integer.parseInt(param[1]) + ", "
                    + "'" + param[2] + "', "
                    + "'" + param[3] + "', "
                    + "'" + param[4] + "', "
                    + "'" + fioPOL[0] + "', "
                    + "'" + fioPOL[1] + "', "
                    + "'" + fioPOL[2] + "', "
                    + Integer.parseInt(param[12]) + ", "
                    + Integer.parseInt(param[13]) + ", "
                    + "'" + param[24] + "', "
                    + "'" + param[26] + "', "
                    + "'" + param[30] + "', "
                    + "'" + param[31] + "', "
                    // +"'"+ param[14]+"' "
                    + "'" + beg_dt + "'"
                    + ") ");




            /*pstmt.setString(1, param[0]);
            pstmt.setInt(2, Integer.parseInt(param[1]));
            pstmt.setString(3, param[2]);
            pstmt.setString(4, param[3]);
            pstmt.setString(5, param[4]);
            pstmt.setString(6, param[5]);
            pstmt.setString(7, param[6]);
            pstmt.setString(8, param[7]);
            pstmt.setInt(9, Integer.parseInt(param[8]));
            pstmt.setInt(10, Integer.parseInt(param[9]));
            pstmt.setInt(11, Integer.parseInt(param[10]));

            pstmt.setString(12, param[11]);
            pstmt.setString(13, param[12]);
            pstmt.setString(14, param[13]);
            pstmt.setString(15, param[14]);*/

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (SQLException e) {
            // Если произошла ошибка, откатываем изменения
            conn.rollback();
            System.out.println("Ошибюка записи");

            e.printStackTrace();
        }
    }

    private static boolean Del(Connection conn, String tabl, String beg_dt) throws SQLException {
     try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(PER) as PER FROM " + tabl + " ");
           
            // String PER1 = dateFormatter1.format(time.getTime()).toString();
            String PER = "not";
            // Достаем полученные из таблицы данны
            while (rs.next()) {
                PER = rs.getString("PER");

            }
if(PER==null){PER = "not";}
            rs.close();
            stmt.close();
System.out.println("++++++++1"+PER);
System.out.println("++++++++2"+beg_dt);
            return !PER.equals(beg_dt) && !PER.equals("not");
        } catch (SQLException e) {

            System.out.println("++++++++6666666666666"+beg_dt);

            return false;
        }
    }

    private static void createTable(Connection conn, String tabl) throws SQLException {
        // Создаем и заполняем теблицу, если она еще не существует


        if (!new Connection_ASU_POL().isTableExist(conn, tabl)) {
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(" CREATE TABLE ASU_POL.NEW_USER_ASU_TR "
                        + " (PODR              CHARACTER(200), "
                        + " TAB_NUM           INTEGER         NOT NULL, "
                        + " SN_SHTAT_JOB      CHARACTER(250), "
                        + " FN_SHTAT_JOB      CHARACTER(250), "
                        + " KAT               CHARACTER(200), "
                        //  + " DT_ROZH           DATE, "
                        //  + " DT_JOB_IN         DATE, "
                        + " LAST_NAME         CHARACTER(50)   NOT NULL, "
                        + " FIRST_NAME        CHARACTER(50)   NOT NULL, "
                        + " MIDDLE_NAME       CHARACTER(50)   NOT NULL, "
                        + " ID_JOB            INTEGER         NOT NULL, "
                        + " ID_ROAD_ASU_TR    INTEGER         NOT NULL, "
                        + " RPPS              CHARACTER(20)   NOT NULL, "
                        + " CODE_ROAD_ASU_TR  CHARACTER(20), "
                        + " RAZD_PER          CHARACTER(200), "
                        + " PFR               CHARACTER(20), "
                        + " PER               DATE "
                        // + " PRIZ              SMALLINT "
                        + " ) "
                        + " DATA CAPTURE NONE "
                        + " IN USERSPACE1 ");

                stmt.executeUpdate("ALTER TABLE ASU_POL.NEW_USER_ASU_TR"
                        + " LOCKSIZE ROW "
                        + " APPEND OFF "
                        + " NOT VOLATILE "
                        + " LOG INDEX BUILD NULL ");

                stmt.executeUpdate(" COMMENT ON ASU_POL.NEW_USER_ASU_TR "
                        + " (PODR IS 'Подразделение', "
                        + " TAB_NUM IS 'таб ном', "
                        + " SN_SHTAT_JOB IS 'Штатная должность', "
                        + " FN_SHTAT_JOB IS 'Полное наименование шт. дол.', "
                        + " KAT IS 'категория', "
                        //    + " DT_ROZH IS 'дата рождения', "
                        //    + " DT_JOB_IN IS 'дата приемки', "
                        + " LAST_NAME IS 'Фамилия', "
                        + " FIRST_NAME IS 'Имя', "
                        + " MIDDLE_NAME IS 'Отчество', "
                        + " ID_JOB IS 'id должности', "
                        + " ID_ROAD_ASU_TR IS 'id объекта', "
                        + " RPps IS 'балансовая единица', "
                        + " CODE_ROAD_ASU_TR IS 'код объекта', "
                        + " RAZD_PER IS 'раздел персоонала', "
                        + " PFR IS 'номер пнф', "
                        + " PER IS 'период отчета' "
                        //  + " PRIZ IS 'признак систем (H58-0, PHR-1,HFK-2)' "
                        + " ) ");

                stmt.executeUpdate(" ALTER TABLE ASU_POL.NEW_USER_ASU_TR "
                        + " ADD CONSTRAINT SQL100324150610720 PRIMARY KEY "
                        + " (ID_ROAD_ASU_TR, "
                        + " TAB_NUM, "
                        + " LAST_NAME, "
                        + " FIRST_NAME, "
                        + " MIDDLE_NAME, "
                        + " ID_JOB ,"
                        + " RPPS "
                        + " ) ");



                stmt.executeBatch();
                conn.commit();
                stmt.close();
                System.out.println("Табюлица " + tabl + " создана");
            } catch (SQLException e) {
                // Если произошла ошибка, откатываем изменения
                conn.rollback();
                System.out.println("Ошибюка создания таблици");

                e.printStackTrace();
            }

        } else {

            System.out.println("Табюлица ранее создана");

        }
    }

    public static void main(String[] args) throws Exception {





        class RunCode extends Thread {
        Object lock = new Object();
            String PytIn;
            String UserID;
            String Password;
            String DB2;
            public RunCode(String PytIn,String UserID,String Password,String DB2) {

                this.PytIn = PytIn;
                this.UserID = UserID;
                this.Password = Password;
                this.DB2 = DB2;
            }

            public void run() {

      synchronized(lock)
{



                try {
           //-----------------------
          String beg_dt = "";
          Connection conn = null;
            String tab = "ASU_POL.NEW_USER_ASU_TR";

            try {

            conn = new Connection_ASU_POL().getConnection(UserID,Password,DB2,"");


            File FL = new File(PytIn);
            BufferedReader InStream1 = new BufferedReader(
            new InputStreamReader(
            new FileInputStream(PytIn), "windows-1251"));
            InStream1.mark(0);
            String line1 = "";

            if (((line1 = InStream1.readLine()) != null)) {
            System.out.println(line1);
            beg_dt = line1.substring(6, 10) + "-" + line1.substring(3, 5) + "-" + line1.substring(0, 2);
            System.out.println(beg_dt);
            }

  
            if (Del(conn, tab, beg_dt)) {
            new Connection_ASU_POL().deleteTable(conn, tab);
            }

            createTable(conn, tab);
                
            int countProb=0;

            while(countProb<3){
            line1 = InStream1.readLine();
            if( line1.substring(0, 5).equals("-----")){countProb++;System.out.println(countProb);}
            System.out.println(line1);

            }

            while (((line1 = InStream1.readLine()) != null)) {

            System.out.println(line1);

            StringTokenizer line = new StringTokenizer(line1, "|");
            System.out.println("Размер массива" + line.countTokens());
            if (line.countTokens() > 30) {
            String[] linePOL = new String[line.countTokens()];

            int kline = 0;
            while (line.hasMoreTokens()) {
            linePOL[kline] = line.nextToken();
            System.out.println("Строка " + kline + ":  " + linePOL[kline]);
            kline++;
            }
            updateTable(conn, linePOL, beg_dt);
            } else {
            
            System.out.println("Пропуск строки  " + line1);
            
            
            }


            }



            conn.commit();
            } catch (Exception e) {
            e.printStackTrace();
            } finally {
            // Обязательно закрываем соединение с БД!
            new Connection_ASU_POL().closeConnection(conn,"");
            }






        





                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            }
        }





        Calendar time = Calendar.getInstance();
        String beg = dateFormatter3.format(time.getTime()).toString();

        
        String beg_dt = "";
        String[] PytInM = new String[6];

        String UserID = "db2admin";
        String Password = "11111111";
        String DB2="jdbc:db2:ASU_POL4";

        PytInM[0] = "D:\\ASU_POL_NSI\\HFK_POL.TXT";
        PytInM[1] = "D:\\ASU_POL_NSI\\H58_POL.TXT";
        PytInM[2] = "D:\\ASU_POL_NSI\\PHR_POL.TXT";
        PytInM[3] = "D:\\ASU_POL_NSI\\VRK1_POL.TXT";
        PytInM[4] = "D:\\ASU_POL_NSI\\VRK2_POL.TXT";//убрать | группы
        PytInM[5] = "D:\\ASU_POL_NSI\\VRK3_POL.TXT";
        String PytIn = "";
        for (int iPytIn = 0; iPytIn < PytInM.length; iPytIn++) {
            if (PytInM.length >= iPytIn + 1) {
                PytIn = PytInM[iPytIn];
                System.out.println("Файл первый " + iPytIn + " :" + PytIn);
            }

            
            Thread thread = new RunCode(PytIn,UserID,Password,DB2);
            thread.start();



//--------------
            /*

            Connection conn = null;
            String tab = "ASU_POL.NEW_USER_ASU_TR";

            try {

            conn = new Connection_ASU_POL().getConnection(UserID,Password,DB2);


            File FL = new File(PytIn);
            BufferedReader InStream1 = new BufferedReader(
            new InputStreamReader(
            new FileInputStream(PytIn), "windows-1251"));
            InStream1.mark(0);
            String line1 = "";

            if (((line1 = InStream1.readLine()) != null)) {
            System.out.println(line1);
            beg_dt = line1.substring(6, 10) + "-" + line1.substring(3, 5) + "-" + line1.substring(0, 2);
            System.out.println(beg_dt);
            }


            if (Del(conn, tab, beg_dt)) {
            new Connection_ASU_POL().deleteTable(conn, tab);
            }

            createTable(conn, tab);

            int countProb=0;

            while(countProb<3){
            line1 = InStream1.readLine();
            if( line1.substring(0, 5).equals("-----")){countProb++;System.out.println(countProb);}
            System.out.println(line1);

            }

            while (((line1 = InStream1.readLine()) != null)) {

            System.out.println(line1);

            StringTokenizer line = new StringTokenizer(line1, "|");
            System.out.println("Размер массива" + line.countTokens());
            if (line.countTokens() > 30) {
            String[] linePOL = new String[line.countTokens()];

            int kline = 0;
            while (line.hasMoreTokens()) {
            linePOL[kline] = line.nextToken();
            System.out.println("Строка " + kline + ":  " + linePOL[kline]);
            kline++;
            }
            updateTable(conn, linePOL, beg_dt);
            } else {
           // if (((line1 = InStream1.readLine()) != null)) {
            System.out.println("Пропуск строки  " + line1);
           // }
            
            }


            }



            conn.commit();
            } catch (Exception e) {
            e.printStackTrace();
            } finally {
            // Обязательно закрываем соединение с БД!
            new Connection_ASU_POL().closeConnection(conn);
            }

            */
            //-----------------------

        }

        Calendar time1 = Calendar.getInstance();
        String end = dateFormatter3.format(time1.getTime()).toString();

        System.out.println("Начало " + beg);
        System.out.println("Еонец " + end);
    }
}
