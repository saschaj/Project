<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   30.05.2015
Dokument:	admin_dynamic.jsp
Version:	1.0
Veränderungen:	1.0 (Sascha Jungenkrüger)
                - Erstellung der Seite mit Musterausgabe
                1.1 René Kanzenbach (28.07.2015)
                - JSP zeigt jetzt die Statistiken an.

--%>

<%@page import="java.util.List"%>
<%@page import="Entitys.Benutzer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String benutzerStatistikURL;
    List<Benutzer> benutzerListe;
    
    //URL fuer die BenutzerStatistik auslesen.
    benutzerStatistikURL = (String) request.getAttribute("StatistikURL");
    //Liste aller gefundenen Benutzer auslesen.
    benutzerListe = (List<Benutzer>) request.getAttribute("BenutzerListe");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Administratorsicht</title>
    </head>
    <body>
        <% 
        if(benutzerStatistikURL != null) { 
        %>
            <IMG src='<%= benutzerStatistikURL %>' width='500' height='500' border='0'>
        <%
        }else if(benutzerListe != null) { 
        }
        %>        
    </body>
</html>
