<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.05.2015
Dokument:	user.jsp
Version:	1.0
Veränderungen:	1.0
                - Template an Entwurf angepasst
		- Button für die Suche und Textsuche in Content eingefügt
                - Button "Neuen Vertrag anlegen" und JSP zur Ergebnisanzeige
                  eingefügt

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Vertragsverwaltung</title>
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
                            <li class="current">
                                <a href="user.jsp">Vertragsübersicht</a>
                            </li>
                            <li>
                                <a href="contact.jsp">Kontakt</a>
                            </li>
                            <li>
                                <a href="user_account.jsp">Account verwalten</a>
                            </li>
                            <li>
                                <a href="LoginLogoutServlet?logout=true">Abmelden</a>
                            </li>
                            <%-- Testseiten.. Werden später wieder entfernt --%>
                            <li><a href="index.jsp">Startseite</a></li>
                            <li><a href="admin.jsp">Admin</a></li>
                        </ul>
                    </nav>
                    
                </div>
            </header>
            
            <div id="site_content">
                
                <div id="content">
                    <h1>Vertragsverwaltung</h1>
                    
                    <div class="sidebar_container_user">
                        <div class="sidebar_user">
                            <div class="sidebar_item_user">
                                <%-- Dynmaische JSP & Button neuer Vertrag --%>
                                <p><span>&nbsp;</span><input class="submit" type="submit" name="search_text" value="Neuen Vertrag anlegen" /></p>
                                <%@include file="contract_dynamic.jsp"%>
                            </div><!--close sidebar_item--> 
                        </div><!--close sidebar-->
                    </div>
                    
                    <div id="form_settings">
                        <%-- Formular der Vertragssuche --%>
                        <form method="POST" action="" >
                            <span>Vertrag suchen: </span><p><input class="search_text" type="text" name="search" value="" />
                            <span>&nbsp;</span><input class="submit" type="submit" name="search_text" value="Suchen" /></p>
                        </form>
                    </div>
                    
                    <div id="form_settings">
                        <%-- Formular der Vertragssuche über Kategorien --%>
                        <form method="POST" action="">
                            <span>Vertrag via Kategorie suchen: </span>
                            <p><span>&nbsp;</span><input class="submit" type="submit" name="search_cat" value="Strom" /></p>
                            <p><span>&nbsp;</span><input class="submit" type="submit" name="search_cat" value="Gas" /></p>
                            <p><span>&nbsp;</span><input class="submit" type="submit" name="search_cat" value="Festnetz/DSL" /></p>
                            <p><span>&nbsp;</span><input class="submit" type="submit" name="search_cat" value="Zeitschriften" /></p>
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
