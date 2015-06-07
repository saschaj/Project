<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.05.2015
Dokument:	index.jsp
Version:	1.1
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Template an Entwurf angepasst
                1.1 (René Kanzenbach) 07.06.2015
                - Navigationsbereich ausgelagert

--%>

<%@page import="Hilfsklassen.Konstanten"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>SWP SS 2015</title>
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
                        <jsp:param name="HIGHLIGHT_LINK" value="STARTSEITE" />
                    </jsp:include>

                </div><!--close header_section-->
            </header>

            <%-- Inhalt --%>
            <div id="site_content">                
                <div id="content">

                    <%-- Überschrift --%>
                    <h1>Willkommen in unserer Vertragsverwaltung</h1>
                    <p>Das ist unsere Applikation für das Softwareprojekt im Sommersemester 2015.</p>

                </div><!--close content-->                  
            </div><!--close site_content-->

            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>

        </div>        
    </body>
</html>
