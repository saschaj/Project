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

            #dialogoverlay{
                display: none;
                opacity: .8;
                position: fixed;
                top: 0px;
                left: 0px;
                background: #FFF;
                width: 100%;
                z-index: 10;
            }
            #dialogbox{
                display: none;
                position: fixed;
                background: #000;
                border-radius:7px; 
                width:550px;
                z-index: 10;
            }
            #dialogbox > div{ background:#FFF; margin:8px; }
            #dialogbox > div > #dialogboxhead{ background: #666; font-size:19px; padding:10px; color:#CCC; }
            #dialogbox > div > #dialogboxbody{ background:#333; padding:20px; color:#FFF; }
            #dialogbox > div > #dialogboxfoot{ background: #666; padding:10px; text-align:right; }
        </style>

        <script>
            function CustomAlert() {
                this.render = function (dialog) {
                    var winW = window.innerWidth;
                    var winH = window.innerHeight;
                    var dialogoverlay = document.getElementById('dialogoverlay');
                    var dialogbox = document.getElementById('dialogbox');
                    dialogoverlay.style.display = "block";
                    dialogoverlay.style.height = winH + "px";
                    dialogbox.style.left = (winW / 2) - (550 * .5) + "px";
                    dialogbox.style.top = "100px";
                    dialogbox.style.display = "block";
                    document.getElementById('dialogboxhead').innerHTML = "Acknowledge This Message";
                    document.getElementById('dialogboxbody').innerHTML = dialog;
                    document.getElementById('dialogboxfoot').innerHTML = '<button onclick="Alert.ok()">OK</button>';
                }
                this.ok = function () {
                    document.getElementById('dialogbox').style.display = "none";
                    document.getElementById('dialogoverlay').style.display = "none";
                }
            }
            var Alert = new CustomAlert();

            function CustomConfirm() {
                this.render = function (dialog) {
                    var winW = window.innerWidth;
                    var winH = window.innerHeight;
                    var dialogoverlay = document.getElementById('dialogoverlay');
                    var dialogbox = document.getElementById('dialogbox');
                    dialogoverlay.style.display = "block";
                    dialogoverlay.style.height = winH + "px";
                    dialogbox.style.left = (winW / 2) - (550 * .5) + "px";
                    dialogbox.style.top = "100px";
                    dialogbox.style.display = "block";

                    document.getElementById('dialogboxhead').innerHTML = "Confirm that action";
                    document.getElementById('dialogboxbody').innerHTML = dialog;
                    document.getElementById('dialogboxfoot').innerHTML = '<button onclick="Confirm.yes()">Yes</button> <button onclick="Confirm.no()">No</button>';
                }
                this.no = function () {
                    document.getElementById('dialogbox').style.display = "none";
                    document.getElementById('dialogoverlay').style.display = "none";
                }
                this.yes = function () {
                    document.getElementById('dialogbox').style.display = "none";
                    document.getElementById('dialogoverlay').style.display = "none";
                }
            }
            var Confirm = new CustomConfirm();
        </script>


        <title>Administratorsicht</title>
    </head>
    <body>

        <div id="dialogoverlay"></div>
        <div id="dialogbox">
            <div>
                <div id="dialogboxhead"></div>
                <div id="dialogboxbody"></div>
                <div id="dialogboxfoot"></div>
            </div>
        </div>

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
                            
                            <button class="button_small" id="test" type="submit"
                                    name="PW_zurueck" value="OK" 
                                    onclick="this.disabled = true; Confirm.render('TEST TEST')">
                                Passwort zurücksetzen
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <!--Ausgabe des Benutzerstatus
                            Aktiv wird in Grün dargestellt unbestätigt und gelöscht in Rot
                            -->
                            <span style="color:
                                  <%= benutzer.getStatus().getBenutzerStatusId()
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
