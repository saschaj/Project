<%--

Ersteller:	René Kanzenbach
Dokument:	navigation.jsp
Info:           Erstellt dynamisch den Navigationsbereich je nach angemeldetem 
                Benutzer. 
Version:	1.0
Veränderungen:	-

--%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="Entitys.Benutzer_Recht"%>
<%@page import="java.util.Collection"%>
<%@page import="Entitys.Benutzer"%>
<%@page import="Hilfsklassen.Konstanten"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>

    <%
        //Link, welcher in der Navigationsleiste hervorgehoben werden soll.
        String highlightLink = "";
        highlightLink = request.getParameter("HIGHLIGHT_LINK");

        //Benutzer aus der Session.
        Benutzer benutzer = (Benutzer) request.getSession().getAttribute(
                Konstanten.SESSION_ATTR_BENUTZER);
        Collection<Integer> benRechte = new HashSet();

        //Wenn der Benutzer angemeldet ist.
        if (benutzer != null) {
            //Rechte des Benutzers auslesen.
            for (Benutzer_Recht recht : benutzer.getRechte()) {
                benRechte.add(recht.getBenutzerRechtId());
            }
        }
    %>

</head>
<nav>
    <ul id="nav">

		<!--BENUTZER-->
        <% if (benutzer != null && 
                benRechte.contains(Konstanten.ID_BEN_RECHT_BENUTZER_ANSICHT)) { %>

        <!-- Vertragsverwaltung -->
        <li 
            <% if (highlightLink.equals("BENUTZER")) { %>
            class="current" 
            <% } %>>
            <a href="user.jsp">Vertragsverwaltung</a>
        </li>
		
		<!-- BenutzerAccountSeite -->
        <li 
            <% if (highlightLink.equals("BENUTZER_ACCOUNT")) { %>
            class="current" 
            <% } %>>
            <a href="user_account.jsp">Benutzerprofil</a>
        </li>
		
		<!-- Kontakt -->
        <li 
            <% if (highlightLink.equals("KONTAKT")) {
            %> class="current" <%
                }
            %>>
            <a href="contact.jsp">Kontakt</a>
        </li>
        
        <!-- LogOut -->
        <li <% if (highlightLink.equals("LOGOUT")) {
            %> class="current" <%
                }
            %>>
            <a href="<%= request.getContextPath() %>/LoginLogoutServlet?<%= Konstanten.URL_PARAM_AKTION %>=<%= Konstanten.URL_AKTION_LOGOUT %>" >
                Abmelden</a>
        </li>

		
		<!--ADMIN-->
        <% } else if (benutzer != null && 
                benRechte.contains(Konstanten.ID_BEN_RECHT_ADMIN_ANSICHT)) { %>

        <!-- Adminstartseite -->
        <li <% if (highlightLink.equals("ADMIN")) {
            %> class="current" <%
                }
            %>>
            <a href="admin.jsp">Admin</a>
        </li>
		
		<!-- LogOut -->
        <li <% if (highlightLink.equals("LOGOUT")) {
            %> class="current" <%
                }
            %>>
            <a href="<%= request.getContextPath() %>/LoginLogoutServlet?<%= Konstanten.URL_PARAM_AKTION %>=<%= Konstanten.URL_AKTION_LOGOUT %>" >
                Abmelden</a>
        </li>

		
		<!--NICHT EINGELOGGT-->
        <% } else { %>

        <!-- Startseite -->
        <li <% if (highlightLink.equals("STARTSEITE")) { %>
            class="current" 
            <% } %>>
            <a href="index.jsp">Startseite</a>
        </li>
		
		<!-- Kontakt -->
        <li 
            <% if (highlightLink.equals("KONTAKT")) {
            %> class="current" <%
                }
            %>>
            <a href="contact.jsp">Kontakt</a>
        </li>
		
		<!-- LogIn & Registrierung -->
        <li <% if (highlightLink.equals("LOGIN")) {
            %> class="current" <%
                }
            %>>
            <a href="login_register.jsp">
                Anmelden & Registrieren</a>
        </li>

        <% } %>

    </ul><!--close nav-->
</nav>