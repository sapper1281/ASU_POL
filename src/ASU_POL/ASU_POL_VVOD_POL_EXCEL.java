/*
 * EXCEL To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL;


import org.apache.poi.hssf.usermodel.*;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;

/**
 *
 * @author apopovkin
 */
public class ASU_POL_VVOD_POL_EXCEL {

    private static SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private static String cellN(HSSFCell Cell) throws Exception {

        try {

            String st = "";
            if (Cell != null && Cell.getCellStyle().getFillForegroundColor() == 27) {
                switch (Cell.getCellType()) {
                    default:
                        st = "не определен";
                        break;
                    case 0:
                        st = String.valueOf((int) Cell.getNumericCellValue());
                        break;
                    case 1:
                        st = Cell.getRichStringCellValue().getString();
                        break;

                }
            }

            return st;
        } catch (Exception e) {
            return "not";
        }
    }

    private static void updateTable(Connection conn, String[] param) throws SQLException {

        for (int i = 0; i < param.length; i++) {
            System.out.println(i + "  -  " + param[i]);
        }


    StringTokenizer fio=new StringTokenizer(param[5]," ");
/*int i=0;
while (i<param[5].length()){
    System.out.println("'"+param[5].charAt(i)+"'");
    i++;}*/
System.out.println("Размер массива"+fio.countTokens());
String[] fioPOL=new String[fio.countTokens()];

int kPOL=0;
while(fio.hasMoreTokens()){
   fioPOL[kPOL]=fio.nextToken();
   System.out.println("ФИО "+kPOL+":  "+fioPOL[kPOL]);
   kPOL++;
}
/*StringTokenizer fio1=new StringTokenizer(param[5]," ");
while(fio1.hasMoreTokens()){
System.out.println("hhhhhhh-1----"+fio.nextToken());
}*/

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


                    + Integer.parseInt(param[6]) + ", "
                    + Integer.parseInt(param[7]) + ", "
                    + "'" + param[8] + "', "
                    + "'" + param[9] + "', "
                    + "'" + param[10] + "', "
                    + "'" + param[11] + "', "
                    // +"'"+ param[14]+"' "
                    + "'" + param[12].substring(6, 10) + "-" + param[12].substring(3, 5)
                    + "-" + param[12].substring(0, 2) + "'"
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

    private static boolean Del(Connection conn, String tabl,String beg_dt) throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tabl + " ");
            Calendar time = Calendar.getInstance();

           // String PER1 = dateFormatter1.format(time.getTime()).toString();
            String PER = "not";
            // Достаем полученные из таблицы данны
            while (rs.next()) {
                PER = rs.getString("PER");
               }

            rs.close();
            stmt.close();

