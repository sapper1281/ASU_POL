/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notall;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author apopovkin
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
               String to = "IMerkulova@serw.rzd";//line0_1.getE_MAIL();//
                
              String us = " ";
                      
                       
                        /*font-size:13px;
	text-decoration: none;
	font-weight: bold;
	color:#806C59;**/

                String from = "IMerkulova@serw.rzd";
                String host = "10.58.0.47";
                Properties properties = System.getProperties();
                properties.setProperty("mail.smtp.host", host);
                Session session = Session.getDefaultInstance(properties);
  try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.addRecipient(Message.RecipientType.TO,
                            new InternetAddress(/*"apopovkin@serw.rzd"*/to));
                    message.setSubject(" ");
                    message.setContent(us, "text/html;charset=UTF-8");


                    Transport.send(message);
                    System.out.println("Sent message successfully....");
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }
    }
}
