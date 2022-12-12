package com.site.controllers.categorie;

import java.io.IOException;

import com.site.entities.Category;
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
@WebServlet("/modifierCategorie")
public class ModifierCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   @PersistenceContext(unitName = "projet-java")
	   private EntityManager em;
	   @Resource
	   private UserTransaction userTransaction;

	   /**
	    * Default constructor.
	    */
    public ModifierCategorie() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         Long codeCategorie = Long.valueOf(request.getParameter("codeCategorie"));
         Category categorie = em.find(Category.class, codeCategorie);
         request.setAttribute("categorie", categorie);
         String errorMessage = request.getParameter("errorMessage");
         request.setAttribute("errorMessage", errorMessage);
         this.getServletContext().getRequestDispatcher("/modifier-categorie.jsp").forward(request,
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
         Long codeCategorie = Long.valueOf(request.getParameter("codeCategorie"));
         String uriRedirect = "modifierCategorie?codeCategorie=" + codeCategorie + "&errorMessage=%s";
         if (libelle == null || libelle.isEmpty() || libelle.isBlank()) {
            message = "Veuillez renseignez le libellé de la categorie";
            redirectWithErrorMessage(response, uriRedirect, message);

         } else {
            Category categorie = em.find(Category.class, codeCategorie);
            if (categorie == null) {
               message = "Aucun produit correspondant à ce code";
               redirectWithErrorMessage(response, uriRedirect, message);
            } else {
               boolean transactionOk = false;
               try {
                  userTransaction.begin();
                  categorie.setLibelle(libelle);
                  em.merge(categorie);
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