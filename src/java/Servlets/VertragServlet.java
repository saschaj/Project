package Servlets;

import Entitys.Festnetzvertrag;
import Entitys.Gasvertrag;
import Entitys.Handyvertrag;
import Entitys.Kunde;
import Entitys.Stromvertrag;
import Entitys.Vertrag;
import Entitys.Zeitschriftvertrag;
import Hilfsklassen.Konstanten;
import Manager.DatenZugriffsObjekt;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Ersteller: Sascha Jungenkrüger Erstelldatum: 03.06.2015 Version: 1.1
 * Änderungen: 1.0 - Erstellung 1.1 (Sascha Jungenkrüger) - Vertrag hinzufügen
 * implementiert
 *
 * Diese Klasse übernimmt die Verarbeitung für die Suche und das Hinzufügen
 * eines Vertrag.
 */
public class VertragServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        if (request.getParameter("contract_save") != null) {
            // Methode zum Abspeichern eines Vertrags
            this.saveContract(request, response);
        } else if (request.getParameter("search") != null) {
            this.searchContract(request, response);
        }

    }

    public void saveContract(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        // Initialisierung der obligatorischen Formulardaten
        String vertragsNr = request.getParameter("vertragsNr"),
                vertragsBeginn = request.getParameter("vertragsBeginn"),
                laufzeit = request.getParameter("laufzeit"),
                laufzeitEinheit = request.getParameter("laufzeiteinheit"),
                vertragsEnde = request.getParameter("vertragsEnde"),
                kuendigungsfrist = request.getParameter("kuendigungsfrist"),
                kuendigungsfristEinheit
                = request.getParameter("kuendigungsfristeinheit"),
                kategorie = request.getParameter("cat");
        // Initialisierung der optionalen Formulardaten
        String stromNr = request.getParameter("snr"),
                stromStand = request.getParameter("sstand"),
                stromVerbrauch = request.getParameter("sverbrauch"),
                stromPreis = request.getParameter("spreisKwh"),
                gasNr = request.getParameter("gnr"),
                gasStand = request.getParameter("gstand"),
                gasVerbrauch = request.getParameter("gverbrauch"),
                gasPreis = request.getParameter("gpreisKwh"),
                festnetzTarif = request.getParameter("ftarifname"),
                festnetzEmpfang = request.getParameter("fempfangstyp"),
                handyTarif = request.getParameter("htarifname"),
                handyNetz = request.getParameter("hnetztyp"),
                handyNr = request.getParameter("hrufnummer"),
                zeitschriftName = request.getParameter("zname"),
                zeitschriftIntervall = request.getParameter("zintervall"),
                zeitschriftEinheit = request.getParameter("zeinheit"),
                zeitschriftGebiet = request.getParameter("zinteressen");

        HttpSession session = request.getSession();
        int vBeginnJahr = 0, vBeginnMonat = 0, vBeginnTag = 0,
                vEndeJahr = 0, vEndeMonat = 0, vEndeTag = 0,
                kfrist = 0;
        boolean vertragGespeichert = false;
        DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
        Calendar calBeginn = GregorianCalendar.getInstance(),
                calEnde = GregorianCalendar.getInstance();
        java.util.Date parseDate = null;
        java.sql.Date parseDateSqlvBeginn = null;
        java.sql.Date parseDateSqlvEnde = null;
        Vertrag neuerVertrag = null;
        Stromvertrag neuStromvertrag = null;
        Gasvertrag neuGasvertrag = null;
        Festnetzvertrag neuFestnetzvertrag = null;
        Handyvertrag neuHandyvertrag = null;
        Zeitschriftvertrag neuZeitschriftvertrag = null;
        String ausgabe = "", fehler[] = null;

        // Überprüfung der Vertragsnummer, ob das Feld gefüllt ist
        // , wenn ja wird überprüft ob ungültige Ziffern enthalten sind
        if (vertragsNr.equals("")) {
            ausgabe = "Bitte geben Sie eine Vertragsnummer ein.!";
        } else if (vertragsNr.matches("[\\d]+[\\d\\w-./]")) {
            ausgabe = "Ihr Vertragsnummer enthält ungültige Ziffen.!";
        }

        // Überprüfung, ob der Vertragsbeginn & die Laufzeit
        // oder nur das Vertragsende eingegeben wurde
        if (!vertragsBeginn.equals("") && !laufzeit.equals("")
                || !vertragsEnde.equals("")) {
            // Überprüfung, ob das Vertragsende gefüllt ist
            if (!vertragsEnde.equals("")) {
                // Datum aus String entnehmen und Formatierung zu DD/MM/JJJJ
                // ändern.
                vEndeJahr = Integer.parseInt(vertragsEnde.substring(6, 10));
                vEndeMonat = Integer.parseInt(vertragsEnde.substring(0, 2));
                vEndeTag = Integer.parseInt(vertragsEnde.substring(3, 5));
                calEnde.set(vEndeJahr, vEndeMonat - 1, vEndeTag, 0, 0, 0);
                parseDate = new java.util.Date(calEnde.getTimeInMillis());

                // Prüfung, ob das Vertragsende in der Vergangenheit liegt
                if (parseDate.before(Calendar.getInstance().getTime())) {
                    ausgabe = ausgabe + "Der eingegebene Vertrag ist abgelaufen.";
                } else {
                    parseDateSqlvEnde = new java.sql.Date(parseDate.getTime());
                    if (!vertragsBeginn.equals("")) {
                        vBeginnJahr = Integer.parseInt(vertragsBeginn.substring(6, 10));
                        vBeginnMonat = Integer.parseInt(vertragsBeginn.substring(0, 2));
                        vBeginnTag = Integer.parseInt(vertragsBeginn.substring(3, 5));
                        calBeginn.set(vBeginnJahr, vBeginnMonat - 1, vBeginnTag, 0, 0, 0);
                        parseDate = new java.util.Date(calBeginn.getTimeInMillis());
                        parseDateSqlvBeginn = new java.sql.Date(parseDate.getTime());
                    } else {
                        parseDateSqlvBeginn = null;
                    }
                    if (laufzeit.equals("")) {
                        laufzeit = "0";
                    }
                }

            } else if (!vertragsBeginn.equals("") && !laufzeit.equals("")) {
                // Überprüfung, ob die Laufzeit nur Ziffern enthält
                if (laufzeit.matches("[^0-9]")) {
                    ausgabe = ausgabe + ""
                            + "Bei der Laufzeit sind nur Ziffern erlaubt.!";
                } else {
                    //Formatierung des Datum von MM/DD/JJJJ zu DD/MM/JJJJ
                    vBeginnJahr = Integer.parseInt(vertragsBeginn.substring(6, 10));
                    vBeginnMonat = Integer.parseInt(vertragsBeginn.substring(0, 2));
                    vBeginnTag = Integer.parseInt(vertragsBeginn.substring(3, 5));
                    calBeginn.set(vBeginnJahr, vBeginnMonat - 1, vBeginnTag, 0, 0, 0);
                    parseDate = new java.util.Date(calBeginn.getTimeInMillis());
                    parseDateSqlvBeginn = new java.sql.Date(parseDate.getTime());
                    // Kündigungsfrist als int konvertieren
                    kfrist = Integer.parseInt(kuendigungsfrist);
                    if (kuendigungsfristEinheit.equals("Tage")) {
                        vEndeJahr = 1970;
                        vEndeMonat = 1;
                        vEndeTag = 1 + kfrist;
                    } else if (kuendigungsfristEinheit.equals("Wochen")) {
                        vEndeJahr = 1970;
                        vEndeMonat = 1;
                        vEndeTag = 1;
                    } else if (kuendigungsfristEinheit.equals("Monate")) {
                        vEndeJahr = 1970;
                        vEndeMonat = 1 + kfrist;
                        vEndeTag = 1;
                    }
                    calEnde.set(vEndeJahr, vEndeMonat - 1, vEndeTag, 0, 0, 0);
                    // Erstelle das neue Datum
                    parseDate = new java.util.Date(
                            calBeginn.getTimeInMillis()
                            + calEnde.getTimeInMillis());
                    // Erstelle das sql-Date für die Datenbank
                    parseDateSqlvEnde = new java.sql.Date(parseDate.getTime());
                }
            } else {
                ausgabe = ausgabe + ""
                        + "Bitte geben Sie entweder die ein Vertragsbeginn "
                        + "& die Laufzeit oder ein Vertragsende ein.!";
            }
        } else {
            ausgabe = ausgabe + ""
                    + "Bitte geben Sie entweder die ein Vertragsbeginn "
                    + "& die Laufzeit oder ein Vertragsende ein.!";
        }

        // Überprüft, ob die Kündigungsfrist nicht leer und nur Ziffern enthält
        if (kuendigungsfrist.equals("")) {
            ausgabe = ausgabe + ""
                    + "Bitte geben Sie die vertragliche Kündigungsfrist ein.!";
        } else if (kuendigungsfrist.matches("[^0-9]")) {
            ausgabe = ausgabe + ""
                    + "Bei der Kündigungsfrist sind nur Ziffern erlaubt.!";
        }

        // Überprüft die optionalen Eingaben
        switch (kategorie) {
            case "Strom":
                if (!stromNr.equals("")
                        && stromNr.matches("[\\d]+?[\\d\\w-./]")) {
                    ausgabe = "Ihr Stromnummer enthält ungültige Zeichen & Ziffen.!";
                }
                if (!stromStand.equals("")
                        && !stromStand.matches("[\\d]+")) {
                    ausgabe = ausgabe + ""
                            + "Beim Stromstand sind nur Ziffern erlaubt.!";
                }
                if (!stromVerbrauch.equals("")
                        && !stromVerbrauch.matches("[\\d]+")) {
                    ausgabe = ausgabe + ""
                            + "Beim Stromverbrauch sind nur Ziffern erlaubt.!";
                }
                if (!stromPreis.equals("")
                        && stromPreis.matches("[\\d]+[,][\\d]+")) {
                    ausgabe = ausgabe
                            + "Beim Strompreis sind nur ganze Zahlen "
                            + "oder Fließkommazahlen erlaubt.!";
                }
                break;
            case "Gas":
                if (!gasNr.equals("")
                        && gasNr.matches("[\\d]+?[\\d\\w-./]")) {
                    ausgabe = "Ihr Gasnummer enthält ungültige Zeichen & Ziffen.!";
                }
                if (!gasStand.equals("")
                        && !gasStand.matches("[\\d]+")) {
                    ausgabe = ausgabe + ""
                            + "Beim Stromstand sind nur Ziffern erlaubt.!";
                }
                if (!gasVerbrauch.equals("")
                        && !gasVerbrauch.matches("[\\d]+")) {
                    ausgabe = ausgabe + ""
                            + "Beim Stromverbrauch sind nur Ziffern erlaubt.!";
                }
                if (!gasPreis.equals("")
                        && gasPreis.matches("[\\d]+[,][\\d]+")) {
                    ausgabe = ausgabe
                            + "Beim Strompreis sind nur ganze Zahlen "
                            + "oder Fließkommazahlen erlaubt.!";
                }
                break;
            case "Festnetz/DSL":
                break;
            case "Handy":
                if (!handyNr.equals("")
                        && handyNr.matches("[01]{2}[5-7][1-9]{1}[\\d]{6,}")) {
                    ausgabe = ausgabe + ""
                            + "Die Handynummer enthält ungültige Ziffern.!";
                }
                break;
            case "Zeitschriften":
                if (!zeitschriftIntervall.equals("")
                        && zeitschriftIntervall.matches("[\\d]")) {
                    ausgabe = ausgabe + ""
                            + "Der Zeitschriftenintervall akzeptiert "
                            + "nur Ziffern.!";
                }
                break;
        }

        // Wenn die nicht im Ausgabestring steht, sind die Eingaben korrekt
        // und es kann fortgesetzt werden
        if (ausgabe.equals("")) {

            // Überprüfung, welche Kategorie gewählt wurde
            switch (kategorie) {
                case "Strom":
                    // Vertragsdaten setzen
                    neuStromvertrag = new Stromvertrag();
                    neuStromvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuStromvertrag.setVertragNr(vertragsNr);
                    neuStromvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuStromvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuStromvertrag.setLaufzeitEinheit(laufzeitEinheit);
                    neuStromvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuStromvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuStromvertrag.setKuendigungsfristEinheit(kuendigungsfristEinheit);
                    neuStromvertrag.setStromzaehlerNr(stromNr);
                    neuStromvertrag.setStromzaehlerStand(Integer.parseInt(stromStand));
                    neuStromvertrag.setVerbrauchProJahr(Integer.parseInt(stromVerbrauch));
                    neuStromvertrag.setPreisProKwh(Float.parseFloat(stromPreis));

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuStromvertrag);
                    break;
                case "Gas":
                    // Vertragsdaten setzen
                    neuGasvertrag = new Gasvertrag();
                    neuGasvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuGasvertrag.setVertragNr(vertragsNr);
                    neuGasvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuGasvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuGasvertrag.setLaufzeitEinheit(laufzeitEinheit);
                    neuGasvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuGasvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuGasvertrag.setKuendigungsfristEinheit(kuendigungsfristEinheit);
                    neuGasvertrag.setGaszaehlerNr(gasNr);
                    neuGasvertrag.setGaszaehlerStand(Integer.parseInt(gasStand));
                    neuGasvertrag.setVerbrauchProJahr(Integer.parseInt(gasVerbrauch));
//                    neuGasvertrag.setPreisProKhw(Float.parseFloat(gasPreis));

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
//                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuGasvertrag);
                    break;
                case "Festnetz/DSL":
                    // Vertragsdaten setzen
                    neuFestnetzvertrag = new Festnetzvertrag();
                    neuFestnetzvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuFestnetzvertrag.setVertragNr(vertragsNr);
                    neuFestnetzvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuFestnetzvertrag.setVertragBeginn(parseDate);
                    neuFestnetzvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuFestnetzvertrag.setLaufzeitEinheit(laufzeitEinheit);
                    neuFestnetzvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuFestnetzvertrag.setVertragEnde(parseDate);
                    neuFestnetzvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuFestnetzvertrag.setKuendigungsfristEinheit(kuendigungsfristEinheit);
                    neuFestnetzvertrag.setTarifname(festnetzTarif);
                    neuFestnetzvertrag.setEmpfangstyp(festnetzEmpfang);

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuFestnetzvertrag);
                    break;
                case "Handy":
                    // Vertragsdaten setzen
                    neuHandyvertrag = new Handyvertrag();
                    neuHandyvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuHandyvertrag.setVertragNr(vertragsNr);
                    neuHandyvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuHandyvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuHandyvertrag.setLaufzeitEinheit(laufzeitEinheit);
                    neuHandyvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuHandyvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuHandyvertrag.setKuendigungsfristEinheit(kuendigungsfristEinheit);
                    neuHandyvertrag.setTarifname(handyTarif);
                    neuHandyvertrag.setNetztyp(handyNetz);
                    neuHandyvertrag.setRufnummer(handyNr);

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuHandyvertrag);
                    break;
                case "Zeitschriften":
                    // Vertragsdaten setzen
                    neuZeitschriftvertrag = new Zeitschriftvertrag();
                    neuZeitschriftvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuZeitschriftvertrag.setVertragNr(vertragsNr);
                    neuZeitschriftvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuZeitschriftvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuZeitschriftvertrag.setLaufzeitEinheit(laufzeitEinheit);
                    neuZeitschriftvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuZeitschriftvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuZeitschriftvertrag.setKuendigungsfristEinheit(kuendigungsfristEinheit);
                    neuZeitschriftvertrag.setZeitschriftName(zeitschriftName);
