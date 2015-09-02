/*
 * Ersteller:       Sascha Jungenkrüger
 * Erstelldatum:    31.05.2015
 * Dokument:        KontaktServlet
 * Version:         1.0
 * Veränderungen:   1.0 (Sascha Jungenkrüger)
 *                  - Überprüfung der Verlinkungen mit passender Ausgabe 
 *                    eingebunden
 *                  1.1 (Julie Kenfack) 20.07.2015
 *                    - Prüfungen ob die Pflichtfelder leer sind.
 *                    - Lexikalische und syntaktische Korrektheit der Eingaben.
 *                    - Vollständigkeitsprüfungen der Eingaben.
 *                    - Wenn die Felder nicht korrekt ausgefüllt sind, werden 
 *                       entsprechende Fehlern ausgewerfen.Sonst erscheint eine Meldung 
 *                       , dass alle Daten erfolgreich gesendet wurden.
 *                  1.2 (Julie Kenfack) 19.08.2015
 *                     - Email-Benachrichtungen hinzugefügt.
 */
package Servlets;

import Hilfsklassen.Konstanten;
import Manager.EmailHandler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KontaktServlet extends HttpServlet {

    /**
     * Ersteller: Julie Kenfack Datum: 20.07.2015 Version: 1.1 Änderungen: -
     *
     * Diese Methode prüft, ob alle kontaktdaten(Captcha erforderlich) die
     * eingegeben wurden, korrekt sind. Wenn ja dann werden die Daten per email
     * an uns geschickt und der User wird auf Startseite weitergeleitet.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String ausgabe = "", meta = "";
        String fehler[];

        // Formulardaten in Variablen speichern
        String name = request.getParameter("your_name");
        String email = request.getParameter("your_email");
        String mitteilung = request.getParameter("your_message");

        //Abschicken wurde geklickt
        if (request.getParameter("contact_submitted") != null) {
            // Überprüfung, ob der Name leer ist
            if (name.equals("")) {
                ausgabe = "Bitte geben Sie Ihrer Name ein.<br/>";
            }
            // Überprüfung, ob die email leer ist
            if (email.equals("")) {
                ausgabe += "Bitte geben Sie Ihre E-Mail-Adresse ein.<br/>";
            } // Überprüfung, ob die email konform ist
            else if (!email.matches("^[a-zA-Z0-9][\\w\\.-]*@(?:[a-zA-Z0-9][a-zA-Z0-9_-]+\\.)+[A-Z,a-z]{2,5}$")) {
                ausgabe += "Deine Email entspricht das Format nicht.<br/>";
            }
            // Überprüfung, ob die Mitteilung leer ist
            if (mitteilung.equals("")) {
                ausgabe += "Bitte geben Sie Ihre Mitteilung ein.<br/>";
            }
            // Überprüfung, ob bei dem Captcha die richtige Antwort eingetragen wurde.
            if (!(request.getParameter("benutzer_antwort") != null && request.getParameter("antwort") != null
                    && request.getParameter("antwort").equals(request.getParameter("benutzer_antwort")))) {
                ausgabe += "captcha falsch, ewartet ist:" + request.getParameter("antwort") + ", Deine Antwort ist aber:" + request.getParameter("benutzer_antwort");
            }
            if (!ausgabe.equals("")) {
                // Fehlermeldung wird gesplittet und im Array gespeichert
                // und auf der contact.jsp ausgegeben.
                fehler = ausgabe.split("!");
                // Setzen der Fehler in den Request                    
                request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, fehler);

                // Weiterleitung an contact.jsp
                request.getRequestDispatcher("/contact.jsp").forward(request, response);
            } else {
                meta = "<meta http-equiv='refresh' content='2; URL=index.jsp'>";
                ausgabe = "Daten wurden erfolgreich gesendet";

                // Automatisch generiert
                response.setContentType("text/html;charset=UTF-8");

                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet KontaktServlet</title>");
                    out.println(meta);
                    out.println("</head>");
                    out.println("<body>");
                    out.println(ausgabe);
                    out.println("</body>");
                    out.println("</html>");
                }
                // Erzeugung eines EmailHanhlers
                EmailHandler eh = new EmailHandler();
                // Aufruf der Methode für die Email-Benachrichtung
                eh.sendInfoMail(email, mitteilung, name);
            }
        }
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
