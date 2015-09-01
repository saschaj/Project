package Manager;

import javax.persistence.*;
import Entitys.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import Hilfsklassen.Konstanten;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
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
    }

    /**
     * Legt Testweise einen neuen Kunden und einen Vertrag zum Kunden an.
     */
    public void beispiel() {
        Kunde neuerKunde = new Kunde();
        Stromvertrag stromVertrag = new Stromvertrag();

        neuerKunde.setVorname("Saul Slash");
        neuerKunde.setNachname("Hudson");
        neuerKunde.setGeburtsdatum(new java.util.Date(1965, 7, 23));
        neuerKunde.setEmail("gunsNroses@web.de");

        stromVertrag.setLaufzeit(30);
        stromVertrag.setStromzaehlerNr("1337#YOLO");
        stromVertrag.setKunde(neuerKunde);

        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(neuerKunde);
            this.entityManager.persist(stromVertrag);
            this.entityManager.getTransaction().commit();
        } catch (RollbackException re) {

        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }

    }

    public Kunde getKunde(int benutzerId) {
        return entityManager.find(Kunde.class, benutzerId);
    }
    
    /**
     * Die Methode soll den in der Datenbank,bestehenden Kunden mit den
     * übergebenen Kundendaten aktualisieren.
     *
     * @param vorname
     * @param nachname
     * @param straße
     * @param hausnummer
     * @param plz
     * @param wohnort
     * @param nummer
     */
    public boolean updateKundeDaten(String vorname, String nachname,
            Adresse adresse, Date gebdt, String nummer, int benutzerId) {
        boolean istAktualisiert = false;

        Kunde k = entityManager.find(Kunde.class, benutzerId);
        try {
//            entityManager.flush();
//            this.entityManager.persist(k);
            entityManager.getTransaction().begin();

//       Kunde k= new Kunde();
            k.setGeburtsdatum(gebdt);
            k.setVorname(vorname);
            k.setNachname(nachname);
            k.setAdresse(adresse);
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
     * Die Methode soll den in der Datenbank,bestehenden benutzer mit den
     * übergebenen Benutzerdaten aktualisieren.
     *
     * @param email
     * @param passwort
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
     * Methode zum Update eines Vertrags, wenn der Kunde ihn ändern möchte.
     *
     * @param vertrag Zu verändernde Vertrag
     * @return true, bei Erfolg.. false, bei Nicht-Erfolg
     */
    public boolean updateVertrag(Vertrag vertrag) {
        // Initialisierung der benötigten Variablen
        boolean istAktualisiert = false;
        Vertrag alterVertrag = 
                this.entityManager.find(Vertrag.class, vertrag.getVertragId());

        // Starten der Transaktion
        this.entityManager.getTransaction().begin();

        try {
            // Setze die Vertragsdaten
            alterVertrag.setVertragsBezeichnung(
                    vertrag.getVertragsBezeichnung());
//            alterVertrag.setVertragNr(vertrag.getVertragNr());
//            alterVertrag.setVertragBeginn(vertrag.getVertragBeginn());
//            alterVertrag.setVertragEnde(vertrag.getVertragEnde());
//            alterVertrag.setLaufzeit(vertrag.getLaufzeit());
//            alterVertrag.setLaufzeitEinheit(vertrag.getLaufzeitEinheit());
//            alterVertrag.setKuendigungsfrist(vertrag.getKuendigungsfrist());
//            alterVertrag.setKuendigungsfristEinheit(
//                    vertrag.getKuendigungsfristEinheit());
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
            istAktualisiert = true;
        } catch (PersistenceException pe) {
            // Rollback, falls etwas schief,gelaufen ist
            this.entityManager.getTransaction().rollback();
            istAktualisiert = false;
        }

        return istAktualisiert;
    }
    
    public boolean loescheVertrag(int vertragID) { 
        boolean istGeloescht = false;        
        Vertrag vertrag = this.entityManager.find(Vertrag.class, vertragID);
        
        this.entityManager.getTransaction().begin();
        try {
            vertrag.setIstGeloescht(true); 
            this.entityManager.getTransaction().commit();
            istGeloescht = true;
        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
            istGeloescht = false;
        }
        
        return istGeloescht;
    }

    public boolean addBenutzer(Benutzer b) {
        boolean addComplete = false;

        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(b);
            this.entityManager.getTransaction().commit();
            addComplete = true;

        } catch (RollbackException re) {

        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }

        return addComplete;
    }
    

    /**
     * Methode zur Überprüfung, ob die E-Mail Adresse schon existiert 
     * 
     * @param email Zu überprüfende E-Mail
     * @return true, wenn sie existiert.. false, wenn sie nicht existiert
     */
    public boolean isEmailAvailable(String email) {
        String query = "select count(b) from Benutzer b where "
                + "b.email like '" + email + "'";
        long i = 0;
        i = (long) this.entityManager.createQuery(query).getSingleResult();

        return i == 0;
    }

    /**
     * Methode zum Hinzufügen eines Vertrags
     * 
     * @param vertrag Der hinzuzufügende Vertrag
     * @return true, wenn erfolgreich.. false, wenn nicht erfolgreich
     */
    public boolean addContract(Vertrag vertrag) {
        boolean addComplete = false;

        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(vertrag);
            this.entityManager.getTransaction().commit();
            addComplete = true;

        } catch (RollbackException re) {

        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }

        return addComplete;
    }

    /**
     * Methode dient zur textuellen Suche eines Vertrags
     * 
     * @param suchText Der zu suchende Text
     * @param k Das Kundenobjekt
     * @return Eine Collection der Verträge unter dem Suchbegriff
     */
    public Collection<Vertrag> searchContract(String suchText, Kunde k) {
        Collection<Vertrag> vertraegeErg = null, vertraegeErg2 = null;
        java.util.Date beginn = null, ende = null;
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        try {
            beginn = df.parse(suchText);
        } catch (ParseException ex) {
            beginn = null;
        }
        try {
            ende = df.parse(suchText);
        } catch (ParseException ex) {
            ende = null;
        }

        suchText = "%" + suchText + "%";
        // Hiermit werden alle Verträge des Kunden gesucht
        vertraegeErg = this.entityManager.createQuery(
                "SELECT v FROM Vertrag v WHERE "
                + "v.kunde.benutzerId = " + k.getBenutzerId() + " "
                + "AND v.vertragNr LIKE '" + suchText + "' "
                + "OR v.vertragsBezeichnung LIKE '" + suchText + "' "
                + "OR v.kundenNr LIKE '" + suchText + "' "
                + "OR v.vertragsPartner LIKE '" + suchText + "'").getResultList();

//        if (!vertraegeErg2.isEmpty()) {
//            for (Vertrag v : vertraegeErg2) {
//                String beginnV = v.getVertragBeginn() != null ? v.getVertragBeginn().toString() : null;
//                String endeV = v.getVertragEnde() != null ? v.getVertragEnde().toString() : null;
//
//                if (beginnV != null && beginnV.contains(suchText)) {
//                    vertraegeErg.add(v);
//                } else if (endeV != null && endeV.contains(suchText)) {
//                    vertraegeErg.add(v);
//                }
//            }
//        }
        return vertraegeErg;
    }

    /**
     * Methode dient zur kategorisierten Suche der hinzugefügten Verträge
     * 
     * @param kategorie spezielle Kategorie
     * @param k Kundenobjekt
     * @return Collection mit den speziellen Verträgen
     */
    public Collection<Vertrag> searchContractCategory(String kategorie, Kunde k) {
        Collection<Vertrag> vertraegeErg = null;

        vertraegeErg = this.entityManager.createQuery(
                "SELECT v FROM Vertrag v WHERE "
                + "v.kunde.benutzerId = " + k.getBenutzerId() + " AND "
                + "v.vertragArt.name = '" + kategorie + "'").getResultList();

        return vertraegeErg;
    }

    /**
     * Ersteller: Sascha Jungenkrüger Erstelldatum: 08.06.2015 Methode: register
     * Version: -1.0 -1.1 René Kanzenbach 11.06.2015 -Dem Benutzer wird jetzt
     * bei der Registrierung das Recht "Benutzer_Ansicht" verliehen. -1.2 René
     * Kanzenbach 22.07.2015 -Dem Benutzer wird jetzt bei der Registrierung der
     * Status "Aktiv" verliehen.
     *
     * @param vname Vorname des Benutzer
     * @param name Nachname des Benutzer
     * @param email E-Mail Adresse des Benutzer
     * @param passwort Verschlüsselte Passwort des Benutzer
     * @return true, wenn die Registrierung erfolgreich war false, wenn die
     * Registrierung fehlgeschlagen ist
     */
    public boolean register(String vname, String name, 
            String email, String passwort) {
        boolean istRegistriert = false;
        Kunde neuerKunde = new Kunde();
        Benutzer_Recht recht = this.entityManager.find(Benutzer_Recht.class,
                Konstanten.ID_BEN_RECHT_BENUTZER_ANSICHT);

        neuerKunde.setVorname(vname);
        neuerKunde.setNachname(name);
        neuerKunde.setEmail(email);
        neuerKunde.setPasswort(passwort);
        neuerKunde.addRecht(recht);
        neuerKunde.setStatus(this.entityManager.find(Benutzer_Status.class,
                Konstanten.ID_BEN_STATUS_AKTIV));

        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(neuerKunde);
            this.entityManager.getTransaction().commit();
            istRegistriert = true;
        } catch (RollbackException re) {

        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }

        return istRegistriert;
    }

    /**
     * Ersteller: René Kanzenbach Datum: 02.06.2015 Methode: getBenutzer
     * Version: 1.0 1.1 René Kanzenbach 20.07.2015 -Fehler behoben. Wirft jetzt
     * keine NullpointerException mehr
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
     * Ersteller: Sascha Jungenkrüger Datum: 29.07.2015 Methode: getEinheiten
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
     * Ersteller: Sascha Jungenkrüger Datum: 29.07.2015 Methode: getEinheit
     * Version: 1.0
     *
     * Liest Zeiteinheiten aus der Datenbank und gibt es zurück.
     *
     * @param einheitID
     * @return Ein Objekt der übergebenen Zeit_Einheiten
     */
    public Zeit_Einheit getZeitEinheit(int einheitID) {
        return this.entityManager.find(Zeit_Einheit.class, einheitID);
    }

    /**
     * Ersteller: Sascha Jungenkrüger Datum: 29.07.2015 Methode:
     * getInteressengebiete Version: 1.0
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
     * Ersteller: Sascha Jungenkrüger Datum: 29.07.2015 Methode:
     * getInteressengebiet Version: 1.0
     *
     * Liest ein Interessengebiet aus der Datenbank und gibt das Objekt zurück.
     *
     * @param gebietID
     * @return Ein Objekt mit speziellem Interessengebieten
     */
    public Interessengebiet getInteressengebiet(int gebietID) {
        return this.entityManager.find(Interessengebiet.class, gebietID);
    }

    /**
     * Ersteller: Sascha Jungenkrüger Datum: 29.07.2015 Methode: getNetztyp
     * Version: 1.0
     *
     * @param netztypID Primärschlüssel für ein Netztyp-Objekt
     * @return Liefert ein Objekt der übergebenen ID zurück
     */
    public Netztyp getNetztyp(int netztypID) {
        return this.entityManager.find(Netztyp.class, netztypID);
    }

    /**
     * Ersteller: Sascha Jungenkrüger Datum: 29.07.2015 Methode: getNetztypen
     * Version: 1.0
     *
     * Liest alle Netztypen aus der Datenbank und gibt Sie zurück.
     *
     * @param istHandyTyp true oder false
     * @param istFestnetzTyp true oder false
     * @return Eine Liste mit allen Netztypen der Datenbank
     */
    public List<Netztyp> getNetztypen(boolean istHandyTyp, boolean istFestnetzTyp) {
        Query query = this.entityManager.createQuery(
                "SELECT typ FROM Netztyp typ "
                + "WHERE typ.istHandyTyp = :HandyTyp "
                + "OR typ.istFestnetzTyp = :FestnetzTyp"
        );
        query.setParameter("HandyTyp", istHandyTyp);
        query.setParameter("FestnetzTyp", istFestnetzTyp);

        return query.getResultList();
    }

    /**
     * Ersteller: Sascha Jungenkrüger Datum: 29.07.2015 Methode: getVertrag
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
     * Ersteller: Sascha Jungenkrüger Datum: 29.07.2015 Methode: getVertragsArt
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
     * Ersteller: Sascha Jungenkrüger Datum: 29.07.2015 Methode:
     * getVertragsStatus Version: 1.0
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
	 * Datum: 28.07.2015 
	 * Version: 1.0 Änderungen: -
     *
     * Erzeugt ein Tortendiagramm, welches anzeigt, wie viele Benutzer im System
     * registriert sind und welchen Status diese besitzen.
     *
     * @return JFreeChart mit Benutzerinformationen.
     */
    public JFreeChart getBenutzerStatistik() {

        JFreeChart chart;
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Benutzer> benutzerListe = this.getAllBenutzer();
        PiePlot plot;

        //Ermitteln, wie viele Benutzer es mit welchem Status gibt.
        for (Benutzer ben : benutzerListe) {

            String benutzerStatus = ben.getStatus().getName();

            if (dataset.getKeys().contains(benutzerStatus)) {
                /*
                 Wenn sich bereits Benutzer mit dem gleichen Status im Dataset
                 befinden, erhoehe den Wert um 1.
                 */
                dataset.setValue(benutzerStatus, dataset.getValue(benutzerStatus)
                        .intValue() + 1);
            } else {
                /*
                 Wenn noch keine Benutzer mit gleichem Status im Dataset sind,
                 setze den Wert auf 1;
                 */
                dataset.setValue(benutzerStatus, 1);
            }
        }

        //Diagramm erstellen.
        chart = ChartFactory.createPieChart("Benutzerübersicht", dataset);
        //Anpassen des Labelformates im Diagramm.
        plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(
                new StandardPieSectionLabelGenerator("{0} Anzahl: {1} ({2})"));
		//Diagrammhintergrund transparent setzen
		plot.setBackgroundPaint( new Color(255,255,255,0) );
		plot.setBackgroundImageAlpha(0.0f);
		//Rand um das Diagramm deaktivieren
		plot.setOutlineVisible(false);
		
        return chart;
    }

    /**
     * Ersteller: René Kanzenbach 
	 * Datum: 28.07.2015 
	 * Version: 1.0 Änderungen: -
     *
     * Erzeugt ein Tortendiagramm, welches anzeigt, wie viele Vertraege im
     * System registriert sind und was es fuer Vertraege sind.
     *
     * @return
     */
    public JFreeChart getVertragStatistik() {

        JFreeChart chart;
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
                new StandardPieSectionLabelGenerator("{0} Anzahl: {1} ({2})"));
		//Diagrammhintergrund transparent setzen
		plot.setBackgroundPaint( new Color(255,255,255,0) );
		plot.setBackgroundImageAlpha(0.0f);
		//Rand um das Diagramm deaktivieren
		plot.setOutlineVisible(false);

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
     * Ersteller: René Kanzenbach Erstelldatum: 28.07.2015 Version: 1.0
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
     * Ersteller: René Kanzenbach Erstelldatum: 04.08.2015 Version: 1.0
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
	 * Ersteller:	René Kanzenbach
	 * Datum:		19.08.2015
	 * Version:		1.0
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
	 * Datum:		20.08.2015
	 * Version:		1.0
	 * 
	 * Änder das Passwort des übergebenen Benutzers, innerhalb einer Transaktion.
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
	 * Ersteller:	René Kanzenbach
	 * Datum:		24.08.2015
	 * Version:		1.0
	 * 
	 * Erstellt einen neuen Adminaccount und fügt ihn in die Datenbank ein.
	 * Der neu erzeugte Admin erhält sofort den Status "aktiv".
	 * 
	 * @param name
	 * @param pw 
	 * @return 'true' wenn der Admin angelegt wurde; 'false' wenn der Admin 
	 *			nicht angelegt werden konnte
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
        
        public Benutzer updateBenutzer(Benutzer b){
            this.entityManager.getTransaction().begin();
            b = this.entityManager.merge(b);
            this.entityManager.getTransaction().commit();
            return b;
        }

    /**
     * Methode zum schließen des EntityManagers.
     */
    public void close() {
        this.entityManager.close();
    }
}
