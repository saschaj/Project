<%-- 
    Document   : Index
    Created on : 25.05.2015, 20:11:23
    Author     : Sascha Jungenkrüger
--%>

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
                            <li><a href="user.jsp">Vertragsübersicht</a></li>
                            <li><a href="contact.jsp">Kontakt</a></li>
                            <li class="current"><a href="user_account.jsp">Account verwalten</a></li>
                            <li><a href="logout.jsp">Abmelden</a></li>
                            <%-- Testseiten.. Werden später wieder entfernt --%>
                            <li><a href="index.jsp">Startseite</a></li>
                            <li><a href="admin.jsp">Admin</a></li>
                        </ul>
                    </nav>
                    
                </div>
            </header>
            
            <div id="site_content">
                
                <div id="content">
                    <h1>Benutzer-Account Verwaltung</h1>
                    <p>Hier kommt der Inhalt zum Ändern der Benutzerdaten rein.</p>
                </div>
                
            </div>
            
            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>
            
        </div>
        
    </body>
</html>
