package Manager;

import javax.persistence.*;
import Entitys.*;
import java.util.Date;
import java.util.List;

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
        neuerKunde.setGeburtsdatum(new Date(1965, 7, 23));
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
        String query = "select count(b) from Benutzer b where b.email like '"+email+"'";
        long i = 0;
        i = (long) this.entityManager.createQuery(query).getSingleResult();
        
        return i == 0;
    }

    public boolean register(String vname, String name, String email, String password) {
        boolean isRegister = false;
        Kunde neuerKunde = new Kunde();

        neuerKunde.setVorname(vname);
        neuerKunde.setNachname(name);
        neuerKunde.setEmail(email);
        neuerKunde.setPasswort(password);

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
     * Ersteller:	René Kanzenbach
     * Erstelldatum:    02.06.2015
     * Methode:         getBenutzer
     * Version:         1.0
     * Änderungen:      -
     * 
     * Sucht einen Benutzer anhand der übergebenen E-Mail aus der Datenbank und
     * gibt diesen zurück. Wird kein Benutzer mit der gesuchten E-Mail gefunden
     * gibt die Methode eine NULL-Referenz zurück.
     * 
     * Diese Methode setzt vorraus, dass eine E-Mail nur einmal in der 
     * Datenbank abgespeichert ist.
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
        benutzerListe = this.entityManager.createQuery( ""
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
