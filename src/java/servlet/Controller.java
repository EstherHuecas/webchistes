/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import entities.Categoria;
import entities.Chiste;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.JPAUtil;

/**
 *
 * @author Esther
 */
@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

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
        // Variables.
        HttpSession session = request.getSession(); //Session es que no voy a borrar nada
        String op = request.getParameter("op"); //Reque3st que puedo llegar a borrar
        RequestDispatcher dispatcher; //Siempre se pone
        String sql = null; //Donde voy a hacer las sql
        Query query = null; //Donde se va a guardar las querys del sql
        
        List<Categoria> listaCategorias;
        List<Chiste> listaChistesCategoria;
        String idCategoria;
        
         EntityTransaction entityTransaction; //Siempre se pone
        // Crear EntityManager.
        EntityManager em = (EntityManager) session.getAttribute("em"); //Quita de hacer daos (es el dao en si)
        
        //Siempre se pone para hacer el EntityManager
        if(em==null){
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            session.setAttribute("em", em);
        }
        
        //Cualquier cosa que me venga de la JSP al controller es con
        //request.getParameter("nombre");
        
        switch(op){
            //Llama a la página
            case "inicio":
                sql = "SELECT c FROM Categoria c";
                query = em.createQuery(sql);
                listaCategorias = query.getResultList();
                
                //Lo guardo en Session porque me viene bien tener siempre las categorias cargadas
                request.getSession().setAttribute("listaCategorias", listaCategorias);
                request.getSession().setAttribute("bandera",false);
                
                dispatcher = request.getRequestDispatcher("home.jsp");
                dispatcher.forward(request, response);
  
            break;
                
            case "categoriaPulsada": 
                //Recojo lo que había en el combo, es decir, el id de la categoria que ha pulsado el usuario 
                //Short idCategoria = Short.parseShort(request.getParameter("idCategoria"));
                idCategoria = request.getParameter("selectCategoria");
                //Hago la query que va a decir que el id de los chistes sea igual al id de la categoria que le pasamos
                //sql = "select ch from Chiste ch where ch.idcategoria = (select c.id FROM Categoria c where c.id = :idCategoria)";
                sql = "select ch from Chiste ch where ch.idcategoria = (select c.id FROM Categoria c where c.id = "+idCategoria+")";
                query = em.createQuery(sql);
                //Creamos una lista y guardamos la respuesta de la query ahí
                listaChistesCategoria = query.getResultList();
                request.setAttribute("listaChistes", listaChistesCategoria); //"listaChistesCategoria" como lo llamo en JSP y sin "" es el objeto 
                
                request.getSession().setAttribute("idCategoria", idCategoria); //Guardo el id de la categoria pulsada en el Session
                
                request.setAttribute("bandera", false); 
                
                //Volvemos a redireccionarlo a la home
                dispatcher = request.getRequestDispatcher("home.jsp");
                dispatcher.forward(request, response);
                
            break;
            
            case "mejoresChistes":
                boolean bandera = Boolean.parseBoolean(request.getParameter("mejores")); //Nombre de la bandera del home
                //Si la bandera está subida
                if (bandera == false){
                    //Ordename los chistes de la media de sus puntos de manera de mayor a menor
                    sql = "select p.idchiste from Puntos p group by p.idchiste order by avg(p.puntos) DESC"; 
                    query = em.createQuery(sql);
                    //Me creo una lista para guardar el resultado de los mejores chistes
                    listaChistesCategoria = query.getResultList();                    
                    request.setAttribute("listaChistes",listaChistesCategoria);
                    request.getSession().setAttribute("bandera", !bandera);
                    
                } else{ 
                    //Cuando la bandera esté subida (osea yo en mejores chistes) me tiene que aparecer un enlace por categorias, 
                    //que va a hacer lo mismo que en el inicio, me va a cargar las categorias
                    idCategoria = request.getParameter("idCategoria");
                    sql = "select ch from Chiste ch where ch.idcategoria = (select c.id FROM Categoria c where c.id = "+idCategoria+")";
                    query = em.createQuery(sql);
                    
                    listaChistesCategoria = query.getResultList();
                    request.setAttribute("listaChistes", listaChistesCategoria);
                    request.getSession().setAttribute("bandera", !bandera);
                }
                   //Volvemos a redireccionarlo a la home
                dispatcher = request.getRequestDispatcher("home.jsp");
                dispatcher.forward(request, response);
            break;
            
            case "insertarCategorias":
                String categoria = request.getParameter("Categoria");
                Categoria nuevaCategoria = new Categoria();
                nuevaCategoria.setNombre(categoria);
                
                em.getTransaction().begin();
                em.persist(nuevaCategoria);
                em.getTransaction().commit();
                
                //Volvemos a redireccionarlo a la home
                dispatcher = request.getRequestDispatcher("home.jsp");
                dispatcher.forward(request, response);
            break;
            
            case "insertarChistes":
                String apodo = request.getParameter("Apodo");
                String chiste = request.getParameter("Chistes"); //Descripcion
                String categoriaCh = request.getParameter("Categoria");
                String titulo = request.getParameter("Titulo");
                
                //Para poder sacar el id de la categoria del chiste
                Categoria categoriaTotal = em.find(Categoria.class, Short.parseShort(categoriaCh));
                
                Chiste nuevoChiste = new Chiste();
                
                nuevoChiste.setAdopo(apodo);
                nuevoChiste.setIdcategoria(categoriaTotal);
                nuevoChiste.setDescripcion(chiste);
                nuevoChiste.setTitulo(titulo);
                
                em.getTransaction().begin();
                em.persist(nuevoChiste);
                em.getTransaction().commit();
                
                //Volvemos a redireccionarlo a la home
                dispatcher = request.getRequestDispatcher("home.jsp");
                dispatcher.forward(request, response);
            break;
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
