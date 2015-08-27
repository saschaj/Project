/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entitys.Benutzer;
import Entitys.Kunde;
import Hilfsklassen.ZufallsStringErzeuger;
import Manager.DatenZugriffsObjekt;
import Manager.EmailHandler;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mladko
 */
public class ConfirmationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ref = request.getParameter("ref");
        String action = request.getParameter("action");
        String user = request.getParameter("user");
        if (user != null && !user.equals("") && action != null
                && !action.equals("") && ref != null && !ref.equals("")) {
            DatenZugriffsObjekt dao = new DatenZugriffsObjekt();
            Benutzer b = dao.getBenutzer(user);
            if (b != null) {
                if (action.equals("password") && b.getPasswortZuruecksetzen().equals(ref)) {
                    sendeNeuesPasswort(b);
                } else if (action.equals("register") && b.getEmailBestaetigung().equals(ref)) {
                    Kunde k = dao.getKunde(b.getBenutzerId());
                    sendeRegistrierungsMail(k);
                } else {

                }
                request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
            }
        }
    }

    private void sendeNeuesPasswort(Benutzer b) {
        ZufallsStringErzeuger z = new ZufallsStringErzeuger();
        b.setPasswort(z.holeNeuesPasswort());
        EmailHandler e = new EmailHandler();
        e.sendPasswortMail(b);
    }

    private void sendeRegistrierungsMail(Kunde k) {
       EmailHandler e = new EmailHandler();
       e.sendRegisterMail("Registrierung für " + k.getVorname() + " "
                            + k.getNachname() + " erfolgt", k.getEmail(), k.getPasswort());
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
        return "Short description";
    }// </editor-fold>

}
