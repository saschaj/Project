<%-- 
    Document   : Index
    Created on : 25.05.2015, 20:11:23
    Author     : Sascha Jungenkrüger
--%>
<%@page import="Manager.DatenZugriffsObjekt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ page import="javax.persistence.*" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>SWP SS 2015</title>
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
                            <li class="current"><a href="index.jsp">Startseite</a></li>
                            <li><a href="contact.jsp">Kontakt</a></li>
                            <li><a href="login_register.jsp">Anmelden & Registrieren</a></li>
                            <%-- Testseiten.. Werden später wieder entfernt --%>
                            <li><a href="admin.jsp">Admin</a></li>
                            <li><a href="user.jsp">Benutzer</a></li>
                        </ul>
                    </nav>
                    
                </div>
            </header>
            
            <div id="site_content">
                
                <div id="content">
                    <h1>Willkommen in unserer Vertragsverwaltung</h1>
                    <p>Das ist unsere Applikation für das Softwareprojekt im Sommersemester 2015.</p>
                    <%
                        //DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
                        //dao.beispiel();
                    %>
                </div>
                
            </div>
            
            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>
            
        </div>
        
    </body>
</html>
