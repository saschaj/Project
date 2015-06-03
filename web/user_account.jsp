<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   30.05.2015
Dokument:	user_account.jsp
Version:	1.1
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Template an Entwurf angepasst
                1.1 (Sascha Jungenkrüger)
                - Einfügen der Formulardaten

--%>
<%@page import="java.sql.Date"%>
<%@page import="Hilfsklassen.Konstanten"%>
<% Benutzer ben = (Benutzer)session.getAttribute(Konstanten.SESSION_ATTR_BENUTZER);
   Kunde k = (Kunde)session.getAttribute(Konstanten.SESSION_ATTR_KUNDE); %>

<%@page import="Entitys.Benutzer"%>
<%@page import="Entitys.Kunde"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Benutzer-Account</title>
    </head>
    <body>
        <div id="main">
            
            <header>
                <div id="header_section">
                    
                    <div id="welcome">
                        <h2>SWP SS 2015</h2>
                    </div>
                    
                    <nav>
                        <ul id="nav">
                            <li>
                                <a href="user.jsp">Vertragsübersicht</a>
                            </li>
                            <li>
                                <a href="contact.jsp">Kontakt</a>
                            </li>
                            <li class="current">
                                <a href="user_account.jsp">Account verwalten</a>
                            </li>
                            <li>
                                <a href="LoginLogoutServlet?logout=true">Abmelden</a>
                            </li>
                            <%-- Testseiten.. Werden später wieder entfernt --%>
                            <li><a href="index.jsp">Startseite</a></li>
                            <li><a href="admin.jsp">Admin</a></li>
                        </ul><!--close nav-->
                    </nav>
                    
                </div><!--close header_section-->
            </header>
            
            <div id="site_content">
                
                <div id="content">
                    <h1>Benutzer-Account Verwaltung</h1>
                    <form method="POST" action="BenutzerServlet">
                    <div id="form_settings">
                        <h2>Kundendaten:</h2>
                        <% if (k != null && !k.getVorname().equals("")) { %>
                        <p><span>Vorname:</span><input type="text" name="acc_vname" value="<%= k.getVorname() %>" ></p>    
                        <% } else { %>
                        <p><span>Vorname:</span><input type="text" name="acc_vname" value="" ></p>
                        <% } %>
                        <% if (k != null && !k.getNachname().equals("")) { %>
                        <p><span>Nachname:</span><input type="text" name="acc_name" value="<%= k.getNachname() %>" ></p>   
                        <% } else { %>
                        <p><span>Nachname:</span><input type="text" name="acc_name" value="" ></p>
                        <% } %>
                        
                        <p><span>Strasse & Hausnummer:</span>
                            <% if (k != null && !k.getStrasse().equals("")) { %>
                            <input type="text" name="acc_strasse" value="<%= k.getStrasse() %>" >
                            <% } else { %>
                            <input type="text" name="acc_strasse" value="" >
                            <% } %>
                            
                            <% if (k != null && !k.getHausnummer().equals("")) { %>
                            <input type="text" name="acc_hnr" value="<%= k.getHausnummer() %>" >
                            <% } else { %>
                            <input type="text" name="acc_hnr" value="" >
                            <% } %>                            
                        </p>                        
                        
                        <% if (k != null && !k.getPLZ().equals("")) { %>
                        <p><span>PLZ:</span><input type="text" name="acc_plz" value="<%= k.getPLZ() %>"></p>  
                        <% } else { %>
                        <p><span>PLZ:</span><input type="text" name="acc_plz" value=""></p>
                        <% } %>                         
                        <% if (k != null && !k.getOrt().equals("")) { %>
                        <p><span>Ort:</span><input type="text" name="acc_ort" value="<%= k.getOrt() %>"></p>  
                        <% } else { %>
                        <p><span>Ort:</span><input type="text" name="acc_ort" value=""></p>
                        <% } %>
                        <% if (k != null && !k.getGeburtsdatum().equals("")) { %>
                        <p><span>Geburtsdatum:</span><input type="text" name="acc_gebdat" value="<%= k.getGeburtsdatum() %>"></p> 
                        <% } else { %>
                        <p><span>Geburtsdatum:</span><input type="text" name="acc_gebdat" value=""></p>
                        <% } %>                        
                    </div>
                    
                    <div id="form_settings">
                        <h2>Benutzerdaten</h2>
                        <p>Wenn Sie Ihre Benutzerdaten ändern wollen, müssen Sie alle Felder ausfüllen!</p>
                        <p>
                            <span>E-Mail:</span>
                            <input type="text" name="e-mail" value="">
                        </p>
                        <p>
                            <span>Neue E-Mail wiederholen:</span>
                            <input type="text" name="e-mail_n" value="">
                        </p>
                        <p>
                            <span>Passwort:</span>
                            <input type="password" name="pw" value="">
                        </p>
                        <p>
                            <span>Neues Passwort wiederholen:</span>
                            <input type="password" name="pw_n" value="">
                        </p>
                    </div>
                        <input type="submit" name="Speichern" value="Änderungen speichern" >
                    </form>
                    
                </div><!--close content-->
                
            </div><!--close site_content-->
            
            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>
            
        </div>
        
    </body>
</html>
