<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.05.2015
Dokument:	admin.jsp
Version:	1.0
Veränderungen:	1.0
                - Template an Entwurf angepasst
		- Textsuche und Button für Statistiken eingefügt
                - JSP zur Ergebnisanzeige eingefügt

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Adminbereich</title>
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
                            <li class="current">
                                <a href="admin.jsp">Administratorfunktionen</a>
                            </li>
                            <li>
                                <a href="contact.jsp">Kontakt</a>
                            </li>
                            <li>
                                <a href="LoginLogoutServlet?logout=true">Abmelden</a>
                            </li>
                            <%-- Testseiten.. Werden später wieder entfernt --%>
                            <li><a href="index.jsp">Startseite</a></li>
                            <li><a href="user.jsp">Benutzer</a></li>
                        </ul><!--close nav-->
                    </nav>             
                            
                </div><!--close header_section-->                 
            </header>
            
            <%-- Inhalt --%>
            <div id="site_content">                
                <div id="content">
                    
                    <%-- Überschrift --%>
                    <h1>Administratorbereich</h1>
                    
                    <%-- Seitenbar mit der dynamischen JSP --%>
                    <div class="sidebar_container_user">
                        <div class="sidebar_user">
                            <%-- Dynamische JSP --%>
                            <div class="sidebar_item_user">
                                <%@include file="admin_dynamic.jsp"%>
                            </div><!--close sidebar_item_user--> 
                        </div><!--close sidebar_user-->
                    </div><!--close sidebar_container_user-->
                    
                    <%-- Benutzersuche --%>
                    <div id="form_settings">
                        
                        <%-- Formular der Benutzersuche --%>
                        <form method="POST" action="" >
                            <span>Benutzer suchen: </span><p><input class="search_text" type="text" name="search" value="" />
                            <span>&nbsp;</span><input class="submit" type="submit" name="search_text" value="Suchen" /></p>
                        </form>
                        
                    </div>
                    
                    <%-- Button für Statistiken --%>
                    <div id="form_settings">
                        
                        <%-- Formular der Statistiken --%>
                        <form method="POST" action="">
                            <p><span>&nbsp;</span><input class="submit" type="submit" name="search_cat" value="Statistik anzeigen" /></p>
                        </form>
                        
                    </div>
                    
                </div><!--close content-->                
            </div><!--close site_content-->
            
            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>
            
        </div>        
    </body>
</html>
