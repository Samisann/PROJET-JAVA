package com.site.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category{
  @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "code_category")
   private Long code;
   @Column(name = "intitule")
   private String libelle;
   @JoinColumn(name = "code_produit")
   @ManyToOne(optional = false, cascade = CascadeType.ALL)
   private Produit produit;

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

   public Produit getProduit() {
      return produit;
   }

   public void setProduit(Produit produit) {
      this.produit = produit;
   }

   public void addProduit(Produit produit) {
      this.produit = produit;
      produit.getCategories().add(this);
   }

}