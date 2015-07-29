/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Manager.DatenZugriffsObjekt;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;

/**
 *
 * @author René
 */
public class AdminServlet extends HttpServlet {

    /**
     * Ersteller:   René Kanzenbach
     * Datum:       28.07.2015
     * Version:     1.0
     * Änderungen:  -
     * 
     * Prüft, welche Aktion in der admin.jsp aufgerufen wurde und führt dann die
     * entsprechende Methode auf.
     * 
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Pruefen, welche Aktion ausgeführt wurde.
        if(request.getParameter("BenutzerStatistik") != null) {
            //Der Button "Benutzerübersicht" wurde in der admin.jsp betaetigt.
            this.zeigeBenutzerStatistik(request, response);
        }else if(request.getParameter("VertragStatistik") != null){
            //Der Button "Vertragsübersicht" wurde in der admin.jsp betaetigt.
            this.zeigeVertragsStatistik(request, response);
        }else if(request.getParameter("search_text") != null){
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet, welches sich um saemtliche Eingaben aus der admin.jsp"
                + " kuemmert.";
    }// </editor-fold>

    
    /**
     * Ersteller:   René Kanzenbach
     * Datum:       28.07.2015
     * Version:     1.0
     * Änderungen:  -
     * 
     * Diese Methode erzeugt ein Diagramm-PNG, welches in der 
     * admin_dynamic.jsp ausgegeben wird.
     * 
     * Dazu wird die Methode getBenutzerStatistik():JFreeChart ,des 
     * DatenZugriffsObjekt, aufgerufen. Das JFreeChart-Diagramm wird dann in 
     * dieser Methode in ein PNG umgewandelt und der admin_dynamic.jsp 
     * übergeben.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void zeigeBenutzerStatistik(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
        JFreeChart benutzerStatistik = dao.getBenutzerStatistik();
        String dateiName;
        String url;
        
        /* Diagramm in 500x500Pixel großes Bild umwandeln und den Namen des 
         * Bildes in dateiName speichern.
         */
        dateiName = ServletUtilities.saveChartAsPNG(
                benutzerStatistik, 500, 500, request.getSession());
        
        /*
        Erstellen der URL, die verwendet werden kann um das Diagramm anzuzeigen.
        Dazu wird ein DisplayChart-Servlet verwendet, welches das Diagramm-Bild
        an den aufrufenden Browser weiterleitet.
        */
        url = request.getContextPath() + "/servlet/DisplayChart?filename=" 
                + dateiName;
        
        //URL des Diagrammbildes als Parameter uebergeben.
        request.setAttribute("StatistikURL", url);
        //Aufrufen der admin_dynamic.jsp
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
        dao.close();
    }
    
    /**
     * Ersteller:   René Kanzenbach
     * Datum:       28.07.2015
     * Version:     1.0
     * Änderungen:  -
     * 
     * Diese Methode erzeugt ein Diagramm-PNG, welches in der 
     * admin_dynamic.jsp ausgegeben wird.
     * 
     * Dazu wird die Methode getVertragStatistik():JFreeChart ,des 
     * DatenZugriffsObjekt, aufgerufen. Das JFreeChart-Diagramm wird dann in 
     * dieser Methode in ein PNG umgewandelt und der admin_dynamic.jsp 
     * übergeben.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void zeigeVertragsStatistik(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
        JFreeChart vertragDiagramm = dao.getVertragStatistik();
        String dateiName;
        String url;
        
        /* Diagramm in 500x500Pixel großes Bild umwandeln und den Namen des 
         * Bildes in dateiName speichern.
         */
        dateiName = ServletUtilities.saveChartAsPNG(
                vertragDiagramm, 500, 500, request.getSession());
        
        /*
        Erstellen der URL, die verwendet werden kann um das Diagramm anzuzeigen.
        Dazu wird ein DisplayChart-Servlet verwendet, welches das Diagramm-Bild
        an den aufrufenden Browser weiterleitet.
        */
        url = request.getContextPath() + "/servlet/DisplayChart?filename=" 
                + dateiName;
        
        //URL des Diagrammbildes als Parameter uebergeben.
        request.setAttribute("StatistikURL", url);
        //Aufrufen der admin_dynamic.jsp
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
        dao.close();
    }
}
