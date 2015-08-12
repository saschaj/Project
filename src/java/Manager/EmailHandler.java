/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import Entitys.Benutzer;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.naming.InitialContext;

/**
 * 
 * @author Mladko
 */
public class EmailHandler {
    
    private DatenZugriffsObjekt dao;
    
    
    /**
     * Diese Methode sendet eine Email mit den Benutzerdaten an die übergebene
     * Emailadresse.
     * 
     * @param subject Text der Betreffzeile
     * @param recipient Empfänger
     * @param passwort Passwort
     * @param url Loginseiten URL
     * @throws Exception 
     */
    public void sendRegisterMail(String subject, InternetAddress recipient, String passwort, String url) throws Exception {
        InitialContext ctx = new InitialContext();
        
        Session session = (Session) ctx.lookup("mail/info");       
        
        Message msg = new MimeMessage(session);
        msg.setSubject(subject);
        msg.setRecipient(RecipientType.TO, recipient);
        
        // Body text.
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Herzlich Willkommen bei der SWPSS2015 "
                + "Vertragsverwaltung."
                + "\n\nIhre Registrierung war erfolgreich."
                + ""
                + "\nIhre Zugangsdaten sind:"
                + "\nEmail-Adresse: " + recipient.getAddress()
                + "\nPasswort:" + passwort
                + "\n\nSie können sich nun auf folgender Seite einloggen:"
                + "\n\n" + url);

        // Multipart message.
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Add multipart message to email.
        msg.setContent(multipart);

        // Send email.
        Transport.send(msg);
    }
     
     public void sendHintMail() throws Exception {
        InitialContext ctx = new InitialContext();
        Session session =
            (Session) ctx.lookup("mail/info");
        
        
    }
     
}