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
 *                  1.2 (René Kanzenbach) 02.06.2015
 *                  -logIn-Methode hinzugefügt
 */
package Servlets;

import Entitys.Benutzer;
import Entitys.Kunde;
import Hilfsklassen.Konstanten;
import Hilfsklassen.ZufallsStringErzeuger;
import Manager.DatenZugriffsObjekt;
import Manager.EmailHandler;
import Manager.SystemManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginLogoutServlet extends HttpServlet {

    /**
     * Fehlertext, der ausgegeben wird, wenn der Loginvorgang, aufgrund eines
     * falschen Passworts oder falscher Email fehlschlägt.
     */
    private final String LOGINFEHLER_TEXT = "Login fehlgeschlagen! Überprüfen "
	    + "Sie ihre Login-Daten!";

    /**
     * Fehlertext, der ausgegeben wird, wenn der Loginvorgang fehlschlägt, weil
     * der Benutzeraccount gelöscht wurde.
     */
    private final String FEHLER_ACCOUNT_GELOESCHT = "Ihr Account wurde gelöscht! "
	    + "Um ihn wiederherzustellen, melden Sie sich bitte bei uns über "
	    + "das Kontaktformular.";

    /**
     * Fehlertext, der ausgegeben wird, wenn der Benutzer seinen Account noch
     * nicht über die Bestätigungsemail aktiviert hat.
     */
    private final String FEHLER_ACCOUNT_NICHT_AKTIVIERT = "Ihr Account wurde "
	    + "noch nicht aktiviert! Bitte bestätigen Sie zu erst den Link in "
	    + "der Email, die Ihnen zugeschickt wurde!";

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

        //Zeichensatz des Request-Objektes auf "UTF-8" setzen
	//Ermöglicht die korrekte Verwendung von Umlauten
	request.setCharacterEncoding("UTF-8");

	// Deklaration des Hilfsvariablen für die demenstprechenden Funktionen
	String meta = "", ausgabe = "";
	boolean sendeMail = false;

	// Initialisierung der verschiedenen Verlinkungen
	String login = request.getParameter("login");
	String register = request.getParameter("register");
	String getPassword = request.getParameter("get_pw");
	String logout = request.getParameter("logout");
	String email = request.getParameter("login_email");
	//Aktion die das Servlet anstoßen soll.
	String aktion = request.getParameter(Konstanten.URL_PARAM_AKTION);

	// Überprüfung, welcher Button gedrückt wurde
	if (register != null) {
	    // Registrierung durchführen
	    this.register(request, response);

	} else if (login != null) {
	    //LogIn durchführen
	    this.logIn(request, response);
	} else if (getPassword != null) {
	    if (email != null && !email.equals("")) {
		sendeMail = true;
	    } else {
		String fehler[] = {"Geben Sie ihre E-Mail-Adresse an."};
		request.setAttribute("error", fehler);
                // Da es Fehler im Formular gibt stellt man dem Besucher seine
		// eingegebenen Daten zur Verfügung, damit er sie
		// überarbeiten bzw. ergänzen kann.                
		request.getRequestDispatcher("/login_register.jsp")
			.forward(request, response);
	    }
	} else if (aktion != null && aktion.equals(Konstanten.URL_AKTION_LOGOUT)) {
	    //Ausloggen
	    this.logOut(request, response);
	} else {
	    ausgabe = "Irgendwas wurde nicht richtig programmiert!";
	}

	if (sendeMail) {
	    String info = "Ihnen wird ein Bestätigungslink zugesandt mit dem Sie ein neues Passwort anfordern können.";
	    request.setAttribute("info", info);
	    request.getRequestDispatcher("/confirmation.jsp")
		    .forward(request, response);
	    passwortZuruecksetzen(email, request.getRequestURL().toString());
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

    private void registrierungsBestaetigung(String email, String pfad, String password) {
	DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
	ZufallsStringErzeuger p = new ZufallsStringErzeuger();
	String registrierungsBestaetigung = p.erzeugeBestaetigungsReferenz();
	Benutzer b = dao.getBenutzer(email);
	b.setEmailBestaetigung(registrierungsBestaetigung);
	b = dao.updateBenutzer(b);
	Kunde k = dao.getKunde(b.getBenutzerId());
	EmailHandler emailer = new EmailHandler();
	emailer.sendeRegistrierungsBestaetigung("Registrierung für " + k.getVorname() + " "
		+ k.getNachname(), email, registrierungsBestaetigung, pfad, password);
    }

    /**
     * Ersteller: Sascha Jungenkrüger Erstelldatum: 01.06.2015 Methode: register
     * Version: 1.0 Änderungen: -
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void register(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	HttpSession session = request.getSession();
	DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
	Benutzer ben = new Benutzer();
	String fehler[];
	String ausgabe = "", meta = "", vname, name, email1, email2, pw1, pw2;
	boolean vnameIsTrue = false, nameIsTrue = false, isRegister = false,
		emailIsTrue = false, pwIsTrue = false, eMailIsAvailable = false;
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
	    ausgabe = ausgabe + "\n E-Mail-Adressen stimmen nicht überein!";
	} else {
	    // Überprüfung, ob die E-Mail-Adressen konform sind
	    if (!email1.matches("[a-zA-Z0-9].+@[a-zA-Z0-9\\.-]+[a-zA-Z]{2,4}")) {
		// Fehlermeldung in Variable ausgabe gespeichert
		ausgabe = ausgabe
			+ "\n E-Mail-Adresse ist nicht konform!";
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
		    + "\n Passwörter stimmen nicht überein!";
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
	    eMailIsAvailable = dao.isEmailAvailable(email1);
	    if (eMailIsAvailable) {
                // Registrierung wird durchgeführt
		// Boolscher Rückgabewert wird in isRegister gespeichert
		isRegister = dao.register(vname, name, email1, pw1);
		// Überprüfung, ob die Registrierung erfolgreich war
		if (isRegister) {
		    // Zurücksetzen der Session
		    session.invalidate();
		    EmailHandler emailer = new EmailHandler();
		    registrierungsBestaetigung(email1, request.getRequestURL().toString(), pw1);
		    request.getRequestDispatcher("/register_complete.jsp")
			    .forward(request, response);
		}
	    } else {
		ausgabe = "Ihre E-Mail-Adresse ist schon vorhanden!";
                // Fehlermeldung wird gesplittet und im Array gespeichert
		// und auf der login_register.jsp ausgegeben.
		fehler = ausgabe.split("!");
		// Setzen der Fehler in den Request                    
		request.setAttribute(Konstanten.URL_PARAM_FEHLER, fehler);
                // Da es Fehler im Formular gibt stellt man dem Besucher seine
		// eingegebenen Daten zur Verfügung, damit er sie
		// überarbeiten bzw. ergänzen kann.                
		request.getRequestDispatcher("/login_register.jsp")
			.forward(request, response);
	    }
	    // DAO-Verbindung wird geschlossen
	    dao.close();
	} else {
            // Fehlermeldung wird gesplittet und im Array gespeichert
	    // und auf der login_register.jsp ausgegeben.
	    fehler = ausgabe.split("!");
	    // Setzen der Fehler in den Request
	    request.setAttribute(Konstanten.URL_PARAM_FEHLER, fehler);
            // Da es Fehler im Formular gibt stellt man dem Besucher seine
	    // eingegebenen Daten zur Verfügung, damit er sie
	    // überarbeiten bzw. ergänzen kann.
	    request.getRequestDispatcher("/login_register.jsp")
		    .forward(request, response);
	}
    }

    /**
     * Ersteller:	René Kanzenbach Datum:	02.06.2015 Methode:	logIn Version:	1.0
     * -1.1 René Kanzenbach 20.08.2015 Wenn sich ein Admin einloggt, wird er
     * jetzt direkt auf die "admin.jsp" weitergeleitet
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void logIn(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	Benutzer benutzer;
	DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
	HttpSession session = request.getSession();

	String loginEmail;
	String loginPasswort;
	String fehlerText[];

	//Eingegebene Benutzer-Email auslesen
	loginEmail = request.getParameter("login_email");
	//Eingegebenes Passwort auslesen
	loginPasswort = request.getParameter("login_passwort");

	//BenutzerObjekt mit Hilfe der Email laden
	benutzer = dao.getBenutzer(loginEmail);

	//Prüfen ob Benutzer gefunden wurde und das Passwort korrekt ist
	if (benutzer != null
		&& benutzer.pruefePasswort(loginPasswort)) {

	    //Prüfen welchen Status der Benutzer besitzt
	    switch (benutzer.getStatus().getBenutzerStatusId()) {

		case Konstanten.ID_BEN_STATUS_AKTIV:
		    //Benutzer ist Aktiv. Login war erfolgreich

		    //BenutzerObjekt in Session laden
		    session.setAttribute(Konstanten.SESSION_ATTR_BENUTZER, benutzer);

		    //Prüfen ob Benutzer ein Kunde oder Admin ist.
		    if (benutzer.besitztRecht(
			    Konstanten.ID_BEN_RECHT_BENUTZER_ANSICHT)) {
			//Benutzer besitzt Benutzerrechte.
			//Weiterleitung auf Benutzerstartseite
			request.getRequestDispatcher("/user.jsp")
				.forward(request, response);
		    } else if (benutzer.besitztRecht(
			    Konstanten.ID_BEN_RECHT_ADMIN_ANSICHT)) {
			//Benutzer besitzt Adminrechte.
			//Weiterleitung auf Adminstartseite.
			request.getRequestDispatcher("/admin.jsp")
				.forward(request, response);
		    }
		    break;

		case Konstanten.ID_BEN_STATUS_UNBESTAETIGT:
		//Benutzer hat die Bestätigungsemail noch nicht bearbeitet

		    //Übergabe des entsprechenden Fehlertextes an 
		    //das "login_register.jsp"
		    fehlerText = new String[1];
		    fehlerText[0] = this.FEHLER_ACCOUNT_NICHT_AKTIVIERT;
		    request.setAttribute(Konstanten.URL_PARAM_FEHLER, fehlerText);

		    //Weiterleitung auf login_register.jsp
		    request.getRequestDispatcher("/login_register.jsp")
			    .forward(request, response);
		    break;

		case Konstanten.ID_BEN_STATUS_GELOESCHT:
		//Benutzeraccount besitzt den Status gelöscht

		    //Benutzer hat die Bestätigungsemail noch nicht bearbeitet
		    //Übergabe des entsprechenden Fehlertextes an 
		    //das "login_register.jsp"
		    fehlerText = new String[1];
		    fehlerText[0] = this.FEHLER_ACCOUNT_GELOESCHT;
		    request.setAttribute(Konstanten.URL_PARAM_FEHLER, fehlerText);

		    //Weiterleitung auf login_register.jsp
		    request.getRequestDispatcher("/login_register.jsp")
			    .forward(request, response);
		    break;
	    }

	} else { //Falls LogIn nicht erfolgreich

	    //Übergabe des Fehlertextes an das "login_register.jsp"
	    fehlerText = new String[1];
	    fehlerText[0] = this.LOGINFEHLER_TEXT;
	    request.setAttribute(Konstanten.URL_PARAM_FEHLER, fehlerText);

	    //Weiterleitung auf login_register.jsp
	    request.getRequestDispatcher("/login_register.jsp")
		    .forward(request, response);
	}

	//DatenZugriffsObjekt schließen
	dao.close();
    }

    /**
     *
     * Ersteller: René Kanzenbach Erstelldatum: 11.06.2015 Methode: logOut
     * Version: 1.0 Veränderungen: -
     *
     * Liest die aktuelle Session aus dem request und löst diese auf.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void logOut(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	//Session auslesen
	HttpSession session = request.getSession();
	//Session auflösen
	session.invalidate();
	//Weiterleitung auf Startseite
	request.getRequestDispatcher("/index.jsp")
		.forward(request, response);
    }

    private void passwortZuruecksetzen(String email, String pfad) {
	DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
	ZufallsStringErzeuger p = new ZufallsStringErzeuger();
	String passwortBestaetigung = p.erzeugeBestaetigungsReferenz();
	Benutzer b = dao.getBenutzer(email);
	b.setPasswortZuruecksetzen(passwortBestaetigung);
	b = dao.updateBenutzer(b);
	EmailHandler emailer = new EmailHandler();
	emailer.sendePasswortBestaetigung(email, passwortBestaetigung, pfad);
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
