
<%@page import="java.text.SimpleDateFormat"%>
<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   30.05.2015
Dokument:	user_account.jsp
Version:	1.2
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Template an Entwurf angepasst
                1.1 (Sascha Jungenkrüger)
                - Einfügen der Formulardaten
                1.2 (René Kanzenbach) 11.06.2015
                - Navigationsleiste ausgelagert
                1.3(Julie Kenfack) 20.07.2015
                - Formular angepasst, damit der User beim Ausfüllen der Formular
                  die Möglichkeit hat,die Fehlern zu korrigieren.
                - Fehlern in dem Formular auswerfen und Konstanten.REQUEST_ATTR_FEHLER
                  auh null setzen.
                - Datepicker hinzugefügt.
                - Feld Telefonummer in dem Formular hinzugefügt.

--%>
<%@page import="Entitys.Kunde"%>
<%@page import="Entitys.Adresse"%>
<%@page import="java.util.Date"%>
<%@page import="Hilfsklassen.Konstanten"%>
<% String fehler[], fehler2[]; String error[]; %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Benutzer-Account</title>
        <script>
            $(function () {
                $("#datepicker").datepicker({
                    dateFormat: 'dd.mm.yy'
                });
            });
            $(function () {
                $("#datepicker2").datepicker({
                    dateFormat: 'dd.mm.yy'
                });
            });
        </script>
        <%
            Kunde k = (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
            Adresse adr = k.getAdresse();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        %>
    </head>
    <body>
        <div id="main">

            <header>
                <div id="header_section">

                    <div id="welcome">
                        <h2>SWP SS 2015</h2>
                    </div>

                    <%-- Navigationsbereich --%>
                    <jsp:include page="navigation.jsp">
                        <jsp:param name="HIGHLIGHT_LINK" value="BENUTZER_ACCOUNT" />
                    </jsp:include>

                </div><!--close header_section-->
            </header>

            <div id="site_content">

                <div id="content">

                    <h1>Benutzer-Account Verwaltung</h1>


                    <div id="form_settings">

                        <h2>Kundendaten:</h2>
                        <div class="sidebar">
                            <div class="sidebar_item">

                                <form method="POST" action="BenutzerServlet">
                                    <%     if (request.getAttribute("fehler") != null) {
                                            fehler = (String[]) request.getAttribute("fehler");
                                            for (int i = 0; i < fehler.length; i++) {%>
                                    <span style="color:#FF0000"><%= fehler[i]%></span><br>
                                    <%}
                                            request.setAttribute(Konstanten.URL_PARAM_FEHLER, null);
                                        }
                                        if (k != null && !k.getVorname().equals("")) {%>
                                    <p><span class="span_contact">Vorname*:</span><input type="text" name="acc_vname" value="<%= k.getVorname()%>" ></p>    
                                        <% } else if (request.getParameter("acc_vname") != null) {%>
                                    <p><span class="span_contact">Vorname:</span><input type="text" name="acc_vname" value="<%=request.getParameter("acc_vname")%>"></p>
                                        <% } else { %>
                                    <p><span class="span_contact">Vorname*:</span><input type="text" name="acc_vname" value="" ></p>
                                        <% } %>

                                    <% if (k != null && !k.getNachname().equals("")) {%>
                                    <p><span class="span_contact">Nachname*:</span><input type="text" name="acc_name" value="<%= k.getNachname()%>" ></p>   
                                        <% } else if (request.getParameter("acc_name") != null) {%>
                                    <p><span class="span_contact">Nachname:</span><input type="text" name="acc_name" value="<%=request.getParameter("acc_name")%>"></p>
                                        <% } else { %>
                                    <p><span class="span_contact">Nachname*:</span><input type="text" name="acc_name" value="" ></p>
                                        <% } %>

                                    <% if (k != null && k.getGeburtsdatum() != null) {%>
                                    <p><span class="span_contact">Geburtsdatum:</span><input type="text" pattern="[0-3][0-9].[0-1][0-9].[1-2][0-9]{3}" title="Datumsformat lautet: DD.MM.YYYY" name="acc_gebdat" value="<%= dateFormatter.format(k.getGeburtsdatum())%>"></p> 
                                        <% } else if (request.getParameter("acc_gebdat") != null) {%>
                                    <p><span class="span_contact">Geburtsdatum:</span><input type="text" pattern="[0-3][0-9].[0-1][0-9].[1-2][0-9]{3}" title="Datumsformat lautet: DD.MM.YYYY" name="acc_gebdat" value="<%=request.getParameter("acc_gebdat")%>"></p>
                                        <% } else { %>
                                    <p><span class="span_contact">Geburtsdatum:</span><input type="text" pattern="[0-3][0-9].[0-1][0-9].[1-2][0-9]{3}" title="Datumsformat lautet: DD.MM.YYYY" name="acc_gebdat" value=""></p>
                                        <% }%> 

                                    <% if (k != null && adr != null && !adr.getStrasse().equals("")) {%>
                                    <p><span class="span_contact">Strasse:</span><input type="text" name="acc_strasse" value="<%= adr.getStrasse()%>" ></p>
                                        <% } else if (request.getParameter("acc_strasse") != null) {%>
                                    <p><span class="span_contact">Strasse:</span><input type="text" name="acc_strasse" value="<%=request.getParameter("acc_strasse")%>"></p>
                                        <% } else { %>
                                    <p><span class="span_contact">Strasse:</span><input type="text" name="acc_strasse" value="" ></p>
                                        <% } %>

                                    <% if (k != null && adr != null && !adr.getHausNr().equals("")) {%>
                                    <p><span class="span_contact">Hausnummer:</span><input type="text" name="acc_hnr" value="<%= adr.getHausNr()%>" ></p>
                                        <% } else if (request.getParameter("acc_hnr") != null) {%>
                                    <p><span class="span_contact">Hausnummer:</span><input type="text" name="acc_hnr" value="<%=request.getParameter("acc_hnr")%>"></p>
                                        <% } else { %>
                                    <p><span class="span_contact">Hausnummer:</span><input type="text" name="acc_hnr" value="" ></p>
                                        <% } %>                            


                                    <% if (k != null && adr != null && !adr.getPlz().equals("")) {%>
                                    <p><span class="span_contact">PLZ:</span><input type="text" name="acc_plz" value="<%= adr.getPlz()%>"></p>  
                                        <% } else if (request.getParameter("acc_plz") != null) {%>
                                    <p><span class="span_contact">PLZ:</span><input type="text" name="acc_plz" value="<%=request.getParameter("acc_plz")%>"></p>
                                        <% } else { %>
                                    <p><span class="span_contact">PLZ:</span><input type="text" name="acc_plz" value=""></p>
                                        <% } %>   

                                    <% if (k != null && adr != null && !adr.getOrt().equals("")) {%>
                                    <p><span class="span_contact">Ort:</span><input type="text" name="acc_ort" value="<%= adr.getOrt()%>"></p>  
                                        <% } else if (request.getParameter("acc_ort") != null) {%>
                                    <p><span class="span_contact">Ort:</span><input type="text" name="acc_ort" value="<%=request.getParameter("acc_ort")%>"></p>
                                        <% } else { %>
                                    <p><span class="span_contact">Ort:</span><input type="text" name="acc_ort" value=""></p>
                                        <% } %>

                                    <% if (k != null && adr != null && !adr.getLand().equals("")) {%>
                                    <p><span class="span_contact">Land:</span><input type="text" name="acc_land" value="<%= adr.getLand()%>"></p>  
                                        <% } else if (request.getParameter("acc_land") != null) {%>
                                    <p><span class="span_contact">Land:</span><input type="text" name="acc_land" value="<%=request.getParameter("acc_land")%>"></p>
                                        <% } else { %>
                                    <p><span class="span_contact">Land:</span><input type="text" name="acc_land" value=""></p>
                                        <% } %>

                                    <% if (k != null && k.getTelefonnummer() != null) {%>
                                    <p><span class="span_contact">Telefonnummer:</span><input type="text" name="acc_tel" value="<%= k.getTelefonnummer()%>"></p> 
                                        <% } else if (request.getParameter("acc_tel") != null) {%>
                                    <p><span class="span_contact">Telefonnummer:</span><input type="text" name="acc_tel" value="<%=request.getParameter("acc_tel")%>"></p>
                                        <% } else { %>
                                    <p><span class="span_contact">Telefonnummer:</span><input type="text" name="acc_tel" value=""></p>
                                        <% }%>  

                                    <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="kd_speichern" value="Kundendaten ändern" ></p>
                                </form>
                            </div><!--close sidebar_item--> 
                        </div><!--close sidebar-->
                    </div>

                    <div id="form_settings">
                        <h2>Benutzerdaten:</h2>
                        <%-- Formular der Anmeldung --%>
                            <%--     <% if (request.getAttribute(
                                            "error") != null) {
                                        error = (String[]) request.getAttribute("error");
                                        for (int i = 0; i < error.length; i++) {%>
                                <span class="span_error"><%= error[i]%></span><br>
                                <%  }
                                        request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, null);
                                    }%> --%>
                        <form method="POST" action="BenutzerServlet">
                              <%     if (request.getAttribute("fehler2") != null) {
                                       fehler2 = (String[]) request.getAttribute("fehler2");
                                       for (int i = 0; i < fehler2.length; i++) {%>
                               <span style="color:#FF0000"><%= fehler2[i]%></span><br>
                               <%}
                                       request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, null);
                                   }%>
                            <p>Wenn Sie Ihre Benutzerdaten ändern wollen, müssen Sie alle Felder ausfüllen!</p>
                            <p><span class="span_contact">Aktuelle E-mail:</span><input type="text" name="aktuelle e-mail" value="<%= k.getEmail()%>" /></p>
                                <%
                                    if (request.getParameter("e-mail") != null) {%>   

                            <p><span class="span_contact">Neue E-mail*:</span><input type="text" name="e-mail" value="<%= request.getParameter("e-mail")%>" /></p>
                                <% } else {%>
                            <p><span class="span_contact">Neue E-Mail*:</span> <input type="text" name="e-mail" value=""></p>

                            <% }
                                if (request.getParameter("e-mail_n") != null) {%>
                            <p><span class="span_contact">Neue E-mail wiederholen*:</span><input type="text" name="e-mail_n" value="<%= request.getParameter("e-mail_n")%>" /></p>
                                <% } else {%>
                            <p> <span class="span_contact">Neue E-Mail wiederholen*:</span> <input type="text" name="e-mail_n" value=""></p>
                                <% }
                                    if (request.getParameter("pw") != null) {%>
                            <p><span class="span_contact">Passwort*:</span><input type="password" name="pw" value="<%= request.getParameter("pw")%>" /></p>
                                <% } else {%>

                            <p> <span class="span_contact">Passwort*:</span> <input type="password" name="pw" value=""></p>
                                <% }
                                    if (request.getParameter("pw_n") != null) {%>
                            <p><span class="span_contact">Neues Passwort wiederholen*:</span><input type="password" name="pw_n" value="<%= request.getParameter("pw_n")%>" /></p>
                                <% } else {%>

                            <p> <span class="span_contact">Neues Passwort wiederholen*:</span> <input type="password" name="pw_n" value=""></p>
                                <% }%>
                            <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="bd_speichern" value="Benutzerdaten ändern" ></p>
                        </form>
                    </div>




                </div><!--close content-->
            </div><!--close site_content-->

            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>

        </div>

    </body>
</html>
