/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import Entitys.Benutzer;
import Entitys.Kunde;
import Entitys.Vertrag;
import Hilfsklassen.ZufallsStringErzeuger;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
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

    /**
     * Konstruktor des EmailHandler. Hier werden die benötigten Daten für die
     * Session, anhand der in der JavaMailSession hinterlegten Daten, belegt.
     */
    public EmailHandler() {
        try {
            ctx = new InitialContext();
            session = (Session) ctx.lookup("mail/info");
        } catch (NamingException ex) {
            Logger.getLogger(EmailHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * sendRegisterMai sendet eine Email mit den Benutzerdaten an die übergebene
     * Emailadresse.
     *
     * @param subject Text der Betreffzeile
     * @param recipient Empfänger
     * @param passwort Passwort
     *
     */
    public void sendRegisterMail(String subject, String recipient) {

        try {
            Message msg = new MimeMessage(session);
            msg.setSubject(subject);
            msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));

            // Body text.
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Ihre Registrierung wurde erfolgreich abgeschlossen."
                    + "\nSollten ihr Benutzername lautet:\n\n"
                    + recipient + "\n\n"
                    + "Sollten Sie ihr Passwort vergessen haben nutzen Sie die"
                    + "\"Passwort vergessen\"-Funktion."
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

    /**
     * sendInfoMail sendet eine E-Mail an die eigene E-Mail-Adresse und belegt
     * den Absender mit der vom Kontaktierenden hinterlegten E-Mail-Adresse.
     *
     * @param emailAbsender Die vom Kontaktierenden hinterlegte E-Mail-Adresse
     * @param text Der hinterlegte Text
     * @param name (Optional) der angegebene Name
     */
    public void sendInfoMail(String emailAbsender, String text, String name) {
        try {
            Message msg = new MimeMessage(session);
            msg.setSubject("Kontaktaufnahme von " + name);
            msg.setRecipient(RecipientType.TO, new InternetAddress("lovy.julia@gmail.com"));
            msg.setFrom(new InternetAddress(emailAbsender));
            // Body text.
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(text);

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

    /**
     * sendPasswortMail sendet eine E-Mail an die übergebene E-Mail-Adresse in
     * der ein neuerzeugtes Passwort für den Benutzeraccount eben dieser steht.
     *
     * @param b
     */
    public void sendPasswortMail(Benutzer b, String passwort) {
        try {
            Message msg = new MimeMessage(session);
            msg.setSubject("Neues Passwort für SWPSS2015 Vertragsverwaltung");
            msg.setRecipient(RecipientType.TO, new InternetAddress(b.getEmail()));

            // Body text.
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("\nIhre Zugangsdaten: \n"
                    + "\nEmail-Adresse: " + b.getEmail()
                    + "\nPasswort: " + passwort);

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

    /**
     * Diese Methode versendet eine E-Mail. Der Inhalt sind alle in kürze ab-
     * laufenden Vertäge eines Kunden.
     *
     * @param k In der E-Mail erwähnte Kunde
     * @param l Liste alle Verträge die Ablaufen
     */
    public void sendeAblaufbenachrichtigung(Kunde k, ArrayList<Vertrag> l) {
        try {
            Message msg = new MimeMessage(session);
            msg.setSubject("Bevorstehende Kündigungsfristen ihrer Verträge");
            msg.setRecipient(RecipientType.TO, new InternetAddress(k.getEmail()));

            String msgBody = "Diese Email informiert sie über in Kürze "
                    + "ablaufende Kündigungsfristen Ihrer Verträge.\n";
            Calendar datum = Calendar.getInstance(Locale.GERMANY);
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMANY);
            for (Vertrag v : l) {
                SystemManager.datumAendern(datum, v.getKuendigungsfristEinheit(), -v.getKuendigungsfrist());
                Date anzeige = new Date(datum.getTimeInMillis());
                msgBody = msgBody
                        + "\nVertrag : " + v.getVertragNr() + " - "
                        + v.getVertragsBezeichnung() + "\n"
                        + "Kündbar bis zum: " + df.format(anzeige) + "\n";
            }
            // Body text.
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(msgBody);

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

    public void sendePasswortBestaetigung(String recipient, String ref, String pfad) {
        try {
            Message msg = new MimeMessage(session);
            msg.setSubject("Bestätigung erforderlich - Passwort zurücksetzen");
            msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));

            // Body text.
            BodyPart messageBodyPart = new MimeBodyPart();
            String msgBody = "Sie haben die \"Passwort vergessen\"-Funktion"
                    + " aufgerufen. Klicken Sie auf den folgenden Link um das"
                    + " Passwort zurückzusetzen. Dieses wird Ihnen dann in einer"
                    + " seperaten E-Mail zugesendet. \n";
            pfad = pfad.substring(0, pfad.lastIndexOf("/"));
            pfad = pfad + "/ConfirmationServlet";
            String link = pfad + "?user=" + recipient + "?action=password?ref=";
            messageBodyPart.setText(msgBody + "\n" + link + ref);

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

    public void sendeRegistrierungsBestaetigung(String subject, String recipient, String ref,
            String pfad, String passwort) {
        try {
            Message msg = new MimeMessage(session);
            msg.setSubject(subject);
            msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));

            // Body text.
            BodyPart messageBodyPart = new MimeBodyPart();
            String msgBody = "Herzlich Willkommen bei der SWPSS2015 "
                    + "Vertragsverwaltung."
                    + "\n"
                    + ""
                    + "\nIhre Zugangsdaten: \n"
                    + "\nEmail-Adresse: " + recipient
                    + "\nPasswort: " + passwort + "\n\n";

            pfad = pfad.substring(0, pfad.lastIndexOf("/"));
            pfad = pfad + "/ConfirmationServlet";
            String link = pfad + "?user=" + recipient + "?action=register?ref=";
            messageBodyPart.setText(msgBody + "\n" + link + ref);

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
