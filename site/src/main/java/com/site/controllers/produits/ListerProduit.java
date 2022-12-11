package com.site.controllers.produits;

import java.io.IOException;
import java.util.List;
import com.site.entities.Produit;

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

public class ListerProduit extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "projet-java")
   private EntityManager em;

   /**
    * Default constructor.
    */
   public ListerProduit() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      TypedQuery<Produit> query = em.createQuery("SELECT s FROM Produit s", Produit.class);
      List<Produit> produits = query.getResultList();
      request.setAttribute("produits", produits);
      this.getServletContext().getRequestDispatcher("/produit.jsp").forward(request, response);

   }

}