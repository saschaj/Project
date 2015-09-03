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
                    <p>
					Haben Sie auch die Übersicht über Ihre Verträge verloren?
					<br>
					<br>
					Ständig werden Ihre Verträge automatisch verlängert und Sie müssen mehr für die gleiche
					Leistung bezahlen, weil Sie wieder den Kündigungstermin verpasst haben ?
					<br>
					<br>
					Mit der SWPSS2015 Vertragsverwaltung erhalten Sie automatisch E-Mail-Benachrichtigungen über die
					Kündigungstermine Ihrer Verträge! Hören Sie also auf mehr zu zahlen, ohne dafür auch mehr zu bekommen!
					<br>
					<br>
					Sie müssen sich dafür lediglich registrieren und los gehts!</p>

                </div><!--close content-->                  
            </div><!--close site_content-->

            <footer>
                SWP SS 2015 von Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>

        </div>        
    </body>
</html>
