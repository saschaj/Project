<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   03.06.2015
Dokument:	contract_search.jsp
Version:	1.0 
                - Template angepasst
                - Formular angepasst
                1.1 Sascha Jungenkrüger
                - Neuer Button zum Löschen eingefügt

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
        <!--CSS für Bestätigungsfenster importieren-->
        <link rel="stylesheet" type="text/css" href="css/dialog.css" />
        <script>
	    function CustomConfirm() {

		this.render = function (dialog, formId) {

		    var winW = window.innerWidth;
		    var winH = window.innerHeight;
		    var dialogoverlay = document.
                            getElementById('dialogoverlay');
		    var dialogbox = document.getElementById('dialogbox');
		    dialogoverlay.style.display = "block";
		    dialogoverlay.style.height = winH + "px";
		    dialogbox.style.left = (winW / 2) - (550 * .5) + "px";
		    dialogbox.style.top = "100px";
		    dialogbox.style.display = "block";

		    document.getElementById('dialogboxhead').
                            innerHTML = "Bitte bestätigen!";
		    document.getElementById('dialogboxbody').innerHTML = dialog;
		    document.getElementById('dialogboxfoot').innerHTML 
			    = '<button class="submit" \n\
                                onclick="Confirm.yes(\'' + formId + '\')">\n\
				Ja\n\
				</button>\n\
				<button class="submit" \n\
                                onclick="Confirm.no()">\n\Nein\n\
				</button>';
		};

		this.no = function () {
		    document.getElementById('dialogbox').
                            style.display = "none";
		    document.getElementById('dialogoverlay').
                            style.display = "none";
		};

		this.yes = function (formId) {
		    document.getElementById(formId).submit();
		    document.getElementById('dialogbox').
                            style.display = "none";
		    document.getElementById('dialogoverlay').
                            style.display = "none";
		};
	    }
	    ;

	    var Confirm = new CustomConfirm();
	</script>
    </head>
    <body>
        <!--Elemente für Bestätigenfenster-->
	<div id="dialogoverlay"></div>
	<div id="dialogbox">
	    <div>
		<div id="dialogboxhead"></div>
		<div id="dialogboxbody"></div>
		<div id="dialogboxfoot"></div>
	    </div>
	</div>
        <div id="main">
            <div class="content" style="width:650px">

                <%  if (request.getParameter("search") != null) {
                        if (request.getAttribute("kategorie") != null) {%>
                <h2><%= request.getAttribute("kategorie")%></h2>
                <%      } else if (request.getAttribute("suchText") != null) {%>
                <h2>Ergebnisse für ihren Suchbegriff 
                    "<%= request.getAttribute("suchText")%>"</h2>
                <% }  %> 
                <% if (request.getAttribute("vertraege") != null) { %>                
                        <%  vertraege = (Collection<Vertrag>) 
                                request.getAttribute("vertraege");
                            for (Vertrag vertrag : vertraege) { %>
                            <p>                            
                             <% if (!vertrag.isIstGeloescht()) { %>                            
                            <form method="POST" action="VertragServlet" 
                                  style="display:inline">
                            <span class="span_search">
                                <%= vertrag.getVertragsBezeichnung()%></span>
                            <input class="submit" style="width:150px" 
                                   type="submit" name="aendern" 
                                   value="Anzeigen/Ändern" />
                            <input type="hidden" name="vertrag" 
                                   value="<%= vertrag.getVertragId()%>">
                            </form>
                            <form method="POST" action="VertragServlet" 
                                  id="vertrag_loeschen" style="display:inline">
                            <button type="button" class="submit" 
                                    style="width:150px"
				    onclick="Confirm.render(
						'Vertrag wirklich löschen?',
						'vertrag_loeschen')">
				Löschen
			    </button>
                            <input type="hidden" name="loeschen" value="1">
                            <input type="hidden" name="vertrag" 
                                   value="<%= vertrag.getVertragId()%>">
                            </form>
                            <% } else { %>   
                            <form method="POST" action="VertragServlet">
                            <span class="span_search">
                                <%= vertrag.getVertragsBezeichnung()%>
                            </span>
                            <input class="submit_marked" style="width:150px" 
                                   type="submit" name="aendern" 
                                   value="Anzeigen" />
                            <input type="hidden" name="vertrag" 
                                   value="<%= vertrag.getVertragId()%>">
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
