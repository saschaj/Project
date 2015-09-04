<%--

Ersteller:	Julie Kenfack
Erstelldatum:   30.08.2015
Dokument:	contact_complete.jsp
Version:	1.0
--%>

<%@page import="Hilfsklassen.Konstanten"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv='refresh' content='4; URL=index.jsp'>
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

                    <p>Ihre Daten wurden erfolgreich aktualisiert.</p>
                    <p>Sie können sich ab sofort mit ihren neuen Daten anmelden.</p>

                </div><!--close content-->                  
            </div><!--close site_content-->

            <footer>
                SWP SS 2015 von Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>

        </div>        
    </body>
</html>
