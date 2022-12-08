package com.site.entities;

import java.util.ArrayList;
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
@Table(name = "produit")
public class Produit { 
	
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "code_produit")
   private Long code;
   
   @Column(name = "libelle")
   private String libelle;
   
   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "produit")
   private Set<Produit> produits;
   
   @OneToMany
   @JoinTable( name = "produit",
               joinColumns = @JoinColumn( name = "Produit" ),
               inverseJoinColumns = @JoinColumn( name = "idCommand" ) )
   private List<Produit> commands = new ArrayList<>();

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

   public void addProduit(Produit produit) {
      if (this.produits == null) {
         this.produits = new HashSet<>();
      }
      this.produits.add(produit);
   }

   public void removeProduit(Produit produit) {
      if (this.produits != null)
         produits.remove(produit);
   }

   public Set<Produit> getProduits() {
      return produits;
   }


}