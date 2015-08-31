<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   30.05.2015
Dokument:	admin_dynamic.jsp
Version:	1.0
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Erstellung der Seite mit Musterausgabe
                1.1 René Kanzenbach (28.07.2015)
                - JSP zeigt jetzt die Statistiken an.
                2.0 René Kanzenbach 20.08.2015
                - Listet jetzt alle Benutzer auf
                - Benutzer können aktiviert und gelöscht werden
                - Das Passwort von Benutzern kann zurückgesetzt werden
                - Es kann jetzt ein neuer Adminaccount angelegt werden
                - Das Passwort des Admins kann jetzt geändert werden

--%>

<%@page import="Entitys.Benutzer_Recht"%>
<%@page import="Hilfsklassen.Konstanten"%>
<%@page import="java.util.List"%>
<%@page import="Entitys.Benutzer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String statistikURL;
    List<Benutzer> benutzerListe;
    Benutzer aktuellerBenutzer;
    String pwAusgabe;
    String pwFehler;
    String adminAusgabe;
    String adminFehler;

    //URL fuer die BenutzerStatistik auslesen
    statistikURL = (String) request.getAttribute("StatistikURL");
    //Liste aller gefundenen Benutzer aus der Session lesen
    benutzerListe = (List<Benutzer>) session.getAttribute("BenutzerListe");
    //Aktuell eingeloggten Benutzer aus Session lesen
    aktuellerBenutzer = (Benutzer) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
    //Textausgaben für das Ändern des Adminpassworts auslesen
    pwAusgabe = (String) request.getAttribute("PW_Ausgabe");
    //Fehlerausgabe für das Ändern des Adminpassworts auslesen
    pwFehler = (String) request.getAttribute("PW_Fehler");
    //Textausagbe für das Anlegen eines neuen Admins
    adminAusgabe = (String) request.getAttribute("admin_ausgabe");
    //Fehlerausgabe für das Anlegen eines neuen Admins
    adminFehler = (String) request.getAttribute("admin_fehler");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />

        <style>
            .button_small {width: 150px}
        </style>

        <title>Administratorsicht</title>
    </head>
    <body>

        <% if (statistikURL != null) {%>

        <!--Statistik ausgeben-->
        <IMG src='<%= statistikURL%>' width='500' height='500' border='0'>

        <% } else if (request.getParameter("Zeige_BenutzerListe") != null
                || request.getAttribute("Zeige_BenutzerListe") != null) { %>

        <!--Tabelle mit Benutzern ausgeben-->
        <TABLE rules="groups" border="2">

            <% for (Benutzer benutzer : benutzerListe) {%>

            <form method="POST" action="AdminServlet">

                <!--Benutzer-Email übergeben-->
                <input type="hidden" name="Ben_Email" 
                       value="<%= benutzer.getEmail()%>">
                <!--Index des Benutzers innerhalb der benutzerListe übergeben-->
                <input type="hidden" name="Ben_Index" 
                       value="<%= benutzerListe.indexOf(benutzer)%>">

                <tbody>

                    <tr>
                        <!--Benutzer-Email-->
                        <td colspan="2" width="400">
                            <h3><%= benutzer.getEmail()%></h3>
                        </td>

                        <!--Buttons für Löschen/Aktivieren/PW zurücksetzen-->
                        <td rowspan="2" style="border-left:solid 2px black">
                            <button type="submit" class="button_small" name="Ben_loeschen"
                                    value="OK"
                                    <%= benutzer.getStatus().getBenutzerStatusId()
                                            == Konstanten.ID_BEN_STATUS_GELOESCHT
                                                    ? "disabled"
                                                    : ""%>>
                                Löschen
                            </button>
                            <br>
                            <button type="submit" class="button_small" name="Ben_aktivieren"
                                    value="OK"
                                    <%= benutzer.getStatus().getBenutzerStatusId()
                                            == Konstanten.ID_BEN_STATUS_AKTIV
                                                    ? "disabled"
                                                    : ""%> >
                                Aktivieren
                            </button>
                            <br>
                            <button type="submit" class="button_small" 
                                    name="PW_zurueck" value="OK" 
                                    onclick="confirm('Passwort wirklich zurücksetzen?')">
                                Passwort zurücksetzen
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <!--Ausgabe des Benutzerstatus
                            Aktiv wird in Grün dargestellt unbestätigt und gelöscht in Rot
                            -->
                                  <span style="color:<%= benutzer.getStatus().getBenutzerStatusId()
                                    == Konstanten.ID_BEN_STATUS_AKTIV
                                            ? "green"
                                            : "red"%> ">
                                <%= benutzer.getStatus().getName()%>
                            </span>
                        </td>
                        <!--Auflistung der Benutzerrechte-->
                        <td>
                            <%for (Benutzer_Recht recht : benutzer.getRechte()) {
                                    out.write(recht.getName());
                                    out.write("<br>");
                                }%>
                        </td>
                    </tr>

                </tbody>
            </form>
            <% } %>
        </TABLE>

        <% } else if (request.getParameter("Zeige_Admin_pw_aendern") != null
                || pwAusgabe != null || pwFehler != null) {%>

        <!--Formular zum Ändern des Adminpassworts anzeigen-->
        <div>
            <form method="POST" action="AdminServlet">
                <h3>Passwort ändern</h3>
                <p>
                    <%=pwAusgabe == null
                            ? ""
                            : "<span style='color: green'>" + pwAusgabe + "</span>"%>
                    <%=pwFehler == null
                            ? ""
                            : "<span style='color: red'>" + pwFehler + "</span>"%>
                </p>
                <p>
                    <span class="span_reg">Neues Passwort</span>
                    <input type="password" name="neues_pw">
                </p>
                <p>
                    <span class="span_reg">Passwort wiederholen</span>
                    <input type="password" name="check_pw">
                </p>
                <p>
                    <button type="submit" name="Admin_pw_aendern" 
                            class="submit_add">
                        Bestätigen
                    </button>
                </p>

            </form>
        </div>

        <% } else if (request.getParameter("Zeige_Admin_anlegen") != null
                || adminAusgabe != null || adminFehler != null) {%>

        <div>
            <form method="POST" action="AdminServlet">
                <h3>Adminaccount erstellen</h3>
                <p>
                    <%= adminAusgabe == null
                            ? ""
                            : "<span style='color: green'>" + adminAusgabe
                            + "</span>"%>
                    <%=adminFehler == null
                            ? ""
                            : "<span style='color: red'>" + adminFehler
                            + "</span>"%>
                </p>
                <p>
                    <span class="span_reg">Name</span>
                    <input type="text" name="admin_name">
                </p>
                <p>
                    <span class="span_reg">Passwort</span>
                    <input type="password" name="admin_pw">
                </p>
                <p>
                    <span class="span_reg">Passwort wiederholen</span>
                    <input type="password" name="check_pw">
                </p>
                <p>
                    <button type="submit" name="Admin_erstellen" 
                            class="submit_add">
                        Admin anlegen
                    </button>
                </p>
            </form>
        </div>

        <% }%>

    </body>
</html>
