package folderfetchimap;

import com.sun.mail.imap.IMAPFolder;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author oscar
 */
public class FolderFetchIMAP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MessagingException, IOException{
        IMAPFolder folder = null;
        Store store = null;
        String subject = null;
        Flag flag = null;
        try{
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
            
            Session session = Session.getDefaultInstance(props,null);
            store = session.getStore("imaps");
            store.connect("imap.gmail.com","micorreo@gmail.com","mipassword");
            
            folder = (IMAPFolder) store.getFolder("[Gmail]/Spam");
            
            if (!folder.isOpen()) {
                folder.open(Folder.READ_WRITE);
                Message[] messages = folder.getMessages();
                System.out.println("No. of messages: "+folder.getMessageCount());
                System.out.println("No. of Unread messages: "+folder.getUnreadMessageCount());
                System.out.println(messages.length);
                for (int i = 0; i < messages.length; i++) {
                    System.out.println("****************************************");
                    System.out.println("MESSAGE "+(i+1)+":");
                    Message msg = messages[i];
                    subject = msg.getSubject();
                    
                    System.out.println("Subject: "+subject);
                    System.out.println("From: "+msg.getFrom()[0]);
                    System.out.println("To: "+msg.getAllRecipients()[0]);
                    System.out.println("Date: "+msg.getReceivedDate());
                    System.out.println("Size: "+msg.getSize());
                    System.out.println(msg.getFlags());
                    System.out.println("Body : \n"+msg.getContent());
                    System.out.println(msg.getContentType());
                }
            }
        }
        finally {
            if (folder != null && folder.isOpen()) {
                folder.close(true);
            }
            if (store != null) {
                store.close();
            }
        }
    }
    
}
