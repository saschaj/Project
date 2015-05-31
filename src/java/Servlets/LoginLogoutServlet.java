/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sascha
 */
public class LoginLogoutServlet extends HttpServlet {

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
        
        HttpSession session = request.getSession();
        String ausgabe;
        // Initialisierung der verschiedenen Verlinkungen
        String login = request.getParameter("login");
        String register = request.getParameter("register");
        String getPassword = request.getParameter("get_pw");
        String logout = request.getParameter("logout");
        
        // ÜBerprüfung, welcher Button gedrückt wurde
        if (login != null) {
            ausgabe = "Sie wurden erfolgreich angemeldet und werden automatisch weitergeleitet!";
        } else if (register != null) {
            ausgabe = "Sie wurden erfolgreich registriert und werden automatisch weitergeleitet!";
        } else if (getPassword != null) {
            ausgabe = "Ihnen wurde ein neues Passwort zugeschickt und werden automatisch weitergeleitet!";
        } else if (logout != null) {
            ausgabe = "Sie wurden erfolgreich ausgeloggt und werden automatisch auf die Startseite weitergeleitet!";
        } else {
            ausgabe = "Irgendwas wurde nicht richtig programmiert!";
        }
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");  
            out.println("<meta http-equiv='refresh' content='5; URL=index.jsp'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>"+ ausgabe +"</h1>");
            out.println("</body>");
            out.println("</html>");
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
        return "Short description";
    }// </editor-fold>

}
