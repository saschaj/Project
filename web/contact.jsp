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
        <title>Kontakt</title>
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
                            <li><a href="index.jsp">Startseite</a></li>
                            <li class="current"><a href="contact.jsp">Kontakt</a></li>
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
                    <h1>Kontaktieren Sie uns!</h1>
                    <p>Bei Verbesserungsvorschläge, Anmerkungen oder ähnlichem nutzen Sie bitte unser Kontakformular.</p>
                                    
                    <div id="content_item">
                        <div id="form_settings">
                            <form method="POST" action="KontaktServlet">
                                <p><span>Name:</span><input class="contact" type="text" name="your_name" value="" /></p>
                                <p><span>Email Adresse*:</span><input class="contact" type="text" name="your_email" value="" /></p>
                                <p><span>Mitteilung*:</span><textarea class="contact textarea" rows="4" cols="50" name="your_message"></textarea></p>
                                <p><span>Bitte beantworten Sie die folgende einfache Aufgabe:</span></p>
                                <p><span>17 + 25 = ?</span><input class="contact" type="text" name="benutzer_antwort" /><input type="hidden" name="antwort" value="42" /></p>                                    
                                <p>* Bitte füllen Sie die Felder aus</p>
                                <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="contact_submitted" value="Abschicken" /></p>
                            </form>
                        </div>
                    </div>
                </div>
                
            </div>
            
            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>
            
        </div>
        
    </body>
</html>
