package Hilfsklassen;

/**
 * Ersteller:       René Kanzenbach
 * Erstelldatum:    02.06.2015
 * Version:         1.0
 * Änderungen:      -
 * 
 * Diese Klasse stellt eine Sammlung verschiedener Konstanten dar. Unter
 * anderem die Namen der Attribute der HttpSession Objekte.
 */
public class Konstanten {
    
    /**
     * Attributname für eine Benutzer_Entity in einem HttpSession Objekt.
     */
    public static final String SESSION_ATTR_BENUTZER = "BENUTZER";
    
    /**
     * Attributname für einen Kunde_Entity in einem HttpSession Objekt.
     */
    public static final String SESSION_ATTR_KUNDE = "KUNDE";
    
    /**
     * Attributname für einen Fehlertext in einem HttpRequest Objekt. 
     */
    public static final String REQUEST_ATTR_FEHLER = "fehler";
}
