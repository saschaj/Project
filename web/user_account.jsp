<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.05.2015
Dokument:	user_account.jsp
Version:	1.0
Veränderungen:	1.0
                - Template an Entwurf angepasst

--%>
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
                    <form method="POST" action="">
                    <div id="form_settings">
                        <h2>Kundendaten:</h2>
                        <p><span>Vorname:</span><input type="text" name="vorname" value="Testvorname" ></p>
                        <p><span>Nachname:</span><input type="text" name="name" value="Testname" ></p>
                        <p><span>Strasse & Hausnummer:</span>
                            <input type="text" name="strasse" value="Testname" >
                            <input type="text" name="hnr" value="00" >
                        </p>
                        <p><span>PLZ:</span><input type="text" name="plz" value="00000"></p>
                        <p><span>Ort:</span><input type="text" name="ort" value="Testort"></p>
                        <p><span>Geburtsdatum:</span><input type="date" name="name" value=""></p>
                    </div>
                    
                    <div id="form_settings">
                        <h2>Benutzerdaten</h2>
                        <p>
                            <span>E-Mail:</span>
                            <input type="text" name="e-mail" value="Testmail">
                        </p>
                        <p>
                            <span>Neue E-Mail wiederholen:</span>
                            <input type="text" name="e-mail_n" value="Testname">
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
                        <input type="submit" name="Speichern" value="save" >
                    </form>
                    
                </div><!--close content-->
                
            </div><!--close site_content-->
            
            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>
            
        </div>
        
    </body>
</html>
