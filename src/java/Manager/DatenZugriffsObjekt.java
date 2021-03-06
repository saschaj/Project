package Manager;

import javax.persistence.*;
import Entitys.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import Hilfsklassen.Konstanten;
import java.awt.Color;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author René
 */
public class DatenZugriffsObjekt {

    private final EntityManager entityManager;

    /**
     * Konstruktor
     */
    public DatenZugriffsObjekt() {

        this.entityManager = Persistence
                .createEntityManagerFactory("VertragsverwaltungPU")
                .createEntityManager();

        initializeDB();
    }

    /**
	 * Ersteller:	Mladen Sikiric
	 * Datum:		25.08.2015 
     * 
     *  Diese Funktion wird jedesmal beim Aufruf des DatenZugriffsObjekt ge-
     *  startet. Sie prüft Anhand vorhandener Benutzer ob die Datenbank schon
     * für die erste Inbetriebnahme initialisiert wurde.
     * 
     */
    public void initializeDB() {
         /* 
             Es gibt keinen Benutzer, keinen Admin, das heißt es wird ange-
             das die Datenbank zum ersten mal gestartet wird. Oder jemand
             alle Admins gelöscht hat...
             Zusätzlich werden benötigte Felder via SQL inserts in die 
             Datenbank geschrieben.
             */
        if (this.entityManager.find(Benutzer.class, 1) == null) {
            this.entityManager.getTransaction().begin();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_RECHT (BENUTZER_RECHT_ID, \"NAME\", BESCHREIBUNG) VALUES (1, 'Benutzer_verwalten', 'Das Recht BenutzerAccountDaten zu hinzuzufügen, aendern und zu loeschen')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_RECHT (BENUTZER_RECHT_ID, \"NAME\", BESCHREIBUNG) VALUES (2, 'Vertrag_verwalten', 'Das Recht Verträge anzulegen, zu ändern und zu löschen.')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_RECHT (BENUTZER_RECHT_ID, \"NAME\", BESCHREIBUNG) VALUES (3, 'Statistik_anzeigen', 'Das Recht sich Statistiken anzeigen zu lassen.')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_RECHT (BENUTZER_RECHT_ID, \"NAME\", BESCHREIBUNG) VALUES (4, 'Fremde_Benutzer_verwalten', 'Das Recht fremde BenutzerAccountDaten zu hinzuzufügen, zu ändern und zu löschen')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_RECHT (BENUTZER_RECHT_ID, \"NAME\", BESCHREIBUNG) VALUES (5, 'Benutzer_wiederherstellen', 'Das Recht Benutzeraccounts wiederherzustellen.')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_RECHT (BENUTZER_RECHT_ID, \"NAME\", BESCHREIBUNG) VALUES (6, 'Benutzer', 'Das Recht alle für einen Benutzer geeigneten Webseiten aufzurufen.')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_RECHT (BENUTZER_RECHT_ID, \"NAME\", BESCHREIBUNG) VALUES (7, 'Admin', 'Das Recht alle für einen Admin geeigneten Webseiten aufzurufen.')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_STATUS (BENUTZER_STATUS_ID, \"NAME\", BESCHREIBUNG) VALUES (1, 'Aktiv', 'Benutzer ist normal im System registriert und der Account kann genutzt werden.')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_STATUS (BENUTZER_STATUS_ID, \"NAME\", BESCHREIBUNG) VALUES (2, 'Gelöscht', 'Benutzer ist als gelöscht markiert. Der Account kann nicht genutzt werden.')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO BENUTZER_STATUS (BENUTZER_STATUS_ID, \"NAME\", BESCHREIBUNG) VALUES (3, 'Unbestätigt', 'Der Benutzer hat noch nicht den Registrationslink bestätigt. Der Account kann nicht genutzt werden.')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO ZEIT_EINHEIT (ZEITEINHEITID, \"NAME\", BESCHREIBUNG) VALUES (1, 'Tag(e)', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO ZEIT_EINHEIT (ZEITEINHEITID, \"NAME\", BESCHREIBUNG) VALUES (2, 'Woche(n)', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO ZEIT_EINHEIT (ZEITEINHEITID, \"NAME\", BESCHREIBUNG) VALUES (3, 'Monat(e)', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO ZEIT_EINHEIT (ZEITEINHEITID, \"NAME\", BESCHREIBUNG) VALUES (4, 'Jahr(e)', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (1, 'Audio- und Hifimagazin', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (2, 'Automobilzeitschrift', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (3, 'Computermagazin', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (4, 'Fachzeitschrift', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (5, 'Fitnessmagazin', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (6, 'Gartenmagazin', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (7, 'Kindermagazin', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (8, 'Kochen & Rezepte', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (9, 'Reisemagazin', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (10, 'Sonstiges', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (11, 'Tageszeitung', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (12, 'Wissensmagazin', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO INTERESSENGEBIET (INTERESSENGEBIET_ID, \"NAME\", BESCHREIBUNG) VALUES (13, 'Wohnideenmagazin', '')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO NETZTYP(NETZTYPID, \"NAME\", BESCHREIBUNG, IST_HANDY_TYP, IST_FESTNETZ_TYP) VALUES (1, 'GPRS', '', 1, 0)").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO NETZTYP(NETZTYPID, \"NAME\", BESCHREIBUNG, IST_HANDY_TYP, IST_FESTNETZ_TYP) VALUES (2, 'EDGE', '', 1, 0)").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO NETZTYP(NETZTYPID, \"NAME\", BESCHREIBUNG, IST_HANDY_TYP, IST_FESTNETZ_TYP) VALUES (3, 'UMTS', '', 1, 0)").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO NETZTYP(NETZTYPID, \"NAME\", BESCHREIBUNG, IST_HANDY_TYP, IST_FESTNETZ_TYP) VALUES (4, 'HSDPA', '', 1, 0)").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO NETZTYP(NETZTYPID, \"NAME\", BESCHREIBUNG, IST_HANDY_TYP, IST_FESTNETZ_TYP) VALUES (5, 'LTE', '', 1, 1)").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO NETZTYP(NETZTYPID, \"NAME\", BESCHREIBUNG, IST_HANDY_TYP, IST_FESTNETZ_TYP) VALUES (6, 'DSL', '', 0, 1)").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO NETZTYP(NETZTYPID, \"NAME\", BESCHREIBUNG, IST_HANDY_TYP, IST_FESTNETZ_TYP) VALUES (7, 'VDSL', '', 0, 1)").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO VERTRAG_ART(VERTRAG_ART_ID, \"NAME\") VALUES(1, 'Festnetzvertrag')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO VERTRAG_ART(VERTRAG_ART_ID, \"NAME\") VALUES(2, 'Gasvertrag')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO VERTRAG_ART(VERTRAG_ART_ID, \"NAME\") VALUES(3, 'Handyvertrag')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO VERTRAG_ART(VERTRAG_ART_ID, \"NAME\") VALUES(4, 'Stromvertrag')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO VERTRAG_ART(VERTRAG_ART_ID, \"NAME\") VALUES(5, 'Zeitschriftvertrag')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO VERTRAG_STATUS(VERTRAG_STATUS_ID, \"NAME\", BESCHREIBUNG) VALUES(1, 'aktiv', 'Der Vertrag ist zurzeit noch aktiv.')").executeUpdate();
            this.entityManager.createNativeQuery("INSERT INTO VERTRAG_STATUS(VERTRAG_STATUS_ID, \"NAME\", BESCHREIBUNG) VALUES(2, 'gekündigt', 'Der Vertrag wurde gekündigt.')").executeUpdate();
//			
			//Admin anlegen
//			this.entityManager.createNativeQuery("INSERT INTO BENUTZER(BENUTZERID, DTYPE, EMAIL, PASSWORT, BENUTZER_STATUS_ID) "
//					+ "VALUES(1, 'Benutzer', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', 1)").executeUpdate();
//			this.entityManager.createNativeQuery("INSERT INTO BENUTZER_RECHT_ZUORDNUNG(BENUTZER_BENUTZERID,RECHTE_BENUTZER_RECHT_ID) "
//					+ "VALUES(1, 7)").executeUpdate();
			
			Benutzer admin = new Benutzer();
			
			admin.setEmail("admin");
			admin.setPasswort("1234");
			admin.setStatus(this.entityManager.find(Benutzer_Status.class,
					Konstanten.ID_BEN_STATUS_AKTIV));
			admin.addRecht(this.entityManager.find(Benutzer_Recht.class,
					Konstanten.ID_BEN_RECHT_ADMIN_ANSICHT));
			this.entityManager.persist(admin);
			
            this.entityManager.getTransaction().commit(); 
        }
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 06.06.2015 
     * Methode: getKunde
     * Version: 1.0 
     * 
     * Holt sich aus der Datenbank ein Kundenobjekt mit Hilfe einer 
     * übergebenen BenutzerID.
     * @param benutzerId ID des Kunden
     * @return Kundenobjekt aus der Datenbank
     */
    public Kunde getKunde(int benutzerId) {
        return entityManager.find(Kunde.class, benutzerId);
    }

    /**
     * Ersteller: Julie Kenfack Erstelldatum: 20.08.2015 Methode:
     * updateKundeDaten Version: 1.0 Die Methode soll den in der
     * Datenbank,bestehenden Kunden mit den übergebenen Kundendaten
     * aktualisieren.
     *
     * @param vorname
     * @param nachname
     * @param straße
     * @param hausnummer
     * @param plz
     * @param wohnort
     * @param nummer
     * @return true, wenn die Aktualisierung der Kundendaten erfolgreich war und
     * false, wenn nicht
     */
    public boolean updateKundeDaten(String vorname, String nachname,
            Date gebdt, String nummer, int benutzerId) {
        boolean istAktualisiert = false;
        Kunde k = entityManager.find(Kunde.class, benutzerId);
        entityManager.getTransaction().begin();
        try {

            k.setGeburtsdatum(gebdt);
            k.setVorname(vorname);
            k.setNachname(nachname);
            k.setGeburtsdatum(gebdt);
            k.setTelefonnummer(nummer);

            entityManager.getTransaction().commit();
            istAktualisiert = true;
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();

        }
        return istAktualisiert;
    }

    /**
     * Ersteller: Julie Kenfack 
     * Erstelldatum: 20.08.2015 
     * Methode: updateAdresseDaten Version: 1.0
     *
     * Die Methode soll den in der Datenbank,bestehenden Kunden mit den
     * übergebenen Adresse aktualisieren.
     *
     * @param vorname
     * @param nachname
     * @param straße
     * @param hausnummer
     * @param plz
     * @param wohnort
     * @param nummer
     * @return true, wenn die Aktualisierung der Adresse erfolgreich war und
     * false, wenn nicht
     */
    public boolean updateAdresse(String strasse, String hsnum, String plz, String ort, String land,
            int adresseId) {
        Adresse ad = entityManager.find(Adresse.class, adresseId);
        boolean istAktualisiert = false;

        entityManager.getTransaction().begin();
        try {
            ad.setStrasse(strasse);
            ad.setHausNr(hsnum);
            ad.setPlz(plz);
            ad.setOrt(ort);
            ad.setLand(land);
            entityManager.getTransaction().commit();

            istAktualisiert = true;
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();

        }
        return istAktualisiert;
    }

    /**
     * Ersteller: Julie Kenfack 
     * Erstelldatum: 20.08.2015 
     * Methode: updateKundeDaten 
     * Version: 1.0
     *
     * Die Methode soll den in der Datenbank,bestehenden benutzer mit den
     * übergebenen Benutzerdaten aktualisieren.
     *
     * @param email
     * @param passwort
     * @return true, wenn die Aktualisierung der Benutzerdaten erfolgreich war
     * und false, wenn nicht
     */
    public boolean updateBenutzerDaten(String email, String passwort, int id) {
        Benutzer ben = entityManager.find(Benutzer.class, id);
        boolean istAktualisiert = false;

        entityManager.getTransaction().begin();
        try {
            ben.setEmail(email);
            ben.setPasswort(passwort);
            entityManager.getTransaction().commit();

            istAktualisiert = true;
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();

        }
        return istAktualisiert;
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 15.08.2015 
     * Methode: updateVertrag
     * Version: 1.0 
     * 
     * Methode zum Update eines Vertrags, wenn der Kunde ihn ändern möchte.
     *
     * @param vertrag Zu verändernde Vertrag
     * @return true, bei Erfolg.. false, bei Nicht-Erfolg
     */
    public boolean updateVertrag(Vertrag vertrag) {
        // Initialisierung der benötigten Variablen
        boolean istAktualisiert = false;
        Vertrag alterVertrag
                = this.entityManager.find(Vertrag.class, vertrag.getVertragId());

        // Starten der Transaktion
        this.entityManager.getTransaction().begin();

        try {
            // Setze die Vertragsdaten
            alterVertrag.setVertragsBezeichnung(
                    vertrag.getVertragsBezeichnung());
            alterVertrag.setVertragsPartner(vertrag.getVertragsPartner());
            alterVertrag.setBenachrichtigungsfrist(
                    vertrag.getBenachrichtigungsfrist());
            alterVertrag.setBenachrichtigungsfristEinheit(
                    vertrag.getBenachrichtigungsfristEinheit());
            alterVertrag.setKundenNr(vertrag.getKundenNr());

            // Setze die vertragsspezifischen Daten
            switch (alterVertrag.getVertragArt().getVertragArtId()) {
                case Konstanten.ID_VERTRAG_ART_STROM:
                    ((Stromvertrag) alterVertrag).setStromzaehlerNr(
                            ((Stromvertrag) vertrag).getStromzaehlerNr());
                    ((Stromvertrag) alterVertrag).setStromzaehlerStand(
                            ((Stromvertrag) vertrag).getStromzaehlerStand());
                    ((Stromvertrag) alterVertrag).setVerbrauchProJahr(
                            ((Stromvertrag) vertrag).getVerbrauchProJahr());
                    ((Stromvertrag) alterVertrag).setPreisProKwh(
                            ((Stromvertrag) vertrag).getPreisProKwh());
                    ((Stromvertrag) alterVertrag).setGrundpreisMonat(
                            ((Stromvertrag) vertrag).getGrundpreisMonat());
                    ((Stromvertrag) alterVertrag).setAnzPersonenHaushalt(
                            ((Stromvertrag) vertrag).getAnzPersonenHaushalt());
                    break;
                case Konstanten.ID_VERTRAG_ART_GAS:
                    ((Gasvertrag) alterVertrag).setGaszaehlerNr(
                            ((Gasvertrag) vertrag).getGaszaehlerNr());
                    ((Gasvertrag) alterVertrag).setGaszaehlerStand(
                            ((Gasvertrag) vertrag).getGaszaehlerStand());
                    ((Gasvertrag) alterVertrag).setPreisProKhw(
                            ((Gasvertrag) vertrag).getPreisProKhw());
                    ((Gasvertrag) alterVertrag).setVerbrauchProJahr(
                            ((Gasvertrag) vertrag).getVerbrauchProJahr());
                    ((Gasvertrag) alterVertrag).setVerbrauchsFlaeche(
                            ((Gasvertrag) vertrag).getVerbrauchsFlaeche());
                    break;
                case Konstanten.ID_VERTRAG_ART_FESTNETZ:
                    ((Festnetzvertrag) alterVertrag).setTarifname(
                            ((Festnetzvertrag) vertrag).getTarifname());
                    ((Festnetzvertrag) alterVertrag).setNetztypp(
                            ((Festnetzvertrag) vertrag).getNetztypp());
                    ((Festnetzvertrag) alterVertrag).setIstISDN(
                            ((Festnetzvertrag) vertrag).isIstISDN());
                    ((Festnetzvertrag) alterVertrag).setIstVOIP(
                            ((Festnetzvertrag) vertrag).isIstVOIP());
                    break;
                case Konstanten.ID_VERTRAG_ART_HANDY:
                    ((Handyvertrag) alterVertrag).setTarifname(
                            ((Handyvertrag) vertrag).getTarifname());
                    ((Handyvertrag) alterVertrag).setRufnummer(
                            ((Handyvertrag) vertrag).getRufnummer());
                    ((Handyvertrag) alterVertrag).setNetztyp(
                            ((Handyvertrag) vertrag).getNetztyp());
                    break;
                case Konstanten.ID_VERTRAG_ART_ZEITSCHRIFT:
                    ((Zeitschriftvertrag) alterVertrag).setZeitschriftName(
                            ((Zeitschriftvertrag) vertrag).
                            getZeitschriftName());
                    ((Zeitschriftvertrag) alterVertrag).
                            setLieferintervall(
                                    ((Zeitschriftvertrag) vertrag).
                                    getLieferintervall());
                    ((Zeitschriftvertrag) alterVertrag).
                            setLieferintervallEinheit(
                                    ((Zeitschriftvertrag) vertrag).
                                    getLieferintervallEinheit());
                    ((Zeitschriftvertrag) alterVertrag).setInteressengebiet(
                            ((Zeitschriftvertrag) vertrag).
                            getInteressengebiet());
                    break;
            }
            // Abschließen der Transaktion
            entityManager.getTransaction().commit();
            // Bei Erfolg wird die Returnvariable auf true gesetzt
            istAktualisiert = true;
        } catch (PersistenceException pe) {
            // Rollback, falls etwas schiefgelaufen ist
            this.entityManager.getTransaction().rollback();
        }

        return istAktualisiert;
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 16.08.2015 
     * Methode: loescheVertrag
     * Version: 1.0 
     * 
     * Hier wird ein Vertrag in der Datenbank als gelöscht markiert
     * 
     * @param vertragID Primärschlüssel des Vertrags
     * @return true, bei Erfolg.. false, bei Misserfolg
     */
    public boolean loescheVertrag(int vertragID) {
        // Returnvariable initialiseren
        boolean istGeloescht = false;
        // Vertrag mittels find() aus der Datenbank holen
        Vertrag vertrag = this.entityManager.find(Vertrag.class, vertragID);

        try {
            // Transaktion starten, setten & committen
            this.entityManager.getTransaction().begin();
            vertrag.setIstGeloescht(true);
            this.entityManager.getTransaction().commit();
            // Bei Erfolg wird die Returnvariable auf true gesetezt
            istGeloescht = true;
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        }

        return istGeloescht;
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 01.09.2015 
     * Methode: aktualsiereKunde 
     * Version: 1.0
     *
     * Nachdem ein Vertrag erfolgreich angelegt wurde, wird diese Methode
     * aufgerufen, damit das Kundenobjekt in der Session immer die aktuellen
     * Verträge eines Kunden besitzt
     *
     * @param k alte Kundenobjekt
     * @return neue Kundenobjekt
     */
    public Benutzer aktualisiereKunde(Benutzer k) {
        // Benutzer mittels find() aus der Datenbank holen
        Benutzer ku = this.entityManager.find(Benutzer.class, k.getBenutzerId());
        try {
            // Transaktion starten, Objekt aktualisieren & committen
            this.entityManager.getTransaction().begin();
            this.entityManager.refresh(ku);
            this.entityManager.getTransaction().commit();
        } catch (RollbackException re) {
            this.entityManager.getTransaction().rollback();
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }
        return ku;
    }
    
    /**
     * Ersteller: Sascha Jungenkrüger
     * Datum: 27.08.2015 
     * Methode: verlaengereVertrag
     * Version: 1.0 
     * 
     * Methode um die Vertragsverlängerung in die Datenbank zu schreiben.
     *
     * @param vertragID Primärschlüssel des Vertrags
     * @param neuBeginn neue Vertragsbeginn
     * @param neuEnde neue Vertragsende
     * @return true, wenn erfolgreich.. false, wenn nicht erfolgreich
     */
    public boolean verlaengereVertrag(int vertragID, int laufzeit, 
            Zeit_Einheit einheit, java.util.Date neuBeginn, java.util.Date neuEnde) {
        // Returnvariable initialisieren
        boolean istVerlaengert = false;
        // Vertrag aus Datenbank holen
        Vertrag v = this.entityManager.find(Vertrag.class, vertragID);

        try {
            // Starte Transaktion
            this.entityManager.getTransaction().begin();
            // Setten der Variablen für die Verlängerung
            v.setVertragBeginn(neuBeginn);
            v.setVertragEnde(neuEnde);
            v.setLaufzeit(laufzeit);
            v.setLaufzeitEinheit(einheit);
            v.setBenachrichtigungVersand(false);
            v.setIstGeloescht(false);
            // Commit durchführen
            this.entityManager.getTransaction().commit();
            // Vertrag wurde verlängert
            istVerlaengert = true;

        } catch (RollbackException re) {
            this.entityManager.getTransaction().rollback();
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }

        return istVerlaengert;
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 02.05.2015 
     * Methode: addBenutzer
     * Version: 1.0 
     * 
     * Methode fügt einen Benutzer zur Datenbank hinzu.
     * 
     * @param b Das zu speichernde Benutzerobjekt
     * @return true, bei Erfolg.. false, bei Misserfolg
     */
    public boolean addBenutzer(Benutzer b) {
        // Returnvariable initialisieren
        boolean addComplete = false;

        try {
            // Transaktion starten, Objekte persistieren & committen
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(b);
            this.entityManager.getTransaction().commit();
            // Bei Erfolg boolsche Variable auf true setzen
            addComplete = true;

        } catch (RollbackException re) {
            this.entityManager.getTransaction().rollback();
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }

        return addComplete;
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 02.06.2015 
     * Methode: isEmailAvailable
     * Version: 1.0
     *          1.1 Mladen Sikiric
     *              - Aufruf der getBenutzer & Überprüfung, ob der 
     *                Benutzer existiert
     * 
     * Methode zur Überprüfung, ob die E-Mail Adresse schon existiert
     *
     * @param email Zu überprüfende E-Mail
     * @return true, wenn sie existiert.. false, wenn sie nicht existiert
     */
    public boolean isEmailAvailable(String email) {
        Benutzer b = getBenutzer(email);
        return b == null;
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 02.06.2015 
     * Methode: addContract
     * Version: 1.0 
     * 
     * Methode zum Hinzufügen eines Vertrags
     *
     * @param vertrag Der hinzuzufügende Vertrag
     * @return true, wenn erfolgreich.. false, wenn nicht erfolgreich
     */
    public boolean addContract(Vertrag vertrag) {
        // Boolsche Returnvariable initialisieren
        boolean addComplete = false;

        try {
            // Transaktion starten, Objekt persistieren & speichern
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(vertrag);
            this.entityManager.getTransaction().commit();
            // Bei Erfolg Rückgabevariable auf true setzen
            addComplete = true;

        } catch (RollbackException re) {
            this.entityManager.getTransaction().rollback();
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }

        return addComplete;
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Erstelldatum: 08.06.2015 
     * Methode: register
     * Version: -1.0 
     *          -1.1 René Kanzenbach 11.06.2015 
     *          -Dem Benutzer wird jetzt bei der Registrierung das 
     *           Recht "Benutzer_Ansicht" verliehen. 
     *          -1.2 René Kanzenbach 22.07.2015 
     *          -Dem Benutzer wird jetzt bei der Registrierung der
     * Status "Aktiv" verliehen.
     *          - 1.3 Julie Kenfack 25.08.2015
     *           Leere Adresse an den neuen Benutzer gesetzt.
     * Die Methode schreibt einen neuen Kunden in die Datenbank.
     *
     * @param vname Vorname des Benutzer
     * @param name Nachname des Benutzer
     * @param email E-Mail Adresse des Benutzer
     * @param passwort Verschlüsselte Passwort des Benutzer
     * @return true, wenn die Registrierung erfolgreich war false, wenn die
     * Registrierung fehlgeschlagen ist
     */
   public boolean register(String vname, String name, String email, String passwort) {
        boolean istRegistriert = false;
        Kunde neuerKunde = new Kunde();
        Benutzer_Recht recht = this.entityManager.find(Benutzer_Recht.class,
                Konstanten.ID_BEN_RECHT_BENUTZER_ANSICHT);
        Adresse adr = new Adresse();

        neuerKunde.setVorname(vname);
        neuerKunde.setNachname(name);
        neuerKunde.setEmail(email);
        neuerKunde.setPasswort(passwort);
        neuerKunde.addRecht(recht);
        neuerKunde.setStatus(this.entityManager.find(Benutzer_Status.class,
                Konstanten.ID_BEN_STATUS_UNBESTAETIGT));
        
        adr.setHausNr("");
        adr.setLand("");
        adr.setOrt("");
        adr.setPlz("");
        adr.setStrasse("");
        neuerKunde.setAdresse(adr);

        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(neuerKunde);
            this.entityManager.persist(adr);
            this.entityManager.getTransaction().commit();
            istRegistriert = true;
        } catch (RollbackException re) {
			System.out.println(re.getMessage());
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }

        return istRegistriert;
    }

    /**
     * Ersteller: René Kanzenbach 
     * Datum: 02.06.2015 
     * Methode: getBenutzer
     * Version: 1.0 1.1 René Kanzenbach 20.07.2015 
     *              -Fehler behoben. Wirft jetzt
     *              keine NullpointerException mehr
     *
     * Sucht einen Benutzer anhand der übergebenen E-Mail aus der Datenbank und
     * gibt diesen zurück. Wird kein Benutzer mit der gesuchten E-Mail gefunden
     * gibt die Methode eine NULL-Referenz zurück.
     *
     * Diese Methode setzt vorraus, dass eine E-Mail nur einmal in der Datenbank
     * abgespeichert ist.
     *
     * @param eMail EMail-Adresse des gesuchten Benutzers.
     *
     * @return Benutzer Falls ein Benutzer gefunden wurde, wird eine
     * BenutzerEntity zurückgegeben. Wird kein Benutzer gefunden wird NULL
     * zurückgegeben.
     */
    public Benutzer getBenutzer(String eMail) {

        List<Benutzer> benutzerListe;
        Query query;
        Iterator iterator;
        Benutzer benutzer;

        //Benutzer mit gleicher EMail-Adresse suchen
        query = this.entityManager.createQuery(""
                + "SELECT ben "
                + "FROM Benutzer ben "
                + "WHERE ben.email LIKE '" + eMail + "' ");
        query.setMaxResults(1);
        benutzerListe = query.getResultList();

        //Iterator holen
        iterator = benutzerListe.iterator();

        if (iterator.hasNext()) {
            benutzer = (Benutzer) iterator.next();
        } else {
            benutzer = null;
        }

        return benutzer;
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 29.07.2015 
     * Methode: getEinheiten
     * Version: 1.0
     *
     * Liest alle Zeiteinheiten aus der Datenbank und gibt Sie zurück.
     *
     * @return Eine Liste mit allen Zeit_Einheiten der Datenbank
     */
    public List<Zeit_Einheit> getEinheiten() {
        return this.entityManager.createQuery(
                "SELECT einheit FROM Zeit_Einheit einheit"
        ).getResultList();
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 29.07.2015 
     * Methode: getEinheit
     * Version: 1.0
     *
     * Liest Zeiteinheiten aus der Datenbank und gibt es zurück.
     *
     * @param einheitID Primärschlüssel der ZeitEinheit
     * @return Ein Objekt der übergebenen Zeit_Einheiten
     */
    public Zeit_Einheit getZeitEinheit(int einheitID) {
        return this.entityManager.find(Zeit_Einheit.class, einheitID);
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 29.07.2015 
     * Methode: getInteressengebiete 
     * Version: 1.0
     *
     * Liest alle Interessengebiete aus der Datenbank und gibt Sie zurück.
     *
     * @return Eine Liste mit allen Interessengebieten der Datenbank
     */
    public List<Interessengebiet> getInteressengebiete() {
        return this.entityManager.createQuery(
                "SELECT gebiet FROM Interessengebiet gebiet"
        ).getResultList();
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 29.07.2015 
     * Methode: getInteressengebiet 
     * Version: 1.0
     *
     * Liest ein Interessengebiet aus der Datenbank und gibt das Objekt zurück.
     *
     * @param gebietID Primärschlüssel des Interessengebiet
     * @return Ein Objekt mit speziellem Interessengebieten
     */
    public Interessengebiet getInteressengebiet(int gebietID) {
        return this.entityManager.find(Interessengebiet.class, gebietID);
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 29.07.2015 
     * Methode: getNetztyp
     * Version: 1.0
     *
     * @param netztypID Primärschlüssel für ein Netztyp-Objekt
     * @return Liefert ein Objekt der übergebenen ID zurück
     */
    public Netztyp getNetztyp(int netztypID) {
        return this.entityManager.find(Netztyp.class, netztypID);
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 29.07.2015 
     * Methode: getNetztypen
     * Version: 1.0
     *
     * Liest alle Netztypen aus der Datenbank und gibt Sie zurück.
     *
     * @param istHandyTyp true oder false
     * @param istFestnetzTyp true oder false
     * @return Eine Liste mit allen Netztypen der Datenbank
     */
    public List<Netztyp> getNetztypen(boolean istHandyTyp, boolean istFestnetzTyp) {
        // Query mit Übergabeparamaeter erzeugen
        Query query = this.entityManager.createQuery(
                "SELECT typ FROM Netztyp typ "
                + "WHERE typ.istHandyTyp = :HandyTyp "
                + "OR typ.istFestnetzTyp = :FestnetzTyp"
        );
        // Übergabeparamter setzen
        query.setParameter("HandyTyp", istHandyTyp);
        query.setParameter("FestnetzTyp", istFestnetzTyp);
        
        // Ergebnisse ausgeben
        return query.getResultList();
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 29.07.2015 
     * Methode: getVertrag
     * Version: 1.0
     *
     * Liest einen Vertrag über den übergebenen Index aus der Datenbank und
     * liefert das entsprechende Objekt zurück.
     *
     * @param vertragID Primärschlüssel für ein Objekt des Vertrags
     * @return Ein Objekt den gewünschten Vertrag der Datenbank
     */
    public Vertrag getVertrag(int vertragID) {
        return this.entityManager.find(Vertrag.class, vertragID);
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 29.07.2015 
     * Methode: getVertragsArt
     * Version: 1.0
     *
     * Liest eine Vertragsart über den übergebenen Index aus der Datenbank und
     * liefert das entsprechende Objekt zurück.
     *
     * @param artID Primärschlüssel für ein Objekt der Vertragsart
     * @return Ein Objekt der gewünschten VertragsArt der Datenbank
     */
    public Vertrag_Art getVertragsArt(int artID) {
        return this.entityManager.find(Vertrag_Art.class, artID);
    }

    /**
     * Ersteller: Sascha Jungenkrüger 
     * Datum: 29.07.2015 
     * Methode: getVertragsStatus 
     * Version: 1.0
     *
     * Liest eine VertragsStatus über den übergebenen Index aus der Datenbank
     * und liefert das entsprechende Objekt zurück.
     *
     * @param statusID Primärschlüssel für ein Objekt der VertragsStatus
     * @return Ein Objekt des gewünschten VertragsStatus
     */
    public Vertrag_Status getVertragsStatus(int statusID) {
        return this.entityManager.find(Vertrag_Status.class, statusID);
    }

    /**
     * Ersteller: René Kanzenbach 
     * Datum:	28.07.2015 
     * Version:	1.0 1.1 
     * René Kanzenbach 01.09.2015 
     *          - Die verschiedenen Status bekommen jetzt immer
     *              eine feste Farbe zugewiesen. 
     *              1.2 
     * René Kanzenbach 03.09.2015 - Benutzer
     *  die Adminrechte haben werden der Statistik nicht mehr hinzugefügt.
     *
     * Erzeugt ein Tortendiagramm, welches anzeigt, wie viele Benutzer im System
     * registriert sind und welchen Status diese besitzen. Die unterschiedlichen
     * Status bekommen dabei immmer die gleiche Farbe zugewiesen.
     *
     * @return JFreeChart mit Benutzerinformationen.
     */
    public JFreeChart getBenutzerStatistik() {

        JFreeChart chart;
        Benutzer_Status status;
	//Recht zum Prüfen ob der jeweilige Benutzer über Benutzerrechte und
        //nicht Adminrechte verfügt
        Benutzer_Recht benRecht = this.entityManager.find(Benutzer_Recht.class,
                Konstanten.ID_BEN_RECHT_BENUTZER_ANSICHT);
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Benutzer> benutzerListe = this.getAllBenutzer();
        PiePlot plot;

        //Ermitteln, wie viele Benutzer es mit welchem Status gibt.
        for (Benutzer ben : benutzerListe) {

	    //Namen des Benuzerstatus auslesen, um ihn als Key für das Dataset
            //zu nutzen
            String benutzerStatus = ben.getStatus().getName();

            //Prüfen ob der Benutzer normale Benutzerrechte hat
            if (ben.getRechte().contains(benRecht)) {
		//Benutzer hat normale Benutzerrechte und kann der Statistik
                //hinzugefügt werden

                //Prüfen ob es bereits einen Eintrag mit diesem Status gibt
                if (dataset.getKeys().contains(benutzerStatus)) {
                    /*
                     Wenn sich bereits Benutzer mit dem gleichen Status im 
                     Dataset befinden, erhoehe den Wert um 1.
                     */
                    dataset.setValue(benutzerStatus, dataset.getValue(
                            benutzerStatus).intValue() + 1);
                } else {
                    /*
                     Wenn noch keine Benutzer mit gleichem Status im Dataset sind,
                     setze den Wert auf 1;
                     */
                    dataset.setValue(benutzerStatus, 1);
                }

            } //Benutzer hat Adminrechte und wird der Statistik nicht
            //hinzugefügt

        }

        //Diagramm erstellen.
        chart = ChartFactory.createPieChart("Benutzerübersicht", dataset);

        //Anpassen des Labelformates im Diagramm.
        plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(
                new StandardPieSectionLabelGenerator("{0} \n Anzahl: {1} ({2})"));

        //Diagrammhintergrund transparent setzen
        plot.setBackgroundPaint(new Color(255, 255, 255, 0));
        plot.setBackgroundImageAlpha(0.0f);

        //Rand um das Diagramm deaktivieren
        plot.setOutlineVisible(false);

	//Farben der einzelnen Sektionen im Chart festlegen
        //Dem Status 'Aktiv' die Farbe Grün zuweisen
        status = this.entityManager.find(Benutzer_Status.class,
                Konstanten.ID_BEN_STATUS_AKTIV);
        plot.setSectionPaint(status.getName(), new Color(0, 153, 0));
        //Dem Status 'Geloescht' die Farbe Rot zuweisen
        status = this.entityManager.find(Benutzer_Status.class,
                Konstanten.ID_BEN_STATUS_GELOESCHT);
        plot.setSectionPaint(status.getName(), Color.RED);
        //Dem Status 'Unbestaetigt' die Farbe Orange zuweisen
        status = this.entityManager.find(Benutzer_Status.class,
                Konstanten.ID_BEN_STATUS_UNBESTAETIGT);
        plot.setSectionPaint(status.getName(), new Color(250, 128, 0));

        return chart;
    }

    /**
     * Ersteller: René Kanzenbach 
     * Datum:	28.07.2015 
     * Version:	1.0
     *
     * Erzeugt ein Tortendiagramm, welches anzeigt, wie viele Vertraege im
     * System registriert sind und was es fuer Vertraege sind.
     *
     * @return
     */
    public JFreeChart getVertragStatistik() {

        JFreeChart chart;
        Vertrag_Art vArt;
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Vertrag> vertragListe = this.getAllVertraege();
        PiePlot plot;

        //Dataset mit Informationen fuellen.
        for (Vertrag vertrag : vertragListe) {

            //Vertragsart auslesen.
            String vertragArt = vertrag.getVertragArt().getName();

            if (dataset.getKeys().contains(vertragArt)) {

                /*
                 Wenn das Dataset bereits einen Vertrag mit dieser Vertragsart
                 enthaelt, wird der Wert um 1 erhoeht.
                 */
                dataset.setValue(vertragArt, dataset.getValue(vertragArt).
                        intValue() + 1);
            } else {
                /*
                 Wenn Das Dataset noch keinen Vertrag mit dieser Vertragsart
                 enthaelt, wird der Wert auf 1 gesetzt.
                 */
                dataset.setValue(vertragArt, 1);
            }

        }

        //Diagramm erstellen.
        chart = ChartFactory.createPieChart("Vertragsübersicht", dataset);

        //Anpassen des Labelformates im Diagramm.
        plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(
                new StandardPieSectionLabelGenerator("{0} \n Anzahl: {1} ({2})"));

        //Diagrammhintergrund transparent setzen
        plot.setBackgroundPaint(new Color(255, 255, 255, 0));
        plot.setBackgroundImageAlpha(0.0f);

        //Rand um das Diagramm deaktivieren
        plot.setOutlineVisible(false);

	//Den Vertragsarten eine feste Farbe zuweisen
        //Der Vertragsart 'Stromvertrag' die Farbe Gelb zuweisen
        vArt = this.entityManager.find(Vertrag_Art.class,
                Konstanten.ID_VERTRAG_ART_STROM);
        plot.setSectionPaint(vArt.getName(), Color.YELLOW);
        //Der Vertragsart 'Gasvertrag' die Farbe Orange zuweisen
        vArt = this.entityManager.find(Vertrag_Art.class,
                Konstanten.ID_VERTRAG_ART_GAS);
        plot.setSectionPaint(vArt.getName(), new Color(250, 128, 0));
        //Der Vertragsart 'Handyvertrag' die Farbe Magenta zuweisen
        vArt = this.entityManager.find(Vertrag_Art.class,
                Konstanten.ID_VERTRAG_ART_HANDY);
        plot.setSectionPaint(vArt.getName(), new Color(204, 0, 204));
        //Der Vertragsart 'Festnetzvertrag' die Farbe Blau zuweisen
        vArt = this.entityManager.find(Vertrag_Art.class,
                Konstanten.ID_VERTRAG_ART_FESTNETZ);
        plot.setSectionPaint(vArt.getName(), Color.BLUE);
        //Der Vertragsart 'Zeitschriftvertrag' die Farbe Grün zuweisen
        vArt = this.entityManager.find(Vertrag_Art.class,
                Konstanten.ID_VERTRAG_ART_ZEITSCHRIFT);
        plot.setSectionPaint(vArt.getName(), new Color(0, 153, 0));
        return chart;
    }

    /**
     * Ersteller: René Kanzenbach Erstelldatum: 27.07.2015 Version: 1.0
     * Veränderungen: -
     *
     * Gibt alle Benutzer zurück, die sich in der Datenbank befinden, unabhängig
     * vom Benutzerstatus oder den Rechten des Benutzers.
     *
     * @return Liste aller Benutzer im System.
     */
    public List<Benutzer> getAllBenutzer() {

        List<Benutzer> alleBenutzer = null;
        Query query;

        //Select-Query erstellen.
        query = this.entityManager.createQuery("SELECT ben "
                + "FROM Benutzer ben");

        //Query ausführen.
        alleBenutzer = query.getResultList();

        return alleBenutzer;
    }

    /**
     * Ersteller: René Kanzenbach 
     * Erstelldatum: 28.07.2015 
     * Version: 1.0
     * Veränderungen: -
     *
     * Gibt alle Vertraege zurück, die sich in der Datenbank befinden.
     *
     * @return Liste aller Benutzer im System.
     */
    public List<Vertrag> getAllVertraege() {

        List<Vertrag> alleVertraege = null;
        Query query;

        //Select-Query erstellen.
        query = this.entityManager.createQuery("SELECT vert "
                + "FROM Vertrag vert");

        //Query ausführen.
        alleVertraege = query.getResultList();

        return alleVertraege;
    }

    /**
     * Ersteller: René Kanzenbach 
     * Erstelldatum: 04.08.2015 
     * Version: 1.0
     * Veränderungen: -
     *
     * Sucht Benutzer mit Hilfe der eingegebenen Suche und gibt diese in einer
     * Liste zurück.
     *
     * Es wird nur die E-Mail des Benutzers auf Übereinstimmungen geprüft.
     *
     * @param suche
     * @return
     */
    public List<Benutzer> sucheBenutzer(String suche) {

        Query query;

        if (suche.isEmpty()) {
            suche = "%";
        }

        query = this.entityManager.createQuery(""
                + "SELECT b "
                + "FROM Benutzer b "
                + "WHERE b.email LIKE :emailName ", Benutzer.class);
        //Suche auf 25 Ergebnisse beschränken.
        query.setMaxResults(25);
        query.setParameter("emailName", "%" + suche + "%");
        return query.getResultList();
    }

    /**
     * Ersteller: René Kanzenbach 
     * Datum:	19.08.2015 
     * Version:	1.0
     *
     * Ändert den Status des übergebenen Benutzers auf den Status, mit der
     * übergebenen Id.
     *
     * @param benutzer
     * @param statusId
     */
    public void setBenutzerStatus(Benutzer benutzer, int statusId) {

        //Status mit gesuchter Id finden.
        Benutzer_Status status = this.entityManager.find(Benutzer_Status.class,
                statusId);
		//Sicherstellen, dass sich der Benutzer im PersistenceContext des
        //EntityManagers befindet
        Benutzer updateBenutzer = this.entityManager.merge(benutzer);

        try {
            //Transaktion beginnen.
            this.entityManager.getTransaction().begin();
            //Benutzerstatus ändern.
            updateBenutzer.setStatus(status);
            //Transaktion bestätigen.
            this.entityManager.getTransaction().commit();
        } catch (RollbackException rbe) {
            System.out.println("Fehler Rollback musste ausgeführt werden.");
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();
            System.out.println("Fehler Rollback musste ausgeführt werden.");
        }
    }

    /**
     * Ersteller:	René Kanzenbach 
     * Datum:	20.08.2015 
     * Version:	1.0
     *
     * Änder das Passwort des übergebenen Benutzers, innerhalb einer
     * Transaktion.
     *
     * @param benutzer
     * @param pw
     */
    public void setBenutzerPW(Benutzer benutzer, String pw) {

        EntityTransaction tr = this.entityManager.getTransaction();
		//Sicherstellen, dass sich der Benutzer im PersistenceContext
        //des EntityManagers befindet
        Benutzer updateBenutzer = this.entityManager.merge(benutzer);

        try {
            //Transaktion beginnen.
            tr.begin();
            //Benutzerstatus ändern.
            updateBenutzer.setPasswort(pw);
            //Transaktion bestätigen.
            tr.commit();
        } catch (RollbackException rbe) {
            System.out.println("RollbackException aufgetreten in "
                    + "DatenZugriffsObjekt -> setBenutzerPW() \n"
                    + rbe.getMessage());
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();
            System.out.println("Allgemeine Exception aufgetreten in "
                    + "DatenZugriffsObjekt -> setBenutzerPW()"
                    + e.getMessage());
        }
    }

     /**
     * Ersteller:	Mladen Sikiric
     * Datum:		01.09.2015
     * 
     * Führt ein Update des übergebenen Benutzers in der Datenbank durch.
     * 
     * @param b
     * @return 
     */
    public Benutzer updateBenutzer(Benutzer b) {
        this.entityManager.getTransaction().begin();
        
        this.entityManager.merge(b);
        this.entityManager.getTransaction().commit();
        
        return b;
    }

    /**
     * Ersteller: René Kanzenbach 
     * Datum:	24.08.2015 
     * Version:	1.0
     *
     * Erstellt einen neuen Adminaccount und fügt ihn in die Datenbank ein. Der
     * neu erzeugte Admin erhält sofort den Status "aktiv".
     *
     * @param name
     * @param pw
     * @return 'true' wenn der Admin angelegt wurde; 'false' wenn der Admin
     * nicht angelegt werden konnte
     */
    public boolean addAdmin(String name, String pw) {

        EntityTransaction tr = this.entityManager.getTransaction();
        boolean istAdminAngelegt = false;

        //Neuen Benutzer erstellen
        Benutzer neuerAdmin = new Benutzer();
        //Status "aktiv" aus der Datenbank laden
        Benutzer_Status statusAktiv = this.entityManager.find(
                Benutzer_Status.class, Konstanten.ID_BEN_STATUS_AKTIV);
        //Adminrecht aus der Datenbank laden
        Benutzer_Recht adminRecht = this.entityManager.find(
                Benutzer_Recht.class, Konstanten.ID_BEN_RECHT_ADMIN_ANSICHT);

        //Adminobjekt mit Daten füllen
        neuerAdmin.setEmail(name);
        neuerAdmin.setPasswort(pw);
        neuerAdmin.addRecht(adminRecht);
        neuerAdmin.setStatus(statusAktiv);

        //Admin in die Datenbank einfügen
        try {
            //Transaktion beginnen
            tr.begin();
            //Adminobjekt dem EntityManager übergeben
            this.entityManager.persist(neuerAdmin);
            //Transaktion abschließen
            tr.commit();
            istAdminAngelegt = true;
        } catch (RollbackException e) {
            System.out.println("Fehler in DatenZugriffsObjekt -> addAdmin() \n"
                    + e.getMessage());
        } catch (Exception e) {
            System.out.println("Fehler in DatenZugriffsObjekt -> addAdmin() \n"
                    + e.getMessage());
        }
        return istAdminAngelegt;
    }

    /**
     * Ersteller:	Mladen Sikiric
     * Datum:		01.09.2015
     * 
     *  Liefert zur übergebenen id den Status.
     * 
     * @param id
     * @return 
     */
    public Benutzer_Status getStatusByID(int id) {
        return this.entityManager.find(Benutzer_Status.class, id);
    }

    /**
     * Ersteller: René Kanzenbach 
     * Datum:	29.08.2015 
     * Version:	1.0
     *
     * Gibt das Benutzerrecht mit der übergebenen Id zurück. Existiert kein
     * Benutzerrecht mit der gesuchten Id wird NULL zurückgegeben.
     *
     * @param id
     * @return
     */
    public Benutzer_Recht getBenutzerRecht(int id) {
        return this.entityManager.find(Benutzer_Recht.class, id);
    }

    /**
     * Methode zum schließen des EntityManagers.
     */
    public void close() {
        this.entityManager.close();
    }
}
