package Manager;

import Entitys.Benutzer;
import Entitys.Kunde;
import Entitys.Vertrag;
import Entitys.Zeit_Einheit;
import Hilfsklassen.Konstanten;
import Hilfsklassen.ZufallsStringErzeuger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author René
 */
@Stateless
public class SystemManager {
	/* EmailHanlder zum verschicken der Emails */

	private EmailHandler emailer;

	public SystemManager() {
		emailer = new EmailHandler();
	}

	/**
	 * Ersteller: Mladen Sikiric 
	 * Erstelldatum: 20.08.2015 
	 * 
	 * Einmal Täglich wird zu
	 * jedem Vertrag geprüft ob eine Benachrichtigung verschickt werden muss.
	 */
	@Schedule(minute = "*/0")
	private void pruefeAufAblaufendeFristen() {
		Calendar now;
		DatenZugriffsObjekt dao;

		ArrayList<Vertrag> alleVertraege;

		HashMap<Kunde, ArrayList<Vertrag>> ablaufendeVertraege = new HashMap<>();

		now = Calendar.getInstance();
		dao = new DatenZugriffsObjekt();
		alleVertraege = new ArrayList(dao.getAllVertraege());
		for (Vertrag v : alleVertraege) {
			Calendar benachrichtigungsDatum = Calendar.getInstance(Locale.GERMANY);
			benachrichtigungsDatum.setTime(v.getVertragEnde());

			int benachrichtigungsFrist = v.getBenachrichtigungsfrist();
			int kuendigungsFrist = v.getKuendigungsfrist();
			Zeit_Einheit bfEinheit = v.getBenachrichtigungsfristEinheit();
			Zeit_Einheit kfEinheit = v.getKuendigungsfristEinheit();

			datumAendern(benachrichtigungsDatum, kfEinheit, -kuendigungsFrist);
			datumAendern(benachrichtigungsDatum, bfEinheit, -benachrichtigungsFrist);
			Kunde k = v.getKunde();
			if ((k.getStatus().getBenutzerStatusId() == Konstanten.ID_BEN_STATUS_AKTIV) && benachrichtigungsDatum.before(now) && !v.isBenachrichtigungVersand()) {

				ArrayList<Vertrag> tmpList;
				if (ablaufendeVertraege.containsKey(k)) {
					tmpList = ablaufendeVertraege.get(k);
				} else {
					tmpList = new ArrayList<>();
				}
				v.setBenachrichtigungVersand(true);
				dao.addContract(v);
				tmpList.add(v);
				ablaufendeVertraege.put(k, tmpList);
			}
		}

		for (HashMap.Entry<Kunde, ArrayList<Vertrag>> entry
				: ablaufendeVertraege.entrySet()) {
			emailer.sendeAblaufbenachrichtigung(
					(Kunde) entry.getKey(), (ArrayList) entry.getValue());
		}
	}

	/**
	 * Ersteller: Mladen Sikiric
	 * Erstelldatum: 20.08.2015 
	 * 
	 * Diese Methode ändert ein Datum anhand der übergebenen Parameter
	 *
	 * @param cal Zu änderndes Datum
	 * @param ze Zeiteinheit die geändert werden soll
	 * @param frist Wert um den die Zeiteinheit geändert werden soll
	 */
	public static void datumAendern(Calendar cal, Zeit_Einheit ze, int frist) {
		switch (ze.getName()) {
			case "Tag(e)":
				cal.add(Calendar.DAY_OF_YEAR, frist);
				break;
			case "Woche(n)":
				cal.add(Calendar.WEEK_OF_YEAR, frist);
				break;
			case "Monat(e)":
				cal.add(Calendar.MONTH, frist);
				break;
			case "Jahr(e)":
				cal.add(Calendar.YEAR, frist);
				break;
		}
	}

	/**
	 * Ersteller: Mladen Sikiric 
	 * Erstelldatum: 01.09.2015
	 *
	 * Der übergebene Benutzer erhält ein neues Passwort, welches von der
	 * Methode zurückgeliefert wird.
	 *
	 * @param b Benutezr dessen Passwort geändert werden soll
	 * @return Das geänderte Passwort
	 */
	public String setzePasswort(Benutzer b) {
		DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
		ZufallsStringErzeuger z = new ZufallsStringErzeuger();
		String password = z.holeNeuesPasswort();
		b.setPasswort(password);
		b.setPasswortZuruecksetzen("");
		dao.updateBenutzer(b);
		dao.close();
		return password;
	}
}
