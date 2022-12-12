
package com.site.controllers.categorie;

import java.io.IOException;
import java.util.List;
import com.site.entities.Category;
import com.site.entities.Produit;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

/**
 * Servlet implementation class CreerProduit
 */
//@WebServlet("/creerCategorie")
public class CreerCategorie extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "projet-java")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   /**
    * Default constructor.
    */
   public CreerCategorie() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      TypedQuery<Produit> query = em.createQuery("SELECT s FROM Produit s", Produit.class);
      List<Produit> Produits = query.getResultList();
      request.setAttribute("Produits", Produits);
      this.getServletContext().getRequestDispatcher("/form-categorie.jsp").forward(request,
            response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      String message = "Veuillez selectionner un Produit";
      String libelle = request.getParameter("libelle");
      if (libelle == null || libelle.isEmpty() || libelle.isBlank()) {
         message = "Veuillez renseignez le libellé de la catégorie";
         request.setAttribute("errorMessage", message);
         redirectWithErrorMessage(response, request);
      } else {
         Produit Produit = this.findProduit(request.getParameter("Produit"));
         if (Produit == null) {
            message = "Aucun enregistrement de Produit trouvé";
            request.setAttribute("errorMessage", message);
            redirectWithErrorMessage(response, request);
         } else {
            saveCategory(response, request, libelle, Produit);
         }

      }


   }

   private void saveCategory(HttpServletResponse response, HttpServletRequest request,
         String libelle, Produit produit) throws IOException {
      String message;
      boolean transactionOk = false;
      try {
         userTransaction.begin();
         Category categorie = new Category();
         categorie.setLibelle(libelle);
         categorie.setProduit(em.merge(produit));
         em.persist(categorie);
         transactionOk = true;
      } catch (Exception e) {
         System.out.print("Une erreur est survennue lors de l'enregistrement");
      } finally {
         try {
            if (transactionOk) {
               userTransaction.commit();
               response.sendRedirect("categorie");
            } else {
               message =
                     "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
               userTransaction.rollback();
               request.setAttribute("errorMessage", message);
               redirectWithErrorMessage(response, request);
            }
         } catch (Exception e) {
            message =
                  "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
            request.setAttribute("errorMessage", message);
            redirectWithErrorMessage(response, request);
         }
      }
   }

   private Produit findProduit(String codeProduit) throws IOException {
      Produit Produit = null;
      try {
         Produit = em.find(Produit.class, Long.valueOf(codeProduit));
      } catch (EntityNotFoundException e) {
         System.out.print("Aucune entité trouvé");
      } catch (Exception ex) {
         System.out.print(
               "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur ");
      }
      return Produit;
   }

   private void redirectWithErrorMessage(HttpServletResponse response, HttpServletRequest request)
         throws IOException {
      try {
         this.getServletContext().getRequestDispatcher("/form-categorie.jsp").forward(request,
               response);
      } catch (ServletException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

}