//                    neuZeitschriftvertrag.setLieferintervall(Integer.parseInt(zeitschriftIntervall));
                    neuZeitschriftvertrag.setInteressengebiet(zeitschriftGebiet);

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
//                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuZeitschriftvertrag);
                    break;
            }
            if (vertragGespeichert) {
                ausgabe = "Ihr Vertrag wurde angelegt!";
                request.setAttribute(Konstanten.REQUEST_ATTR_ERFOLG, ausgabe);
                request.setAttribute("check", null);
                request.setAttribute("cat", null);
                request.getRequestDispatcher("/user.jsp").forward(request, response);
            } else {
                ausgabe = "Vertrag konnte nicht gespeichert werden. (Ausklammern des Zugriffs der DAO)";
                request.setAttribute(Konstanten.REQUEST_ATTR_ERFOLG, ausgabe);
                request.setAttribute("check", request.getParameter("check"));
                request.setAttribute("cat", request.getParameter("cat"));
                request.getRequestDispatcher("/user.jsp").forward(request, response);
            }
        } else {
            // Fehler in den obligatorischen Feldern
            fehler = ausgabe.split("!");
            request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, fehler);
            request.setAttribute("check", request.getParameter("check"));
            request.setAttribute("cat", request.getParameter("cat"));
            request.getRequestDispatcher("/user.jsp").forward(request, response);
        }
    }

    public void searchContract(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        HttpSession session = request.getSession();
        Collection<Vertrag> vertraege = null;
        String kategorie = request.getParameter("search");
        String suchText = request.getParameter("suchText");

        if (suchText != null) {
            if(!suchText.equals("")) {
                vertraege = new DatenZugriffsObjekt().searchContract(suchText, (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
            
                if (vertraege.isEmpty()) {
                    vertraege = null;
                }
            } else {
                vertraege = null;
            }
            
            request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, "Bitten füllen sie das Suchfeld, damit Ihnen bei erfolgreicher Suche Verträge angezeigt werden.");
            request.setAttribute("vertraege", vertraege);
            request.setAttribute("kategorie", null);
            request.setAttribute("suchText", suchText);
            request.getRequestDispatcher("/user.jsp").forward(request, response);
        } else if (kategorie != null) {
            vertraege = new DatenZugriffsObjekt().searchContractCategory(kategorie, (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
            
            if (!vertraege.isEmpty()) {
                request.setAttribute("vertraege", vertraege);
            } else {
                request.setAttribute("vertraege", null);
            }
            request.setAttribute("kategorie", kategorie);
            request.setAttribute("suchText", null);
            request.getRequestDispatcher("/user.jsp").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(VertragServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(VertragServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
