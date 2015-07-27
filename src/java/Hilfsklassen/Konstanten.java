package Hilfsklassen;

/**
 * Ersteller:       René Kanzenbach
 * Erstelldatum:    02.06.2015
 * Version:         1.0
 * Änderungen:      1.1 (René Kanzenbach) 07.06.2015
 *                  -SESSION_ATTR_KUNDE entfernt. (Unnötig, da Benutzer zu Kunde 
 *                  gecastet werden kann.)
 * 
 * Diese Klasse stellt eine Sammlung verschiedener Konstanten dar. Unter
 * anderem die Namen der Attribute der HttpSession Objekte.
 */
public class Konstanten {
    
    
    /*
    ----------------------------------------------------------------------------
    SESSION ATTRIBUTE
    ----------------------------------------------------------------------------
    */
    /**
     * Attributname für eine Benutzer_Entity in einem HttpSession Objekt.
     */
    public static final String SESSION_ATTR_BENUTZER = "BENUTZER";
    
    
    /*
    ----------------------------------------------------------------------------
    URL PARAMETER
    ----------------------------------------------------------------------------
    */
    /**
     * Parametername für einen Fehlertext in einem HttpRequest Objekt. 
     */
    public static final String REQUEST_ATTR_FEHLER = "fehler";
    
    /**
     * Attributname für einen Fehlertext in einem HttpRequest Objekt. 
     */
    public static final String REQUEST_ATTR_ERFOLG = "erfolg";
    
    /**
     * Attributname für die aufzurufende Vertragsseite.
     */
    public static final String REQUEST_ATTR_CONTRACT_PAGE = "fehler";
    public static final String URL_PARAM_FEHLER = "fehler";
     
    /**
     * Parametername für eine URL.
     */
    public static final String URL_PARAM_AKTION = "AKTION";
    
    /**
     * Inhalt für URL_PARAM_AKTION.
     */
    public static final String URL_AKTION_LOGOUT = "LOGOUT";
    
    /**
     * Inhalt für URL_PARAM_AKTION.
     */
    public static final String URL_AKTION_LOGIN = "LOGIN";
    
    
    /*
    ----------------------------------------------------------------------------
    PRIMÄRSCHLÜSSEL DER BENUTZER RECHTE
    ----------------------------------------------------------------------------
    */
    /**
     * Primärschlüssel des BenutzerRechtes "Benutzer_verwalten" in der Tabelle
     * Benutzer_Recht.
     */
    public static final int ID_BEN_RECHT_BENUTZER_VERWALTEN = 1;
    
    /**
     * Primärschlüssel des BenutzerRechtes "Vertrag_verwalten" in der Tabelle
     * Benutzer_Recht.
     */
    public static final int ID_BEN_RECHT_VERTRAG_VERWALTEN = 2;
    
    /**
     * Primärschlüssel des BenutzerRechtes "Statistik_anzeigen" in der Tabelle
     * Benutzer_Recht.
     */
    public static final int ID_BEN_RECHT_STATISTIK_ANZEIGEN = 3;
    
    /**
     * Primärschlüssel des BenutzerRechtes "Fremde_Benutzer_verwalten" in der Tabelle
     * Benutzer_Recht.
     */
    public static final int ID_BEN_RECHT_FREMDE_BENUTZER_VERWALTEN = 4;
    
    /**
     * Primärschlüssel des BenutzerRechtes "Benutzer_wiederherstellen" in der Tabelle
     * Benutzer_Recht.
     */
    public static final int ID_BEN_RECHT_BENUTZER_WIEDERHERSTELLEN = 5;
    
    /**
     * Primärschlüssel des BenutzerRechtes "Benutzer_Ansicht" in der Tabelle
     * Benutzer_Recht.
     */
    public static final int ID_BEN_RECHT_BENUTZER_ANSICHT = 6;
    
    /**
     * Primärschlüssel des BenutzerRechtes "Admin_Ansicht" in der Tabelle
     * Benutzer_Recht.
     */
    public static final int ID_BEN_RECHT_ADMIN_ANSICHT = 7;
    
    
    /*
    ----------------------------------------------------------------------------
    PRIMÄRSCHLÜSSEL DER BENUTZER STATUS
    ----------------------------------------------------------------------------
    */
    
    /**
     * Primärschlüssel des BenutzerStatus "Aktiv" in der Tabelle 
     * "BENUTZER_STATUS"
     */
    public static final int ID_BEN_STATUS_AKTIV = 1;
    
    /**
     * Primärschlüssel des BenutzerStatus "Geloescht" in der Tabelle 
     * "BENUTZER_STATUS"
     */
    public static final int ID_BEN_STATUS_GELOESCHT= 2;
    
    /**
     * Primärschlüssel des BenutzerStatus "Unbestaetigt" in der Tabelle 
     * "BENUTZER_STATUS"
     */
    public static final int ID_BEN_STATUS_UNBESTAETIGT= 3;
}
