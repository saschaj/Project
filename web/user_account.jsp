
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
                  die Möglichkeit hat,die Fehler zu korrigieren.
                - Fehlern in dem Formular auswerfen und Konstanten.REQUEST_ATTR_FEHLER
                  auh null setzen.
                - Feld Telefonummer in dem Formular hinzugefügt.

--%>
<%@page import="Entitys.Kunde"%>
<%@page import="Entitys.Adresse"%>
<%@page import="java.util.Date"%>
<%@page import="Hilfsklassen.Konstanten"%>
<% String fehler[]; %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Benutzer-Account</title>
        <%
            Kunde k = (Kunde) session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
            Adresse adr = k.getAdresse();
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
                    <form method="POST" action="BenutzerServlet">

                        <div id="form_settings">

                            <h2>Kundendaten:</h2>
                            <%     if (request.getAttribute("fehler") != null) {
                                    fehler = (String[]) request.getAttribute("fehler");
                                    for (int i = 0; i < fehler.length; i++) {%>
                            <span style="color:#FF0000"><%= fehler[i]%></span><br>
                            <%}
                                    request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, null);
                                }
                                if (k != null && !k.getVorname().equals("")) {%>
                            <p><span class="span_contact">Vorname:</span><input type="text" name="acc_vname" value="<%= k.getVorname()%>" ></p>    
                                <% } else { %>
                            <p><span class="span_contact">Vorname:</span><input type="text" name="acc_vname" value="" ></p>
                                <% } %>
                                <% if (k != null && !k.getNachname().equals("")) {%>
                            <p><span class="span_contact">Nachname:</span><input type="text" name="acc_name" value="<%= k.getNachname()%>" ></p>   
                                <% } else { %>
                            <p><span class="span_contact">Nachname:</span><input type="text" name="acc_name" value="" ></p>
                                <% } %>
                            
                               <% if (k != null && k.getGeburtsdatum() != null) {%>
                            <p><span class="span_contact">Geburtsdatum:</span><input type="text" name="acc_gebdat" value="<%= k.getGeburtsdatum()%>"></p> 
                                <% } else { %>
                            <p><span class="span_contact">Geburtsdatum:</span><input type="text" name="acc_gebdat" value=""></p>
                                <% }%> 

                            <p><span class="span_contact">Strasse & Hausnummer:</span>
                                <% if (k != null && adr != null && !adr.getStrasse().equals("")) {%>
                                <input type="text" name="acc_strasse" value="<%= adr.getStrasse()%>" >
                                <% } else { %>
                                <input type="text" name="acc_strasse" value="" >
                                <% } %>

                                <% if (k != null && adr != null && !adr.getHausNr().equals("")) {%>
                                <input type="text" name="acc_hnr" value="<%= adr.getHausNr()%>" >
                                <% } else { %>
                                <input type="text" name="acc_hnr" value="" >
                                <% } %>                            
                            </p>                        

                            <% if (k != null && adr != null && !adr.getPlz().equals("")) {%>
                            <p><span class="span_contact">PLZ:</span><input type="text" name="acc_plz" value="<%= adr.getPlz()%>"></p>  
                                <% } else { %>
                            <p><span class="span_contact">PLZ:</span><input type="text" name="acc_plz" value=""></p>
                                <% } %>                         
                                <% if (k != null && adr != null && !adr.getOrt().equals("")) {%>
                            <p><span class="span_contact">Ort:</span><input type="text" name="acc_ort" value="<%= adr.getOrt()%>"></p>  
                                <% } else { %>
                            <p><span class="span_contact">Ort:</span><input type="text" name="acc_ort" value=""></p>
                                <% } %>
                                <% if (k != null && adr != null && !adr.getLand().equals("")) {%>
                            <p><span class="span_contact">Land:</span><input type="text" name="acc_land" value="<%= adr.getLand()%>"></p>  
                                <% } else { %>
                            <p><span class="span_contact">Land:</span><input type="text" name="acc_land" value=""></p>
                                <% } %>
                                
                              <% if (request.getParameter("acc_tel")!= null) {%>
                            <p><span class="span_contact">Telefonnummer:</span><input type="text" name="acc_tel" value="<%= request.getParameter("acc_tel")%>"></p> 
                                <% } else { %>
                            <p><span class="span_contact">Telefonnummer:</span><input type="text" name="acc_tel" value=""></p>
                                <% }%>  
                        </div>

                        <div id="form_settings">
                            <h2>Benutzerdaten</h2>
                            <p>Wenn Sie Ihre Benutzerdaten ändern wollen, müssen Sie alle Felder ausfüllen!</p>


                            <%
                                 if (request.getParameter("e-mail") != null) {%>   

                            <p><span class="span_contact">E-mail:</span><input type="text" name="e-mail" value="<%= request.getParameter("e-mail")%>" /></p>
                                <% } else {%>
                            <p><span class="span_contact">E-Mail:</span> <input type="text" name="e-mail" value=""></p>

                            <% }
                                    if (request.getParameter("e-mail_n") != null) {%>
                            <p><span class="span_contact">Neue E-mail wiederholen:</span><input type="text" name="e-mail_n" value="<%= request.getParameter("e-mail_n")%>" /></p>
                                <% } else {%>
                            <p> <span class="span_contact">Neue E-Mail wiederholen:</span> <input type="text" name="e-mail_n" value=""></p>
                                <% }
                                if (request.getParameter("pw") != null) {%>
                            <p><span class="span_contact">Passwort:</span><input type="text" name="pw" value="<%= request.getParameter("pw")%>" /></p>
                                <% } else {%>

                            <p> <span class="span_contact">Passwort:</span> <input type="password" name="pw" value=""></p>
                                <% }
                                   if (request.getParameter("pw_n") != null) {%>
                            <p><span class="span_contact">Neues Passwort wiederholen:</span><input type="password" name="pw_n" value="<%= request.getParameter("pw_n")%>" /></p>
                                <% } else {%>

                            <p> <span class="span_contact">Neues Passwort wiederholen:</span> <input type="password" name="pw_n" value=""></p>
                                <% }%>
                        </div>

                        <input class="submit" type="submit" name="Speichern" value="Speichern" >
                    </form>

                </div><!--close content-->
            </div><!--close site_content-->

            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>

        </div>

    </body>
</html>
