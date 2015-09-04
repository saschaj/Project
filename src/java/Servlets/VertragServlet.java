package Servlets;

import Entitys.Benutzer;
import Entitys.Festnetzvertrag;
import Entitys.Gasvertrag;
import Entitys.Handyvertrag;
import Entitys.Interessengebiet;
import Entitys.Kunde;
import Entitys.Netztyp;
import Entitys.Stromvertrag;
import Entitys.Vertrag;
import Entitys.Vertrag_Art;
import Entitys.Zeit_Einheit;
import Entitys.Zeitschriftvertrag;
import Hilfsklassen.Konstanten;
import Manager.DatenZugriffsObjekt;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Ersteller:   Sascha Jungenkrüger 
 * Datum:       03.06.2015 
 * Version:     1.1
 * Änderungen:  1.0 - Erstellung 
 *              1.1 (Sascha Jungenkrüger) 
 *                  - Vertrag hinzufügen implementiert
 *                  - Vertrag ändern implementiert
 *
 * Diese Klasse übernimmt die Verarbeitung für die Suche und das Hinzufügen
 * eines Vertrag.
 */
public class VertragServlet extends HttpServlet {

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       04.06.2015 
     * Version:     1.0 Sascha Jungenkrüger 
     *              1.1 Sascha Jungenkrüger 10.06.2015 
     *                  - Vertrag anlegen eingefügt
     *                  - Vertrag ändern eingefügt
     *              1.2 Sascha Jungenkrüger 25.07.2015 
     *                  - Vertrag löschen eingefügt
     *              1.3 Sascha Jungenkrüger 20.08.2015
     *                  - Vertrag verlängern eingefügt
     * 
     * Methode dient zur Überprüfung, welche Aktion ausgeführt wird wenn 
     * man auf einen Button in der Vertragsverwaltung klickt.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        
        //Zeichensatz des Request-Objektes auf "UTF-8" setzen
	//Ermöglicht die korrekte Verwendung von Umlauten
	request.setCharacterEncoding("UTF-8");
        // Überprüfung, welcher Button geklickt wurde
        if ((Benutzer)request.getSession().getAttribute(Konstanten.SESSION_ATTR_BENUTZER) == null) {
            request.getRequestDispatcher("/index.jsp").
                    forward(request, response);
        } else if (request.getParameter("add") != null)  {
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        }else if (request.getParameter("contract_save") != null
                || request.getParameter("anlegen") != null
                && request.getParameter("anlegen").equals("1")) {
            // Methode zum Abspeichern eines Vertrags
            this.speichereVertrag(request, response);
        } else if (request.getParameter("cat") != null) {
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        } else if (request.getParameter("search") != null) {
            // Methode zum Suchen eines Vertrags
            this.sucheVertrag(request, response);
        } else if (request.getParameter("contract_change") != null) {
            // Methode zum Ändern eines Vertrags
            this.aendereVertrag(request, response);
        } else if (request.getParameter("contract_extend") != null) {
            request.setAttribute("verlaengern", true);
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        } else if (request.getParameter("verlaengern") != null) {
            this.verlaengereVertrag(request, response);
        } else if (request.getParameter("aendern") != null) {
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        } else if (request.getParameter("back_change") != null) {
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        } else if (request.getParameter("loeschen") != null) {
            // Methode zum Löschen eines Vertrags
            this.loescheVertrag(request, response);
        } else if (request.getParameter("back") != null) {
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        }
    }

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       04.06.2015 
     * Version:     1.0 Sascha Jungenkrüger 
     *              1.1 Sascha Jungenkrüger 15.06.2015 
     *                  - Datepicker eingefügt und angepasst 
     *              1.2 Sascha Jungenkrüger 02.08.2015 
     *                  - Datumsberechnung überarbeitet 
     *                  - Obligatorische Daten eingefügt (Vertragsbezeichnung)
     *
     * @param request Http request-Objekt
     * @param response Http response-Objekt
     * @throws ServletException
     * @throws IOException
     * @throws ParseException
     */
    public void speichereVertrag(HttpServletRequest request, HttpServletResponse response)
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
        String kundennr = request.getParameter("kundennr"),
                vertragsBez = request.getParameter("vertragsbez"),
                vertragsPartner = request.getParameter("vertragspartner"),
                benachrichtigungsfrist
                = request.getParameter("benachrichtigungsfrist"),
                benachrichtigungsfristEinheit
                = request.getParameter("benachrichtigungsfristeinheit"),
                stromNr = request.getParameter("snr"),
                stromStand = request.getParameter("sstand"),
                stromVerbrauch = request.getParameter("sverbrauch"),
                stromPreis = request.getParameter("spreisKwh"),
                stromPersonen = request.getParameter("sanzPers"),
                stromGrundPreis = request.getParameter("sPreisMonat"),
                gasNr = request.getParameter("gnr"),
                gasStand = request.getParameter("gstand"),
                gasVerbrauch = request.getParameter("gverbrauch"),
                gasPreis = request.getParameter("gpreisKwh"),
                gasFlaeche = request.getParameter("gflaeche"),
                gasPreisMonat = request.getParameter("gPreisMonat"),
                festnetzTarif = request.getParameter("ftarifname"),
                festnetzEmpfang = request.getParameter("fempfangstyp"),
                festnetzIstISDN = request.getParameter("fistISDN"),
                festnetzIstVOIP = request.getParameter("fistVOIP"),
                festnetzPreisMonat = request.getParameter("fPreisMonat"),
                handyTarif = request.getParameter("htarifname"),
                handyNetz = request.getParameter("hnetztyp"),
                handyNr = request.getParameter("hrufnummer"),
                zeitschriftName = request.getParameter("zname"),
                zeitschriftIntervall = request.getParameter("zintervall"),
                zeitschriftEinheit = request.getParameter("zeinheit"),
                zeitschriftGebiet = request.getParameter("zinteressen");

        // Initialsierung der Hilfsvaribalen & Objektes
        DatenZugriffsObjekt dao = null;
        HttpSession session = request.getSession();
        int vEndeJahr = 0, vEndeMonat = 0, vEndeTag = 0, vLaufzeit = 0;
        boolean vertragGespeichert = false;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calBeginn = GregorianCalendar.getInstance(),
                calEnde = GregorianCalendar.getInstance();
        java.util.Date parseDate = null;
        java.sql.Date parseDateSqlvBeginn = null;
        java.sql.Date parseDateSqlvEnde = null;
        Stromvertrag neuStromvertrag = null;
        Gasvertrag neuGasvertrag = null;
        Festnetzvertrag neuFestnetzvertrag = null;
        Handyvertrag neuHandyvertrag = null;
        Zeitschriftvertrag neuZeitschriftvertrag = null;
        Vertrag neuerVertrag = new Vertrag();
        Kunde k = (Kunde) 
                session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        Collection<Vertrag> alleVertraege = null;
        Vertrag_Art art = null;
        String ausgabe = "", fehler[] = null;

        // Überprüfung der Vertragsbezeichnung, ob es gefüllt ist
        // Der Text wird innerhalb der Suche angezeigt
        if (vertragsBez.equals("")) {
            ausgabe = "Bitte geben Sie eine Vertragsbezeichnung ein.!";
        }

        // Überprüfung der Vertragsnummer, ob das Feld gefüllt ist
        // , wenn ja wird überprüft ob ungültige Ziffern enthalten sind
        if (vertragsNr.equals("")) {
            ausgabe = ausgabe + "Bitte geben Sie eine Vertragsnummer ein.!";
        }

        // Überprüfung, ob der Vertragsbeginn & die Laufzeit
        // oder nur das Vertragsende eingegeben wurde
        if (!vertragsBeginn.equals("") && !laufzeit.equals("")
                || !vertragsEnde.equals("")) {
            // Überprüfung, ob das Vertragsende gefüllt ist
            if (!vertragsBeginn.equals("") && !vertragsEnde.equals("")) {
                // Übergebene Vertragsende als Date parsen
                parseDate = format.parse(vertragsEnde);

                // Prüfung, ob das Vertragsende in der Vergangenheit liegt
                if (parseDate.before(Calendar.getInstance().getTime())) {
                    ausgabe = ausgabe + "Der eingegebene "
                            + "Vertrag ist abgelaufen.";
                } else {
                    parseDateSqlvEnde = new java.sql.Date(parseDate.getTime());
                }
                // Den eingegebenen Vertragsbeginn parsen und
                // in Date Objekt speichern
                parseDate = format.parse(vertragsBeginn);
                parseDateSqlvBeginn = new java.sql.Date(parseDate.getTime());

            } else if (!vertragsBeginn.equals("") && !laufzeit.equals("")) {
                // Wenn nur Vertragsbegonn & Laufzeit eingetragen wurden
                // Übergebene Vertragsbeginn als Date parsen
                parseDate = format.parse(vertragsBeginn);
                parseDateSqlvBeginn = new java.sql.Date(parseDate.getTime());
                // Kündigungsfrist als int konvertieren
                vLaufzeit = Integer.parseInt(laufzeit);
                if (laufzeitEinheit.equals("Tag(e)")) {
                    vEndeJahr = 1970;
                    vEndeMonat = 1;
                    vEndeTag = 1 + vLaufzeit;
                } else if (laufzeitEinheit.equals("Woche(n)")) {
                    vEndeJahr = 1970;
                    vEndeMonat = 1;
                    vEndeTag = 1 + (vLaufzeit * 7);
                } else if (laufzeitEinheit.equals("Monat(e)")) {
                    vEndeJahr = 1970;
                    vEndeMonat = 1 + vLaufzeit;
                    vEndeTag = 1;
                } else if (laufzeitEinheit.equals("Jahr(e)")) {
                    vEndeJahr = 1970 + vLaufzeit;
                    vEndeMonat = 1;
                    vEndeTag = 1;
                }
                calBeginn.setTimeInMillis(parseDate.getTime());
                calEnde.set(vEndeJahr, vEndeMonat - 1, vEndeTag, 0, 0, 0);
                // Erstelle das neue Datum
                parseDate = new java.util.Date(
                        calBeginn.getTimeInMillis()
                        + calEnde.getTimeInMillis());
                // Erstelle das sql-Date für die Datenbank
                parseDateSqlvEnde = new java.sql.Date(parseDate.getTime());
            } else if (!vertragsEnde.equals("")) {
                // Übergebene Vertragsende als Date parsen
                parseDate = format.parse(vertragsEnde);
                // Erstelle das sql-Date für die Datenbank
                parseDateSqlvEnde = new java.sql.Date(parseDate.getTime());
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
        }

        // Überprüft, ob die Benachrichtigungsfrist nur Ziffern enthält
        if (!benachrichtigungsfrist.equals("")
                && benachrichtigungsfrist.matches("[^0-9]")) {
            ausgabe = ausgabe
                    + "Bei der Benachrichtigungsfrist "
                    + "sind nur Ziffern erlaubt.!";
        }
        // Wenn nichts im Ausgabestring steht, sind die Eingaben korrekt
        // und es kann fortgesetzt werden
        if (ausgabe.equals("")) {
            dao = new DatenZugriffsObjekt();
            
            // Übeprüfung, welche Vertragsart gewählt wurde und
            // demnentsprechend wird ein abgeleitetes Vertragsobjekt erzeugt 
            // und mit den Formulardaten gefüllt
            // Im Anschluss wird das geänderte Vertrag mittels DAO in der 
            // Datenbank überschrieben
            switch (kategorie) {
                case "Strom":
                    // Vertragsdaten setzen
                    neuStromvertrag = new Stromvertrag();
                    neuStromvertrag.setVertragArt(
                            new DatenZugriffsObjekt().getVertragsArt(
                                    Konstanten.ID_VERTRAG_ART_STROM));
                    neuStromvertrag.setKunde((Kunde) 
                            session.getAttribute(
                                    Konstanten.SESSION_ATTR_BENUTZER));
                    neuStromvertrag.setVertragNr(vertragsNr);
                    neuStromvertrag.setVertragStatus(
                            new DatenZugriffsObjekt().getVertragsStatus(
                                    Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuStromvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    if (laufzeit.equals("")) {
                        neuStromvertrag.setLaufzeit(0);
                    } else {
                        neuStromvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    }
                    neuStromvertrag.setLaufzeitEinheit(
                            getZeitEinheit(laufzeitEinheit));
                    neuStromvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuStromvertrag.setKuendigungsfrist(
                            Integer.parseInt(kuendigungsfrist));
                    neuStromvertrag.setKuendigungsfristEinheit(
                            getZeitEinheit(kuendigungsfristEinheit));
                    neuStromvertrag.setIstGeloescht(false);
                    neuStromvertrag.setKundenNr(kundennr);
                    neuStromvertrag.setVertragsBezeichnung(
                            aenderUmlaute(vertragsBez));
                    neuStromvertrag.setVertragsPartner(
                            aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuStromvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuStromvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuStromvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuStromvertrag.setStromzaehlerNr(stromNr);
                    if (stromStand.equals("")) {
                        neuStromvertrag.setStromzaehlerStand(0);
                    } else {
                        neuStromvertrag.setStromzaehlerStand(
                                Integer.parseInt(stromStand));
                    }
                    if (stromVerbrauch.equals("")) {
                        neuStromvertrag.setVerbrauchProJahr(0);
                    } else {
                        neuStromvertrag.setVerbrauchProJahr(
                                Integer.parseInt(stromVerbrauch));
                    }
                    if (stromPreis.equals("")) {
                        neuStromvertrag.setPreisProKwh(0);
                    } else {
                        neuStromvertrag.setPreisProKwh(
                                Float.parseFloat(stromPreis.replace(',', '.')));
                    }
                    if (stromPersonen.equals("")) {
                        neuStromvertrag.setAnzPersonenHaushalt(0);
                    } else {
                        neuStromvertrag.setAnzPersonenHaushalt(
                                Integer.parseInt(stromPersonen));
                    }
                    if (stromGrundPreis.equals("")) {
                        neuStromvertrag.setGrundpreisMonat(0);
                    } else {
                        neuStromvertrag.setGrundpreisMonat(
                                Float.parseFloat(
                                        stromGrundPreis.replace(',', '.')));
                    }

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = dao.addContract(neuStromvertrag);
                    neuerVertrag = neuStromvertrag;
                    break;
                case "Gas":
                    // Vertragsdaten setzen
                    neuGasvertrag = new Gasvertrag();
                    neuGasvertrag.setVertragArt((Vertrag_Art) 
                            new DatenZugriffsObjekt().getVertragsArt(
                                    Konstanten.ID_VERTRAG_ART_GAS));
                    neuGasvertrag.setKunde((Kunde) session.getAttribute(
                            Konstanten.SESSION_ATTR_BENUTZER));
                    neuGasvertrag.setVertragNr(vertragsNr);
                    neuGasvertrag.setVertragStatus(
                            new DatenZugriffsObjekt().getVertragsStatus(
                                    Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuGasvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    if (laufzeit.equals("")) {
                        neuGasvertrag.setLaufzeit(0);
                    } else {
                        neuGasvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    }
                    neuGasvertrag.setLaufzeitEinheit(
                            getZeitEinheit(laufzeitEinheit));
                    neuGasvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuGasvertrag.setKuendigungsfrist(
                            Integer.parseInt(kuendigungsfrist));
                    neuGasvertrag.setKuendigungsfristEinheit(
                            getZeitEinheit(kuendigungsfristEinheit));
                    neuGasvertrag.setIstGeloescht(false);
                    neuGasvertrag.setKundenNr(kundennr);
                    neuGasvertrag.setVertragsBezeichnung(
                            aenderUmlaute(vertragsBez));
                    neuGasvertrag.setVertragsPartner(
                            aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuGasvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuGasvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuGasvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuGasvertrag.setGaszaehlerNr(gasNr);
                    if (gasStand.equals("")) {
                        neuGasvertrag.setGaszaehlerStand(0);
                    } else {
                        neuGasvertrag.setGaszaehlerStand(
                                Integer.parseInt(gasStand));
                    }
                    if (gasVerbrauch.equals("")) {
                        neuGasvertrag.setVerbrauchProJahr(0);
                    } else {
                        neuGasvertrag.setVerbrauchProJahr(
                                Integer.parseInt(gasVerbrauch));
                    }
                    if (gasPreis.equals("")) {
                        neuGasvertrag.setPreisProKhw(0);
                    } else {
                        neuGasvertrag.setPreisProKhw(
                                Float.parseFloat(gasPreis.replace(',', '.')));
                    }
                    if (gasFlaeche.equals("")) {
                        neuGasvertrag.setVerbrauchsFlaeche(0);
                    } else {
                        neuGasvertrag.setVerbrauchsFlaeche(
                                Float.parseFloat(gasFlaeche.replace(',', '.')));
                    }
                    if (gasPreisMonat.equals("")) {
                        neuGasvertrag.setGrundpreisMonat(0);
                    } else {
                        neuGasvertrag.setGrundpreisMonat(
                                Float.parseFloat(
                                        gasPreisMonat.replace(',', '.')));
                    }

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = dao.addContract(neuGasvertrag);
                    neuerVertrag = neuGasvertrag;
                    break;
                case "Festnetz/DSL":
                    // Vertragsdaten setzen
                    neuFestnetzvertrag = new Festnetzvertrag();
                    neuFestnetzvertrag.setVertragArt(
                            new DatenZugriffsObjekt().getVertragsArt(
                                    Konstanten.ID_VERTRAG_ART_FESTNETZ));
                    neuFestnetzvertrag.setKunde((Kunde) session.getAttribute(
                            Konstanten.SESSION_ATTR_BENUTZER));
                    neuFestnetzvertrag.setVertragNr(vertragsNr);
                    neuFestnetzvertrag.setVertragStatus(
                            new DatenZugriffsObjekt().getVertragsStatus(
                                    Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuFestnetzvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuFestnetzvertrag.setVertragBeginn(parseDate);
                    if (laufzeit.equals("")) {
                        neuFestnetzvertrag.setLaufzeit(0);
                    } else {
                        neuFestnetzvertrag.setLaufzeit(
                                Integer.parseInt(laufzeit));
                    }
                    neuFestnetzvertrag.setLaufzeitEinheit(
                            getZeitEinheit(laufzeitEinheit));
                    neuFestnetzvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuFestnetzvertrag.setVertragEnde(parseDate);
                    neuFestnetzvertrag.setKuendigungsfrist(
                            Integer.parseInt(kuendigungsfrist));
                    neuFestnetzvertrag.setKuendigungsfristEinheit(
                            getZeitEinheit(kuendigungsfristEinheit));
                    neuFestnetzvertrag.setIstGeloescht(false);
                    neuFestnetzvertrag.setKundenNr(kundennr);
                    neuFestnetzvertrag.setVertragsBezeichnung(
                            aenderUmlaute(vertragsBez));
                    neuFestnetzvertrag.setVertragsPartner(
                            aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuFestnetzvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuFestnetzvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuFestnetzvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuFestnetzvertrag.setTarifname(festnetzTarif);
                    neuFestnetzvertrag.setNetztypp(getNetztyp(festnetzEmpfang));
                    if (festnetzIstISDN == null) {
                        neuFestnetzvertrag.setIstISDN(false);
                    } else {
                        neuFestnetzvertrag.setIstISDN(true);
                    }
                    if (festnetzIstVOIP == null) {
                        neuFestnetzvertrag.setIstVOIP(false);
                    } else {
                        neuFestnetzvertrag.setIstVOIP(true);
                    }

                    if (festnetzPreisMonat.equals("")) {
                        neuFestnetzvertrag.setGrundpreisMonat(0);
                    } else {
                        neuFestnetzvertrag.setGrundpreisMonat(
                                Float.parseFloat(festnetzPreisMonat));
                    }

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = dao.addContract(neuFestnetzvertrag);
                    neuerVertrag = neuFestnetzvertrag;
                    break;
                case "Handy":
                    // Vertragsdaten setzen
                    neuHandyvertrag = new Handyvertrag();
                    neuHandyvertrag.setVertragArt(
                            new DatenZugriffsObjekt().getVertragsArt(
                                    Konstanten.ID_VERTRAG_ART_HANDY));
                    neuHandyvertrag.setKunde((Kunde) session.getAttribute(
                            Konstanten.SESSION_ATTR_BENUTZER));
                    neuHandyvertrag.setVertragNr(vertragsNr);
                    neuHandyvertrag.setVertragStatus(
                            new DatenZugriffsObjekt().getVertragsStatus(
                                    Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuHandyvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    if (laufzeit.equals("")) {
                        neuHandyvertrag.setLaufzeit(0);
                    } else {
                        neuHandyvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    }
                    neuHandyvertrag.setLaufzeitEinheit(
                            getZeitEinheit(laufzeitEinheit));
                    neuHandyvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuHandyvertrag.setKuendigungsfrist(
                            Integer.parseInt(kuendigungsfrist));
                    neuHandyvertrag.setKuendigungsfristEinheit(
                            getZeitEinheit(kuendigungsfristEinheit));
                    neuHandyvertrag.setIstGeloescht(false);
                    neuHandyvertrag.setKundenNr(kundennr);
                    neuHandyvertrag.setVertragsBezeichnung(
                            aenderUmlaute(vertragsBez));
                    neuHandyvertrag.setVertragsPartner(
                            aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuHandyvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuHandyvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuHandyvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuHandyvertrag.setTarifname(handyTarif);
                    neuHandyvertrag.setNetztyp(getNetztyp(handyNetz));
                    neuHandyvertrag.setRufnummer(handyNr);

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = dao.addContract(neuHandyvertrag);
                    neuerVertrag = neuHandyvertrag;
                    break;
                case "Zeitschriften":
                    // Vertragsdaten setzen
                    neuZeitschriftvertrag = new Zeitschriftvertrag();
                    neuZeitschriftvertrag.setVertragArt(
                            new DatenZugriffsObjekt().getVertragsArt(
                                    Konstanten.ID_VERTRAG_ART_ZEITSCHRIFT));
                    neuZeitschriftvertrag.setKunde((Kunde) 
                            session.getAttribute(
                                    Konstanten.SESSION_ATTR_BENUTZER));
                    neuZeitschriftvertrag.setVertragNr(vertragsNr);
                    neuZeitschriftvertrag.setVertragStatus(
                            new DatenZugriffsObjekt().getVertragsStatus(
                                    Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuZeitschriftvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    if (laufzeit.equals("")) {
                        neuZeitschriftvertrag.setLaufzeit(0);
                    } else {
                        neuZeitschriftvertrag.setLaufzeit(
                                Integer.parseInt(laufzeit));
                    }
                    neuZeitschriftvertrag.setLaufzeitEinheit(
                            getZeitEinheit(laufzeitEinheit));
                    neuZeitschriftvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuZeitschriftvertrag.setKuendigungsfrist(
                            Integer.parseInt(kuendigungsfrist));
                    neuZeitschriftvertrag.setKuendigungsfristEinheit(
                            getZeitEinheit(kuendigungsfristEinheit));
                    neuZeitschriftvertrag.setIstGeloescht(false);
                    neuZeitschriftvertrag.setKundenNr(kundennr);
                    neuZeitschriftvertrag.setVertragsBezeichnung(
                            aenderUmlaute(vertragsBez));
                    neuZeitschriftvertrag.setVertragsPartner(
                            aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuZeitschriftvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuZeitschriftvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuZeitschriftvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuZeitschriftvertrag.setZeitschriftName(zeitschriftName);
                    if (zeitschriftIntervall.equals("")) {
                        neuZeitschriftvertrag.setLieferintervall(0);
                    } else {
                        neuZeitschriftvertrag.setLieferintervall(
                                Integer.parseInt(zeitschriftIntervall));
                    }
                    neuZeitschriftvertrag.setLieferintervallEinheit(
                            getZeitEinheit(zeitschriftEinheit));
                    neuZeitschriftvertrag.setInteressengebiet(
                            getInteressengebiet(zeitschriftGebiet));

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = dao.addContract(neuZeitschriftvertrag);
                    neuerVertrag = neuZeitschriftvertrag;
                    break;
            }
            if (vertragGespeichert) {
                ausgabe = "Ihr Vertrag wurde angelegt!";
                session.setAttribute(Konstanten.SESSION_ATTR_BENUTZER, 
                        dao.aktualisiereKunde(k));
                dao.close();                
                request.setAttribute(Konstanten.REQUEST_ATTR_ERFOLG, ausgabe);
                request.setAttribute("gespeichert", "test");
                request.setAttribute("check", null);
                request.setAttribute("cat", null);
                request.getRequestDispatcher("/user.jsp").
                        forward(request, response);
            } else {
                dao.close();
                ausgabe = "Vertrag konnte nicht gespeichert werden.";
                request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, ausgabe);
                request.setAttribute("gespeichert", null);
                request.setAttribute("check", request.getParameter("check"));
                request.setAttribute("cat", request.getParameter("cat"));
                request.getRequestDispatcher("/user.jsp").
                        forward(request, response);
            }
        } else {
            // Fehler in den obligatorischen Feldern
            fehler = ausgabe.split("!");
            request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, fehler);
            request.setAttribute("gespeichert", null);
            request.setAttribute("check", request.getParameter("check"));
            request.setAttribute("cat", request.getParameter("cat"));
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        }
    }

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       12.06.2015 
     * Version:     1.0 Sascha Jungenkrüger 
     *              1.1 Sascha Jungenkrüger 15.07.2015 
     *                  - Vertragssuche über die neuen Formulardaten angepasst
     * 
     * Verträge werden über das SessionObjekt des Kunden gesucht, da der
     * Kunde eine Collection mit allen angelegten Verträgen des Kunden hat
     *
     * @param request Http request-Objekt
     * @param response Http response-Objekt
     * @throws ServletException
     * @throws IOException
     * @throws ParseException
     */
    public void sucheVertrag(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        // Initialisierung der benötigten Objekte
        HttpSession session = request.getSession();
        Kunde k = 
                (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        java.util.Date datum = null;        
        Collection<Vertrag> vertraege = new ArrayList<Vertrag>();
        String kategorie = request.getParameter("search");
        String suchText = request.getParameter("suchText");

        // Überprüfung, ob die textuelle Suche genutzt wird
        if (suchText != null) {
            // Überprüfung, ob die textuelle Suche nicht leer ist
            if (!suchText.equals("")) {
                // Überprüft die Verträge aus dem Session-Objekt
                if (k.getVertraege() != null) {
                    // Überprüfung, ob nur die Wildcard genutzt wird
                    // Wenn ja, dann soll er alle Verträge ausgeben
                    if (suchText.equals("*")) {
                        vertraege = k.getVertraege();
                    } else {
                        // Durchläuft jedes Objekt der Collection und sucht
                        // nach passenden Verträgen
                        for (Vertrag v : k.getVertraege()) {
                            // Überprüfung, ob der Suchtext mit einem Feld
                            // übereinstimmt
                            if ((v.getVertragsBezeichnung().toLowerCase()).
                                    contains(aenderUmlaute(suchText).
                                            toLowerCase())
                                    || (v.getVertragNr().toLowerCase()).
                                        contains(suchText.toLowerCase())
                                    || (v.getKundenNr().toLowerCase()).
                                        contains(suchText.toLowerCase())
                                    || (v.getVertragsPartner().toLowerCase()).
                                        contains(suchText.toLowerCase())) {
                                vertraege.add(v);
                            } else if (v.getVertragBeginn() != null 
                                    && df.format(v.getVertragBeginn())
                                        .contains(suchText)
                                    || v.getVertragEnde() != null 
                                    && df.format(v.getVertragEnde())
                                        .contains(suchText)) {
                                vertraege.add(v);
                            }
                        }
                    }
                }
                // Ist die Vertragsliste für die Session leer, dann wird
                // sie auf null gesetzt
                if (vertraege.isEmpty()) {
                    vertraege = null;
                }
            } else {
                // Textuelle Suche ist leer, somit wird die Collection auf
                // null gestzt
                vertraege = null;
            }
            // request Objekt wird mit Hilfsattributen gefüllt,
            // damit der Benutzer entweder seine Verträge bekommt oder
            // es eine Fehlermeldung gibt, wenn keine Verträge gefunden wurden
            request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, 
                    Konstanten.FEHLERTEXT_SUCHE);
            request.setAttribute(Konstanten.REQUEST_ATTR_VERTRAEGE, vertraege);
            request.setAttribute(Konstanten.REQUEST_ATTR_KATEGORIE, null);
            request.setAttribute(Konstanten.REQUEST_ATTR_VERTRAG, null);
            request.setAttribute(Konstanten.REQUEST_ATTR_SUCHTEXT, 
                    aenderUmlaute(suchText));
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        } else if (kategorie != null) {
            // Es wurde die kategorisierte Suche benutzt
            // Übeprüfung, ob die Kundenverträge im Session-Objekt null sind
            if (k.getVertraege() != null) {
                // Überprüft jedes Objekt der Collection
                for (Vertrag v : k.getVertraege()) {
                    // Überprüfung, ob die Vertragsart des Vertrags mit der
                    // angeklickten Vertragsart übereinstimmt
                    // Wenn ja, wird der Vertrag zur Collection hinzugefügt
                    if (v.getVertragArt().getName().equals(kategorie)) {
                        vertraege.add(v);
                    }
                }
            }
            // Wenn die neue Collection gefüllt ist, dann werden die Verträge
            // in die Session gespeichert, damit man sie auf der nächste Seite
            // abrufen kann
            if (!vertraege.isEmpty()) {
                request.setAttribute(
                        Konstanten.REQUEST_ATTR_VERTRAEGE, vertraege);
            } else {
                // Nicht gefüllt -> Verträge im Session-Objekt gleich null
                request.setAttribute(Konstanten.REQUEST_ATTR_VERTRAEGE, null);
            }
            request.setAttribute(Konstanten.REQUEST_ATTR_KATEGORIE, kategorie);
            request.setAttribute(Konstanten.REQUEST_ATTR_SUCHTEXT, null);
            request.setAttribute(Konstanten.REQUEST_ATTR_VERTRAG, null);
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        }
    }

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       12.08.2015 
     * Methode:     aendereVertrag
     * Version:     1.0 Sascha Jungenkrüger 
     *              1.1 Sascha Jungenkrüger 15.07.2015 
     *                  - Vertragssuche über die neuen Formulardaten angepasst 
     * 
     * @param request Http request-Objekt
     * @param response Http response-Objekt
     * @throws ServletException
     * @throws IOException
     * @throws ParseException 
     */
    public void aendereVertrag(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        // Initialisierung der obligatorischen Formulardaten
        String vertragsNr = request.getParameter("vertragsNr"),
                kategorie = request.getParameter("kategorie");
        int vertragID = Integer.parseInt(request.getParameter("vertragID"));

        // Initialisierung der optionalen Formulardaten
        String kundennr = request.getParameter("kundennr"),
                vertragsBez = request.getParameter("vertragsbez"),
                vertragsPartner = request.getParameter("vertragspartner"),
                benachrichtigungsfrist
                = request.getParameter("benachrichtigungsfrist"),
                benachrichtigungsfristEinheit
                = request.getParameter("benachrichtigungsfristeinheit"),
                stromNr = request.getParameter("snr"),
                stromStand = request.getParameter("sstand"),
                stromVerbrauch = request.getParameter("sverbrauch"),
                stromPreis = request.getParameter("spreisKwh"),
                stromPersonen = request.getParameter("sanzPers"),
                stromGrundPreis = request.getParameter("gPreisMonat"),
                gasNr = request.getParameter("gnr"),
                gasStand = request.getParameter("gstand"),
                gasVerbrauch = request.getParameter("gverbrauch"),
                gasPreis = request.getParameter("gpreisKwh"),
                gasFlaeche = request.getParameter("gflaeche"),
                festnetzTarif = request.getParameter("ftarifname"),
                festnetzEmpfang = request.getParameter("fempfangstyp"),
                festnetzIstISDN = request.getParameter("fistISDN"),
                festnetzIstVOIP = request.getParameter("fistVOIP"),
                handyTarif = request.getParameter("htarifname"),
                handyNetz = request.getParameter("hnetztyp"),
                handyNr = request.getParameter("hrufnummer"),
                zeitschriftName = request.getParameter("zname"),
                zeitschriftIntervall = request.getParameter("zintervall"),
                zeitschriftEinheit = request.getParameter("zeinheit"),
                zeitschriftGebiet = request.getParameter("zinteressen");
        // Initialsierung/Deklaration weiterer benötigte Objekte
        DatenZugriffsObjekt dao = null;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Stromvertrag neuStromvertrag = null;
        Gasvertrag neuGasvertrag = null;
        Festnetzvertrag neuFestnetzvertrag = null;
        Handyvertrag neuHandyvertrag = null;
        Zeitschriftvertrag neuZeitschriftvertrag = null;
        HttpSession session = request.getSession();
        Kunde k = 
                (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        boolean vertragAktualisiert = false;
        String[] fehler = null;
        String ausgabe = "";

        // Überprüfung der Vertragsbezeichnung, ob es gefüllt ist
        // Der Text wird innerhalb der Suche angezeigt
        if (vertragsBez.equals("")) {
            ausgabe = "Bitte geben Sie eine Vertragsbezeichnung ein.!";
        }

        // Überprüfung der Vertragsnummer, ob das Feld gefüllt ist
        // , wenn ja wird überprüft ob ungültige Ziffern enthalten sind
        if (vertragsNr.equals("")) {
            ausgabe = ausgabe + "Bitte geben Sie eine Vertragsnummer ein.!";
        }

        // Überprüfung, ob es Fehler ergab
        if (ausgabe.equals("")) {
            // Initialisierung des DatenZugriffsObjekt
            dao = new DatenZugriffsObjekt();

            // Übeprüfung, welche Vertragsart gewählt wurde und
            // demnentsprechend wird der Vertrag aus der Datenbank mittels 
            // Primärschlüssel geholt und neu gesetzt
            // Im Anschluss wird das geänderte Vertrag mittels DAO in der 
            // Datenbank überschrieben
            switch (kategorie) {
                case "Stromvertrag":
                    // Vertragsdaten setzen
                    neuStromvertrag = (Stromvertrag) 
                            new DatenZugriffsObjekt().getVertrag(vertragID);
                    neuStromvertrag.setKundenNr(kundennr);
                    neuStromvertrag.
                            setVertragsBezeichnung(aenderUmlaute(vertragsBez));
                    neuStromvertrag.
                            setVertragsPartner(aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuStromvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuStromvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuStromvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuStromvertrag.setStromzaehlerNr(stromNr);
                    if (stromStand.equals("")) {
                        neuStromvertrag.setStromzaehlerStand(0);
                    } else {
                        neuStromvertrag.setStromzaehlerStand(
                                Integer.parseInt(stromStand));
                    }
                    if (stromVerbrauch.equals("")) {
                        neuStromvertrag.setVerbrauchProJahr(0);
                    } else {
                        neuStromvertrag.setVerbrauchProJahr(
                                Integer.parseInt(stromVerbrauch));
                    }
                    if (stromPreis.equals("")) {
                        neuStromvertrag.setPreisProKwh(0);
                    } else {
                        neuStromvertrag.setPreisProKwh(
                                Float.parseFloat(stromPreis));
                    }
                    if (stromPersonen.equals("")) {
                        neuStromvertrag.setAnzPersonenHaushalt(0);
                    } else {
                        neuStromvertrag.setAnzPersonenHaushalt(
                                Integer.parseInt(stromPersonen));
                    }
                    if (stromGrundPreis.equals("")) {
                        neuStromvertrag.setGrundpreisMonat(0);
                    } else {
                        neuStromvertrag.setGrundpreisMonat(
                                Float.parseFloat(stromGrundPreis));
                    }

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragAktualisiert = dao.updateVertrag(neuStromvertrag);
                    break;
                case "Gasvertrag":
                    // Vertragsdaten setzen
                    neuGasvertrag = (Gasvertrag) 
                            new DatenZugriffsObjekt().getVertrag(vertragID);
                    neuGasvertrag.setKundenNr(kundennr);
                    neuGasvertrag.setVertragsBezeichnung(
                            aenderUmlaute(vertragsBez));
                    neuGasvertrag.setVertragsPartner(
                            aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuGasvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuGasvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuGasvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuGasvertrag.setGaszaehlerNr(gasNr);
                    if (gasStand.equals("")) {
                        neuGasvertrag.setGaszaehlerStand(0);
                    } else {
                        neuGasvertrag.setGaszaehlerStand(
                                Integer.parseInt(gasStand));
                    }
                    if (gasVerbrauch.equals("")) {
                        neuGasvertrag.setVerbrauchProJahr(0);
                    } else {
                        neuGasvertrag.setVerbrauchProJahr(
                                Integer.parseInt(gasVerbrauch));
                    }
                    if (gasPreis.equals("")) {
                        neuGasvertrag.setPreisProKhw(0);
                    } else {
                        neuGasvertrag.setPreisProKhw(
                                Float.parseFloat(gasPreis));
                    }
                    if (gasFlaeche.equals("")) {
                        neuGasvertrag.setVerbrauchsFlaeche(0);
                    } else {
                        neuGasvertrag.setVerbrauchsFlaeche(
                                Float.parseFloat(gasFlaeche));
                    }

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragAktualisiert = dao.updateVertrag(neuGasvertrag);
                    break;
                case "Festnetzvertrag":
                    // Vertragsdaten setzen
                    neuFestnetzvertrag = (Festnetzvertrag) 
                            new DatenZugriffsObjekt().getVertrag(vertragID);
                    neuFestnetzvertrag.setKundenNr(kundennr);
                    neuFestnetzvertrag.setVertragsBezeichnung(
                            aenderUmlaute(vertragsBez));
                    neuFestnetzvertrag.setVertragsPartner(
                            aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuFestnetzvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuFestnetzvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuFestnetzvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuFestnetzvertrag.setTarifname(festnetzTarif);
                    neuFestnetzvertrag.setNetztypp(getNetztyp(festnetzEmpfang));
                    if (festnetzIstISDN == null) {
                        neuFestnetzvertrag.setIstISDN(false);
                    } else {
                        neuFestnetzvertrag.setIstISDN(true);
                    }
                    if (festnetzIstVOIP == null) {
                        neuFestnetzvertrag.setIstVOIP(false);
                    } else {
                        neuFestnetzvertrag.setIstVOIP(true);
                    }

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragAktualisiert = dao.updateVertrag(neuFestnetzvertrag);
                    break;
                case "Handyvertrag":
                    // Vertragsdaten setzen
                    neuHandyvertrag = (Handyvertrag) 
                            new DatenZugriffsObjekt().getVertrag(vertragID);
                    neuHandyvertrag.setKundenNr(kundennr);
                    neuHandyvertrag.setVertragsBezeichnung(
                            aenderUmlaute(vertragsBez));
                    neuHandyvertrag.setVertragsPartner(
                            aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuHandyvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuHandyvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuHandyvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuHandyvertrag.setTarifname(handyTarif);
                    neuHandyvertrag.setNetztyp(getNetztyp(handyNetz));
                    neuHandyvertrag.setRufnummer(handyNr);

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragAktualisiert = dao.updateVertrag(neuHandyvertrag);
                    break;
                case "Zeitschriftvertrag":
                    // Vertragsdaten setzen
                    neuZeitschriftvertrag = (Zeitschriftvertrag) 
                            new DatenZugriffsObjekt().getVertrag(vertragID);
                    neuZeitschriftvertrag.setKundenNr(kundennr);
                    neuZeitschriftvertrag.setVertragsBezeichnung(
                            aenderUmlaute(vertragsBez));
                    neuZeitschriftvertrag.setVertragsPartner(
                            aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuZeitschriftvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuZeitschriftvertrag.setBenachrichtigungsfrist(
                                Integer.parseInt(benachrichtigungsfrist));
                    }
                    neuZeitschriftvertrag.setBenachrichtigungsfristEinheit(
                            getZeitEinheit(benachrichtigungsfristEinheit));
                    neuZeitschriftvertrag.setZeitschriftName(zeitschriftName);
                    if (zeitschriftIntervall.equals("")) {
                        neuZeitschriftvertrag.setLieferintervall(0);
                    } else {
                        neuZeitschriftvertrag.setLieferintervall(
                                Integer.parseInt(zeitschriftIntervall));
                    }
                    neuZeitschriftvertrag.setLieferintervallEinheit(
                            getZeitEinheit(zeitschriftEinheit));
                    neuZeitschriftvertrag.setInteressengebiet(
                            getInteressengebiet(zeitschriftGebiet));

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragAktualisiert = dao.updateVertrag(
                            neuZeitschriftvertrag);
                    break;
            }
            if (vertragAktualisiert) {
                ausgabe = "Ihr Vertrag wurde geändert!";
                session.setAttribute(
                        Konstanten.SESSION_ATTR_BENUTZER, 
                        dao.aktualisiereKunde(k));
                dao.close();
                session.setAttribute(Konstanten.REQUEST_ATTR_VERTRAG, null);
                request.setAttribute(Konstanten.REQUEST_ATTR_ERFOLG, ausgabe);
                request.setAttribute("check", null);
                request.setAttribute("cat", null);
                request.getRequestDispatcher("/user.jsp").
                        forward(request, response);
            } else {
                dao.close();
                ausgabe = "Vertrag konnte nicht geändert werden.";
                request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, ausgabe);
                request.setAttribute("check", request.getParameter("check"));
                request.setAttribute("cat", request.getParameter("cat"));
                request.getRequestDispatcher("/user.jsp").
                        forward(request, response);
            }
        } else {
            // Fehler in den obligatorischen Feldern
            fehler = ausgabe.split("!");
            request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, fehler);
            request.setAttribute("check", request.getParameter("check"));
            request.setAttribute("cat", request.getParameter("cat"));
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        }
    }

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       12.06.2015 
     * Methode      loescheVertrag
     * Version:     1.0 
     * 
     * Der Vertrag eines Kunden wird mit Hilfe dieser Funktion als 
     * gelöscht markiert
     * 
     * @param request Http request-Objekt
     * @param response Http response-Objekt
     * @throws ServletException
     * @throws IOException
     * @throws ParseException 
     */
    public void loescheVertrag(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
        int vertragID = Integer.parseInt(request.getParameter("vertrag"));
        HttpSession session = request.getSession();
        String ausgabe = "";
        Kunde k = (Kunde) 
                session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        boolean istGeloescht = dao.loescheVertrag(vertragID);

        if (istGeloescht) {
            ausgabe = "Ihr Vertrag wurde erfolgreich gelöscht! "
                    + "Sie werden nicht mehr benachrichtigt!";   
            session.setAttribute(Konstanten.SESSION_ATTR_BENUTZER, 
                    dao.aktualisiereKunde(k));
            dao.close();
            request.setAttribute(Konstanten.REQUEST_ATTR_ERFOLG, ausgabe);
            request.setAttribute("check", null);
            request.setAttribute("cat", null);
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        }
    }

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       27.08.2015 
     * Methode:     verlaengereVertrag
     * Version:     1.0 
     * 
     * Methode dient zum Verlängern eines Vertrags.
     *
     * @param request Http request-Objekt
     * @param response Http response-Objekt
     * @throws ServletException
     * @throws IOException
     * @throws ParseException
     */
    public void verlaengereVertrag(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        // Initialsierung & Deklartion der benötigten Objekte & Hilfsvariablen
        DatenZugriffsObjekt dao = null;
        HttpSession session = request.getSession();
        Kunde k = (Kunde)session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
        String vertragID = request.getParameter("vertragID"),
                verlaengerung = request.getParameter("verlaengerung"),
                laufzeitEinheit = request.getParameter("laufzeitEinheit");
        String ausgabe = "";
        int vEndeJahr = 0, vEndeMonat = 0, vEndeTag = 0, vLaufzeit = 0,
                vID = Integer.parseInt(vertragID);
        Calendar calBeginn = GregorianCalendar.getInstance(),
                calEnde = GregorianCalendar.getInstance();
        Vertrag vertrag = null;
        java.util.Date neueEnde = null;
        String[] fehler = null;
        boolean istVerlaengert = false;

        if (verlaengerung.equals("")) {
            ausgabe = "Bitte geben Sie eine Zahl ein "
                    + "damit der Vertrag verlängert werden kann!.";
        }
        if (ausgabe.equals("")) {
            dao = new DatenZugriffsObjekt();
            // Holt sich den passenden Vertrag für die Verlängerung
            vertrag = dao.getVertrag(vID);
            // Hier wird das Enddatum aus dem Vertragsobjekt geholt und 
            // formatiert.
            calBeginn.setTime(vertrag.getVertragEnde());
            // Kündigungsfrist als int konvertieren
            vLaufzeit = Integer.parseInt(verlaengerung);
            // Überprüfung, welche Einheit der Benutzer gewählt hat
            // und dementsprechend werden die Variablen gesetzt.
            if (laufzeitEinheit.equals("Tag(e)")) {
                vEndeJahr = 1970;
                vEndeMonat = 1;
                vEndeTag = 1 + vLaufzeit;
            } else if (laufzeitEinheit.equals("Woche(n)")) {
                vEndeJahr = 1970;
                vEndeMonat = 1;
                vEndeTag = 1 + (vLaufzeit * 7);
            } else if (laufzeitEinheit.equals("Monat(e)")) {
                vEndeJahr = 1970;
                vEndeMonat = 1 + vLaufzeit;
                vEndeTag = 1;
            } else if (laufzeitEinheit.equals("Jahr(e)")) {
                vEndeJahr = 1970 + vLaufzeit;
                vEndeMonat = 1;
                vEndeTag = 1;
            }
            calEnde.set(vEndeJahr, vEndeMonat - 1, vEndeTag, 0, 0, 0);
            // Erstelle das neue Datum
            neueEnde = new java.util.Date(
                    calBeginn.getTimeInMillis()
                    + calEnde.getTimeInMillis());
            // Aufruf, der Methode zur Vertragsverlängerung
            istVerlaengert = dao.verlaengereVertrag(Integer.parseInt(vertragID), 
                    vLaufzeit, getZeitEinheit(laufzeitEinheit), 
                    vertrag.getVertragEnde(), neueEnde);
            // Überprüfung, ob der Vertrag verlängert wurde
            if (istVerlaengert) {
                // Kunde wird aktualisiert und in die Session gespeichert
                session.setAttribute(Konstanten.SESSION_ATTR_BENUTZER, 
                        dao.aktualisiereKunde(k));
                // DatenZugriffsObjekt wird geschlossen
                dao.close();
                // Erfolgreich Ausgabe
                ausgabe = "Ihr Vertrag wurde erfolgreich verlängert!";
                request.setAttribute(Konstanten.REQUEST_ATTR_ERFOLG, ausgabe);
                request.setAttribute("check", null);
                request.setAttribute("cat", null);
                request.getRequestDispatcher("/user.jsp").
                        forward(request, response);
            } else {
                // DatenZugriffsObjekt wird geschlossen
                dao.close();
            }
        } else {
            // Fehler in den obligatorischen Feldern
            // Hilfsattribute der Request werden gesetzt, damit der Kunde
            // seinen Fehler erkennt.
            fehler = ausgabe.split("!");
            request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, fehler);
            request.setAttribute("check", request.getParameter("check"));
            request.setAttribute("cat", request.getParameter("cat"));
            request.setAttribute("verlaengern", true);
            request.getRequestDispatcher("/user.jsp").
                    forward(request, response);
        }
    }

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       29.07.2015 
     * Methode:     getZeitEinheit
     * Version:     1.0
     * 
     * Methode liefert ein Zeit_Einheit Objekt mit Hilfe des übergebenen
     * Strings des Dropdown Felds zurück
     *
     * @param art Die gewählte Zeiteinheit aus dem Formular
     *
     * @return Das Objekt der ausgewählten Zeiteinheit
     */
    public Zeit_Einheit getZeitEinheit(String art) {
        // Hilfsvariable für den passenden Primärschlüssel
        int zeiteinheitID = 0;
        // Überprüfung, welche Zeit_Einheit übergeben wurde
        switch (art) {
            case "Tag(e)":
                zeiteinheitID = Konstanten.ID_ZEIT_EINHEIT_TAG;
                break;
            case "Woche(n)":
                zeiteinheitID = Konstanten.ID_ZEIT_EINHEIT_WOCHE;
                break;
            case "Monat(e)":
                zeiteinheitID = Konstanten.ID_ZEIT_EINHEIT_MONAT;
                break;
            case "Jahr(e)":
                zeiteinheitID = Konstanten.ID_ZEIT_EINHEIT_JAHR;
                break;
        }
        // Holt das passende Objekt aus der Datenbank
        return new DatenZugriffsObjekt().getZeitEinheit(zeiteinheitID);
    }

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       29.07.2015 
     * Methode:     getNetztyp
     * Version:     1.0
     * 
     * Hier wird mittels übergebenem String der Primärschlüssel des Netztyps
     * ermittelt. Mit dem Primärschlüssel wird dann das passende Objekt aus
     * der Datenbank geholt.
     *
     * @param netztyp String des Netztyp aus dem Dropdown Feld
     * @return passendens Netztyp Objekt zum übergebenen String
     */
    public Netztyp getNetztyp(String netztyp) {
        // Hilfsvariable für den passenden Primärschlüssel
        int netztypID = 0;
        // Überprüfung, welcher Netztyp übergeben wurde
        switch (netztyp) {
            case "GPRS":
                netztypID = Konstanten.ID_NETZTYP_GPRS;
                break;
            case "EDGE":
                netztypID = Konstanten.ID_NETZTYP_EDGE;
                break;
            case "UMTS":
                netztypID = Konstanten.ID_NETZTYP_UMTS;
                break;
            case "HSDPA":
                netztypID = Konstanten.ID_NETZTYP_HSDPA;
                break;
            case "LTE":
                netztypID = Konstanten.ID_NETZTYP_LTE;
                break;
            case "DSL":
                netztypID = Konstanten.ID_NETZTYP_DSL;
                break;
            case "VDSL":
                netztypID = Konstanten.ID_NETZTYP_VDSL;
                break;
        }
        // Holt das passende Objekt aus der Datenbank
        return new DatenZugriffsObjekt().getNetztyp(netztypID);
    }

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       29.07.2015 
     * Methode:     getInteressengebiet
     * Version:     1.0
     * 
     * Hier wird mittels übergebenem String der Primärschlüssel des 
     * Interessengebietsermittelt. Mit dem Primärschlüssel wird dann das 
     * passende Objekt aus der Datenbank geholt.
     *
     * @param gebiet Die gewählte Zeiteinheit aus dem Formular     *
     * @return Das Objekt der ausgewählten Zeiteinheit
     */
    public Interessengebiet getInteressengebiet(String gebiet) {
        // Hilfsvariable für den passenden Primärschlüssel
        int gebietID = 0;
        // Überprüfung, welches Interessengebiet übergeben wurde
        switch (gebiet) {
            case "Audio- und Hifimagazin":
                gebietID = Konstanten.ID_GEBIETE_AUDIO;
                break;
            case "Automobilzeitschrift":
                gebietID = Konstanten.ID_GEBIETE_AUTO;
                break;
            case "Computermagazin":
                gebietID = Konstanten.ID_GEBIETE_COMPUTER;
                break;
            case "Fachzeitschrift":
                gebietID = Konstanten.ID_GEBIETE_FACH;
                break;
            case "Fitnessmagazin":
                gebietID = Konstanten.ID_GEBIETE_FITNESS;
                break;
            case "Gartenmagazin":
                gebietID = Konstanten.ID_GEBIETE_GARTEN;
                break;
            case "Kindermagazin":
                gebietID = Konstanten.ID_GEBIETE_KINDER;
                break;
            case "Kochen & Rezepte":
                gebietID = Konstanten.ID_GEBIETE_KOCHEN;
                break;
            case "Reisemagazin":
                gebietID = Konstanten.ID_GEBIETE_REISE;
                break;
            case "Sonstiges":
                gebietID = Konstanten.ID_GEBIETE_SONSTIGES;
                break;
            case "Tageszeitung":
                gebietID = Konstanten.ID_GEBIETE_TAGESZEITUNG;
                break;
            case "Wissensmagazin":
                gebietID = Konstanten.ID_GEBIETE_WISSEN;
                break;
            case "Wohnideenmagazin":
                gebietID = Konstanten.ID_GEBIETE_WOHNEN;
                break;
        }
        // Holt das passende Objekt aus der Datenbank
        return new DatenZugriffsObjekt().getInteressengebiet(gebietID);
    }

    /**
     * Ersteller:   Sascha Jungenkrüger 
     * Datum:       29.07.2015 
     * Methode:     aendereUmlaute
     * Version:     1.0
     * 
     * Die Methode dient zum Ändern der Umlaute.
     * 
     * @param begriff übergebener Begriff der geaendert werden soll
     * @return der neue Begriff mit Umformung
     */
    public String aenderUmlaute(String begriff) {
        if (begriff.contains("Ã¤")) {
            begriff = begriff.replace("Ã¤", "ae");
        }
        if (begriff.contains("Ã¶")) {
            begriff = begriff.replace("Ã¶", "oe");
        }
        if (begriff.contains("Ã¼")) {
            begriff = begriff.replace("Ã¼", "ue");
        }
        if (begriff.contains("Ã")) {
            begriff = begriff.replace("Ã", "ss");
        }
        return begriff;
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
        return "Ich bin das Servlet, welches sich um die Verträge kümmert!";
    }// </editor-fold>

}
