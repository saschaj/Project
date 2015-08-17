<%--

Ersteller:	Sascha Jungenkrüger
Erstelldatum:   03.06.2015
Dokument:	contract_add.jsp
Version:	1.0
Veränderungen:	-

--%>
<%@page import="Entitys.Zeitschriftvertrag"%>
<%@page import="java.text.SimpleDateFormat"%>
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
    String isISDN = "";
    String isVOIP = "";
    List<Interessengebiet> gebiete = null;
    List<Netztyp> typen = null;
    String test = request.getParameter("vertrag");
    Vertrag vertrag = 
            new DatenZugriffsObjekt().getVertrag(Integer.parseInt(test));
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    // Liest alles Zeiteinheiten aus der DB und gibt
    // diese in einer Auswahlbox aus
    List<Zeit_Einheit> einheiten = new DatenZugriffsObjekt().getEinheiten();%>

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
            $(function () {
                $("#datepicker").datepicker({
                    dateFormat: 'dd.mm.yy'
                });
            });
            $(function () {
                $("#datepicker2").datepicker({
                    dateFormat: 'dd.mm.yy'
                });
            });
        </script>
    </head>
    <body>

        <div id="main" style="width:800px">
            <div id="site_content">    

                <% if (request.getAttribute(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null) {
                        fehler = (String[]) request.getAttribute(Konstanten.REQUEST_ATTR_FEHLER_CHANGE);
                        for (int i = 0; i < fehler.length; i++) {%>
                <span class="span_error_contract"><%= fehler[i]%></span><br>
                <% }
                         }%>

                <div id="content">
                    <form method="POST" action="VertragServlet"> 
                        <!-- Pflichtdaten eines Vertrags -->
                        <u>Obligatorische Vertragsdaten</u>
                        <p>
                            <span class="span_reg">Vertragsbezeichnung:</span>
                            <input class="contact" type="text" name="vertragsbez"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("vertragsbez") : vertrag.getVertragsBezeichnung()%>" >
                        </p>
                        <p>
                            <span class="span_reg">Vertragsnummer:</span>
                            <input class="contact" type="text" name="vertragsNr"
                                   pattern="[0-9]{5,10}" title="Die Nummer darf minimal 5 und maximal 10 Ziffern beinhalten."
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("vertragsNr") : vertrag.getVertragNr()%>">
                        </p>
                        <p>
                            <span class="span_reg">Vertragsbeginn*:</span>
                            <input id="datepicker" class="contact" 
                                   type="text" name="vertragsBeginn"
                                   pattern="[0-3][0-9].[0-1][0-9].[1-2][0-9]{3}" title="Datumsformat lautet: DD.MM.YYYY"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("vertragsBeginn") : dateFormatter.format(vertrag.getVertragBeginn())%>" readonly>
                        </p>
                        <p>
                            <span class="span_reg">Laufzeit*:</span>
                            <input class="contact" type="text" name="laufzeit"
                                   pattern="[1-9]{1}[0-9]*" title="Die Laufzeit darf nur Ziffern enthalten und nicht mit einer 0 beginnen!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("laufzeit") : vertrag.getLaufzeit()%>">
                            <select name="laufzeiteinheit">
                                <%
                                for (Zeit_Einheit einheit : einheiten) {
                                    if (einheit.getName().equals(vertrag.getLaufzeitEinheit().getName())) {%>
                                <option selected><%= einheit.getName()%></option>
                                <%} else {%>
                                <option><%= einheit.getName()%></option>
                                <%}%>                            
                                <% }%>                            
                            </select>
                        </p>
                        <p>
                            <span class="span_reg">Vertragsende*:</span>
                            <input id="datepicker2" class="contact" 
                                   type="text" name="vertragsEnde"
                                   pattern="[0-3][0-9].[0-1][0-9].[1-2][0-9]{3}" title="Datumsformat lautet: DD.MM.YYYY"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("vertragsEnde") : dateFormatter.format(vertrag.getVertragEnde())%>" readonly>
                        </p>
                        * Hier können Sie entweder nur das Vertragsende 
                        oder den Vertragsbeginn & die Laufzeit eingeben<br>
                        <p>
                            <span class="span_reg">Kündigungsfrist:</span>
                            <input class="contact" type="text" name="kuendigungsfrist"
                                   pattern="[1-9]{1}[0-9]*" title="Die Kündigungsfrist darf nur Ziffern enthalten und nicht mit einer 0 beginnen!"  
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("kuendigungsfrist") : vertrag.getKuendigungsfrist()%>">
                            <select name="kuendigungsfristeinheit">
                                <% for (Zeit_Einheit einheit : einheiten) {
                                    if (einheit.getName().equals(vertrag.getKuendigungsfristEinheit().getName())) {%>
                                <option selected><%= einheit.getName()%></option>
                                <%} else {%>
                                <option><%= einheit.getName()%></option>
                                <%}%>
                                <% }%>    
                            </select>
                        </p>
                        <br><u>Optionale Vertragsdaten</u>
                        <p>
                            <span class="span_reg">Kundennummer:</span>
                            <input class="contact" type="text" name="kundennr"
                                   pattern="[0-9]{1,}" title="Das Feld darf nur Ziffern enthalten!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("kundennr") : vertrag.getKundenNr()%>" >
                        </p>
                        <p>
                            <span class="span_reg">Vertragspartner:</span>
                            <input class="contact" type="text" name="vertragspartner"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("vertragspartner") : vertrag.getVertragsPartner()%>" >
                        </p>
                        <p>
                            <span class="span_reg">Benachrichtigungsfrist:</span>
                            <input class="contact" type="text" name="benachrichtigungsfrist"
                                   pattern="[1-9]{1}[0-9]*" title="Das Feld darf nur Ziffern enthalten!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("benachrichtigungsfrist") : vertrag.getBenachrichtigungsfrist()%>">
                            <select name="benachrichtigungsfristeinheit">
                                <% for (Zeit_Einheit einheit : einheiten) {
                                    if (einheit.getName().equals(vertrag.getBenachrichtigungsfristEinheit().getName())) {%>
                                <option selected><%= einheit.getName()%></option>
                                <%} else {%>
                                <option><%= einheit.getName()%></option>
                                <%}%>
                                <% } %>    
                            </select>
                        </p>
                        <br><u>Vertragsspezifische Daten</u>
                            <%
                            if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_STROM) {%>                        
                        <p>
                            <span class="span_reg">Stromzählernr:</span>
                            <input class="contact" type="text" name="snr"
                                   pattern="[1-9]{1}[0-9]*" title="Das Feld darf nur Ziffern enthalten!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("snr") : ((Stromvertrag) vertrag).getStromzaehlerNr()%>">
                        </p>
                        <p>
                            <span class="span_reg">Stromzählerstand:</span>
                            <input class="contact" type="text" name="sstand"
                                   pattern="[1-9]{1}[0-9]*" title="Das Feld darf nur Ziffern enthalten!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("sstand") : ((Stromvertrag) vertrag).getStromzaehlerStand()%>">
                        </p>
                        <p>
                            <span class="span_reg">Verbrauch pro Jahr(in kWh):</span>
                            <input class="contact" type="text" name="sverbrauch"
                                   pattern="[1-9]{1}[0-9]*" title="Die Verbrauch muss größer als 0 sein und darf nur Ziffern enthalten!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("sverbrauch") : ((Stromvertrag) vertrag).getVerbrauchProJahr()%>">
                        </p>
                        <p>
                            <span class="span_reg">Preis pro kWh (in Cent):</span>
                            <input class="contact" type="text" name="spreisKwh"
                                   pattern="[\\d]+[,][\\d]+" title="Es sind nur Fließkommazahlen erlaubt!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("spreisKwh") : ((Stromvertrag) vertrag).getPreisProKwh()%>">
                        </p>
                        <p>
                            <span class="span_reg">Anzahl Personen im Haushalt:</span>
                            <input class="contact" type="text" name="sanzPers"
                                   pattern="[1-9][0-9]*" title="Nur Ziffern erlaubt!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("sanzPers") : ((Stromvertrag) vertrag).getAnzPersonenHaushalt()%>">
                        </p>
                        <p>
                            <span class="span_reg">Grundpreis(pro Monat in €):</span>
                            <input class="contact" type="text" name="gPreisMonat"
                                   pattern="[\\d]+[,][\\d]+" title="Es sind nur Fließkommazahlen erlaubt!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("sPreisMonat") : ((Stromvertrag) vertrag).getGrundpreisMonat()%>">
                        </p>

                        <% } else if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_GAS) {%>
                        <br>Optionale Daten
                        <p>
                            <span class="span_reg">Gaszählernr:</span>
                            <input class="contact" type="text" name="gnr"
                                   pattern="[1-9][0-9]*" title="Es sind nur Ziffern erlaubt!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("gnr") : ((Gasvertrag) vertrag).getGaszaehlerNr()%>">
                        </p>
                        <p>
                            <span class="span_reg">Gaszählerstand:</span>
                            <input class="contact" type="text" name="gstand"
                                   pattern="[1-9][0-9]*" title="Es sind nur Ziffern erlaubt!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("gnr") : ((Gasvertrag) vertrag).getGaszaehlerStand()%>">
                        </p>
                        <p>
                            <span class="span_reg">Verbrauch pro Jahr(in kWh):</span>
                            <input class="contact" type="text" name="gverbrauch"
                                   pattern="[1-9][0-9]*" title="Es sind nur Ziffern erlaubt!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("gverbrauch") : ((Gasvertrag) vertrag).getVerbrauchProJahr()%>">
                        </p>
                        <p>
                            <span class="span_reg">Preis pro kWh:</span>
                            <input class="contact" type="text" name="gpreisKwh"
                                   pattern="[1-9][0-9]+[,][0-9]+" title="Es sind nur Fließkommazahlen erlaubt!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("gpreisKwh") : ((Gasvertrag) vertrag).getPreisProKhw()%>">
                        </p>
                        <p>
                            <span class="span_reg">Verbrauchsfläche (in m²):</span>
                            <input class="contact" type="text" name="gflaeche"
                                   pattern="[1-9][0-9]{0,}" title="Es sind nur Ziffern erlaubt."
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("gflaeche") : ((Gasvertrag) vertrag).getVerbrauchsFlaeche()%>">
                        </p>
                        <% } else if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_FESTNETZ) {%>
                        <br>Optionale Daten
                        <p>
                            <span class="span_reg">Tarifname:</span>
                            <input class="contact" type="text" name="ftarifname"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("ftarifname") : ((Festnetzvertrag) vertrag).getTarifname()%>">
                        </p>
                        <p>
                            <span class="span_reg">Empfangstyp:</span>
                            <select name="fempfangstyp">
                                <% typen = new DatenZugriffsObjekt().getNetztypen(false, true);
                                for (Netztyp typ : typen) {
                                    if (typ.getName().equals(((Festnetzvertrag) vertrag).getNetztypp().getName())) {%>
                                <option selected><%= typ.getName()%> </option>  
                                <%  } else {%>
                                <option><%= typ.getName()%> </option>  
                                <%    } %>

                                <% } %> 
                            </select>
                        </p>         
                        <p>
                            <span class="span_reg">ISDN:</span>
                            <% if (((Festnetzvertrag) vertrag).isIstISDN()) { %>
                            <input type="checkbox" name="fistISDN" checked>
                            <% } else { %>
                            <input type="checkbox" name="fistISDN"> 
                            <%}%>       
                        </p>
                        <p>
                            <span class="span_reg">VOIP:</span>
                            <% if (((Festnetzvertrag) vertrag).isIstVOIP()) { %>
                            <input type="checkbox" name="fistVOIP" checked>
                            <% } else { %>
                            <input type="checkbox" name="fistVOIP"> 
                            <%}%> 
                        </p>
                        <% } else if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_HANDY) {%>
                        <br>Optionale Daten
                        <p>
                            <span class="span_reg">Tarifname:</span>
                            <input class="contact" type="text" name="htarifname"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("htarifname") : ((Handyvertrag) vertrag).getTarifname()%>">
                        </p>
                        <p>
                            <span class="span_reg">Netztyp:</span>
                            <select name="hnetztyp">
                                <% typen = new DatenZugriffsObjekt().getNetztypen(true, false);
                                for (Netztyp typ : typen) {%>
                                <option><%= typ.getName()%></option>
                                <% }%> 
                            </select>
                        </p>
                        <p>
                            <span class="span_reg">Rufnummer:</span>
                            <input class="contact" type="text" name="hrufnummer"
                                   pattern="[01][0-9]*" title="Es sind nur Ziffern die mit 01 anfangen erlaubt!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("hrufnummer") : ((Handyvertrag) vertrag).getRufnummer()%>">
                        </p>
                        <% } else if (vertrag.getVertragArt().getVertragArtId() == Konstanten.ID_VERTRAG_ART_ZEITSCHRIFT) {%>
                        <br>Optionale Daten
                        <p>
                            <span class="span_reg">Zeitschriftname:</span>
                            <input class="contact" type="text" name="zname"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("zname") : ((Zeitschriftvertrag) vertrag).getZeitschriftName()%>">
                        </p>
                        <p>
                            <span class="span_reg">Lieferintervall:</span>
                            <input class="contact" type="text" name="zintervall"
                                   pattern="[1-9][0-9]*" title="Es sind nur Ziffern erlaubt!"
                                   value="<%= request.getParameter(Konstanten.REQUEST_ATTR_FEHLER_CHANGE) != null
                                ? request.getParameter("zintervall") : ((Zeitschriftvertrag) vertrag).getLieferintervall()%>">
                            <select name="zeinheit">
                                <% for (Zeit_Einheit einheit : einheiten) {
                                    if (((Zeitschriftvertrag) vertrag).getLieferintervallEinheit().getName() == einheit.getName()) {%>
                                <option selected><%= einheit.getName()%></option>
                                <% } else {%> 
                                <option><%= einheit.getName()%></option>
                                <%}%>        
                                <% } %> 
                            </select>
                        </p>
                        <p>
                            <span class="span_reg">Interessengebiet:</span>
                            <select name="zinteressen">
                                <% gebiete = new DatenZugriffsObjekt().getInteressengebiete();
                                for (Interessengebiet gebiet : gebiete) { 
                                    if (((Zeitschriftvertrag)vertrag).
                                            getInteressengebiet().getName()
                                            .equals(gebiet.getName())) { %>
                                        <option selected><%= gebiet.getName() %></option>
                                <%  } else { %>
                                        <option><%= gebiet.getName() %></option>
                                <%  }
                                } %>  
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
