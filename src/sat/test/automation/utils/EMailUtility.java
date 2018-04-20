package sat.test.automation.utils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EMailUtility  {

	private static boolean sendEmail;

	public static boolean isSendEmail() {
		return sendEmail;
	}

	public static void setSendEmail(boolean sendEmail) {
		EMailUtility.sendEmail = sendEmail;
	}

	public static boolean isBlank(String str) {
		boolean result = false;
		if (str == null || str.trim().length() == 0) {
			result = true;
		}
		return result;
	}

	public static boolean isListNullOrEmpty(List<?> list) {

		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	public static String getCurrentDate(String pattern) {
		final Date currentDateTime = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(pattern);
		return simpleDateFormat.format(currentDateTime);
	}

	/**
	 * This method sends status e-mail.
	 * 
	 * @author
	 * @param emailAddresses
	 * @param body
	 * @throws DACException
	 */
	public static void generateStatusEMail(final List<String> emailAddresses,
			String body) throws Exception {
		final List<String> senders = new ArrayList<String>();
		for (int i = 0; i < emailAddresses.size(); i++) {
			final String email = emailAddresses.get(i);
			if (!isBlank(email)) {
				senders.add(email.trim());
			}
		}
		if (!isListNullOrEmpty(senders)) {
			final Object toId[] = senders.toArray();
			final String fromID = "anil.nagaraju@igate.com";
			sendEmailMessage(fromID, toId, "AKN Testing :"
					+ getCurrentDate("MM/dd/yyyy"), body);
		}
	}

	private static Address[] recipients(Object[] to) throws Exception {
		Address[] addresses = new Address[to.length];
		try {
			for (int i = 0; i < to.length; i++) {
				if (to[i] != null) {
					addresses[i] = new InternetAddress(to[i].toString());
				}
			}
		} catch (AddressException e) {
			throw new Exception("Reading recipients failed: " + e.getMessage());
		}
		return addresses;
	}

	/**
	 * This method sends e-mail.
	 * 
	 * @param sender
	 * @param receiver
	 * @param subjectLine
	 * @param bodyContent
	 * @throws DACException
	 */
	public static void sendEmailMessage(String sender, Object[] receiver,
			String subjectLine, String bodyContent) throws Exception {

		// Recipient's email ID needs to be mentioned.
		Address[] addresses = null;
		Object[] to = receiver; // We can call a function which returns array
								// String of Recipient’s
		addresses = recipients(to);

		// Sender's email ID needs to be mentioned
		String from = sender;

		// Assuming you are sending email from iGATE mail server
		String host = "IGTNSEZBF138"; // sfarrayw.opr.statefarm.org

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipients(Message.RecipientType.TO, addresses);

			// Set Subject: header field
			message.setSubject(subjectLine);

			// Now set the actual message
			message.setText(bodyContent);

			// Send message
			// -- Set some other header information --
			message.setSentDate(new Date());

			// INFO: only SMTP protocol is supported...
			Transport transport = session.getTransport("smtp");
			transport.connect(host, "", "");
			message.saveChanges();

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();

			String str = new String("This is a sample mail");

			String lEXCEL_DB_PATH=null;
			String file = lEXCEL_DB_PATH;// "C:/DEV/examples.txt"; //path of file
										// to be attached
			String fileName = "examples.txt";
			System.out.println(file + "    " + fileName);
			DataSource source = new FileDataSource(file);

			messageBodyPart1.setText(str);
			messageBodyPart1.setContent(str, "text/html");
			multipart.addBodyPart(messageBodyPart1);
			messageBodyPart.setDescription("This is a sample mail",
					"sample mail");
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			message.saveChanges();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Sent message successfully....");
		} catch (MessagingException e) {
			throw new Exception("EMail sending failed: " + e.getMessage());
		}
	}

}
