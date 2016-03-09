/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL_NEW;

import ASU_POL.Connection_ASU_POL;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author apopovkin
 */
public class ASU_POL_STRUK_TEXT_New {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        class RunCode implements Runnable {

            Object lock = new Object();
            String PytIn;
            String UserID;
            String Password;
            String DB2;
            Thread thread;

            public RunCode(String PytIn, String UserID, String Password, String DB2) {

                this.PytIn = PytIn;
                this.UserID = UserID;
                this.Password = Password;
                this.DB2 = DB2;


                thread = new Thread(this);
                thread.start();


            }

            public void run() {

                Connection conn = null;
                try {

                    conn = new Connection_ASU_POL().getConnection(UserID, Password, DB2, PytIn);

                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(
                                new InputStreamReader(
                                new FileInputStream(PytIn), "windows-1251"));
                        br.mark(0);
                        String line = "";
                        String beg_dt = "";
String data123="";
                        //определение строки c датой--------
                        boolean t_d = true;
                        while (((line = br.readLine()) != null) && t_d) {
                            t_d = !line.contains("Контрольная дата");
                            if (!t_d) {
                                data123 = line.substring(27, 31) + "-" + line.substring(24, 26) + "-" + line.substring(21, 23);
                                System.out.println(data123);
                            }
                        }


                        //поиск начало столбцов
                        t_d = true;
                        int[] Mass = new int[11];
                        while (((line = br.readLine()) != null) && t_d) {
                            if(line.contains("ИдОобъекта")){
                            t_d = !t_d;
                            Mass[0] = line.indexOf("ИдОобъекта");
                            Mass[1] = line.indexOf("Код объекта");
                            Mass[2] = line.indexOf("Тип объекта");
                            Mass[3] = line.indexOf("РасширеннИдОбъекта");
                            Mass[4] = line.indexOf("Статус (объект)");
                            Mass[5] = line.indexOf("Дата начала (объект)");
                            Mass[6] = line.indexOf("Дата завершения (объект)");
                            Mass[7] = line.indexOf("Статус (соединение)");
                            Mass[8] = line.indexOf("Дата начала (соединение)");
                            Mass[9] = line.indexOf("Дата завершения (соединение)");
                            Mass[10] = line.indexOf("Процентная ставка");
                            }
                        }

                       

boolean t=true;
  while (t&&((line = br.readLine()) != null) ) {
     
      
  if(line.length()>75&&!line.substring(0, 5).equals("-----")){
     t= !t;           
                String CODE_ROAD_ASU_TR = line.substring(Mass[1], Mass[2]).trim();
                System.out.println(CODE_ROAD_ASU_TR);
                int ID_ROAD_ASU_TR = Integer.parseInt(line.substring(Mass[3], (Mass[4])).trim());
                System.out.println(ID_ROAD_ASU_TR);
                String SN_ROAD = line.substring(3, Mass[1]).trim();
                System.out.println(SN_ROAD);
                String FN_ROAD = line.substring(3, Mass[1]).trim();
                System.out.println(FN_ROAD);
                int MY_ID_ROAD = 0;
                int MY_ID_ROAD_ASU_TR = 0;
                String STATUS = line.substring(Mass[4], Mass[5] ).trim();
                System.out.println(STATUS);
                String TYPE = line.substring(Mass[2], Mass[3]).trim();
                System.out.println(TYPE);
                String data1 = "9999.12.31";
                System.out.println("'" + data1 + "'");
                String data2 = "9999.12.31";
                System.out.println("'" + data2 + "'");
          
  

                SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
                String data123eData = "";
                    

                    Statement stmt1data123 = conn.createStatement();
                    ResultSet rs1data123 = stmt1data123.executeQuery(
                            " select max(DT_VVOD) as ss from ASU_POL.POLYGON_ROAD"
                            + " where   ID_ROAD_ASU_TR=" + ID_ROAD_ASU_TR + "  and  R3='yes' and "
                            + "  SN_ROAD='" + SN_ROAD + "'  ");
                    if (rs1data123.next()) {

                        if (rs1data123.getString("ss") != null) {


                            data123eData = rs1data123.getString("ss");
                        } else {
                            data123eData = dateFormatter1.format(new Date()).toString();
                        }


                    }
                    rs1data123.close();
                    stmt1data123.close();


                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(" select ID, CODE_ROAD_ASU_TR, ID_ROAD_ASU_TR, SN_ROAD, FN_ROAD, "
                            + " MY_ID_ROAD_ASU_TR, MY_ID_ROAD, DT_BIG, DT_END, STATUS, TYPE, "
                            + " PR_SERV, DT_VVOD, R3 "
                            + " from ASU_POL.POLYGON_ROAD  where "
                            + " ID_ROAD_ASU_TR=" + ID_ROAD_ASU_TR + "  and  R3='yes'"
                            + "  and SN_ROAD='" + SN_ROAD + "'   and "
                            + " DT_VVOD='" + data123eData + "'");
                    if (rs.next()) {
                        System.out.println(rs.getInt(6));
                        MY_ID_ROAD_ASU_TR = rs.getInt(6);
                        System.out.println(rs.getInt(7));
                        MY_ID_ROAD = rs.getInt(7);
                    } else {
                        
                        try{
                            String query = " insert into ASU_POL.POLYGON_ROAD ( CODE_ROAD_ASU_TR, ID_ROAD_ASU_TR, "
                                + " SN_ROAD "
                                + " ,FN_ROAD,MY_ID_ROAD_ASU_TR,MY_ID_ROAD, DT_BIG,STATUS,TYPE,DT_VVOD,R3 "
                                + ") "
                                + " values ( '" + CODE_ROAD_ASU_TR + "', " + ID_ROAD_ASU_TR + ",'" + SN_ROAD + "' "
                                + " ,'" + FN_ROAD + "' ," + MY_ID_ROAD_ASU_TR + ", " + MY_ID_ROAD + ",   "
                                + " '" + data1 + "', '" + STATUS + "', '" + TYPE + "','" + data123 + "','yes'"
                                + ")";
                        PreparedStatement myStmt = conn.prepareStatement(query);
                        myStmt.executeUpdate();
                        myStmt.close();

                        } catch (SQLException e) {
            // Если произошла ошибка, откатываем изменения
            try{
                conn.rollback();
                System.out.println("Ошибюка записи "+PytIn);
                System.out.println("Строка :{ " +ID_ROAD_ASU_TR+" }");
                e.printStackTrace();
            } catch (SQLException e1) {
               System.out.println("Ошибюка rollback");
               e1.printStackTrace(); 
            }
           
        }


                        System.out.println("Конец");


                    }


                    rs.close();
                    stmt.close();
                   


 }}
 conn.commit();








                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("Ошибка  I/O");
                        e.printStackTrace();
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                        } catch (IOException e) {
                            System.out.println("Ошибка  закрытия ф-ла");
                            e.printStackTrace();
                        }
                    }
                    
                    
                    conn.commit();


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // Обязательно закрываем соединение с БД!
                    new Connection_ASU_POL().closeConnection(conn, PytIn);
                }
            }
        }
















        String userID = "db2admin";
        String password = "11111111";
        String db2 = "jdbc:db2:ASU_POL";


        String[] PytInM = new String[6];
        PytInM[0] = "D:\\ASU_POL_NSI\\HFK_SP.TXT";
        PytInM[1] = "D:\\ASU_POL_NSI\\H58_SP.TXT";
        PytInM[2] = "D:\\ASU_POL_NSI\\PHR_SP.TXT";
        PytInM[3] = "D:\\ASU_POL_NSI\\VRK1_SP.TXT";
        PytInM[4] = "D:\\ASU_POL_NSI\\VRK2_SP.TXT";
        PytInM[5] = "D:\\ASU_POL_NSI\\VRK3_SP.TXT";

        for (String pyt : PytInM) {
            new RunCode(pyt, userID, password, db2);
        }
    }
}
