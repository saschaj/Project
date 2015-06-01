package Manager;

import javax.persistence.*;
import Entitys.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
     * Methode zum schließen des EntityManagers.
     */
    public void close() {
        this.entityManager.close();
    }
}
