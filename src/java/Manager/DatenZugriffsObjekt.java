package Manager;

import javax.persistence.*;
import Entitys.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import Hilfsklassen.Konstanten;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        neuerKunde.setStrasse("Highway To Hell");
        neuerKunde.setHausnummer("666");
        neuerKunde.setOrt("Paradise City");
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
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        
        try {
            beginn = df.parse(suchText);
        } catch (ParseException ex) {
            try {
                beginn = df.parse("01.01.1970");
            } catch (ParseException ex1) {
                Logger.getLogger(DatenZugriffsObjekt.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        try {
            ende = df.parse(suchText);
        } catch (ParseException ex) {
            ende = new java.util.Date();
        }        

        vertraegeErg = this.entityManager.createQuery(
                "SELECT v FROM Vertrag v WHERE "
                + "v.kunde.benutzerId = " + k.getBenutzerId() + " AND "
                + "v.vertragNr = '" + suchText + "'").getResultList();
        // Hiermit werden alle Verträge des Kunden gesucht
        // um die Daten mit dem Suchbegriff einzeln abzugleichen
        vertraegeErg2 = 
                this.entityManager.createQuery("SELECT v FROM Vertrag v WHERE "
                        + "v.kunde.benutzerId = "+k.getBenutzerId()+"")
                        .getResultList();
        
        if (!vertraegeErg2.isEmpty()) {
            for (Vertrag v : vertraegeErg2) {
                String beginnV = v.getVertragBeginn() != null ? v.getVertragBeginn().toString() : null;
                String endeV = v.getVertragEnde() != null ? v.getVertragEnde().toString() : null;
                
                if (beginnV != null && beginnV.contains(suchText)) {
                    vertraegeErg.add(v);
                } else if (endeV != null && endeV.contains(suchText)) {
                    vertraegeErg.add(v);
                }                
            }
        }

        return vertraegeErg;
    }

    public Collection<Vertrag> searchContractCategory(String kategorie, Kunde k) {
        Collection<Vertrag> vertraegeErg = null;

        vertraegeErg = this.entityManager.createQuery(
                "SELECT v FROM Vertrag v WHERE "
                + "v.kunde.benutzerId = " + k.getBenutzerId() + " AND "
                + "v.art.name = '" + kategorie + "'").getResultList();

        return vertraegeErg;
    }

    /**
     * Ersteller:	
     * Erstelldatum:    
     * Methode:         register
     * Version:         1.0
     * Änderungen:      1.1 René Kanzenbach 11.06.2015
     *                  -Dem Benutzer wird jetzt bei der Registrierung das Recht
     *                  "Benutzer_Ansicht" verliehen.
     * 
     * 
     * @param vname
     * @param name
     * @param email
     * @param password
     * @return 
     */
    public boolean register(String vname, String name, String email, String password) {
        boolean isRegister = false;
        Kunde neuerKunde = new Kunde();

        neuerKunde.setVorname(vname);
        neuerKunde.setNachname(name);
        neuerKunde.setEmail(email);
        neuerKunde.setPasswort(password);
        neuerKunde.addRecht(this.entityManager.find(Benutzer_Recht.class,
                Konstanten.ID_BEN_RECHT_BENUTZER_ANSICHT));

        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(neuerKunde);
            this.entityManager.getTransaction().commit();
            isRegister = true;
        } catch (RollbackException re) {

        } catch (PersistenceException pe) {
            this.entityManager.getTransaction().rollback();
        } catch (Throwable th) {
            this.entityManager.getTransaction().rollback();
        }

        return isRegister;
    }

    /**
     * Ersteller:	René Kanzenbach Erstelldatum: 02.06.2015 Methode: getBenutzer
     * Version: 1.0 Änderungen: -
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

        //Benutzer mit gleicher EMail-Adresse suchen
        benutzerListe = this.entityManager.createQuery(""
                + "SELECT ben "
                + "FROM Benutzer ben "
                + "WHERE ben.email LIKE '" + eMail + "' ").getResultList();

        //Den ersten gefundenen Benutzer zurückgeben
        return (Benutzer) benutzerListe.get(0);
    }

    /**
     * Methode zum schließen des EntityManagers.
     */
    public void close() {
        this.entityManager.close();
    }
}
