package Hilfsklassen;

/**
 * Ersteller:       René Kanzenbach
 * Erstelldatum:    02.06.2015
 * Version:         1.0
 * Änderungen:      1.1 (René Kanzenbach) 07.06.2015
 *                  -SESSION_ATTR_KUNDE entfernt. (Unnötig, da Benutzer zu Kunde 
 *                  gecastet werden kann.)
 *                  1.2 (Sascha Jungenkrüger) 29.07.2015
 *                  -Erweiterung durch weitere Primärschlüssel
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
     * Parametername für einen Fehlertext (Vertrag ändern) 
     * in einem HttpRequest Objekt. 
     */
    public static final String REQUEST_ATTR_FEHLER_CHANGE = "fehler";
    
    /**
     * Attributname für einen Fehlertext in einem HttpRequest Objekt. 
     */
    public static final String REQUEST_ATTR_ERFOLG = "erfolg";
    
    /**
     * Attributname für gefundene Verträge in einem HttpRequest Objekt. 
     */
    public static final String REQUEST_ATTR_VERTRAEGE = "vertraege";
    
    /**
     * Attributname für eine Kategorie in einem HttpRequest Objekt. 
     */
    public static final String REQUEST_ATTR_KATEGORIE = "kategorie";
    
    /**
     * Attributname für einen Suchtext in einem HttpRequest Objekt. 
     */
    public static final String REQUEST_ATTR_SUCHTEXT = "suchText";
    
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
    
    /*
    ----------------------------------------------------------------------------
    PRIMÄRSCHLÜSSEL DER ZEIT EINHEIT
    ----------------------------------------------------------------------------
    */
    
    /**
     * Primärschlüssel der ZeitEinheit "Tag(e)" in der Tabelle 
     * "ZEIT_EINHEIT"
     */
    public static final int ID_ZEIT_EINHEIT_TAG = 1;
    
    /**
     * Primärschlüssel der ZeitEinheit "Woche(n)" in der Tabelle 
     * "ZEIT_EINHEIT"
     */
    public static final int ID_ZEIT_EINHEIT_WOCHE = 2;
    
    /**
     * Primärschlüssel der ZeitEinheit "Monat(e)" in der Tabelle 
     * "ZEIT_EINHEIT"
     */
    public static final int ID_ZEIT_EINHEIT_MONAT = 3;
    
    /**
     * Primärschlüssel der ZeitEinheit "Jahr(e)" in der Tabelle 
     * "ZEIT_EINHEIT"
     */
    public static final int ID_ZEIT_EINHEIT_JAHR = 4;
    
    /*
    ----------------------------------------------------------------------------
    PRIMÄRSCHLÜSSEL DER VERTRAG ART
    ----------------------------------------------------------------------------
    */
    
    /**
     * Primärschlüssel der Vertragsart "Festnetzvertrag" in der Tabelle 
     * "VERTRAG_ART"
     */
    public static final int ID_VERTRAG_ART_FESTNETZ = 1;
    
    /**
     * Primärschlüssel der Vertragsart "Gasvertrag" in der Tabelle 
     * "VERTRAG_ART"
     */
    public static final int ID_VERTRAG_ART_GAS = 2;
    
    /**
     * Primärschlüssel der Vertragsart "Handyvertrag" in der Tabelle 
     * "VERTRAG_ART"
     */
    public static final int ID_VERTRAG_ART_HANDY = 3;
    
    /**
     * Primärschlüssel der Vertragsart "Stromvertrag" in der Tabelle 
     * "VERTRAG_ART"
     */
    public static final int ID_VERTRAG_ART_STROM = 4;
    
    /**
     * Primärschlüssel der Vertragsart "Zeitschriftvertrag" in der Tabelle 
     * "VERTRAG_ART"
     */
    public static final int ID_VERTRAG_ART_ZEITSCHRIFT = 5;
    
    /*
    ----------------------------------------------------------------------------
    PRIMÄRSCHLÜSSEL DER INTERESSENGEBIETE
    ----------------------------------------------------------------------------
    */
    
    /**
     * Primärschlüssel des Interessengebiet "Audio" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_AUDIO = 1;
    
    /**
     * Primärschlüssel des Interessengebiet "Auto" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_AUTO = 2;
    
    /**
     * Primärschlüssel des Interessengebiet "Computer" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_COMPUTER = 3;
    
    /**
     * Primärschlüssel des Interessengebiet "Fach" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_FACH = 4;
    
    /**
     * Primärschlüssel des Interessengebiet "Fitness" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_FITNESS = 5;
    
    /**
     * Primärschlüssel des Interessengebiet "Garten" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_GARTEN = 6;
    
    /**
     * Primärschlüssel des Interessengebiet "Kinder" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_KINDER = 7;
    
    /**
     * Primärschlüssel des Interessengebiet "Kochen" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_KOCHEN = 8;
    
    /**
     * Primärschlüssel des Interessengebiet "Reise" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_REISE = 9;
    
    /**
     * Primärschlüssel des Interessengebiet "Sonstiges" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_SONSTIGES = 10;
    
    /**
     * Primärschlüssel des Interessengebiet "Tageszeitung" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_TAGESZEITUNG = 11;
    
    /**
     * Primärschlüssel des Interessengebiet "Wissen" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_WISSEN = 12;
    
    /**
     * Primärschlüssel des Interessengebiet "Wohnen" in der Tabelle 
     * "INTERESSENGEBIETE"
     */
    public static final int ID_GEBIETE_WOHNEN = 13;
    
    /*
    ----------------------------------------------------------------------------
    PRIMÄRSCHLÜSSEL DER NETZTYPEN
    ----------------------------------------------------------------------------
    */
    
    /**
     * Primärschlüssel des Netztyp "GPRS" in der Tabelle 
     * "NETZTYP"
     */
    public static final int ID_NETZTYP_GPRS = 1;
    
    /**
     * Primärschlüssel des Netztyp "EDGE" in der Tabelle 
     * "NETZTYP"
     */
    public static final int ID_NETZTYP_EDGE = 2;
    
    /**
     * Primärschlüssel des Netztyp "UMTS" in der Tabelle 
     * "NETZTYP"
     */
    public static final int ID_NETZTYP_UMTS = 3;
    
    /**
     * Primärschlüssel des Netztyp "HSDPA" in der Tabelle 
     * "NETZTYP"
     */
    public static final int ID_NETZTYP_HSDPA = 4;
    
    /**
     * Primärschlüssel des Netztyp "LTE" in der Tabelle 
     * "NETZTYP"
     */
    public static final int ID_NETZTYP_LTE = 5;
    
    /**
     * Primärschlüssel des Netztyp "DSL" in der Tabelle 
     * "NETZTYP"
     */
    public static final int ID_NETZTYP_DSL = 6;
    
    /**
     * Primärschlüssel des Netztyp "VDSL" in der Tabelle 
     * "NETZTYP"
     */
    public static final int ID_NETZTYP_VDSL = 7;
    
    /*
    ----------------------------------------------------------------------------
    PRIMÄRSCHLÜSSEL DER VERTRAGSSTATI
    ----------------------------------------------------------------------------
    */
    
    /**
     * Primärschlüssel des VertragsStatus "aktiv" in der Tabelle 
     * "VERTRAGS_STATUS"
     */
    public static final int ID_VERTRAGSSTATUS_AKTIV = 1;
    
    /**
     * Primärschlüssel des VertragsStatus "gekündigt" in der Tabelle 
     * "VERTRAGS_STATUS"
     */
    public static final int ID_VERTRAGSSTATUS_GEKUENDIGT = 2;
}
