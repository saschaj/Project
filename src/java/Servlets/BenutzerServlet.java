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
 *                  
 */
package Servlets;

import Entitys.Adresse;
import Entitys.Benutzer;
import Entitys.Kunde;
import Hilfsklassen.Konstanten;
import Manager.DatenZugriffsObjekt;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BenutzerServlet extends HttpServlet {

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
        DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
        Kunde k = (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        Benutzer ben =(Benutzer) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        // Formulardaten in Variablen speichern
        String vname = request.getParameter("acc_vname");
        String name = request.getParameter("acc_name");
        String tel= request.getParameter("acc_tel");
        String strasse = request.getParameter("acc_strasse");
        String hnr = request.getParameter("acc_hnr");
        String plz = request.getParameter("acc_plz");
        String ort = request.getParameter("acc_ort");
        String land = request.getParameter("acc_land");
        String gebdatum = request.getParameter("acc_gebdat");
        String email1 = request.getParameter("e-mail");
        String email2 = request.getParameter("e-mail_n");
        String pw1 = request.getParameter("pw");
        String pw2 = request.getParameter("pw_n");
        
        //Daten von Adresse speichern.
         Adresse adr = new Adresse();
        adr.setHausNr(hnr);
        adr.setLand(land);
        adr.setOrt(ort);
        adr.setPlz(plz);
        adr.setStrasse(strasse);

        // variable für die Speicherung von Fehlern.
        String fehler[];

        //Hilfsvariable fÃ¼r die Ausgabe
        String ausgabe = "", meta = "";
        
        int kId = k.getBenutzerId();
        int bId = ben.getBenutzerId();

        // Ueberpruefung, ob der Vorname leer ist
        if (vname.equals("")) {
            // Fehlermeldung in Variable ausgabe gespeichert
            ausgabe = "Bitte geben Sie Ihren Vornamen ein!";

        } else if (!vname.matches("[A-Za-z]{1,30}")) {
            ausgabe += "Vorname ist nicht konform!";
        }
        
        if (name.equals("")) {
            // Fehlermeldung in Variable ausgabe gespeichert
            ausgabe = ausgabe + "\n Bitte geben Sie Ihren Nachnamen ein!";

        } else if (!name.matches("[A-Za-z]{1,30}")) {
            ausgabe += "Name ist nicht konform!";
        }
        
       
         // Ueberpruefung ob die Telefonummer konform ist. 
        if (!tel.equals("")) {
           if (!tel.matches("[0-9]{5,12}")) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe+="Telefonnummer ist nicht konform!";
        }
        }
        // Ueberpruefung ob die Strasse konform ist.
        if (!strasse.equals("")) {
            if (!strasse.matches("[A-Za-z]{1,30}")) {
                ausgabe += "Strasse ist nicht konform!";
            }
        }
        // Ueberpruefung ob der Hausnummer konform ist.
        if (!hnr.equals("")) {
            if (!hnr.matches("[0-9]{1,30}")) {
                ausgabe += "Hausnummer ist nicht konform!";
            }
        }
        // Ueberpruefung ob der Plz konform ist.
        if (!plz.equals("")) {
            if (!plz.matches("[0-9]{5,5}")) {
                ausgabe += "Postleitzahl ist nicht konform!";
            }
        }

       
        // Ueberpruefung ob der Ort konform ist.
        if (!ort.equals("")) {
            if (!ort.matches("[A-Za-z]{1,30}")) {
                ausgabe += "Ort ist nicht konform!";
            }
        }

          // Ueberpruefung ob das Land konform ist.
        if (!land.equals("")) {
            if (!land.matches("[A-Za-z]{1,30}")) {
                ausgabe += "Land ist nicht konform!";
            }
        }
        

        // Überprüfung, ob die E-Mail-Adresse leer ist
        if (!email1.equals("") && !email2.equals("")) {
            // Überprüfung, ob die E-Mail-Adressen gleich sind
            if (!email1.equals(email2)) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe + "\n E-Mail-Adressen stimmen nicht überein!";
            } // Überprüfung, ob die E-Mail-Adressen konform sind
            else if (!email1.matches("[a-zA-Z0-9].+@[a-zA-Z0-9\\.-]+[a-zA-Z]{2,4}")) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe
                        + "\n E-Mail-Adresse ist nicht konform!";
            }
        }

        // Überprüfung, ob die Passwörter nicht leer sind
        if (!pw1.equals("") && !pw2.equals("")) {
            // Überprüfung, ob die Passwörter gleich sind
            if (!pw1.equals(pw2)) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe
                        + "\n Passwörter stimmen nicht überein!";
            } // Überprüfung, ob das Passwort weniger als 6 Zeichen hat
            else if (pw1.length() < 6) {
                // Fehlermeldung in Variable ausgabe gespeichert
                ausgabe = ausgabe
                        + "\n Das Passwort muss länger als 6 Zeichen sein!";
            }
        }
        if (!ausgabe.equals("")) {
                // Fehlermeldung wird gesplittet und im Array gespeichert
            // und auf der user_account.jsp ausgegeben.
            fehler = ausgabe.split("!");
            // Setzen der Fehler in den Request                    
            request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, fehler);
            request.getRequestDispatcher("/user_account.jsp").forward(request, response);
        } else {
            dao.updateBenutzerDaten(email1, pw1, bId);
//             dao.updateKundeDaten(vname, name, adr, null, name, kId);
                 ausgabe = "Kundendten und Benutzerdaten wurden erfolgreich aktualisiert";
             
            //Benutzerobjekt und KundeObjekt in Session laden
//           session.setAttribute(Konstanten.SESSION_ATTR_BENUTZER, k);
           
            meta = "<meta http-equiv='refresh' content='2; URL=index.jsp'>";
            
            session.invalidate();

            response.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet KBenutzerServlet</title>");
                out.println(meta);
                out.println("</head>");
                out.println("<body>");
                out.println(ausgabe);
                out.println("</body>");
                out.println("</html>");
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
