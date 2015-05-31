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
        <title>Registrierung & Anmeldung</title>
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
                            <li><a href="contact.jsp">Kontakt</a></li>
                            <li class="current"><a href="login_register.jsp">Anmelden & Registrieren</a></li>
                            <%-- Testseiten.. Werden später wieder entfernt --%>
                            <li><a href="admin.jsp">Admin</a></li>
                            <li><a href="user.jsp">Benutzer</a></li>
                        </ul>
                    </nav>
                    
                </div>
            </header>
            
            <div id="site_content">
                
                <div id="content">
                    
                    <div class="sidebar_container">
                        <h2>Anmelden</h2>
                        <div class="sidebar">
                            <div class="sidebar_item">
                                <form method="POST" action="LoginLogoutServlet">
                                    <p><span>Email-Adresse:</span><input class="login" type="text" name="login_email" value="" /></p>
                                    <p><span>Passwort:</span><input class="login" type="password" name="login_passwort" value="" /></p>
                                    <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="login" value="Anmelden" />
                                    <span>&nbsp;</span><input class="submit" type="submit" name="get_pw" value="Passwort vergessen?" /></p>
                                </form>
                            </div><!--close sidebar_item--> 
                        </div><!--close sidebar-->
                    </div>
                    
                    <div id="form_settings">
                        
                        <h2>Registrieren</h2>
                        <form method="POST" action="LoginLogoutServlet">                        
                            <p><span>Name:</span><input class="register" type="text" name="reg_name" value="" /></p>
                            <p><span>Email-Adresse:</span><input class="register" type="text" name="reg_email" value="" /></p>
                            <p><span>Email-Adresse wiederholen:</span><input class="register" type="text" name="reg_email2" value="" /></p>
                            <p><span>Passwort</span><input class="register" type="password" name="reg_passwort" value="" /></p>
                            <p><span>Passwort wiederholen</span><input class="register" type="password" name="reg_passwort2" value="" /></p>
                            <div class="button_small">
                                <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="register" value="Registrieren" /></p>
                            </div>
                        </form>
                    </div>
                    
                </div>
                
            </div>
            
            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>
            
        </div>
        
    </body>
</html>
