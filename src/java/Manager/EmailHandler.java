/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import Entitys.Benutzer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * 
 * @author Mladko
 */
public class EmailHandler {
        
    private InitialContext ctx;
    private Session session;
    
    public EmailHandler(){
        try {
            ctx = new InitialContext();
            session = (Session) ctx.lookup("mail/info");  
        } catch (NamingException ex) {
            Logger.getLogger(EmailHandler.class.getName()).log(Level.SEVERE, null, ex);              
        }           
    }
    
    /**
     * Diese Methode sendet eine Email mit den Benutzerdaten an die übergebene
     * Emailadresse.
     * 
     * @param subject Text der Betreffzeile
     * @param recipient Empfänger
     * @param passwort Passwort
     * @param url Loginseiten URL
     *  
     */
    public void sendRegisterMail(String subject, String recipient, String passwort) {
                
        try {
            Message msg = new MimeMessage(session);
            msg.setSubject(subject);
            msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
            
            // Body text.
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Herzlich Willkommen bei der SWPSS2015 "
                    + "Vertragsverwaltung."
                    + "\n\nIhre Registrierung war erfolgreich."
                    + ""
                    + "\nIhre Zugangsdaten sind:"
                    + "\nEmail-Adresse: " + recipient
                    + "\nPasswort:" + passwort
                    );
            
            // Multipart message.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            
            // Add multipart message to email.
            msg.setContent(multipart);
            
            // Send email.
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void sendHintMail() throws Exception {
               
        
    }
     
    public void sendPasswortMail(String recipient) {
        try {
            Message msg = new MimeMessage(session);
            msg.setSubject("Neues Passwort für SWPSS2015 Vertragsverwaltung");
            msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
            
            // Body text.
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Leider ist diese Funktion noch nicht"
                    + " implementiert.");
            
            // Multipart message.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            
            // Add multipart message to email.
            msg.setContent(multipart);
            
            // Send email.
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}