package sat.test.automation.utils;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import sat.test.automation.core.GlobalVariables;
import sat.test.automation.core.SuperScript;

public class SendEmailAttachment extends SuperScript{

	private static final String STATUS_EMAIL_BODY = null;


	public void sendStatusEmailForRSA(String fromEmailId, String toReciepientsEmailAddresses, String attachmentPath){

	    final String username = ""; // from user email id
	    final String password = ""; // password

	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "172.28.16.77");
	    props.put("mail.smtp.port", "25");

	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(fromEmailId));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(toReciepientsEmailAddresses));
	        message.setSubject(GlobalVariables.STATUS_EMAIL_SUBJECT);
	        message.setText("PFA");

	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        MimeBodyPart emailContent = new MimeBodyPart();

	        Multipart multipart = new MimeMultipart();

	        messageBodyPart = new MimeBodyPart();
	        String file = attachmentPath;
	        String fileName = "TestDataoutput.xls";
	        DataSource source = new FileDataSource(file);
	        String emailBody = STATUS_EMAIL_BODY;
	        emailContent.setText(emailBody);
	        emailContent.setContent(emailBody, "text/html");
			
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(fileName);
	        multipart.addBodyPart(messageBodyPart);
	        messageBodyPart.setDescription("This is a sample mail",
			"sample mail");
	        multipart.addBodyPart(emailContent);
	        message.setContent(multipart);

//	        System.out.println("Sending");

	        Transport.send(message);

//	        System.out.println("Done");

	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
	
	
public static void main(String[] args) {
	
	SendEmailAttachment sendEmailAttachment = new SendEmailAttachment();
	sendEmailAttachment.sendStatusEmailForRSA("anupama.satija@igate.com", "jagdish.singh@igate.com", GlobalVariables.STATUS_EMAIL_ATTACHEMENT);
	

//    final String username = ""; // from user email id
//    final String password = ""; // password
//
//    Properties props = new Properties();
//    props.put("mail.smtp.auth", true);
//    props.put("mail.smtp.starttls.enable", true);
//    props.put("mail.smtp.host", "172.28.16.77");
//    props.put("mail.smtp.port", "25");
//
//    Session session = Session.getInstance(props,
//            new javax.mail.Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(username, password);
//                }
//            });
//
//    try {
//
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress("anupama.satija@igate.com"));
//        message.setRecipients(Message.RecipientType.TO,
//                InternetAddress.parse("jagdish.singh@igate.com,anupama.satija@igate.com"));
//        message.setSubject("Testing Subject");
//        message.setText("PFA");
//
//        MimeBodyPart messageBodyPart = new MimeBodyPart();
//
//        Multipart multipart = new MimeMultipart();
//
//        messageBodyPart = new MimeBodyPart();
//        String file = "C:\\dev\\testingdocument.docx";
//        String fileName = "testingdocument.docx";
//        DataSource source = new FileDataSource(file);
//        messageBodyPart.setDataHandler(new DataHandler(source));
//        messageBodyPart.setFileName(fileName);
//        multipart.addBodyPart(messageBodyPart);
//
//        message.setContent(multipart);
//
//        System.out.println("Sending");
//
//        Transport.send(message);
//
//        System.out.println("Done");
//
//    } catch (MessagingException e) {
//        e.printStackTrace();
//    }
  }
}