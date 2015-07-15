<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.05.2015
Dokument:	user.jsp
Version:	1.1
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Template an Entwurf angepasst
                - Button für die Suche und Textsuche in Content eingefügt
                - Button "Neuen Vertrag anlegen" und JSP zur Ergebnisanzeige
                  eingefügt
                1.1 (René Kanzenbach) 07.06.2015
                - Navigationsbereich ausgelagert

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

                    <%-- Navigationsbereich --%>
                    <jsp:include page="navigation.jsp">
                        <jsp:param name="HIGHLIGHT_LINK" value="BENUTZER" />
                    </jsp:include>

                </div>
            </header>

            <div id="site_content">

                <div id="content">
                    <div class="sidebar_container_user" style="width:650px">
                        <div class="sidebar_user" style="width:650px">
                            <div class="sidebar_item_user" style="widht:650px">
                                <%-- Dynmaische JSP & Button neuer Vertrag --%>
                                <form method="POST" action="user.jsp">
                                <p><input class="submit" type="submit" name="add" value="Neuen Vertrag anlegen" /></p>
                                </form>
                                
                                <% if (request.getAttribute(Konstanten.REQUEST_ATTR_ERFOLG) != null) { %>
                                <span><%= request.getAttribute(Konstanten.REQUEST_ATTR_ERFOLG) %></span>
                                <% } %>
                                
                                <% if (request.getParameter("search") != null
                                        || request.getAttribute("search") != null) { %>
                                    <%@include file="contract_search.jsp"%>
                                <% } %> 
                                <% if (request.getParameter("add") != null
                                    || request.getAttribute("check") != null) { %>
                                    <%@include file="contract_add.jsp"%>
                                <% } %>
                                
                            </div><!--close sidebar_item--> 
                        </div><!--close sidebar-->
                    </div>

                    <div id="form_settings">
                        <%-- Formular der Vertragssuche --%>
                        <form method="POST" action="VertragServlet" >
                            Vertrag suchen:<br>
                            <p><input class="contact" style="width:170px" type="text" name="suchText" value="" /></p>
                            <p><input class="submit" type="submit" name="search" value="Suchen" /></p>
                        </form>
                    </div>

                    <div id="form_settings">
                        <%-- Formular der Vertragssuche über Kategorien --%>
                        <form method="POST" action="VertragServlet">
                            <p>Vertrag via Kategorie suchen:</p>
                            <p><input class="submit" type="submit" name="search" value="Stromvertrag" /></p>
                            <p><input class="submit" type="submit" name="search" value="Gasvertrag" /></p>
                            <p><input class="submit" type="submit" name="search" value="Festnetzvertrag" /></p>
                            <p><input class="submit" type="submit" name="search" value="Handyvertrag" /></p>
                            <p><input class="submit" type="submit" name="search" value="Zeitschriftvertrag" /></p>
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
