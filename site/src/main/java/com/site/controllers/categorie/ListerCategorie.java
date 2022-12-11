package com.site.controllers.categorie;

import java.io.IOException;
import java.util.List;
import com.site.entities.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListerSportServlet
 */

public class ListerCategorie extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "projet-java")
   private EntityManager em;

   /**
    * Default constructor.
    */
   public ListerCategorie() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      TypedQuery<Category> query = em.createQuery("SELECT s FROM Produit s", Category.class);
      List<Category> categories = query.getResultList();
      request.setAttribute("categories", categories);
      this.getServletContext().getRequestDispatcher("/categorie.jsp").forward(request, response);

   }

}