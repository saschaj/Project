package Servlets;

import Entitys.Benutzer;
import Hilfsklassen.Konstanten;
import Manager.DatenZugriffsObjekt;
import Manager.SystemManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;

/**
 *
 * @author René
 */
public class AdminServlet extends HttpServlet {

	/**
	 * Ersteller: René Kanzenbach
	 * Datum: 28.07.2015
	 * Version: 1.0
	 * Änderungen: -
	 *
	 * Prüft, welche Aktion in der admin.jsp aufgerufen wurde und führt dann die
	 * entsprechende Methode auf.
	 *
	 * @param request
	 * @param response
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Zeichensatz des Request-Objektes auf "UTF-8" setzen
		//Ermöglicht die korrekte Verwendung von Umlauten
		request.setCharacterEncoding("UTF-8");

		//Pruefen, welche Aktion ausgeführt wurde.
		if (request.getParameter("BenutzerStatistik") != null) {
			//Der Button "Benutzerübersicht" wurde in der admin.jsp betaetigt.
			this.zeigeBenutzerStatistik(request, response);
		} else if (request.getParameter("VertragStatistik") != null) {
			//Der Button "Vertragsübersicht" wurde in der admin.jsp betaetigt.
			this.zeigeVertragsStatistik(request, response);
		} else if (request.getParameter("Zeige_BenutzerListe") != null) {
			this.benutzerSuche(request, response);
		} else if (request.getParameter("Ben_loeschen") != null) {
			//Der Button "Löschen" wurde in der admin_dynamic.jsp betätigt.
			this.benutzerLoeschen(request, response);
		} else if (request.getParameter("Ben_aktivieren") != null) {
			//Der Button "Aktivieren" wurde in der admin_dynamic.jsp betätigt.
			this.benutzerAktivieren(request, response);
		} else if (request.getParameter("Admin_pw_aendern") != null) {
			//Der Button zum Ändern des Adminpassworts in der admin_dynamic.jsp
			//wurde betätigt.
			this.aendereAdminPasswort(request, response);
		} else if (request.getParameter("Admin_erstellen") != null) {
			//Der Button zum Anlegen eines neuen Adminaccounts in der 
			//"admin_dynamic.jsp" wurde betätigt
			this.erstelleAdminAccount(request, response);
		} else if (request.getParameter("PW_zurueck") != null) {
			//Der Button 'Passwort zurücksetzen' in der 'admin_dynamic.jsp' 
			//wurde betätigt
			this.setzePwZurueck(request, response);
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
		return "Servlet, welches sich um saemtliche Eingaben aus der admin.jsp"
				+ " kuemmert.";
	}// </editor-fold>

	/**
	 * Ersteller: René Kanzenbach
	 * Datum: 28.07.2015
	 * Version: 1.0
	 * Änderungen: -
	 *
	 * Diese Methode erzeugt ein Diagramm-PNG, welches in der
	 * admin_dynamic.jsp ausgegeben wird.
	 *
	 * Dazu wird die Methode getBenutzerStatistik():JFreeChart ,des
	 * DatenZugriffsObjekt, aufgerufen. Das JFreeChart-Diagramm wird dann in
	 * dieser Methode in ein PNG umgewandelt und der admin_dynamic.jsp
	 * übergeben.
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void zeigeBenutzerStatistik(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
		JFreeChart benutzerStatistik = dao.getBenutzerStatistik();
		String dateiName;
		String url;

		/* Diagramm in 500x500Pixel großes Bild umwandeln und den Namen des 
		 * Bildes in dateiName speichern.
		 */
		dateiName = ServletUtilities.saveChartAsPNG(
				benutzerStatistik, 500, 500, request.getSession());

		/*
		 Erstellen der URL, die verwendet werden kann um das Diagramm anzuzeigen.
		 Dazu wird ein DisplayChart-Servlet verwendet, welches das Diagramm-Bild
		 an den aufrufenden Browser weiterleitet.
		 */
		url = request.getContextPath() + "/servlet/DisplayChart?filename="
				+ dateiName;

