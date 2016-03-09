/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package notall;

/**
 *
 * @author apopovkin
 */

import org.apache.poi.hssf.usermodel.*;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;
import ASU_POL.Connection_ASU_POL;

/**
 *
 * @author apopovkin
 */
public class insert_not_r3 {

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
/*
    private static void updateTable(Connection conn, String[] param) throws SQLException {

        for (int i = 0; i < param.length; i++) {
            System.out.println(i + "  -  " + param[i]);
        }


    StringTokenizer fio=new StringTokenizer(param[5]," ");

System.out.println("Размер массива"+fio.countTokens());
String[] fioPOL=new String[fio.countTokens()];

int kPOL=0;
while(fio.hasMoreTokens()){
   fioPOL[kPOL]=fio.nextToken();
   System.out.println("ФИО "+kPOL+":  "+fioPOL[kPOL]);
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


   */

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
       // PytInM[0] = "D:\\Рабочая\\АСУ Пользователь\\1\\PHR_POL_20110124_el_tab.XLS";
        PytInM[0] = "C:\\Documents and Settings\\apopovkin\\Рабочий стол\\Нина\\пользователи для закачки_1_new.xls";
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






            /*    if (Del(conn, tab,beg_dt)) {
                    new Connection_ASU_POL().deleteTable(conn, tab);
                }
*/



                for (Iterator i1 = sheet.rowIterator(); i1.hasNext();) {
                    HSSFRow rowN2 = (HSSFRow) i1.next();

 System.out.println(rowN2.getCell(0)+" "+rowN2.getCell(1)+" "+rowN2.getCell(2)+" "+rowN2.getCell(3));
               
 
 
  PreparedStatement pstmt = conn.prepareStatement(
            
 "insert into ASU_POL.MAIN_USER_ALL (ID_ROAD_ASU_TR, TAB_NUM, ID_JOB, BLOCK_USER, PFR,"
                    + " LAST_NAME, FIRST_NAME, MIDDLE_NAME, TEL, IP, E_MAIL, "
                    + " DT_BIG, PRIZ, ROAD, SN_JOB, R3_YES_OR_NIOT ) values "
                    + "( "
                    //+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                    + "-32, "
                    + "-1, "
                    + "-1, "
                    + "0, "
                    + "'not', "

                    + "'" +rowN2.getCell(6) + "', "
                    + "'" + rowN2.getCell(7) + "', "
                    + "'" + rowN2.getCell(8) + "', "
+ "'" + rowN2.getCell(9) + "', "
         + "'" + rowN2.getCell(10) + "', "
         + "'-', "
+ "'2011-06-09', "
                    +  "-1, "
                    + "'" + rowN2.getCell(15) + "', "
                      + "'" + rowN2.getCell(16) + "', "
                   + "'not' "

                    + ") ");

   pstmt.executeUpdate();
   pstmt.close();
   
conn.commit();

                   
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
