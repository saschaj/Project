<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   03.06.2015
Dokument:	contract_add.jsp
Version:	1.0
Veränderungen:	-

--%>
<%@page import="Entitys.Handyvertrag"%>
<%@page import="Entitys.Festnetzvertrag"%>
<%@page import="Entitys.Gasvertrag"%>
<%@page import="Entitys.Stromvertrag"%>
<%@page import="Entitys.Vertrag"%>
<%@page import="Entitys.Netztyp"%>
<%@page import="Entitys.Interessengebiet"%>
<%@page import="java.util.List"%>
<%@page import="Entitys.Zeit_Einheit"%>
<%@page import="Manager.DatenZugriffsObjekt"%>
<%@page import="Hilfsklassen.Konstanten"%>
<% String fehler[] = null;
   String test = request.getParameter("vertrag");
   Vertrag vertrag = new DatenZugriffsObjekt().getVertrag(Integer.parseInt(test));%>
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
            <div id="site_content">    
                
                <% if (request.getAttribute(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null) {
                        fehler = (String[]) request.getAttribute(Konstanten.REQUEST_ATTR_FEHLER_CHANGE);
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
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                              ? request.getParameter("vertragsNr") : vertrag.getVertragNr() %>">
                        </p>
                        <p>
                        <span class="span_reg">Vertragsbeginn*:</span>
                        <input id="datepicker" class="contact" 
                               type="text" name="vertragsBeginn"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                              ? request.getParameter("vertragsBeginn") : vertrag.getVertragBeginn() %>" readonly>
                        </p>
                        <p>
                        <span class="span_reg">Laufzeit*:</span>
                        <input class="contact" type="text" name="laufzeit"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                              ? request.getParameter("laufzeit") : vertrag.getLaufzeit() %>">
                        <select name="laufzeiteinheit">
                            <% 
                            // Liest alles Zeiteinheiten aus der DB und gibt
                            // diese in einer Auswahlbox aus
                            List<Zeit_Einheit> einheiten = new DatenZugriffsObjekt().getEinheiten();
                            for (Zeit_Einheit einheit : einheiten) { 
                                if (einheit.getName().equals(vertrag.getLaufzeitEinheit().getName())) { %>
                                    <option selected><%= einheit.getName() %></option>
                                <%} else { %>
                                    <option><%= einheit.getName() %></option>
                                <%}%>                            
                            <% } %>                            
                        </select>
                        </p>
                        <p>
                        <span class="span_reg">Vertragsende*:</span>
                        <input id="datepicker2" class="contact" 
                               type="text" name="vertragsEnde"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                              ? request.getParameter("vertragsEnde") : vertrag.getVertragEnde() %>" readonly>
                        </p>
                        <p>
                        <span class="span_reg">Kündigungsfrist:</span>
                        <input class="contact" type="text" name="kuendigungsfrist"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                              ? request.getParameter("kuendigungsfrist") : vertrag.getKuendigungsfrist() %>">
                        <select name="kuendigungsfristeinheit">
                            <% for (Zeit_Einheit einheit : einheiten) { 
                                if (einheit.getName().equals(vertrag.getLaufzeitEinheit().getName())) { %>
                                    <option selected><%= einheit.getName() %></option>
                                <%} else { %>
                                    <option><%= einheit.getName() %></option>
                                <%}%>
                            <% } %>    
                        </select>
                        </p>
                        
                        * Hier können Sie entweder nur das Vertragsende 
                        oder den Vertragsbeginn & die Laufzeit eingeben<br>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Kundennummer:</span>
                        <input class="contact" type="text" name="kundennr"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                              ? request.getParameter("kundennr") : vertrag.getKundenNr() %>" >
                        </p>
                        <p>
                        <span class="span_reg">Vertragsbezeichnung:</span>
                        <input class="contact" type="text" name="vertragsbez"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                              ? request.getParameter("vertragsbez") : vertrag.getVertragsBezeichnung() %>" >
                        </p>
                        <p>
                        <span class="span_reg">Vertragspartner:</span>
                        <input class="contact" type="text" name="vertragspartner"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                              ? request.getParameter("vertragspartner") : vertrag.getVertragsPartner() %>" >
                        </p>
                        <p>
                        <span class="span_reg">Benachrichtigungsfrist:</span>
                        <input class="contact" type="text" name="benachrichtigungsfrist"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                              ? request.getParameter("benachrichtigungsfrist") : vertrag.getBenachrichtigungsfrist() %>">
                        <select name="benachrichtigungsfristeinheit">
                            <% for (Zeit_Einheit einheit : einheiten) { 
                                if (einheit.getName().equals(vertrag.getLaufzeitEinheit().getName())) { %>
                                    <option selected><%= einheit.getName() %></option>
                                <%} else { %>
                                    <option><%= einheit.getName() %></option>
                                <%}%>
                            <% } %>    
                        </select>
                        </p>
                        <% 
                        if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_STROM) {%>                        
                        <p>
                        <span class="span_reg">Stromzählernr:</span>
                        <input class="contact" type="text" name="snr" 
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("snr") : ((Stromvertrag)vertrag).getStromzaehlerNr() %>">
                        </p>
                        <p>
                        <span class="span_reg">Stromzählerstand:</span>
                        <input class="contact" type="text" name="sstand"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("sstand") : ((Stromvertrag)vertrag).getStromzaehlerStand() %>">
                        </p>
                        <p>
                        <span class="span_reg">Verbrauch pro Jahr(in kWh):</span>
                        <input class="contact" type="text" name="sverbrauch"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("sverbrauch") : ((Stromvertrag)vertrag).getVerbrauchProJahr() %>">
                        </p>
                        <p>
                        <span class="span_reg">Preis pro kWh:</span>
                        <input class="contact" type="text" name="spreisKwh"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("spreisKwh") : ((Stromvertrag)vertrag).getPreisProKwh() %>">
                        </p>
                        <p>
                        <span class="span_reg">Anzahl Personen im Haushalt:</span>
                        <input class="contact" type="text" name="sanzPers"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("sanzPers") : ((Stromvertrag)vertrag).getAnzPersonenHaushalt() %>">
                        </p>
                        <p>
                        <span class="span_reg">Grundpreis(pro Monat):</span>
                        <input class="contact" type="text" name="gPreisMonat"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("sPreisMonat") : ((Stromvertrag)vertrag).getGrundpreisMonat() %>">
                        </p>
                        
                        <% } else if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_GAS) { %>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Gaszählernr:</span>
                        <input class="contact" type="text" name="gnr"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("gnr") : ((Gasvertrag)vertrag).getGaszaehlerNr() %>">
                        </p>
                        <p>
                        <span class="span_reg">Gaszählerstand:</span>
                        <input class="contact" type="text" name="gstand"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("gnr") : ((Gasvertrag)vertrag).getGaszaehlerStand() %>">
                        </p>
                        <p>
                        <span class="span_reg">Verbrauch pro Jahr(in kWh):</span>
                        <input class="contact" type="text" name="gverbrauch"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("gverbrauch") : ((Gasvertrag)vertrag).getVerbrauchProJahr() %>">
                        </p>
                        <p>
                        <span class="span_reg">Preis pro kWh:</span>
                        <input class="contact" type="text" name="gpreisKwh"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("gpreisKwh") : ((Gasvertrag)vertrag).getPreisProKhw() %>">
                        </p>
                        <p>
                        <span class="span_reg">Verbrauchsfläche:</span>
                        <input class="contact" type="text" name="gflaeche"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("gflaeche") : ((Gasvertrag)vertrag).getVerbrauchsFlaeche() %>">
                        </p>
                        <% } else if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_FESTNETZ) { %>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Tarifname:</span>
                        <input class="contact" type="text" name="ftarifname"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("ftarifname") : ((Festnetzvertrag)vertrag).getTarifname() %>">
                        </p>
                        <p>
                        <span class="span_reg">Empfangstyp:</span>
                        <select name="fempfangstyp">
                            <% List<Netztyp> typen = new DatenZugriffsObjekt().getNetztypen(false,true);
                            for (Netztyp typ : typen) { 
                                if (typ.getName().equals(((Festnetzvertrag)vertrag).getNetztypp().getName())) {%>
                                    <option selected><%= typ.getName() %> </option>  
                            <%  } else { %>
                                    <option><%= typ.getName() %> </option>  
                            <%    } %>
                            
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
                        <% } else if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_HANDY) { %>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Tarifname:</span>
                        <input class="contact" type="text" name="htarifname"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("htarifname") : ((Handyvertrag)vertrag).getTarifname() %>">
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
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("hrufnummer") : ((Handyvertrag)vertrag).getRufnummer() %>">
                        </p>
                        <% } else if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_ZEITSCHRIFT) { %>
                        <br>Optionale Daten
                        <p>
                        <span class="span_reg">Zeitschriftname:</span>
                        <input class="contact" type="text" name="zname"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("zname") : "" %>">
                        </p>
                        <p>
                        <span class="span_reg">Lieferintervall:</span>
                        <input class="contact" type="text" name="zintervall"
                        value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null 
                                ? request.getParameter("zintervall") : "" %>">
                        <select name="zeinheit">
                            <% for (Zeit_Einheit einheit : einheiten) { 
                                if (request.getParameter("") == einheit.getName() ) { %>
                                    <option selected><%= einheit.getName() %></option>
                               <% } else {%> 
                                    <option><%= einheit.getName() %></option>
                               <%}%>        
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
                        <input class="submit" type="submit" name="contract_change" value="Vertrag ändern">        
                    </form>
                </div> 

            </div>
        </div>

    </body>
</html>
