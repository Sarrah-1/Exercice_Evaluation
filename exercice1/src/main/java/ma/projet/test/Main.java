package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import ma.projet.util.HibernateUtil;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        Categorie ordinateurs = new Categorie("PC", "Ordinateurs");
        Categorie peripheriques = new Categorie("PER", "Périphériques");
        categorieService.create(ordinateurs);
        categorieService.create(peripheriques);

        Produit es12 = new Produit("E100", 100, ordinateurs);
        Produit zr85 = new Produit("Z200", 120, ordinateurs);
        Produit ee85 = new Produit("E300", 220, ordinateurs);
        Produit kb10 = new Produit("K400", 100, peripheriques);
        Produit ms20 = new Produit("M500", 250, peripheriques);
        produitService.create(es12);
        produitService.create(zr85);
        produitService.create(ee85);
        produitService.create(kb10);
        produitService.create(ms20);

        Commande c1 = new Commande(LocalDate.of(2019, 3, 14));
        Commande c2 = new Commande(LocalDate.of(2020, 4, 2));
        Commande c3 = new Commande(LocalDate.of(2021, 5, 20));
        commandeService.create(c1);
        commandeService.create(c2);
        commandeService.create(c3);

        ligneService.create(new LigneCommandeProduit(es12, c1, 7));
        ligneService.create(new LigneCommandeProduit(zr85, c1, 14));
        ligneService.create(new LigneCommandeProduit(ee85, c1, 5));
        ligneService.create(new LigneCommandeProduit(kb10, c2, 3));
        ligneService.create(new LigneCommandeProduit(ms20, c2, 10));
        ligneService.create(new LigneCommandeProduit(es12, c3, 2));

        System.out.println("Liste des catégories");
        for (Categorie c : categorieService.findAll()) {
            System.out.println(c.getId() + " " + c.getCode() + " " + c.getLibelle());
        }

        System.out.println();
        System.out.println("Liste des produits");
        for (Produit p : produitService.findAll()) {
            System.out.println(p.getId() + " " + p.getReference() + " " + p.getPrix());
        }

        System.out.println();
        System.out.println("Produits par catégorie");
        produitService.afficherProduitsParCategorie(ordinateurs);
        System.out.println();
        produitService.afficherProduitsParCategorie(peripheriques);

        System.out.println();
        System.out.println("Produits commandés entre deux dates");
        produitService.afficherProduitsCommandesEntreDates(LocalDate.of(2013, 3, 1), LocalDate.of(2013, 4, 30));

        System.out.println();
        System.out.println("Produits d'une commande");
        produitService.afficherProduitsParCommande(c1.getId());

        System.out.println();
        System.out.println("Produits dont le prix est supérieur à 100 DH");
        produitService.afficherProduitsPrixSuperieurA(100);

        HibernateUtil.shutdown();
    }
}
