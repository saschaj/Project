/*
 * Ersteller:       Sascha Jungenkrüger
 * Erstelldatum:    31.05.2015
 * Dokument:        BenutzerServlet
 * Version:         1.0
 * Veränderungen:   1.0 (Sascha Jungenkrüger)
 *                  - Überprüfung der Verlinkungen mit passender Ausgabe 
 *                    eingebunden
 *                  1.1 (Julie Kenfack) 20.07.2015
 *                    - Prüfungen ob die Felder leer sind.
 *                    - Lexikalische und syntaktische Korrektheit der Eingaben.
 *                    - Vollständigkeitsprüfungen der Eingaben.
 *                    - Wenn die Felder nicht korrekt ausgefüllt sind, werden 
 *                      entsprechende Fehlern ausgewerfen.Sonst erscheint eine Meldung 
 *                      dass alle Daten erfolgreich aktualisiert wurden.
 *                  1.2 (julie Kenfack) 19.08.2015
 *                    - Prüfungen von Datumeingaben hinzugefüt.
 */
package Servlets;

import Entitys.Adresse;
import Entitys.Benutzer;
import Hilfsklassen.Datum;

import Entitys.Kunde;
import Hilfsklassen.Konstanten;
import Manager.DatenZugriffsObjekt;
import Manager.EmailHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BenutzerServlet extends HttpServlet {

    /**
     * Ersteller: Julie Kenfack Datum: 20.07.2015 Version: 1.1 
     *
     * Prüft, welche Aktion in der user_account.jsp aufgerufen wurde und führt
     * dann die entsprechende Methode auf.
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Pruefen, welche Aktion ausgeführt wurde.
        if (request.getParameter("kd_speichern") != null) {
            //Der Button "Kundendaten ändern" wurde in der User_account.jsp betaetigt.
            this.kundenDatenAendern(request, response);
        } else if (request.getParameter("bd_speichern") != null) {
            //Der Button "Benutzerdaten ändern" wurde in der User_account.jsp betaetigt.
            this.benutzerDatenAendern(request, response);
        }
    }

    /**
     * Ersteller: Julie Kenfack Datum: 28.07.2015 Version:1.1
     *
     * Diese Methode prüft, ob alle Kundendaten die eingegeben wurden, korrekt
     * sind Wenn ja dann werden die Daten in der Datenbank aktualisiert und der
     * Kunde wird auf Startseite weitergeleitet.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void kundenDatenAendern(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Datumsobjekte erzeugt.
        Datum date = new Datum();
        Datum aktuellesDatum = new Datum();

        // Session
        HttpSession session = request.getSession();

        DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
        Kunde k = (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        Benutzer ben = (Benutzer) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        Adresse adr = k.getAdresse();

        // Formulardaten in Variablen speichern
        String vname = request.getParameter("acc_vname");
        String name = request.getParameter("acc_name");
        String tel = request.getParameter("acc_tel");
        String strasse = request.getParameter("acc_strasse");
        String hnr = request.getParameter("acc_hnr");
        String plz = request.getParameter("acc_plz");
        String ort = request.getParameter("acc_ort");
        String land = request.getParameter("acc_land");
        String gebdatum = request.getParameter("acc_gebdat");

        // Hilfsvariablen
        boolean vnameIsTrue = false, nameIsTrue = false,
                gedatumIsTrue = false, strasseIsTrue = false,
                HnumIsTrue = false, plzIsTrue = false,
                ortIsTrue = false, landIstrue = false, telIsTrue = false;

        // variable für die Speicherung von Fehlern.
        String fehler[], fehler2[];

        //Hilfsvariable für die Ausgabe
        String ausgabe = "";

        // KundenId,BenutzerId und AdresseId in den Variablen gespeichert.
        int kId = k.getBenutzerId();
        int bId = ben.getBenutzerId();
        int aId = adr.getAdressId();

        //Formateingaben für das Datum
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date parseDate = null;

        // Ueberpruefung, ob der Vorname leeroder konform ist
        if (vname.equals("")) {
            // Fehlermeldung in Variable ausgabe gespeichert
            ausgabe = "Bitte geben Sie Ihren Vornamen ein!";

        } else if (!vname.matches("[A-Za-z]{1,30}")) {
            ausgabe += "\n Vorname ist nicht konform!";
        } else {
            vnameIsTrue = true;
        }
        // Ueberpruefung, ob der Name leer oder konform ist
        if (name.equals("")) {
            // Fehlermeldung in Variable ausgabe gespeichert
            ausgabe = ausgabe + "\n Bitte geben Sie Ihren Nachnamen ein!";

        } else if (!name.matches("[A-Za-z]{1,30}")) {
            ausgabe += "\n Name ist nicht konform!";
        } else {
            nameIsTrue = true;
        }

        // Ueberpruefung ob das Geburtsdatum konform ist. 
        try {
            if (!gebdatum.equals("")) {
                parseDate = format.parse(gebdatum);
                if ((gebdatum.charAt(2) == '.') && (gebdatum.charAt(5) == '.')) {
                    int tag = Integer.parseInt(gebdatum.substring(0, 2));
                    int mon = Integer.parseInt(gebdatum.substring(3, 5));
                    int jah = Integer.parseInt(gebdatum.substring(6, 10));
                    date.setzeDatum(tag, mon, jah);
                    if (aktuellesDatum.fueherAls(date)) {
                        gedatumIsTrue = false;
                        ausgabe += "\n Datum Liegt in die Zukunft.";
                    } else {
                        gedatumIsTrue = true;
                    }
                } else {
                    gedatumIsTrue = false;
                    ausgabe += "\n Datum Muss in der TT.MM.JJJJ sein. Bitte Korigieren.";
                }
            } else {
                gedatumIsTrue = true;
            }
        } catch (ParseException ex) {
            ausgabe += "\n Geburtsdatum ist nicht konform.";
        } catch (IllegalArgumentException iex) {
            ausgabe += iex.getMessage();
        }

        // Ueberpruefung ob die Strasse konform ist.
        if (!strasse.equals("")) {
            if (!strasse.matches("[A-Za-z]{1,30}")) {
                ausgabe += "\n Strasse ist nicht konform!";
            } else {
                strasseIsTrue = true;
            }
        } else {
            strasseIsTrue = true;
        }

        // Ueberpruefung ob der Hausnummer konform ist.
        if (!hnr.equals("")) {
            if (!hnr.matches("[0-9]{1,30}")) {
                ausgabe += "\n Hausnummer ist nicht konform!";
            } else {
                HnumIsTrue = true;
            }
        } else {
            HnumIsTrue = true;
        }
        // Ueberpruefung ob der Plz konform ist.
        if (!plz.equals("")) {
            if (!plz.matches("[0-9]{5,5}")) {
                ausgabe += "\n Postleitzahl ist nicht konform!";
            } else {
                plzIsTrue = true;
            }
        } else {
            plzIsTrue = true;
        }

        // Ueberpruefung ob der Ort konform ist.
        if (!ort.equals("")) {
            if (!ort.matches("[A-Za-z]{1,30}")) {
                ausgabe += "\n Ort ist nicht konform!";
            } else {
                ortIsTrue = true;
            }
        } else {
            ortIsTrue = true;
        }

        // Ueberpruefung ob das Land konform ist.
        if (!land.equals("")) {
            if (!land.matches("[A-Za-z]{1,30}")) {
                ausgabe += "\n Land ist nicht konform!";
            } else {
                landIstrue = true;
            }
        } else {
            landIstrue = true;
        }

        // Ueberpruefung ob die Telefonummer konform ist. 
        if (!tel.equals("")) {
            if (!tel.matches("[0-9]{5,12}")) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe += "\n Telefonnummer ist nicht konform!";
            } else {
                telIsTrue = true;
            }
        } else {
            telIsTrue = true;
        }

        // Überprüfung, ob alle Daten des Formulars korrekt sind
        if (vnameIsTrue && nameIsTrue && strasseIsTrue && gedatumIsTrue
                && HnumIsTrue && plzIsTrue && ortIsTrue && landIstrue && telIsTrue) {
            // Änderungen der Kundendaten werden durchgeführt
            dao.updateKundeDaten(vname, name, parseDate, tel, bId);
            // Änderungen der Adressedaten werden durchgeführt.
            dao.updateAdresse(strasse, hnr, plz, ort, land, aId);
            //Weiterleitung an update_user_complete.jsp
                 request.getRequestDispatcher("/update_user_complete.jsp")
                            .forward(request, response);

        } else {

            // Fehlermeldung wird gesplittet und im Array gespeichert
            // und auf der user_account.jsp ausgegeben.
            fehler = ausgabe.split("!");
            // Setzen der Fehler in den Request                    
            request.setAttribute(Konstanten.URL_PARAM_FEHLER, fehler);
            // Weiterleitung an user_account.jsp                
            request.getRequestDispatcher("/user_account.jsp")
                    .forward(request, response);

        }
        // DAO-Verbindung wird geschlossen
        dao.close();

    }

    /**
     * Ersteller: Julie Kenfack Datum: 28.07.2015 Version: 1.1
     *
     * Diese Methode prüft, ob alle benutzerdaten die eingegeben wurden, korrekt
     * sind Wenn ja dann werden die Daten in der Datenbank aktualisiert und der
     * Benutzer wird auf Startseite weitergeleitet.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void benutzerDatenAendern(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
        Kunde k = (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        Benutzer ben = (Benutzer) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);

        // Formulardaten in Variablen speichern
        String email1 = request.getParameter("e-mail");
        String email2 = request.getParameter("e-mail_n");
        String pw1 = request.getParameter("pw");
        String pw2 = request.getParameter("pw_n");

        // variable für die Speicherung von Fehlern.
        String fehler[], fehler2[];

        //Hilfsvariablen für die Ausgabe
        String ausgabe = "";

        //Hilfsvariablen für die Ausgabe
        boolean emailIsTrue = false, pwIsTrue = false;

        // BenutzerId in den Variablen gespeichert.
        int bId = ben.getBenutzerId();

        // Überprüfung, ob die E-Mail-Adresse leer ist
        if (email1.equals("") && email2.equals("")) {
            // Fehlermeldung in Variable ausgabe gespeichert
            ausgabe = ausgabe + "Bitte geben Sie Ihre neue E-Mail-Adresse ein!";
            // Überprüfung, ob die E-Mail-Adressen gleich sind
        } else if (!email1.equals(email2)) {
            // Fehlermeldung in Variable ausgabe gespeichert
            ausgabe = ausgabe + "\n E-Mail-Adressen stimmen nicht überein!";
        } else {
            // Überprüfung, ob die E-Mail-Adressen konform sind
            if (!email1.matches("[a-zA-Z0-9].+@[a-zA-Z0-9\\.-]+[a-zA-Z]{2,4}")) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe
                        + "\n E-Mail-Adresse ist nicht konform!";
            } else {
                // E-Mail-Adressen stimmen überein und werden als true gespeichert
                emailIsTrue = true;
            }
        }
        // Überprüfung, ob die Passwörter nicht leer sind
        if (pw1.equals("") && pw2.equals("")) {
            // Fehlermeldung in Variable ausgabe gespeichert
            ausgabe = ausgabe + "\n Bitte geben Sie Ihr neues Passwort ein!";
            // Überprüfung, ob die Passwörter gleich sind
        } else if (!pw1.equals(pw2)) {
            // Fehlermeldung in Variable ausgabe gespeichert
            ausgabe = ausgabe
                    + "\n Passwörter stimmen nicht überein!";
        } else {
            // Überprüfung, ob das Passwort weniger als 6 Zeichen hat
            if (pw1.length() < 6) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe
                        + "\n Das Passwort muss länger als 6 Zeichen sein!";
            } else {
                // Passwörter stimmen überein  und werden als true gespeichert
                pwIsTrue = true;
            }
        }
        // Überprüfung, ob alle Daten des Formulars korrekt sind
        if (emailIsTrue && pwIsTrue) {
            // Änderungen der Kundendaten werden durchgeführt.
            dao.updateBenutzerDaten(email1, pw1, bId);
             //Weiterleitung an update_user_complete.jsp
                 request.getRequestDispatcher("/update_user_complete.jsp")
                            .forward(request, response);

        } else {

            // Fehlermeldung wird gesplittet und im Array gespeichert
            // und auf der user_account.jsp ausgegeben.
            fehler2 = ausgabe.split("!");
            // Setzen der Fehler in den Request                    
            request.setAttribute("fehler2", fehler2);
            // Weiterleitung an user_account.jsp               
            request.getRequestDispatcher("/user_account.jsp")
                    .forward(request, response);

        }
        // DAO-Verbindung wird geschlossen
        dao.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
