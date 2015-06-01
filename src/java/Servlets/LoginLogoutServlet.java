/*
 * Ersteller:       Sascha Jungenkrüger
 * Erstelldatum:    31.05.2015
 * Dokument:        LoginLogoutServlet
 * Version:         1.1
 * Veränderungen:   1.0 (Sascha Jungenkrüger)
 *                  - Überprüfung der Verlinkungen mit passender Ausgabe 
 *                    eingebunden
 *                  1.1 (Sascha Jungenkrüger) 01.06.2015
 *                  - Registrierung eingefügt
 */
package Servlets;

import Entitys.Benutzer;
import Manager.DatenZugriffsObjekt;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginLogoutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        // Deklaration des Hilfsvariablen für die demenstprechenden Funktionen
        DatenZugriffsObjekt dao;
        Benutzer ben = new Benutzer();
        String fehler[], meta = "", ausgabe = "", vname,
                name, email1, email2, pw1, pw2;
        Boolean vnameIsTrue = false, nameIsTrue = false, isRegister = false,
                emailIsTrue = false, pwIsTrue = false, eMailIsAvailable = false;
        // Initialisierung der verschiedenen Verlinkungen
        String login = request.getParameter("login");
        String register = request.getParameter("register");
        String getPassword = request.getParameter("get_pw");
        String logout = request.getParameter("logout");

        // Überprüfung, welcher Button gedrückt wurde
        if (register != null) {
            // Benutzerregistrierung
            // Formulardaten in Variablen speichern
            vname = request.getParameter("reg_vname");
            name = request.getParameter("reg_name");
            email1 = request.getParameter("reg_email");
            email2 = request.getParameter("reg_email2");
            pw1 = request.getParameter("reg_pw");
            pw2 = request.getParameter("reg_pw2");

            // Überprüfung, ob der Vorname leer ist
            if (vname.equals("")) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = "Bitte geben Sie Ihren Vornamen ein!";
            } else {
                // Vorname wurde eingetragen und wird also true gespeichert
                vnameIsTrue = true;
            }
            // Überprüfung, ob der Nachname leer ist
            if (name.equals("")) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe + "\n Bitte geben Sie Ihren Nachnamen ein!";
            } else {
                // Nachname wurde eingetragen und wird also true gespeichert
                nameIsTrue = true;
            }
            // Überprüfung, ob die E-Mail-Adresse leer ist
            if (email1.equals("") && email2.equals("")) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe + "Bitte geben Sie Ihre E-Mail-Adresse ein!";
                // Überprüfung, ob die E-Mail-Adressen gleich sind
            } else if (!email1.equals(email2)) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe + "\n E-Mail-Adressen stimmen nicht überein. "
                        + "Bitte überprüfen Sie Ihre Eingabe!";
            } else {
                // Überprüfung, ob die E-Mail-Adressen konform sind
                if (!email1.matches("[a-zA-Z0-9].+@[a-zA-Z0-9\\.-]+[a-zA-Z]{2,4}")) {
                    // Fehlermeldung in Variable ausgabe gespeichert
                    ausgabe = ausgabe
                            + "\n E-Mail-Adresse ist nicht konform. "
                            + "Bitte überprüfen Sie Ihre Eingabe!";
                } else {
                    // E-Mail-Adressen stimmen überein 
                    // und werden als true gespeichert
                    emailIsTrue = true;
                }
            }
            // Überprüfung, ob die Passwörter nicht leer sind
            if (pw1.equals("") && pw2.equals("")) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe + "\n Bitte geben Sie ein Passwort ein!";
                // Überprüfung, ob die Passwörter gleich sind
            } else if (!pw1.equals(pw2)) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe
                        + "\n Passwörter stimmen nicht überein. "
                        + "Bitte überprüfen Sie Ihre Eingabe!";
            } else {
                // Überprüfung, ob das Passwort weniger als 6 Zeichen hat
                if (pw1.length() < 6) {
                    // Fehlermeldung in Variable ausgabe gespeichert
                    ausgabe = ausgabe
                            + "\n Das Passwort muss länger als 6 Zeichen sein!";
                } else {
                    // Passwörter stimmen überein 
                    // und werden als true gespeichert
                    pwIsTrue = true;
                }
            }

            // Überprüfung, ob alle Daten des Formulars korrekt sind
            if (vnameIsTrue && nameIsTrue && emailIsTrue && pwIsTrue) {
                // Initialisierung des DAO
                dao = new DatenZugriffsObjekt();
                eMailIsAvailable = dao.isEmailAvailable(email1);
                if (eMailIsAvailable) {
                    // Registrierung wird durchgeführt
                    // Boolscher Rückgabewert wird in isRegister gespeichert
                    isRegister = dao.register(vname, name, email1, ben.createHash(pw1));
                    // Überprüfung, ob die Registrierung erfolgreich war
                    if (isRegister) {
                        meta
                                = "<meta http-equiv='refresh' content='2; URL=index.jsp'>";
                        ausgabe = "Ihre Registrierung war erfolgreich!";
                        // Zurücksetzen der Session
                        session.invalidate();
                        // DAO-Verbindung wird geschlossen
                        dao.close();
                    }
                } else {
                    ausgabe = "Ihre E-Mail-Adresse ist schon vorhanden!";
                    // Fehlermeldung wird gesplittet und im Array gespeichert
                    // und auf der login_register.jsp ausgegeben.
                    fehler = ausgabe.split("!");
                    // Setzen der Fehler in den Request                    
                    request.setAttribute("fehler", fehler);
                    // Da es Fehler im Formular gibt stellt man dem Besucher seine
                    // eingegebenen Daten zur Verfügung, damit er sie
                    // überarbeiten bzw. ergänzen kann.
                    request.getRequestDispatcher("/login_register.jsp")
                            .forward(request, response);
                }
            } else {
                // Fehlermeldung wird gesplittet und im Array gespeichert
                // und auf der login_register.jsp ausgegeben.
                fehler = ausgabe.split("!");
                // Setzen der Fehler in den Request
                request.setAttribute("fehler", fehler);
                // Da es Fehler im Formular gibt stellt man dem Besucher seine
                // eingegebenen Daten zur Verfügung, damit er sie
                // überarbeiten bzw. ergänzen kann.
                request.getRequestDispatcher("/login_register.jsp")
                        .forward(request, response);
            }

        } else if (login != null) {
            ausgabe = "Sie wurden erfolgreich angemeldet und werden automatisch weitergeleitet!";
        } else if (getPassword != null) {
            ausgabe = "Ihnen wurde ein neues Passwort zugeschickt und werden automatisch weitergeleitet!";
        } else if (logout != null) {
            ausgabe = "Sie wurden erfolgreich ausgeloggt und werden automatisch auf die Startseite weitergeleitet!";
        } else {
            ausgabe = "Irgendwas wurde nicht richtig programmiert!";
        }

        // Automatisch generiert
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println(meta);
            out.println("</head>");
            out.println("<body>");
            out.println(ausgabe);
            out.println("</body>");
            out.println("</html>");
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
