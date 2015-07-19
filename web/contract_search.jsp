<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   03.06.2015
Dokument:	contract_search.jsp
Version:	1.0
Veränderungen:	-

--%>
<%@page import="java.util.Collection"%>
<%@page import="Entitys.Vertrag"%>
<% Collection<Vertrag> vertraege = null;%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Verträge suchen</title>
    </head>
    <body>
        <div id="main">
            <div class="content" style="width:650px">

                <%  if (request.getParameter("search") != null) {
                        if (request.getAttribute("kategorie") != null) {%>
                <h2><%= request.getAttribute("kategorie")%></h2>
                <%      } else if (request.getAttribute("suchText") != null) {%>
                <h2>Ergebnisse für ihren Suchbegriff "<%= request.getAttribute("suchText")%>"</h2>
                <% }  %> 
                <% if (request.getAttribute("vertraege") != null) { %>
                <form method="POST" action="VertragServlet">
                    <table border="0">
                        <%  vertraege = (Collection<Vertrag>) request.getAttribute("vertraege");
                            for (Vertrag vertrag : vertraege) {%>
                        <tr>
                            <td style="width:350px"><%= vertrag.getVertragNr() %></td>
                            <td><input class="submit" style="width:100px" type="submit" name="anzeigen" value="Anzeigen" /></td>
                            <td><input class="submit" style="width:100px" type="submit" name="aendern" value="Ändern" /></td>
                            <td><input class="submit" style="width:100px" type="submit" name="loeschen" value="Löschen" /></td>
                            <td><input type="hidden" name="vertrag" value="<%= vertrag %>"></td>
                        </tr>                                        
                        <% } %>                 
                    </table>
                </form>
                <% } else { %>
                Es wurde keine passenden Ergebnnise gefunden!
                <%}
                    }%>

            </div>
        </div>


    </body>
</html>