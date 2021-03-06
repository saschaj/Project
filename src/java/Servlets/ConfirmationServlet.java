/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entitys.Benutzer;
import Entitys.Benutzer_Status;
import Entitys.Kunde;
import Hilfsklassen.ZufallsStringErzeuger;
import Manager.DatenZugriffsObjekt;
import Manager.EmailHandler;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 */
public class ConfirmationServlet extends HttpServlet {

	/**
	 * Ersteller:	Mladen Sikiric
	 * Datum:		25.08.2015
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
		
		//Prüfung ob Session abgelaufen ist
		if (!request.isRequestedSessionIdValid()) {
			//Session abgelaufen
			//Weiterleiten auf Startseite
			request.getRequestDispatcher("index.jsp").forward(request, response);
			//Servlet beenden
			return;
		}

		String info = "Unglütiger Link. Es gab Probleme mit der Verarbeitung "
				+ "ihrer Daten. Versuchen Sie es erneut oder lassen Sie sich "
				+ "eine neue E-Mail zusenden.";
		String urlParameter = request.getQueryString();
		String ref = belegeParameter(urlParameter, "ref");
		String action = belegeParameter(urlParameter, "action");
		String user = belegeParameter(urlParameter, "user");
		if (user != null && !user.equals("") && action != null
				&& !action.equals("") && ref != null && !ref.equals("")) {
			DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
			Benutzer b = dao.getBenutzer(user);
			if (b != null) {
				if (action.equals("password") && b.getPasswortZuruecksetzen() != null && b.getPasswortZuruecksetzen().equals(ref)) {
					info = "Ihre Bestätigung war erfolgreich.\n\n"
							+ "In Kürze erhalten Sie eine E-Mail mit dem neuen Passwort.";
					ZufallsStringErzeuger z = new ZufallsStringErzeuger();
					String password = z.holeNeuesPasswort();
					b.setPasswort(password);
					b.setPasswortZuruecksetzen("");
					b = dao.updateBenutzer(b);
					sendeNeuesPasswort(b, password);
				} else if (action.equals("register") && b.getEmailBestaetigung() != null && b.getEmailBestaetigung().equals(ref)) {
					info = "Ihre Bestätigung war erfolgreich.";
					Benutzer_Status bs = dao.getStatusByID(1);
					b.setStatus(bs);
					b.setEmailBestaetigung("");
					dao.updateBenutzer(b);
					Kunde k = dao.getKunde(b.getBenutzerId());
					sendeRegistrierungsMail(k);
				}
			}
			dao.close();
		}

		request.setAttribute("info", info);
		request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
	}

	/**
	 * Ersteller:	Mladen Sikiric
	 * Datum:		25.08.2015
	 *
         * Die Methode sende an den übergebenen Benutzer eine E-Mail mit 
         * dem übergebenen Passwort als Inhalt.
         * 
         * @param b Benutzer
         * @param password Passwort
         */
	private void sendeNeuesPasswort(Benutzer b, String password) {

		EmailHandler e = new EmailHandler();
		e.sendPasswortMail(b, password);
	}

       /**
	 * Ersteller:	Mladen Sikiric
	 * Datum:		25.08.2015
	 *
         * Die Methode sende an den übergebenen Kunden eine E-Mail zur  
         * Bestätigung der abgeschlossenen Registrierung.
         * 
         * @param k Kunde
         */
	private void sendeRegistrierungsMail(Kunde k) {
		EmailHandler e = new EmailHandler();
		e.sendRegisterMail("Registrierung für " + k.getVorname() + " "
				+ k.getNachname() + " abgeschlossen", k.getEmail());
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

        /**
	 * Ersteller:	Mladen Sikiric
	 * Datum:		25.08.2015
         * 
         * Diese Methode zerlegt eine URL in Tokens. Trenner sind ? und =
         * Auf diese Weise können in der URL eingebette Parameter gefunden
         * werden.
         * 
         * @param urlParameter Vollständige URL
         * @param feldname Parametername
         * @return null oder Parameterstring
         */
	private String belegeParameter(String urlParameter, String feldname) {
		String parameter = null;
		if (urlParameter != null) {
			StringTokenizer st1 = new StringTokenizer(urlParameter, "?");
			while (st1.hasMoreTokens()) {
				StringTokenizer st2 = new StringTokenizer(st1.nextToken(), "=");
				while (st2.hasMoreTokens()) {
					if (feldname.equals(st2.nextToken())) {
						parameter = st2.nextToken();
					}
				}
			}
		}
		return parameter;
	}
}
