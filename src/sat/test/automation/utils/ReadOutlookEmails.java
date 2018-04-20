/*package sat.test.automation.utils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.jsoup.Jsoup;

public class ReadOutlookEmails {
	public static Folder inboxEmailFolderName;
	public static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	public static final String EMAIL_HOST = "nbcumail.inbcu.com";
	public static final String EMAIL_PORT = "993";
	public static final String USER_NAME = "tfayd\\206543321";
	public static final String PASSWORD = "Pa55word";

	public static String _from = "";
	public static String _to = "";
	public static String _subject = "";
	public static String _body = "";

	public static HashMap<String, String> emailDetails = new HashMap<>();

	public static void printLatestEmailDetails() {
		HashMap<String, String> finalEmailDetails = getLatestEmailDetails();
		
		System.out.println("From details : "+finalEmailDetails.get("From"));
		System.out.println("To Details : "+finalEmailDetails.get("To"));
		System.out.println("Subject Details : "+finalEmailDetails.get("Subject"));
//		System.out.println("Body Details : "+finalEmailDetails.get("Body"));
		
		System.out.println(html2text(finalEmailDetails.get("Body")));
	}

	public static HashMap<String, String> getLatestEmailDetails() {
		System.out.println("Inside get all email message ...");

		Properties props = System.getProperties();
		// Set manual Properties
		props.setProperty("mail.imaps.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.imaps.socketFactory.fallback", "false");
		props.setProperty("mail.imaps.port", "993");
		props.setProperty("mail.imaps.socketFactory.port", "993");
		props.put("mail.imaps.host", "nbcumail.inbcu.com");
		// props.put("mail.imaps.domain", "tfayd.inbcu.com");

		try {
			 Create the session and get the store for read the mail. 

			Session session = Session.getDefaultInstance(System.getProperties(), null);
			Store store = session.getStore("imaps");

			store.connect(EMAIL_HOST, 993, USER_NAME, PASSWORD);

			 Mention the folder name which you want to read. 

			// inbox = store.getDefaultFolder();
			// inbox = inbox.getFolder("INBOX");
			inboxEmailFolderName = store.getFolder("INBOX");

			 Open the inbox using store. 

			inboxEmailFolderName.open(Folder.READ_ONLY);

			Message messages[] = inboxEmailFolderName.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			System.out.println("No. of Unread Messages : " + inboxEmailFolderName.getUnreadMessageCount());

			 Use a suitable FetchProfile 
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);

			inboxEmailFolderName.fetch(messages, fp);

			try {

				// printAllMessages(messages);
				writeAllMessages(messages);

				inboxEmailFolderName.close(true);
				store.close();

			} catch (Exception ex) {
				System.out.println("Exception arise at the time of read mail");
				ex.printStackTrace();
			}

		} catch (MessagingException e) {
			System.out.println("Exception while connecting to server: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(2);
		}
		return emailDetails;
	}

	// Constructor of the calss.

	public ReadOutlookEmails() {
		System.out.println("Inside MailReader()...");
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		 Set the mail properties 
		
		 * props.put("mail.smtp.starttls.enable", "true"); Session session =
		 * Session.getInstance(props); MimeMessage msg = new
		 * MimeMessage(session); // set the message content here
		 * Transport.send(msg, username, password);
		 

		Properties props = System.getProperties();
		// Set manual Properties
		props.setProperty("mail.imaps.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.imaps.socketFactory.fallback", "false");
		props.setProperty("mail.imaps.port", "993");
		props.setProperty("mail.imaps.socketFactory.port", "993");
		props.put("mail.imaps.host", "nbcumail.inbcu.com");
		// props.put("mail.imaps.domain", "tfayd.inbcu.com");

		try {
			 Create the session and get the store for read the mail. 

			Session session = Session.getDefaultInstance(System.getProperties(), null);
			Store store = session.getStore("imaps");

			store.connect("nbcumail.inbcu.com", 993, "tfayd\\206433293", "****");

			 Mention the folder name which you want to read. 

			// inbox = store.getDefaultFolder();
			// inbox = inbox.getFolder("INBOX");
			inboxEmailFolderName = store.getFolder("INBOX/newsconnect");

			 Open the inbox using store. 

			inboxEmailFolderName.open(Folder.READ_ONLY);

			Message messages[] = inboxEmailFolderName.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			System.out.println("No. of Unread Messages : " + inboxEmailFolderName.getUnreadMessageCount());

			 Use a suitable FetchProfile 
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);

			inboxEmailFolderName.fetch(messages, fp);

			try {

				// printAllMessages(messages);
				writeAllMessages(messages);

				inboxEmailFolderName.close(true);
				store.close();

			} catch (Exception ex) {
				System.out.println("Exception arise at the time of read mail");
				ex.printStackTrace();
			}

		} catch (MessagingException e) {
			System.out.println("Exception while connecting to server: " + e.getLocalizedMessage());
			e.printStackTrace();
			System.exit(2);
		}

	}

	public void printAllMessages(Message[] msgs) throws Exception {
		for (int i = 0; i < msgs.length; i++) {
			System.out.println("MESSAGE #" + (i + 1) + ":");
			printEnvelope(msgs[i]);
		}
	}

	public static void writeAllMessages(Message[] msgs) throws Exception {
		System.out.println(msgs.length + " -------- message length ");
		// for (int i = 0; i < msgs.length; i++) {
		// System.out.println("MESSAGE #" + (i + 1) + ":");
		// writePart(msgs[i]);
		// }

		if (msgs.length != 0) {
			writePart(msgs[msgs.length - 1]);
		}
	}

	public static void writePart(Part p) throws Exception {
		if (p instanceof Message)
			// Call methos writeEnvelope
			writeEnvelope((Message) p);

		System.out.println("----------------------------");
		System.out.println("CONTENT-TYPE: " + p.getContentType());

		// check if the content is plain text
		if (p.isMimeType("text/plain")) {
			System.out.println("This is plain text");
			System.out.println("---------------------------");
			System.out.println((String) p.getContent());
			_body = (String) p.getContent();
		}
		// check if the content has attachment
		else if (p.isMimeType("multipart/*")) {
			System.out.println("This is a Multipart");
			System.out.println("---------------------------");
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++){
				writePart(mp.getBodyPart(i));
			}
				
		}
		// check if the content is a nested message
		else if (p.isMimeType("message/rfc822")) {
			System.out.println("This is a Nested Message");
			System.out.println("---------------------------");
			writePart((Part) p.getContent());
			
		}
		// check if the content is an inline image
		else if (p.isMimeType("image/jpeg")) {
			System.out.println("--------> image/jpeg");
			Object o = p.getContent();

			InputStream x = (InputStream) o;
			// Construct the required byte array
			System.out.println("x.length = " + x.available());
			int i = 0;
			byte[] bArray = new byte[x.available()];
			while ((i = (int) ((InputStream) x).available()) > 0) {
				int result = (int) (((InputStream) x).read(bArray));
				if (result == -1)
					i = 0;

				break;
			}
			FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
			f2.write(bArray);
		} else if (p.getContentType().contains("image/")) {
			System.out.println("content type" + p.getContentType());
			File f = new File("image" + new Date().getTime() + ".jpg");
			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) p.getContent();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = test.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} else {
			Object o = p.getContent();
			if (o instanceof String) {
				System.out.println("This is a string");
				System.out.println("---------------------------");
				System.out.println((String) o);
				_body = _body+_body+(String) o;
			} else if (o instanceof InputStream) {
				System.out.println("This is just an input stream");
				System.out.println("---------------------------");
				InputStream is = (InputStream) o;
				is = (InputStream) o;
				int c;
				while ((c = is.read()) != -1)
					System.out.write(c);
			} else {
				System.out.println("This is an unknown type");
				System.out.println("---------------------------");
				System.out.println(o.toString());
				_body = _body+(String) o;
			}
		}
		emailDetails.put("Body", _body);
	}

	
	 * This method would print FROM,TO and SUBJECT of the message
	 
	public static void writeEnvelope(Message m) throws Exception {
		System.out.println("This is the message envelope");
		System.out.println("---------------------------");
		Address[] a;

		// FROM
		if ((a = m.getFrom()) != null) {
			for (int j = 0; j < a.length; j++){
				System.out.println("FROM: " + a[j].toString());
				_from = _from +";"+ a[j].toString();
			}
				
			emailDetails.put("From", _from);
		}

		// TO
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
			for (int j = 0; j < a.length; j++){
				System.out.println("TO: " + a[j].toString());
				_to = _to  +";"+ a[j].toString();
				}
			emailDetails.put("To", _to);
		}

		// SUBJECT
		if (m.getSubject() != null){
			System.out.println("SUBJECT: " + m.getSubject());
			_subject = _subject+m.getSubject();
			emailDetails.put("Subject", _subject);
		}
			

	}

	public void printEnvelope(Message message) throws Exception {

		Address[] a;

		// FROM
		if ((a = message.getFrom()) != null) {
			for (int j = 0; j < a.length; j++) {
				System.out.println("From : " + a[j].toString());
			}
		}

		String subject = message.getSubject();
		String body = (String) message.getContent();
		System.out.println("Here is the subject  : " + subject);
		System.out.println("Here is the body : " + body);
		Date receivedDate = message.getReceivedDate();
		Date sentDate = message.getSentDate(); // receivedDate is returning
		// null. So used getSentDate()

		// //Dar Formato a la fecha
		// SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		//
		// System.out.println("Subject : " + subject);
		//
		// if (receivedDate != null) {
		// System.out.println("Recibido: " + df.format(receivedDate));
		// }
		//
		// System.out.println("Enviado : " + df.format(sentDate));
	}

	public static void main(String args[]) {
//		new OutlookReader_imap();
		printLatestEmailDetails();
	}
	
	public static String html2text(String html) {
	    return Jsoup.parse(html).text();
	}
}*/