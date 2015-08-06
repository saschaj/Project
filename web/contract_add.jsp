<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   03.06.2015
Dokument:	contract_add.jsp
Version:	1.0
Veränderungen:	-

--%>
<%@page import="Entitys.Netztyp"%>
<%@page import="Entitys.Interessengebiet"%>
<%@page import="java.util.List"%>
<%@page import="Entitys.Zeit_Einheit"%>
<%@page import="Manager.DatenZugriffsObjekt"%>
<%@page import="Hilfsklassen.Konstanten"%>
<% String fehler[] = null; %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" />
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <title>Vertragsverwaltung</title>
        <script>
            $(function(){
                $( "#datepicker" ).datepicker();
            });
            $(function(){
                $( "#datepicker2" ).datepicker();
            });
        </script>
    </head>
    <body>

        <div id="main" style="width:800px">
            <h2>Vertrag hinzufügen</h2>
            <form method="POST" action="user.jsp"> 
            <input class="submit" type="submit" name="cat" value="Strom" />
            <input class="submit" type="submit" name="cat" value="Gas" />
            <input class="submit" type="submit" name="cat" value="Festnetz/DSL" />
            <input class="submit" type="submit" name="cat" value="Handy" />
            <input class="submit" type="submit" name="cat" value="Zeitschriften" />
            <input type="hidden" name="check" value="check" />
            <input type="hidden" name="add" value="Neuen Vertrag anlegen" />
            </form>
            <div id="site_content">    
                
                <% if (request.getParameter("check") != null
                        || request.getAttribute("check") != null) { 
                    if (request.getAttribute(Konstanten.REQUEST_ATTR_FEHLER) != null) {
                        fehler = (String[]) request.getAttribute(Konstanten.REQUEST_ATTR_FEHLER);
                        for(int i=0; i < fehler.length ; i++) { %>
                            <span class="span_error_contract"><%= fehler[i]%></span><br>
                     <% }
                    } %>

                <div id="content">
                    <form method="POST" action="VertragServlet"> 
                        <!-- Pflichtdaten eines Vertrags -->
                        Obligatorische Daten
                        <p>
                        <span class="span_reg">Vertragsnummer:</span>
                        <input class="contact" type="text" name="vertragsNr"
                        value="<%= request.getParameter("vertragsNr") != null 
                              ? request.getParameter("vertragsNr") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Vertragsbeginn*:</span>
                        <input id="datepicker" class="contact" 
                               type="text" name="vertragsBeginn"
                        value="<%= request.getParameter("vertragsBeginn") != null 
                              ? request.getParameter("vertragsBeginn") : "" %>" readonly>
                        </p>
                        <p>
                        <span class="span_reg">Laufzeit*:</span>
                        <input class="contact" type="text" name="laufzeit"
                        value="<%= request.getParameter("laufzeit") != null 
                              ? request.getParameter("laufzeit") : "" %>">
                        <select name="laufzeiteinheit">
                            <% 
                            // Liest alles Zeiteinheiten aus der DB und gibt
                            // diese in einer Auswahlbox aus
                            List<Zeit_Einheit> einheiten = new DatenZugriffsObjekt().getEinheiten();
                            for (Zeit_Einheit einheit : einheiten) { %>
                            <option><%= einheit.getName() %></option>
                            <% } %>                            
                        </select>
                        </p>
                        <p>
                        <span class="span_reg">Vertragsende*:</span>
                        <input id="datepicker2" class="contact" 
                               type="text" name="vertragsEnde"
                        value="<%= request.getParameter("vertragsEnde") != null 
                              ? request.getParameter("vertragsEnde") : "" %>" readonly>
                        </p>
                        <p>
                        <span class="span_reg">Kündigungsfrist:</span>
                        <input class="contact" type="text" name="kuendigungsfrist"
                        value="<%= request.getParameter("kuendigungsfrist") != null 
                              ? request.getParameter("kuendigungsfrist") : "" %>">
                        <select name="kuendigungsfristeinheit">
                            <% for (Zeit_Einheit einheit : einheiten) { %>
                            <option><%= einheit.getName() %></option>
                            <% } %>    
                        </select>
                        </p>
                        
                        * Hier können Sie entweder nur das Vertragsende 
                        oder den Vertragsbeginn & die Laufzeit eingeben<br>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Kundennummer:</span>
                        <input class="contact" type="text" name="kundennr"
                        value="<%= request.getParameter("kundennr") != null 
                              ? request.getParameter("kundennr") : "" %>" >
                        </p>
                        <p>
                        <span class="span_reg">Vertragsbezeichnung:</span>
                        <input class="contact" type="text" name="vertragsbez"
                        value="<%= request.getParameter("vertragsbez") != null 
                              ? request.getParameter("vertragsbez") : "" %>" >
                        </p>
                        <p>
                        <span class="span_reg">Vertragspartner:</span>
                        <input class="contact" type="text" name="vertragspartner"
                        value="<%= request.getParameter("vertragspartner") != null 
                              ? request.getParameter("vertragspartner") : "" %>" >
                        </p>
                        <p>
                        <span class="span_reg">Benachrichtigungsfrist:</span>
                        <input class="contact" type="text" name="benachrichtigungsfrist"
                        value="<%= request.getParameter("benachrichtigungsfrist") != null 
                              ? request.getParameter("benachrichtigungsfrist") : "" %>">
                        <select name="benachrichtigungsfristeinheit">
                            <% for (Zeit_Einheit einheit : einheiten) { %>
                            <option><%= einheit.getName() %></option>
                            <% } %>    
                        </select>
                        </p>
                        <% 
                        if (request.getParameter("cat").equals("Strom")) {%>                        
                        <p>
                        <span class="span_reg">Stromzählernr:</span>
                        <input class="contact" type="text" name="snr" 
                        value="<%= request.getParameter("snr") != null 
                                ? request.getParameter("snr") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Stromzählerstand:</span>
                        <input class="contact" type="text" name="sstand"
                        value="<%= request.getParameter("sstand") != null 
                                ? request.getParameter("sstand") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Verbrauch pro Jahr(in kWh):</span>
                        <input class="contact" type="text" name="sverbrauch"
                        value="<%= request.getParameter("sverbrauch") != null 
                                ? request.getParameter("sverbrauch") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Preis pro kWh:</span>
                        <input class="contact" type="text" name="spreisKwh"
                        value="<%= request.getParameter("spreisKwh") != null 
                                ? request.getParameter("spreisKwh") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Anzahl Personen im Haushalt:</span>
                        <input class="contact" type="text" name="sanzPers"
                        value="<%= request.getParameter("sanzPers") != null 
                                ? request.getParameter("sanzPers") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Grundpreis(pro Monat):</span>
                        <input class="contact" type="text" name="gPreisMonat"
                        value="<%= request.getParameter("sPreisMonat") != null 
                                ? request.getParameter("sPreisMonat") : "" %>">
                        </p>
                        
                        <% } else if (request.getParameter("cat").equals("Gas")) { %>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Gaszählernr:</span>
                        <input class="contact" type="text" name="gnr"
                        value="<%= request.getParameter("gnr") != null 
                                ? request.getParameter("gnr") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Gaszählerstand:</span>
                        <input class="contact" type="text" name="gstand"
                        value="<%= request.getParameter("gnr") != null 
                                ? request.getParameter("gnr") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Verbrauch pro Jahr(in kWh):</span>
                        <input class="contact" type="text" name="gverbrauch"
                        value="<%= request.getParameter("gverbrauch") != null 
                                ? request.getParameter("gverbrauch") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Preis pro kWh:</span>
                        <input class="contact" type="text" name="gpreisKwh"
                        value="<%= request.getParameter("gpreisKwh") != null 
                                ? request.getParameter("gpreisKwh") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Verbrauchsfläche:</span>
                        <input class="contact" type="text" name="gflaeche"
                        value="<%= request.getParameter("gflaeche") != null 
                                ? request.getParameter("gflaeche") : "" %>">
                        </p>
                        <% } else if (request.getParameter("cat").equals("Festnetz/DSL")) { %>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Tarifname:</span>
                        <input class="contact" type="text" name="ftarifname"
                        value="<%= request.getParameter("ftarifname") != null 
                                ? request.getParameter("ftarifname") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Empfangstyp:</span>
                        <select name="fempfangstyp">
                            <% List<Netztyp> typen = new DatenZugriffsObjekt().getNetztypen(false,true);
                            for (Netztyp typ : typen) { %>
                            <option><%= typ.getName() %></option>
                            <% } %> 
                        </select>
                        </p>
                        <p>
                        <span class="span_reg">ISDN:</span>  
                        <input type="checkbox" name="fistISDN">
                        </p>
                        <p>
                        <span class="span_reg">VOIP:</span>  
                        <input type="checkbox" name="fistVOIP">
                        </p>
                        <% } else if (request.getParameter("cat").equals("Handy")) { %>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Tarifname:</span>
                        <input class="contact" type="text" name="htarifname"
                        value="<%= request.getParameter("htarifname") != null 
                                ? request.getParameter("htarifname") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Netztyp:</span>
                        <select name="hnetztyp">
                            <% List<Netztyp> typen = new DatenZugriffsObjekt().getNetztypen(true,false);
                            for (Netztyp typ : typen) { %>
                            <option><%= typ.getName() %></option>
                            <% } %> 
                        </select>
                        </p>
                        <p>
                        <span class="span_reg">Rufnummer:</span>
                        <input class="contact" type="text" name="hrufnummer"
                        value="<%= request.getParameter("rufnummer") != null 
                                ? request.getParameter("rufnummer") : "" %>">
                        </p>
                        <% } else if (request.getParameter("cat").equals("Zeitschriften")) { %>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Zeitschriftname:</span>
                        <input class="contact" type="text" name="zname"
                        value="<%= request.getParameter("zname") != null 
                                ? request.getParameter("zname") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Lieferintervall:</span>
                        <input class="contact" type="text" name="zintervall"
                        value="<%= request.getParameter("intervall") != null 
                                ? request.getParameter("intervall") : "" %>">
                        <select name="zeinheit">
                            <% for (Zeit_Einheit einheit : einheiten) { %>
                            <option><%= einheit.getName() %></option>
                            <% } %> 
                        </select>
                        </p>
                        <p>
                        <span class="span_reg">Interessengebiet</span>
                        <select name="zinteressen">
                            <% List<Interessengebiet> gebiete = new DatenZugriffsObjekt().getInteressengebiete();
                            for (Interessengebiet gebiet : gebiete) { %>
                            <option><%= gebiet.getName() %></option>
                            <% } %>  
                        </select>
                        </p>
                        <% } %>
                        <input type="hidden" name="check" value="<%= request.getParameter("check") %>">
                        <input type="hidden" name="cat" value="<%= request.getParameter("cat") %>">
                        <input class="submit" type="submit" name="contract_save" value="Vertrag speichern">        
                    </form>
                </div>               
                <% }%>

            </div>
        </div>

    </body>
</html>
