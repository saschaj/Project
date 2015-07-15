<%--

Ersteller:	René Kanzenbach
Dokument:	navigation.jsp
Info:           Erstellt dynamisch den Navigationsbereich je nach angemeldetem 
                Benutzer. 
Version:	1.0
Veränderungen:	-

--%>

<%@page import="Entitys.Benutzer"%>
<%@page import="Hilfsklassen.Konstanten"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>

    <%
        //Link, welcher in der Navigationsleiste hervorgehoben werden soll.
        String highlightLink = "";
        highlightLink = request.getParameter("HIGHLIGHT_LINK");
        
        Benutzer benutzer = (Benutzer) request.getSession().getAttribute(
                Konstanten.SESSION_ATTR_BENUTZER);
    %>

</head>
<nav>
    <ul id="nav">

        <!-- Startseite -->
         <li
            <% if (highlightLink.equals("STARTSEITE")) {
                    %> class="current" <%
                }
            %>>
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

        <!-- Adminstartseite -->
        <li <% if (highlightLink.equals("ADMIN")) {
                    %> class="current" <%
                }
            %>>
            <a href="admin.jsp">Admin</a>
        </li>

        <!-- Benutzerstartseite -->
        <li <% if (highlightLink.equals("BENUTZER")) {
                    %> class="current" <%
                }
            %>>
            <a href="user.jsp">Vertragsverwaltung</a>
        </li>

    </ul><!--close nav-->
</nav>
