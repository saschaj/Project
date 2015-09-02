<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.05.2015
Dokument:	admin.jsp
Version:	1.1
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Template an Entwurf angepasst
		- Textsuche und Button für Statistiken eingefügt
                - JSP zur Ergebnisanzeige eingefügt
                1.1 (René Kanzenbach) 07.06.2015
                - Navigationsbereich ausgelagert

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

                    <%-- Navigationsbereich --%>
                    <jsp:include page="navigation.jsp">
                        <jsp:param name="HIGHLIGHT_LINK" value="ADMIN" />
                    </jsp:include>           

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
                        <form method="POST" action="AdminServlet" >
                            <span>Benutzer suchen: </span>
                            <p>
				<input class="search_text" type="text" 
				       name="SucheBenutzerText" value="" />
				<span>&nbsp;</span>
				<input class="submit" type="submit" 
				       name="Zeige_BenutzerListe" value="Suchen" />
			    </p>
                        </form>

                    </div>

                    <%-- Buttons für Statistiken --%>
                    <div id="form_settings">

                        <%-- Formular der Statistiken --%>
                        <form method="POST" action="AdminServlet">
                            <p>
                                <input class="submit" type="submit" 
				       name="BenutzerStatistik" value="Benutzerübersicht" />
			    </p>
                            <p>
                                <input class="submit" type="submit" 
				       name="VertragStatistik" value="Vertragsübersicht" />
                            </p>
                        </form>

                    </div>

		    <br>

		    <!--Button um neuen Admin hinzuzufügen-->	
		    <div>
			<form method="POST" action="admin.jsp">
			    <button type="submit" class="submit"
				    name="Zeige_Admin_anlegen">
				Neuen Admin anlegen
			    </button>
			</form>
		    </div>

		    <br>

		    <!-- Adminpasswort ändern Button -->
		    <div>
			<form method="POST" action="admin.jsp">
			    <input class="submit" type="submit" name="Zeige_Admin_pw_aendern" 
				   value="Adminpasswort ändern">
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
