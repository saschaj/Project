<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.05.2015
Dokument:	login_register.jsp
Version:	1.2
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Template an Entwurf angepasst
                - Formular für die Registrierung und Anmeldung eingefügt
                1.1 (Sascha Jungenkrüger) 02.05.2015
                - Formular angepasst,damit der Nutzer eingegebene Daten nicht
                  nochmal eingeben muss.
                1.2 (René Kanzenbach) 07.06.2015
                - Navigationsbereich ausgelagert

--%>
<%@page import="Hilfsklassen.Konstanten"%>
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

                    <%-- Navigationsbereich --%>
                    <jsp:include page="navigation.jsp">
                        <jsp:param name="HIGHLIGHT_LINK" value="LOGIN" />
                    </jsp:include>

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
                                    <p>
                                        <span class="span_login">Email-Adresse:</span>
                                        <input class="contact" type="text" name="login_email" value="" />
                                    </p>
                                    <p>
                                        <span class="span_login">Passwort:</span>
                                        <input class="contact" type="password" name="login_passwort" value="" />
                                    </p>
                                    <p>
                                        <input style="width:170px" class="submit" type="submit" name="login" value="Anmelden" />
                                        <input style="width:170px" class="submit" type="submit" name="get_pw" value="Passwort vergessen?" />
                                    </p>
                                </form>
                            </div><!--close sidebar_item--> 
                        </div><!--close sidebar-->
                    </div><!--close sidebar_container-->

                    <div id="form_settings">

                        <%-- Überschrift --%>
                        <h2>Registrieren</h2>
                        <%-- Formular der Registrierung --%>
                        <form method="POST" action="LoginLogoutServlet">
                            <% if (request.getAttribute("fehler") != null) {
                                    fehler = (String[]) request.getAttribute("fehler");
                                    for (int i = 0; i < fehler.length; i++) {%>
                            <span class="span_error"><%= fehler[i]%></span><br>
                            <%  }
                                    request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, null);
                                }%>
                            <p>
                            <span class="span_reg">Vorname:</span>
                            <input class="contact" type="text" name="reg_vname" value="<%= request.getParameter("reg_vname") != null ? request.getParameter("reg_vname") : ""%>" />
                            </p>                            
                            <p>
                            <span class="span_reg">Nachname:</span>
                            <input class="contact" type="text" name="reg_name" value="<%= request.getParameter("reg_name") != null ? request.getParameter("reg_name") : ""%>" />
                            </p>                            
                            <p>
                            <span class="span_reg">Email-Adresse:</span>
                            <input class="contact" type="text" name="reg_email" value="<%= request.getParameter("reg_email") != null ? request.getParameter("reg_email") : ""%>" />
                            </p>                            
                            <p>
                            <span class="span_reg">Email-Adresse wiederholen:</span>
                            <input class="contact" type="text" name="reg_email2" value="<%= request.getParameter("reg_email2") != null ? request.getParameter("reg_email2") : ""%>" />
                            </p>                           
                            <p>
                            <span class="span_reg">Passwort:</span>
                            <input class="contact" type="password" name="reg_pw" value="" />
                            </p>
                            <p>
                            <span class="span_reg">Passwort wiederholen:</span>
                            <input class="contact" type="password" name="reg_pw2" value="" />
                            </p>
                            <p style="padding-top: 15px">
                            <span>&nbsp;</span>
                            <input class="submit" type="submit" name="register" value="Registrieren" />
                            </p>
                        </form>
                    </div><!--close form_settings-->

                </div><!--close content-->                
            </div><!--close site_content-->

            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric,
                René Kanzenbach & Sascha Jungenkrüger
            </footer>

        </div><!--close main-->

    </body>
</html>
