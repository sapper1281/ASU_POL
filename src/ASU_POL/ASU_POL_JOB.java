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
import java.io.File;
import java.sql.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author  apopovkin
 */
public class ASU_POL_JOB {

    public static int c;
    public static String Inarr[];

    public static void Rec_DB(int j_db) {
        System.out.print("Begin:");
        PrintStream out = System.out;
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar DT_NOW_1 = Calendar.getInstance();
        String date = dateFormatter1.format(DT_NOW_1.getTime()).toString();

        String Driver = "COM.ibm.db2.jdbc.app.DB2Driver";
        String UserID = "db2admin";
        String Password = "11111111";

        try {
            Class.forName(Driver);
            Connection myconnection1 = DriverManager.getConnection("jdbc:db2:ASU_POL", UserID, Password);
            Calendar time = Calendar.getInstance();
            System.out.print("DB conect open insert job ; time=" + dateFormatter3.format(time.getTime()).toString() + "\n");

            System.out.println("Поиск максимального значения таблици POLYGON_JOB");
            Statement stmt_id = myconnection1.createStatement();
            ResultSet rs_id = stmt_id.executeQuery(" select max(ID) as f " +
                    " from ASU_POL.POLYGON_JOB ");

            if (rs_id.next()) {
                int f = rs_id.getInt("f") + 1;
                System.out.println("Максимального значения+1 таблици POLYGON_JOB--" + f);
                PreparedStatement myStmt_id = myconnection1.prepareStatement(
                        " ALTER TABLE ASU_POL.POLYGON_JOB " +
                        " ALTER COLUMN ID " +
                        " RESTART WITH " + f + " ");
                myStmt_id.executeUpdate();
                myStmt_id.close();
            }
            rs_id.close();
            stmt_id.close();
            System.out.println("Завершение записи максимального значения+1");

            for (int j = j_db; j < c - 3; j++) {
                int k = 0;
                for (int j_1 = 0; j_1 < Inarr[j].length(); j_1++) {
                    if (String.valueOf(Inarr[j].charAt(j_1)).equals("|")) {
                        k++;
                        System.out.println("№начала значения 1=" + j_1);
                    }
                }
                System.out.println(k);
                String[] Mass = new String[k - 1];
                int k1 = -1;
                int k2 = -1;
                for (int j_1 = 0; j_1 < Inarr[j].length(); j_1++) {
                    if (String.valueOf(Inarr[j].charAt(j_1)).equals("|")) {
                        k2++;
                        System.out.println("№начала значения 2=" + k2);
                        System.out.println("№начала значения 3=" + (k1 + 1) + "  " + (j_1));
                        System.out.println("№начала значения 4=" + (j_1 - k1 + 1));
                        if ((j_1 - k1 + 1) > 250) {
                            System.out.println("№начала значения 5=" + (k1 + 1 + 250));
                            if (k1 != -1) {
                                Mass[k2 - 1] = Inarr[j].substring(k1 + 1, k1 + 1 + 250).trim();
                            }
                        } else {
                            if (k1 != -1) {
                                Mass[k2 - 1] = Inarr[j].substring(k1 + 1, j_1).trim();
                            }
                        }
                        k1 = j_1;
                    }
                }


                String SN_JOB = Mass[3].trim();
                String FN_JOB = Mass[3].trim();
                int ID_JOB = 0;
                int ID_JOB_ASU_TR = Integer.parseInt(Mass[4].trim());


                System.out.println("Наименование дол. '" + Mass[3].trim() + "'");
                System.out.println("Код дол. '" + Mass[4].trim() + "'");


                Statement stmt0 = myconnection1.createStatement();

                ResultSet rs0 = stmt0.executeQuery(" select a1.ID " +
                        " from ASU_POL.SOOTV_JOB_ASUTR a1 where  a1.ID_JOB_ASU_TR=" + ID_JOB_ASU_TR + " ");
                if (!rs0.next()) {

                    System.out.println("Должность с кодом " + ID_JOB_ASU_TR + " не найдена");

                    Statement stmt = myconnection1.createStatement();
                    ResultSet rs = stmt.executeQuery(" select a1.ID " +
                            " from ASU_POL.POLYGON_JOB a1 where a1.SN_JOB='" + SN_JOB + "'  " +
                            " and a1.FN_JOB='" + FN_JOB + "' ");
                    if (!rs.next()) {




                        System.out.println("Должность с наимен. " + SN_JOB + "   " + FN_JOB + " не найдена");

                        String query = "insert into ASU_POL.POLYGON_JOB ( SN_JOB, FN_JOB) values ( ?, ?)";
                        PreparedStatement myStmt = myconnection1.prepareStatement(query);
                        myStmt.setString(1, SN_JOB);
                        myStmt.setString(2, FN_JOB);
                        myStmt.executeUpdate();
                        myStmt.close();

                        System.out.println("Запись должность с наимен." + SN_JOB + " записана в POLYGON_JOB");
                    }

                    Statement stmt1 = myconnection1.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(" select a1.ID " +
                            " from ASU_POL.POLYGON_JOB a1 where a1.SN_JOB='" + SN_JOB + "'  " +
                            " and a1.FN_JOB='" + FN_JOB + "' ");
                    while (rs1.next()) {

                        ID_JOB = rs1.getInt("ID");
                        System.out.println("id новой записи " + ID_JOB + " в POLYGON_JOB");
                    }

                    rs.close();
                    stmt.close();
                    rs1.close();
                    stmt1.close();


                    Statement stmt2 = myconnection1.createStatement();

                    ResultSet rs2 = stmt2.executeQuery(" select a1.ID " +
                            " from ASU_POL.SOOTV_JOB_ASUTR a1 where a1.ID_JOB=" + ID_JOB + "  " +
                            " and a1.ID_JOB_ASU_TR=" + ID_JOB_ASU_TR + " ");
                    System.out.println("5----");
                    if (!rs2.next()) {
                        System.out.println("Не найдено совпадений в табл SOOTV_JOB_ASUTR ID_JOB=" + ID_JOB + " и ID_JOB_ASU_TR=" + ID_JOB_ASU_TR + " ");
                        String query = "insert into ASU_POL.SOOTV_JOB_ASUTR ( ID_JOB, ID_JOB_ASU_TR,DT_VVOD) values ( ?, ?,'" + date + "')";
                        PreparedStatement myStmt = myconnection1.prepareStatement(query);
                        myStmt.setInt(1, ID_JOB);
                        myStmt.setInt(2, ID_JOB_ASU_TR);
                        myStmt.executeUpdate();
                        myStmt.close();
                        System.out.println("Записано в табл SOOTV_JOB_ASUTR ID_JOB=" + ID_JOB + " и ID_JOB_ASU_TR=" + ID_JOB_ASU_TR + " ");

                    }

                    Statement stmt3 = myconnection1.createStatement();
                    ResultSet rs3 = stmt3.executeQuery(" select a1.ID " +
                            " from ASU_POL.SOOTV_JOB_ASUTR a1 where a1.ID_JOB=" + ID_JOB + "  " +
                            " and a1.ID_JOB_ASU_TR=" + ID_JOB_ASU_TR + " ");
                    while (rs3.next()) {
                        System.out.print("Записано в табл SOOTV_JOB_ASUTR di=" + rs3.getInt("ID"));

                    }


                    rs2.close();
                    stmt2.close();
                    rs3.close();
                    stmt3.close();



                } else {
                    PreparedStatement stmt_up = myconnection1.prepareStatement(
                            " UPDATE ASU_POL.SOOTV_JOB_ASUTR SET  DT_VVOD = '" + date + "' " +
                            " where  ID_JOB_ASU_TR=" + ID_JOB_ASU_TR + " ");
                    stmt_up.executeUpdate();
                    stmt_up.close();




                }
                rs0.clearWarnings();
                rs0.close();

                stmt0.close();




            }
            myconnection1.close();


        } catch (Exception e) {
            out.print("DB error" + e + "\n");
            System.exit(1);
        }


    }

