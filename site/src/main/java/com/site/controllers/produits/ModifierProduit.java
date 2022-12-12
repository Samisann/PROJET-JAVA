package com.site.controllers.produits;

import java.io.IOException;
import com.site.entities.Produit;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

/**
 * Servlet implementation class ModifierProduit
 */
@WebServlet("/modifierProduit")
public class ModifierProduit extends HttpServlet {
   private static final long serialVersionUID = 1L;
   @PersistenceContext(unitName = "projet-java")
   private EntityManager em;
   @Resource
   private UserTransaction userTransaction;

   /**
    * Default constructor.
    */
   public ModifierProduit() {
      // TODO Auto-generated constructor stub
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      Long codeProduit = Long.valueOf(request.getParameter("codeProduit"));
      Produit produit = em.find(Produit.class, codeProduit);
      request.setAttribute("produit", produit);
      String errorMessage = request.getParameter("errorMessage");
      request.setAttribute("errorMessage", errorMessage);
      this.getServletContext().getRequestDispatcher("/modifier-produit.jsp").forward(request,
            response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      // TODO Auto-generated method stub
      String libelle = request.getParameter("libelle");
      String message = "";
      Long codeProduit = Long.valueOf(request.getParameter("codeProduit"));
      String uriRedirect = "modifierProduit?codeProduit=" + codeProduit + "&errorMessage=%s";
      if (libelle == null || libelle.isEmpty() || libelle.isBlank()) {
         message = "Veuillez renseignez le libellé du produit";
         redirectWithErrorMessage(response, uriRedirect, message);

      } else {
         Produit produit = em.find(Produit.class, codeProduit);
         if (produit == null) {
            message = "Aucun produit correspondant à ce code";
            redirectWithErrorMessage(response, uriRedirect, message);
         } else {
            boolean transactionOk = false;
            try {
               userTransaction.begin();
               produit.setLibelle(libelle);
               em.merge(produit);
               transactionOk = true;
            } catch (Exception e) {
               System.out.print("Une erreur est survenue lors de l'enregistrement de la modification");
            } finally {
               try {
                  if (transactionOk) {
                     userTransaction.commit();
                     response.sendRedirect("");
                  } else {
                     message =
                           "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
                     userTransaction.rollback();
                     redirectWithErrorMessage(response, uriRedirect, message);

                  }
               } catch (Exception e) {
                  message =
                        "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
                  redirectWithErrorMessage(response, uriRedirect, message);
               }
            }
         }

      }
   }

   private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
         String message) throws IOException {
      String urlRediret = String.format(uriRedirect, message);
      response.sendRedirect(urlRediret);
   }

}