package com.site.entities;

import java.util.ArrayList;
import com.site.entities.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Produit")
public class Produit { 
	
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "code_produit")
   private Long code;
   
   @Column(name = "libelle")
   private String libelle;
   
  @OneToMany( cascade = CascadeType.ALL,mappedBy = "produit")
   private Set<Category> categories;


   public Long getCode() {
      return code;
   }

   public void setCode(Long code) {
      this.code = code;
   }

   public String getLibelle() {
      return libelle;
   }

   public void setLibelle(String libelle) {
      this.libelle = libelle;
   }

   public void addProduit(Category category) {
      if (this.categories == null) {
         this.categories = new HashSet<>();
      }
      this.categories.add(category);
   }

   public void removeProduit(Category category) {
      if (this.categories != null)
    	  categories.remove(category);
   }

   public Set<Category> getCategories() {
      return categories;
   }


}