package Manager;

import javax.persistence.*;
import Entitys.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import Hilfsklassen.Konstanten;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
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

    public boolean isEmailAvailable(String email) {
        boolean isAvailable = false;
        String query = "select count(b) from Benutzer b where "
                + "b.email like '" + email + "'";
        long i = 0;
        i = (long) this.entityManager.createQuery(query).getSingleResult();

        return i == 0;
    }

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

    public Collection<Vertrag> searchContract(String suchText, Kunde k) {
        Collection<Vertrag> vertraegeErg = null, vertraegeErg2 = null;
        java.util.Date beginn = null, ende = null;
        int kundenNr = 0;
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

        suchText = suchText.replace("*", "%");

        vertraegeErg = this.entityManager.createQuery(
                "SELECT v FROM Vertrag v WHERE "
                + "v.kunde.benutzerId = " + k.getBenutzerId() + " "
                + "AND v.vertragNr LIKE '" + suchText + "' "
                + "OR v.vertragsBezeichnung LIKE '" + suchText + "' "
                + "OR v.kundenNr LIKE '" + suchText + "' "
                + "OR v.vertragsPartner LIKE '" + suchText + "'").getResultList();
        // Hiermit werden alle Verträge des Kunden gesucht
        // um die Daten mit dem Suchbegriff einzeln abzugleichen
//        vertraegeErg2
//                = this.entityManager.createQuery("SELECT v FROM Vertrag v WHERE"
//                        + " v.kunde.benutzerId = " + k.getBenutzerId() + "")
//                .getResultList();

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
    public boolean register(String vname, String name, String email, String passwort) {
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
     * @param einheitID
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
     * @param gebietID
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
     * Ersteller: Sascha Jungenkrüger
     * Datum: 29.07.2015
     * Methode: getVertragsArt
     * Version: 1.0
     *
     * Liest eine Vertragsart über den übergebenen Index aus der Datenbank
     * und liefert das entsprechende Objekt zurück.
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
     * Datum: 28.07.2015 
     * Version: 1.0 
     * Änderungen: -
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

        return chart;
    }

    /**
     * Ersteller: René Kanzenbach 
     * Datum: 28.07.2015 
     * Version: 1.0 
     * Änderungen: -
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

        return chart;
    }

    /**
     * Ersteller: René Kanzenbach 
     * Erstelldatum: 27.07.2015 
     * Version: 1.0
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
        
        if(suche.isEmpty()){
            suche = "%";
        }
        
        query = this.entityManager.createQuery(""
                + "SELECT b "
                + "FROM Benutzer b "
                + "WHERE b.email LIKE :emailName ", Benutzer.class);
        //Suche auf 25 Ergebnisse beschränken.
        query.setMaxResults(25);
        query.setParameter("emailName", suche);
        return query.getResultList();
    }

    /**
     * Methode zum schließen des EntityManagers.
     */
    public void close() {
        this.entityManager.close();
    }
}
