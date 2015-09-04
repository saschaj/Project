<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   25.08.2015
Dokument:	contract_extend.jsp
Version:	1.0
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Template an Entwurf angepasst
                - Formular erzeugt

--%>
<%@page import="Manager.DatenZugriffsObjekt"%>
<%@page import="java.util.List"%>
<%@page import="Entitys.Zeit_Einheit"%>
<%@page import="Entitys.Kunde"%>
<%@page import="Entitys.Adresse"%>
<%@page import="java.util.Date"%>
<%@page import="Hilfsklassen.Konstanten"%>
<% String fehler[]; %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Vertragsverlängerung</title>
        <%
            List<Zeit_Einheit> einheiten
                    = new DatenZugriffsObjekt().getEinheiten();
        %>
    </head>
    <body>
        <div id="main">
            <div id="site_content">

                <div id="content">

                    <h2>Bitte geben Sie Ihre neue Vertragslaufzeit ein!</h2>
                    <%
                        if (request.getAttribute(Konstanten.REQUEST_ATTR_FEHLER) != null) {
                            fehler = (String[]) request.getAttribute(Konstanten.REQUEST_ATTR_FEHLER);
                            for (int i = 0; i < fehler.length; i++) {%>
                    <span class="span_error_contract"><%= fehler[i]%></span><br>
                    <% }
                        }%>
                    <form method="POST" action="VertragServlet" style="display:inline">
                        <p>
                            <input type="text" name="verlaengerung" 
                                   pattern="[0-9]{1,}" 
                                   title="Es sind nur ganze Ziffern erlaubt!"/>
                            <select name="laufzeitEinheit">
                                <%
                                    for (Zeit_Einheit einheit : einheiten) {%>
                                <option><%= einheit.getName()%></option>
                                <%}%>
                            </select>
                        </p>
                        <input class="submit" type="submit" 
                               name="verlaengern" value="Verlängern" />
                        <input type="hidden" name="vertragID" 
                               value="<%=request.getParameter("vertragID")%>" />
                        <input type="hidden" name="kundenEmail" 
                               value="<%=request.getParameter("kundenEmail")%>"/>
                    </form>
                    <form method="POST" action="VertragServlet" style="display:inline">
                        <input type="hidden" name="vertrag" value="<%=request.getParameter("vertragID")%>">
                        <input type="hidden" name="aendern" value="1">
                        <input class="submit" type="submit" name="back_change" value="Zurück">     
                    </form>

                </div><!--close content-->
            </div><!--close site_content-->
        </div>

    </body>
</html>
