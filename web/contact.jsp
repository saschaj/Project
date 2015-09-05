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
                1.2(Julie Kenfack)20.07.2015
                - Kontaktformular angepasst
                - Automatische erzeugung von Captcha.
                - Button "Zuruecksetzen" hinzugefügt.
				

--%>
<%@page import="Entitys.Kunde"%>
<%@page import="Entitys.Benutzer"%>
<% String fehler[]; %>
<%@page import="Hilfsklassen.Konstanten"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Kontakt</title>

		<%	//Aktuell eingeloggten Benutzer aus Session lesen
			Kunde benutzer = (Kunde) session.getAttribute(
					Konstanten.SESSION_ATTR_BENUTZER);
			
		%>
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
                                <% //if (formular.getAttribute("fehler") != null) {
									if (request.getAttribute("fehler") != null) {
										//fehler = (String[])formular.getAttribute("fehler");
										fehler = (String[]) request.getAttribute("fehler");
										for (int i = 0; i < fehler.length; i++) {%>
                                <span style="color:#FF0000"><%= fehler[i]%></span><br>
                                <%}
										request.setAttribute(Konstanten.REQUEST_ATTR_FEHLER, null);
									}
									int op1 = (int) (10 * Math.random());
									int op2 = (int) (10 * Math.random());
									if (request.getParameter("your_name") != null) {%>
                                <p><span class="span_contact">Name*:</span><input class="contact" type="text" name="your_name" value="<%= request.getParameter("your_name")%>" /></p>
                                    <% } else {%>
                                <p><span class="span_contact">Name*:</span>
									<input class="contact" type="text" name="your_name" 
										   value="<%=(benutzer == null)
												   ? "" + request.getParameter("your_name")
												   : "" + benutzer.getVorname() + " " + benutzer.getNachname()%>" /></p>
                                    <% }
										if (request.getParameter("your_email") != null) {%>
                                <p><span class="span_contact">Email Adresse*:</span>
									<input class="contact" type="text" name="your_email" 
										   value="<%=(benutzer == null)
												   ? "" + request.getParameter("your_email")
												   : "" + benutzer.getEmail()%>" /></p>
                                    <% } else {%>
                                <p><span class="span_contact">Email Adresse*:</span><input class="contact" type="text" name="your_email" value="" /></p>
                                    <% }
										if (request.getParameter("your_message") != null) {%>
                                <p><span class="span_contact">Mitteilung*:</span><textarea class="contact textarea" rows="4" cols="50" name="your_message"></textarea></p>
                                    <% } else {%>
                                <p><span class="span_contact">Mitteilung*:</span><textarea class="contact textarea" rows="4" cols="50" name="your_message"></textarea></p>
                                    <% }%>
                                <p><span>Bitte beantworten Sie die folgende einfache Aufgabe:</span></p>
                                <p><span class="span_contact"><%=op1%> + <%=op2%> = ?</span><input class="contact" type="text" name="benutzer_antwort" /><input type="hidden" name="antwort" value="<%=(op1 + op2)%>" /></p>                                    
                                <p>* Bitte füllen Sie die Felder aus</p>
                                <p style="padding-top: 15px"><span>&nbsp;</span><input class="submit" type="submit" name="contact_submitted" value="Abschicken" /><input class="submit" type="reset" name="contact_reset" value="Zuruecksetzen" /></p>
                            </form>
                        </div><!--close form_settings-->
                        <%

							request.setAttribute("opResult", String.valueOf(op1 + op2));

                        %>
                    </div><!--close content_item-->

                </div><!--close content-->                
            </div><!--close site_content-->

            <footer>
                SWP SS 2015 von Julie Kenfack, Mladen Sikiric, René Kanzenbach & Sascha Jungenkrüger
            </footer>

        </div>        
    </body>
</html>
