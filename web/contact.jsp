<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.05.2015
Dokument:	contact.jsp
Version:	1.0
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Template an Entwurf angepasst
                - Kontakformular erstellt und eingefügt
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
        <title>Kontakt</title>
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
                        <jsp:param name="HIGHLIGHT_LINK" value="KONTAKT" />
                    </jsp:include>

                </div>
            </header>

            <%-- Inhalt --%>
            <div id="site_content">                
                <div id="content">

                    <%-- Überschrift --%>
                    <h1>Kontaktieren Sie uns!</h1>
                    <p>Bei Verbesserungsvorschläge, Anmerkungen oder ähnlichem nutzen Sie bitte unser Kontakformular.</p>

                    <%-- Kontaktformular --%>
                    <div id="content_item">
                        <div id="form_settings">
                            <!--Der Sascha ist voll doof -->
                            <form method="POST" action="KontaktServlet">
                                <p><span>Name:</span><input class="contact" type="text" name="your_name" value="" /></p>
                                <p><span>Email Adresse*:</span><input class="contact" type="text" name="your_email" value="" /></p>
                                <p><span>Mitteilung*:</span><textarea class="contact textarea" rows="4" cols="50" name="your_message"></textarea></p>
                                <p><span>Bitte beantworten Sie die folgende einfache Aufgabe:</span></p>
                                <p><span>17 + 25 = ?</span><input class="contact" type="text" name="benutzer_antwort" /><input type="hidden" name="antwort" value="42" /></p>                                    
                                <p>* Bitte füllen Sie die Felder aus</p>
                                <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="contact_submitted" value="Abschicken" /></p>
                            </form>
                        </div><!--close form_settings-->
                    </div><!--close content_item-->

                </div><!--close content-->                
            </div><!--close site_content-->

            <footer>
                SWP SS 2015 by Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>

        </div>        
    </body>
</html>
