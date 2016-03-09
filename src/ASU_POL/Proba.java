/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ASU_POL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author apopovkin
 */
public class Proba {
  public static void main(String[] args) throws Exception {
            
    File fIn = new File("D:\\ASU_POL_NSI\\H58_SP.TXT");
    ArrayList<String> list=new ArrayList<String>();
            if (fIn.isFile()) {
                
                BufferedReader InStream1 = new BufferedReader(
                        new InputStreamReader(
                        new FileInputStream("D:\\ASU_POL_NSI\\H58_SP.TXT"), "windows-1251"));
                InStream1.mark(0);
                String line;
                while (((line = InStream1.readLine()) != null) ) {
                   System.out.println(line.length());
                    list.add(line);
                }
                
               /* for (String str:list){
                System.out.println(str);}
                
                /*for (Iterator<String> it = list.iterator(); it.hasNext();) {
                   System.out.println(it.next());
                }*/
                
        }
    }
}
