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



@WebServlet("/creerproduit")
/**
 * Servlet implementation class CreerProduit
 */
public class CreerProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   @PersistenceContext(unitName = "projet-java")
	   private EntityManager em;
	   @Resource
	   private UserTransaction userTransaction;
    /**
     * Default constructor. 
     */
    public CreerProduit() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect("create-form.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)      throws ServletException, IOException {
	      // TODO Auto-generated method stub
	      String libelle = request.getParameter("libelle");
	      String uriRedirect = """
	            create-form.jsp?errorMessage=%s
	            """;
	      String message = "";
	      if (libelle == null || libelle.isEmpty() || libelle.isBlank()) {
	         message = "Veuillez renseignez le libellÃ© du produit";
	         redirectWithErrorMessage(response, uriRedirect, message);

	      } else {
	         boolean transactionOk = false;
	         try {
	            userTransaction.begin();
	            Produit produit = new Produit();
	            produit.setLibelle(libelle);
	            em.persist(produit);
	            transactionOk = true;
	         } catch (Exception e) {
	            System.out.print("Une erreur est survennue lors de l'enregistrement");
	         } finally {
	            try {
	               if (transactionOk) {
	                  userTransaction.commit();
	                  response.sendRedirect("");
	               } else {
	                  message =
	                        "Une erreur est survenue lors de l'enregistrement veuillez contactez votre administrateur";
	                  userTransaction.rollback();
	                  String.format(uriRedirect, message);
	                  response.sendRedirect(uriRedirect);

	               }
	            } catch (Exception e) {
	               message =
	                     "Une erreur est survenue lors de l'enregistrement veuillez contactez votre administrateur";
	               String.format(uriRedirect, message);
	               response.sendRedirect(uriRedirect);
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
