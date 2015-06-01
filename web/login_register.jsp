<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.05.2015
Dokument:	login_register.jsp
Version:	1.0
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Template an Entwurf angepasst
                - Formular für die Registrierung und Anmeldung eingefügt

--%>
<% String fehler[]; %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Registrierung & Anmeldung</title>
    </head>
    <body>
        <div id="main">

            <%-- Navigationsleiste --%>
            <header>
                <div id="header_section">

                    <%-- Überschrift --%>
                    <div id="welcome">
                        <h2>SWP SS 2015</h2>
                    </div>

                    <%-- Verlinkungen --%>
                    <nav>
                        <ul id="nav">
                            <li>
                                <a href="index.jsp">Startseite</a>
                            </li>
                            <li>
                                <a href="contact.jsp">Kontakt</a>
                            </li>
                            <li class="current">
                                <a href="login_register.jsp">
                                    Anmelden & Registrieren</a>
                            </li>
                            <%-- Testseiten.. Werden später wieder entfernt --%>
                            <li><a href="admin.jsp">Admin</a></li>
                            <li><a href="user.jsp">Benutzer</a></li>
                        </ul><!--close nav-->
                    </nav>

                </div><!--close header_section-->
            </header>

            <%-- Inhalt --%>
            <div id="site_content">                
                <div id="content">

                    <div class="sidebar_container">
                        <%-- Überschrift Sidebar--%>
                        <h2>Anmelden</h2>
                        <div class="sidebar">
                            <div class="sidebar_item">

                                <%-- Formular der Anmeldung --%>
                                <form method="POST" action="LoginLogoutServlet">
                                    <p><span>Email-Adresse:</span><input class="login" type="text" name="login_email" value="" /></p>
                                    <p><span>Passwort:</span><input class="login" type="password" name="login_passwort" value="" /></p>
                                    <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="login" value="Anmelden" />
                                        <span>&nbsp;</span><input class="submit" type="submit" name="get_pw" value="Passwort vergessen?" /></p>
                                </form>

                            </div><!--close sidebar_item--> 
                        </div><!--close sidebar-->
                    </div><!--close sidebar_container-->

                    <div id="form_settings">

                        <%-- Überschrift --%>
                        <h2>Registrieren</h2>

                        <%-- Formular der Registrierung --%>
                        <form method="POST" action="LoginLogoutServlet">
                            <% //if (formular.getAttribute("fehler") != null) {
                                if (request.getAttribute("fehler") != null) {
                                //fehler = (String[])formular.getAttribute("fehler");
                                fehler = (String[])request.getAttribute("fehler");
                                for(int i=0; i < fehler.length; i++) {  %>
                                <span style="color:#FF0000"><%= fehler[i] %></span><br>
                            <% }} if (request.getParameter("reg_vname") != null) { %>
                            <p><span>Vorname:</span><input class="contact" type="text" name="reg_vname" value="<%= request.getParameter("reg_vname") %>" /></p>
                            <% } else {%>
                            <p><span>Vorname:</span><input class="contact" type="text" name="reg_vname" value="" /></p>
                            <% } if (request.getParameter("reg_name") != null) { %>
                            <p><span>Nachname:</span><input class="contact" type="text" name="reg_name" value="<%= request.getParameter("reg_name") %>" /></p>
                            <% } else {%>
                            <p><span>Nachname:</span><input class="contact" type="text" name="reg_name" value="" /></p>
                            <% } if (request.getParameter("reg_email") != null) { %>
                            <p><span>Email-Adresse:</span><input class="contact" type="text" name="reg_email" value="<%= request.getParameter("reg_email") %>" /></p>
                            <% } else {%>
                            <p><span>Email-Adresse:</span><input class="contact" type="text" name="reg_email" value="" /></p>
                            <% } if (request.getParameter("reg_email2") != null) { %>
                            <p><span>Email-Adresse wiederholen:</span><input class="contact" type="text" name="reg_email2" value="<%= request.getParameter("reg_email2") %>" /></p>
                            <% } else {%>
                            <p><span>Email-Adresse wiederholen:</span><input class="contact" type="text" name="reg_email2" value="" /></p>
                            <% }%>
                            <p><span>Passwort:</span><input class="contact" type="password" name="reg_pw" value="" /></p>
                            <p><span>Passwort wiederholen:</span><input class="contact" type="password" name="reg_pw2" value="" /></p>
                            <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="register" value="Registrieren" /></p>
                            
                        </form>
                    </div><!--close form_settings-->

                </div><!--close content-->                
            </div><!--close site_content-->

            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>

        </div>

    </body>
</html>
