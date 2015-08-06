package Servlets;

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
 * Ersteller: Sascha Jungenkrüger 
 * Erstelldatum: 03.06.2015 
 * Version: 1.1
 * Änderungen: 1.0 - Erstellung 
 *             1.1 (Sascha Jungenkrüger) - Vertrag hinzufügen implementiert
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
        String  kundennr = request.getParameter("kundennr"),
                vertragsBez = request.getParameter("vertragsbez"),
                vertragsPartner = request.getParameter("vertragspartner"),
                benachrichtigungsfrist = 
                request.getParameter("benachrichtigungsfrist"),
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
                gasFlaeche = request.getParameter("gFlaeche"),
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
        Vertrag_Art art = null;
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
        
        // Überprüft, ob die Benachrichtigungsfrist nur Ziffern enthält
        if (!benachrichtigungsfrist.equals("") 
                && benachrichtigungsfrist.matches("[^0-9]")) {
            ausgabe = ausgabe + 
                    "Bei der Benachrichtigungsfrist sind nur Ziffern erlaubt.!";
        }

        // Überprüft die optionalen Eingaben
        switch (kategorie) {
            case "Strom":
                if (!stromNr.equals("")
                        && stromNr.matches("[\\d]+?[\\d\\w-./]")) {
                    ausgabe = 
                            "Ihr Stromnummer enthält "
                            + "ungültige Zeichen & Ziffen.!";
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
                if (!stromPersonen.equals("")
                        && stromPersonen.matches("[\\d]+")) {
                    ausgabe = ausgabe + "Bei der Personenangabe sind nur"
                            + "ganze Ziffern erlaubt.!";
                }
                if (!stromGrundPreis.equals("")
                        && stromGrundPreis.matches("[\\d]+[,][\\d]+")) {
                    ausgabe = ausgabe + "Ihr Grundpreis enthält "
                            + "ungültige Ziffern.!";
                }
                break;
            case "Gas":
                if (!gasNr.equals("")
                        && gasNr.matches("[\\d]+?[\\d\\w-./]")) {
                    ausgabe = "Ihr Gasnummer enthält "
                            + "ungültige Zeichen & Ziffen.!";
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
                if (!gasFlaeche.equals("")
                        && gasFlaeche.matches("[\\d]+[,][\\d]+")) {
                    ausgabe = ausgabe
                            + "Bei der Gasfläche sind nur ganze Zahlen "
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

        // Wenn nichts im Ausgabestring steht, sind die Eingaben korrekt
        // und es kann fortgesetzt werden
        if (ausgabe.equals("")) {

            // Überprüfung, welche Kategorie gewählt wurde
            switch (kategorie) {
                case "Strom":
                    // Vertragsdaten setzen
                    neuStromvertrag = new Stromvertrag();
                    neuStromvertrag.setVertragArt(new DatenZugriffsObjekt().getVertragsArt(Konstanten.ID_VERTRAG_ART_STROM));
                    neuStromvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuStromvertrag.setVertragNr(vertragsNr);
                    neuStromvertrag.setVertragStatus(new DatenZugriffsObjekt().getVertragsStatus(Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuStromvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuStromvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuStromvertrag.setLaufzeitEinheit(getZeitEinheit(laufzeitEinheit));
                    neuStromvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuStromvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuStromvertrag.setKuendigungsfristEinheit(getZeitEinheit(kuendigungsfristEinheit));
                    neuStromvertrag.setKundenNr(kundennr);
                    neuStromvertrag.setVertragsBezeichnung(aenderUmlaute(vertragsBez));
                    neuStromvertrag.setVertragsPartner(aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuStromvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuStromvertrag.setBenachrichtigungsfrist(Integer.parseInt(benachrichtigungsfrist));
                    }                    
                    neuStromvertrag.setBenachrichtigungsfristEinheit(getZeitEinheit(benachrichtigungsfristEinheit));
                    neuStromvertrag.setStromzaehlerNr(stromNr);
                    if (stromStand.equals("")) {
                        neuStromvertrag.setStromzaehlerStand(0);
                    } else {
                        neuStromvertrag.setStromzaehlerStand(Integer.parseInt(stromStand));
                    }
                    if (stromVerbrauch.equals("")) {
                        neuStromvertrag.setVerbrauchProJahr(0);
                    } else {
                        neuStromvertrag.setVerbrauchProJahr(Integer.parseInt(stromVerbrauch));
                    }
                    if (stromPreis.equals("")) {
                        neuStromvertrag.setPreisProKwh(0);
                    } else {
                        neuStromvertrag.setPreisProKwh(Float.parseFloat(stromPreis));
                    }
                    if (stromPersonen.equals("")) {
                        neuStromvertrag.setAnzPersonenHaushalt(0);
                    } else {
                        neuStromvertrag.setAnzPersonenHaushalt(Integer.parseInt(stromPersonen));
                    }
                    if (stromGrundPreis.equals("")) {
                        neuStromvertrag.setGrundpreisMonat(0);
                    } else {
                        neuStromvertrag.setGrundpreisMonat(Float.parseFloat(stromGrundPreis));
                    }

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuStromvertrag);
                    break;
                case "Gas":
                    // Vertragsdaten setzen
                    neuGasvertrag = new Gasvertrag();
                    neuGasvertrag.setVertragArt((Vertrag_Art) new DatenZugriffsObjekt().getVertragsArt(Konstanten.ID_VERTRAG_ART_GAS));
                    neuGasvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuGasvertrag.setVertragNr(vertragsNr);
                    neuGasvertrag.setVertragStatus(new DatenZugriffsObjekt().getVertragsStatus(Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuGasvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuGasvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuGasvertrag.setLaufzeitEinheit(getZeitEinheit(laufzeitEinheit));
                    neuGasvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuGasvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuGasvertrag.setKuendigungsfristEinheit(getZeitEinheit(kuendigungsfristEinheit));
                    neuGasvertrag.setKundenNr(kundennr);
                    neuGasvertrag.setVertragsBezeichnung(aenderUmlaute(vertragsBez));
                    neuGasvertrag.setVertragsPartner(aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuGasvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuGasvertrag.setBenachrichtigungsfrist(Integer.parseInt(benachrichtigungsfrist));
                    }                    
                    neuGasvertrag.setBenachrichtigungsfristEinheit(getZeitEinheit(benachrichtigungsfristEinheit));
                    neuGasvertrag.setGaszaehlerNr(gasNr);
                    if (gasStand.equals("")) {
                        neuGasvertrag.setGaszaehlerStand(0);
                    } else {
                        neuGasvertrag.setGaszaehlerStand(Integer.parseInt(gasStand));
                    }
                    if (gasVerbrauch.equals("")) {
                        neuGasvertrag.setVerbrauchProJahr(0);
                    } else {
                        neuGasvertrag.setVerbrauchProJahr(Integer.parseInt(gasVerbrauch));
                    }                    
                    if (gasPreis.equals("")) {
                        neuGasvertrag.setPreisProKhw(0);
                    } else {
                        neuGasvertrag.setPreisProKhw(Float.parseFloat(gasPreis));
                    }  
                    if (gasFlaeche.equals("")) {
                        neuGasvertrag.setVerbrauchsFlaeche(0);
                    } else {
                        neuGasvertrag.setVerbrauchsFlaeche(Float.parseFloat(gasFlaeche));
                    }  

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuGasvertrag);
                    break;
                case "Festnetz/DSL":
                    // Vertragsdaten setzen
                    neuFestnetzvertrag = new Festnetzvertrag();
                    neuFestnetzvertrag.setVertragArt(new DatenZugriffsObjekt().getVertragsArt(Konstanten.ID_VERTRAG_ART_FESTNETZ));
                    neuFestnetzvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuFestnetzvertrag.setVertragNr(vertragsNr);
                    neuFestnetzvertrag.setVertragStatus(new DatenZugriffsObjekt().getVertragsStatus(Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuFestnetzvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuFestnetzvertrag.setVertragBeginn(parseDate);
                    neuFestnetzvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuFestnetzvertrag.setLaufzeitEinheit(getZeitEinheit(laufzeitEinheit));
                    neuFestnetzvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuFestnetzvertrag.setVertragEnde(parseDate);
                    neuFestnetzvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuFestnetzvertrag.setKuendigungsfristEinheit(getZeitEinheit(kuendigungsfristEinheit));
                    neuFestnetzvertrag.setKundenNr(kundennr);
                    neuFestnetzvertrag.setVertragsBezeichnung(aenderUmlaute(vertragsBez));
                    neuFestnetzvertrag.setVertragsPartner(aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuFestnetzvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuFestnetzvertrag.setBenachrichtigungsfrist(Integer.parseInt(benachrichtigungsfrist));
                    }                    
                    neuFestnetzvertrag.setBenachrichtigungsfristEinheit(getZeitEinheit(benachrichtigungsfristEinheit));
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
                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuFestnetzvertrag);
                    break;
                case "Handy":
                    // Vertragsdaten setzen
                    neuHandyvertrag = new Handyvertrag();
                    neuHandyvertrag.setVertragArt(new DatenZugriffsObjekt().getVertragsArt(Konstanten.ID_VERTRAG_ART_HANDY));
                    neuHandyvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuHandyvertrag.setVertragNr(vertragsNr);
                    neuHandyvertrag.setVertragStatus(new DatenZugriffsObjekt().getVertragsStatus(Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuHandyvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuHandyvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuHandyvertrag.setLaufzeitEinheit(getZeitEinheit(laufzeitEinheit));
                    neuHandyvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuHandyvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuHandyvertrag.setKuendigungsfristEinheit(getZeitEinheit(kuendigungsfristEinheit));
                    neuHandyvertrag.setKundenNr(kundennr);
                    neuHandyvertrag.setVertragsBezeichnung(aenderUmlaute(vertragsBez));
                    neuHandyvertrag.setVertragsPartner(aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuHandyvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuHandyvertrag.setBenachrichtigungsfrist(Integer.parseInt(benachrichtigungsfrist));
                    }                    
                    neuHandyvertrag.setBenachrichtigungsfristEinheit(getZeitEinheit(benachrichtigungsfristEinheit));
                    neuHandyvertrag.setTarifname(handyTarif);
                    neuHandyvertrag.setNetztyp(getNetztyp(handyNetz));
                    neuHandyvertrag.setRufnummer(handyNr);

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuHandyvertrag);
                    break;
                case "Zeitschriften":
                    // Vertragsdaten setzen
                    neuZeitschriftvertrag = new Zeitschriftvertrag();
                    neuZeitschriftvertrag.setVertragArt(new DatenZugriffsObjekt().getVertragsArt(Konstanten.ID_VERTRAG_ART_ZEITSCHRIFT));
                    neuZeitschriftvertrag.setKunde((Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));
                    neuZeitschriftvertrag.setVertragNr(vertragsNr);
                    neuZeitschriftvertrag.setVertragStatus(new DatenZugriffsObjekt().getVertragsStatus(Konstanten.ID_VERTRAGSSTATUS_AKTIV));
                    neuZeitschriftvertrag.setVertragBeginn(parseDateSqlvBeginn);
                    neuZeitschriftvertrag.setLaufzeit(Integer.parseInt(laufzeit));
                    neuZeitschriftvertrag.setLaufzeitEinheit(getZeitEinheit(laufzeitEinheit));
                    neuZeitschriftvertrag.setVertragEnde(parseDateSqlvEnde);
                    neuZeitschriftvertrag.setKuendigungsfrist(Integer.parseInt(kuendigungsfrist));
                    neuZeitschriftvertrag.setKuendigungsfristEinheit(getZeitEinheit(kuendigungsfristEinheit));
                    neuZeitschriftvertrag.setKundenNr(kundennr);
                    neuZeitschriftvertrag.setVertragsBezeichnung(aenderUmlaute(vertragsBez));
                    neuZeitschriftvertrag.setVertragsPartner(aenderUmlaute(vertragsPartner));
                    if (benachrichtigungsfrist.equals("")) {
                        neuZeitschriftvertrag.setBenachrichtigungsfrist(0);
                    } else {
                        neuZeitschriftvertrag.setBenachrichtigungsfrist(Integer.parseInt(benachrichtigungsfrist));
                    }                    
                    neuZeitschriftvertrag.setBenachrichtigungsfristEinheit(getZeitEinheit(benachrichtigungsfristEinheit));
                    neuZeitschriftvertrag.setZeitschriftName(zeitschriftName);
                    if (zeitschriftIntervall.equals("")) {
                        neuZeitschriftvertrag.setLieferintervall(0);
                    } else {
                        neuZeitschriftvertrag.setLieferintervall(Integer.parseInt(zeitschriftIntervall));
                    }                    
                    neuZeitschriftvertrag.setLieferintervallEinheit(getZeitEinheit(zeitschriftEinheit));
                    neuZeitschriftvertrag.setInteressengebiet(getInteressengebiet(zeitschriftGebiet));

                    // Zugriff auf DAO, damit die Daten in der DB
                    // gespeichert werden
                    // return true=gespeichert false=nicht gespeichert
                    vertragGespeichert = new DatenZugriffsObjekt().addContract(neuZeitschriftvertrag);
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
                request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, ausgabe);
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
            if (!suchText.equals("")) {
                vertraege = new DatenZugriffsObjekt().searchContract(aenderUmlaute(suchText), (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));

                if (vertraege.isEmpty()) {
                    vertraege = null;
                }
            } else {
                vertraege = null;
            }

            request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, "Bitten füllen sie das Suchfeld aus damit Ihnen, bei erfolgreicher Suche, Verträge angezeigt werden.");
            request.setAttribute(Konstanten.REQUEST_ATTR_VERTRAEGE, vertraege);
            request.setAttribute(Konstanten.REQUEST_ATTR_KATEGORIE, null);
            request.setAttribute(Konstanten.REQUEST_ATTR_SUCHTEXT, aenderUmlaute(suchText));
            request.getRequestDispatcher("/user.jsp").forward(request, response);
        } else if (kategorie != null) {
            vertraege = new DatenZugriffsObjekt().searchContractCategory(kategorie, (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER));

            if (!vertraege.isEmpty()) {
                request.setAttribute(Konstanten.REQUEST_ATTR_VERTRAEGE, vertraege);
            } else {
                request.setAttribute(Konstanten.REQUEST_ATTR_VERTRAEGE, null);
            }
            request.setAttribute(Konstanten.REQUEST_ATTR_KATEGORIE, kategorie);
            request.setAttribute(Konstanten.REQUEST_ATTR_SUCHTEXT, null);
            request.getRequestDispatcher("/user.jsp").forward(request, response);
        }
    }
    
    /**
     * Ersteller: Sascha Jungenkrüger 
     * Erstelldatum: 29.07.2015 
     * Version: 1.0
     * Änderungen: 1.0 - Erstellung 
     * 
     * @param art Die gewählte Zeiteinheit aus dem Formular
     * 
     * @return Das Objekt der ausgewählten Zeiteinheit
     */
    public Zeit_Einheit getZeitEinheit(String art) {
        int zeiteinheitID = 0;
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
        return new DatenZugriffsObjekt().getZeitEinheit(zeiteinheitID);
    }
    
    /**
     * Ersteller: Sascha Jungenkrüger 
     * Erstelldatum: 29.07.2015 
     * Version: 1.0
     * Änderungen: 1.0 - Erstellung 
     * 
     * @param netztyp 
     * @return 
     */
    public Netztyp getNetztyp(String netztyp) {
        int netztypID = 0;
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
        return new DatenZugriffsObjekt().getNetztyp(netztypID);
    }
    
    /**
     * Ersteller: Sascha Jungenkrüger 
     * Erstelldatum: 29.07.2015 
     * Version: 1.0
     * Änderungen: 1.0 - Erstellung 
     * 
     * @param gebiet Die gewählte Zeiteinheit aus dem Formular
     * 
     * @return Das Objekt der ausgewählten Zeiteinheit
     */
    public Interessengebiet getInteressengebiet(String gebiet) {
        int gebietID = 0;
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
        return new DatenZugriffsObjekt().getInteressengebiet(gebietID);
    }
    
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
        return "Short description";
    }// </editor-fold>

}
