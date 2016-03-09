/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL;

import java.io.*;
import java.io.File;
import java.sql.*;
import java.util.*;
import java.text.*;

/**
 *Begin:DB errorCOM.ibm.db2.jdbc.DB2Exception: [IBM][CLI Driver] SQL30081N  A communication error has been detected.  Communication protocol being used: "TCP/IP".  Communication API being used: "SOCKETS".  Location where the error was detected: "10.58.1.242".  Communication function detecting the error: "connect".  Protocol specific error code(s): "10060", "*", "*".  SQLSTATE=08001

 * @author  apopovkin
 */
public class ASU_POL_STRUK_TEXT {

    public static int c;
    public static String Inarr[];

    /** Creates a new instance of ASU_POL_STRUK */
    public ASU_POL_STRUK_TEXT() {
    }

    /**
     * @param args the command line arguments
     */
    public static void Rec_DB(int j_db, int j_db1, int j_db2, int MY_ID_ROAD_ASU_TR, int MY_ID_ROAD,
            int Mass75, int Mass90, int Mass104, int Mass127, int Mass145, int Mass168, int Mass195, String date,
            String Driver, String UserID, String Password, String BData) {
        System.out.print("Begin:");
        boolean t = true;
        int MY_ID_ROAD1 = 0;
        int MY_ID_ROAD_ASU_TR1 = 0;
        PrintStream out = System.out;
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        int j = j_db;
        int k = 0;
        if (Inarr[j].length() < 75) {
            j++;
        }

        try {
            Class.forName(Driver);
            Connection myconnection1 = DriverManager.getConnection(BData, UserID, Password);
            Calendar time = Calendar.getInstance();
            System.out.print("DB conect open insert ; time=" + dateFormatter3.format(time.getTime()).toString() + "\n");


            do {

                if ((Inarr[j].length() > 75) && ("-  ".equals(Inarr[j].substring(j_db1, j_db2 + 2)))) {
                    System.out.println("'" + Inarr[j].substring(j_db1, j_db2).trim() + "'");//вложенность
                    System.out.println("'" + Inarr[j].substring(j_db2, Mass75).trim() + "'");//название
                    System.out.println("'" + Inarr[j].substring(Mass75, Mass90).trim() + "'");//код
                    System.out.println("'" + Inarr[j].substring(Mass90, Mass104).trim() + "'");//тип
                    System.out.println("'" + Inarr[j].substring(Mass104, Mass127).trim() + "'");//ид
                    System.out.println("'" + Inarr[j].substring(Mass127, Mass145).trim() + "'");//статус
                    System.out.println("'" + Inarr[j].substring(Mass145, Mass168).trim() + "'");//дт нач
                    System.out.println("'" + Mass168 + "-----" + Mass195 + "'");//дт кон
                    System.out.println("'" + Inarr[j].substring(Mass168, Mass195).trim() + "'");//дт кон
                    System.out.println("'----------------------------------------'");
                    System.out.println(Inarr[j]);

                    String CODE_ROAD_ASU_TR = Inarr[j].substring(Mass75, Mass90).trim();
                    int ID_ROAD_ASU_TR = Integer.parseInt(Inarr[j].substring(Mass104, Mass127).trim());
                    String SN_ROAD = Inarr[j].substring(j_db2, Mass75).trim();
                    String FN_ROAD = Inarr[j].substring(j_db2, Mass75).trim();
                    String STATUS = Inarr[j].substring(Mass127, Mass145).trim();
                    String TYPE = Inarr[j].substring(Mass90, Mass104).trim();

                    String dday_p1 = Inarr[j].substring(Mass145, Mass168).trim().substring(0, 2);
                    String mmonth_p1 = Inarr[j].substring(Mass145, Mass168).trim().substring(3, 5);
                    String yyear_p1 = Inarr[j].substring(Mass145, Mass168).trim().substring(6, 10);
                    String data1 = yyear_p1 + "-" + mmonth_p1 + "-" + dday_p1;
                    System.out.println("'" + data1 + "'");

                    String dday_p2 = Inarr[j].substring(Mass168, Mass195).trim().substring(0, 2);
                    String mmonth_p2 = Inarr[j].substring(Mass168, Mass195).trim().substring(3, 5);
                    String yyear_p2 = Inarr[j].substring(Mass168, Mass195).trim().substring(6, 10);
                    String data2 = yyear_p2 + "-" + mmonth_p2 + "-" + dday_p2;
                    System.out.println("'" + data2 + "'");


                    System.out.println("1");

                    Statement stmt = myconnection1.createStatement();
                    ResultSet rs = stmt.executeQuery(" select ID,DT_END,DT_VVOD "
                            + " from ASU_POL.POLYGON_ROAD  where "
                            + " ID_ROAD_ASU_TR=" + ID_ROAD_ASU_TR + " "
                            + " and MY_ID_ROAD_ASU_TR=" + MY_ID_ROAD_ASU_TR + " "
                            + " and SN_ROAD='" + SN_ROAD + "'  "
                            + " and STATUS='" + STATUS + "' and TYPE='" + TYPE + "' and R3='yes' ");

                    if (!rs.next()) {
                        System.out.println("2");
                        /* String query = "insert into ASU_POL.POLYGON_ROAD_1DEL ( CODE_ROAD_ASU_TR, ID_ROAD_ASU_TR, SN_ROAD, FN_ROAD,MY_ID_ROAD_ASU_TR,MY_ID_ROAD, DT_BIG,STATUS,TYPE,DT_VVOD,R3 ) values ( ?, ?, ?, ?,?, ?, ?, ?, ?,'"+date+"','yes')";
                        PreparedStatement myStmt = myconnection1.prepareStatement(query);
                        myStmt.setString(1,CODE_ROAD_ASU_TR);
                        myStmt.setInt(2,ID_ROAD_ASU_TR);
                        myStmt.setString(3,SN_ROAD );
                        myStmt.setString(4,FN_ROAD);
                        myStmt.setInt(5,MY_ID_ROAD_ASU_TR);
                        myStmt.setInt(6,MY_ID_ROAD);
                        myStmt.setString(7,data1);
                        myStmt.setString(8,STATUS);
                        myStmt.setString(9,TYPE);
                        myStmt.executeUpdate();*/



                        String query = " insert into ASU_POL.POLYGON_ROAD( CODE_ROAD_ASU_TR, ID_ROAD_ASU_TR, "
                                + " SN_ROAD, FN_ROAD,MY_ID_ROAD_ASU_TR,MY_ID_ROAD, DT_BIG,STATUS,TYPE,DT_VVOD,R3 ) "
                                + " values ( '" + CODE_ROAD_ASU_TR + "', " + ID_ROAD_ASU_TR + ",'" + SN_ROAD + "', '" + FN_ROAD + "'," + MY_ID_ROAD_ASU_TR + ", " + MY_ID_ROAD + ",   "
                                + " '" + data1 + "', '" + STATUS + "', '" + TYPE + "','" + date + "','yes')";
                        PreparedStatement myStmt = myconnection1.prepareStatement(query);
                        myStmt.executeUpdate();
                        myStmt.close();
                        System.out.println("3");

                    } else {
                        System.out.println("4");
                        if ((rs.getString("DT_END") == null) || (!rs.getString("DT_END").equals(data2)) || (rs.getString("DT_VVOD") == null) || (!rs.getString("DT_VVOD").equals(date))) {
                            System.out.println("4_1");
                            PreparedStatement stmt_up = myconnection1.prepareStatement(
                                    " UPDATE ASU_POL.POLYGON_ROAD SET  DT_END = '" + data2 + "', "
                                    + " DT_VVOD = '" + date + "' "
                                    + " where "
                                    + " ID_ROAD_ASU_TR=" + ID_ROAD_ASU_TR + " "
                                    + " and SN_ROAD='" + SN_ROAD + "'  "
                                    + " and MY_ID_ROAD_ASU_TR=" + MY_ID_ROAD_ASU_TR + " "
                                    + " and STATUS='" + STATUS + "' and TYPE='" + TYPE + "' and R3='yes'");
                            stmt_up.executeUpdate();
                            stmt_up.close();
                        }
                        System.out.println("5");
                    }

                    Statement stmt1 = myconnection1.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(" select ID "
                            + " from ASU_POL.POLYGON_ROAD  where "
                            + " ID_ROAD_ASU_TR=" + ID_ROAD_ASU_TR + " "
                            + " and MY_ID_ROAD_ASU_TR=" + MY_ID_ROAD_ASU_TR + " "
                            + " and SN_ROAD='" + SN_ROAD + "'  "
                            + " and STATUS='" + STATUS + "' and TYPE='" + TYPE + "' and R3='yes' ");
                    while (rs1.next()) {
                        MY_ID_ROAD1 = rs1.getInt("ID");
                    }
                    MY_ID_ROAD_ASU_TR1 = ID_ROAD_ASU_TR;

                    rs.close();
                    rs1.close();

                    if (((j + 1 < c) && !((Inarr[j + 1].length() > 75) && ("-  ".equals(Inarr[j + 1].substring(j_db1, j_db2 + 2)))))
                            && (!("-".equals(Inarr[j].substring(j_db1 - 1, j_db2 - 1))))) {
                        Rec_DB(j + 1, j_db1 + 2, j_db2 + 2, MY_ID_ROAD_ASU_TR1, MY_ID_ROAD1,
                                Mass75, Mass90, Mass104, Mass127, Mass145, Mass168, Mass195, date, Driver, UserID, Password, BData);
                    }


                }
                k = j;
                if ((j + 1 < c) && (Inarr[j + 1].length() < 75)) {
                    j = j + 2;
                } else {
                    j = j + 1;
                }
                out.print("DB error" + k + "\n");
                t = true;
                if (j_db1 - 2 > 0) {
                    t = !("-".equals(Inarr[k].substring(j_db1 - 2, j_db2 - 2)));
                }
            } while (j < c && !("-".equals(Inarr[k].substring(j_db1 - 1, j_db2 - 1))) && t);

            myconnection1.close();
        } catch (Exception e) {
            out.print("DB error" + e + "\n");
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {
        /* System.out.println(args[0]);
        System.out.println(args[1]);
        System.out.println(args[2]);
        System.out.println(args[3]);
        System.out.println(args[4]);
        System.out.println(args[5]);
        System.out.println(args[6]);
        System.out.println(args.length);
        String beg_dt = "";
        String PytIn ="";
        String Driver ="";
        String UserID="";
        String Password="";
        String BData="";
        String eData="";*/

        // String beg_dt = "24.01.2011";

        String Driver = "COM.ibm.db2.jdbc.app.DB2Driver";
        String UserID = "db2admin";
        String Password = "11111111";
        String BData = "jdbc:db2:ASU_POL4";
        // String eData="07.12.2010";

        String[] PytInM = new String[6];


       /* PytInM[0] = "D:\\ASU_POL_NSI\\HFK_POL.TXT";
        PytInM[1] = "D:\\ASU_POL_NSI\\H58_POL.TXT";
        PytInM[2] = "D:\\ASU_POL_NSI\\PHR_POL.TXT";
        PytInM[3] = "D:\\ASU_POL_NSI\\HRK200_POL.TXT";
        PytInM[4] = "D:\\ASU_POL_NSI\\HRK300_POL.TXT";
        PytInM[5] = "D:\\ASU_POL_NSI\\HRK400_POL.TXT";*/
        
        
        PytInM[0] = "D:\\ASU_POL_NSI\\H58_SP_20160125.TXT";
        PytInM[1] = "D:\\ASU_POL_NSI\\HFK_SP_20160125.TXT";
        PytInM[2] = "D:\\ASU_POL_NSI\\PHR_SP_20160125.TXT";
        PytInM[3] = "D:\\ASU_POL_NSI\\HRK(200)_SP_20160125.TXT";
        PytInM[4] = "D:\\ASU_POL_NSI\\HRK(300)_SP_20160125.TXT";
        PytInM[5] = "D:\\ASU_POL_NSI\\HRK(400)_SP_20160125.TXT";
        String PytIn = "";

        for (int iPytIn = 0; iPytIn < PytInM.length; iPytIn++) {
            if (PytInM.length >= iPytIn + 1) {
                PytIn = PytInM[iPytIn];
                System.out.println("Файл первый " + iPytIn + " :" + PytIn);
            }



            /*try
            {
            String consoleEnc = System.getProperty("console.encoding","Cp866");
            System.setOut(new CodepagePrintStream(System.out,consoleEnc) );
            System.setErr(new CodepagePrintStream(System.err,consoleEnc) );
            }
            catch(UnsupportedEncodingException e)
            {
            System.out.println("Unable to setup console codepage: " + e);
            }*/

            PrintStream out = System.out;
            SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            Calendar time1 = Calendar.getInstance();
            out.print("Begin: " + dateFormatter3.format(time1.getTime()).toString() + "\n");

            /* try {
            if (args.length>=1){beg_dt=args[0];} else beg_dt = "current date - 1 day";
            if (args.length>=2){PytIn=args[1];}
            if (args.length>=3){Driver=args[2];}
            if (args.length>=4){UserID=args[3];}
            if (args.length>=5){Password=args[4];}
            if (args.length>=6){BData=args[5];}
            if (args.length>=6){eData=args[6];}//дата послендего обнавления
            }
            catch (Exception e){System.out.print("23"+e);System.exit(1);}

            if (beg_dt.equals("")||PytIn.equals("")||PytIn.equals("")||Driver.equals("")||Password.equals("")||BData.equals("")||eData.equals("")){
            System.out.print("Неверно заданны параметры. Формат параметров gen_stat db_alias user pass otchname year month \n По умолчанию устанавливается текущий год и предыдущий месяц");
            System.exit(0);
            }*/

            /*String dday_p = beg_dt.substring(0,2);
            int dday1_p=Integer.parseInt(dday_p);
            String mmonth_p = beg_dt.substring(3,5);
            int mmonth1_p=Integer.parseInt(mmonth_p)-1;
            String yyear_p = beg_dt.substring(6,10);
            int yyear1_p=Integer.parseInt(yyear_p);

            String data123=yyear_p+"-"+mmonth_p+"-"+dday_p;*/

            String data123 = "";

            String data123eData = "";
            /*   String dday_peData = eData.substring(0,2);
            int dday1_peData=Integer.parseInt(dday_peData);
            String mmonth_peData = eData.substring(3,5);
            int mmonth1_peData=Integer.parseInt(mmonth_peData)-1;
            String yyear_peData = eData.substring(6,10);
            int yyear1_peData=Integer.parseInt(yyear_peData);

            String data123eData=yyear_peData+"-"+mmonth_peData+"-"+dday_peData;*/


            System.out.println("File: " + PytIn + "; ");
            File fIn = new File(PytIn);
            String str = "";
            if (fIn.isFile()) {
                System.out.println("ok_In: " + PytIn + "; ");
                //Чтение файла содержащий имена Input файлов и помещение в массив Inarr
                DataInputStream InStream;
                InStream = new DataInputStream(new BufferedInputStream(new FileInputStream(PytIn))); //читаем файл в поток
                String line = "";
                InStream.mark(0);
                c = 0;

                while ((line = InStream.readLine()/*readLine()*/) != null) {
                    c++;
                }
                System.out.println(c);
                Inarr = new String[c];

                BufferedReader InStream1 = new BufferedReader(
                        new InputStreamReader(
                        new FileInputStream(PytIn), "windows-1251"));
                InStream1.mark(0);
                int j1 = 0;
                String line1 = "";
                while (((line1 = InStream1.readLine()) != null) && (j1 < c)) {
                    Inarr[j1] = line1;
                    j1++;
                }
                System.out.println(c);

                //определение строки--------
                int j2 = 1;
                boolean t = true;
                while (t) {
                    if (Inarr[j2].length() > 50) {

                        t = !Inarr[j2].substring(0, 5).equals("-----");
                        System.out.println(j2 + "=     " + Inarr[j2].substring(0, 5) + "  " + t);
                    }
                    j2++;
                    //t=Inarr[j2].substring(0,5).equals("-------------------")
                }
                j2 = j2 - 2;





                //определение строки c датой--------
                int j2_1 = 1;
                boolean t_d = true;
                while (t_d) {
                    if (Inarr[j2_1].length() > 16) {
                        //Контрольная дата     24.01.2011


                        t_d = !Inarr[j2_1].substring(0, 16).equals("Контрольная дата");
                        System.out.println(Inarr[j2_1].substring(0, 16));
                        if (!t_d) {

                            String dday_p = Inarr[j2_1].substring(21, 23);
                            String mmonth_p = Inarr[j2_1].substring(24, 26);
                            String yyear_p = Inarr[j2_1].substring(27, 31);

                            data123 = yyear_p + "-" + mmonth_p + "-" + dday_p;

                            System.out.println(j2_1 + "=     " + data123 + "  " + t_d);
                        }
                    }
                    j2_1++;
                }





                //поиск начало столбцов
                int k = 0;
                boolean t1 = false;
                int j_1 = 0;
                while (j_1 < Inarr[j2].length()) {
                    if (!String.valueOf(Inarr[j2].charAt(j_1)).equals(" ")) {
                        k++;
                        System.out.println(String.valueOf(Inarr[j2].charAt(j_1)));
                        while (j_1 < Inarr[j2].length() && (!String.valueOf(Inarr[j2].charAt(j_1)).equals(" "))) {
                            j_1++;
                            if (j_1 < Inarr[j2].length() && (String.valueOf(Inarr[j2].charAt(j_1)).equals(" "))) {
                                j_1++;
                            }
                        }

                    } else {
                        j_1++;
                    }
                }
                System.out.println(k);





                int[] Mass = new int[k - 1];
                int k1 = -1;
                int k2 = -1;
                int k3 = 0;
                j_1 = 0;
                while (j_1 < Inarr[j2].length()) {

                    if (!String.valueOf(Inarr[j2].charAt(j_1)).equals(" ")) {
                        k2++;
                        k1 = j_1;
                        while (j_1 < Inarr[j2].length() && (!String.valueOf(Inarr[j2].charAt(j_1)).equals(" "))) {
                            j_1++;
                            if (j_1 < Inarr[j2].length() && (String.valueOf(Inarr[j2].charAt(j_1)).equals(" "))) {
                                j_1++;
                            }
                        }
                    } else {
                        while (j_1 < Inarr[j2].length() && (String.valueOf(Inarr[j2].charAt(j_1)).equals(" "))) {
                            j_1++;
                        }
                        System.out.println("22222  " + (k1) + "---" + (j_1));
                        if (k1 != -1) {
                            Mass[k3] = k1;
                            k3++;
                            System.out.println("'" + Inarr[j2].substring(k1, j_1) + "'");
                        }
                    }
                }

                for (j_1 = 0; j_1 < Mass.length; j_1++) {
                    System.out.println(Mass[j_1]);
                }

                j2 = j2 + 2;


                String CODE_ROAD_ASU_TR = Inarr[j2].substring(Mass[1], Mass[2]).trim();
                int ID_ROAD_ASU_TR = Integer.parseInt(Inarr[j2].substring(Mass[3], (Mass[4] - 3)).trim());
                String SN_ROAD = Inarr[j2].substring(3, Mass[1]).trim();
                String FN_ROAD = Inarr[j2].substring(3, Mass[1]).trim();
                int MY_ID_ROAD = 0;
                int MY_ID_ROAD_ASU_TR = 0;
                String STATUS = Inarr[j2].substring(Mass[4] - 3, Mass[5] - 3).trim();
                String TYPE = Inarr[j2].substring(Mass[2], Mass[3]).trim();

                //Mass[5]-3,Mass[6]-3,Mass[7]-3
                // String dt=Inarr[j].substring(142,165).trim();
                String dday_p1 = Inarr[j2].substring(Mass[5] - 3, Mass[6] - 3).trim().substring(0, 2);
                String mmonth_p1 = Inarr[j2].substring(Mass[5] - 3, Mass[6] - 3).trim().substring(3, 5);
                String yyear_p1 = Inarr[j2].substring(Mass[5] - 3, Mass[6] - 3).trim().substring(6, 10);
                String data1 = yyear_p1 + "-" + mmonth_p1 + "-" + dday_p1;
                System.out.println("'" + data1 + "'");

                String dday_p2 = Inarr[j2].substring(Mass[6] - 3, Mass[7] - 3).trim().substring(0, 2);
                String mmonth_p2 = Inarr[j2].substring(Mass[6] - 3, Mass[7] - 3).trim().substring(3, 5);
                String yyear_p2 = Inarr[j2].substring(Mass[6] - 3, Mass[7] - 3).trim().substring(6, 10);
                String data2 = yyear_p2 + "-" + mmonth_p2 + "-" + dday_p2;
                System.out.println("'" + data2 + "'");













                if ((Inarr[j2].length() > 75) && ("-  ".equals(Inarr[j2].substring(1, 4)))) {
                    System.out.println(CODE_ROAD_ASU_TR + "  " + ID_ROAD_ASU_TR + "  " + SN_ROAD + "  " + FN_ROAD);
                }


                try {
                    Class.forName(Driver);
                    Connection myconnection1 = DriverManager.getConnection(BData, UserID, Password);
                    Calendar time = Calendar.getInstance();
                    System.out.print("DB conect open insert ; time=" + dateFormatter3.format(time.getTime()).toString() + "\n");



                    Statement stmt1data123 = myconnection1.createStatement();
                    ResultSet rs1data123 = stmt1data123.executeQuery(
                            " select max(DT_VVOD) as ss from ASU_POL.POLYGON_ROAD"
                            + " where   ID_ROAD_ASU_TR=" + ID_ROAD_ASU_TR + "  and  R3='yes' and "
                            + "  SN_ROAD='" + SN_ROAD + "'  ");
                    if (rs1data123.next()) {

                        if (rs1data123.getString("ss") != null) {


                            data123eData = rs1data123.getString("ss");
                        } else {
                            data123eData = dateFormatter1.format(time.getTime()).toString();
                        }


                    }
                    rs1data123.close();
                    stmt1data123.close();


                    Statement stmt = myconnection1.createStatement();
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
                        System.out.println(CODE_ROAD_ASU_TR);
                        /*String query = "insert into ASU_POL.POLYGON_ROAD_1DEL ( CODE_ROAD_ASU_TR, ID_ROAD_ASU_TR, SN_ROAD, FN_ROAD,MY_ID_ROAD_ASU_TR,MY_ID_ROAD, DT_BIG,STATUS,TYPE,DT_VVOD,R3 ) values ( ?, ?, ?, ?,?, ?, ?, ?, ?,'"+data123+"','yes')";
                        PreparedStatement myStmt = myconnection1.prepareStatement(query);
                        myStmt.setString(1,CODE_ROAD_ASU_TR);
                        myStmt.setInt(2,ID_ROAD_ASU_TR);
                        myStmt.setString(3,SN_ROAD );
                        myStmt.setString(4,FN_ROAD);
                        myStmt.setInt(5,MY_ID_ROAD_ASU_TR);
                        myStmt.setInt(6,MY_ID_ROAD);
                        myStmt.setString(7,data1);
                        myStmt.setString(8,STATUS);
                        myStmt.setString(9,TYPE);
                        myStmt.executeUpdate();
                        myStmt.close();*/
                        String query = " insert into ASU_POL.POLYGON_ROAD ( CODE_ROAD_ASU_TR, ID_ROAD_ASU_TR, "
                                + " SN_ROAD "
                                + " ,FN_ROAD,MY_ID_ROAD_ASU_TR,MY_ID_ROAD, DT_BIG,STATUS,TYPE,DT_VVOD,R3 "
                                + ") "
                                + " values ( '" + CODE_ROAD_ASU_TR + "', " + ID_ROAD_ASU_TR + ",'" + SN_ROAD + "' "
                                + " ,'" + FN_ROAD + "' ," + MY_ID_ROAD_ASU_TR + ", " + MY_ID_ROAD + ",   "
                                + " '" + data1 + "', '" + STATUS + "', '" + TYPE + "','" + data123 + "','yes'"
                                + ")";
                        PreparedStatement myStmt = myconnection1.prepareStatement(query);
                        /*myStmt.setString(1,CODE_ROAD_ASU_TR);
                        myStmt.setInt(2,ID_ROAD_ASU_TR);
                        myStmt.setString(3,SN_ROAD );
                        myStmt.setString(4,FN_ROAD);
                        myStmt.setInt(5,MY_ID_ROAD_ASU_TR);
                        myStmt.setInt(6,MY_ID_ROAD);
                        myStmt.setString(7,data1);
                        myStmt.setString(8,STATUS);
                        myStmt.setString(9,TYPE);*/
                        // myStmt.setString(1,SN_ROAD );
                        // myStmt.setString(2,FN_ROAD);
                        myStmt.executeUpdate();
                        myStmt.close();



                        System.out.println("Конец");


                    }


                    rs.close();
                    stmt.close();
                    myconnection1.close();


                } catch (Exception e) {
                    out.print("DB error" + e + "\n");
                    System.exit(1);
                }



                //  Rec_DB(j2,1,2,nach_1,nach_2,Mass[1],Mass[2],Mass[3],Mass[4]-3,Mass[5]-3,Mass[6]-3,Mass[7]-3,data123 );

                Rec_DB(j2, 1, 2, MY_ID_ROAD_ASU_TR, MY_ID_ROAD, Mass[1], Mass[2], Mass[3], Mass[4] - 3, Mass[5] - 3, Mass[6] - 3, Mass[7] - 3, data123, Driver, UserID, Password, BData);



            }








        }
    }
}