            return !PER.equals(beg_dt) && !PER.equals("not");
        } catch (SQLException e) {
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
                        + " RPps              CHARACTER(20), "
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
                        + " ID_JOB "
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

 Calendar time = Calendar.getInstance();
 String beg = dateFormatter3.format(time.getTime()).toString();
        /* try {
        String consoleEnc = System.getProperty("console.encoding", "Cp866");
        System.setOut(new CodepagePrintStream(System.out, consoleEnc));
        System.setErr(new CodepagePrintStream(System.err, consoleEnc));
        } catch (UnsupportedEncodingException e) {
        System.out.println("Unable to setup console codepage: " + e);
        }*/
        //  "D:\\Рабочая\\АСУ Пользователь\\1\\1.XLS"

//--------------------

        String beg_dt = "";

String UserID = "db2admin";
        String Password = "11111111";
        String DB2="jdbc:db2:ASU_POL4";


        
        String[] PytInM = new String[1];
        PytInM[0] = "D:\\Рабочая\\АСУ Пользователь\\1\\PHR_POL_20110124_el_tab.XLS";
        //PytInM[0] = "D:\\Рабочая\\АСУ Пользователь\\1\\1.XLS";
        //PytInM[1] = "D:\\Рабочая\\АСУ Пользователь\\1\\2.XLS";
       
        String PytIn = "";
        for (int iPytIn = 0; iPytIn < PytInM.length; iPytIn++) {
            if (PytInM.length >= iPytIn + 1) {
                PytIn = PytInM[iPytIn];
                System.out.println("Файл первый " + iPytIn + " :" + PytIn);
            }

//-----------------------

            Connection conn = null;
            String tab = "ASU_POL.NEW_USER_ASU_TR";

            try {




                File FL = new File(PytIn);
                InputStream inputStream = new FileInputStream(FL);
                HSSFWorkbook wb = new HSSFWorkbook(inputStream);
                HSSFSheet sheet = wb.getSheetAt(0);

                conn = new Connection_ASU_POL().getConnection(UserID,Password,DB2);
                HSSFRow rowN1=sheet.getRow(0);
                HSSFCell cellN1=rowN1.getCell(0);

                 switch (cellN1.getCellType()) {
                    default:
                        beg_dt = "не определен";
                        break;
                    case 0:
                        beg_dt =  dateFormatter1.format(cellN1.getDateCellValue()).toString();
                        break;
                    case 1:
                        beg_dt = cellN1.getRichStringCellValue().getString();
                        break;

                }


                
               
               

                if (Del(conn, tab,beg_dt)) {
                    new Connection_ASU_POL().deleteTable(conn, tab);
                }

                createTable(conn, tab);




                for (Iterator i1 = sheet.rowIterator(); i1.hasNext();) {
                    HSSFRow rowN2 = (HSSFRow) i1.next();


                    HSSFCell cellN_PODR = rowN2.getCell(0);//начиная со столбца (+1=столбц)
                    HSSFCell cellN_TAB_NUM = rowN2.getCell(2);

                    HSSFCell cellN_SN_SHTAT_JOB = rowN2.getCell(3);
                    HSSFCell cellN_FN_SHTAT_JOB = rowN2.getCell(4);
                    HSSFCell cellN_KAT = rowN2.getCell(5);
                    // HSSFCell cellN_DT_ROZH = rowN2.getCell( 6);
                    // HSSFCell cellN_DT_JOB_IN = rowN2.getCell( 7);
                    HSSFCell cellN_FIRST_NAME = rowN2.getCell(10);
                    // HSSFCell cellN_MIDDLE_NAME = rowN2.getCell((short) 8);
                    // HSSFCell cellN_LAST_NAME = rowN2.getCell((short) 9);
                    HSSFCell cellN_ID_JOB = rowN2.getCell(13);
                    HSSFCell cellN_ID_ROAD_ASU_TR = rowN2.getCell(14);
                    HSSFCell cellN_RPPS = rowN2.getCell(25);
                    HSSFCell cellN_CODE_ROAD_ASU_TR = rowN2.getCell(27);
                    HSSFCell cellN_RAZD_PER = rowN2.getCell(31);
                    HSSFCell cellN_PFR = rowN2.getCell(32);
                    HSSFCell cellN_PER = rowN2.getCell(37);
                    String[] st = new String[13];

                    if (cellN_PODR.getCellStyle().getFillForegroundColor() == 27) {

                        st[0] = cellN(cellN_PODR);
                        st[1] = cellN(cellN_TAB_NUM);
                        st[2] = cellN(cellN_SN_SHTAT_JOB);
                        st[3] = cellN(cellN_FN_SHTAT_JOB);
                        st[4] = cellN(cellN_KAT);
                        //  st[5] = cellN(cellN_DT_ROZH);
                        //  st[6] = cellN(cellN_DT_JOB_IN);
                        st[5] = cellN(cellN_FIRST_NAME);
                        st[6] = cellN(cellN_ID_JOB);
                        st[7] = cellN(cellN_ID_ROAD_ASU_TR);
                        st[8] = cellN(cellN_RPPS);
                        st[9] = cellN(cellN_CODE_ROAD_ASU_TR);
                        st[10] = cellN(cellN_RAZD_PER);
                        st[11] = cellN(cellN_PFR);
                        st[12] = cellN(cellN_PER);



                        for (int i = 0; i < st.length; i++) {
                            System.out.println(st[i]);
                     }



                        updateTable(conn, st);

                    }
                }


                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Обязательно закрываем соединение с БД!
                new Connection_ASU_POL().closeConnection(conn);
            }
            //----------------
        }
        //----------------
        Calendar time1 = Calendar.getInstance();
        String end = dateFormatter3.format(time1.getTime()).toString();

       System.out.println("Начало "+beg);
       System.out.println("Еонец "+end);
    }
}
