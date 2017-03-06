/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import hibernate.NewHibernateUtil;
import hibernate.RegistrationTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author SGR
 */
public class loginServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           String user = request.getParameter("username");
            String pass = request.getParameter("password");
            
            Session se=NewHibernateUtil.getSessionFactory().openSession();
            ArrayList<RegistrationTable> ls=new ArrayList();
            HttpSession ssn=request.getSession();
            Transaction tx=se.beginTransaction();
            ls=(ArrayList<RegistrationTable>) se.createQuery("from RegistrationTable where username='"+user+"' and password='"+pass+"'").list();
            tx.commit();
            se.close();
            out.println(ls.size());
            if(ls.size()>0) {
                ssn.setAttribute("data", ls);
                if(ls.get(0).getUserType().equals("Admin")){
                    
                    response.sendRedirect("admin.htm");
                
                }else{
                    response.sendRedirect("./Home.jsp");
                }
                
            }else{
               // if(request.getParameter("name").equals("null")){
              //  ssn= request.getSession();
           // ssn.setAttribute("msg", "");
                ssn.setAttribute("error","Sorry, Wrong Username or Password");
                response.sendRedirect("./redirect.jsp");
            }
          //  }
            
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
