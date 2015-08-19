<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   03.06.2015
Dokument:	contract_search.jsp
Version:	1.0
Veränderungen:	-

--%>
<%@page import="java.util.Collection"%>
<%@page import="Entitys.Vertrag"%>
<% Collection<Vertrag> vertraege = null; %>
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
                        <%  vertraege = (Collection<Vertrag>) request.getAttribute("vertraege");
                            for (Vertrag vertrag : vertraege) { %>
                            <p>                            
                             <% if (!vertrag.isIstGeloescht()) { %>                            
                            <form method="POST" action="VertragServlet">
                            <span class="span_search"><%= aenderUmlaute(vertrag.getVertragsBezeichnung())%></span>
                            <input class="submit" style="width:150px" type="submit" name="aendern" value="Anzeigen/Ändern" />   
                            <input class="submit" style="width:150px" type="submit" name="loeschen" value="Kündigen" />
                            <input type="hidden" name="vertrag" value="<%= vertrag.getVertragId()%>">
                            </form>
                            <% } else { %>   
                            <form method="POST" action="user.jsp">
                            <span class="span_search"><%= aenderUmlaute(vertrag.getVertragsBezeichnung())%></span>
                            <input class="submit_marked" style="width:150px" type="submit" name="aendern" value="Anzeigen" />
                            <input type="hidden" name="vertrag" value="<%= vertrag.getVertragId()%>">
                            <input type="hidden" name="change" value="1">
                            </form>
                            <% } %>                            
                        <% } %>        
                <% } else { %>
                Es wurde keine passenden Ergebnisse gefunden!
                <%}
                }%>

            </div>
        </div>
    </body>
</html>

<%! 
        public String aenderUmlaute(String begriff) {
        if (begriff.contains("Ã¤")) {
            begriff = begriff.replace("Ã¤", "ae");
        }
        if (begriff.contains("Ã¶")) {
            begriff = begriff.replace("Ã¶", "oe");
        }
        if (begriff.contains("Ã¼")) {
            begriff = begriff.replace("Ã¼", "ue");
        }
        if (begriff.contains("Ã")) {
            begriff = begriff.replace("Ã", "ss");
        }
        return begriff;
    }
%>