		//URL des Diagrammbildes als Parameter uebergeben.
		request.setAttribute("StatistikURL", url);
		//Aufrufen der admin_dynamic.jsp
		request.getRequestDispatcher("/admin.jsp").forward(request, response);
		dao.close();
	}

	/**
	 * Ersteller: René Kanzenbach
	 * Datum: 28.07.2015
	 * Version: 1.0
	 * Änderungen: -
	 *
	 * Diese Methode erzeugt ein Diagramm-PNG, welches in der
	 * admin_dynamic.jsp ausgegeben wird.
	 *
	 * Dazu wird die Methode getVertragStatistik():JFreeChart ,des
	 * DatenZugriffsObjekt, aufgerufen. Das JFreeChart-Diagramm wird dann in
	 * dieser Methode in ein PNG umgewandelt und der admin_dynamic.jsp
	 * übergeben.
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void zeigeVertragsStatistik(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
		JFreeChart vertragDiagramm = dao.getVertragStatistik();
		String dateiName;
		String url;

		/* Diagramm in 500x500Pixel großes Bild umwandeln und den Namen des 
		 * Bildes in dateiName speichern.
		 */
		dateiName = ServletUtilities.saveChartAsPNG(
				vertragDiagramm, 500, 500, request.getSession());

		/*
		 Erstellen der URL, die verwendet werden kann um das Diagramm anzuzeigen.
		 Dazu wird ein DisplayChart-Servlet verwendet, welches das Diagramm-Bild
		 an den aufrufenden Browser weiterleitet.
		 */
		url = request.getContextPath() + "/servlet/DisplayChart?filename="
				+ dateiName;

		//URL des Diagrammbildes als Parameter uebergeben.
		request.setAttribute("StatistikURL", url);
		//Aufrufen der admin_dynamic.jsp
		request.getRequestDispatcher("/admin.jsp").forward(request, response);
		dao.close();
	}

	/**
	 * Ersteller: René Kanzenbach
	 * Datum: 04.08.2015
	 * Version: 1.0
	 *			- 1.1 René Kanzenbach 19.08.2015 
	 *			Der eingelogte Benutzer wird jetzt aus der Ergebnisliste gefiltert.
	 *			- 1.2 René Kanzenbach 27.08.2015
	 *			Die Liste der gefundenen Benutzer wird jetzt in der Session 
	 *			gespeichert.
	 *
	 * Sucht anhand der Benutzereingabe nach Benutzern. Bei der Suche werden nur
	 * Übereinstimmungen in der E-Mail des Benutzers berücksichtigt.
	 * 
	 * Der Benutzer, der die Suche angestoßen hat wird aus der Ergebnisliste 
	 * gefiltert.
	 * Die Liste aller gefundenen Benutzer wird in der HttpSession gespeichert,
	 * um die Benutzer auch nach dem Ändern des Benutzerstatus auflisten zu
	 * können.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void benutzerSuche(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Benutzer> benutzerListe;
		HttpSession session = request.getSession();
		DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
		String benutzerSuche;
		Benutzer benutzer;

		//Benutzer Suchtext auslesen.
		benutzerSuche = request.getParameter("SucheBenutzerText");
		//Eingelogter Benutzer aus der Session lesen.
		benutzer = (Benutzer) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
		//Liste aller Benutzer erzeugen, die mit der Sucher übereinstimmen.
		benutzerListe = dao.sucheBenutzer(benutzerSuche);
		//Aktiver Benutzer aus der Liste der gefundenen Benutzer filtern.
		benutzerListe.remove(benutzer);
		//Benutzerliste an die Session hängen
		session.setAttribute("BenutzerListe", benutzerListe);
		//Weiterleiten auf admin.jsp
		request.getRequestDispatcher("/admin.jsp").forward(request, response);
	}

	/**
	 * Ersteller:	René Kanzenbach
	 * Datum:		19.08.2015
	 * Version:		1.0
	 *				1.1 René Kanzenbach 27.08.2015
	 *				- Updatet jetzt die BenutzerListe mit dem aktuellen Benutzer.
	 *				- Setzt das RequestAttribut "Zeige_BenutzerListe", damit in
	 *				der "admin_dynamic.jsp" die Liste aller Benutzer angezeigt wird.
	 * 
	 * Ließt den Benutzer aus den übergebenen Formularparameter und setzt 
	 * den Status des Benutzers auf "geloescht".
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException 
	 */
	private void benutzerLoeschen(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
		HttpSession session = request.getSession();
		//Liste der Benutzer, die in der "admin_dynamic.jsp" ausgegeben werden
		List<Benutzer> benutzerListe = (List<Benutzer>) 
				session.getAttribute("BenutzerListe");
		//Index des Benutzers innhalb der 'benutzerListe'
		int benIndex = Integer.parseInt(request.getParameter("Ben_Index"));
		
		
		//Benutzer anhand der übergebenen Email suchen.
		Benutzer benutzer = dao.getBenutzer(request.getParameter("Ben_Email"));
		//Benutzerstatus ändern.
		dao.setBenutzerStatus(benutzer, Konstanten.ID_BEN_STATUS_GELOESCHT);
		/* Index des alten Benutzers innerhalb der 'benutzerListe' überschreiben.
		So bleibt die Reihenfolge der aufgelisteten Benutzer innhalb der 
		'admin_dynamic.jsp' bestehen und es wird sofort der neue Benutzerstatus
		angezeigt. */
		benutzerListe.set(benIndex, benutzer);
		/*Parameter 'Zeige_BenutzerListe' übergeben, damit in der 
		'admin_dynamic.jsp' die Benutzerliste angezeigt wird */
		request.setAttribute("Zeige_BenutzerListe", "true");
		//Weiterleitung auf admin.jsp
		request.getRequestDispatcher("admin.jsp").forward(request, response);
	}

	/**
	 * Ersteller:	René Kanzenbach
	 * Datum:		19.08.2015
	 * Version:		1.0
	 *				1.1 René Kanzenbach 27.08.2015
	 *				- Updatet jetzt die BenutzerListe mit dem aktuellen Benutzer.
	 *				- Setzt das RequestAttribut "Zeige_BenutzerListe", damit in
	 *				der "admin_dynamic.jsp" die Liste aller Benutzer angezeigt wird.
	 * 
	 * Ließt den Benutzer aus den übergebenen Formularparameter und setzt 
	 * den Status des Benutzers auf "aktiviert".
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException 
	 */
	private void benutzerAktivieren(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		//Liste der Benutzer, die in der "admin_dynamic.jsp" ausgegeben werden
		List<Benutzer> benutzerListe = (List<Benutzer>) 
				session.getAttribute("BenutzerListe");
		//Index des Benutzers innhalb der 'benutzerListe'
		int benIndex = Integer.parseInt(request.getParameter("Ben_Index"));
		
		DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
		//Benutzer anhand der übergebenen Email suchen.
		Benutzer benutzer = dao.getBenutzer(request.getParameter("Ben_Email"));
		//Benutzerstatus ändern.
		dao.setBenutzerStatus(benutzer, Konstanten.ID_BEN_STATUS_AKTIV);
		/*Index des alten Benutzers innerhalb der 'benutzerListe' überschreiben.
		So bleibt die Reihenfolge der aufgelisteten Benutzer innhalb der 
		'admin_dynamic.jsp' bestehen und es wird sofort der neue Benutzerstatus
		angezeigt.*/
		benutzerListe.set(benIndex, benutzer);
		/*Parameter 'Zeige_BenutzerListe' übergeben, damit in der 
		'admin_dynamic.jsp' die Benutzerliste angezeigt wird */
		request.setAttribute("Zeige_BenutzerListe", "true");
		//Weiterleitung auf admin.jsp
		request.getRequestDispatcher("admin.jsp").forward(request, response);
	}

	/**
	 * Ersteller:	René Kanzenbach
	 * Datum:		20.08.2015
	 * Version:		1.0
	 *
	 * Ließt den aktuellen Benutzer aus der Session und das Passwort aus der 
	 * Benutzereingabe. Ändert dann das Passwort des Benutzers auf das neue 
	 * Passwort.
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException 
	 */
	private void aendereAdminPasswort(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//Ausgaben, die auf dem Bildschirm erscheinen sollen, wenn die Passwörter
		//nicht gültig sind.
		final String FEHLER_PW_NICHT_IDENTISCH = "Die beiden Passwörter stimmen"
				+ " nicht überein! <br>";
		final String FEHLER_PW_ZU_KURZ = "Das Passwort muss mindestens 6 "
				+ "Zeichen lang sein! <br>";

		//Ausgabe wenn das Passwort erfolgreich geändert wurde.
		final String PW_GEAENDERT = "Das Passwort wurde erfolgreich geändert!";

		boolean istPwGueltig = false;

		//Ausgabe die auf dem Bildschirm erscheinen soll, wenn die Passwörter
		//nicht gültig sind.
		String fehlerAusgabe = "";

		//Aktuell eingeloggter Benutzer.
		Benutzer aktuellerBenutzer;

		//Passwort welches der Benutzer eingegeben hat.
		String passwort;

		//Passwort, welches der Benutzer in das "Passwort wiederholen" Feld
		//eingetragen hat.
		String checkPasswort;

		DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
		HttpSession session = request.getSession();

		//Aktuell eingeloggten Benutzer aus der Session laden.
		aktuellerBenutzer = (Benutzer) session.getAttribute(
				Konstanten.SESSION_ATTR_BENUTZER);

		//Passworteingaben aus den übergebenen Http-Parametern lesen.
		passwort = request.getParameter("neues_pw");
		checkPasswort = request.getParameter("check_pw");

		//Prüfen, ob die beiden Passwörter identisch sind.
		if (passwort.equals(checkPasswort)) {
			//Passwörter identisch.
			istPwGueltig = true;
		} else {
			//Passwörter nicht identisch.
			istPwGueltig = false;
			fehlerAusgabe += FEHLER_PW_NICHT_IDENTISCH;
		}

		//Prüfen, ob das Passwort länger als 6 Zeichen ist.
		if (istPwGueltig && passwort.length() >= 6) {
			istPwGueltig = true;
		} else if (passwort.length() < 6) {
			istPwGueltig = false;
			fehlerAusgabe += FEHLER_PW_ZU_KURZ;
		}

		//Prüfen ob Passwort gültig ist.
		if (istPwGueltig) {
			//Passwort ändern.
			dao.setBenutzerPW(aktuellerBenutzer, passwort);
			//Ausgabe als Parameter übergeben.
			request.setAttribute("PW_Ausgabe", PW_GEAENDERT);
			//Weiterleitung auf "admin.jsp".
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		} else {
			//Fehlerausgabe als Parameter übergeben.
			request.setAttribute("PW_Fehler", fehlerAusgabe);
			//Weiterleitung auf "admin.jsp"
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		}
	}

	/**
	 * Ersteller:	René Kanzenbach
	 * Datum:	24.08.2015
	 * Version:	1.0
	 *
	 * Liest die Benutzereingaben aus dem "admin_dynamic.jsp" und erstellt 
	 * daraus einen neuen Adminaccount.
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException 
	 */
	private void erstelleAdminAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//Ausgaben, die auf dem Bildschirm erscheinen sollen, wenn die 
		//Passwörter nicht gültig sind.
		final String FEHLER_PW_NICHT_IDENTISCH = "Die beiden Passwörter stimmen"
				+ " nicht überein! <br>";
		final String FEHLER_PW_ZU_KURZ = "Das Passwort muss mindestens 6 "
				+ "Zeichen lang sein! <br>";
		//Ausgabe, die auf dem Bildschirm erscheinen soll, wenn der Adminaccount
		//bereits existiert.
		final String FEHLER_ADMIN_EXISTIERT_BEREITS = "Der angegebene Nutzername"
				+ " ist bereits vergeben <br>";
		//Ausgabe, wenn der Adminaccount erfolgreich angelegt wurde
		final String ADMIN_ANGELEGT = "Der Adminaccount wurder erfolgreich"
				+ " angelegt!";

		boolean sindEingabenKorrekt = true;
		String fehlerAusgabe = "";
		DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
		String adminName = request.getParameter("admin_name");
		String passwort = request.getParameter("admin_pw");
		String checkPasswort = request.getParameter("check_pw");

		//Prüfen ob der Benutzer bereits existiert
		if (dao.getBenutzer(adminName) == null) {
			//Benutzer existiert noch nicht
			sindEingabenKorrekt = true;
		} else {
			//Adminname ist bereits vergeben
			sindEingabenKorrekt = false;
			fehlerAusgabe += FEHLER_ADMIN_EXISTIERT_BEREITS;
		}

		//Prüfen ob beide Passwörter identisch sind
		if (sindEingabenKorrekt  && passwort.equals(checkPasswort)) {
			//Passwörter identisch.
			sindEingabenKorrekt = true;
		} else if (!passwort.equals(checkPasswort)) {
			//Passwörter nicht identisch.
			sindEingabenKorrekt = false;
			fehlerAusgabe += FEHLER_PW_NICHT_IDENTISCH;
		}

		//Prüfen, ob das Passwort länger als 6 Zeichen ist.
		if (sindEingabenKorrekt && passwort.length() >= 6) {
			//Passwort ist mindestens 6 Zeichen lang
			sindEingabenKorrekt = true;
		} else if (passwort.length() < 6) {
			//Passwort ist zu kurz
			sindEingabenKorrekt = false;
			fehlerAusgabe += FEHLER_PW_ZU_KURZ;
		}
		
		//Prüfen, ob Passwort gültig ist
		if (sindEingabenKorrekt) {
			//Admin anlegen
			dao.addAdmin(adminName, passwort);
			//Erfolgreichausgabe als Parameter übergeben
			request.setAttribute("admin_ausgabe", ADMIN_ANGELEGT);
			//Weiterleitung auf "admin.jsp"
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		} else {
			//Fehlertext als Parameter übergeben
			request.setAttribute("admin_fehler", fehlerAusgabe);
			//Weiterleitung auf "admin.jsp"
			request.getRequestDispatcher("admin.jsp").forward(request, response);
		}
	}
	
	/**
	 * Ersteller:	René Kanzenbach
	 * Datum:	30.08.2015
	 * Version:	1.0
	 *
	 * Setzt das Passwort des im request übergebenen Benutzers zurück.
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException 
	 */
	private void setzePwZurueck(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException {
	    
	    DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
	    SystemManager sm= new SystemManager();
	    Benutzer benutzer = dao.getBenutzer(request.getParameter("Ben_Email"));
            
	    //Passwort des Benutzers zurücksetzen
            sm.setzePasswort(benutzer);
	    //Weiterleitung auf 'admin.jsp'
	    request.getRequestDispatcher("admin.jsp").forward(request, response);
	}

}