    public static void main(String[] args) throws Exception {
        System.out.println(args[0]);
        System.out.println(args[1]);
        System.out.println(args[2]);
        System.out.println(args[3]);
        System.out.println(args.length);

        String beg_dt = "";
        String PytIn = "";
        String PytMail = "";
        int Stroc_1 = -1;
        try {
            String consoleEnc = System.getProperty("console.encoding", "Cp866");
            System.setOut(new CodepagePrintStream(System.out, consoleEnc));
            System.setErr(new CodepagePrintStream(System.err, consoleEnc));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unable to setup console codepage: " + e);
        }
        PrintStream out = System.out;
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormatter3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Calendar time1 = Calendar.getInstance();
        out.print("Begin: " + dateFormatter3.format(time1.getTime()).toString() + "\n");

        try {
            if (args.length >= 1) {
                beg_dt = args[0];
            } else {
                beg_dt = "current date - 1 day";
            }
            if (args.length >= 2) {
                PytIn = args[1];
            }
            if (args.length >= 3) {
                PytMail = args[2];
            }
            if (args.length >= 4) {
                Stroc_1 = Integer.parseInt(args[3]);
            }
        } catch (Exception e) {
            System.out.print("23" + e);
            System.exit(1);
        }

        if (beg_dt.equals("") || PytIn.equals("") || PytMail.equals("") || (Stroc_1 == -1)) {
            System.out.print("Неверно заданны параметры. Формат параметров gen_stat db_alias user pass otchname year month \n По умолчанию устанавливается текущий год и предыдущий месяц");
            System.exit(0);
        }



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
            //String 
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
            System.out.println("c=" + c);

            Rec_DB(Stroc_1);


        }



    }
}
